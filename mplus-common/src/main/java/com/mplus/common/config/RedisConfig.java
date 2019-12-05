package com.mplus.common.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.LinkedHashSet;
import java.util.List;

@Configuration
@EnableConfigurationProperties({CacheProperties.class})
@ConditionalOnBean({RedisConnectionFactory.class})
@AutoConfigureAfter({CacheAutoConfiguration.class})
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    private RedisConnectionFactory redisConnectionFactory;
    private CacheProperties cacheProperties;

    RedisConfig(RedisConnectionFactory redisConnectionFactory, CacheProperties cacheProperties) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.cacheProperties = cacheProperties;
    }

    /**
     * 配置缓存管理器
     */
    @Bean
    public CacheManager cacheManager() {
        RedisSerializer<String> keySerializer = keySerializer();
        Jackson2JsonRedisSerializer<Object> valueSerializer = valueSerializer();

        CacheProperties.Redis redisProperties = this.cacheProperties.getRedis();

        // 生成一个默认配置，通过config对象即可对缓存进行自定义配置
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                // 设置 key为string序列化
                .serializeKeysWith(SerializationPair.fromSerializer(keySerializer))
                // 设置value为json序列化
                .serializeValuesWith(SerializationPair.fromSerializer(valueSerializer));

        if (redisProperties.getTimeToLive() != null) {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        if (redisProperties.getKeyPrefix() != null) {
            config = config.prefixKeysWith(redisProperties.getKeyPrefix());
        }
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }

        // 使用自定义的缓存配置初始化一个cacheManager
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(config);

        List<String> cacheNames = this.cacheProperties.getCacheNames();
        if (!cacheNames.isEmpty()) {
            builder.initialCacheNames(new LinkedHashSet<>(cacheNames));
        }
        return builder.build();
    }

    // redis key序列化
    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    // redis value序列化
    private Jackson2JsonRedisSerializer<Object> valueSerializer() {
        Jackson2JsonRedisSerializer<Object> valueSerializer = new Jackson2JsonRedisSerializer(Object.class);

        // 解决查询缓存转换异常的问题
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        valueSerializer.setObjectMapper(om);
        return valueSerializer;
    }
}
