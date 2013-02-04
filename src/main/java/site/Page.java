package site;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Page {
	private URL url;
	private HtmlPage htmlPage;
	private List<String> arguments;

	public Page(HtmlPage htmlPage) throws MalformedURLException {
		this.htmlPage = htmlPage;
		URL tempUrl = htmlPage.getWebResponse().getWebRequest().getUrl();
		this.url = new URL(tempUrl.getProtocol(), tempUrl.getHost(),
				tempUrl.getPath());
		this.arguments = new LinkedList<String>();

	}

	public void discoverInputs() {
		addArguments(htmlPage);
	}

	public HtmlPage getHtmlPage() {
		return htmlPage;
	}

	public URL getURL() {
		return url;
	}
	
	public void addArguments(HtmlPage linkedPage) {
		URL otherUrl = linkedPage.getWebResponse().getWebRequest().getUrl();
		String query = otherUrl.getQuery();
		if (query == null) {
			return;
		}
		String[] pairs = query.split("&");
		for (String pair : pairs) {
			String[] splitPair = pair.split("=");
			String argument = splitPair[0];
			if (!arguments.contains(argument)) {
				arguments.add(argument);
			}
		}

	}

	public boolean equals(Object o) {
		if (o.getClass() != this.getClass()) {
			return false;
		}

		Page other = (Page) o;

		boolean equal = other.url.getPath().equals(this.url.getPath());

		return equal;
	}

	public String toString() {
		String string = "";

		string += "URL:\t" + url + "\n";
		for (String argument : arguments) {
			string += "\tQuery parameter:\t" + argument + "\n";
		}
		return string;
	}

}
