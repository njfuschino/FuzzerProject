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
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
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
		
		guessPages();
		
		discoverCookies();
	}

	private void guessPages() {
		// TODO Auto-generated method stub
		
	}

	private void discoverCookies() {
		CookieManager cookieManager = webClient.getCookieManager();
		for (Cookie cookie : cookieManager.getCookies()) {
			cookies.add(cookie);
		}
	}
	public void authenticateJPetStore(HtmlForm form){
		//username: njf1116
		//password: genericpassword
	}
	
	/**
	 * Authenticate BodgeIt
	 * username: njfuschino@gmail.com
	 * password: genericpassword
	 */
	public void authenticateBodgeIt(HtmlForm form) throws IOException{
		
		final HtmlSubmitInput submit = form.getInputByValue("Login");
		final HtmlTextInput username = form.getInputByName("username");
		final HtmlPasswordInput password = form.getInputByName("password");
		
		username.setValueAttribute("njfuschino@gmail.com");
		password.setValueAttribute("genericpassword");
		
		System.out.println("Login with username: " + username.getValueAttribute());
		System.out.println("Login with password: " + password.getValueAttribute());
		System.out.println("Submitting login information...");
		final HtmlPage p = submit.click();
		
		System.out.println("Authentication response: " + p.asText());
	}
		
	public void authenticateDVWA(HtmlForm form){
		//This method assumes current form is the login form
	}	

	
	private void authenticateLogin(List<Form> forms) throws IOException{		
		if(this.baseUrl.toString().contains("dvwa")){
			for(Form form : forms){
				if(form.toString().contains("login")){
					authenticateDVWA(form.getForm());
				}
			}
		}
		else if(this.baseUrl.toString().contains("bodgeit")){
			for(Form form : forms) {
				if(form.toString().contains("login")){
					authenticateBodgeIt(form.getForm());
				}
			}
		}
		else if(this.baseUrl.toString().contains("jpetstore")){
			for(Form form : forms) {
				if(form.toString().contains("signon")){
					authenticateJPetStore(form.getForm());
				}
			}
		}
	}
	
	private void discoverPage(Page page) throws MalformedURLException,
			IOException {
		page.discoverInputs();
		forms.addAll(page.getForms());

		System.out.println("DISCOVERING PAGE: " + page.getURL().toString());
		
		if(page.getURL().toString().contains("login") || page.getURL().toString().contains("signon")){
			authenticateLogin(page.getForms());
		}
		
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
