package com.ai.learning.springai;

import org.springframework.ai.chat.client.ChatClient;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/policies")
public class PolicyQAController {

    private final PolicyIngestService ingestService;
    private final ChatClient.Builder chatClientBuilder;
    private final VectorStore vectorStore;

    public PolicyQAController(PolicyIngestService ingestService,
                              ChatClient.Builder chatClientBuilder,
                              VectorStore vectorStore) {
        this.ingestService = ingestService;
        this.chatClientBuilder = chatClientBuilder;
        this.vectorStore = vectorStore;
    }

    @PostMapping("/upload")
    public Map<String, String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("I am here");
        String policyId = ingestService.ingest(file);
        return Map.of("policyId", policyId);
    }

    @PostMapping("/{policyId}/ask")
    public String ask(@PathVariable String policyId, @RequestBody Map<String, String> body) {
        String question = body.get("question");



        // 1. Retrieve relevant chunks from vector store
        List<Document> relevantDocs = vectorStore.similaritySearch(SearchRequest.builder()
                .query(question)
                .topK(3)
                .build());

        // 2. Combine document text as context
        StringBuilder contextBuilder = new StringBuilder();
        for (Document doc : relevantDocs) {
            contextBuilder.append(doc.getText()).append("\n---\n");
        }
        String context = contextBuilder.toString();

        // 3. Build system prompt with context
        String systemPrompt = "You are a helpful assistant answering questions based on the following documents:\n" + context;

        // 4. Call chat model
        var chatClient = chatClientBuilder
                .defaultSystem(systemPrompt)
                .build();

        var response = chatClient.prompt()
                .user(question)
                .call();

        return response.content();

    }
}
