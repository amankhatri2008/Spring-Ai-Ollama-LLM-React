package com.ai.learning.springai;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class EmbeddingModelConfig {

    @Value("${spring.ai.ollama.base-url}")
    private String ollamaBaseUrl;

/*    @Bean
    public EmbeddingModel ollamaEmbeddingModel(OllamaApi ollamaApi) {

        return new OllamaEmbeddingModel(ollamaBaseUrl);
    }*/
}
