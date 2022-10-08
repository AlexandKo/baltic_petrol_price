package bpp.service.lv.statistic;

import bpp.exception.StatisticChartException;
import bpp.model.ChartCategoryModel;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.imageio.ImageIO;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.CategorySeries;
import org.springframework.stereotype.Component;

@Component
public class PetrolChart {
    private static final String X_AXIS_NAME = "Date";
    private static final String Y_AXIS_NAME = "Price, EUR";
    private static final String CHART_FILE_FORMAT = "png";
    private static final String CREATE_CHART_CREATION_ERROR = "Error during created a petrol station statistics";
    private static final double MAX_PRICE_Y_AXIS = 5d;
    private CategoryChart petrolCategoryChart;

    private void createNewChart() {
        petrolCategoryChart = new CategoryChartBuilder()
                .xAxisTitle(X_AXIS_NAME)
                .yAxisTitle(Y_AXIS_NAME)
                .build();
        petrolCategoryChart.getStyler().setYAxisMax(MAX_PRICE_Y_AXIS);
        petrolCategoryChart.setTitle("Petrol station statistic");
        petrolCategoryChart.getStyler().setDefaultSeriesRenderStyle(CategorySeries.CategorySeriesRenderStyle.Bar);
        petrolCategoryChart.getStyler().setLabelsVisible(true);
    }

    public byte[] createPetrolChart(List<ChartCategoryModel> chartCategoryModelList, List<String> date) {
        try {
            createNewChart();
            chartCategoryModelList.forEach(i -> petrolCategoryChart.addSeries(i.getCategoryName(), date, i.getCategoryValue()));

            byte[] bytes = BitmapEncoder.getBitmapBytes(petrolCategoryChart, BitmapEncoder.BitmapFormat.PNG);

            InputStream inputStream = new ByteArrayInputStream(bytes);
            BufferedImage statisticChartBufferedImage = ImageIO.read(inputStream);

            ByteArrayOutputStream statisticChartOutputStream = new ByteArrayOutputStream();
            ImageIO.write(statisticChartBufferedImage, CHART_FILE_FORMAT, statisticChartOutputStream);

            return statisticChartOutputStream.toByteArray();
        } catch (IOException e) {
            throw new StatisticChartException(CREATE_CHART_CREATION_ERROR, e);
        } catch (IllegalArgumentException e) {
            return new byte[0];
        }
    }
}
