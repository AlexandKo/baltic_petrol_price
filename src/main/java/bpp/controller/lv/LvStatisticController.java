package bpp.controller.lv;

import bpp.controller.StatisticsBaseController;
import bpp.model.ErrorModel;
import bpp.service.lv.statistic.CircleStatisticService;
import bpp.service.lv.statistic.GotikaStatisticService;
import bpp.service.lv.statistic.NesteStatisticService;
import bpp.service.lv.statistic.ViadaStatisticService;
import bpp.service.lv.statistic.VirsiStatisticService;
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
@RequestMapping(path = "/lv/petrol/statistic")
@RequiredArgsConstructor
public class LvStatisticController extends StatisticsBaseController {
    private final NesteStatisticService nesteStatistic;
    private final GotikaStatisticService gotikaStatisticService;
    private final VirsiStatisticService virsiStatisticService;
    private final CircleStatisticService circleStatisticService;
    private final ViadaStatisticService viadaStatisticService;

    @GetMapping("/neste")
    @Operation(description = "Return weekly statistics for Neste Gas Station Latvia", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return statistic chart png format", content = {@Content(mediaType = MediaType.IMAGE_PNG_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Not found data for statistic", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorModel.class))})
    })
    public ResponseEntity<Object> getNesteStatistic() {
        return createStatisticsResponse(nesteStatistic.getWeeklyStatisticChart());
    }

    @GetMapping("/gotika")
    @Operation(description = "Return weekly statistics for Gotika Gas Station Latvia", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return statistic chart png format", content = {@Content(mediaType = MediaType.IMAGE_PNG_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Not found data for statistic", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorModel.class))})
    })
    public ResponseEntity<Object> getGotikaStatistic() {
        return createStatisticsResponse(gotikaStatisticService.getWeeklyStatisticChart());
    }

    @GetMapping("/virsi")
    @Operation(description = "Return weekly statistics for Virshi Gas Station Latvia", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return statistic chart png format", content = {@Content(mediaType = MediaType.IMAGE_PNG_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Not found data for statistic", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorModel.class))})
    })
    public ResponseEntity<Object> getVirsiStatistic() {
        return createStatisticsResponse(virsiStatisticService.getWeeklyStatisticChart());
    }

    @GetMapping("/circlek")
    @Operation(description = "Return weekly statistics for CircleK Gas Station Latvia", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return statistic chart png format", content = {@Content(mediaType = MediaType.IMAGE_PNG_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Not found data for statistic", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorModel.class))})
    })
    public ResponseEntity<Object> getCircleKStatistic() {
        return createStatisticsResponse(circleStatisticService.getWeeklyStatisticChart(Country.LV));
    }

    @GetMapping("/viada")
    @Operation(description = "Return weekly statistics for Viada Gas Station Latvia", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return statistic chart png format", content = {@Content(mediaType = MediaType.IMAGE_PNG_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Not found data for statistic", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorModel.class))})
    })
    public ResponseEntity<Object> getViadaStatistic() {
        return createStatisticsResponse(viadaStatisticService.getWeeklyStatisticChart());
    }
}
