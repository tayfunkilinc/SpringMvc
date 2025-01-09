package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.exception.StudentNotFoundException;
import com.tpe.repository.IStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //burdada Service annatation katmanini kullanarak burada islerin yapilacagini belirtiyoruz
public class StudentService implements IStudentService{

    @Autowired
    private IStudentRepository repository;

    //1-c
    @Override
    public List<Student> listAllStudents() {

        return repository.findAll();
    }

    //2-c
    @Override
    public void addOrUpdateStudent(Student student) {
        repository.saveOrUpdate(student);
    }

    //3-c
    @Override
    public Student findStudentById(Long id) {

        /*`orElseThrow` metodu, `Optional` sınıfından bir değeri almak için kullanılır. Eğer `Optional` boşsa, belirtilen istisnayı fırlatır.
        Bu durumda, `StudentNotFountException` istisnası fırlatılır ve öğrenci bulunamadığında özel bir hata mesajı gösterilir.
        ```java
        orElseThrow(() -> new StudentNotFountException("Student not found by id :" + id));

        - `orElseThrow`: `Optional` nesnesi boşsa, belirtilen istisnayı fırlatır.
        - `() -> new StudentNotFountException("Student not found by id :" + id)`:
         Lambda ifadesi, özel bir hata mesajı ile `StudentNotFountException` oluşturur.
          Mesajda, bulunamayan öğrenci kimliği belirtilir.

        Özetle, `repository.findById(id)` tarafından döndürülen `Optional<Student>` boşsa,
         bu satır `StudentNotFountException` fırlatır ve öğrenci kimliğini içeren bir mesaj gösterir.*/

        Student student = repository.findById(id).
                orElseThrow(()->new StudentNotFoundException("Student not found by id : " + id)); //null gelme durumune orElseThrow() hatasini firlat diyorum
        //supplier interfaceini implemente eden bir class olusturup,
        // objesinin get methodunu kullanmak yerine kisaca lambda exo ile get metodunu override ediyoruz.


        //findById metodunun geriye döndürdüğü optional içinde
        //student varsa student değişkenine atar.
        //optional objesi boşsa orElseThrow custom exception fırlatılabilir.
        //get():NoSuchElementException() varsayilan bunu kullanabilirsin ozel mesaj icin orElseThrow kullanilir

        return student;
    }

    //4-b
    @Override
    public void deleteStudent(Long id) {
            //idsi verilen ogrenciyi bulalim
            Student student = findStudentById(id); //silemk istedigimiz ogrenci bulunamassa dogrudan exception firlatir
            repository.delete(student);
    }
}
