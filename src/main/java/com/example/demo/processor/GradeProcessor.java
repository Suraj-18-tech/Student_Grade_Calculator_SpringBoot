package com.example.demo.processor;

import com.example.demo.entity.StudentMarks;
import com.example.demo.entity.StudentResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class GradeProcessor implements ItemProcessor<StudentMarks, StudentResult> {

    private static final Logger logger = LoggerFactory.getLogger(GradeProcessor.class);

    @Override
    public StudentResult process(StudentMarks student) {

        String grade;
        int marks = student.getMarks();

        if (marks >= 90)
            grade = "A";
        else if (marks >= 75)
            grade = "B";
        else if (marks >= 50)
            grade = "C";
        else
            grade = "Fail";

        StudentResult result = new StudentResult();
        result.setName(student.getName());
        result.setGrade(grade);

        logger.info("Processed: {} ({}) -> {}", student.getName(), marks, grade);

        return result;
    }
}