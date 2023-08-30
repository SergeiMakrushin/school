package ru.hogwarts.school.sevice;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class FacultyService {
    FacultyRepository facultyRepository;

  public   FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository=facultyRepository;
    }


    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);

    }

    public Collection<Faculty> getAllFaculty() {
        return  facultyRepository.findAll();

    }



    public  Collection <Faculty> findByColor (String color) {
        return facultyRepository.findByColor(color);

    }


    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);


    }

    public void removeElement(long id) {
        facultyRepository.deleteById(id);

    }






}
