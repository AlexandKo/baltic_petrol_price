package bpp.infrastructure;

import bpp.model.PetrolPrice;
import bpp.model.WebPageResponse;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;
import javax.annotation.PostConstruct;

import static bpp.util.CodeErrors.WEB_CLIENT_CONNECTION_FAILED;
import static bpp.util.CodeErrors.WEB_CLIENT_CONNECTION_SUCCESSFULLY;
import static bpp.util.Messages.CONNECTION_ERROR;

abstract class WebClient {
    private com.gargoylesoftware.htmlunit.WebClient petrolWebClient;

    @PostConstruct
    private void init() {
        petrolWebClient = new com.gargoylesoftware.htmlunit.WebClient(BrowserVersion.BEST_SUPPORTED);
        petrolWebClient.getOptions().setCssEnabled(false);
        petrolWebClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        petrolWebClient.getOptions().setThrowExceptionOnScriptError(false);
        petrolWebClient.getOptions().setPrintContentOnFailingStatusCode(false);
        petrolWebClient.getOptions().setJavaScriptEnabled(false);
    }

    protected WebPageResponse getWebContent(String url) {
        HtmlPage htmlPage;
        try {
            htmlPage = petrolWebClient.getPage(url);
            return WebPageResponse.builder()
                    .id(WEB_CLIENT_CONNECTION_SUCCESSFULLY)
                    .content(htmlPage.asNormalizedText())
                    .build();
        } catch (IOException e) {
            return WebPageResponse.builder()
                    .id(WEB_CLIENT_CONNECTION_FAILED)
                    .content(String.format(CONNECTION_ERROR, url))
                    .build();
        }
    }

    protected PetrolPrice createFailedPetrolPrice(int id, String message) {
        return PetrolPrice.builder()
                .id(id)
                .errorMessage(message)
                .build();
    }

    public abstract PetrolPrice getContent();
}
