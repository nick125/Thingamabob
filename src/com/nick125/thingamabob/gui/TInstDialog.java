package simulation.gui;

import simulation.machine.core.SimpleInst;
import simulation.machine.core.SimpleInst.InstructionEntry;
import simulation.machine.core.SimpleInstSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a dialog box that allows the user to create an instruction set.
 *
 * @author baumbata. Created Oct 27, 2010.
 */
public class TInstDialog extends JFrame {
    private static final Dimension SIZE = new Dimension(450, 400);
    private static final String TITLE = "Instruction Editor Dialg";
    private ArrayList<LinePanel> lines;

    private int numLines;
    private JScrollPane linesPane;
    private JPanel linesPanel;

    // private JTable instructionTable;

    /**
     * Creates a blank instruction dialog box.
     */
    public TInstDialog() {
        this.numLines = 0;
        this.lines = new ArrayList<LinePanel>(0);
        this.setSize(TInstDialog.SIZE);
        this.setTitle(TInstDialog.TITLE);
        this.setBackground(Color.DARK_GRAY);
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton addLineButton = new JButton("Add Line");
        addLineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                TInstDialog.this.addLine();
            }
        });
        JButton finalizeButton = new JButton("Write Code");
        finalizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String sToFile = TInstDialog.this.finalizef();
                JFileChooser chooser = new JFileChooser();
                File saveTo = null;
                if (chooser.showSaveDialog(TInstDialog.this) == JFileChooser.APPROVE_OPTION) {
                    saveTo = chooser.getSelectedFile();
                }
                try {
                    BufferedWriter out = new BufferedWriter(new FileWriter(saveTo));
                    String[] theLines = sToFile.split("\n");
                    for (String line : theLines) {
                        out.write(line);
                        out.newLine();
                    }
                    out.close();
                } catch (FileNotFoundException exception) {
                    exception.printStackTrace();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });

        this.linesPanel = new JPanel();
        this.linesPanel.setSize(new Dimension(500, 800));
        this.linesPanel.setBackground(Color.BLACK);
        this.linesPanel.setLayout(new BoxLayout(this.linesPanel,
                BoxLayout.Y_AXIS));
        this.linesPanel.setVisible(true);
        this.linesPane = new JScrollPane(this.linesPanel);
        this.linesPane.setPreferredSize(new Dimension(400, 300));
        // this.linePane.setLayout(new ScrollPaneLayout());
        // this.linePane.se
        this.add(this.linesPane);
        this.add(addLineButton);
        this.add(finalizeButton);
        // this.instructionTable = new JTable();
    }

    /**
     * Initializes the dialog from a provided instruction set.
     *
     * @param startSet The initial instructions
     */
    public void fromSet(SimpleInstSet<String> startSet) {
        for (int i = 0; i < startSet.size(); i++) {
            SimpleInst<String> currentInst = (SimpleInst<String>) startSet.getInstruction(i);
            Map<String, InstructionEntry<String>> lineMap = currentInst.getLines();
            for (String symbol : lineMap.keySet()) {
                this.addLine();
                this.lines.get(this.numLines - 1).getMnemonic().setText(currentInst.getMnemonic());
                this.lines.get(this.numLines - 1).getSymbolEncountered().setText(symbol);
                InstructionEntry<String> currentInstLine = lineMap.get(symbol);
                this.lines.get(this.numLines - 1).getSymbolWrite().setText(currentInstLine.getNewTapeValue());
                this.lines.get(this.numLines - 1).getNextDirection().setSelectedIndex(currentInstLine.getNextDirection());
                this.lines.get(this.numLines - 1).getNextInstruction().setText(currentInstLine.getNextInstruction());

            }
        }
    }

    /**
     * Adds a line to the dialog box
     */
    protected void addLine() {
        this.lines.add(new LinePanel());
        if (this.numLines > 0) {
            this.linesPanel.remove(2 * this.numLines);
        }
        this.linesPanel.add(this.lines.get(this.numLines));
        this.linesPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        this.linesPanel.add(Box.createVerticalGlue());
        this.linesPane.revalidate();
        this.numLines++;
    }

    private String finalizef() {
        String theInstructions = "";
        Map<String, String> mnemonicMap = new HashMap<String, String>();
        for (int i = 0; i < this.numLines; i++) {
            String mnemonic = this.lines.get(i).getMnemonic().getText();
            if (!mnemonicMap.containsKey(mnemonic)) {
                mnemonicMap.put(mnemonic, "");
            }
            String newLine = "";
            newLine += "\tIF " + this.lines.get(i).getSymbolEncountered().getText() + "\n";
            String nextSymbol = this.lines.get(i).getSymbolWrite().getText();
            if (nextSymbol.compareTo("") != 0) {
                newLine += "\t\tWRITE " + nextSymbol + "\n";
            }
            int nextDirection = this.lines.get(i).getNextDirection().getSelectedIndex();
            if (nextDirection > 0) {
                newLine += "\t\tMOVE ";
                if (nextDirection == 1) {
                    newLine += "LEFT";
                } else {
                    newLine += "RIGHT";
                }
                newLine += "\n";
            }
            String nextInstruction = this.lines.get(i).getNextInstruction().getText();
            if (nextInstruction.compareTo("") == 0) {
                nextInstruction = mnemonic;
            }
            newLine += "\t\t";
            if (nextInstruction.compareToIgnoreCase("HALT") == 0) {
                newLine += "HALT";
            } else {
                newLine += "GOTO " + nextInstruction;
            }
            newLine += "\n\tENDIF\n";
            mnemonicMap.put(mnemonic, mnemonicMap.get(mnemonic) + newLine);
        }
        for (String instruction : mnemonicMap.keySet()) {
            theInstructions += "DEFINE " + instruction + "\n" + mnemonicMap.get(instruction) + "ENDDEFINE\n";
        }
        return theInstructions;
	}
}
