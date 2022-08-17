package com.woowacourse.gongcheck.config;

import com.woowacourse.gongcheck.auth.presentation.AuthenticationInterceptor;
import com.woowacourse.gongcheck.auth.presentation.AuthenticationPrincipalArgumentResolver;
import com.woowacourse.gongcheck.core.presentation.filter.RequestContext;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";

    private final AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver;
    private final AuthenticationInterceptor authenticationInterceptor;

    public WebConfig(final AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver,
                     final AuthenticationInterceptor authenticationInterceptor) {
        this.authenticationPrincipalArgumentResolver = authenticationPrincipalArgumentResolver;
        this.authenticationInterceptor = authenticationInterceptor;
    }

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedMethods(ALLOWED_METHOD_NAMES.split(","))
                .exposedHeaders(HttpHeaders.LOCATION);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/hosts/*/enter")
                .excludePathPatterns("/api/login");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationPrincipalArgumentResolver);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public RequestContext requestStorage() {
        return new RequestContext();
    }
}
