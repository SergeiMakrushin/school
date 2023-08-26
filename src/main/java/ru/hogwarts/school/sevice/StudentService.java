package ru.hogwarts.school.sevice;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class StudentService {

    public Map<Long, Student> studentMap = new HashMap<>();
    private long id = 0;

    public Student createStudent(Student student) {
        student.setId(++id);
        studentMap.put(id, student);
        return student;
    }

    public Collection<Student> getAllStudent() {
        return studentMap.values();
    }

    public Collection<Student> searchStudentAge (int age) {
       return studentMap.values().stream()
                .filter(e->e.getAge()==age)
                .toList();
    }

    public Student updateStudent(Student student) {
        studentMap.put(student.getId(), student);
        return student;

    }

    public Student removeElement(long id) {
        return studentMap.remove(id);
    }

}
