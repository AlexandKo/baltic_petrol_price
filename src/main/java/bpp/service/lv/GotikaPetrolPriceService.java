package bpp.service.lv;

import bpp.entity.GotikaPriceEntity;
import bpp.infrastructure.ContentWebClient;
import bpp.infrastructure.lv.GotikaContentWebClient;
import bpp.mapper.GotikaPriceMapper;
import bpp.model.ErrorModel;
import bpp.model.GotikaPetrolPriceModel;
import bpp.model.Response;
import bpp.repository.GotikaPriceRepository;
import bpp.service.PetrolPriceService;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static bpp.util.Messages.NEW_RECORD_INFO;
import static bpp.util.Messages.NOT_FOUND_ERROR;
import static bpp.util.Messages.SERVICE_NOT_FOUND_ERROR;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class GotikaPetrolPriceService implements PetrolPriceService {
    private static final String PETROL_STATION = "Gotika";
    private final List<ContentWebClient<?>> contentWebClients;
    private final GotikaPriceRepository gotikaPriceRepository;

    @Override
    public void savePetrolPrice() {
        Response<?> gotikaPetrolPriceResponse = contentWebClients
                .stream()
                .filter(GotikaContentWebClient.class::isInstance)
                .map(GotikaContentWebClient.class::cast)
                .map(GotikaContentWebClient::getContent)
                .findFirst()
                .orElse(null);

        if (gotikaPetrolPriceResponse == null) {
            log.error(String.format(SERVICE_NOT_FOUND_ERROR, PETROL_STATION));
            return;
        }

        if (gotikaPetrolPriceResponse.getResponseModel() != null) {
            if (gotikaPetrolPriceResponse.getResponseModel() instanceof ErrorModel) {
                ErrorModel errorModel = (ErrorModel) gotikaPetrolPriceResponse.getResponseModel();
                log.error(String.format(NOT_FOUND_ERROR, PETROL_STATION, errorModel.getCountry()));
                return;
            }
            GotikaPetrolPriceModel gotikaPetrolPriceModel = (GotikaPetrolPriceModel) gotikaPetrolPriceResponse.getResponseModel();
            GotikaPriceEntity nestePriceEntity = GotikaPriceMapper.toGotikaPriceEntity(gotikaPetrolPriceModel);

            gotikaPriceRepository.save(nestePriceEntity);
            log.info(String.format(NEW_RECORD_INFO, PETROL_STATION, nestePriceEntity.getCountry()));
        }
    }
}
