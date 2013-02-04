package site;

import java.util.LinkedList;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Site {

	Page basePage;
	private List<Page> pages;
	
	public Site(HtmlPage baseUrlPage) {
		this.basePage = new Page(baseUrlPage);
		this.pages = new LinkedList<Page>();
		pages.add(basePage);
	}

	public void discoverSite() {
		// TODO Auto-generated method stub
		
	}

	
}
