package com.turkcell.kafkalogbackxml.configuration.kafka;


import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.spi.AppenderAttachableImpl;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import com.turkcell.kafkalogbackxml.configuration.kafka.delivery.FailedDeliveryCallback;


public class KafkaAppender<E> extends KafkaAppenderConfig<E> {
    private static final String KAFKA_LOGGER_PREFIX = "org.apache.kafka.clients";
    private KafkaAppender<E>.LazyProducer lazyProducer = null;
    private final AppenderAttachableImpl<E> aai = new AppenderAttachableImpl();
    private final ConcurrentLinkedQueue<E> queue = new ConcurrentLinkedQueue();
    private final FailedDeliveryCallback<E> failedDeliveryCallback = new FailedDeliveryCallback<E>() {
        public void onFailedDelivery(E evt, Throwable throwable) {
            KafkaAppender.this.aai.appendLoopOnAppenders(evt);
        }
    };

    public KafkaAppender() {
        this.addProducerConfigValue("key.serializer", ByteArraySerializer.class.getName());
        this.addProducerConfigValue("value.serializer", ByteArraySerializer.class.getName());
    }

    public void doAppend(E e) {
        this.ensureDeferredAppends();
        if (e instanceof ILoggingEvent && ((ILoggingEvent)e).getLoggerName().startsWith("org.apache.kafka.clients")) {
            this.deferAppend(e);
        } else {
            super.doAppend(e);
        }

    }

    public void start() {
        if (this.checkPrerequisites()) {
            this.lazyProducer = new LazyProducer();
            super.start();
        }
    }

    public void stop() {
        super.stop();
        if (this.lazyProducer != null && this.lazyProducer.isInitialized()) {
            try {
                this.lazyProducer.get().close();
            } catch (KafkaException var2) {
                this.addWarn("Failed to shut down kafka producer: " + var2.getMessage(), var2);
            }

            this.lazyProducer = null;
        }

    }

    public void addAppender(Appender<E> newAppender) {
        this.aai.addAppender(newAppender);
    }

    public Iterator<Appender<E>> iteratorForAppenders() {
        return this.aai.iteratorForAppenders();
    }

    public Appender<E> getAppender(String name) {
        return this.aai.getAppender(name);
    }

    public boolean isAttached(Appender<E> appender) {
        return this.aai.isAttached(appender);
    }

    public void detachAndStopAllAppenders() {
        this.aai.detachAndStopAllAppenders();
    }

    public boolean detachAppender(Appender<E> appender) {
        return this.aai.detachAppender(appender);
    }

    public boolean detachAppender(String name) {
        return this.aai.detachAppender(name);
    }

    protected void append(E e) {
        byte[] payload = this.encoder.doEncode(e);
        byte[] key = this.keyingStrategy.createKey(e);
        ProducerRecord<byte[], byte[]> record = new ProducerRecord(this.topic, key, payload);
        this.deliveryStrategy.send(this.lazyProducer.get(), record, e, this.failedDeliveryCallback);
    }

    protected Producer<byte[], byte[]> createProducer() {
        return new KafkaProducer(new HashMap(this.producerConfig));
    }

    private void deferAppend(E event) {
        this.queue.add(event);
    }

    private void ensureDeferredAppends() {
        Object event;
        while((event = this.queue.poll()) != null) {
            super.doAppend((E) event);
        }

    }

    private class LazyProducer {
        private volatile Producer<byte[], byte[]> producer;

        private LazyProducer() {
        }

        public Producer<byte[], byte[]> get() {
            Producer<byte[], byte[]> result = this.producer;
            if (result == null) {
                synchronized(this) {
                    result = this.producer;
                    if (result == null) {
                        this.producer = result = this.initialize();
                    }
                }
            }

            return result;
        }

        protected Producer<byte[], byte[]> initialize() {
            Producer<byte[], byte[]> producer = null;

            try {
                producer = KafkaAppender.this.createProducer();
            } catch (Exception var3) {
                KafkaAppender.this.addError("error creating producer", var3);
            }

            return producer;
        }

        public boolean isInitialized() {
            return this.producer != null;
        }
    }
}
