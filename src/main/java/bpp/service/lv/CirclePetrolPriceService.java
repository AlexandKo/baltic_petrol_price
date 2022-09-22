package bpp.service.lv;

import bpp.entity.CirclePriceEntity;
import bpp.infrastructure.ContentWebClient;
import bpp.infrastructure.lv.CircleContentWebClient;
import bpp.mapper.CirclePriceMapper;
import bpp.model.PetrolPriceModel;
import bpp.repository.CirclePriceRepository;
import bpp.service.PetrolPriceService;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static bpp.util.MessageCodes.WEB_CLIENT_CONNECTION_SUCCESSFULLY;
import static bpp.util.Messages.NEW_RECORD_ERROR;
import static bpp.util.Messages.NEW_RECORD_INFO;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CirclePetrolPriceService implements PetrolPriceService {
    private static final String PETROL_STATION = "Circle K";
    private final List<ContentWebClient> contentWebClients;
    private final CirclePriceRepository circlePriceRepository;

    @Override
    public void savePetrolPrice() {
        Optional<PetrolPriceModel> circlePetrolPriceModelOptional = contentWebClients
                .stream()
                .filter(CircleContentWebClient.class::isInstance)
                .map(ContentWebClient::getContent).findFirst();

        circlePetrolPriceModelOptional.ifPresent(circlePetrolPriceModel -> {
            if (circlePetrolPriceModel.getId() == WEB_CLIENT_CONNECTION_SUCCESSFULLY) {
                CirclePriceEntity circlePriceEntity = CirclePriceMapper.toCirclePriceEntity(circlePetrolPriceModel);
                circlePriceRepository.save(circlePriceEntity);
                log.info(String.format(NEW_RECORD_INFO, PETROL_STATION, circlePriceEntity.getCountry()));
            } else {
                log.error(String.format(NEW_RECORD_ERROR, PETROL_STATION, circlePetrolPriceModel.getCountry()));
            }
        });
    }
}
