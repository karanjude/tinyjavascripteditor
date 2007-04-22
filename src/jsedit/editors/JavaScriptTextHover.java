package jsedit.editors;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.swt.graphics.Point;

public class JavaScriptTextHover implements ITextHover {

	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		System.out.println("onnnnnnnnnnnnnnnnneeeeeeee");
		if (hoverRegion != null) {
			try {
				if (hoverRegion.getLength() > -1)
					return textViewer.getDocument().get(hoverRegion.getOffset(), hoverRegion.getLength());
			} catch (BadLocationException x) {
			}
		}

		
		return "booooo";
	}

	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
		System.out.println("twoooooooooooooooooox");
		Point selectedRange = textViewer.getSelectedRange();
		if(offset >= selectedRange.x && offset < selectedRange.x + selectedRange.y){
			System.out.println("in offset");
			return new Region(selectedRange.x,selectedRange.y);
		}
		else
			System.out.println("out of offsetxe");
		return new Region(offset,0);
	}

}
