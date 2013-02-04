package fuzzer;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import site.Page;
import site.Site;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Fuzzer {
	private WebClient webClient;

	public Fuzzer(WebClient webClient) {
		this.webClient = webClient;
	}

	public void fuzz(String targetURL) throws FailingHttpStatusCodeException,
			MalformedURLException, IOException {
		Site site = discoverAttackSurface(targetURL);
		reportAttackSurface(site);

	}

	private void reportAttackSurface(Site site) {
		List<Page> pages = site.getPages();
		for(Page page : pages) {
			System.out.println(page);
		}
	}

	private Site discoverAttackSurface(String siteUrl)
			throws FailingHttpStatusCodeException, MalformedURLException,
			IOException {
		HtmlPage page = (HtmlPage) webClient.getPage(siteUrl);
		Site site = new Site(page);
		site.discoverSite();
		return site;

	}
	
	public static void main(String[] args)
			throws FailingHttpStatusCodeException, MalformedURLException,
			IOException {
		WebClient webClient = new WebClient();
		webClient.setJavaScriptEnabled(true);
		Fuzzer fuzzer = new Fuzzer(webClient);
		fuzzer.fuzz("http://127.0.0.1:8080/bodgeit/");
	}

}
