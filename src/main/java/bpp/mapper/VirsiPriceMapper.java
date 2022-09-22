package bpp.mapper;

import bpp.entity.VirsiPriceEntity;
import bpp.model.PetrolPriceModel;
import bpp.model.VirsiPetrolPriceModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VirsiPriceMapper {
    public static VirsiPriceEntity toVirsiPriceEntity(PetrolPriceModel petrolPriceModel) {
        return VirsiPriceEntity.builder()
                .country(petrolPriceModel.getCountry())
                .petrol(petrolPriceModel.getPetrol())
                .petrolBestPriceAddress(petrolPriceModel.getPetrolBestPriceAddress())
                .petrolPro(petrolPriceModel.getPetrolPro())
                .petrolProBestPriceAddress(petrolPriceModel.getPetrolProBestPriceAddress())
                .diesel(petrolPriceModel.getDiesel())
                .dieselBestPriceAddress(petrolPriceModel.getDieselBestPriceAddress())
                .gas(petrolPriceModel.getGas())
                .gasBestPriceAddress(petrolPriceModel.getGasBestPriceAddress())
                .build();
    }

    public static VirsiPetrolPriceModel toVirsiPetrolPriceModel(PetrolPriceModel petrolPriceModel) {
        return VirsiPetrolPriceModel.builder()
                .id(petrolPriceModel.getId())
                .country(petrolPriceModel.getCountry())
                .petrol(petrolPriceModel.getPetrol())
                .petrolBestPriceAddress(petrolPriceModel.getPetrolBestPriceAddress())
                .petrolPro(petrolPriceModel.getPetrolPro())
                .petrolProBestPriceAddress(petrolPriceModel.getPetrolProBestPriceAddress())
                .diesel(petrolPriceModel.getDiesel())
                .dieselBestPriceAddress(petrolPriceModel.getDieselBestPriceAddress())
                .gas(petrolPriceModel.getGas())
                .gasBestPriceAddress(petrolPriceModel.getGasBestPriceAddress())
                .errorMessage(petrolPriceModel.getErrorMessage())
                .build();
    }
}
