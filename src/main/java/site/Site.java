package site;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Site {
	URL baseUrl;
	Page basePage;
	private List<Page> pages;
	
	public Site(HtmlPage baseHtmlPage) {
		this.basePage = new Page(baseHtmlPage);
		this.baseUrl = baseHtmlPage.getWebResponse().getUrl();
		this.pages = new LinkedList<Page>();
		pages.add(basePage);
	}

	public void discoverSite() {
		// TODO Auto-generated method stub
		
	}

	
}
