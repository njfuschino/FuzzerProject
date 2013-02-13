package attackSurface;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import site.Form;
import site.Page;
import site.Site;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;

public class AttackSurfaceDiscoverer {
	private WebClient webClient;
	private String targetURL;
	private String pageGuessFilePath;

	public AttackSurfaceDiscoverer(WebClient webClient, String targetURL, String pageGuessFilePath) {
		this.webClient = webClient;
		this.targetURL = targetURL;
		this.pageGuessFilePath = pageGuessFilePath;
	}
	
	public Site discoverAttackSurface() throws MalformedURLException,
			IOException {
		HtmlPage page = webClient.getPage(targetURL);
		Site site = new Site(page, pageGuessFilePath);
		site.discoverSite();
		return site;
	}

	public void reportAttackSurface(Site site) {
		List<Page> pages = site.getPages();
		for (Page page : pages) {
			System.out.println(page);
		}
		List<Cookie> cookies = site.getCookies();
		for (Cookie cookie : cookies) {
			System.out.println("Cookie:\t" + cookie.getName());
		}
		List<Form> forms = site.getForms();
		for (Form form : forms) {
			System.out.println(form);
		}
	}
}
