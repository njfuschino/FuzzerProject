package site;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
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
		this.pages = new LinkedList<Page>();
		pages.add(basePage);
	}

	public void discoverSite() throws FailingHttpStatusCodeException,
			MalformedURLException, IOException {
		discoverPage(basePage);
	}

	private void discoverPage(Page page)
			throws FailingHttpStatusCodeException, MalformedURLException,
			IOException {
		page.discoverInputs();
		List<HtmlAnchor> links = page.getAnchors();
		List<HtmlPage> linkedPages = new LinkedList<HtmlPage>();
		for (HtmlAnchor link : links) {
			HtmlPage linkedHtmlPage = (HtmlPage) webClient.getPage(link.getHrefAttribute());
			if (isNotExternalLink(linkedHtmlPage)) {
				linkedPages.add(linkedHtmlPage);
			}
		}
		
		List<Page> nonDuplicateLinkedPages = new LinkedList<Page>();
		for (HtmlPage linkedPage : linkedPages) {
			if (!pages.contains(linkedPage)) {
				nonDuplicateLinkedPages.add(new Page(linkedPage));
			} else {
				//TODO: if applicable, add arguments to original page
			}
		}
		
		pages.addAll(nonDuplicateLinkedPages);
		
		for (Page linkedPage : nonDuplicateLinkedPages) {
			discoverPage(linkedPage);
		}
	}

	private boolean isNotExternalLink(HtmlPage linkedHtmlPage) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	
	
	
	
	
	
	
	
}
