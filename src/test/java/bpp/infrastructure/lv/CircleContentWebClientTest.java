package bpp.infrastructure.lv;

import bpp.model.CirclePetrolPriceModel;
import bpp.model.ErrorModel;
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
class CircleContentWebClientTest {
    @Spy
    private CircleContentWebClient circleContentWebClient = new CircleContentWebClient();

    @BeforeEach
    void init() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method postConstructMethod = CircleContentWebClient.class.getDeclaredMethod("before");
        postConstructMethod.setAccessible(true);
        postConstructMethod.invoke(circleContentWebClient);
    }

    @Test
    void notGetContentPage_ReturnError() {
        //Arrange, Act
        doReturn(WebPageResponseModel
                .builder()
                .id(404)
                .content("Error")
                .build())
                .when(circleContentWebClient)
                .getWebContent(null);
        Response<?> response = circleContentWebClient.getContent();
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
                .when(circleContentWebClient)
                .getWebContent(null);
        Response<?> response = circleContentWebClient.getContent();
        CirclePetrolPriceModel circlePetrolPriceModel = (CirclePetrolPriceModel) response.getResponseModel();
        // Assert
        assertThat(circlePetrolPriceModel.getId()).isEqualTo(200);
        assertThat(circlePetrolPriceModel.getPetrol()).isEqualTo(new BigDecimal("1.547"));
        assertThat(circlePetrolPriceModel.getPetrolPro()).isEqualTo(new BigDecimal("1.597"));
        assertThat(circlePetrolPriceModel.getDiesel()).isEqualTo(new BigDecimal("1.417"));
        assertThat(circlePetrolPriceModel.getDieselPro()).isEqualTo(new BigDecimal("1.517"));
        assertThat(circlePetrolPriceModel.getGas()).isEqualTo(new BigDecimal("0.715"));
    }

    @Test
    void mustReturnBestPricesAddresses() {
        // Arrange, Act
        doReturn(WebPageResponseModel
                .builder()
                .id(200)
                .content(returnContent())
                .build())
                .when(circleContentWebClient)
                .getWebContent(null);
        Response<?> response = circleContentWebClient.getContent();
        CirclePetrolPriceModel circlePetrolPriceModel = (CirclePetrolPriceModel) response.getResponseModel();
        // Assert
        assertThat(circlePetrolPriceModel.getId()).isEqualTo(200);
        assertThat(circlePetrolPriceModel.getPetrolBestPriceAddress()).isEqualTo("Valdeķu iela 35");
        assertThat(circlePetrolPriceModel.getPetrolProBestPriceAddress()).isEqualTo("Valdeķu iela 35");
        assertThat(circlePetrolPriceModel.getDieselBestPriceAddress()).isEqualTo("Gunāra Astras iela 10, Valdeķu iela 35");
        assertThat(circlePetrolPriceModel.getDieselProBestPriceAddress()).isEqualTo("Gunāra Astras iela 10");
        assertThat(circlePetrolPriceModel.getGasBestPriceAddress()).isEqualTo("Lubānas iela 119a");
    }

    private String returnContent() {
        return """
                Meklēt staciju
                Image
                Image
                Degvielas cenas
                Zemākās cenas DUS tīklā Rīgā
                Cenas spēkā no 05.07.2023.
                Cenām ir informatīvs raksturs, tās \uFEFF\uFEFFvar mainīties vairākkārtīgi dienas laikā un atšķirties dažādās stacijās.
                Degviela\tCena EUR\tUzpildes stacijas adrese
                miles 95\t1.547 EUR\tValdeķu iela 35
                milesPLUS 98\t1.597 EUR\tValdeķu iela 35
                miles D\t1.417 EUR\tGunāra Astras iela 10, Valdeķu iela 35
                milesPLUS D\t1.517 EUR\tGunāra Astras iela 10
                Autogāze\t0.715 EUR\tLubānas iela 119a
                Degvielas cenu ietekmējošie faktori""";
    }
}
