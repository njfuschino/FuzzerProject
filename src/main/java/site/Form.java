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
	
	public HtmlForm getForm(){
		return this.form;
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
