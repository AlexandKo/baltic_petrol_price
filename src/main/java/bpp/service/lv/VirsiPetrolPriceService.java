package bpp.service.lv;

import bpp.entity.VirsiPriceEntity;
import bpp.infrastructure.ContentWebClient;
import bpp.infrastructure.lv.VirsiContentWebClient;
import bpp.mapper.VirsiPriceMapper;
import bpp.model.ErrorModel;
import bpp.model.Response;
import bpp.model.VirsiPetrolPriceModel;
import bpp.repository.VirsiPriceRepository;
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
public class VirsiPetrolPriceService implements PetrolPriceService {
    private static final String PETROL_STATION = "Virsi";
    private final List<ContentWebClient<?>> contentWebClients;
    private final VirsiPriceRepository virsiPriceRepository;

    @Override
    public void savePetrolPrice() {
        Response<?> virsiPetrolPriceResponse = contentWebClients
                .stream()
                .filter(VirsiContentWebClient.class::isInstance)
                .map(VirsiContentWebClient.class::cast)
                .map(VirsiContentWebClient::getContent)
                .findFirst()
                .orElse(null);

        if (virsiPetrolPriceResponse == null) {
            log.error(String.format(SERVICE_NOT_FOUND_ERROR, PETROL_STATION));
            return;
        }

        if (virsiPetrolPriceResponse.getResponseModel() != null) {
            if (virsiPetrolPriceResponse.getResponseModel() instanceof ErrorModel) {
                ErrorModel errorModel = (ErrorModel) virsiPetrolPriceResponse.getResponseModel();
                log.error(String.format(NOT_FOUND_ERROR, PETROL_STATION, errorModel.getCountry()));
                return;
            }
            VirsiPetrolPriceModel nestePetrolPriceModel = (VirsiPetrolPriceModel) virsiPetrolPriceResponse.getResponseModel();
            VirsiPriceEntity virsiPriceEntity = VirsiPriceMapper.toVirsiPriceEntity(nestePetrolPriceModel);

            virsiPriceRepository.save(virsiPriceEntity);
            log.info(String.format(NEW_RECORD_INFO, PETROL_STATION, virsiPriceEntity.getCountry()));
        }
    }
}
