package com.example.producer.core.ports;

import com.example.producer.core.model.Student;

import java.util.UUID;

public interface SaveStudentsUseCase {

    UUID execute(Student student);


}
