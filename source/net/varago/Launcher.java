package net.varago;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Launcher extends JPanel implements ActionListener {

	private static final long serialVersionUID = -8682315389095747602L;

	protected JTextField textField, input;
	protected JTextArea textArea;
	private final static String newLine = "\n";
	private String storeAllString = "";
	JCheckBox checkbox = new JCheckBox("Upload to database");
	JCheckBox checkbox2 = new JCheckBox("New line");

	public static JPopupMenu.Separator SEPARATOR_ITEM, SEPARATOR_ITEM2;

	public Launcher() {
		super(new GridBagLayout());

		JMenuBar menuBar = new JMenuBar();
		SEPARATOR_ITEM = new JPopupMenu.Separator();
		SEPARATOR_ITEM2 = new JPopupMenu.Separator();

		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(fileMenu);

		JMenu fileMenu2 = new JMenu("Help");
		fileMenu2.setMnemonic(KeyEvent.VK_H);
		menuBar.add(fileMenu2);

		JMenuItem newMenuItem = new JMenuItem("Open");
		JMenuItem newMenuItem5 = new JMenuItem("Clear");
		JMenuItem newMenuItem2 = new JMenuItem("Exit");
		fileMenu.add(newMenuItem);
		fileMenu.add(newMenuItem5);
		fileMenu.add(SEPARATOR_ITEM);
		fileMenu.add(newMenuItem2);

		JMenuItem newMenuItem3 = new JMenuItem("About");
		JMenuItem newMenuItem4 = new JMenuItem("Instructions");
		fileMenu2.add(newMenuItem3);
		fileMenu2.add(SEPARATOR_ITEM2);
		fileMenu2.add(newMenuItem4);

		frame.setJMenuBar(menuBar);

		// Display the window.
		frame.pack();
		frame.setVisible(true);

		newMenuItem3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame,
						"            Created by Zaen Khilji\n Copyright (c) 2016 - 2017 "
								+ Settings.NAME, Settings.NAME, 1);
			}
		});

		newMenuItem4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane
						.showMessageDialog(
								frame,
								"1. Type in your name.\n2. Write what you did.\n3. Click enter/commit.",
								Settings.NAME, 1);
			}
		});

		newMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					FileReader read = new FileReader(Settings.PATH);
					Scanner scan = new Scanner(read);
					while (scan.hasNextLine()) {
						String temp = scan.nextLine() + "\n";
						storeAllString = storeAllString + temp;
					}
					textArea.setText(storeAllString);
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		});

		newMenuItem5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(null);
			}
		});

		newMenuItem2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null,
						"Are you sure you want to quit?", Settings.NAME,
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					System.exit(1);
				} else {
					return;
				}
			}
		});

		JButton btn = new JButton("Commit");
		btn.setPreferredSize(new Dimension(694, 50));

		JButton editBtn = new JButton("Toggle Editing");
		editBtn.setPreferredSize(new Dimension(694, 50));

		JButton databaseBtn = new JButton("Check Database");
		databaseBtn.setPreferredSize(new Dimension(694, 50));

		textField = new JTextField(20);
		textField.addActionListener(this);
		textField.setForeground(Color.GRAY);

		input = new JTextField(20);
		input.setForeground(Color.GRAY);

		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				handleWrite();
			}
		});

		editBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				textArea.setEditable(textArea.isEditable() ? false : true);
			}
		});

		databaseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JOptionPane.showMessageDialog(frame,
						"Unable to connect to database", Settings.NAME, 2);
			}
		});

		input.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				String text = input.getText();
			}
		});
		input.setText("Name");

		// add to a container

		// set state
		checkbox.setSelected(true);
		checkbox2.setSelected(false);

		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;

		c.fill = GridBagConstraints.HORIZONTAL;

		add(input, c);

		textArea = new JTextArea(10, 30);
		textArea.setEditable(false);
		textArea.setForeground(Color.BLACK);
		JScrollPane scrollPane = new JScrollPane(textArea);

		final DefineLineNumber lineNumberingTextArea = new DefineLineNumber(
				textArea);
		scrollPane.setRowHeaderView(lineNumberingTextArea);

		textArea.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent documentEvent) {
				lineNumberingTextArea.updateLineNumbers();
			}

			@Override
			public void removeUpdate(DocumentEvent documentEvent) {
				lineNumberingTextArea.updateLineNumbers();
			}

			@Override
			public void changedUpdate(DocumentEvent documentEvent) {
				lineNumberingTextArea.updateLineNumbers();
			}
		});

		// Add Components to this panel.

		add(textField, c);
		add(editBtn);
		add(databaseBtn);

		add(checkbox, c);
		add(checkbox2, c);

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(scrollPane, c);
		add(btn);
		try {
			if (!new File(Settings.PATH).exists())
				new File(Settings.PATH).createNewFile();
			FileReader read = new FileReader(Settings.PATH);
			Scanner scan = new Scanner(read);
			while (scan.hasNextLine()) {
				String temp = scan.nextLine() + "\n";
				storeAllString = storeAllString + temp;
			}
			textArea.setText(storeAllString);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void handleWrite() {
		if (textField.getText().equalsIgnoreCase("")) {
			JOptionPane.showMessageDialog(this, "Please write an update.",
					Settings.NAME, 2);
			return;
		}
		if (JOptionPane.showConfirmDialog(null,
				"Are you sure you want to write this log?", Settings.NAME,
				JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
			return;
		Calendar calender = Calendar.getInstance();
		String text = textField.getText();
		String name2 = input.getText();
		if (checkbox2.isSelected()) {
			textArea.append(newLine);
			write(newLine);
			checkbox2.setSelected(false);
		}
		textArea.append("[" + calender.getTime() + "]: " + fix(name2) + " - "
				+ fix(text) + " " + newLine);
		write("[" + calender.getTime() + "]: " + fix(name2) + " - " + fix(text)
				+ " " + newLine);
		textField.selectAll();

		// Make sure the new text is visible, even if there
		// was a selection in the text area.
		textArea.setCaretPosition(textArea.getDocument().getLength());
		JOptionPane.showMessageDialog(this, "The update has been saved.",
				Settings.NAME, 1);
		textField.setText(null);
	}

	public void actionPerformed(ActionEvent evt) {
		handleWrite();
	}

	private void write(String input) {
		final String FILE_PATH = Settings.PATH;
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					FILE_PATH, true));
			writer.write(input);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event dispatch thread.
	 */
	public static JFrame frame = new JFrame(Settings.NAME + " - Update Logger");

	public static String fix(String message) {
		StringBuilder newText = new StringBuilder();
		boolean wasSpace = true;
		boolean exception = false;
		for (int i = 0; i < message.length(); i++) {
			if (!exception) {
				if (wasSpace) {
					newText.append(("" + message.charAt(i)).toUpperCase());
					if (!String.valueOf(message.charAt(i)).equals(" ")) {
						wasSpace = false;
					}
				} else {
					newText.append(("" + message.charAt(i)).toLowerCase());
				}
			} else {
				newText.append(("" + message.charAt(i)));
			}
			if (String.valueOf(message.charAt(i)).contains(":")) {
				exception = true;
			} else if (String.valueOf(message.charAt(i)).contains(".")
					|| String.valueOf(message.charAt(i)).contains("!")
					|| String.valueOf(message.charAt(i)).contains("?")) {
				wasSpace = true;
			}
		}
		return newText.toString();
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}

				/*
				 * try { Settings.init(); } catch (IOException e) {
				 * e.printStackTrace(); }
				 */

				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				frame.setForeground(Color.GRAY);

				frame.add(new Launcher());

				frame.setResizable(false);

				frame.setPreferredSize(Settings.SIZE);
				frame.setSize(Settings.SIZE);
				frame.setLocationRelativeTo(null);
			}
		});
	}
}