package bpp.model;

import bpp.util.Country;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CirclePetrolPriceModel {
    @Schema(example = "200")
    private int id;
    @Schema(example = "LV")
    private Country country;
    @Schema(example = "1.704")
    private BigDecimal petrol;
    @Schema(example = "1.754")
    private BigDecimal petrolPro;
    @Schema(example = "1.764")
    private BigDecimal diesel;
    @Schema(example = "1.874")
    private BigDecimal dieselPro;
    @Schema(example = "0.825")
    private BigDecimal gas;
    @Schema(example = "Valdeķu iela 35; Maskavas iela 324")
    private String petrolBestPriceAddress;
    @Schema(example = "Valdeķu iela 35; Maskavas iela 324")
    private String petrolProBestPriceAddress;
    @Schema(example = "Valdeķu iela 35; Maskavas iela 324")
    private String dieselBestPriceAddress;
    @Schema(example = "The fuel price is set for all gas stations")
    private String dieselProBestPriceAddress;
    @Schema(example = "The fuel price is set for all gas stations")
    private String gasBestPriceAddress;
    @Schema(example = "null")
    private String errorMessage;
}
