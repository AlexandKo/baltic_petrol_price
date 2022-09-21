package bpp.service.lv;

import bpp.entity.GotikaPriceEntity;
import bpp.infrastructure.ContentWebClient;
import bpp.infrastructure.lv.GotikaContentWebClient;
import bpp.mapper.GotikaPriceMapper;
import bpp.model.PetrolPriceModel;
import bpp.repository.GotikaPriceRepository;
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
public class GotikaPetrolPriceService implements PetrolPriceService {
    private final List<ContentWebClient> contentWebClients;
    private final GotikaPriceRepository gotikaPriceRepository;

    @Override
    public void savePetrolPrice() {
        PetrolPriceModel nestePetrolPriceModel = contentWebClients
                .stream()
                .filter(GotikaContentWebClient.class::isInstance)
                .map(ContentWebClient::getContent).findFirst()
                .orElse(null);

        if (nestePetrolPriceModel != null) {
            GotikaPriceEntity gotikaPriceEntity = GotikaPriceMapper.toGotikaPriceEntity(nestePetrolPriceModel);
            gotikaPriceRepository.save(gotikaPriceEntity);
            log.info(String.format(NEW_RECORD_INFO, "Gotika", gotikaPriceEntity.getCountry()));
        }
    }
}
