### Logback 

In order to send logs thrown with Slf4j to a Kafka topic.  
logback.xml configuration file needs to be defined in the resources directory.  
When defining this configuration, file and console appenders are provided by default, but there is no appender provided for Kafka.  
Community libraries are available for a Kafka appender, or we can define our own appender class.