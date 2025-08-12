package com.ai.learning.springai;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class PolicyIngestService {

    private final VectorStore vectorStore;

    public PolicyIngestService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public String ingest(MultipartFile file) throws IOException {
        // Wrap file in Resource for Tika
        ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };

        // Extract text
        TikaDocumentReader tikaReader = new TikaDocumentReader(resource);
        List<Document> docs = tikaReader.get();
        System.out.println("I am here 2");
        // Split into chunks
        TokenTextSplitter splitter = TokenTextSplitter.builder()
                .withChunkSize(800)
                .build();
        List<Document> chunks = splitter.split(docs);

        // Store in vector store
        vectorStore.add(chunks);

        return UUID.randomUUID().toString();
    }
}
