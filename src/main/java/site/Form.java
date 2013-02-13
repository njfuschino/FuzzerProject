package site;

import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;

public class Form {
	HtmlForm form;
	List<HtmlInput> inputs;

	public Form(HtmlForm form) {
		this.form = form;
		this.inputs = new ArrayList<HtmlInput>();

		for (HtmlElement element : form.getHtmlElementDescendants()) {
			if (element instanceof HtmlInput) {
				inputs.add((HtmlInput) element);
			}
		}
	}
	
	public HtmlForm getForm() {
		return form;
	}
	
	public List<HtmlInput> getInputs() {
		return inputs;
	}
	public String toString() {
		String string = "";

		string += "Form-  Page:  " + form.getPage()
				+ ";  Name:  " + form.getAttribute("name")
				+ ";  Id:  " + form.getAttribute("id");
		
		string += "\n\tInputs:";
		for (HtmlElement input : inputs) {
			string += "\n\t\tName:  " + input.getAttribute("name") + ";  Id:  "
					+ input.getAttribute("id");
		}

		return string;
	}
	
}
