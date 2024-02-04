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
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTestRestTemplate {
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;
    @Autowired
    FacultyRepository facultyRepository;
    @Autowired
    private TestRestTemplate restTemplate;


    Faculty faculty1 = new Faculty(20L, "7", "pink");
    Faculty faculty2 = new Faculty(30L, "8", "yellow");
    Faculty faculty3 = new Faculty(40L, "9", "grey");


    @Test
    void contextLoad() throws Exception {

        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    void createTest() throws Exception {
        Faculty faculty = new Faculty();
        int sizeTableBefore = facultyController.getAllFaculty().size();

        faculty = restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty1, Faculty.class);
        Assertions.assertThat(faculty.getColor()).isEqualTo(faculty1.getColor());

        Collection<Faculty> facultyArrayList = new ArrayList<>();
        facultyArrayList = facultyRepository.findFacultyByNameIgnoreCaseOrColorIgnoreCase(faculty.getName(), faculty.getColor());

        facultyRepository.deleteById(facultyArrayList
                .stream()
                .findFirst()
                .get()
                .getId());
        int sizeTableAfter = facultyController.getAllFaculty().size();
        Assertions.assertThat(sizeTableBefore).isEqualTo(sizeTableAfter);
    }

    @Test
    void updateFacultyTest() throws Exception {
        Faculty faculty = new Faculty();
        int sizeTableBefore = facultyController.getAllFaculty().size();

        faculty = restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty2, Faculty.class);
        Assertions.assertThat(faculty.getColor()).isEqualTo(faculty2.getColor());

        Collection<Faculty> facultyArrayList = new ArrayList<>();
        facultyArrayList = facultyRepository.findFacultyByNameIgnoreCaseOrColorIgnoreCase(faculty.getName(), faculty.getColor());

        facultyRepository.deleteById(facultyArrayList
                .stream()
                .findFirst()
                .get()
                .getId());

        int sizeTableAfter = facultyController.getAllFaculty().size();
        Assertions.assertThat(sizeTableBefore).isEqualTo(sizeTableAfter);
    }

    @Test
    void searchColorFacultyTest() throws Exception {
        String color = faculty3.getColor();
        facultyRepository.save(faculty3);
        int sizeTableBefore = facultyController.getAllFaculty().size();

        ResponseEntity<List<Faculty>> faculties = restTemplate.exchange("http://localhost:" + port + "/faculty/colorFaculty/{color}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Faculty>>() {
                },
                color);

        Assertions.assertThat(faculties
                .getBody()
                .stream()
                .findFirst()
                .get()
                .getColor()).isEqualTo(faculty3.getColor());

        Collection<Faculty> facultyArrayList = new ArrayList<>();
        facultyArrayList = facultyRepository.findFacultyByNameIgnoreCaseOrColorIgnoreCase(faculty3.getName(), faculty3.getColor());
        facultyRepository.deleteById(facultyArrayList
                .stream()
                .findFirst()
                .get()
                .getId());

        int sizeTableAfter = facultyController.getAllFaculty().size();
        Assertions.assertThat(sizeTableBefore).isEqualTo(sizeTableAfter);
    }

    @Test
    void deleteFacultyTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(50L);
        faculty.setName("10");
        faculty.setColor("brown");

        Faculty facultyRetern = facultyController.create(faculty);
        Long id = facultyRetern.getId();

        Assertions.assertThat(this.restTemplate.exchange("http://localhost:" + port + "/faculty/{id}", HttpMethod.DELETE, HttpEntity.EMPTY, String.class, id))
                .isNotNull();

    }

    @Test
    void getAllFacultyTest() throws Exception {

        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty", String.class))
                .isNotEmpty();
    }


    @Test
    void findNameOrColorTest() throws Exception {
        int sizeTableBefore = facultyController.getAllFaculty().size();
        Faculty faculty = new Faculty();
        faculty.setId(60L);
        faculty.setColor("ColorTest");
        faculty.setName("NameOrColor");

        Faculty facultyRetern = facultyController.create(faculty);

        Assertions.assertThat(this.restTemplate.getForObject(
                        "http://localhost:" + port + "/faculty/colorFaculty_two?color=ColorTest&name=NameOrColor",
                        String.class))
                .contains(faculty.getName());

        facultyRepository.deleteById(facultyRetern.getId());

        int sizeTableAfter = facultyController.getAllFaculty().size();
        Assertions.assertThat(sizeTableBefore).isEqualTo(sizeTableAfter);
    }


}
