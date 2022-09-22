package bpp.service.lv;

import bpp.entity.VirsiPriceEntity;
import bpp.infrastructure.ContentWebClient;
import bpp.infrastructure.lv.VirsiContentWebClient;
import bpp.mapper.VirsiPriceMapper;
import bpp.model.PetrolPriceModel;
import bpp.repository.VirsiPriceRepository;
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
public class VirsiPetrolPriceService implements PetrolPriceService {
    private static final String PETROL_STATION = "Virsi";
    private final List<ContentWebClient> contentWebClients;
    private final VirsiPriceRepository virsiPriceRepository;

    @Override
    public void savePetrolPrice() {
        Optional<PetrolPriceModel> virsiPetrolPriceModelOptional = contentWebClients
                .stream()
                .filter(VirsiContentWebClient.class::isInstance)
                .map(ContentWebClient::getContent).findFirst();

        virsiPetrolPriceModelOptional.ifPresent(virsiPetrolPriceModel -> {
            if (virsiPetrolPriceModel.getId() == WEB_CLIENT_CONNECTION_SUCCESSFULLY) {
                VirsiPriceEntity virsiPriceEntity = VirsiPriceMapper.toVirsiPriceEntity(virsiPetrolPriceModel);
                virsiPriceRepository.save(virsiPriceEntity);
                log.info(String.format(NEW_RECORD_INFO, PETROL_STATION, virsiPriceEntity.getCountry()));
            } else {
                log.error(String.format(NEW_RECORD_ERROR, PETROL_STATION, virsiPetrolPriceModel.getCountry()));
            }
        });
    }
}
