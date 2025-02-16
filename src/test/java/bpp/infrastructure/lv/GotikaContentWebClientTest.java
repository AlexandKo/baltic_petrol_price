package bpp.infrastructure.lv;

import bpp.model.ErrorModel;
import bpp.model.GotikaPetrolPriceModel;
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
class GotikaContentWebClientTest {
    @Spy
    private GotikaContentWebClient gotikaContentWebClient = new GotikaContentWebClient();

    @BeforeEach
    void init() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method postConstructMethod = GotikaContentWebClient.class.getDeclaredMethod("before");
        postConstructMethod.setAccessible(true);
        postConstructMethod.invoke(gotikaContentWebClient);
    }

    @Test
    void notGetContentPage_ReturnError() {
        //Arrange, Act
        doReturn(WebPageResponseModel
                .builder()
                .id(404)
                .content("Error")
                .build())
                .when(gotikaContentWebClient)
                .getWebContent(null);
        Response<?> response = gotikaContentWebClient.getContent();
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
                .when(gotikaContentWebClient)
                .getWebContent(null);
        Response<?> response = gotikaContentWebClient.getContent();
        GotikaPetrolPriceModel gotikaPetrolPriceModel = (GotikaPetrolPriceModel) response.responseModel();
        // Assert
        assertThat(gotikaPetrolPriceModel.getId()).isEqualTo(200);
        assertThat(gotikaPetrolPriceModel.getPetrol()).isEqualTo(new BigDecimal("1.647"));
        assertThat(gotikaPetrolPriceModel.getDiesel()).isEqualTo(new BigDecimal("1.707"));
    }

    @Test
    void mustReturnBestPricesAddresses() {
        // Arrange, Act
        doReturn(WebPageResponseModel
                .builder()
                .id(200)
                .content(returnContent())
                .build())
                .when(gotikaContentWebClient)
                .getWebContent(null);
        Response<?> response = gotikaContentWebClient.getContent();
        GotikaPetrolPriceModel gotikaPetrolPriceModel = (GotikaPetrolPriceModel) response.responseModel();
        // Assert
        assertThat(gotikaPetrolPriceModel.getId()).isEqualTo(200);
        assertThat(gotikaPetrolPriceModel.getPetrolBestPriceAddress()).isEqualTo(PRICE_FOR_ALL_STATIONS);
        assertThat(gotikaPetrolPriceModel.getDieselBestPriceAddress()).isEqualTo(PRICE_FOR_ALL_STATIONS);
    }

    private String returnContent() {
        return """
                Pieslēgties
                Paldies!
                ×
                Jūsu pieprasījums tika veiksmīgi nosūtīts. Mēs sazināsimies ar Jums tuvākajā laikā!
                OK
                95E
                1.647 €
                DD
                1.707 €
                Latviski""";
    }
}
