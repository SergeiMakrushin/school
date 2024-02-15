package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.sevice.FacultyService;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty create(@RequestBody Faculty faculty) {
        return this.facultyService.createFaculty(faculty);
    }


    @GetMapping
    public Collection<Faculty> getAllFaculty() {
        return this.facultyService.getAllFaculty();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFacultyId(@PathVariable("id") Long id) {

        return ResponseEntity.ok().build();
    }

    @GetMapping("/colorFaculty/{color}")
    public ResponseEntity<Collection<Faculty>> searchColorFaculty(@PathVariable("color") String color) {
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.findByColor(color));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/colorFaculty_two")
    public ResponseEntity<Collection<Faculty>> findNameOrColor(@RequestParam(required = false) String name,
                                                               @RequestParam(required = false) String color) {
        if (name != null && !name.isBlank() && color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(name, color));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {

        Faculty foundFaculty = facultyService.updateFaculty(faculty);
        if (foundFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundFaculty);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable long id) {
        facultyService.removeElement(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{color}")
    public ResponseEntity<Void> deleteFacultyColor(@PathVariable String color) {
        facultyService.removeElementColor(color);
        return ResponseEntity.ok().build();
    }

    @GetMapping ("/long_color")
    public ResponseEntity <String> getLongColorFaculty() {
        return ResponseEntity.ok(facultyService.findLongColor());
    }


}
