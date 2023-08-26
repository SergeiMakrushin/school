package ru.hogwarts.school.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Faculty;

import ru.hogwarts.school.sevice.FacultyService;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class FacultyServiceTest {

    FacultyService facultyService;
    Faculty faculty1;
    Faculty faculty2;
    Faculty faculty3;
    Faculty faculty4;

    @BeforeEach
    void setUp() {
        faculty1 = new Faculty(1L, "1", "red");
        faculty2 = new Faculty(2L, "2", "gren");
        faculty3 = new Faculty(3L, "3", "white");
        faculty4 = new Faculty(4L, "4", "red");
        facultyService = new FacultyService();
        facultyService.createFaculty(faculty1);
        facultyService.createFaculty(faculty2);
        facultyService.createFaculty(faculty3);
        facultyService.createFaculty(faculty4);
    }

    @Test
    void createFaculty() {
        Collection<Faculty> expected = facultyService.getAllFaculty();
        Faculty actual = facultyService.getAllFaculty().iterator().next();

        assertNotNull(expected);
        assertInstanceOf(Faculty.class, actual);
    }

    @Test
    void getAllFaculty() {
        int expected = facultyService.getAllFaculty().size();
        int actual = 4;
        assertEquals(expected, actual);
    }

    @Test
    void searchColorFaculty() {
        int actual = facultyService.searchColorFaculty("red").size();
        int expected = 2;
        assertEquals(expected, actual);
    }

    @Test
    void updateFaculty() {
        Faculty faculty5 = new Faculty(5L, "5", "orange");

        int sizeMap = facultyService.getAllFaculty().size();
        facultyService.updateFaculty(faculty5);
        int actual = facultyService.getAllFaculty().size();
        int expected = sizeMap + 1;
        assertEquals(expected, actual);
    }

    @Test
    void removeElement() {


        int sizeMap = facultyService.getAllFaculty().size();
        facultyService.removeElement(2);
        int actual = facultyService.getAllFaculty().size();
        int expected = sizeMap - 1;
        assertEquals(expected, actual);
    }


}
