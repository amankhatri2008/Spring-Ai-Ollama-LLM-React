package com.ai.learning.springai;

import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.ai.embedding.EmbeddingModel;

@Configuration
public class VectorStoreConfig {

    @Bean
    public VectorStore inMemoryVectorStore(EmbeddingModel embeddingModel) {
        return SimpleVectorStore.builder(embeddingModel)
                        .build();
    }
}
