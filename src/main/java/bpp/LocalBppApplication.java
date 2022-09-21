package bpp;

import bpp.infrastructure.lv.NesteWebClient;
import bpp.model.PetrolPriceModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class LocalBppApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(LocalBppApplication.class).run(args);
//        ConfigurableApplicationContext context = SpringApplication.run(LocalBppApplication.class, args);
//        NesteWebClient nesteWebClient = context.getBean(NesteWebClient.class);
//        PetrolPriceModel nestePetrolPriceModel = nesteWebClient.getContent();
//
//        CircleWebClient circleWebClient = context.getBean(CircleWebClient.class);
//        PetrolPrice circlePetrolPrice = circleWebClient.getContent();
//
//        GotikaWebClient gotikaWebClient = context.getBean(GotikaWebClient.class);
//        PetrolPrice gotikaPetrolPrice = gotikaWebClient.getContent();
//
//        VirsiWebClient virsiWebClient = context.getBean(VirsiWebClient.class);
//        PetrolPrice virsiPetrolPrice = virsiWebClient.getContent();
//
//        ViadaWebClient viadaWebClient = context.getBean(ViadaWebClient.class);
//        PetrolPrice viadaPetrolPrice = viadaWebClient.getContent();
    }
}
