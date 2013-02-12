package fuzzer;
import java.io.IOException;
import java.net.URL;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;

public class TimeDelayWebClient extends WebClient {
	private int timeDelay;

	public TimeDelayWebClient(int timeDelay) {
		super();
		this.timeDelay = timeDelay;
	}

	public <P extends Page> P getPage(URL url)
			throws FailingHttpStatusCodeException, IOException {
		P page = super.getPage(url);
		try {
			Thread.sleep(timeDelay);
		} catch (InterruptedException e) {
			//Can't throw this for some reason, but it will never happen anyway
		}
		return page;
	}
}
