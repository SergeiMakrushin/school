package ru.hogwarts.school.sevice;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.Optional;


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

    //1
    public Collection<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    //    2
    public Collection<String> getAllNameStudent() {
        return studentRepository.findAllNameStudent();
    }

    //    3
    public Collection<Student> findAllByNameContains(String name) {
        return studentRepository.findAllByNameIgnoreCaseContains(name);
    }

    // 4
    public Collection<Student> findAllByAgeLessThanId() {
        return studentRepository.findAllByAgeLessId();
    }

    //5
    public Collection<Student> findByOrderByAge() {
        return studentRepository.findByOrderByAge();
    }

    public Collection<Long> findStudentFaculty(String name) {
        return studentRepository.findStudentFaculty(name);
    }

    public Collection<Student> findFacultyStudents(Integer id) {
        return studentRepository.findStudentsByFaculty_Id(id);
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public void removeElement(long id) {
        studentRepository.deleteById(id);
    }

    public Student findStudent (long id) {
       return studentRepository.findStudentById(id);
    }

}
