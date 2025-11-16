package com.shreejenu.controller;

import com.shreejenu.dto.InitiateUploadRequest;
import com.shreejenu.dto.UploadResponse;
import com.shreejenu.entity.Document;
import com.shreejenu.entity.User;
import com.shreejenu.repository.DocumentRepository;
import com.shreejenu.repository.UserRepository;
import com.shreejenu.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private S3Service s3Service;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/initiate-upload")
    public ResponseEntity<UploadResponse> initiateUpload(
            @RequestBody InitiateUploadRequest request,
            Authentication authentication // Spring Security injects the current user
    ) {

        // 1. Get the authenticated user
        String userEmail = authentication.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));

        // 2. Generate a unique key for S3
        String s3Key = "uploads/" + user.getId() + "/" + UUID.randomUUID().toString() + "_" + request.getFileName();

        // 3. Generate the pre-signed URL
        String uploadUrl = s3Service.generateUploadUrl(s3Key);

        // 4. Create and save the document metadata to PostgreSQL
        Document newDocument = new Document();
        newDocument.setFileName(request.getFileName());
        newDocument.setS3Key(s3Key);
        newDocument.setOwner(user);
        newDocument.setUploadTimestamp(Instant.now());

        // Create a simple JSON string for the metadata
        String metadataJson = "{\"fileType\": \"" + request.getFileType() + "\"}";
        newDocument.setMetadata(metadataJson);

        documentRepository.save(newDocument);

        // 5. Return the URL and key to the client
        return ResponseEntity.ok(new UploadResponse(uploadUrl, s3Key));
    }

    @GetMapping("/{documentId}/download-url")
    public ResponseEntity<String> getDownloadUrl(@PathVariable Long documentId, Authentication authentication) {

        // 1. Find the document in the database
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        // 2. Authorize: Check if the authenticated user owns this document
        String userEmail = authentication.getName();
        if (!document.getOwner().getEmail().equals(userEmail)) {
            // Note: In a real app, you'd throw a 403 Forbidden exception
            throw new RuntimeException("You are not authorized to access this file");
        }

        // 3. Get the S3 key
        String s3Key = document.getS3Key();

        // 4. Generate and return the pre-signed download URL
        String downloadUrl = s3Service.generateDownloadUrl(s3Key);

        return ResponseEntity.ok(downloadUrl);
    }
}