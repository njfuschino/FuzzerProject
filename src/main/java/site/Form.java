package site;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class Form {
	HtmlForm form;
	List<HtmlElement> inputs;

	public Form(HtmlForm form) {
		this.form = form;
		this.inputs = new ArrayList<HtmlElement>();

		for (HtmlElement element : form.getHtmlElementDescendants()) {
			if (element instanceof HtmlInput || element instanceof HtmlTextArea) {
				inputs.add(element);
			}
		}
	}
	
	public void authenticateJPetStore(){
		form.setAttribute("username", "njf1116");
		form.setAttribute("password", "genericpassword");
		//username: njf1116
		//password: genericpassword
	}
	
	
	/**
	 * Authenticate BodgeIt
	 * username: njfuschino@gmail.com
	 * password: genericpassword
	 */
	public void authenticateBodgeIt() throws IOException{
		
		final HtmlSubmitInput submit = form.getInputByValue("Login");
		final HtmlTextInput username = form.getInputByName("username");
		final HtmlPasswordInput password = form.getInputByName("password");
		
		username.setValueAttribute("njfuschino@gmail.com");
		password.setValueAttribute("genericpassword");
		
		System.out.println("Login with username: " + username.getValueAttribute());
		System.out.println("Login with password: " + password.getValueAttribute());
		System.out.println("Submitting login information...");
		final HtmlPage p = submit.click();
		
		System.out.println("Authentication response: " + p.asText());
	}
		
/*
		for(HtmlElement input : inputs){
			if(input.getId().equals("username")){
				HtmlInput in = (HtmlInput)input;
				input.setAttribute("username", "njfuschino@gmail.com");
				System.out.println("Login with username: " + input.getAttribute("username"));
			}
			else if(input.getId().equals("password")){
				input.setAttribute("password", "genericpassword");
				System.out.println("Login with password: " + input.getAttribute("password"));
			}
		}
		for(HtmlElement input : inputs){
			if(input.getId().equals("submit")){
				System.out.println("Submitting login information.");
				HtmlPage p = input.click();
				System.out.println("AUTHENTICATED?: " + p.asText());
			}
		}
	}
*/
	public void authenticateDVWA(){
		//This method assumes current form is the login form
		form.setAttribute("username", "admin");
		form.setAttribute("password", "password");
	}	

	public String toString() {
		String string = "";

		string += "Form-  Page:  " + form.getPage()
				+ ";  Name:  " + form.getAttribute("name")
				+ ";  Id:  " + form.getAttribute("id")
				+ "\n";
		
		string += "\tInputs:\n";
		for (HtmlElement input : inputs) {
			string += "\t\tName:  " + input.getAttribute("name") + ";  Id:  "
					+ input.getAttribute("id") + "\n";
		}

		return string;
	}
	
}
