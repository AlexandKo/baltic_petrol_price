package bpp.infrastructure.lv;

import bpp.model.ErrorModel;
import bpp.model.Response;
import bpp.model.VirsiPetrolPriceModel;
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
class VirsiContentWebClientTest {
    @Spy
    private VirsiContentWebClient virsiContentWebClient = new VirsiContentWebClient();

    @BeforeEach
    void init() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method postConstructMethod = VirsiContentWebClient.class.getDeclaredMethod("before");
        postConstructMethod.setAccessible(true);
        postConstructMethod.invoke(virsiContentWebClient);
    }

    @Test
    void notGetContentPage_ReturnError() {
        //Arrange, Act
        doReturn(WebPageResponseModel
                .builder()
                .id(404)
                .content("Error")
                .build())
                .when(virsiContentWebClient)
                .getWebContent(null);
        Response<?> response = virsiContentWebClient.getContent();
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
                .when(virsiContentWebClient)
                .getWebContent(null);
        Response<?> response = virsiContentWebClient.getContent();
        VirsiPetrolPriceModel virsiPetrolPriceModel = (VirsiPetrolPriceModel) response.getResponseModel();
        // Assert
        assertThat(virsiPetrolPriceModel.getId()).isEqualTo(200);
        assertThat(virsiPetrolPriceModel.getPetrol()).isEqualTo(new BigDecimal("1.697"));
        assertThat(virsiPetrolPriceModel.getPetrolPro()).isEqualTo(new BigDecimal("1.737"));
        assertThat(virsiPetrolPriceModel.getDiesel()).isEqualTo(new BigDecimal("1.757"));
        assertThat(virsiPetrolPriceModel.getGas()).isEqualTo(new BigDecimal("0.825"));
    }

    @Test
    void mustReturnBestPricesAddresses() {
        // Arrange, Act
        doReturn(WebPageResponseModel
                .builder()
                .id(200)
                .content(returnContent())
                .build())
                .when(virsiContentWebClient)
                .getWebContent(null);
        Response<?> response = virsiContentWebClient.getContent();
        VirsiPetrolPriceModel virsiPetrolPriceModel = (VirsiPetrolPriceModel) response.getResponseModel();
        // Assert
        assertThat(virsiPetrolPriceModel.getId()).isEqualTo(200);
        assertThat(virsiPetrolPriceModel.getPetrolBestPriceAddress()).isEqualTo("Satekles iela 2, RĪga, LV-1050");
        assertThat(virsiPetrolPriceModel.getPetrolProBestPriceAddress()).isEqualTo("Satekles iela 2, RĪga, LV-1050");
        assertThat(virsiPetrolPriceModel.getDieselBestPriceAddress()).isEqualTo("Satekles iela 2, RĪga, LV-1050");
        assertThat(virsiPetrolPriceModel.getGasBestPriceAddress()).isEqualTo("Uzvaras bulvāris 16, Rīga, LV-1048");
    }

    private String returnContent() {
        return "Degvielas cenas\n" +
                "dd\n" +
                "1.757\n" +
                "Satekles iela 2, RĪga, LV-1050\n" +
                "95e\n" +
                "1.697\n" +
                "Satekles iela 2, RĪga, LV-1050\n" +
                "98e\n" +
                "1737\n" +
                "Satekles iela 2, RĪga, LV-1050\n" +
                "cng\n" +
                "5.075\n" +
                "Lubānas iela 102a, Rīga, LV-1021\n" +
                "lpg\n" +
                "0.825\n" +
                "Uzvaras bulvāris 16, Rīga, LV-1048\n" +
                "dd\n" +
                "1.757\n" +
                "Satekles iela 2, RĪga, LV-1050\n" +
                "95e\n" +
                "1.697\n" +
                "Satekles iela 2, RĪga, LV-1050\n" +
                "98e\n" +
                "1737\n" +
                "Satekles iela 2, RĪga, LV-1050\n" +
                "cng\n" +
                "5.075\n" +
                "Lubānas iela 102a, Rīga, LV-1021\n" +
                "lpg\n" +
                "0.825\n" +
                "Uzvaras bulvāris 16, Rīga, LV-1048\n" +
                "Lai darītāji vienmēr būtu  informēti par Viršu izdevīgākajiem piedāvājumiem, regulāri atjaunojam aktuālo informāciju par labākajām degvielas cenām Rīgas un Pierīgas Viršu spēka stacijās.";
    }
}
