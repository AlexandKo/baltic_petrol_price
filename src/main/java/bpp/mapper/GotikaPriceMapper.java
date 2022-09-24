package bpp.mapper;

import bpp.entity.GotikaPriceEntity;
import bpp.model.GotikaPetrolPriceModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GotikaPriceMapper {
    public static GotikaPriceEntity toGotikaPriceEntity(GotikaPetrolPriceModel petrolPriceModel) {
        return GotikaPriceEntity.builder()
                .country(petrolPriceModel.getCountry())
                .petrol(petrolPriceModel.getPetrol())
                .petrolBestPriceAddress(petrolPriceModel.getPetrolBestPriceAddress())
                .diesel(petrolPriceModel.getDiesel())
                .dieselBestPriceAddress(petrolPriceModel.getDieselBestPriceAddress())
                .build();
    }
}
