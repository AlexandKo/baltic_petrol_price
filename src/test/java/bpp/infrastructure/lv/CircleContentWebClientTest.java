package bpp.infrastructure.lv;

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
        assertThat(circlePetrolPriceModel.getPetrol()).isEqualTo(new BigDecimal("1.684"));
        assertThat(circlePetrolPriceModel.getPetrolPro()).isEqualTo(new BigDecimal("1.734"));
        assertThat(circlePetrolPriceModel.getDiesel()).isEqualTo(new BigDecimal("1.744"));
        assertThat(circlePetrolPriceModel.getDieselPro()).isEqualTo(new BigDecimal("1.854"));
        assertThat(circlePetrolPriceModel.getGas()).isEqualTo(new BigDecimal("0.825"));
    }

    private String returnContent() {
        return "Meklēt staciju\n" +
                "Image\n" +
                "Image\n" +
                "Degvielas cenas\n" +
                "Zemākās cenas DUS tīklā Rīgā\n" +
                "Cenas spēkā no 23.09.2022.\n" +
                "Cenām ir informatīvs raksturs, tās \uFEFF\uFEFFvar mainīties vairākkārtīgi dienas laikā un atšķirties dažādās stacijās.\n" +
                "Degviela\tCena EUR\tUzpildes stacijas adrese\n" +
                "miles 95\t1.684 EUR\t\n" +
                "Valdeķu iela 35; Maskavas iela 324\n" +
                "milesPLUS 98\t1.734 EUR\t\n" +
                "Valdeķu iela 35; Maskavas iela 324\n" +
                "miles D\t1.744 EUR\t\n" +
                "Valdeķu iela 35; Maskavas iela 324\n" +
                "milesPLUS D\t1.854 EUR\t\n" +
                "Visos Rīgas DUS degvielas cenas ir vienādas.\n" +
                "Autogāze\t0.825 EUR\tVisos Rīgas DUS degvielas cenas ir vienādas.\n" +
                "Degvielas cenu ietekmējošie faktori";
    }
}
