package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Collection<Faculty> findByColor(String faculty);

    Collection<Faculty> findFacultyByNameIgnoreCaseOrColorIgnoreCase(String name, String color);

    void deleteByColor(String color);

}
