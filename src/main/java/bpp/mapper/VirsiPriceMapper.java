package bpp.mapper;

import bpp.entity.VirsiPriceEntity;
import bpp.model.VirsiPetrolPriceModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VirsiPriceMapper {
    public static VirsiPriceEntity toVirsiPriceEntity(VirsiPetrolPriceModel petrolPriceModel) {
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
}
