package bpp.service.lv;

import bpp.entity.NestePriceEntity;
import bpp.infrastructure.ContentWebClient;
import bpp.infrastructure.lv.NesteContentWebClient;
import bpp.mapper.NestePriceMapper;
import bpp.model.PetrolPriceModel;
import bpp.repository.NestePriceRepository;
import bpp.service.PetrolPriceService;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static bpp.util.Messages.NEW_RECORD_INFO;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NestePetrolPriceService implements PetrolPriceService {
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
            log.info(String.format(NEW_RECORD_INFO, "Neste", nestePriceEntity.getCountry()));
        }
    }
}
