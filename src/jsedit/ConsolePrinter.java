/**
 * 
 */
package jsedit;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

/**
 * @author karasing
 * 
 */
public class ConsolePrinter {

	private static MessageConsole console;

	/**
	 * @param result
	 */
	public static void print(Object result) {
		ConsolePlugin.getDefault().getConsoleManager().addConsoles(
				new IConsole[] { getConsole() });
		MessageConsoleStream messageStream = consoleStream();
		printTo(result, messageStream);
	}

	/**
	 * @param result
	 * @param messageStream
	 */
	private static void printTo(Object result,
			MessageConsoleStream messageStream) {
		String message = result.toString();
		messageStream.println(message);
		try {
			messageStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				messageStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return
	 */
	private static MessageConsoleStream consoleStream() {
		MessageConsoleStream messageStream = console.newMessageStream();
		messageStream.setColor(Display.getCurrent().getSystemColor(
				SWT.COLOR_BLACK));
		return messageStream;
	}

	/**
	 * @return
	 */
	private static MessageConsole getConsole() {
		if (null == console)
			console = new MessageConsole("Result", null);
		return console;
	}

}
