package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.sevice.StudentService;

import java.util.Collection;

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
    public Collection<Student> searchStudentAge(@PathVariable ("intAge") int intAge){
           return  this.studentService.searchStudentAge(intAge);
}
    @PutMapping
    public Student updateStudent (@RequestBody Student student) {
        return  this.studentService.updateStudent(student);
    }
    @DeleteMapping ("{id}")
    public Student deleteStudent (@PathVariable  long id) {
        return this.studentService.removeElement(id);
    }

}
