package bpp.infrastructure.lt;

import bpp.model.CirclePetrolPriceModel;
import bpp.model.ErrorModel;
import bpp.model.Response;
import bpp.model.WebPageResponseModel;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class LtCircleContentWebClientTest {
    @Spy
    private LtCircleContentWebClient ltCircleContentWebClient = new LtCircleContentWebClient();

    @BeforeEach
    void init() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method postConstructMethod = LtCircleContentWebClient.class.getDeclaredMethod("before");
        postConstructMethod.setAccessible(true);
        postConstructMethod.invoke(ltCircleContentWebClient);
    }

    @Test
    void notGetContentPage_ReturnError() {
        //Arrange, Act
        doReturn(WebPageResponseModel
                .builder()
                .id(404)
                .content("Error")
                .build())
                .when(ltCircleContentWebClient)
                .getWebContent(null);
        Response<?> response = ltCircleContentWebClient.getContent();
        ErrorModel errorModel = (ErrorModel) response.responseModel();
        // Assert
        assertThat(response.responseModel()).isInstanceOf(ErrorModel.class);
        assertThat(errorModel.getId()).isEqualTo(404);
    }

    @Test
    void mustReturnPetrolPrices() {
        // Arrange, Act
        doReturn(WebPageResponseModel
                .builder()
                .id(200)
                .content(returnContent())
                .build())
                .when(ltCircleContentWebClient)
                .getWebContent(null);
        Response<?> response = ltCircleContentWebClient.getContent();
        CirclePetrolPriceModel circlePetrolPriceModel = (CirclePetrolPriceModel) response.responseModel();
        // Assert
        assertThat(circlePetrolPriceModel.getId()).isEqualTo(200);
        assertThat(circlePetrolPriceModel.getPetrol()).isEqualTo(new BigDecimal("1.445"));
        assertThat(circlePetrolPriceModel.getPetrolPro()).isEqualTo(new BigDecimal("1.564"));
        assertThat(circlePetrolPriceModel.getDiesel()).isEqualTo(new BigDecimal("1.59"));
        assertThat(circlePetrolPriceModel.getDieselPro()).isEqualTo(new BigDecimal("1.679"));
        assertThat(circlePetrolPriceModel.getGas()).isEqualTo(new BigDecimal("0.603"));
    }

    @Test
    void mustReturnBestPricesAddresses() {
        // Arrange, Act
        doReturn(WebPageResponseModel
                .builder()
                .id(200)
                .content(returnContent())
                .build())
                .when(ltCircleContentWebClient)
                .getWebContent(null);
        Response<?> response = ltCircleContentWebClient.getContent();
        CirclePetrolPriceModel circlePetrolPriceModel = (CirclePetrolPriceModel) response.responseModel();
        // Assert
        assertThat(circlePetrolPriceModel.getId()).isEqualTo(200);
        assertThat(circlePetrolPriceModel.getPetrolBestPriceAddress()).isEqualTo("Circle K Vilkaviškis Vilkaviškio g. 10, Serdokų k. Vilkaviškis");
        assertThat(circlePetrolPriceModel.getPetrolProBestPriceAddress()).isEqualTo("Circle K Smiltelė Taikos pr. 112A, Klaipėda");
        assertThat(circlePetrolPriceModel.getDieselBestPriceAddress()).isEqualTo("Circle K Baltupiai Baltupio g. 10, Vilnius");
        assertThat(circlePetrolPriceModel.getDieselProBestPriceAddress()).isEqualTo("Circle K Baltupiai Baltupio g. 10, Vilnius");
        assertThat(circlePetrolPriceModel.getGasBestPriceAddress()).isEqualTo("Circle K Šilutė Cintjoniškių g. 15, Šilutė");
    }

    private String returnContent() {
        return """
                Degalų kainos | Circle K
                Skip to main content
                ns
                Privatiems
                Verslui
                Top menu
                APIE MUS
                DIRBK PAS MUS
                KLIENTŲ APTARNAVIMAS
                Prisijungti
                EXTRA
                Verslo savitarnos svetainė
                Circle K ID
                Main navigation
                EXTRA PRIVATIEMS
                Circle K EXTRA
                Kortelės
                Taisyklės
                MŪSŲ PRODUKTAI
                Maistas ir gėrimai
                Kava
                K-FREEZE gaivusis gėrimas
                Akcijos ir pasiūlymai
                Senjorų nuolaidos
                #mažamedaug
                Žiemos žaidimas
                „Rosmarino“ lipdukų akcija
                Mobilioji programėlė
                Mokėk numeriais
                Mobilieji mokėjimai
                Dovanų kortelės
                Sudėčių katalogas
                AUTOMOBILIUI
                Degalai
                Benzinas
                Dyzelinas
                AdBlue
                Kiti produktai
                D.U.K
                Plovykla
                Savitarnos plovykla
                Plovyklos abonementas
                Plovyklos programos
                Plovyklos stebėjimas
                Tepalai
                Kelių mokesčių vinjetės
                Image
                Degalų kainos
                Čia rasi žemiausias degalų kainas mūsų tinkle.
                Daugiau
                TVARUMAS
                Mūsų tvarumo istorija
                Pokyčiai degaluose
                Aukščiausios kokybės produktai
                Švaresni automobiliai ir švaresnė gamta
                Socialinė atsakomybė
                Inovacijų laboratorija
                Skaidrumas ir etika
                Žiemos žaidimas
                Degalinių žemėlapis
                Degalų kainos
                Apačioje rasite žemiausias mūsų degalinių tinklo produktų kainas.
                Image
                1,445
                Circle K Vilkaviškis
                 Vilkaviškio g. 10, Serdokų k.
                 Vilkaviškis
                Vasario 7d. 11:00:00
                Image
                1,548
                Circle K Aplinkelis II
                 Panevėžio aplinkl. 20, Šilagalio k.
                 Panevėžys
                Vasario 7d. 11:00:00
                Image
                1,564
                Circle K Smiltelė
                 Taikos pr. 112A,
                 Klaipėda
                Vasario 7d. 11:00:00
                Image
                1,59
                Circle K Baltupiai
                 Baltupio g. 10,
                 Vilnius
                Vasario 7d. 11:00:00
                Image
                1,679
                Circle K Baltupiai
                 Baltupio g. 10,
                 Vilnius
                Vasario 9d. 11:00:00
                Image
                0,603
                Circle K Šilutė
                 Cintjoniškių g. 15,
                 Šilutė
                Vasario 7d. 11:00:00
                Image
                0,898
                Visos prekiaujančios degalinės.
                 Degalinių sąrašas:
                 https://www.circlek.lt/degalai/adblue
                Vasario 7d. 11:00:00
                Footer
                PRIVATIEMS
                Degalinių sąrašas
                Klientų aptarnavimas
                Degalų kainos
                Prarastų pinigų grąžinimas
                Inovacijų laboratorija
                VERSLUI
                Tapkite verslo klientu
                EXTRA verslo klientams
                Gaminio duomenų lapai
                Sutartys ir dokumentai įmonėms
                Degalinės užsienyje
                Fiksuota kainodara
                DIRBK PAS MUS
                Darbas degalinėse
                Darbas ofise
                APIE MUS
                Kontaktai
                Naujienos
                Naujos Circle K degalinės
                Socialinė atsakomybė
                Vadovai
                Bottom
                Privatumo politika
                Slapukai
                Copyright © 2024 Circle K Lietuva
                Raskite mus Google Play
                Raskite mus App Store""";
    }
}
