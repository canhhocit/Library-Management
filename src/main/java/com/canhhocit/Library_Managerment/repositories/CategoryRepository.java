package com.canhhocit.Library_Managerment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.canhhocit.Library_Managerment.entities.Category;

public interface CategoryRepository extends JpaRepository<Category,Long>{
    
}
