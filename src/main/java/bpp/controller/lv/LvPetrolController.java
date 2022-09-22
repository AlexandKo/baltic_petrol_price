package bpp.controller.lv;

import bpp.infrastructure.lv.CircleContentWebClient;
import bpp.infrastructure.lv.GotikaContentWebClient;
import bpp.infrastructure.lv.NesteContentWebClient;
import bpp.infrastructure.lv.ViadaContentWebClient;
import bpp.infrastructure.lv.VirsiContentWebClient;
import bpp.mapper.CirclePriceMapper;
import bpp.mapper.GotikaPriceMapper;
import bpp.mapper.NestePriceMapper;
import bpp.mapper.ViadaPriceMapper;
import bpp.mapper.VirsiPriceMapper;
import bpp.model.CirclePetrolPriceModel;
import bpp.model.GotikaPetrolPriceModel;
import bpp.model.NestePetrolPriceModel;
import bpp.model.PetrolPriceModel;
import bpp.model.ViadaPetrolPriceModel;
import bpp.model.VirsiPetrolPriceModel;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/lv/petrol", produces = "application/json")
@RequiredArgsConstructor
public class LvPetrolController {
    private final NesteContentWebClient nesteContentWebClient;
    private final CircleContentWebClient circleContentWebClient;
    private final GotikaContentWebClient gotikaContentWebClient;
    private final ViadaContentWebClient viadaContentWebClient;
    private final VirsiContentWebClient virsiContentWebClient;

    @GetMapping("/neste")
    @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = NestePetrolPriceModel.class))})
    public ResponseEntity<NestePetrolPriceModel> getNestePrice() {
        PetrolPriceModel petrolPriceModel = nesteContentWebClient.getContent();

        NestePetrolPriceModel nestePetrolPriceModel = NestePriceMapper.toNestePetrolPriceModel(petrolPriceModel);

        return ResponseEntity
                .ok()
                .body(nestePetrolPriceModel);
    }

    @GetMapping("/circlek")
    public ResponseEntity<CirclePetrolPriceModel> getCirclePrice() {
        PetrolPriceModel petrolPriceModel = circleContentWebClient.getContent();

        CirclePetrolPriceModel circlePetrolPriceModel = CirclePriceMapper.toCirclePetrolPriceModel(petrolPriceModel);

        return ResponseEntity.ok()
                .body(circlePetrolPriceModel);
    }

    @GetMapping("/gotika")
    public ResponseEntity<GotikaPetrolPriceModel> getGotikaPrice() {
        PetrolPriceModel petrolPriceModel = gotikaContentWebClient.getContent();

        GotikaPetrolPriceModel gotikaPetrolPriceModel = GotikaPriceMapper.toGotikaPetrolPriceModel(petrolPriceModel);

        return ResponseEntity.ok()
                .body(gotikaPetrolPriceModel);
    }

    @GetMapping("/viada")
    public ResponseEntity<ViadaPetrolPriceModel> getViadaPrice() {
        PetrolPriceModel petrolPriceModel = viadaContentWebClient.getContent();

        ViadaPetrolPriceModel viadaPetrolPriceModel = ViadaPriceMapper.toViadaPetrolPriceModel(petrolPriceModel);

        return ResponseEntity.ok()
                .body(viadaPetrolPriceModel);
    }

    @GetMapping("/virsi")
    public ResponseEntity<VirsiPetrolPriceModel> getVirsiPrice() {
        PetrolPriceModel petrolPriceModel = virsiContentWebClient.getContent();

        VirsiPetrolPriceModel virsiPetrolPriceModel = VirsiPriceMapper.toVirsiPetrolPriceModel(petrolPriceModel);

        return ResponseEntity.ok()
                .body(virsiPetrolPriceModel);
    }
}
