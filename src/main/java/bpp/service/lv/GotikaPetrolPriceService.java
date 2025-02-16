package bpp.service.lv;

import bpp.entity.GotikaPriceEntity;
import bpp.infrastructure.lv.GotikaContentWebClient;
import bpp.mapper.GotikaPriceMapper;
import bpp.model.ErrorModel;
import bpp.model.GotikaPetrolPriceModel;
import bpp.model.Response;
import bpp.repository.GotikaPriceRepository;
import bpp.service.PetrolPriceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static bpp.util.Messages.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class GotikaPetrolPriceService implements PetrolPriceService {
    private static final String PETROL_STATION = "Gotika";
    private final GotikaContentWebClient contentWebClients;
    private final GotikaPriceRepository gotikaPriceRepository;

    @Override
    public void savePetrolPrice() {
        Response<?> gotikaPetrolPriceResponse = contentWebClients.getContent();

        if (gotikaPetrolPriceResponse == null) {
            log.error(String.format(SERVICE_NOT_FOUND_ERROR, PETROL_STATION));
            return;
        }

        if (gotikaPetrolPriceResponse.responseModel() != null) {
            if (gotikaPetrolPriceResponse.responseModel() instanceof ErrorModel errorModel) {
                log.error(String.format(NOT_FOUND_ERROR, PETROL_STATION, errorModel.getCountry()));
                return;
            }
            GotikaPetrolPriceModel gotikaPetrolPriceModel = (GotikaPetrolPriceModel) gotikaPetrolPriceResponse.responseModel();
            GotikaPriceEntity gotikaPriceEntity = GotikaPriceMapper.toGotikaPriceEntity(gotikaPetrolPriceModel);

            gotikaPriceRepository.save(gotikaPriceEntity);
            log.info(String.format(NEW_RECORD_INFO, PETROL_STATION, gotikaPriceEntity.getCountry()));
            return;
        }
        log.error(String.format(PARSING_DATA_ERROR, PETROL_STATION));
    }
}
