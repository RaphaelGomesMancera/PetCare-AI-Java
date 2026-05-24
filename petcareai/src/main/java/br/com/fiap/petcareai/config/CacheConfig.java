package br.com.fiap.petcareai.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
    // Spring Boot autoconfigura o SimpleCacheManager via spring.cache.type=simple
    // Os caches "pets" e "recomendacoes" são criados automaticamente ao primeiro uso
}

