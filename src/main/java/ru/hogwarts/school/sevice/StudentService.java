package ru.hogwarts.school.sevice;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;


@Service
public class StudentService {

    StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student createStudent(Student student) {
        return studentRepository.save(student);

    }

    public Collection<Student> getAllStudent() {
        return studentRepository.findAll();

    }

    public Collection<Student> findByAge(int age) {
        return studentRepository.findByAge(age);

    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);


    }

    public void removeElement(long id) {
        studentRepository.deleteById(id);

    }

}
