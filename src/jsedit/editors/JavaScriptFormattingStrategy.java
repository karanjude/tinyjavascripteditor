/**
 * 
 */
package jsedit.editors;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TypedPosition;
import org.eclipse.jface.text.formatter.FormattingContextProperties;
import org.eclipse.jface.text.formatter.IFormattingContext;
import org.eclipse.jface.text.formatter.IFormattingStrategy;
import org.eclipse.jface.text.formatter.IFormattingStrategyExtension;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;

/**
 * @author karasing
 * 
 */
public class JavaScriptFormattingStrategy implements
		IFormattingStrategyExtension, IFormattingStrategy {

	private IDocument document;
	private TypedPosition partition;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.formatter.IFormattingStrategyExtension#format()
	 */
	public void format() {
		MultiTextEdit multiTextEdit = new MultiTextEdit(partition.getOffset(),
				partition.getLength());
		multiTextEdit.addChild(new ReplaceEdit(partition.offset,
				partition.length, "yo baby"));
		try {
			multiTextEdit.apply(document);
		} catch (MalformedTreeException e) {
			e.printStackTrace();
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.formatter.IFormattingStrategyExtension#formatterStarts(org.eclipse.jface.text.formatter.IFormattingContext)
	 */
	public void formatterStarts(IFormattingContext context) {
		Object doucment = context
				.getProperty(FormattingContextProperties.CONTEXT_MEDIUM);
		if (doucment instanceof IDocument) {
			this.document = (IDocument) doucment;
		}

		Object partition = context
				.getProperty(FormattingContextProperties.CONTEXT_PARTITION);
		if (doucment instanceof IDocument) {
			this.partition = (TypedPosition) partition;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.formatter.IFormattingStrategyExtension#formatterStops()
	 */
	public void formatterStops() {
		System.out.println("formatting stopped");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.formatter.IFormattingStrategy#format(java.lang.String,
	 *      boolean, java.lang.String, int[])
	 */
	public String format(String content, boolean isLineStart,
			String indentation, int[] positions) {
		return "format";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.formatter.IFormattingStrategy#formatterStarts(java.lang.String)
	 */
	public void formatterStarts(String initialIndentation) {
		System.out.println("formatting started 2");
	}

}
