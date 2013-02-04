import java.io.IOException;
import java.net.MalformedURLException;

import site.Site;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Fuzzer {

	public static void main(String[] args)
			throws FailingHttpStatusCodeException, MalformedURLException,
			IOException {
		WebClient webClient = new WebClient();
		webClient.setJavaScriptEnabled(true);
		Fuzzer fuzzer = new Fuzzer(webClient);
		fuzzer.fuzz("http://127.0.0.1:8080/bodgeit/");
	}

	private WebClient webClient;

	public Fuzzer(WebClient webClient) {
		this.webClient = webClient;
	}

	public void fuzz(String targetURL) throws FailingHttpStatusCodeException,
			MalformedURLException, IOException {
		Site site = discoverAttackSurface(targetURL);

	}

	private Site discoverAttackSurface(String siteURL)
			throws FailingHttpStatusCodeException, MalformedURLException,
			IOException {
		HtmlPage page = (HtmlPage) webClient.getPage(siteURL);
		Site site = new Site(page);
		site.discoverSite();
		return site;

	}

}
