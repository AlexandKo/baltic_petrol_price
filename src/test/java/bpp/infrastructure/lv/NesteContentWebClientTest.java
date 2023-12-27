package bpp.infrastructure.lv;

import bpp.model.ErrorModel;
import bpp.model.NestePetrolPriceModel;
import bpp.model.Response;
import bpp.model.WebPageResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class NesteContentWebClientTest {
    @Spy
    private NesteContentWebClient nesteContentWebClient = new NesteContentWebClient();

    @BeforeEach
    void init() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method postConstructMethod = NesteContentWebClient.class.getDeclaredMethod("before");
        postConstructMethod.setAccessible(true);
        postConstructMethod.invoke(nesteContentWebClient);
    }

    @Test
    void notGetContentPage_ReturnError() {
        //Arrange, Act
        doReturn(WebPageResponseModel
                .builder()
                .id(404)
                .content("Error")
                .build())
                .when(nesteContentWebClient)
                .getWebContent(null);
        Response<?> response = nesteContentWebClient.getContent();
        ErrorModel errorModel = (ErrorModel) response.getResponseModel();
        // Assert
        assertThat(response.getResponseModel()).isInstanceOf(ErrorModel.class);
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
                .when(nesteContentWebClient)
                .getWebContent(null);
        Response<?> response = nesteContentWebClient.getContent();
        NestePetrolPriceModel nestePetrolPriceModel = (NestePetrolPriceModel) response.getResponseModel();
        // Assert
        assertThat(nestePetrolPriceModel.getId()).isEqualTo(200);
        assertThat(nestePetrolPriceModel.getPetrol()).isEqualTo(new BigDecimal("1.507"));
        assertThat(nestePetrolPriceModel.getPetrolPro()).isEqualTo(new BigDecimal("1.547"));
        assertThat(nestePetrolPriceModel.getDiesel()).isEqualTo(new BigDecimal("1.507"));
        assertThat(nestePetrolPriceModel.getDieselPro()).isEqualTo(new BigDecimal("1.617"));
    }

    @Test
    void mustReturnBestPricesAddresses() {
        // Arrange, Act
        doReturn(WebPageResponseModel
                .builder()
                .id(200)
                .content(returnContent())
                .build())
                .when(nesteContentWebClient)
                .getWebContent(null);
        Response<?> response = nesteContentWebClient.getContent();
        NestePetrolPriceModel nestePetrolPriceModel = (NestePetrolPriceModel) response.getResponseModel();
        // Assert
        assertThat(nestePetrolPriceModel.getId()).isEqualTo(200);
        assertThat(nestePetrolPriceModel.getPetrolBestPriceAddress()).isEqualTo("Dzirnavu 127, Katoļu 4, Brīvības gatve 253");
        assertThat(nestePetrolPriceModel.getPetrolProBestPriceAddress()).isEqualTo("Dzirnavu 127, Katoļu 4, Brīvības gatve 253");
        assertThat(nestePetrolPriceModel.getDieselBestPriceAddress()).isEqualTo("Dzirnavu 127, Katoļu 4, Brīvības gatve 253");
        assertThat(nestePetrolPriceModel.getDieselProBestPriceAddress()).isEqualTo("Katoļu 4");
    }

    private String returnContent() {
        return """
                dienas laikā un atšķirties dažādās stacijās.
                Zemākās degvielas cenas Neste degvielas uzpildes stacijās Rīgā:
                \s
                Cena	EUR/l	DUS
                Neste Futura 95
                	1.507
                	Dzirnavu 127, Katoļu 4, Brīvības gatve 253
                Neste Futura 98
                	1.547
                	Dzirnavu 127, Katoļu 4, Brīvības gatve 253
                Neste Futura D
                	1.507
                	Dzirnavu 127, Katoļu 4, Brīvības gatve 253
                Neste Pro Diesel
                	1.617
                	Katoļu 4
                \s
                Informācija par degvielas cenām netiek atjaunota brīvdienās un svētku dienās.
                Kas veido degvielas cenu \s""";
    }
}
