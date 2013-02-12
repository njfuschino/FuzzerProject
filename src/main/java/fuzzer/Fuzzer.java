package fuzzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;

import site.Page;
import site.Site;

public class Fuzzer {
	private WebClient webClient;
	private List<String> sensitiveData;

	public Fuzzer(WebClient webClient, String sensitiveDataFilePath) throws IOException {
		this.webClient = webClient;
		this.sensitiveData = getSensitiveData(sensitiveDataFilePath);

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

	public void fuzz(Site site) {
		fuzzPages(site);
	}

	private void fuzzPages(Site site) {
		List<Page> pages = site.getPages();
		for (Page page : pages) {
			fuzz(page);
		}
	}

	private void fuzz(Page page) {
		for (String argument : page.getArguments()) {
			
		}
		
		
		
	}





}
