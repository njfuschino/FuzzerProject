package fuzzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;

import site.Form;
import site.Page;
import site.Site;

public class Fuzzer {
	private WebClient webClient;
	private String targetURL;
	private List<String> sensitiveData;

	public Fuzzer(WebClient webClient, String targetURL, String sensitiveDataFilePath) throws IOException {
		this.targetURL = targetURL;
		this.webClient = webClient;
		this.sensitiveData = getSensitiveData(sensitiveDataFilePath);
		
	}

	private List<String> getSensitiveData(String sensitiveDataFilePath) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(sensitiveDataFilePath));
		
		ArrayList<String> sensitiveData = new ArrayList<String>();
		String line = reader.readLine();
		while (line != null) {
			sensitiveData.add(line);
		}
		return this.sensitiveData;
	}

	public void run() throws MalformedURLException,
			IOException {
		Site site = discoverAttackSurface(targetURL);
		reportAttackSurface(site);
		fuzz(site);

	}

	private void fuzz(Site site) {
		
	}

	private void reportAttackSurface(Site site) {
		List<Page> pages = site.getPages();
		for (Page page : pages) {
			System.out.println(page);
		}
		List<Cookie> cookies = site.getCookies();
		for (Cookie cookie : cookies) {
			System.out.println("Cookie:\t" + cookie.getName() + "\n");
		}
		List<Form> forms = site.getForms();
		for (Form form : forms) {
			System.out.println(form);
		}
	}

	private Site discoverAttackSurface(String siteUrl)
			throws MalformedURLException, IOException {
		HtmlPage page = webClient.getPage(siteUrl);
		Site site = new Site(page);
		site.discoverSite();
		return site;
	}

	/**
	 * @param args
	 *     0- target url
	 *     1- time delay (can be 0)
	 */
	public static void main(String[] args) throws MalformedURLException,
			IOException {
		FuzzerFactory fuzzerBuilder = new FuzzerFactory();
		Fuzzer fuzzer = fuzzerBuilder.getFuzzer(args);

		fuzzer.run();
	}

}
