package bpp.service.lv;

import bpp.entity.NestePriceEntity;
import bpp.infrastructure.ContentWebClient;
import bpp.infrastructure.lv.NesteContentWebClient;
import bpp.mapper.NestePriceMapper;
import bpp.model.PetrolPriceModel;
import bpp.repository.NestePriceRepository;
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
public class NestePetrolPriceService implements PetrolPriceService {
    private static final String PETROL_STATION = "Neste";
    private final List<ContentWebClient> contentWebClients;
    private final NestePriceRepository nestePriceRepository;

    @Override
    public void savePetrolPrice() {
        Optional<PetrolPriceModel> nestePetrolPriceModelOptional = contentWebClients
                .stream()
                .filter(NesteContentWebClient.class::isInstance)
                .map(ContentWebClient::getContent).findFirst();

        nestePetrolPriceModelOptional.ifPresent(nestePetrolPriceModel -> {
            if (nestePetrolPriceModel.getId() == WEB_CLIENT_CONNECTION_SUCCESSFULLY) {
                NestePriceEntity nestePriceEntity = NestePriceMapper.toNestePriceEntity(nestePetrolPriceModel);
                nestePriceRepository.save(nestePriceEntity);
                log.info(String.format(NEW_RECORD_INFO, PETROL_STATION, nestePriceEntity.getCountry()));
            } else {
                log.error(String.format(NEW_RECORD_ERROR, PETROL_STATION, nestePetrolPriceModel.getCountry()));
            }
        });
    }
}
