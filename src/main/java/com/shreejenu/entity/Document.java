package com.shreejenu.entity;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import java.time.Instant;

// We have removed 'import lombok.Data;' and the '@Data' annotation

@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String s3Key; // Unique key for the object in AWS S3

    private Instant uploadTimestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private String metadata; // Stores JSON metadata as a String

    // --- Manually added Getters and Setters ---
    // These replace the @Data annotation to fix the compilation error.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) { // <-- This is the missing method
        this.fileName = fileName;
    }

    public String getS3Key() {
        return s3Key;
    }

    public void setS3Key(String s3Key) {
        this.s3Key = s3Key;
    }

    public Instant getUploadTimestamp() {
        return uploadTimestamp;
    }

    public void setUploadTimestamp(Instant uploadTimestamp) {
        this.uploadTimestamp = uploadTimestamp;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}