import com.gargoylesoftware.htmlunit.WebClient;


public class TimeDelayWebClient extends WebClient {
	private int timeDelay;

	public TimeDelayWebClient(int timeDelay) {
		super();
		this.timeDelay = timeDelay;
	}
	
	
}
