package bpp.mapper;

import bpp.entity.GotikaPriceEntity;
import bpp.model.GotikaPetrolPriceModel;
import bpp.model.PetrolPriceModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GotikaPriceMapper {
    public static GotikaPriceEntity toGotikaPriceEntity(PetrolPriceModel petrolPriceModel) {
        return GotikaPriceEntity.builder()
                .country(petrolPriceModel.getCountry())
                .petrol(petrolPriceModel.getPetrol())
                .petrolBestPriceAddress(petrolPriceModel.getPetrolBestPriceAddress())
                .diesel(petrolPriceModel.getDiesel())
                .dieselBestPriceAddress(petrolPriceModel.getDieselBestPriceAddress())
                .build();
    }

    public static GotikaPetrolPriceModel toGotikaPetrolPriceModel(PetrolPriceModel petrolPriceModel) {
        return GotikaPetrolPriceModel.builder()
                .id(petrolPriceModel.getId())
                .country(petrolPriceModel.getCountry())
                .petrol(petrolPriceModel.getPetrol())
                .petrolBestPriceAddress(petrolPriceModel.getPetrolBestPriceAddress())
                .diesel(petrolPriceModel.getDiesel())
                .dieselBestPriceAddress(petrolPriceModel.getDieselBestPriceAddress())
                .errorMessage(petrolPriceModel.getErrorMessage())
                .build();
    }
}
