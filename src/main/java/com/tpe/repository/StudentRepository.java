package com.tpe.repository;

import com.tpe.domain.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Component --> bunun db iletisimi icin gelismis annatation'i var @Repository kullanacagiz
@Repository //db ile ilgili gelebilecek exceptionlari falan oto olarak yapar
public class StudentRepository implements IStudentRepository{

    @Autowired
    private SessionFactory sessionFactory;

    private Session session;

    //1-b ogrencileri listelemek icin db den tum kayitlari getirelim
    @Override
    public List<Student> findAll() {
        session = sessionFactory.openSession();

        List<Student > studentList =session.createQuery("FROM Student",Student.class).getResultList();

        session.close();

        return studentList;
    }

    //2-d
    @Override
    public void saveOrUpdate(Student student) {
        session =sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.saveOrUpdate(student); //burda ogrenci varsa gunceller yoksa yeni kayit olarak ekler

        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Student student) {

    }

    //3-b
    @Override
    public Optional<Student> findById(Long id) {
        session = sessionFactory.openSession();

        Student student = session.get(Student.class, id); //parametrede verilen ogrenciyi getiriyorum
        Optional<Student> optional = Optional.ofNullable(student);  //Attention!! burada null deger gelebilir diyoruz burda bos deger yerine bos student donderdik
        //Optional ile null donme ihtimalini goz onune almak icin kullandik sadece bir uyari
        //bu Optional bir katman gibi korumaya aliyoruz null gelme durumuna karsi
        session.close();
        return optional;
    }
}
