package com.examapp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examapp.model.ExamCategory;

@Repository
public interface ExamCategoryRepository extends JpaRepository<ExamCategory, Long> {
}