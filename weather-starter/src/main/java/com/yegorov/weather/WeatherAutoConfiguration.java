package com.yegorov.weather;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@AutoConfiguration
@ConditionalOnClass(RestClient.class)
@EnableConfigurationProperties(WeatherProperties.class)
@ConditionalOnProperty(
        prefix = "weather",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class WeatherAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    @ConditionalOnMissingBean
    public WeatherService weatherService(RestClient.Builder builder,
                                         WeatherProperties props) {
        return new DefaultWeatherService(builder, props);
    }
}
