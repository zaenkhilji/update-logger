package net.varago;

import javax.swing.JTextArea;
import javax.swing.text.Element;

public class DefineLineNumber extends JTextArea {

	/**
	 * @author: Pax M
	 */

	private JTextArea textArea;

	public DefineLineNumber(JTextArea textArea) {
		this.textArea = textArea;
		updateLineNumbers();
	}

	public void updateLineNumbers() {
		String lineNumbersText = getLineNumbersText();
		setText(lineNumbersText);
	}

	private String getLineNumbersText() {
		int caretPosition = textArea.getDocument().getLength();
		Element root = textArea.getDocument().getDefaultRootElement();
		StringBuilder lineNumbersTextBuilder = new StringBuilder();
		lineNumbersTextBuilder.append("1. ").append(System.lineSeparator());

		for (int elementIndex = 2; elementIndex < root
				.getElementIndex(caretPosition) + 2; elementIndex++)
			lineNumbersTextBuilder.append(elementIndex + ". ").append(
					System.lineSeparator());

		return lineNumbersTextBuilder.toString();
	}
}