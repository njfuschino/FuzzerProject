package fuzzer;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.WebClient;

import site.Site;

import attackSurface.AttackSurfaceDiscoverer;

public class FuzzerRunner {
	private Fuzzer fuzzer;
	private AttackSurfaceDiscoverer attackSurfaceDiscoverer;
	private double randomThreshold = .1; // The decimal percentage of the
											// probability that things are
											// checked
	private boolean complete = false;

	public FuzzerRunner(String[] args) throws IOException {
		String targetURL = args[0];
		int timeDelay = Integer.parseInt(args[1]);
		String sensitiveDataFilePath = args[2];
		String maliciousInputFilePath = args[3];
		String pageGuessFilePath = args[4];

		WebClient webClient = new TimeDelayWebClient(timeDelay,
				sensitiveDataFilePath);
		webClient.setPrintContentOnFailingStatusCode(false);

		fuzzer = new Fuzzer(webClient, maliciousInputFilePath, randomThreshold,
				complete);
		attackSurfaceDiscoverer = new AttackSurfaceDiscoverer(webClient,
				targetURL, pageGuessFilePath);
	}

	public void run() throws MalformedURLException, IOException {
		Site site = attackSurfaceDiscoverer.discoverAttackSurface();
		attackSurfaceDiscoverer.reportAttackSurface(site);
		fuzzer.fuzz(site);

	}

	/**
	 * @param args
	 *            0- target url 1- time delay (can be 0) 2- path to sensitive
	 *            data file
	 */
	public static void main(String[] args) throws MalformedURLException,
			IOException {
		FuzzerRunner fuzzerRunner = new FuzzerRunner(args);
		fuzzerRunner.run();
	}

}
