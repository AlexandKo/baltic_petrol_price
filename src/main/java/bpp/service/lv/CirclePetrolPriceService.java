package bpp.service.lv;

import bpp.entity.CirclePriceEntity;
import bpp.infrastructure.ContentWebClient;
import bpp.infrastructure.lv.CircleContentWebClient;
import bpp.mapper.CirclePriceMapper;
import bpp.model.PetrolPriceModel;
import bpp.repository.CirclePriceRepository;
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
public class CirclePetrolPriceService implements PetrolPriceService {
    private final List<ContentWebClient> contentWebClients;
    private final CirclePriceRepository circlePriceRepository;

    @Override
    public void savePetrolPrice() {
        PetrolPriceModel circlePetrolPriceModel = contentWebClients
                .stream()
                .filter(CircleContentWebClient.class::isInstance)
                .map(ContentWebClient::getContent).findFirst()
                .orElse(null);

        if (circlePetrolPriceModel != null) {
            CirclePriceEntity circlePriceEntity = CirclePriceMapper.toCirclePriceEntity(circlePetrolPriceModel);
            circlePriceRepository.save(circlePriceEntity);
            log.info(String.format(NEW_RECORD_INFO, "Circle K", circlePriceEntity.getCountry()));
        }
    }
}
