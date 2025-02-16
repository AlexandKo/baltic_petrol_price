package bpp.usecase;

import bpp.entity.CirclePriceEntity;
import bpp.mapper.CirclePriceMapper;
import bpp.model.CirclePetrolPriceModel;
import bpp.model.ErrorModel;
import bpp.model.Response;
import bpp.repository.CirclePriceRepository;
import bpp.util.Country;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static bpp.util.Messages.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class CircleRepositoryUseCase {
    private static final String PETROL_STATION = "Circle K";
    private final CirclePriceRepository circlePriceRepository;

    public void saveToDataBase(Response<?> circlePetrolPriceResponse) {
        if (circlePetrolPriceResponse == null) {
            log.error(String.format(SERVICE_NOT_FOUND_ERROR, PETROL_STATION));
            return;
        }

        if (circlePetrolPriceResponse.responseModel() != null) {
            if (circlePetrolPriceResponse.responseModel() instanceof ErrorModel errorModel) {
                log.error(String.format(NOT_FOUND_ERROR, PETROL_STATION, errorModel.getCountry()));
                return;
            }
            CirclePetrolPriceModel circlePetrolPriceModel = (CirclePetrolPriceModel) circlePetrolPriceResponse.responseModel();
            CirclePriceEntity circlePriceEntity = CirclePriceMapper.toCirclePriceEntity(circlePetrolPriceModel);

            circlePriceRepository.save(circlePriceEntity);
            log.info(String.format(NEW_RECORD_INFO, PETROL_STATION, circlePriceEntity.getCountry()));

            if (circlePetrolPriceModel.getCountry() == Country.SE) {
                CirclePriceEntity circleAutomaticPriceEntity = CirclePriceMapper.toCirclePriceEntity(Country.SE_AUTOMATIC, circlePetrolPriceModel);
                circlePriceRepository.save(circleAutomaticPriceEntity);
                log.info(String.format(NEW_RECORD_INFO, PETROL_STATION, circleAutomaticPriceEntity.getCountry()));
            }
            return;
        }
        log.error(String.format(PARSING_DATA_ERROR, PETROL_STATION));
    }
}
