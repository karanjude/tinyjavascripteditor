package jsedit.editors;

import org.eclipse.core.filebuffers.IAnnotationModelFactory;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.ui.texteditor.ResourceMarkerAnnotationModelFactory;

public class JavaScriptAnnotationModelFactory extends ResourceMarkerAnnotationModelFactory {

	public IAnnotationModel createAnnotationModel(IPath location) {
		return super.createAnnotationModel(location);
	}

}
