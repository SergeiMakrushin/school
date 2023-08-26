package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.sevice.StudentService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class StudentServiceTest {
    private StudentService studentServise;
    Student student1;
    Student student2;

    Student student3;
    Student student4;

    @BeforeEach
    void setUp() {
        student1 = new Student(1L, "Vasy", 20);
        student2 = new Student(2L, "Igor", 21);
        student3 = new Student(3L, "Fedor", 22);
        student4 = new Student(4L, "Andrew", 20);

        studentServise = new StudentService();
        studentServise.createStudent(student1);
        studentServise.createStudent(student2);
        studentServise.createStudent(student3);
        studentServise.createStudent(student4);

    }

    @Test
    void createStudent() {
        Collection<Student> expected = studentServise.getAllStudent();
        Student actual = studentServise.getAllStudent().iterator().next();

        assertNotNull(expected);
        assertInstanceOf(Student.class, actual);
    }

    @Test
    void getAllStudent() {


        int expected = studentServise.getAllStudent().size();
        int actual = 4;
        assertEquals(expected, actual);


        assertEquals(expected, actual);

    }

    @Test
    void searchStudentAge() {
        int actual = studentServise.searchStudentAge(20).size();
        int expected = 2;
        assertEquals(expected, actual);

    }

    @Test
    void updateStudent() {
        Student student5 = new Student(5L, "Ivan", 13);

        int sizeMap = studentServise.getAllStudent().size();
        studentServise.updateStudent(student5);
        int actual = studentServise.getAllStudent().size();
        int expected = sizeMap + 1;
        assertEquals(expected, actual);
    }

    @Test
    void removeElement() {
        int sizeMap = studentServise.getAllStudent().size();
        studentServise.removeElement(2L);
        int actual = studentServise.getAllStudent().size();
        int expected = sizeMap - 1;
        assertEquals(expected, actual);
    }


}
