package site;

import java.net.URL;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Page {
	URL url;
	HtmlPage htmlPage;
	
	public Page(HtmlPage htmlPage) {
		this.htmlPage = htmlPage;
		this.url = htmlPage.getWebResponse().getUrl();
	}


	
	
	
	
}
