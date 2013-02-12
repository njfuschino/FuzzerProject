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
	private String targetURL;

	public Fuzzer(WebClient webClient, String targetURL) {
		this.targetURL = targetURL;
		this.webClient = webClient;
	}

	public void fuzz() throws MalformedURLException,
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

	/**
	 * @param args
	 *     0- target url
	 *     1- time delay (can be 0)
	 */
	public static void main(String[] args) throws MalformedURLException,
			IOException {
		FuzzerFactory fuzzerBuilder = new FuzzerFactory();
		Fuzzer fuzzer = fuzzerBuilder.getFuzzer(args);

		fuzzer.fuzz();
	}

}
