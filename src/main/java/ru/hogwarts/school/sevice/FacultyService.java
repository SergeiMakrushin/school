package ru.hogwarts.school.sevice;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger= LoggerFactory.getLogger(FacultyService.class);

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for createFaculty");
        return facultyRepository.save(faculty);

    }

    public Collection<Faculty> getAllFaculty() {
        logger.info("Was invoked method for getAllFaculty");
        return facultyRepository.findAll();

    }


    public Collection<Faculty> findByColor(String color) {
        logger.info("Was invoked method for findByColor");
        return facultyRepository.findByColor(color);

    }

    public Collection<Faculty> findFacultiesByNameIgnoreCaseOrColorIgnoreCase(String name, String color) {
        logger.info("Was invoked method for findFacultiesByNameIgnoreCaseOrColorIgnoreCase");
        return facultyRepository.findFacultyByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.info("Was invoked method for updateFaculty");
        return facultyRepository.save(faculty);
    }

    public void removeElement(long id) {
        logger.info("Was invoked method for removeElement");
        facultyRepository.deleteById(id);
    }

    public void removeElementColor(String color) {
        logger.info("Was invoked method for removeElementColor");
        facultyRepository.deleteByColor(color);
    }

    public Faculty findById(Long id) {
        logger.info("Was invoked method for findById");
        return facultyRepository.findById(id).orElseThrow();
    }
}
