package jsedit.editors;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ContextInformation;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

public class JavaScriptCompletionProcessor implements IContentAssistProcessor {

	private static String[] keywords = {
			"var", "break", "case", "catch", "continue", "default", "do", "else", "finally", "for", "if", "new", "return", "switch", "throw", "throws", "transient", "try", "volatile", "while", "function" }; //$NON-NLS-36$ //$NON-NLS-35$ //$NON-NLS-34$ //$NON-NLS-33$ //$NON-NLS-32$ //$NON-NLS-31$ //$NON-NLS-30$ //$NON-NLS-29$ //$NON-NLS-28$ //$NON-NLS-27$ //$NON-NLS-26$ //$NON-NLS-25$ //$NON-NLS-24$ //$NON-NLS-23$ //$NON-NLS-22$ //$NON-NLS-21$ //$NON-NLS-20$ //$NON-NLS-19$ //$NON-NLS-18$ //$NON-NLS-17$ //$NON-NLS-16$ //$NON-NLS-15$ //$NON-NLS-14$ //$NON-NLS-13$ //$NON-NLS-12$ //$NON-NLS-11$ //$NON-NLS-10$ //$NON-NLS-9$ //$NON-NLS-8$ //$NON-NLS-7$ //$NON-NLS-6$ //$NON-NLS-5$ //$NON-NLS-4$ //$NON-NLS-3$ //$NON-NLS-2$ //$NON-NLS-1$

	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
			int offset) {
		String prefix = stringUpto(viewer, Math.max(0, offset - 1));
		prefix = prefix.trim();
		System.out.println(prefix);
		List<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
		for (String proposalString : keywords) {
			if (proposalString.startsWith(prefix)) {
				System.out.println(offset-prefix.length());
				CompletionProposal completionProposal = new CompletionProposal(
						proposalString, offset-prefix.length(), prefix.length(), proposalString.length());
				proposals.add(completionProposal);
			}
		}
		return proposals.toArray(new ICompletionProposal[0]);
	}

	private String stringUpto(ITextViewer viewer, int index) {
		StringBuffer buffer = new StringBuffer();
		String text = viewer.getDocument().get();
		while ((index >= 0) && (text.charAt(index) != ' ' && text.charAt(index) != '\n')) {
			char c = text.charAt(index);
			index--;
			buffer.insert(0, c);
		}
		return buffer.toString();
	}

	public IContextInformation[] computeContextInformation(ITextViewer viewer,
			int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	public char[] getCompletionProposalAutoActivationCharacters() {
		return null;
	}

	public char[] getContextInformationAutoActivationCharacters() {
		// TODO Auto-generated method stub
		return null;
	}

	public IContextInformationValidator getContextInformationValidator() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
