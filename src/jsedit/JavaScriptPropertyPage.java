package jsedit;


import org.eclipse.core.resources.IResource;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.dialogs.PropertyPage;

public class JavaScriptPropertyPage extends PropertyPage implements
		IWorkbenchPropertyPage {

	private Text tagField;

	@Override
	protected void performApply() {
		IResource resource = (IResource) getElement();
		JavaScriptEditorPlugin.getDefault().addTag(resource,tagField.getText());
		JavaScriptEditorPlugin.getDefault().updateResource(resource);
		JavaScriptLabelDecorator.getJavaScriptLabelDecorator().refresh();
		super.performApply();
	}

	public JavaScriptPropertyPage() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Control createContents(Composite parent) {
		Label tag = new Label(parent,SWT.NONE);
		tag.setText("Enter Tag: ");
		tagField = new Text(parent,SWT.SINGLE | SWT.BORDER); 
		return parent;
		
	}

}
