package fuzzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import site.Page;
import site.Site;

public class Fuzzer {
	private WebClient webClient;
	private List<String> sensitiveData;
	private List<String> maliciousInputs;

	public Fuzzer(WebClient webClient, String sensitiveDataFilePath, String maliciousInputFilePath) throws IOException {
		this.webClient = webClient;
		this.sensitiveData = getSensitiveData(sensitiveDataFilePath);
		this.maliciousInputs = MaliciousInputs.getMaliciousInputs(maliciousInputFilePath);

	}

	private List<String> getSensitiveData(String sensitiveDataFilePath)
			throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(
				sensitiveDataFilePath));

		try {
			ArrayList<String> sensitiveData = new ArrayList<String>();
			String line = reader.readLine();
			while (line != null) {
				sensitiveData.add(line);
				line = reader.readLine();
			}

			return this.sensitiveData;
		} finally {
			reader.close();
		}
	}

	public void fuzz(Site site) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		fuzzPages(site);
	}

	private void fuzzPages(Site site) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		List<Page> pages = site.getPages();
		for (Page page : pages) {
			fuzz(page);
		}
	}

	private void fuzz(Page page) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		for (String argument : page.getArguments()) {
			for (String input : maliciousInputs) {
				String urlToTest = page.getURL().toExternalForm() + "?" + argument + "=" + input;
				try {
					webClient.getPage(urlToTest);
				} catch (FailingHttpStatusCodeException e) {
					continue;
				} catch (IOException e) {
					continue;
				}
				System.out.println("Possible vulnerability at:\t" + urlToTest);
			}
		}
		
		
		
	}





}
