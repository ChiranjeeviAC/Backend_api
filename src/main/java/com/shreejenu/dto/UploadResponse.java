package com.shreejenu.dto;

// We have removed 'import lombok.Data;' and other lombok annotations
public class UploadResponse {

    private String uploadUrl;
    private String s3Key;

    // Manual constructor
    public UploadResponse(String uploadUrl, String s3Key) {
        this.uploadUrl = uploadUrl;
        this.s3Key = s3Key;
    }

    // --- Manually added Getters and Setters ---

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public String getS3Key() {
        return s3Key;
    }

    public void setS3Key(String s3Key) {
        this.s3Key = s3Key;
    }
}