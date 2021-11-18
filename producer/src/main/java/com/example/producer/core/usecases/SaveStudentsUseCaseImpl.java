package com.example.producer.core.usecases;

import com.example.producer.core.model.Student;
import com.example.producer.core.ports.SaveStudentsUseCase;
import com.example.producer.core.ports.StudentRepository;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class SaveStudentsUseCaseImpl implements SaveStudentsUseCase {

    private final StudentRepository studentRepository;

    @Override
    public UUID execute(Student student) {
        return studentRepository.save(student);
    }
}
