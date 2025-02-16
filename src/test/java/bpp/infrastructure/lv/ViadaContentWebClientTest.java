package bpp.infrastructure.lv;

import bpp.model.ErrorModel;
import bpp.model.Response;
import bpp.model.ViadaPetrolPriceModel;
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
class ViadaContentWebClientTest {
    @Spy
    private ViadaContentWebClient circleContentWebClient = new ViadaContentWebClient();

    @BeforeEach
    void init() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method postConstructMethod = ViadaContentWebClient.class.getDeclaredMethod("before");
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
                .when(circleContentWebClient)
                .getWebContent(null);
        Response<?> response = circleContentWebClient.getContent();
        ViadaPetrolPriceModel circlePetrolPriceModel = (ViadaPetrolPriceModel) response.responseModel();
        // Assert
        assertThat(circlePetrolPriceModel.getId()).isEqualTo(200);
        assertThat(circlePetrolPriceModel.getPetrolEcto()).isEqualTo(new BigDecimal("1.694"));
        assertThat(circlePetrolPriceModel.getPetrolEctoPlus()).isEqualTo(new BigDecimal("1.719"));
        assertThat(circlePetrolPriceModel.getPetrolPro()).isEqualTo(new BigDecimal("1.749"));
        assertThat(circlePetrolPriceModel.getPetrolEco()).isEqualTo(new BigDecimal("2.189"));
        assertThat(circlePetrolPriceModel.getDiesel()).isEqualTo(new BigDecimal("1.754"));
        assertThat(circlePetrolPriceModel.getDieselEcto()).isEqualTo(new BigDecimal("1.744"));
        assertThat(circlePetrolPriceModel.getGas()).isEqualTo(new BigDecimal("0.805"));
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
        ViadaPetrolPriceModel circlePetrolPriceModel = (ViadaPetrolPriceModel) response.responseModel();
        // Assert
        assertThat(circlePetrolPriceModel.getId()).isEqualTo(200);
        assertThat(circlePetrolPriceModel.getPetrolEctoBestPriceAddress()).isEqualTo("DUS Dārzciema: Dārzciema iela 69, Rīga, DUS Astras: G.Astras iela 7, Rīga, DUS Dambja: Dambja iela 11, Rīga, DUS Krūzes: Krūzes iela 53, Rīga, ADUS Valdeķu: Valdeķu iela 34, Rīga.");
        assertThat(circlePetrolPriceModel.getPetrolEctoPlusBestPriceAddress()).isEqualTo("DUS Astras: G.Astras iela 7, Rīga.");
        assertThat(circlePetrolPriceModel.getPetrolProBestPriceAddress()).isEqualTo("DUS Mūkusalas: Mūkusalas iela 59, Rīga, DUS Granīta: Granīta iela 16, Rīga, DUS Ziepniekkalns: Ziepniekkalna iela 16, Rīga.");
        assertThat(circlePetrolPriceModel.getPetrolEcoBestPriceAddress()).isEqualTo("DUS Astras: G.Astras iela 7, Rīga, DUS Vecmīlgrāvis: Emmas iela 45, Rīga.");
        assertThat(circlePetrolPriceModel.getDieselBestPriceAddress()).isEqualTo("DUS Dārzciema: Dārzciema iela 69, Rīga, DUS Astras: G.Astras iela 7, Rīga, DUS Dambja: Dambja iela 11, Rīga, DUS Krūzes: Krūzes iela 53, Rīga.");
        assertThat(circlePetrolPriceModel.getDieselEctoBestPriceAddress()).isEqualTo("ADUS Valdeķu: Valdeķu iela 34, Rīga.");
        assertThat(circlePetrolPriceModel.getGasBestPriceAddress()).isEqualTo("ADUS Saharova: A.Saharova iela 10, Rīga, ADUS Valdeķu: Valdeķu iela 34, Rīga.");
    }

    private String returnContent() {
        return "Informācija par degvielas cenām netiek atjaunota brīvdienās un svētku dienās.\n" +
                "Cenas spēkā no 27.09.2022.\n" +
                " \n" +
                "Degvielas veids\tCena EUR litrā\tDegvielas uzpildes stacijas\n" +
                "\t1.694 EUR\tDUS Dārzciema: Dārzciema iela 69, Rīga, DUS Astras: G.Astras iela 7, Rīga, DUS Dambja: Dambja iela 11, Rīga, DUS Krūzes: Krūzes iela 53, Rīga, ADUS Valdeķu: Valdeķu iela 34, Rīga.\n" +
                "\t1.719 EUR\tDUS Astras: G.Astras iela 7, Rīga.\n" +
                "\t1.749 EUR\tDUS Mūkusalas: Mūkusalas iela 59, Rīga, DUS Granīta: Granīta iela 16, Rīga, DUS Ziepniekkalns: Ziepniekkalna iela 16, Rīga.\n" +
                "\t1.754 EUR\tDUS Dārzciema: Dārzciema iela 69, Rīga, DUS Astras: G.Astras iela 7, Rīga, DUS Dambja: Dambja iela 11, Rīga, DUS Krūzes: Krūzes iela 53, Rīga.\n" +
                "\t1.744 EUR\tADUS Valdeķu: Valdeķu iela 34, Rīga.\n" +
                "\t0.805 EUR\tADUS Saharova: A.Saharova iela 10, Rīga, ADUS Valdeķu: Valdeķu iela 34, Rīga.\n" +
                "\t2.189 EUR\tDUS Astras: G.Astras iela 7, Rīga, DUS Vecmīlgrāvis: Emmas iela 45, Rīga.\n" +
                "Sazinies ar mums";
    }
}
