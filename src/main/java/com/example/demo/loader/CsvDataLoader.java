package com.example.demo.loader;

import com.example.demo.entity.StudentMarks;
import com.example.demo.repository.StudentMarksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.annotation.Order;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
@Order(1)   
public class CsvDataLoader implements CommandLineRunner {

    @Autowired
    private StudentMarksRepository studentMarksRepository;

    @Override
    public void run(String... args) throws Exception {

        if (studentMarksRepository.count() > 0) {
            System.out.println("StudentMarks table already has data. Skipping CSV import.");
            return;
        }

        ClassPathResource resource = new ClassPathResource("students.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));

        String line;
        boolean firstLine = true;

        while ((line = reader.readLine()) != null) {
            if (firstLine) { firstLine = false; continue; }

            String[] parts = line.split(",");
            String name = parts[0].trim();
            int marks = Integer.parseInt(parts[1].trim());

            StudentMarks student = new StudentMarks(name, marks);
            studentMarksRepository.save(student);  
        }

        reader.close();
        System.out.println("CSV Import Done! Rows imported: " + studentMarksRepository.count());
    }
}
