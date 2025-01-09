package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@Controller
@RestController //bu classtan restful serviceler gelistirilecek
@RequestMapping("/api")
public class StudentRestController {


    private final IStudentService service;
    @Autowired // sadece bir tane const var ise Autowired annatotion kullanimi opsiyoneldir
    public StudentRestController(IStudentService service) {
        this.service = service;
    }

    //1-tum ogrencileri listeleme
    //http://localhost:8080/SpringMvc/api/all + GET
    @GetMapping("/all")
    public List<Student> allStudent(){
        return service.listAllStudents();
        //burdaki verilerimizi JSON formatina dondermemiz gerek
        // bu donusumu jackson kutuphanesi otomatik olarak gerceklestirir - tam tersi donusumude ayni kutuphane yapar

    }

}
