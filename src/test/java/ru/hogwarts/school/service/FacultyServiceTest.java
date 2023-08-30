package ru.hogwarts.school.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;

import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.sevice.FacultyService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class FacultyServiceTest {
    @Mock
    FacultyRepository facultyRepository;

    FacultyService facultyService;
    Faculty faculty1 = new Faculty(1L, "1", "red");
    Faculty faculty2 = new Faculty(2L, "2", "green");
    Faculty faculty3 = new Faculty(3L, "3", "white");
    Faculty faculty4 = new Faculty(4L, "4", "red");
    List<Faculty> facultyList = new ArrayList<>();


    @BeforeEach
    void setUp() {

        facultyService = new FacultyService(facultyRepository);
        facultyList.add(faculty1);
        facultyList.add(faculty2);
        facultyList.add(faculty3);
        facultyList.add(faculty4);
    }

    @Test
    void createFaculty() {
        Faculty expected = new Faculty(5L, "5", "grey");
        Mockito.when(facultyService.createFaculty(new Faculty(5L, "5", "grey"))).thenReturn(expected);
        Faculty actual = facultyService.createFaculty(new Faculty(5L, "5", "grey"));
        assertEquals(expected, actual);
    }

    @Test
    void getAllFaculty() {
        Mockito.when(facultyService.getAllFaculty()).thenReturn(facultyList);

        int actual = facultyService.getAllFaculty().size();
        int expected = facultyList.size();
        assertEquals(expected, actual);
    }

    @Test
    void searchColorFaculty() {
        Mockito.when(facultyService.findByColor("red")).thenReturn(facultyList);

        int actual = facultyService.findByColor("red")
                .stream()
                .filter(e -> e.getColor().equals("red"))
                .toList()
                .size();
        int expected = 2;
        assertEquals(expected, actual);
    }

    @Test
    void updateFaculty() {
        Faculty expected = new Faculty(5L, "5", "grey");
        Mockito.when(facultyService.updateFaculty(new Faculty(5L, "5", "grey"))).thenReturn(expected);
        Faculty actual = facultyService.updateFaculty(new Faculty(5L, "5", "grey"));
        assertEquals(expected, actual);
    }


}
