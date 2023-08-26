package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;

import ru.hogwarts.school.sevice.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping ("/faculty")
public class FacultyController {
    FacultyService facultyService;
    FacultyController (FacultyService facultyService) {
        this.facultyService=facultyService;
    }

    @PostMapping
    public Faculty create (@RequestBody Faculty faculty) {
        return this.facultyService.createFaculty(faculty);
    }


    @GetMapping
    public Collection<Faculty> getAllFaculty() {
        return this.facultyService.getAllFaculty();
    }

    @GetMapping("/colorFaculty/{color}")
    public Collection<Faculty> searchColorFaculty(@PathVariable ("color") String color) {
        return this.facultyService.searchColorFaculty(color);
    }
    @PutMapping
    public Faculty updateStudent (@RequestBody Faculty faculty) {
        return  this.facultyService.updateFaculty(faculty);
    }
    @DeleteMapping ("{id}")
    public Faculty deleteStudent (@PathVariable  long id) {
        return this.facultyService.removeElement(id);
    }

}
