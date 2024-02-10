package bpp.controller.se;

import bpp.controller.StatisticsBase;
import bpp.entity.CirclePriceEntity;
import bpp.model.ErrorModel;
import bpp.model.StationStatisticModel;
import bpp.service.statistics.CircleStatisticService;
import bpp.util.Country;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/se/petrol/statistic")
@RequiredArgsConstructor
public class SeStatisticController extends StatisticsBase {
    private final CircleStatisticService circleStatisticService;

    @GetMapping("/circlek")
    @Operation(description = "Return weekly statistics for CircleK Gas Station Sweden", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return statistic chart png format", content = {@Content(mediaType = MediaType.IMAGE_PNG_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Not found data for statistic", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorModel.class))})
    })
    public ResponseEntity<Object> getCircleKStatistic() {
        return createStatisticsResponse(circleStatisticService.getWeeklyStatisticChart(Country.SE));
    }

    @GetMapping("/circlek/month")
    @Operation(description = "Return month data for CircleK Gas Station Sweden", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return month data", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Not found data for statistic", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorModel.class))})
    })
    public ResponseEntity<Object> circlekStatisticWeeklyData(@RequestParam Country type) {
        List<CirclePriceEntity> circlekStatisticWeeklyData = circleStatisticService.getStatisticDataPerMonth(type);

        StationStatisticModel<List<CirclePriceEntity>> stationStatisticModel = new StationStatisticModel<>(200, circlekStatisticWeeklyData);

        return ResponseEntity.ok().body(stationStatisticModel);
    }
}
