package bpp.service.lv;

import bpp.entity.NestePriceEntity;
import bpp.infrastructure.ContentWebClient;
import bpp.infrastructure.lv.NesteContentWebClient;
import bpp.mapper.NestePriceMapper;
import bpp.model.ErrorModel;
import bpp.model.NestePetrolPriceModel;
import bpp.model.Response;
import bpp.repository.NestePriceRepository;
import bpp.service.PetrolPriceService;
import jakarta.transaction.Transactional;
import java.util.List;
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
public class NestePetrolPriceService implements PetrolPriceService {
    private static final String PETROL_STATION = "Neste";
    private final List<ContentWebClient<?>> contentWebClients;
    private final NestePriceRepository nestePriceRepository;

    @Override
    public void savePetrolPrice() {
        Response<?> nestePetrolPriceResponse = contentWebClients
                .stream()
                .filter(NesteContentWebClient.class::isInstance)
                .map(NesteContentWebClient.class::cast)
                .map(NesteContentWebClient::getContent)
                .findFirst()
                .orElse(null);

        if (nestePetrolPriceResponse == null) {
            log.error(String.format(SERVICE_NOT_FOUND_ERROR, PETROL_STATION));
            return;
        }

        if (nestePetrolPriceResponse.getResponseModel() != null) {
            if (nestePetrolPriceResponse.getResponseModel() instanceof ErrorModel errorModel) {
                log.error(String.format(NOT_FOUND_ERROR, PETROL_STATION, errorModel.getCountry()));
                return;
            }
            NestePetrolPriceModel nestePetrolPriceModel = (NestePetrolPriceModel) nestePetrolPriceResponse.getResponseModel();
            NestePriceEntity nestePriceEntity = NestePriceMapper.toNestePriceEntity(nestePetrolPriceModel);

            nestePriceRepository.save(nestePriceEntity);
            log.info(String.format(NEW_RECORD_INFO, PETROL_STATION, nestePriceEntity.getCountry()));
        }
    }
}
