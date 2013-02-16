package fuzzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class LoginFuzzer {
	public final List<String> passwords;
	public final List<String> usernames;

	public LoginFuzzer(String userNamesFilePath, String passwordsFilePath) throws IOException {
		this.usernames = initList(userNamesFilePath);
		this.passwords = initList(passwordsFilePath);
	}

	public void fuzzLogin(HtmlSubmitInput submitInput, HtmlTextInput usernameInput, 
			HtmlPasswordInput passwordInput) throws IOException{
		
		for (String username : usernames){
			for (String password : passwords){
				usernameInput.setValueAttribute(username);
				passwordInput.setValueAttribute(password);
				
				final HtmlPage p = submitInput.click();
					
				if (p.asText().contains("successfully") || p.asText().contains("Welcome")){
					System.out.println("Successfully guessed login: ");
					System.out.println("\tusername: " + username);
					System.out.println("\tpassword: " + password);
				}
				
				//simulate clicking "Back" button to retry other login info
				WebClient wc = new WebClient();
				wc.getWebWindows().get(0).getHistory().back();
			}
		}
	}
	
	private List<String> initList(String filePath) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(filePath));

		try {
			ArrayList<String> list = new ArrayList<String>();

			String line = reader.readLine();
			while (line != null) {
				if (!line.equals("")) {
					list.add(line);
				}
				line = reader.readLine();
			}

			return list;
		} finally {
			reader.close();
		}
	}
}
