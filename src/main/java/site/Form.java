package site;

import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;

public class Form {
	HtmlForm form;
	List<HtmlElement> inputs;
	
	public Form(HtmlForm form) {
		this.form = form;
		this.inputs = new ArrayList<HtmlElement>();
		
		for (HtmlElement element : form.getHtmlElementDescendants()) {
			if(elementIsInput(element)) {
				inputs.add(element);
			}
		}
	}

	private boolean elementIsInput(HtmlElement element) {
		String elementType = element.getTagName();
		return elementType.equals("input");
	}
	
	public String toString() {
		String string = "";
		
		string += "Form:\t" + form.asText() + "\n    Page:  " + form.getPage() + "\n";
		
		return string;
	}
	
	
	
	

}
