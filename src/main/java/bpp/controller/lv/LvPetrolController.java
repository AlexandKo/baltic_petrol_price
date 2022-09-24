package bpp.controller.lv;

import bpp.infrastructure.lv.CircleContentWebClient;
import bpp.infrastructure.lv.GotikaContentWebClient;
import bpp.infrastructure.lv.NesteContentWebClient;
import bpp.infrastructure.lv.ViadaContentWebClient;
import bpp.infrastructure.lv.VirsiContentWebClient;
import bpp.mapper.ViadaPriceMapper;
import bpp.mapper.VirsiPriceMapper;
import bpp.model.CirclePetrolPriceModel;
import bpp.model.ErrorModel;
import bpp.model.GotikaPetrolPriceModel;
import bpp.model.NestePetrolPriceModel;
import bpp.model.PetrolPriceModel;
import bpp.model.Response;
import bpp.model.ViadaPetrolPriceModel;
import bpp.model.VirsiPetrolPriceModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @Operation(description = "Return price list Neste Gas Station", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = NestePetrolPriceModel.class))}),
            @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorModel.class))})
    })
    public ResponseEntity<Object> getNestePrice() {
        Response<?> nesteClientResponse = nesteContentWebClient.getContent();

        if (nesteClientResponse.getResponseModel() instanceof ErrorModel) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(nesteClientResponse.getResponseModel());
        }

        return ResponseEntity
                .ok()
                .body(nesteClientResponse.getResponseModel());
    }

    @GetMapping("/circlek")
    @Operation(description = "Return price list Circle K Gas Station", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CirclePetrolPriceModel.class))}),
            @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorModel.class))})
    })
    public ResponseEntity<Object> getCirclePrice() {
        Response<?> circleClientResponse = circleContentWebClient.getContent();

        if (circleClientResponse.getResponseModel() instanceof ErrorModel) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(circleClientResponse.getResponseModel());
        }

        return ResponseEntity.ok()
                .body(circleClientResponse);
    }

    @GetMapping("/gotika")
    @Operation(description = "Return price list Gotika Gas Station", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GotikaPetrolPriceModel.class))}),
            @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorModel.class))})
    })
    public ResponseEntity<Object> getGotikaPrice() {
        Response<?> gotikaClientResponse = gotikaContentWebClient.getContent();

        if (gotikaClientResponse.getResponseModel() instanceof ErrorModel) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(gotikaClientResponse.getResponseModel());
        }

        return ResponseEntity.ok()
                .body(gotikaClientResponse);
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
