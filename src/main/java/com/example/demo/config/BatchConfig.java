package com.example.demo.config;

import com.example.demo.entity.StudentMarks;
import com.example.demo.entity.StudentResult;
import com.example.demo.processor.GradeProcessor;
import com.example.demo.writer.StudentResultWriter;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public JpaPagingItemReader<StudentMarks> reader() {
        JpaPagingItemReader<StudentMarks> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("SELECT s FROM StudentMarks s ORDER BY s.id");
        reader.setPageSize(10); 
        return reader;
    }

    
    @Bean
    public Step step1(JobRepository jobRepository,
                       PlatformTransactionManager txManager,
                       GradeProcessor processor,
                       StudentResultWriter writer) {
        return new StepBuilder("step1", jobRepository)
                .<StudentMarks, StudentResult>chunk(10, txManager)  
                .reader(reader())
                .processor(processor)
                .writer(writer)
                .build();
    }

    
    @Bean
    public Job job(JobRepository jobRepository, Step step1) {
        return new JobBuilder("gradeJob", jobRepository)
                .start(step1)
                .build();
    }
}
