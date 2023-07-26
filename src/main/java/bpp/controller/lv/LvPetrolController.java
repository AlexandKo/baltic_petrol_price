package bpp.controller.lv;

import bpp.infrastructure.lv.*;
import bpp.model.*;
import bpp.service.lv.DailyPriceService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@RequestMapping(path = "/lv/petrol", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class LvPetrolController {
    private static final String DAILY_PRICE_EXAMPLE_OBJECT = "{\"date\":\"2023-07-26\",\"petrolPrice\":[{\"Gotika\":1.587}],\"dieselPrice\":[{\"Gotika\":1.447}]}";
    private final NesteContentWebClient nesteContentWebClient;
    private final CircleContentWebClient circleContentWebClient;
    private final GotikaContentWebClient gotikaContentWebClient;
    private final ViadaContentWebClient viadaContentWebClient;
    private final VirsiContentWebClient virsiContentWebClient;
    private final DailyPriceService dailyPriceService;
    private final ObjectMapper objectMapper;

    @GetMapping("/neste")
    @Operation(description = "Return price list Neste Gas Station Latvia", method = "GET")
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
    @Operation(description = "Return price list Circle K Gas Station Latvia", method = "GET")
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

        return ResponseEntity
                .ok()
                .body(circleClientResponse.getResponseModel());
    }

    @GetMapping("/gotika")
    @Operation(description = "Return price list Gotika Gas Station Latvia", method = "GET")
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

        return ResponseEntity
                .ok()
                .body(gotikaClientResponse.getResponseModel());
    }

    @GetMapping("/viada")
    @Operation(description = "Return price list Viada Gas Station Latvia", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ViadaPetrolPriceModel.class))}),
            @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorModel.class))})
    })
    public ResponseEntity<Object> getViadaPrice() {
        Response<?> viadaClientResponse = viadaContentWebClient.getContent();

        if (viadaClientResponse.getResponseModel() instanceof ErrorModel) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(viadaClientResponse.getResponseModel());
        }

        return ResponseEntity
                .ok()
                .body(viadaClientResponse.getResponseModel());
    }

    @GetMapping("/virsi")
    @Operation(description = "Return price list Virsi Gas Station Latvia", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VirsiPetrolPriceModel.class))}),
            @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorModel.class))})
    })
    public ResponseEntity<Object> getVirsiPrice() {
        Response<?> virsiClientResponse = virsiContentWebClient.getContent();

        if (virsiClientResponse.getResponseModel() instanceof ErrorModel) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(virsiClientResponse.getResponseModel());
        }

        return ResponseEntity
                .ok()
                .body(virsiClientResponse.getResponseModel());
    }

    @GetMapping("/daily")
    @Operation(description = "Return daily best prices list", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = DAILY_PRICE_EXAMPLE_OBJECT))}),
            @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorModel.class))})
    })
    public ResponseEntity<Object> getVBestPrice() {
        DailyPriceModel dailyPriceModel = dailyPriceService.findBestDailyPrice();

        return ResponseEntity.ok(dailyPriceModel);
    }
}
