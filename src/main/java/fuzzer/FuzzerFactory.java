package fuzzer;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.WebClient;

public class FuzzerFactory {

	public Fuzzer getFuzzer(String[] args) throws IOException {
		String targetURL = args[0];
		int timeDelay = Integer.parseInt(args[1]);
		String sensitiveDataFilePath = args[2];

		WebClient webClient = new TimeDelayWebClient(timeDelay);
		
		return new Fuzzer(webClient, targetURL, sensitiveDataFilePath);
	}
	
}
