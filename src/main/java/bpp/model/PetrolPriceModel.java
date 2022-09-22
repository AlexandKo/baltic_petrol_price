package bpp.model;

import bpp.util.Country;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PetrolPriceModel {
    private int id;
    private Country country;
    private BigDecimal petrol;
    private BigDecimal petrolEcto;
    private BigDecimal petrolEctoPlus;
    private BigDecimal petrolPro;
    private BigDecimal petrolEco;
    private BigDecimal diesel;
    private BigDecimal dieselEcto;
    private BigDecimal dieselPro;
    private BigDecimal gas;
    private String petrolBestPriceAddress;
    private String petrolEctoBestPriceAddress;
    private String petrolEctoPlusBestPriceAddress;
    private String petrolProBestPriceAddress;
    private String petrolEcoBestPriceAddress;
    private String dieselBestPriceAddress;
    private String dieselEctoBestPriceAddress;
    private String dieselProBestPriceAddress;
    private String gasBestPriceAddress;
    private String errorMessage;
}
