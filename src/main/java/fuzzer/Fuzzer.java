package fuzzer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;

import site.Form;
import site.Page;
import site.Site;

public class Fuzzer {
	private WebClient webClient;

	public Fuzzer(WebClient webClient) {
		this.webClient = webClient;
	}

	public void fuzz(String targetURL) throws MalformedURLException,
			IOException {
		Site site = discoverAttackSurface(targetURL);
		reportAttackSurface(site);

	}

	private void reportAttackSurface(Site site) {
		List<Page> pages = site.getPages();
		for (Page page : pages) {
			System.out.println(page);
		}
		List<Cookie> cookies = site.getCookies();
		for (Cookie cookie : cookies) {
			System.out.println("Cookie:\t" + cookie.getName() + "\n");
		}
		List<Form> forms = site.getForms();
		for (Form form : forms) {
			System.out.println(form);
		}
	}

	private Site discoverAttackSurface(String siteUrl)
			throws MalformedURLException, IOException {
		HtmlPage page = webClient.getPage(siteUrl);
		Site site = new Site(page);
		site.discoverSite();
		return site;

	}

	public static void main(String[] args) throws MalformedURLException,
			IOException {
		WebClient webClient = new TimeDelayWebClient(0);
		webClient.setJavaScriptEnabled(true);
		Fuzzer fuzzer = new Fuzzer(webClient);
		fuzzer.fuzz("http://127.0.0.1:8080/jpetstore/");
	}

}
