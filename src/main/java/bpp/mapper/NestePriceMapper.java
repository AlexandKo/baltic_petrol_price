package bpp.mapper;

import bpp.entity.NestePriceEntity;
import bpp.model.NestePetrolPriceModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NestePriceMapper {
    public static NestePriceEntity toNestePriceEntity(NestePetrolPriceModel petrolPriceModel) {
        return NestePriceEntity.builder()
                .country(petrolPriceModel.getCountry())
                .petrol(petrolPriceModel.getPetrol())
                .petrolBestPriceAddress(petrolPriceModel.getPetrolBestPriceAddress())
                .petrolPro(petrolPriceModel.getPetrolPro())
                .petrolProBestPriceAddress(petrolPriceModel.getPetrolProBestPriceAddress())
                .diesel(petrolPriceModel.getDiesel())
                .dieselBestPriceAddress(petrolPriceModel.getDieselBestPriceAddress())
                .dieselPro(petrolPriceModel.getDieselPro())
                .dieselProBestPriceAddress(petrolPriceModel.getDieselProBestPriceAddress())
                .build();
    }
}
