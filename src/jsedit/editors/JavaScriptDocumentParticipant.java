package jsedit.editors;
import org.eclipse.core.filebuffers.IDocumentSetupParticipant;
import org.eclipse.jface.text.IDocument;


public class JavaScriptDocumentParticipant implements IDocumentSetupParticipant {
	
	public JavaScriptDocumentParticipant() {
		super();
		System.out.println("in side ...");
	}

	public void setup(IDocument document) {
		System.out.println("Using my java script editor");
	}

}
