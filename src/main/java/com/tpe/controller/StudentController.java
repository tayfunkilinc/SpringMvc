package com.tpe.controller;
//kullanicidan isteklerin alindigi kisim ve bu isteklere gore cevaplari hazirlayacagiz ve gonderecegiz

import com.tpe.domain.Student;
import com.tpe.exception.StudentNotFoundException;
import com.tpe.service.IStudentService;
import net.bytebuddy.matcher.StringMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller //bu class objesi spring tarafindan yonetilecek - //requestler bu classta karşılanacak ve ilgili metodlarla maplenecek
@RequestMapping("/students") ////http:localhost:8080/SpringMvc/students/.... requestin eslestirilmesini sagliyor
//class:tüm metodlar için geçerli olur
//method:sadece ilgili metodu requestle mapler
public class StudentController {
    @Autowired
    private IStudentService service;

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
    public ModelAndView getStudents(){ //model tarafinda verilen sayfayi bulup sayfa icine bunu ekleyecek yani dinamik icerisine bir sey yerlestireceksem ModelAndView kullanilir

        List<Student> allStudent= service.listAllStudents();
        ModelAndView mav=new ModelAndView();
        mav.addObject("studentList",allStudent); //studentList bunun icine tum verileri yerlestirdik bunlarida altta gerekli sayfaya yerlestiriyoruz
        mav.setViewName("students"); //students sayfasina gidiyor students.jsp sayfasina

        return mav;
    }

    //2-a http://localhost:8080/SpringMvc/students/new + GET
    //response: request geldiginde form gostermek istiyoruz kayit etmek icin formu gosterecegiz bu sebeple GET methodu ile karsilamaliyiz
    @GetMapping("/new") //bir path ilerledik bu sebeple bu raya /new ekledik
    public String sendForm(@ModelAttribute("student") Student student){ //burda bir veri cekmeyecegim icin direk sayfa ismini String olarak gondermem yeterli
        //@ModelAttribute("student") Student student // view katmanindan bir datayi alip db ye kaydetmemize yarayacak - yani katmanlar arasi data tasimaya yarar
        //ModelAttribute anatasyonu view katmanı ile controller arasında
        //modelın transferini sağlar.
        //işlem sonunda: studentın firstname,lastname ve grade değerleri set edilmiş halde
        //controller classında yer alır

        return "studentForm";
    }

    //2-b formun icindeki ogrenciyi kaydetme
    //request: http://localhost:8080/SpringMvc/students/saveStudent + POST --> burdan veri kaydedecegiz
    //response: ogrenciyi tabloya ekleyecegiz ve ogrenci listesini gonderecegiz
    @PostMapping("saveStudent")
    public String addStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult){ // burda student'i alip asagida kaydetmeyi yaptik
            //@Valid annatation ile dogrulama yapip BindingResult bindingResult ile olusabilecek hatalari burdan aliyorum
        if (bindingResult.hasErrors()){ // eger hata var ise
            return "studentForm"; //burda yeni satfaya gecirmeyi tekrardan studentForm sayfasini gostermesini sagliyoruz
        }
        service.addOrUpdateStudent(student);

        return "redirect:/students"; //redirect gelen bir istegi baska bir istege yonlendiriri yani http://localhost:8080/SpringMvc/students istegine yonlendirir
    }

    //3-a ogrenciyi guncelleme
    ///request:http://localhost:8080/SpringMvc/students/update?id=3 + GET
    // burada ? query olarak nitelendirilebilir
    //1 - form/body kismindan veri alabilirim - birden fazla bilgi almak icin kullanilir
    //2 - query pamaetresi ile alabilirim - (&, ?) cok fazla degil ama birden fazla bilgi almak icin kullanilir
    //3 - path parametresi ile - bir kac bilgi almak icin kullanilir
    ///response:update icin id si verilen ogrencinin bilgileri ile formu gosterme

    //id'si verilen ogrenciyi bulmaliyiz ki.. - sonradan bunu guncelleyebileyim
    //http://localhost:8080/SpringMvc/students/update?id=1&name=jack
    @GetMapping("/update") //? ine kadar yazdim
    private ModelAndView sendFormUpdate(@RequestParam("id") Long identity){ //? sonrasini okumak icin RequestParam ile request icindeki gelen id degerini aliyorim
        //RequestParam istekte gelen id okunur identity icine kaydedilir
        //update?id=1&name=jack bu sekilde istek gelirse ikisini ayri ayri almamiz lazim
        //@RequestParam("id","name") Long identity
        Student foundStudent = service.findStudentById(identity);
        ModelAndView mav = new ModelAndView();
        mav.addObject("student", foundStudent);
        mav.setViewName("studentForm");
        return mav;
    }


    //kullanicidan bilgi nasil alinir
    //1- form/body(JSON)
    //2- query param : /query?id=3
    //3- path param : /3
    //query param ve path param sadece 1 tane ise isim belirtmek opsiyonel


    //4-Bir Ogrenciyi Silme
    //request : http://localhost:8080/SpringMvc/students/delete/4
    //response : ogrenci silinir ve kalan ogrenciler gosterilir
    @GetMapping("/delete/{id}") //burda olusturdugumuz id degiskenine PathVariable ile aldigimiz identity degiskeninin degerini atamis olduk
    public String deleteStudent(@PathVariable("id") Long identity){
        service.deleteStudent(identity);
        return "redirect:/students";
    }

    //@ExceptionHandler: try-catch blogunun mantigi ile benzer calisir, exception yakalar ve biz bunu handller ederiz
    @ExceptionHandler(StudentNotFoundException.class)
    public ModelAndView handleException(Exception exception){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", exception.getMessage());
        modelAndView.setViewName("notFound");
        return  modelAndView;
    }





}
