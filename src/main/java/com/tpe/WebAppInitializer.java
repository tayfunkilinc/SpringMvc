package com.tpe;
//Java tabanlı Web uygulamaları web.xml dosyası ile config edilir
//bu classı web.xml yerine kullanacağız.

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//AbstractAnnotationConfig... :DispatcherServlet konfigurasyonu ve başlatılması için gerekli adımları gösterir
//Root deyince aklina direk data gelsin
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() { //dataya erisim icin gerekli configurasyon ayarlari : Hibernate, JDBC ayarlari
        return new Class[]{
                RootConfig.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {  //servlet icin konfigurasyonlar : viewresolver, handlermapping
        return new Class[]{
                WebMvcConfig.class
        };
    }

    @Override
    protected String[] getServletMappings() { //hangi url ile gelen istekler servlet tarafından karşılanacak ayarlaması
        return new String[]{
                "/" //burda url'den sonra gelen tum istekleri karsila demis olduk - burda kisitlamayada gidebilirsin orn: "/students" gibi.
        };
    }
}
