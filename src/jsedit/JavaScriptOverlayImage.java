package jsedit;


import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

public class JavaScriptOverlayImage extends CompositeImageDescriptor {

	private Image baseImage;

	public JavaScriptOverlayImage(Image baseImage){
		this.baseImage = baseImage; 
	}
	
	@Override
	protected void drawCompositeImage(int width, int height) {
		drawImage(baseImage.getImageData(), 0,0);
		drawImage(JavaScriptEditorPlugin.getDefault().getOverlayImageData(), 0,0);
	}

	@Override
	protected Point getSize() {
		return new Point(baseImage.getBounds().width,baseImage.getBounds().height);
	}
	
	public Image getImage(){
		return createImage();
	}

}
