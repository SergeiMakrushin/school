package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Student;

import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.sevice.StudentService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    private StudentService studentServise;


    Student student1 = new Student(25L, "Vasy", 20);
    Student student2 = new Student(30L, "Igor", 21);

    Student student3 = new Student(3L, "Fedor", 22);
    Student student4 = new Student(4L, "Andrew", 20);
    List<Student> listStudents = Arrays.asList(student1, student2, student3, student4);

    @BeforeEach
    void setUp() {
        studentServise = new StudentService(studentRepository);

    }

    @Test
    void createStudent() {
        Student expected = new Student(5L, "1", 10);
        Mockito.when(studentServise.createStudent(new Student(5L, "1", 10))).thenReturn(expected);

        Student actual = studentServise.createStudent(new Student(5L, "1", 10));

        assertEquals(expected, actual);

    }

    @Test
    void getAllStudent() {
        Mockito.when(studentServise.getAllStudent()).thenReturn(listStudents);

        int actual = studentServise.getAllStudent().size();
        int expected = listStudents.size();

        assertEquals(expected, actual);

    }

    @Test
    void searchStudentAge() {
        Mockito.when(studentServise.findByAge(20)).thenReturn(listStudents);

        int actual = studentServise.findByAge(20)
                .stream()
                .filter(e -> e.getAge() == 20)
                .toList()
                .size();
        int expected = 2;

        assertEquals(expected, actual);
    }

    @Test
    void updateStudent() {
        Student expected = new Student(5L, "1", 10);
        Mockito.when(studentServise.updateStudent(new Student(5L, "1", 10))).thenReturn(expected);

        Student actual = studentServise.updateStudent(new Student(5L, "1", 10));

        assertEquals(expected, actual);
    }

    @Test
    void findByAgeBetween() {
        Mockito.when(studentServise.findByAgeBetween(20, 21)).thenReturn(listStudents);

        int actual = studentServise.findByAgeBetween(20, 21)
                .stream()
                .filter(student -> student.getAge() >= 20 & student.getAge() <= 21)
                .toList()
                .size();
        int expected = 3;

        assertEquals(expected, actual);
    }

    @Test
    void getAllNameStudent() {
        Mockito.when(studentServise.getAllNameStudent()).thenReturn(listStudents.stream().map(Student::getName).toList());

        Collection<String> actual = studentServise.getAllNameStudent();
        Collection<String> expected = listStudents
                .stream()
                .map(Student::getName)
                .toList();

        assertEquals(actual, expected);
    }

    @Test
    void findAllByNameContains() {
        Collection<Student> newStudentList = Arrays.asList(student2, student3);
        Mockito.when(studentServise.findAllByNameContains("o")).thenReturn(newStudentList);

        Collection<Student> expected = studentServise.findAllByNameContains("o");
        Collection<Student> actual = Arrays.asList(student2, student3);

        assertEquals(expected, actual);
    }

    @Test
    void findAllByAgeLessThanId() {
        Collection<Student> newStudentList = Arrays.asList(student2, student3);
        Mockito.when(studentServise.findAllByAgeLessThanId()).thenReturn(newStudentList);

        Collection<Student> expected = studentServise.findAllByAgeLessThanId();
        Collection<Student> actual = Arrays.asList(student2, student3);

        assertEquals(expected, actual);
    }

    @Test
    void findByOrderByAge() {
        Collection<Student> newStudentList = Arrays.asList(student1,student4,student2,student3);
        Mockito.when(studentServise.findByOrderByAge()).thenReturn(newStudentList);

        Collection<Student> expected = studentServise.findByOrderByAge();
        Collection<Student> actual=listStudents
                .stream()
                .sorted(Comparator.comparingInt(Student::getAge))
                .toList();

        assertEquals(expected,actual);
    }


}
