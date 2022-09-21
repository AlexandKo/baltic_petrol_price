package bpp.service.lv;

import bpp.entity.NestePriceEntity;
import bpp.infrastructure.ContentWebClient;
import bpp.infrastructure.lv.NesteContentWebClient;
import bpp.mapper.NestePriceMapper;
import bpp.model.PetrolPriceModel;
import bpp.repository.NestePriceRepository;
import bpp.service.LvPetrolPriceService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NesteLvPetrolPriceService implements LvPetrolPriceService {
    private final List<ContentWebClient> contentWebClients;
    private final NestePriceRepository nestePriceRepository;

    @Override
    public void savePetrolPrice() {
        PetrolPriceModel nestePetrolPriceModel = contentWebClients
                .stream()
                .filter(NesteContentWebClient.class::isInstance)
                .map(ContentWebClient::getContent).findFirst()
                .orElse(null);

        if (nestePetrolPriceModel != null) {
            NestePriceEntity nestePriceEntity = NestePriceMapper.toNestePriceEntity(nestePetrolPriceModel);
            nestePriceRepository.save(nestePriceEntity);
        }
    }
}
