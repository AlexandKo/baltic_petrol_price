package bpp.controller.lv;

import bpp.infrastructure.lv.CircleContentWebClient;
import bpp.infrastructure.lv.GotikaContentWebClient;
import bpp.infrastructure.lv.NesteContentWebClient;
import bpp.infrastructure.lv.ViadaContentWebClient;
import bpp.infrastructure.lv.VirsiContentWebClient;
import bpp.model.PetrolPriceModel;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<PetrolPriceModel> getNestePrice() {
        PetrolPriceModel petrolPriceModel = nesteContentWebClient.getContent();

        return ResponseEntity
                .ok()
                .body(petrolPriceModel);
    }

    @GetMapping("/circlek")
    public ResponseEntity<PetrolPriceModel> getCirclePrice() {
        PetrolPriceModel petrolPriceModel = circleContentWebClient.getContent();

        return ResponseEntity.ok()
                .body(petrolPriceModel);
    }

    @GetMapping("/gotika")
    public ResponseEntity<PetrolPriceModel> getGotikaPrice() {
        PetrolPriceModel petrolPriceModel = gotikaContentWebClient.getContent();

        return ResponseEntity.ok()
                .body(petrolPriceModel);
    }

    @GetMapping("/viada")
    public ResponseEntity<PetrolPriceModel> getViadaPrice() {
        PetrolPriceModel petrolPriceModel = viadaContentWebClient.getContent();

        return ResponseEntity.ok()
                .body(petrolPriceModel);
    }

    @GetMapping("/virsi")
    public ResponseEntity<PetrolPriceModel> getVirsiPrice() {
        PetrolPriceModel petrolPriceModel = virsiContentWebClient.getContent();

        return ResponseEntity.ok()
                .body(petrolPriceModel);
    }
}
