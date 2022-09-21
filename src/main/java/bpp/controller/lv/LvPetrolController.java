package bpp.controller.lv;

import bpp.infrastructure.lv.CircleContentWebClient;
import bpp.infrastructure.lv.GotikaContentWebClient;
import bpp.infrastructure.lv.NesteContentWebClient;
import bpp.infrastructure.lv.ViadaContentWebClient;
import bpp.infrastructure.lv.VirsiContentWebClient;
import bpp.model.PetrolPriceModel;
import javax.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
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
    public Response getNestePrice() {
        PetrolPriceModel petrolPriceModel = nesteContentWebClient.getContent();

        return Response.ok()
                .entity(petrolPriceModel)
                .build();
    }

    @GetMapping("/circlek")
    public Response getCirclePrice() {
        PetrolPriceModel petrolPriceModel = circleContentWebClient.getContent();

        return Response.ok()
                .entity(petrolPriceModel)
                .build();
    }

    @GetMapping("/gotika")
    public Response getGotikaPrice() {
        PetrolPriceModel petrolPriceModel = gotikaContentWebClient.getContent();

        return Response.ok()
                .entity(petrolPriceModel)
                .build();
    }

    @GetMapping("/viada")
    public Response getViadaPrice() {
        PetrolPriceModel petrolPriceModel = viadaContentWebClient.getContent();

        return Response.ok()
                .entity(petrolPriceModel)
                .build();
    }

    @GetMapping("/virsi")
    public Response getVirsiPrice() {
        PetrolPriceModel petrolPriceModel = virsiContentWebClient.getContent();

        return Response.ok()
                .entity(petrolPriceModel)
                .build();
    }
}
