package fuzzer;

import com.gargoylesoftware.htmlunit.WebClient;

public class FuzzerFactory {

	public Fuzzer getFuzzer(String[] args) {
		String targetURL = args[0];
		int timeDelay = Integer.parseInt(args[1]);

		WebClient webClient = new TimeDelayWebClient(timeDelay);
		
		return new Fuzzer(webClient, targetURL);
	}
	
}
