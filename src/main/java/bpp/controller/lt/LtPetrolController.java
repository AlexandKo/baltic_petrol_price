package bpp.controller.lt;

import bpp.infrastructure.lt.LtCircleContentWebClient;
import bpp.model.CirclePetrolPriceModel;
import bpp.model.ErrorModel;
import bpp.model.Response;
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
@RequestMapping(path = "/lt/petrol", produces = "application/json")
@RequiredArgsConstructor
public class LtPetrolController {
    private final LtCircleContentWebClient ltCircleContentWebClient;

    @GetMapping("/circlek")
    @Operation(description = "Return price list Circle K Gas Station Lithuania", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CirclePetrolPriceModel.class))}),
            @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorModel.class))})
    })
    public ResponseEntity<Object> getCirclePrice() {
        Response<?> circleClientResponse = ltCircleContentWebClient.getContent();

        if (circleClientResponse.responseModel() instanceof ErrorModel) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(circleClientResponse.responseModel());
        }

        return ResponseEntity
                .ok()
                .body(circleClientResponse.responseModel());
    }
}
