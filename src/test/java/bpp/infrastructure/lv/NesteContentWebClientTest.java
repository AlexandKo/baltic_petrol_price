package bpp.infrastructure.lv;

import bpp.model.ErrorModel;
import bpp.model.NestePetrolPriceModel;
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

import static bpp.util.Messages.PRICE_FOR_ALL_STATIONS;
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
        assertThat(nestePetrolPriceModel.getPetrol()).isEqualTo(new BigDecimal("1.697"));
        assertThat(nestePetrolPriceModel.getPetrolPro()).isEqualTo(new BigDecimal("1.737"));
        assertThat(nestePetrolPriceModel.getDiesel()).isEqualTo(new BigDecimal("1.757"));
        assertThat(nestePetrolPriceModel.getDieselPro()).isEqualTo(new BigDecimal("1.867"));
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
        assertThat(nestePetrolPriceModel.getPetrolBestPriceAddress()).isEqualTo(PRICE_FOR_ALL_STATIONS);
        assertThat(nestePetrolPriceModel.getPetrolProBestPriceAddress()).isEqualTo(PRICE_FOR_ALL_STATIONS);
        assertThat(nestePetrolPriceModel.getDieselBestPriceAddress()).isEqualTo(PRICE_FOR_ALL_STATIONS);
        assertThat(nestePetrolPriceModel.getDieselProBestPriceAddress()).isEqualTo(PRICE_FOR_ALL_STATIONS);
    }

    private String returnContent() {
        return "Zemākās degvielas cenas Neste degvielas uzpildes stacijās Rīgā:\n" +
                "  \n" +
                "Cena\tEUR/l\tDUS\n" +
                "Neste Futura 95\n" +
                "\t1.697\n" +
                "\tŠobrīd degvielas cenas visās Rīgas Neste DUS ir vienādas.\n" +
                "Neste Futura 98\n" +
                "\t1.737\n" +
                "\tŠobrīd degvielas cenas visās Rīgas Neste DUS ir vienādas.\n" +
                "Neste Futura D\n" +
                "\t1.757\n" +
                "\tŠobrīd degvielas cenas visās Rīgas Neste DUS ir vienādas.\n" +
                "Neste Pro Diesel\n" +
                "\t1.867\n" +
                "\tŠobrīd degvielas cenas visās Rīgas Neste DUS ir vienādas.\n" +
                " \t \t \n" +
                "Informācija par degvielas cenām netiek atjaunota brīvdienās un svētku dienās.\n" +
                "Kas veido degvielas cenu  ";
    }
}
