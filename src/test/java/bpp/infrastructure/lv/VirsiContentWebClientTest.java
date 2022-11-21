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
        assertThat(virsiPetrolPriceModel.getPetrol()).isEqualTo(new BigDecimal("1.757"));
        assertThat(virsiPetrolPriceModel.getPetrolPro()).isEqualTo(new BigDecimal("1.797"));
        assertThat(virsiPetrolPriceModel.getDiesel()).isEqualTo(new BigDecimal("1.877"));
        assertThat(virsiPetrolPriceModel.getGas()).isEqualTo(new BigDecimal("0.835"));
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
                "DD 1.877\n" +
                "Satekles iela 2, RĪga, LV-1050\n" +
                "95E 1.757\n" +
                "Satekles iela 2, RĪga, LV-1050\n" +
                "98E 1.797\n" +
                "Satekles iela 2, RĪga, LV-1050\n" +
                "CNG 3.395\n" +
                "Lubānas iela 102a, Rīga, LV-1021\n" +
                "LPG 0.835\n" +
                "Uzvaras bulvāris 16, Rīga, LV-1048\n" +
                "Elektrouzlādes cenas\n" +
                "CCS 2 40kW 0.39 EUR/kWh\n" +
                "Varoņu iela 10, Jēkabpils, LV-5202\n" +
                "CCS 2 80kW 0.49 EUR/kWh\n" +
                "Varoņu iela 10, Jēkabpils, LV-5202\n" +
                "CHAdeMO 40kW 0.39 EUR/kWh\n" +
                "Varoņu iela 10, Jēkabpils, LV-5202";
    }
}
