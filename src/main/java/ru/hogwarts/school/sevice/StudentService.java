package ru.hogwarts.school.sevice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;


@Service
public class StudentService {


    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public Student createStudent(Student student) {
        logger.info("Was invoked method for createStudent");
        return studentRepository.save(student);
    }

    public Collection<Student> getAllStudent() {
        logger.info("Was invoked method for getAllStudent");
        return studentRepository.findAll();
    }

    public Collection<Student> findByAge(int age) {
        logger.info("Was invoked method for findByAge");
        return studentRepository.findByAge(age);
    }

    //1
    public Collection<Student> findByAgeBetween(int min, int max) {
        logger.info("Was invoked method for findByAgeBetween");
        return studentRepository.findByAgeBetween(min, max);
    }

    //    2
    public Collection<String> getAllNameStudent() {
        logger.info("Was invoked method for getAllNameStudent");
        return studentRepository.findAllNameStudent();
    }

    //    3
    public Collection<Student> findAllByNameContains(String name) {
        logger.info("Was invoked method for findAllByNameContains");
        return studentRepository.findAllByNameIgnoreCaseContains(name);
    }

    // 4
    public Collection<Student> findAllByAgeLessThanId() {
        logger.info("Was invoked method for findAllByAgeLessThanId");
        return studentRepository.findAllByAgeLessId();
    }

    //5
    public Collection<Student> findByOrderByAge() {
        logger.info("Was invoked method for findByOrderByAge");
        return studentRepository.findByOrderByAge();
    }

    public Collection<Long> findStudentFaculty(String name) {
        logger.info("Was invoked method for findStudentFaculty");
        return studentRepository.findStudentFaculty(name);
    }

    public Collection<Student> findFacultyStudents(Integer id) {
        logger.info("Was invoked method for findFacultyStudents");
        return studentRepository.findStudentsByFaculty_Id(id);
    }

    public Student updateStudent(Student student) {
        logger.info("Was invoked method for updateStudent");
        return studentRepository.save(student);
    }

    public void removeElement(long id) {
        logger.info("Was invoked method for removeElement");
        studentRepository.deleteById(id);
    }

    public Student findStudent(long id) {
        logger.info("Was invoked method for findStudent");
        return studentRepository.findStudentById(id);
    }

    public Integer getCountStudent() {
        logger.info("Was invoked method for getCountStudent");
        return studentRepository.findCountStudent();
    }

    public Integer getAverageAgeStudent() {
        logger.info("Was invoked method for getAverageAgeStudent");
        return studentRepository.findAverageAge();
    }

    public Collection<Student> getStudentOrderByIdDescLimitFive() {
        logger.info("Was invoked method for getStudentOrderByIdDescLimitFive");
        return studentRepository.findStudentOrderByIdDescLimitFive();
    }

    public List<Student> getStudentLimit(Integer pageNamber, Integer pageSize) {
        logger.info("Was invoked method for getStudentLimit");
        PageRequest pageRequest = PageRequest.of(pageNamber - 1, pageSize);
        return studentRepository.findAll(pageRequest).getContent();

    }

}
