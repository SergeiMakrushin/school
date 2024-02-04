package ru.hogwarts.school.sevice;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;

@Transactional
//@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);

    }

    public Collection<Faculty> getAllFaculty() {
        return facultyRepository.findAll();

    }


    public Collection<Faculty> findByColor(String color) {
        return facultyRepository.findByColor(color);

    }

    public Collection<Faculty> findFacultiesByNameIgnoreCaseOrColorIgnoreCase(String name, String color) {
        return facultyRepository.findFacultyByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void removeElement(long id) {
        facultyRepository.deleteById(id);
    }

    public void removeElementColor(String color) {
        facultyRepository.deleteByColor(color);
    }

    public Faculty findById(Long id) {
        return facultyRepository.findById(id).orElseThrow();
    }
}
