package bpp.service.lv;

import bpp.entity.CirclePriceEntity;
import bpp.infrastructure.ContentWebClient;
import bpp.infrastructure.lv.CircleContentWebClient;
import bpp.mapper.CirclePriceMapper;
import bpp.model.CirclePetrolPriceModel;
import bpp.model.ErrorModel;
import bpp.model.Response;
import bpp.repository.CirclePriceRepository;
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
public class CirclePetrolPriceService implements PetrolPriceService {
    private static final String PETROL_STATION = "Circle K";
    private final List<ContentWebClient<?>> contentWebClients;
    private final CirclePriceRepository circlePriceRepository;

    @Override
    public void savePetrolPrice() {
        Response<?> circlePetrolPriceResponse = contentWebClients
                .stream()
                .filter(CircleContentWebClient.class::isInstance)
                .map(CircleContentWebClient.class::cast)
                .map(CircleContentWebClient::getContent)
                .findFirst()
                .orElse(null);

        if (circlePetrolPriceResponse == null) {
            log.error(String.format(SERVICE_NOT_FOUND_ERROR, PETROL_STATION));
            return;
        }

        if (circlePetrolPriceResponse.getResponseModel() instanceof ErrorModel) {
            ErrorModel errorModel = (ErrorModel) circlePetrolPriceResponse.getResponseModel();
            log.error(String.format(NOT_FOUND_ERROR, PETROL_STATION, errorModel.getCountry()));
            return;
        }

        CirclePetrolPriceModel circlePetrolPriceModel = (CirclePetrolPriceModel) circlePetrolPriceResponse.getResponseModel();
        CirclePriceEntity circlePriceEntity = CirclePriceMapper.toCirclePriceEntity(circlePetrolPriceModel);

        circlePriceRepository.save(circlePriceEntity);
        log.info(String.format(NEW_RECORD_INFO, PETROL_STATION, circlePriceEntity.getCountry()));
    }
}
