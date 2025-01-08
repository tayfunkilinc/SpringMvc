package com.tpe.repository;

import com.tpe.domain.Student;

import java.util.List;
import java.util.Optional;

public interface IStudentRepository {

    List<Student> findAll();

    void saveOrUpdate(Student student);

    void delete(Student student);

    Optional<Student> findById(Long id); //NullPointerException null olan degeri kullanmaya calisinca olusan hata
    //NullPointerException almamak icin Optional class'i null yerine bos bir optional objesi doner
    //Dikkat ! Null cikabilir.
}
