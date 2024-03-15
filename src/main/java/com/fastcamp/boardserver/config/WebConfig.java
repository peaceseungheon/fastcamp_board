package com.fastcamp.boardserver.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterFilterRegistrationBean() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        config.addAllowedOrigin("*");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setMaxAge(6000L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        FilterRegistrationBean<CorsFilter> filterBean = new FilterRegistrationBean<>(
            new CorsFilter(source));

        filterBean.setOrder(0);
        return filterBean;
    }
}
