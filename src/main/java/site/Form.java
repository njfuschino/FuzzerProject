package site;

import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;

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
