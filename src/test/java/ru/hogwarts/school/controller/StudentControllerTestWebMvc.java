package ru.hogwarts.school.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.sevice.AvatarService;
import ru.hogwarts.school.sevice.FacultyService;
import ru.hogwarts.school.sevice.StudentService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class StudentControllerTestWebMvc {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    StudentRepository studentRepository;
    @MockBean
    FacultyRepository facultyRepository;
    @MockBean
    AvatarRepository avatarRepository;

    @SpyBean
    StudentService studentService;
    @SpyBean
    FacultyService facultyService;
    @SpyBean
    AvatarService avatarService;

    @InjectMocks
    StudentController studentController;


    @Test
    void createTest() throws Exception {
        Long id = 1L;
        String name = "Filip";
        Integer age = 25;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findStudentById(any(Long.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/student_id")
                        .param("id", String.valueOf(id))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void getAllStudentTest() throws Exception {
        Student student1 = new Student(1L, "Filip", 25);
        Student student2 = new Student(2L, "Zoy", 30);

        List<Student> studentList = new ArrayList<>(Arrays.asList(student1, student2));

        when(studentRepository.findAll()).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(student1.getName()))
                .andExpect(jsonPath("$[0].age").value(student1.getAge()));

    }

    @Test
    void searchStudentAgeTest() throws Exception {
        Student student1 = new Student(1L, "Filip", 25);
        Student student2 = new Student(2L, "Zoy", 30);

        List<Student> studentList = new ArrayList<>(Arrays.asList(student1, student2));

        when(studentRepository.findByAge(any(Integer.class))).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age/{intAge}", student1.getAge())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].name").value(student2.getName()))
                .andExpect(jsonPath("$[1].age").value(student2.getAge()));

    }

    @Test
    void findByAgeBetweenTest() throws Exception {
        Student student1 = new Student(1L, "Filip", 25);
        Student student2 = new Student(2L, "Zoy", 30);

        List<Student> studentList = new ArrayList<>(Arrays.asList(student1, student2));

        int min = 20;
        int max = 30;

        when(studentRepository.findByAgeBetween(any(Integer.class), any(Integer.class))).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age")
                        .param("min", String.valueOf(min))
                        .param("max", String.valueOf(max))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(student1.getName()));
    }

    @Test
    void getAllNameStudentsTest() throws Exception {
        Student student1 = new Student(1L, "Filip", 25);
        Student student2 = new Student(2L, "Zoy", 30);

        List<String> studentNameList = new ArrayList<>(Arrays.asList(student1.getName(), student2.getName()));

        when(studentRepository.findAllNameStudent()).thenReturn(studentNameList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/name")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(student1.getName()));

    }

    @Test
    void findAllByNameContainsTest() throws Exception {
        Student student1 = new Student(1L, "Filip", 25);
        List<Student> studentList = new ArrayList<>(Arrays.asList(student1));
        String partName = "lip";
        when(studentRepository.findAllByNameIgnoreCaseContains(any(String.class))).thenReturn(studentList);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/part_name")
                        .param("name", partName)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(student1.getName()))
                .andExpect(jsonPath("$[0].age").value(student1.getAge()));
    }

    @Test
    void findAllByAgeLessThanIdTest() throws Exception {
        Student student1 = new Student(26L, "Filip", 25);
        List<Student> studentList = new ArrayList<>(Arrays.asList(student1));

        when(studentRepository.findAllByAgeLessId()).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/grouping_age_less_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(student1.getName()))
                .andExpect(jsonPath("$[0].age").value(student1.getAge()));
    }

    @Test
    void findByOrderByAgeTest() throws Exception {
        Student student1 = new Student(1L, "Filip", 25);
        Student student2 = new Student(2L, "Zoy", 30);

        List<Student> studentList = new ArrayList<>(Arrays.asList(student1, student2));

        when(studentRepository.findByOrderByAge()).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/grouping_age")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(student1.getName()))
                .andExpect(jsonPath("$[1].name").value(student2.getName()));

    }


    @Test
    void findStudentFacultyTest() throws Exception {
        Long id = 1L;
        String name = "Filip";
        Integer age = 25;

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        Long facultyId = 3L;
        List<Long> facultyIds = new ArrayList<>(Arrays.asList(facultyId));

        when(studentRepository.findStudentFaculty(any(String.class))).thenReturn(facultyIds);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/student_faculty_id")
                        .param("name", name)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(facultyIds)));
    }

    @Test
    void findFacultyStudentsTest() throws Exception {
        Student student1 = new Student(1L, "Filip", 25);
        Student student2 = new Student(2L, "Zoy", 30);
        List<Student> studentList = new ArrayList<>(Arrays.asList(student1, student2));

        Long id = 2L;

        when(studentRepository.findStudentsByFaculty_Id(any(Integer.class))).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/faculty_students")
                        .param("id", String.valueOf(id))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(student1.getName()))
                .andExpect(jsonPath("$[1].name").value(student2.getName()));

    }

    @Test
    void updateStudentTest() throws Exception {
        Long id = 1L;
        String name = "Filip";
        Integer age = 25;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findStudentById(any(Long.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/student_id")
                        .param("id", String.valueOf(id))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.name").value(name));

    }

    @Test
    void deleteStudentTest() throws Exception {
        Long id = 1L;
        String name = "Filip";
        Integer age = 25;

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        mockMvc.perform(
                        delete("/student/{id}", student.getId()))
                .andExpect(status().isOk());
    }


}
