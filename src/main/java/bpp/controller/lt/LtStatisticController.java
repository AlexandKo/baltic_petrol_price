package bpp.controller.lt;

import bpp.controller.StatisticsBaseController;
import bpp.model.ErrorModel;
import bpp.service.lv.statistic.CircleStatisticService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/lt/petrol/statistic")
@RequiredArgsConstructor
public class LtStatisticController extends StatisticsBaseController {
    private final CircleStatisticService circleStatisticService;

    @GetMapping("/circlek")
    @Operation(description = "Return weekly statistics for CircleK Gas Station Lithuania", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return statistic chart png format", content = {@Content(mediaType = MediaType.IMAGE_PNG_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Not found data for statistic", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorModel.class))})
    })
    public ResponseEntity<Object> getCircleKStatistic() {
        return createStatisticsResponse(circleStatisticService.getWeeklyStatisticChart(Country.LT));
    }
}
