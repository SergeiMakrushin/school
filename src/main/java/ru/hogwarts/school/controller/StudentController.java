package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.sevice.StudentService;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/student")
public class StudentController {
    StudentService studentService;
    StudentController (StudentService studentService) {
        this.studentService=studentService;
    }

    @PostMapping
public Student create (@RequestBody Student student) {
        return this.studentService.createStudent(student);
    }


    @GetMapping
    public Collection<Student> getAllStudent() {
        return this.studentService.getAllStudent();
    }

    @GetMapping("/age/{intAge}")
    public ResponseEntity<Collection<Student>> searchStudentAge(@PathVariable ("intAge") int intAge){
        if (intAge > 0) {
            return ResponseEntity.ok(studentService.searchStudentAge(intAge));
        }
        return ResponseEntity.ok(Collections.emptyList());

}

    @PutMapping
    public ResponseEntity<Student> updateStudent (@RequestBody Student student) {
        Student foundStudent=studentService.updateStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }
    @DeleteMapping ("{id}")
    public ResponseEntity<Void> deleteStudent (@PathVariable  long id) {
        studentService.removeElement(id);
        return ResponseEntity.ok().build();

    }

}
