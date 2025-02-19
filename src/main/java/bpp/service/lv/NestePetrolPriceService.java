package bpp.service.lv;

import bpp.entity.NestePriceEntity;
import bpp.infrastructure.lv.NesteContentWebClient;
import bpp.mapper.NestePriceMapper;
import bpp.model.ErrorModel;
import bpp.model.NestePetrolPriceModel;
import bpp.model.Response;
import bpp.repository.NestePriceRepository;
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
public class NestePetrolPriceService implements PetrolPriceService {
    private static final String PETROL_STATION = "Neste";
    private final NesteContentWebClient contentWebClients;
    private final NestePriceRepository nestePriceRepository;

    @Override
    public void savePetrolPrice() {
        Response<?> nestePetrolPriceResponse = contentWebClients.getContent();

        if (nestePetrolPriceResponse == null) {
            log.error(String.format(SERVICE_NOT_FOUND_ERROR, PETROL_STATION));
            return;
        }

        if (nestePetrolPriceResponse.responseModel() != null) {
            if (nestePetrolPriceResponse.responseModel() instanceof ErrorModel errorModel) {
                log.error(String.format(NOT_FOUND_ERROR, PETROL_STATION, errorModel.getCountry()));
                return;
            }
            NestePetrolPriceModel nestePetrolPriceModel = (NestePetrolPriceModel) nestePetrolPriceResponse.responseModel();
            NestePriceEntity nestePriceEntity = NestePriceMapper.toNestePriceEntity(nestePetrolPriceModel);

            nestePriceRepository.save(nestePriceEntity);
            log.info(String.format(NEW_RECORD_INFO, PETROL_STATION, nestePriceEntity.getCountry()));
            return;
        }
        log.error(String.format(PARSING_DATA_ERROR, PETROL_STATION));
    }
}
