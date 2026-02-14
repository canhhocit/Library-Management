package com.canhhocit.Library_Managerment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.canhhocit.Library_Managerment.entities.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    
}