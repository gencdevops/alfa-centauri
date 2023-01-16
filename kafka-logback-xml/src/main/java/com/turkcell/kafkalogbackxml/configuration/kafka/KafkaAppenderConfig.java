package com.turkcell.kafkalogbackxml.configuration.kafka;

import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.spi.AppenderAttachable;
import com.turkcell.kafkalogbackxml.configuration.kafka.delivery.BlockingDeliveryStrategy;
import com.turkcell.kafkalogbackxml.configuration.kafka.delivery.DeliveryStrategy;
import com.turkcell.kafkalogbackxml.configuration.kafka.encoding.KafkaMessageEncoder;
import com.turkcell.kafkalogbackxml.configuration.kafka.keying.KeyingStrategy;
import com.turkcell.kafkalogbackxml.configuration.kafka.keying.RoundRobinKeyingStrategy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class KafkaAppenderConfig<E> extends UnsynchronizedAppenderBase<E> implements AppenderAttachable<E> {
    protected String topic = null;
    protected KafkaMessageEncoder<E> encoder = null;
    protected KeyingStrategy<? super E> keyingStrategy = null;
    protected DeliveryStrategy deliveryStrategy;
    public static final Set<String> KNOWN_PRODUCER_CONFIG_KEYS = new HashSet();
    public static final Map<String, String> DEPRECATED_PRODUCER_CONFIG_KEYS = new HashMap();
    protected Map<String, Object> producerConfig = new HashMap();

    public KafkaAppenderConfig() {
    }

    protected boolean checkPrerequisites() {
        boolean errorFree = true;
        if (this.producerConfig.get("bootstrap.servers") == null) {
            this.addError("No \"bootstrap.servers\" set for the appender named [\"" + this.name + "\"].");
            errorFree = false;
        }

        if (this.topic == null) {
            this.addError("No topic set for the appender named [\"" + this.name + "\"].");
            errorFree = false;
        }

        if (this.encoder == null) {
            this.addError("No encoder set for the appender named [\"" + this.name + "\"].");
            errorFree = false;
        }

        if (this.keyingStrategy == null) {
            this.addInfo("No partitionStrategy set for the appender named [\"" + this.name + "\"]. Using default RoundRobin strategy.");
            this.keyingStrategy = new RoundRobinKeyingStrategy();
        }

        if (this.deliveryStrategy == null) {
            this.addInfo("No sendStrategy set for the appender named [\"" + this.name + "\"]. Using default Blocking strategy.");
            this.deliveryStrategy = new BlockingDeliveryStrategy();
        }

        return errorFree;
    }

    public void setEncoder(KafkaMessageEncoder<E> layout) {
        this.encoder = layout;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setKeyingStrategy(KeyingStrategy<? super E> keyingStrategy) {
        this.keyingStrategy = keyingStrategy;
    }

    public void addProducerConfig(String keyValue) {
        String[] split = keyValue.split("=", 2);
        if (split.length == 2) {
            this.addProducerConfigValue(split[0], split[1]);
        }

    }

    public void addProducerConfigValue(String key, Object value) {
        if (!KNOWN_PRODUCER_CONFIG_KEYS.contains(key)) {
            this.addWarn("The key \"" + key + "\" is now a known kafka producer config key.");
        }

        if (DEPRECATED_PRODUCER_CONFIG_KEYS.containsKey(key)) {
            StringBuilder deprecationMessage = new StringBuilder("The key \"" + key + "\" is deprectated in kafka and may be removed in a future version.");
            if (DEPRECATED_PRODUCER_CONFIG_KEYS.get(key) != null) {
                deprecationMessage.append(" Consider using key \"").append((String)DEPRECATED_PRODUCER_CONFIG_KEYS.get(key)).append("\" instead.");
            }

            this.addWarn(deprecationMessage.toString());
        }

        this.producerConfig.put(key, value);
    }

    public Map<String, Object> getProducerConfig() {
        return this.producerConfig;
    }

    public void setDeliveryStrategy(DeliveryStrategy deliveryStrategy) {
        this.deliveryStrategy = deliveryStrategy;
    }

    static {
        KNOWN_PRODUCER_CONFIG_KEYS.add("bootstrap.servers");
        KNOWN_PRODUCER_CONFIG_KEYS.add("metadata.fetch.timeout.ms");
        DEPRECATED_PRODUCER_CONFIG_KEYS.put("metadata.fetch.timeout.ms", "max.block.ms");
        KNOWN_PRODUCER_CONFIG_KEYS.add("metadata.max.age.ms");
        KNOWN_PRODUCER_CONFIG_KEYS.add("batch.size");
        KNOWN_PRODUCER_CONFIG_KEYS.add("buffer.memory");
        KNOWN_PRODUCER_CONFIG_KEYS.add("acks");
        KNOWN_PRODUCER_CONFIG_KEYS.add("timeout.ms");
        DEPRECATED_PRODUCER_CONFIG_KEYS.put("metadata.fetch.timeout.ms", "request.timeout.ms");
        KNOWN_PRODUCER_CONFIG_KEYS.add("linger.ms");
        KNOWN_PRODUCER_CONFIG_KEYS.add("client.id");
        KNOWN_PRODUCER_CONFIG_KEYS.add("send.buffer.bytes");
        KNOWN_PRODUCER_CONFIG_KEYS.add("receive.buffer.bytes");
        KNOWN_PRODUCER_CONFIG_KEYS.add("max.request.size");
        KNOWN_PRODUCER_CONFIG_KEYS.add("reconnect.backoff.ms");
        KNOWN_PRODUCER_CONFIG_KEYS.add("block.on.buffer.full");
        DEPRECATED_PRODUCER_CONFIG_KEYS.put("metadata.fetch.timeout.ms", null);
        KNOWN_PRODUCER_CONFIG_KEYS.add("retries");
        KNOWN_PRODUCER_CONFIG_KEYS.add("retry.backoff.ms");
        KNOWN_PRODUCER_CONFIG_KEYS.add("compression.type");
        KNOWN_PRODUCER_CONFIG_KEYS.add("metrics.sample.window.ms");
        KNOWN_PRODUCER_CONFIG_KEYS.add("metrics.num.samples");
        KNOWN_PRODUCER_CONFIG_KEYS.add("metric.reporters");
        KNOWN_PRODUCER_CONFIG_KEYS.add("max.in.flight.requests.per.connection");
        KNOWN_PRODUCER_CONFIG_KEYS.add("key.serializer");
        KNOWN_PRODUCER_CONFIG_KEYS.add("value.serializer");
        KNOWN_PRODUCER_CONFIG_KEYS.add("connections.max.idle.ms");
        KNOWN_PRODUCER_CONFIG_KEYS.add("partitioner.class");
        KNOWN_PRODUCER_CONFIG_KEYS.add("max.block.ms");
        KNOWN_PRODUCER_CONFIG_KEYS.add("request.timeout.ms");
    }
}
