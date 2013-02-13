package fuzzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;

public class TimeDelayWebClient extends WebClient {
	private int timeDelay;
	private List<String> sensitiveData;

	public TimeDelayWebClient(int timeDelay, String sensitiveDataFilePath) throws IOException {
		super();
		this.timeDelay = timeDelay;
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

			return sensitiveData;
		} finally {
			reader.close();
		}
	}

	public <P extends Page> P getPage(URL url)
			throws FailingHttpStatusCodeException, IOException {
		P page = super.getPage(url);
		try {
			Thread.sleep(timeDelay);
		} catch (InterruptedException e) {
			// Can't throw this for some reason, but it will never happen anyway
		}
		for (String data : sensitiveData) {
			if (responseContainsSensitiveData(page.getWebResponse(), data)) {
				System.out.println("Sensitive data disclosed at:\t"
						+ url);
			}
		}
		return page;
	}
	
	private boolean responseContainsSensitiveData(WebResponse response,
			String input) {
		for (String data : sensitiveData) {
			if (response.getContentAsString().contains(data)) {
				return true;
			}
		}

		return false;
	}
}
