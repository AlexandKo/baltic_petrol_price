package bpp.service.lv;

import bpp.entity.ViadaPriceEntity;
import bpp.infrastructure.ContentWebClient;
import bpp.infrastructure.lv.ViadaContentWebClient;
import bpp.mapper.ViadaPriceMapper;
import bpp.model.ErrorModel;
import bpp.model.Response;
import bpp.model.ViadaPetrolPriceModel;
import bpp.repository.ViadaPriceRepository;
import bpp.service.PetrolPriceService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static bpp.util.Messages.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ViadaPetrolPriceService implements PetrolPriceService {
    private static final String PETROL_STATION = "Viada";
    private final List<ContentWebClient<?>> contentWebClients;
    private final ViadaPriceRepository viadaPriceRepository;

    @Override
    public void savePetrolPrice() {
        Response<?> viadaPetrolPriceResponse = contentWebClients
                .stream()
                .filter(ViadaContentWebClient.class::isInstance)
                .map(ViadaContentWebClient.class::cast)
                .map(ViadaContentWebClient::getContent)
                .findFirst()
                .orElse(null);

        if (viadaPetrolPriceResponse == null) {
            log.error(String.format(SERVICE_NOT_FOUND_ERROR, PETROL_STATION));
            return;
        }

        if (viadaPetrolPriceResponse.getResponseModel() != null) {
            if (viadaPetrolPriceResponse.getResponseModel() instanceof ErrorModel errorModel) {
                log.error(String.format(NOT_FOUND_ERROR, PETROL_STATION, errorModel.getCountry()));
                return;
            }
            ViadaPetrolPriceModel viadaPetrolPriceModel = (ViadaPetrolPriceModel) viadaPetrolPriceResponse.getResponseModel();
            ViadaPriceEntity nestePriceEntity = ViadaPriceMapper.toViadaPriceEntity(viadaPetrolPriceModel);

            viadaPriceRepository.save(nestePriceEntity);
            log.info(String.format(NEW_RECORD_INFO, PETROL_STATION, nestePriceEntity.getCountry()));
            return;
        }
        log.error(String.format(PARSING_DATA_ERROR, PETROL_STATION));
    }
}
