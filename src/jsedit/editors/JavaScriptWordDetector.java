/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package jsedit.editors;


import org.eclipse.jface.text.rules.IWordDetector;

/**
 * A Java aware word detector.
 */
public class JavaScriptWordDetector implements IWordDetector {

	/* (non-Javadoc)
	 * Method declared on IWordDetector.
	 */
	public boolean isWordPart(char character) {
		return Character.isJavaIdentifierPart(character);
	}
	
	/* (non-Javadoc)
	 * Method declared on IWordDetector.
	 */
	public boolean isWordStart(char character) {
		return Character.isJavaIdentifierStart(character);
	}
}
