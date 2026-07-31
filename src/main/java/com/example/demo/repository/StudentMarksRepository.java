package com.example.demo.repository;

import com.example.demo.entity.StudentMarks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentMarksRepository extends JpaRepository<StudentMarks, Long> {
}
