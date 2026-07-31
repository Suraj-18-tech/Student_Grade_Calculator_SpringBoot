package com.example.demo.writer;

import com.example.demo.entity.StudentResult;
import com.example.demo.repository.StudentResultRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentResultWriter implements ItemWriter<StudentResult> {

    @Autowired
    private StudentResultRepository studentResultRepository;  

    @Override
    public void write(Chunk<? extends StudentResult> chunk) {
        studentResultRepository.saveAll(chunk.getItems());  
        System.out.println("Wrote chunk of size: " + chunk.size() + " into StudentResult table");
    }
}
