package com.example.producer.entrypoints.rest;

import com.example.producer.core.model.Student;
import com.example.producer.core.ports.SaveStudentsUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class Controller {

    private final SaveStudentsUseCase saveStudentsUseCase;

    @PostMapping("/students")
    @ResponseStatus(HttpStatus.CREATED)
    public StudentOutput save(@RequestBody StudentInput request) {
        Student student = Student.builder().name(request.getName()).surname(request.getSurname()).build();
        UUID uuid = saveStudentsUseCase.execute(student);
        return StudentOutput.builder().uuid(uuid).build();
    }


}
