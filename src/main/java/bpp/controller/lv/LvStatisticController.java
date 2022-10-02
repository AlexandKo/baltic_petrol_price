package bpp.controller.lv;

import bpp.model.ErrorModel;
import bpp.service.lv.statistic.NesteStatisticService;
import bpp.util.Country;
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
@RequestMapping(path = "/lv/petrol/statistic")
@RequiredArgsConstructor
public class LvStatisticController {
    private static final int EMPTY_ARRAY = 0;
    private static final int RESPONSE_CODE = 204;
    private static final String CREATE_CHART_CREATION_ERROR = "Error during created a Neste petrol station statistics";
    private final NesteStatisticService nesteStatistic;

    @GetMapping("/neste")
    @Operation(description = "Return monthly statistics for Neste Gas Station Latvia", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return statistic chart png format", content = {@Content(mediaType = MediaType.IMAGE_PNG_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Not found data for statistic", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorModel.class))})
    })
    public ResponseEntity<Object> getNesteStatistic() {
        byte[] weeklyStatisticChart = nesteStatistic.getWeeklyStatisticChart();

        if (isStatisticChartEmpty(weeklyStatisticChart)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ErrorModel.builder()
                            .id(RESPONSE_CODE)
                            .country(Country.LV)
                            .errorMessage(CREATE_CHART_CREATION_ERROR)
                            .build());
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_PNG)
                .body(weeklyStatisticChart);
    }

    private boolean isStatisticChartEmpty(byte[] weeklyStatisticChart) {
        return weeklyStatisticChart.length == EMPTY_ARRAY;
    }
}
