package com.example.producer.core.ports;

import com.example.producer.core.model.Student;

import java.util.UUID;

public interface StudentRepository {

    UUID save(Student student);

}
