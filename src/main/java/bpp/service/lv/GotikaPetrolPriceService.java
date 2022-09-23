package bpp.service.lv;

import bpp.entity.GotikaPriceEntity;
import bpp.infrastructure.ContentWebClient;
import bpp.infrastructure.lv.GotikaContentWebClient;
import bpp.mapper.GotikaPriceMapper;
import bpp.model.PetrolPriceModel;
import bpp.repository.GotikaPriceRepository;
import bpp.service.PetrolPriceService;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static bpp.util.MessageCodes.WEB_CLIENT_CONNECTION_SUCCESSFULLY;
import static bpp.util.Messages.NOT_FOUND_ERROR;
import static bpp.util.Messages.NEW_RECORD_INFO;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class GotikaPetrolPriceService implements PetrolPriceService {
    private static final String PETROL_STATION = "Gotika";
    private final List<ContentWebClient<PetrolPriceModel>> contentWebClients;
    private final GotikaPriceRepository gotikaPriceRepository;

    @Override
    public void savePetrolPrice() {
        Optional<PetrolPriceModel> gotikaPetrolPriceModelOptional = contentWebClients
                .stream()
                .filter(GotikaContentWebClient.class::isInstance)
                .map(ContentWebClient::getContent).findFirst();

        gotikaPetrolPriceModelOptional.ifPresent(gotikaPetrolPriceModel -> {
            if (gotikaPetrolPriceModel.getId() == WEB_CLIENT_CONNECTION_SUCCESSFULLY) {
                GotikaPriceEntity gotikaPriceEntity = GotikaPriceMapper.toGotikaPriceEntity(gotikaPetrolPriceModel);
                gotikaPriceRepository.save(gotikaPriceEntity);
                log.info(String.format(NEW_RECORD_INFO, PETROL_STATION, gotikaPriceEntity.getCountry()));
            } else {
                log.error(String.format(NOT_FOUND_ERROR, PETROL_STATION, gotikaPetrolPriceModel.getCountry()));
            }
        });
    }
}
