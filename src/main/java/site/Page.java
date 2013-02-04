package site;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Page {
	private URL url;
	private HtmlPage htmlPage;
	private Map<String, List<String>> arguments;
	
	
	public Page(HtmlPage htmlPage) {
		this.htmlPage = htmlPage;
		this.url = htmlPage.getWebResponse().getUrl();
		this.arguments = new HashMap<String, List<String>>();
	}

	public void discoverInputs() {
		
	}
	
	public List<HtmlAnchor> getAnchors() {
		List<HtmlAnchor> links = htmlPage.getAnchors();
		return links;
	}
	
	
	
	
}
