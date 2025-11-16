package com.shreejenu.repository;

import com.shreejenu.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByOwnerId(Long ownerId);
}