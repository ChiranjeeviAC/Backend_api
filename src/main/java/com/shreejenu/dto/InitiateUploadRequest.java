package com.shreejenu.dto;

// We have removed 'import lombok.Data;' and the @Data annotation
public class InitiateUploadRequest {

    private String fileName;
    private String fileType;

    // --- Manually added Getters and Setters ---

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}