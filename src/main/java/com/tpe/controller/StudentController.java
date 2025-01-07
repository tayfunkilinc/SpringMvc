package com.tpe.controller;
//kullanicidan isteklerin alindigi kisim ve bu isteklere gore cevaplari hazirlayacagiz ve gonderecegiz

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller //bu class objesi spring tarafindan yonetilecek - //requestler bu classta karşılanacak ve ilgili metodlarla maplenecek
@RequestMapping("/students") ////http:localhost:8080/SpringMvc/students/.... requestin eslestirilmesini sagliyor
//class:tüm metodlar için geçerli olur
//method:sadece ilgili metodu requestle mapler
public class StudentController {

    //NOT:controllerda metodlar geriye mav veya String data tipi döndürebilir.

    //http:localhost:8080/SpringMvc/students/hi + GET --okuma islemleri
    //http:localhost:8080/SpringMvc/students/hi + PUT --kayit islemler
    //@RequestMapping("/students") --class seviyesinde kullandigim icin methodda kullanmama gerek yok - class seviyesinde olmasaydi methodlarda ayri ayri kullanacaktim
    @GetMapping("/hi")
    public ModelAndView sayHi(){
        //response'u hazirlayacak
        ModelAndView mav = new ModelAndView();
        mav.addObject("message","Hi, "); //message degiskene degerini atadim
        mav.addObject("messagebody","I'm a Student Management System");//messagebody degiskene degerini atadim
        mav.setViewName("hi"); //goruntunun adini verdim
        return mav;
    }
    //view resolver : /WEB-INF/views/hi.jsp dosyasını bulur ve mav içindeki modelı
    //dosyaya bind eder ve clienta gönderir.

    //1-Tum ogrencileri Listeleme
    //http://localhost:8080/SpringMvc/students/ + GET
    @GetMapping // burada extra path vermeme gerek yok devami varsa verilir
    public ModelAndView getStudents(){
        ModelAndView mav = new ModelAndView();

        //todo: db'den liste gelecek

        mav.setViewName("students");

        return mav;
    }


}
