package bpp.mapper;

import bpp.entity.ViadaPriceEntity;
import bpp.model.PetrolPriceModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ViadaPriceMapper {
    public static ViadaPriceEntity toViadaPriceEntity(PetrolPriceModel petrolPriceModel) {
        return ViadaPriceEntity.builder()
                .country(petrolPriceModel.getCountry())
                .petrolEcto(petrolPriceModel.getPetrolEcto())
                .petrolEctoBestPriceAddress(petrolPriceModel.getPetrolEctoBestPriceAddress())
                .petrolEctoPlus(petrolPriceModel.getPetrolEctoPlus())
                .petrolEctoPlusBestPriceAddress(petrolPriceModel.getPetrolEctoPlusBestPriceAddress())
                .petrolPro(petrolPriceModel.getPetrolPro())
                .petrolProBestPriceAddress(petrolPriceModel.getPetrolProBestPriceAddress())
                .diesel(petrolPriceModel.getDiesel())
                .dieselBestPriceAddress(petrolPriceModel.getDieselBestPriceAddress())
                .dieselEcto(petrolPriceModel.getDieselEcto())
                .dieselEctoBestPriceAddress(petrolPriceModel.getDieselEctoBestPriceAddress())
                .gas(petrolPriceModel.getGas())
                .gasBestPriceAddress(petrolPriceModel.getGasProBestPriceAddress())
                .petrolEco(petrolPriceModel.getPetrolE())
                .petrolEcoBestPriceAddress(petrolPriceModel.getPetrolEBestPriceAddress())
                .build();
    }
}