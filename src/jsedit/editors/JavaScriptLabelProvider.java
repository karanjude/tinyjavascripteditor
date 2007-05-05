package jsedit.editors;

import org.eclipse.jface.viewers.LabelProvider;

public class JavaScriptLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		System.out.println("getTExt");
		return super.getText(element);
	}

}
