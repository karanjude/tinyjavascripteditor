/**
 * 
 */
package jsedit.actions;

import jsedit.JavaScriptEditorPlugin;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.widgets.Event;

/**
 * @author karasing
 *
 */
public class RunAction extends Action {

	private final String script;

	public RunAction(String script) {
		this.script = script;
		setText("Run Javascript");
	}

	@Override
	public void run() {
		JavaScriptEditorPlugin.getDefault().execute(script);
	}
	
	
}
