package jsedit;

import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import jsedit.editors.JavaScriptEditor;
import jsedit.editors.JavaScriptScanner;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.osgi.framework.internal.core.ConsoleMsg;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.ui.IDecoratorManager;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.eclipse.ui.internal.browser.WorkbenchBrowserSupport;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.mozilla.javascript.Parser;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class JavaScriptEditorPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "JSEdit";

	// The shared instance
	private static JavaScriptEditorPlugin plugin;

	private JavaScriptScanner javaScriptScanner;

	private IDocumentPartitioner javaScriptDocumentParitioner;

	private IDocument document;

	private Parser javaScriptParser;

	/**
	 * The constructor
	 */
	public JavaScriptEditorPlugin() {
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public void execute(String script) {
		JavaScriptExecutor.execute(this, script);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static JavaScriptEditorPlugin getDefault() {
		return plugin;
	}

	public JavaScriptScanner getPartitionScanner() {
		if (javaScriptScanner == null)
			javaScriptScanner = new JavaScriptScanner();
		return javaScriptScanner;
	}

	private static final ImageDescriptor imageDescriptor = ImageDescriptor
			.createFromFile(JavaScriptEditorPlugin.class, "checkout.gif");

	public static final String FORMAT = JavaScriptEditorPlugin.class.getName()
			+ ".format";

	public ImageData getOverlayImageData() {
		return imageDescriptor.getImageData();
	}

	public IDocumentPartitioner getJavaScriptDocumentPartitioner() {
		if (javaScriptDocumentParitioner == null)
			javaScriptDocumentParitioner = new FastPartitioner(
					JavaScriptEditorPlugin.getDefault().getPartitionScanner(),
					JavaScriptScanner.JAVA_SCRIPT_PARTITIONS);
		return javaScriptDocumentParitioner;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	public void setDocument(IDocument document) {
		this.document = document;
		ToolBarManager toolbarManager = new ToolBarManager();
		// IWorkbenchBrowserSupport browserSupport = getDefault().getWorkbench()
		// .getBrowserSupport();
		// IWebBrowser browser;
		// try {
		// if (browserSupport.isInternalWebBrowserAvailable()) {
		// browser = browserSupport.createBrowser(
		// WorkbenchBrowserSupport.AS_EDITOR
		// | WorkbenchBrowserSupport.LOCATION_BAR
		// | IWorkbenchBrowserSupport.NAVIGATION_BAR,
		// null, null, null);
		// URL findMorePluginsURL = new
		// URL("http://localhost/jquery/mptest.py/index?url=http://www.google.com");
		// browser.openURL(findMorePluginsURL);
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	public IDocument getDocument() {
		return this.document;
	}

	public void setJavaScriptParser(Parser parser) {
		this.javaScriptParser = parser;
	}

	public Parser getJavaScriptParser() {
		return this.javaScriptParser;
	}

	private Hashtable<IResource, List<String>> tags = new Hashtable<IResource, List<String>>();

	private List<IResource> listOfResources = new ArrayList<IResource>();

	private IResource updatedReource;

	public void addTag(IResource resource, String tag) {
		if (tags.containsKey(resource)) {
			List<String> tagsForResource = tags.get(resource);
			tagsForResource.add(tag);
		} else {
			List<String> tagsForResource = new ArrayList<String>();
			tagsForResource.add(tag);
			tags.put(resource, tagsForResource);
		}
	}

	public List<String> getTags(IResource file) {
		if (!tags.containsKey(file))
			return new ArrayList<String>();
		return tags.get(file);
	}

	public void updateResource(IResource resource) {
		updatedReource = resource;
	}

	public IResource getUpdatedResource() {
		return updatedReource;
	}

	/**
	 * @param result
	 */
	public void print(Object result) {
		ConsolePrinter.print(result);
	}

}
