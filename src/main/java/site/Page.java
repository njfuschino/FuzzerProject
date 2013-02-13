package site;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Page {
	private URL url;
	private HtmlPage htmlPage;
	private List<String> arguments;
	private List<Form> forms;

	public Page(HtmlPage htmlPage) throws MalformedURLException {
		this.htmlPage = htmlPage;
		URL tempUrl = htmlPage.getWebResponse().getWebRequest().getUrl();
		this.url = new URL(tempUrl.getProtocol(), tempUrl.getHost(),
				tempUrl.getPath());
		this.arguments = new ArrayList<String>();
		this.forms = new ArrayList<Form>();
	}

	public void discoverInputs() {
		addArguments(htmlPage);
		discoverForms();
	}
	
	private void discoverForms() {
		List<HtmlForm> formsOnPage = htmlPage.getForms();
		for (HtmlForm form : formsOnPage) {
			forms.add(new Form(form));
		}
	}

	public HtmlPage getHtmlPage() {
		return htmlPage;
	}

	public List<String> getArguments() {
		return arguments;
	}
	
	public URL getURL() {
		return url;
	}
	
	public List<Form> getForms() {
		return forms;
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

		string += "URL:\t" + url;
		for (String argument : arguments) {
			string += "\n\tQuery parameter:\t" + argument;
		}
		return string;
	}

}
