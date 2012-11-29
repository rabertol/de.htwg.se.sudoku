package de.htwg.sudoku.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import de.htwg.util.observer.IObserver;

public class SudokuFrame extends JFrame implements IObserver {

	private static final int DEFAULT_Y = 630;
	private static final int DEFAULT_X = 528;
	private Container pane;
	private GridPanel gridPanel;
	private HighlightButtonPanel digitPanel;
	private StatusPanel statusPanel;
	private String status;

	private static final long serialVersionUID = 1L;

	public SudokuFrame(final ISudokuController controller) {
		
		controller.addObserver(this);
		
		JMenuBar menuBar;

		JMenu fileMenu;
		JMenuItem newMenuItem,  quitMenuItem;

		JMenu editMenu;
		JMenuItem undoMenuItem, redoMenuItem, copyMenuItem, pasteMenuItem;

		JMenu solveMenu;
		JMenuItem solveMenuItem;


		JMenu digitMenu;
		JMenuItem noneMenuItem, digitMenuItem;

		JMenu optionsMenu;
		JMenuItem showMenuItem;

		status = controller.getStatus();

		setTitle("HTWG Sudoku");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(DEFAULT_X, DEFAULT_Y);
		pane = getContentPane();
		pane.setLayout(new BorderLayout());

		menuBar = new JMenuBar();

		/*
		 * File Menu
		 */

		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);

		newMenuItem = new JMenuItem("New");
		newMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.create();
			}
		});
		newMenuItem.setMnemonic(KeyEvent.VK_N);
		newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				InputEvent.CTRL_DOWN_MASK));

		fileMenu.add(newMenuItem);
		
		fileMenu.addSeparator();

		quitMenuItem = new JMenuItem("Quit");
		quitMenuItem.setMnemonic(KeyEvent.VK_Q);
		quitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				InputEvent.CTRL_DOWN_MASK));
		quitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				controller.exit();
			}
		});
		fileMenu.add(quitMenuItem);

		menuBar.add(fileMenu);

		/*
		 * Edit Menu
		 */
		editMenu = new JMenu("Edit");
		editMenu.setMnemonic(KeyEvent.VK_E);
		
		editMenu = new JMenu("Edit");
		editMenu.setMnemonic(KeyEvent.VK_E);

		undoMenuItem = new JMenuItem("Undo");
		undoMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.undo();
			}
		});
		undoMenuItem.setMnemonic(KeyEvent.VK_U);
		undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
				InputEvent.CTRL_DOWN_MASK));
		editMenu.add(undoMenuItem);

		redoMenuItem = new JMenuItem("Redo");
		redoMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.redo();
			}
		});
		redoMenuItem.setMnemonic(KeyEvent.VK_R);
		redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,
				InputEvent.CTRL_DOWN_MASK));
		editMenu.add(redoMenuItem);
		
		editMenu.addSeparator();

		copyMenuItem = new JMenuItem("Copy"); 
		copyMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.copy();
			}
		});
        copyMenuItem.setMnemonic(KeyEvent.VK_C);
        copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_C,
                InputEvent.CTRL_DOWN_MASK));
		editMenu.add(copyMenuItem);

		pasteMenuItem = new JMenuItem("Paste"); 
		pasteMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.paste();
			}
		});
        pasteMenuItem.setMnemonic(KeyEvent.VK_P);
        pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_V,
                InputEvent.CTRL_DOWN_MASK));
		editMenu.add(pasteMenuItem);

		menuBar.add(editMenu);

		/*
		 * Solve Menu
		 */
		solveMenu = new JMenu("Solve");
		solveMenu.setMnemonic(KeyEvent.VK_S);

		solveMenuItem = new JMenuItem("Solve");
		solveMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.solve();
			}
		});
		solveMenuItem.setMnemonic(KeyEvent.VK_S);
		solveMenuItem
				.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0));

		solveMenu.add(solveMenuItem);
		menuBar.add(solveMenu);
		
		/*
		 * Highlight Menu
		 */

		digitMenu = new JMenu("Highlight");
		digitMenu.setMnemonic(KeyEvent.VK_H);

		noneMenuItem = new JMenuItem("none");
		noneMenuItem.setMnemonic(KeyEvent.VK_0);
		noneMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,
				0));
		noneMenuItem.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				controller.highlight(0);
			}

		});
		digitMenu.add(noneMenuItem);

		int[] fkey = { KeyEvent.VK_F10, KeyEvent.VK_F1, KeyEvent.VK_F2,
				KeyEvent.VK_F3, KeyEvent.VK_F4, KeyEvent.VK_F5, KeyEvent.VK_F6,
				KeyEvent.VK_F7, KeyEvent.VK_F8, KeyEvent.VK_F9 };
		int[] dkey = { KeyEvent.VK_0, KeyEvent.VK_1, KeyEvent.VK_2,
				KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_5, KeyEvent.VK_6,
				KeyEvent.VK_7, KeyEvent.VK_8, KeyEvent.VK_9 };

		for (int digit = 1; digit <= controller.getGridSize(); digit++) {
			final int fixdigit = digit;
			digitMenuItem = new JMenuItem(Integer.toString(digit));
			digitMenuItem.setMnemonic(dkey[digit]);
			digitMenuItem
					.setAccelerator(KeyStroke.getKeyStroke(fkey[digit], 0));
			digitMenu.add(digitMenuItem);
			digitMenuItem.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent arg0) {
					controller.highlight(fixdigit);
				}
			});
		}

		menuBar.add(digitMenu);

		/*
		 * Options Menu
		 */

		optionsMenu = new JMenu("Options");
		optionsMenu.setMnemonic(KeyEvent.VK_O);

		showMenuItem = new JMenuItem("toggle show Candidates");
		showMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.showAllCandidates();
			}
		});
		showMenuItem.setMnemonic(KeyEvent.VK_S);
		showMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				InputEvent.CTRL_DOWN_MASK));

		optionsMenu.add(showMenuItem);
		menuBar.add(optionsMenu);
		

		setJMenuBar(menuBar);
		constructSudokuPane(controller);
	}

	public void constructSudokuPane(ISudokuController controller) {
		if (digitPanel != null){
			pane.remove(digitPanel);
		}
		digitPanel = new HighlightButtonPanel(controller);
		pane.add(digitPanel, BorderLayout.NORTH);

		if (gridPanel != null){
			pane.remove(gridPanel);
		}
		gridPanel = new GridPanel(controller);
		pane.add(gridPanel, BorderLayout.CENTER);

		if (statusPanel != null){
			pane.remove(statusPanel);
		}
		statusPanel = new StatusPanel();
		pane.add(statusPanel, BorderLayout.SOUTH);
		setVisible(true);
		repaint();
	}

	public void update() {
		statusPanel.setText(status);
		repaint();
	}

}