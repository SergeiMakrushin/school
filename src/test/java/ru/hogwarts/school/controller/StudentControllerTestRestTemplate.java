package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTestRestTemplate {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoad() throws Exception {

        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    void getAllStudentTest() throws Exception {
        Student student = new Student(21L, "TestName", 21);
        Student testStudent = studentRepository.save(student);

        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student", String.class))
                .isNotNull().isNotEmpty().contains(student.getName());

        studentController.deleteStudent(testStudent.getId());
    }

    @Test
    void searchStudentAgeTest() throws Exception {
        Student student = new Student(21L, "TestName", 19);
        Student testStudent = studentRepository.save(student);

        ResponseEntity<List<Student>> studentsList = restTemplate.exchange("http://localhost:" + port + "/student/age/{intAge}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                },
                student.getAge());

        Assertions.assertThat(studentsList
                .getBody()
                .stream()
                .map(e -> e.getName())
                .toList().contains(student.getName()));

        studentController.deleteStudent(testStudent.getId());
    }

    @Test
    void findByAgeBetweenTest() throws Exception {
        Student student = new Student(21L, "TestName", 19);
        Student testStudent = studentRepository.save(student);

        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/age?min=18&max=20", String.class))
                .contains(student.getName());

        studentController.deleteStudent(testStudent.getId());
    }

    @Test
    void getAllNameStudentsTest() throws Exception {

        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/name", String.class))
                .isNotEmpty();
    }

    @Test
    void findAllByNameContainsTest() throws Exception {
        Student student = new Student(21L, "TestName", 19);
        Student testStudent = studentRepository.save(student);

        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/part_name?name=TestName", String.class))
                .contains(student.getName());

        studentController.deleteStudent(testStudent.getId());
    }

    @Test
    void findAllByAgeLessThanIdTest() throws Exception {

        ResponseEntity<List<Student>> studentsList = restTemplate.exchange("http://localhost:" + port + "/student/grouping_age_less_id",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                });

        Assertions.assertThat(studentsList
                        .getBody()
                        .stream()
                        .filter(e -> e.getAge() < e.getId())
                        .toList()
                        .size())
                .isEqualTo(studentsList.getBody().size());


    }

    @Test
    void findByOrderByAgeTest() throws Exception {

        ResponseEntity<List<Student>> studentsList = restTemplate.exchange("http://localhost:" + port + "/student/grouping_age",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                });


        ResponseEntity<List<Student>> studentsListTest = restTemplate.exchange("http://localhost:" + port + "/student",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                });

        Assertions.assertThat(studentsListTest
                        .getBody()
                        .stream()
                        .sorted(Comparator.comparing(Student::getAge))
                        .toList())
                .isEqualTo(studentsList.getBody());


    }

    @Test
    void findStudentTest() throws Exception {
        Student student = new Student(21L, "TestName", 19);
        Student testStudent = studentRepository.save(student);

        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/student_id?id=" + testStudent.getId(), String.class))
                .contains(student.getName());

        studentController.deleteStudent(testStudent.getId());
    }

    //вернуть факультет
    @Test
    void findStudentFacultyTest() throws Exception {
        Collection<String> nameStudents = new ArrayList<>();
        nameStudents = studentRepository.findAllNameStudent();

        String nameTest = nameStudents.stream().findFirst().get();

        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/student_faculty_id?name=" + nameTest, String.class))
                .isNotEmpty();
    }


    @Test
    void findFacultyStudentsTest() throws Exception {
        Collection<Faculty> allFacultys = new ArrayList<>();
        allFacultys = facultyRepository.findAll();

        Long id = allFacultys.stream().findFirst().get().getId();
        ResponseEntity<List<Student>> studentsList = restTemplate.exchange("http://localhost:" + port + "/student/faculty_students?id=" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                });

        Assertions.assertThat(studentsList).isNotNull();
    }

    @Test
    void updateStudentTest() throws Exception {
        Student student = new Student();
        student.setId(20L);
        student.setName("Лариса");
        student.setAge(25);

        Student studentTest = restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class);
        Assertions.assertThat(studentTest.getName()).isEqualTo(student.getName());

        studentRepository.deleteById(studentTest.getId());

    }

    @Test
    void deleteStudentTest() throws Exception {
        Student student1 = new Student();
        student1.setId(20L);
        student1.setName("TesName");
        student1.setAge(25);

        long id = student1.getId();

        studentRepository.save(student1);

        Assertions.assertThat(this.restTemplate.exchange("http://localhost:" + port + "/student/{id}", HttpMethod.DELETE, HttpEntity.EMPTY, String.class, id))
                .isNotNull();
    }

    @Test
    void createTest() throws Exception {
        Student student = new Student();
        student.setId(21L);
        student.setName("Филимон");
        student.setAge(24);

        int sizeTableBefore = studentController.getAllStudent().size();

        Student studentTest = restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class);
        Assertions.assertThat(studentTest.getName()).isEqualTo(student.getName());

        studentRepository.deleteById(studentTest.getId());

        int sizeTableAfter = studentController.getAllStudent().size();
        Assertions.assertThat(sizeTableBefore).isEqualTo(sizeTableAfter);
    }


}
