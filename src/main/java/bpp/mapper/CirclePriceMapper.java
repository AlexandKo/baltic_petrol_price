package bpp.mapper;

import bpp.entity.CirclePriceEntity;
import bpp.model.CirclePetrolPriceModel;
import bpp.model.PetrolPriceModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CirclePriceMapper {
    public static CirclePriceEntity toCirclePriceEntity(PetrolPriceModel petrolPriceModel) {
        return CirclePriceEntity.builder()
                .country(petrolPriceModel.getCountry())
                .petrol(petrolPriceModel.getPetrol())
                .petrolBestPriceAddress(petrolPriceModel.getPetrolBestPriceAddress())
                .petrolPro(petrolPriceModel.getPetrolPro())
                .petrolProBestPriceAddress(petrolPriceModel.getPetrolProBestPriceAddress())
                .diesel(petrolPriceModel.getDiesel())
                .dieselBestPriceAddress(petrolPriceModel.getDieselBestPriceAddress())
                .dieselPro(petrolPriceModel.getDieselPro())
                .dieselProBestPriceAddress(petrolPriceModel.getDieselProBestPriceAddress())
                .gas(petrolPriceModel.getGas())
                .gasBestPriceAddress(petrolPriceModel.getGasBestPriceAddress())
                .build();
    }

    public static CirclePetrolPriceModel toCirclePetrolPriceModel(PetrolPriceModel petrolPriceModel) {
        return CirclePetrolPriceModel.builder()
                .id(petrolPriceModel.getId())
                .country(petrolPriceModel.getCountry())
                .petrol(petrolPriceModel.getPetrol())
                .petrolBestPriceAddress(petrolPriceModel.getPetrolBestPriceAddress())
                .petrolPro(petrolPriceModel.getPetrolPro())
                .petrolProBestPriceAddress(petrolPriceModel.getPetrolProBestPriceAddress())
                .diesel(petrolPriceModel.getDiesel())
                .dieselBestPriceAddress(petrolPriceModel.getDieselBestPriceAddress())
                .dieselPro(petrolPriceModel.getDieselPro())
                .dieselProBestPriceAddress(petrolPriceModel.getDieselProBestPriceAddress())
                .gas(petrolPriceModel.getGas())
                .gasBestPriceAddress(petrolPriceModel.getGasBestPriceAddress())
                .errorMessage(petrolPriceModel.getErrorMessage())
                .build();
    }
}
