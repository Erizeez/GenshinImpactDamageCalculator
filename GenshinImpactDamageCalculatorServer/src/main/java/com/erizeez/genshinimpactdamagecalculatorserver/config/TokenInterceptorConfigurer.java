package com.erizeez.genshinimpactdamagecalculatorserver.config;

import com.erizeez.genshinimpactdamagecalculatorserver.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class TokenInterceptorConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new TokenInterceptor());
        registration.addPathPatterns("/**");
        registration.excludePathPatterns(

                "/user/register"
        );
    }
}
