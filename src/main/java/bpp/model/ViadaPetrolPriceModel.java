package bpp.model;

import bpp.util.Country;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ViadaPetrolPriceModel {
    @Schema(example = "200")
    private int id;
    @Schema(example = "LV")
    private Country country;
    @Schema(example = "1.714")
    private BigDecimal petrolEcto;
    @Schema(example = "1.739")
    private BigDecimal petrolEctoPlus;
    @Schema(example = "1.769")
    private BigDecimal petrolPro;
    @Schema(example = "2.189")
    private BigDecimal petrolEco;
    @Schema(example = "1.774")
    private BigDecimal diesel;
    @Schema(example = "1.764")
    private BigDecimal dieselEcto;
    @Schema(example = "0.805")
    private BigDecimal gas;
    @Schema(example = "DUS Astras: G.Astras iela 7, Rīga.")
    private String petrolEctoBestPriceAddress;
    @Schema(example = "DUS Vecmīlgrāvis: Emmas iela 45, Rīga.")
    private String petrolEctoPlusBestPriceAddress;
    @Schema(example = "DUS Ziepniekkalns: Ziepniekkalna iela 16, Rīga.")
    private String petrolProBestPriceAddress;
    @Schema(example = "DUS Dārzciema: Dārzciema iela 69, Rīga, DUS Astras: G.Astras iela 7")
    private String petrolEcoBestPriceAddress;
    @Schema(example = "DUS Dambja: Dambja iela 11, Rīga")
    private String dieselBestPriceAddress;
    @Schema(example = "DUS Astras: G.Astras iela 7")
    private String dieselEctoBestPriceAddress;
    @Schema(example = "ADUS Saharova: A.Saharova iela 10, Rīga")
    private String gasBestPriceAddress;
    @Schema(example = "null")
    private String errorMessage;
}
