package site;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Page {
	private URL url;
	private HtmlPage htmlPage;
	private List<String> arguments;
	
	
	public Page(HtmlPage htmlPage) {
		this.htmlPage = htmlPage;
		this.url = htmlPage.getWebResponse().getUrl();
		this.arguments = new LinkedList<String>();
	}

	public void discoverInputs() {
		
	}
	
	public HtmlPage getHtmlPage() {
		return htmlPage;
	}
	
	public URL getURL() {
		return url;
	}
	
	public List<HtmlAnchor> getAnchors() {
		List<HtmlAnchor> links = htmlPage.getAnchors();
		return links;
	}

	public void addArguments(HtmlPage linkedPage) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean equals(Object o) {
		if(o.getClass() != this.getClass()) {
			return false;
		}
		
		Page other = (Page) o;
		
		boolean equal = other.url.getPath().equals(this.url.getPath());
		
		return equal;
	}

	public String toString() {
		String string = "";
		
		string += "URL:\t" + url.toExternalForm() + "\n";
		for(String argument : arguments) {
			string += "Query parameters" + argument + "\n";
		}
		return string;
	}
	
	
	
	
	
	
	
}
