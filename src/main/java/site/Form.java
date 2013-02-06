package site;

import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;

public class Form {
	HtmlForm form;
	List<HtmlElement> inputs;
	
	public Form(HtmlForm form) {
		this.form = form;
		this.inputs = new ArrayList<HtmlElement>();
		
		for (HtmlElement element : form.getHtmlElementDescendants()) {
			if(element instanceof HtmlInput) {
				inputs.add(element);
			}
		}
	}

	public String toString() {
		String string = "";
		
		string += "Form:\t" + form.asText() + "\n    Page:  " + form.getPage() + "\n";
		
		return string;
	}
	
}
