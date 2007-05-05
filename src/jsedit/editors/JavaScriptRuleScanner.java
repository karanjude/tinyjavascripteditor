package jsedit.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.internal.ui.text.CombinedWordRule;
import org.eclipse.jdt.ui.text.IJavaColorConstants;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class  JavaScriptRuleScanner extends RuleBasedScanner{

	public static final RGB MULTI_LINE_COMMENT= new RGB(128, 0, 0);
	public static final RGB SINGLE_LINE_COMMENT= new RGB(128, 128, 0);
	public static final RGB KEYWORD= new RGB(178, 34, 34);
	public static final RGB TYPE= new RGB(0, 0, 129);
	public static final RGB STRING= new RGB(0, 128, 0);
	public static final RGB DEFAULT= new RGB(0, 0, 0);
	public static final RGB JAVADOC_KEYWORD= new RGB(0, 128, 0);
	public static final RGB JAVADOC_TAG= new RGB(128, 128, 128);
	public static final RGB JAVADOC_LINK= new RGB(128, 128, 128);
	public static final RGB JAVADOC_DEFAULT= new RGB(0, 128, 128);

	private static String[] fgKeywords= { "var", "break", "case", "catch",  "continue", "default", "do", "else",  "finally", "for", "if",  "new",   "return",  "switch",  "throw", "throws", "transient", "try", "volatile", "while" , "function"}; //$NON-NLS-36$ //$NON-NLS-35$ //$NON-NLS-34$ //$NON-NLS-33$ //$NON-NLS-32$ //$NON-NLS-31$ //$NON-NLS-30$ //$NON-NLS-29$ //$NON-NLS-28$ //$NON-NLS-27$ //$NON-NLS-26$ //$NON-NLS-25$ //$NON-NLS-24$ //$NON-NLS-23$ //$NON-NLS-22$ //$NON-NLS-21$ //$NON-NLS-20$ //$NON-NLS-19$ //$NON-NLS-18$ //$NON-NLS-17$ //$NON-NLS-16$ //$NON-NLS-15$ //$NON-NLS-14$ //$NON-NLS-13$ //$NON-NLS-12$ //$NON-NLS-11$ //$NON-NLS-10$ //$NON-NLS-9$ //$NON-NLS-8$ //$NON-NLS-7$ //$NON-NLS-6$ //$NON-NLS-5$ //$NON-NLS-4$ //$NON-NLS-3$ //$NON-NLS-2$ //$NON-NLS-1$

	private static String[] fgTypes= { "void", "boolean", "char", "byte", "short", "int", "long", "float", "double" }; //$NON-NLS-1$ //$NON-NLS-5$ //$NON-NLS-7$ //$NON-NLS-6$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-2$

	private static String[] fgConstants= { "false", "null", "true" }; //$NON-NLS-3$ //$NON-NLS-2$ //$NON-NLS-1$
	
	
	
//	@Override
//	public IToken nextToken() {
//		fTokenOffset= fOffset;
//		fColumn= UNDEFINED;
//		if (fRules != null) {
//			for (int i= 0; i < fRules.length; i++) {
//				IToken token= (fRules[i].evaluate(this));
//				System.out.println(token.getData());
//				if (!token.isUndefined())
//					return token;
//			}
//		}
//		if (read() == EOF)
//			return Token.EOF;
//		return fDefaultReturnToken;
//	}

	private static final class BracketRule implements IRule {

		/** Java brackets */
		private final char[] JAVA_BRACKETS= { '(', ')', '{', '}', '[', ']' };
		/** Token to return for this rule */
		private final IToken fToken;

		/**
		 * Creates a new bracket rule.
		 *
		 * @param token Token to use for this rule
		 */
		public BracketRule(IToken token) {
			fToken= token;
		}

		/**
		 * Is this character a bracket character?
		 *
		 * @param character Character to determine whether it is a bracket character
		 * @return <code>true</code> iff the character is a bracket, <code>false</code> otherwise.
		 */
		public boolean isBracket(char character) {
			for (int index= 0; index < JAVA_BRACKETS.length; index++) {
				if (JAVA_BRACKETS[index] == character)
					return true;
			}
			return false;
		}

		/*
		 * @see org.eclipse.jface.text.rules.IRule#evaluate(org.eclipse.jface.text.rules.ICharacterScanner)
		 */
		public IToken evaluate(ICharacterScanner scanner) {

			int character= scanner.read();
			if (isBracket((char) character)) {
				do {
					character= scanner.read();
				} while (isBracket((char) character));
				scanner.unread();
				return fToken;
			} else {
				scanner.unread();
				return Token.UNDEFINED;
			}
		}
	}

	/**
	 * Creates a Java code scanner with the given color provider.
	 * 
	 * @param provider the color provider	
	 */
	public JavaScriptRuleScanner() {

		Token keyword= new Token(new TextAttribute(getColor(KEYWORD),null,SWT.BOLD));
		Token type= new Token(new TextAttribute(getColor(TYPE),null,SWT.BOLD));
		Token string= new Token(new TextAttribute(getColor(STRING),null,SWT.BOLD));
		Token comment= new Token(new TextAttribute(getColor(SINGLE_LINE_COMMENT)));
		Token other= new Token(new TextAttribute(getColor(DEFAULT)));
		Token bracket = new Token(new TextAttribute(getColor(KEYWORD)));
		List<IRule> rules= new ArrayList<IRule>();

		rules.add(new EndOfLineRule("//", comment)); //$NON-NLS-1$

		rules.add(new SingleLineRule("\"", "\"", string, '\\')); 
		rules.add(new SingleLineRule("'", "'", string, '\\')); 
		rules.add(new MultiLineRule("/*","*/",string));
		rules.add(new MultiLineRule("/* ","*/",string));

		rules.add(new WhitespaceRule(new JavaScriptWhitespaceDetector()));

		WordRule wordRule = new WordRule(new JavaScriptWordDetector(),other);
		for (int i= 0; i < fgKeywords.length; i++)
			wordRule.addWord(fgKeywords[i], keyword);
		for (int i= 0; i < fgTypes.length; i++)
			wordRule.addWord(fgTypes[i], type);
		for (int i= 0; i < fgConstants.length; i++)
			wordRule.addWord(fgConstants[i], type);
		rules.add(wordRule);
		
		rules.add(new BracketRule(bracket));

		IRule[] result= new IRule[rules.size()];
		rules.toArray(result);
		setRules(result);
		setDefaultReturnToken(other);
	}

	private Color getColor(RGB rgb) {
		return new Color(Display.getCurrent(),rgb);
	}

	
}
