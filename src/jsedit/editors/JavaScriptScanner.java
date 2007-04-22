package jsedit.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;

public class JavaScriptScanner extends RuleBasedPartitionScanner {
	
	public final static String JAVA_SCRIPT_MULTILINE_COMMENT = "__java_script_multiline_comment"; //$NON-NLS-1$
	public final static String JAVA_SCRIPT_SINGLE_LINE_COMMENT = "__java_script_single_line_comment"; //$NON-NLS-1$
	public final static String JAVA_SCRIPT_SINGLE_QUOTES = "__java_script_single_quotes"; //$NON-NLS-1$
	public final static String JAVA_SCRIPT_DOUBLE_QUOTES = "__java_script_double_quotes"; //$NON-NLS-1$
	public final static String JAVA_SCRIPT_BLOCK = "__java_script_block"; //$NON-NLS-1$

	public static final String[] JAVA_SCRIPT_PARTITIONS = {
		JAVA_SCRIPT_BLOCK,
		JAVA_SCRIPT_DOUBLE_QUOTES,
		JAVA_SCRIPT_MULTILINE_COMMENT,
		JAVA_SCRIPT_SINGLE_LINE_COMMENT,
		JAVA_SCRIPT_SINGLE_QUOTES,
	};
	
	public JavaScriptScanner() {
		super();

		IToken singleLineComment = new Token(JAVA_SCRIPT_SINGLE_LINE_COMMENT);
		IToken multiLineComment = new Token(JAVA_SCRIPT_MULTILINE_COMMENT);
		IToken singleQuotes = new Token(JAVA_SCRIPT_SINGLE_QUOTES);
		IToken doubleQuotes = new Token(JAVA_SCRIPT_DOUBLE_QUOTES);
		IToken block = new Token(JAVA_SCRIPT_BLOCK);

		List<IPredicateRule> rules = new ArrayList<IPredicateRule>();

		rules.add(new SingleLineRule("\"","\"",doubleQuotes,'\\'));
		rules.add(new SingleLineRule("'","'",singleQuotes,'\\'));
		rules.add(new EndOfLineRule("//", singleLineComment));
		rules.add(new MultiLineRule("/*", "*/", multiLineComment, (char) 0, true));
		rules.add(new MultiLineRule("{", "}", block , (char) 0, true));

		setPredicateRules(rules.toArray(new IPredicateRule[0]));
	}

	@Override
	public IToken nextToken() {
		IToken token = super.nextToken();
		//System.out.println(token.getData());
		return token;
	}

	
	
	
}
