package site;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;

public class Site {
	private WebClient webClient;

	private URL baseUrl;
	private Page basePage;
	private List<Page> pages;
	private List<Cookie> cookies;
	private List<Form> forms;

	public Site(HtmlPage baseHtmlPage) throws MalformedURLException {
		this.webClient = baseHtmlPage.getWebClient();
		this.basePage = new Page(baseHtmlPage);
		this.baseUrl = baseHtmlPage.getWebResponse().getWebRequest().getUrl();
		this.pages = new ArrayList<Page>();
		this.cookies = new ArrayList<Cookie>();
		this.forms = new ArrayList<Form>();
		pages.add(basePage);
	}

	public void discoverSite() throws MalformedURLException, IOException {
		discoverPage(basePage);
		discoverCookies();
	}

	private void discoverCookies() {
		CookieManager cookieManager = webClient.getCookieManager();
		for (Cookie cookie : cookieManager.getCookies()) {
			cookies.add(cookie);
		}
	}

	private void discoverPage(Page page) throws MalformedURLException,
			IOException {
		page.discoverInputs();
		forms.addAll(page.getForms());
		List<HtmlAnchor> links = page.getHtmlPage().getAnchors();
		List<HtmlPage> linkedPages = new ArrayList<HtmlPage>();
		for (HtmlAnchor link : links) {
			try {
				HtmlPage linkedHtmlPage = (HtmlPage) webClient.getPage(page
						.getHtmlPage().getFullyQualifiedUrl(
								link.getHrefAttribute()));

				if (isInternalLink(linkedHtmlPage)) {
					linkedPages.add(linkedHtmlPage);
				}
			} catch (FailingHttpStatusCodeException e) {
				continue;
			}
		}

		List<Page> nonDuplicateLinkedPages = new ArrayList<Page>();
		for (HtmlPage linkedPage : linkedPages) {
			Page newPage = new Page(linkedPage);
			if (!pages.contains(newPage)
					&& !nonDuplicateLinkedPages.contains(newPage)) {
				nonDuplicateLinkedPages.add(newPage);
			} else {
				for (Page existingPage : pages) {
					if (existingPage.equals(newPage)) {
						existingPage.addArguments(linkedPage);
						break;
					}
				}
			}
		}

		pages.addAll(nonDuplicateLinkedPages);

		for (Page linkedPage : nonDuplicateLinkedPages) {
			discoverPage(linkedPage);
		}
	}

	private boolean isInternalLink(HtmlPage linkedHtmlPage) {
		return baseUrl.getHost().equals(
				linkedHtmlPage.getWebResponse().getWebRequest().getUrl()
						.getHost());
	}

	public List<Page> getPages() {
		return pages;
	}

	public List<Cookie> getCookies() {
		return cookies;
	}

	public List<Form> getForms() {
		return forms;
	}

}
