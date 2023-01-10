package com.example.rediscustomserializer.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.List;

@EnableRedisRepositories
@Configuration
public class RedisConfiguration {

    @Value(value = "${redis.server.address:}")
    private String redisServerAddress;

    @Value(value = "${redis.server.port}")
    private int redisServerPort;

    @Value(value = "${redis.server.username}")
    private String redisUsername;
    @Value(value = "${redis.server.password}")
    private String redisServerPassword;

    @Value(value = "${redis.server.environment}")
    private String redisServerEnvironment;

    @Value(value = "${redis.server.cluster.nodes:}")
    private List<String> redisServerClusterNodes;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
            return new LettuceConnectionFactory(localRedisConnection());
    }

    @Bean("shRedisTemplate")
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setKeySerializer(RedisSerializer.json());
        template.setValueSerializer(RedisSerializer.json());
        template.setHashValueSerializer(RedisSerializer.java());
        template.setHashKeySerializer(RedisSerializer.json());
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }



    private RedisStandaloneConfiguration localRedisConnection(){
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName("localhost");
        redisStandaloneConfiguration.setUsername(redisUsername);
        redisStandaloneConfiguration.setPassword(redisServerPassword);
        redisStandaloneConfiguration.setPort(redisServerPort);
        return redisStandaloneConfiguration;
    }

//    private RedisClusterConfiguration openshiftRedisConnection(){
//        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
//        if (redisServerClusterNodes != null && !redisServerClusterNodes.isEmpty()) {
//            redisClusterConfiguration = new RedisClusterConfiguration(redisServerClusterNodes);
//        } else {
//            redisClusterConfiguration.addClusterNode(new RedisNode(redisServerAddress, redisServerPort));
//        }
//        redisClusterConfiguration.setPassword(RedisPassword.of(redisServerPassword));
//        return redisClusterConfiguration;
//    }
}
