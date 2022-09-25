package bpp.infrastructure;

import bpp.model.WebPageResponseModel;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.annotation.PostConstruct;

import static bpp.util.MessageCodes.WEB_CLIENT_CONNECTION_FAILED;
import static bpp.util.MessageCodes.WEB_CLIENT_CONNECTION_SUCCESSFULLY;
import static bpp.util.Messages.CONNECTION_ERROR;

public abstract class ContentWebClient<T> {
    private com.gargoylesoftware.htmlunit.WebClient petrolWebClient;

    @PostConstruct
    private void init() {
        petrolWebClient = new com.gargoylesoftware.htmlunit.WebClient(BrowserVersion.BEST_SUPPORTED);
        petrolWebClient.getOptions().setCssEnabled(false);
        petrolWebClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        petrolWebClient.getOptions().setThrowExceptionOnScriptError(false);
        petrolWebClient.getOptions().setPrintContentOnFailingStatusCode(false);
        petrolWebClient.getOptions().setJavaScriptEnabled(false);
        petrolWebClient.getOptions().setUseInsecureSSL(true);
    }

    public WebPageResponseModel getWebContent(String url) {
        HtmlPage htmlPage;
        try {
            htmlPage = petrolWebClient.getPage(url);
            return WebPageResponseModel.builder()
                    .id(WEB_CLIENT_CONNECTION_SUCCESSFULLY)
                    .content(htmlPage.asNormalizedText())
                    .build();
        } catch (IOException e) {
            return WebPageResponseModel.builder()
                    .id(WEB_CLIENT_CONNECTION_FAILED)
                    .content(String.format(CONNECTION_ERROR, url))
                    .build();
        }
    }

    protected BigDecimal createPriceFromString(String price) {
        if (price.contains(".")) {
            return new BigDecimal(price);
        }
        return new BigDecimal(price).divide(new BigDecimal("1000"), 3, RoundingMode.DOWN);
    }

    protected BigDecimal createPriceWithDecimalPoint(String price) {
        String changedPriceFormat = price.replace(",", ".");
        return new BigDecimal(changedPriceFormat);
    }

    public abstract T getContent();
}
