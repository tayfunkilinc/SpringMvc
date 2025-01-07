package com.tpe;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@ComponentScan("com.tpe")
@EnableWebMvc //MVC'yi etkinlestirir
public class WebMvcConfig implements WebMvcConfigurer {

    //view resolver -- response ve model ilsemlerini yapar gelen cevap ile modeli birlestiriyor
    //view resolver sadece sayfanin ismi gelecek : students
    @Bean
    public InternalResourceViewResolver resolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();

        resolver.setPrefix("/WEB-INF/views/"); // on ek: view dosyasi nerede yolunu verip cevaplari buraya yerlestirecek : /WEB-INF/views/students
        resolver.setSuffix(".jsp"); // son ek : dosyanin uzantisi nedir : /WEB-INF/views/students.jsp
        resolver.setViewClass(JstlView.class); //JSTL:JavaStandartTagLibrary:JSP içinde daha az Java kodu yazmamızı sağlar

        return resolver;
    }

    //handler mapping : statik sayfa içeren isteklerin servleta gönderilmesine gerek yok -- burda gondermesin diye bu ayarlari yapalim
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/statics/**") //bu url ile gelen istekleri statik olarak sun
                .addResourceLocations("/resources/") //static olan kaynaklarin locasyonunu gosteriyoruz
                .setCachePeriod(0);
    }
}
