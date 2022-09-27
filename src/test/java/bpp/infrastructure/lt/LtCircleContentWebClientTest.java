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
                .when(ltCircleContentWebClient)
                .getWebContent(null);
        Response<?> response = ltCircleContentWebClient.getContent();
        CirclePetrolPriceModel circlePetrolPriceModel = (CirclePetrolPriceModel) response.getResponseModel();
        // Assert
        assertThat(circlePetrolPriceModel.getId()).isEqualTo(200);
        assertThat(circlePetrolPriceModel.getPetrol()).isEqualTo(new BigDecimal("1.629"));
        assertThat(circlePetrolPriceModel.getPetrolPro()).isEqualTo(new BigDecimal("1.738"));
        assertThat(circlePetrolPriceModel.getDiesel()).isEqualTo(new BigDecimal("1.739"));
        assertThat(circlePetrolPriceModel.getDieselPro()).isEqualTo(new BigDecimal("1.828"));
        assertThat(circlePetrolPriceModel.getGas()).isEqualTo(new BigDecimal("0.723"));
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
        CirclePetrolPriceModel circlePetrolPriceModel = (CirclePetrolPriceModel) response.getResponseModel();
        // Assert
        assertThat(circlePetrolPriceModel.getId()).isEqualTo(200);
        assertThat(circlePetrolPriceModel.getPetrolBestPriceAddress()).isEqualTo("Circle K Šilutė Cintjoniškių g. 15, Šilutė");
        assertThat(circlePetrolPriceModel.getPetrolProBestPriceAddress()).isEqualTo("Circle K Parkas Parko g. 7A, Panevėžys");
        assertThat(circlePetrolPriceModel.getDieselBestPriceAddress()).isEqualTo("Circle K Parkas Parko g. 7A, Panevėžys");
        assertThat(circlePetrolPriceModel.getDieselProBestPriceAddress()).isEqualTo("Circle K Parkas Parko g. 7A, Panevėžys");
        assertThat(circlePetrolPriceModel.getGasBestPriceAddress()).isEqualTo("Circle K Dainava Pramonės pr. 18, Kaunas");
    }

    private String returnContent() {
        return "Apačioje rasite žemiausias mūsų degalinių tinklo produktų kainas.\n" +
                "Image\n" +
                "1,629\n" +
                "Circle K Šilutė\n" +
                " Cintjoniškių g. 15,\n" +
                " Šilutė\n" +
                "Rugsėjo 26d. 11:00:00\n" +
                "Image\n" +
                "1,728\n" +
                "Circle K Parkas\n" +
                " Parko g. 7A,\n" +
                " Panevėžys\n" +
                "Rugsėjo 26d. 11:00:00\n" +
                "Image\n" +
                "1,738\n" +
                "Circle K Parkas\n" +
                " Parko g. 7A,\n" +
                " Panevėžys\n" +
                "Rugsėjo 26d. 11:00:00\n" +
                "Image\n" +
                "1,739\n" +
                "Circle K Parkas\n" +
                " Parko g. 7A,\n" +
                " Panevėžys\n" +
                "Rugsėjo 26d. 11:00:00\n" +
                "Image\n" +
                "1,828\n" +
                "Circle K Parkas\n" +
                " Parko g. 7A,\n" +
                " Panevėžys\n" +
                "Rugsėjo 26d. 11:00:00\n" +
                "Image\n" +
                "1,459\n" +
                "Circle K Šilutė\n" +
                " Cintjoniškių g. 15,\n" +
                " Šilutė\n" +
                "Rugsėjo 26d. 11:00:00\n" +
                "Image\n" +
                "0,723\n" +
                "Circle K Dainava\n" +
                " Pramonės pr. 18,\n" +
                " Kaunas\n" +
                "Rugsėjo 26d. 11:00:00\n" +
                "Image\n" +
                "1.164\n" +
                "Visos prekiaujančios degalinės.";
    }
}
