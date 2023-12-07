package ru.hogwarts.school.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import ru.hogwarts.school.model.Student;

import java.util.Collection;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAge(int student);

    //1
    Collection<Student> findByAgeBetween(int min, int max);

    //2
    @Query("SELECT name FROM Student")
    Collection<String> findAllNameStudent();

    //3
    Collection<Student> findAllByNameIgnoreCaseContains(String name);

    //4
    @Query("SELECT stud FROM Student stud WHERE  stud.age<id")
    Collection<Student> findAllByAgeLessId();


    //5
    Collection<Student> findByOrderByAge();


    @Query(value = "SELECT distinct student.faculty_id FROM student, faculty WHERE student.faculty_id=faculty.id and  student.name ILIKE ?1 ", nativeQuery = true)
    Collection<Long> findStudentFaculty(String name);


    Collection<Student> findStudentsByFaculty_Id(int id);

Student findStudentById(long id);

}
