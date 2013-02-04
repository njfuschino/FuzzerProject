package site;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Site {
	private WebClient webClient;

	private URL baseUrl;
	private Page basePage;
	private List<Page> pages;

	public Site(HtmlPage baseHtmlPage) {
		this.webClient = baseHtmlPage.getWebClient();
		this.basePage = new Page(baseHtmlPage);
		this.baseUrl = baseHtmlPage.getWebResponse().getUrl();
		this.pages = new ArrayList<Page>();
		pages.add(basePage);
	}

	public void discoverSite() throws FailingHttpStatusCodeException,
			MalformedURLException, IOException {
		discoverPage(basePage);
	}

	private void discoverPage(Page page) throws FailingHttpStatusCodeException,
			MalformedURLException, IOException {
		page.discoverInputs();
		List<HtmlAnchor> links = page.getAnchors();
		List<HtmlPage> linkedPages = new ArrayList<HtmlPage>();
		for (HtmlAnchor link : links) {
			HtmlPage linkedHtmlPage = (HtmlPage) webClient.getPage(page
					.getHtmlPage()
					.getFullyQualifiedUrl(link.getHrefAttribute()));
			if (baseUrl.getHost().equals(
					linkedHtmlPage.getWebResponse().getUrl().getHost())) {
				linkedPages.add(linkedHtmlPage);
			}
		}

		List<Page> nonDuplicateLinkedPages = new ArrayList<Page>();
		for (HtmlPage linkedPage : linkedPages) {
			Page newPage = new Page(linkedPage);
			if (!pages.contains(newPage)) {
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

	public List<Page> getPages() {
		return pages;
	}

}
