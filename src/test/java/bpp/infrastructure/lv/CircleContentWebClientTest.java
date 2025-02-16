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
        CirclePetrolPriceModel circlePetrolPriceModel = (CirclePetrolPriceModel) response.responseModel();
        // Assert
        assertThat(circlePetrolPriceModel.getId()).isEqualTo(200);
        assertThat(circlePetrolPriceModel.getPetrol()).isEqualTo(new BigDecimal("1.654"));
        assertThat(circlePetrolPriceModel.getPetrolPro()).isEqualTo(new BigDecimal("1.674"));
        assertThat(circlePetrolPriceModel.getDiesel()).isEqualTo(new BigDecimal("1.634"));
        assertThat(circlePetrolPriceModel.getDieselPro()).isEqualTo(new BigDecimal("1.734"));
        assertThat(circlePetrolPriceModel.getGas()).isEqualTo(new BigDecimal("0.945"));
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
        CirclePetrolPriceModel circlePetrolPriceModel = (CirclePetrolPriceModel) response.responseModel();
        // Assert
        assertThat(circlePetrolPriceModel.getId()).isEqualTo(200);
        assertThat(circlePetrolPriceModel.getPetrolBestPriceAddress()).isEqualTo("The fuel price is set for all gas stations");
        assertThat(circlePetrolPriceModel.getPetrolProBestPriceAddress()).isEqualTo("The fuel price is set for all gas stations");
        assertThat(circlePetrolPriceModel.getDieselBestPriceAddress()).isEqualTo("The fuel price is set for all gas stations");
        assertThat(circlePetrolPriceModel.getDieselProBestPriceAddress()).isEqualTo("The fuel price is set for all gas stations");
        assertThat(circlePetrolPriceModel.getGasBestPriceAddress()).isEqualTo("The fuel price is set for all gas stations");
    }

    private String returnContent() {
        return """
                Cenas spēkā no 14.02.2025.
                Cenas attiecas uz privātpersonām, un tām ir informatīvs raksturs. Cenas ﻿﻿var mainīties vairākkārtīgi dienas laikā un atšķirties dažādās stacijās.
                Degviela	Cena EUR	\s
                95miles	1.654 EUR	Visās Rīgas DUS degvielas cenas ir vienādas
                98miles+	1.674 EUR	Visās Rīgas DUS degvielas cenas ir vienādas
                Dmiles	1.634 EUR	Visās Rīgas DUS degvielas cenas ir vienādas
                Dmiles+	1.734 EUR	Visās Rīgas DUS degvielas cenas ir vienādas
                Autogāze	0.945 EUR	Visās Rīgas DUS autogāzes cenas ir vienādas
                Degvielas cenu ietekmējošie faktori""";
    }
}
