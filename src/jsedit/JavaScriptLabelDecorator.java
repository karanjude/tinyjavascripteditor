package jsedit;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IDecoratorManager;


public class JavaScriptLabelDecorator extends LabelProvider implements
		ILabelDecorator {

	public static final String ID = "JSEdit.JavaScriptDecorator";

	public static JavaScriptLabelDecorator getJavaScriptLabelDecorator() {
		IDecoratorManager decoratorManager = JavaScriptEditorPlugin
				.getDefault().getWorkbench().getDecoratorManager();
		

		if (decoratorManager.getEnabled(ID)) {
			return (JavaScriptLabelDecorator) decoratorManager
					.getLabelDecorator(ID);
		}
		return null;
	}

	public Image decorateImage(Image image, Object element) {
		System.out.println("creating image");
		return new JavaScriptOverlayImage(image).getImage();
	}

	public String decorateText(String text, Object element) {
		IResource file = (IResource) element;
		StringBuilder tags = new StringBuilder();
		for (String tag : JavaScriptEditorPlugin.getDefault().getTags(file)) {
			tags.append(tag).append(" ");
		}
		return file.getName() + " [" + tags.toString() + "]";
	}

	public void refresh() {
		JavaScriptLabelDecorator javaScriptLabelDecorator = getJavaScriptLabelDecorator();
		javaScriptLabelDecorator.fireLabelsChanged(new LabelProviderChangedEvent(javaScriptLabelDecorator,JavaScriptEditorPlugin.getDefault().getUpdatedResource()));
	}

	private void fireLabelsChanged(
			final LabelProviderChangedEvent labelProviderChangedEvent) {
		Display.getDefault().asyncExec(new Runnable(){

			public void run() {
				fireLabelProviderChanged(labelProviderChangedEvent);
			}
			
		});
	}
}
