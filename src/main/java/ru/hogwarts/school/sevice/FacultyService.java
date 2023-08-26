package ru.hogwarts.school.sevice;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class FacultyService {

    public Map <Long, Faculty> facultyMap=new HashMap<>();
    private long id = 0;
    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++id);
        facultyMap.put(id, faculty);
        return faculty;
    }

    public Collection<Faculty> getAllFaculty() {
        return facultyMap.values();
    }



    public  Collection <Faculty> searchColorFaculty (String color) {
        return facultyMap.values().stream()
                .filter(e->e.getColor().equals(color))
                .toList();
    }


    public Faculty updateFaculty(Faculty faculty) {
        facultyMap.put(faculty.getId(), faculty);
        return faculty;

    }

    public Faculty removeElement(long id) {
        return facultyMap.remove(id);
    }






}
