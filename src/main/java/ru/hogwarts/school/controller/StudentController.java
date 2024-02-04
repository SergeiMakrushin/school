package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.sevice.StudentService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student create(@RequestBody Student student) {
        return this.studentService.createStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student foundStudent = studentService.updateStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable long id) {
        studentService.removeElement(id);
        return ResponseEntity.ok().build();

    }

    @GetMapping
    public Collection<Student> getAllStudent() {
        return this.studentService.getAllStudent();
    }

    @GetMapping("/age/{intAge}")
    public ResponseEntity<Collection<Student>> searchStudentAge(@PathVariable("intAge") int intAge) {
        if (intAge > 0 & intAge < 100) {
            return ResponseEntity.ok(studentService.findByAge(intAge));
        }
        return ResponseEntity.badRequest().build();
    }


// 1 Получить всех студентов, возраст которых находится между

    @GetMapping("/age")
    public ResponseEntity<Collection<Student>> findByAgeBetween(@RequestParam("min") int min,
                                                                @RequestParam("max") int max) {
        if (min > 0 & max < 100) {

            return ResponseEntity.ok(studentService.findByAgeBetween(min, max));
        }
        return ResponseEntity.badRequest().build();
    }

    // 2 Получить всех студентов, но отобразить только список их имен.
    @GetMapping("/name")
    public Collection<String> getAllNameStudents() {
        return studentService.getAllNameStudent();
    }

    // 3 Получить всех студентов, у которых в имени присутствует буква
    @GetMapping("/part_name")
    public Collection<Student> findAllByNameContains(@RequestParam("name") String name) {
        return studentService.findAllByNameContains(name);
    }

    // 4 Получить всех студентов, у которых возраст меньше идентификатора.
    @GetMapping("/grouping_age_less_id")
    public Collection<Student> findAllByAgeLessThanId() {
        return studentService.findAllByAgeLessThanId();
    }

    // 5 получить всех студентов упорядоченных по возрасту
    @GetMapping("/grouping_age")
    public Collection<Student> findByOrderByAge() {
        return studentService.findByOrderByAge();
    }

    @GetMapping("/student_id")
    public ResponseEntity<Student> findStudent(@RequestParam("id") Long id) {

        if (id != null) {
            return ResponseEntity.ok(studentService.findStudent(id));
        }
//        return ResponseEntity.ok(TypeResolutionContext.Empty);
        return ResponseEntity.noContent().build();
    }

    //6 получить факультет студента
    @GetMapping("/student_faculty_id")
    public ResponseEntity<Collection<Long>> findStudentFaculty(
            @RequestParam("name") String name) {
        if (name != null & !name.isBlank()) {
            return ResponseEntity.ok(studentService.findStudentFaculty(name));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

// 7 получить студентов факультета

    @GetMapping("faculty_students")
    public ResponseEntity<Collection<Student>> findFacultyStudents(
            @RequestParam("id") Integer id) {
        if (id != null) {
            return ResponseEntity.ok(studentService.findFacultyStudents(id));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    // 8 вернуть количество студентов в школе
    @GetMapping("/count")
    public ResponseEntity<Integer> getCountStudent() {
        return ResponseEntity.ok(studentService.getCountStudent());
    }

    //9 вернуть средний возраст студентов
    @GetMapping("/average_age")
    public ResponseEntity<Integer> getAverageAgeStudent() {
        return ResponseEntity.ok(studentService.getAverageAgeStudent());
    }

    @GetMapping("/limit_desc_five")
    public ResponseEntity<Collection<Student>> getStudentOrderByIdDescLimitFive() {
        return ResponseEntity.ok(studentService.getStudentOrderByIdDescLimitFive());
    }

    @GetMapping("/limit")
    public ResponseEntity<List<Student>> getStudentLimit(@RequestParam("page") Integer pageNamber, @RequestParam("size") Integer pageSize) {
        List<Student> students = studentService.getStudentLimit(pageNamber, pageSize);
        return ResponseEntity.ok(students);
    }

}
