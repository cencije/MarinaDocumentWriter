import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.plaf.LayerUI;
import javax.swing.text.JTextComponent;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.imageio.ImageIO;

/**
 * Marina: DocumentWriter is a Basic Text Writer with various functionalities
 * for people who do not have a program to write documents and other text files.
 * 
 * @author Joseph E. Cenci
 * @version March 7th, 2017. Joseph E. Cenci 2017
 */
public class DocumentGUI extends JPanel implements ActionListener
{
    // instance variables - replace the example below with your own
    JFrame mainFrame;

    JLabel lblDocName, topBarColor, dummyLabel;
    JButton btnNewDocument, btnSaveFile, btnLoadFile, btnSearchFile, btnClear;
    JTextField tfDocumentName, tfWord;
    JTextArea tfDocumentArea;
    JScrollPane documentPane;

    Scanner scanner = null;
    BufferedWriter fileWriter = null;
    String currentFile = null;
    FileWriter fw = null;

    BufferedReader fileReader = null;
    FileReader fr = null;
    ArrayList<String> fileLines = null;

    Color lightBlue = new Color(205,255,255);
    Color lightOrange = new Color(255, 204, 128);
    Color mintGreen = new Color(204, 255, 204);
    Color lightGreen = new Color(204, 255, 153);
    Color darkerGreen = new Color(196, 255, 77);
    Color darkestGreen = new Color(102, 255, 51);
    Color aqua = new Color(0, 255, 255);
    Color darkerBlue = new Color(0, 128, 255);
    Color yellow = new Color(255, 255, 0);
    Color lightPurple = new Color(204, 204, 255);
    ImageIcon iconImage = new ImageIcon("Pictures/MarinaFaviconJPG.jpg", "icon");
    ImageIcon qMarkIcon = new ImageIcon("Pictures/QuestionMark.png", "QuestionMark");
    Highlighter highlighter;
    HighlightPainter painter =  new DefaultHighlighter.DefaultHighlightPainter(Color.CYAN);
    public static void main() {
        DocumentGUI dGUI = new DocumentGUI();
        dGUI.createGUI();
    }

    /**
     * Constructor for objects of class DocumentGUI
     */
    public DocumentGUI()
    {
    }

    public void createGUI() {
        mainFrame = new JFrame();
        mainFrame.getContentPane().setBackground(lightGreen);
        mainFrame.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, aqua));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setBounds(0,0,510,810);
        mainFrame.getContentPane().setLayout(new BorderLayout());
        mainFrame.setResizable(false);
        mainFrame.setIconImage(iconImage.getImage());
        mainFrame.setTitle("Document Writer");
        
        btnNewDocument = new JButton("New Document");
        btnNewDocument.setBounds(1,1,130,30);
        btnNewDocument.addActionListener(this);
        mainFrame.add(btnNewDocument);

        btnSaveFile = new JButton("Save File");
        btnSaveFile.setBounds(131,1,80,30);
        btnSaveFile.addActionListener(this);
        mainFrame.add(btnSaveFile);

        btnLoadFile = new JButton("Load File");
        btnLoadFile.setBounds(212,1,80,30);
        btnLoadFile.addActionListener(this);
        mainFrame.add(btnLoadFile);

        btnSearchFile = new JButton("Search Word");
        btnSearchFile.setBounds(285, 1, 110, 30);
        btnSearchFile.addActionListener(this);
        mainFrame.add(btnSearchFile);

        tfWord = new JTextField("", 30);
        tfWord.setBounds(390, 1, 50, 30);
        tfWord.setEditable(false);
        mainFrame.add(tfWord);

        btnClear = new JButton("Clean");
        btnClear.setBounds(440,1,60,30);
        btnClear.addActionListener(this);
        btnClear.setEnabled(false);
        mainFrame.add(btnClear);
        
        lblDocName = new JLabel("<HTML><B>Document Name:</B></HTML>");
        lblDocName.setBounds(10, 35, 120, 30);
        mainFrame.add(lblDocName);

        tfDocumentName = new JTextField("", 40);
        tfDocumentName.setBounds(130, 35, 360, 30);
        tfDocumentName.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
        mainFrame.add(tfDocumentName);

        tfDocumentArea = new JTextArea(10, 10);
        tfDocumentArea.setLineWrap(true);
        tfDocumentArea.setWrapStyleWord(true);
        highlighter = tfDocumentArea.getHighlighter();
        documentPane = new JScrollPane(tfDocumentArea);
        documentPane.setBounds(10, 80,480, 690);
        documentPane.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
        mainFrame.add(documentPane);

        topBarColor = new JLabel("");
        topBarColor.setBackground(lightPurple);
        topBarColor.setBorder(BorderFactory.createMatteBorder(0,0,2,0, aqua));
        topBarColor.setBounds(0, 0, 510, 35);
        topBarColor.setOpaque(true);
        mainFrame.add(topBarColor);
        
        dummyLabel = new JLabel("");
        mainFrame.add(dummyLabel);

        
        mainFrame.setVisible(true);

    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getActionCommand().equals("New Document")) {
            if (!tfDocumentName.getText().trim().isEmpty() || !tfDocumentArea.getText().trim().isEmpty()) {
                Object[] options = {"Yes", "No", "Cancel"};
                int changes = JOptionPane.showOptionDialog(mainFrame,
                        "Changes have been made in the document.\nDo you wish to save them?",
                        "Warning: Changes may be lost",
                        JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,
                        qMarkIcon, options, options[0]);
                if (changes == JOptionPane.YES_OPTION) {
                    try {
                        String docSaveName = JOptionPane.showInputDialog(mainFrame, "Name of file to be saved:");
                        if (docSaveName == null) {}
                        else {
                            currentFile = "TextFiles/" + docSaveName + ".txt";
                            File fold = new File(currentFile);
                            fold.delete();
                            fileWriter = new BufferedWriter(new FileWriter(currentFile, true));
                            fileWriter.write(tfDocumentArea.getText());
                            fileWriter.close();
                        }
                    } catch (Exception e) {
                        System.out.println("Exception caught."); // Prints if an error is found
                    } 
                } else if (changes == JOptionPane.NO_OPTION) {
                    tfDocumentName.setText("");
                    tfDocumentArea.setText("");
                }
            }
        }
        if (evt.getActionCommand().equals("Save File")) {
            if (!tfDocumentName.getText().trim().isEmpty()) {
                try {
                    currentFile = "TextFiles/" + tfDocumentName.getText() + ".txt";
                    File fold = new File(currentFile);
                    fold.delete();
                    fileWriter = new BufferedWriter(new FileWriter(currentFile, true));
                    fileWriter.write(tfDocumentArea.getText());
                    fileWriter.close();

                } catch (Exception e) {
                    System.out.println("Exception caught."); // Prints if an error is found
                } 
            }
            else {
                try {
                    String docSaveName = JOptionPane.showInputDialog(mainFrame, " Name of file to be saved:");
                    if (docSaveName == ""){}
                    else {
                        currentFile = "TextFiles/" + docSaveName + ".txt";
                        File fold = new File(currentFile);
                        fold.delete();
                        fileWriter = new BufferedWriter(new FileWriter(currentFile, true));
                        fileWriter.write(tfDocumentArea.getText());
                        fileWriter.close();
                    }
                } catch (Exception e) {
                    System.out.println("Exception caught."); // Prints if an error is found
                } 
            }
        }
        if (evt.getActionCommand().equals("Load File")) {
            if (!tfDocumentName.getText().trim().isEmpty()) {
                try {
                    fileLines = new ArrayList<String>();
                    currentFile = "TextFiles/" + tfDocumentName.getText() + ".txt";
                    fileReader = new BufferedReader(new FileReader(currentFile));
                    String line;
                    while ((line = fileReader.readLine()) != null) {
                        fileLines.add(line);
                    }
                    String documentLines = "";
                    for (int i = 0; i < fileLines.size(); i ++) {
                        documentLines += fileLines.get(i) + "\n";
                    }
                    tfDocumentArea.setText(documentLines);
                    fileLines.clear();
                    fileReader.close();
                } catch (Exception e) {

                    JOptionPane.showMessageDialog(mainFrame, "File does not exist ", "Loading Issue",0, qMarkIcon);
                } 
            }
        }
        if(evt.getActionCommand().equals("Search Word")) {
            if (!tfDocumentArea.getText().trim().isEmpty()) {
                String searchTerm = JOptionPane.showInputDialog(mainFrame, "Type token to search for:");
                if (searchTerm == null) {}
                else {
                    try {
                        ArrayList<Integer> indices = new ArrayList<Integer>();
                        scanner = new Scanner(tfDocumentArea.getText());
                        String allText = tfDocumentArea.getText();
                        int instanceCounter = 0;
                        int counter = 0;
                        int paragraphCounter = 0;
                        int wordSize = 0;
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            Scanner lineScanner = new Scanner(line);
                            while (lineScanner.hasNext()) {
                                String word = lineScanner.next();
                                if (wordSize == 0) wordSize += word.length();
                                else wordSize += word.length() + 1;
                                if (word.equals(searchTerm) || word.equals(searchTerm + ".") ||  word.equals(searchTerm + "?") || 
                                    word.equals(searchTerm + "!") || word.equals(searchTerm + ",")) {
                                    int start = tfDocumentArea.getText().indexOf(word);
                                    indices.add(start);
                                    int wordLength = start + searchTerm.length();
                                    highlighter.addHighlight(wordSize - word.length() + counter, wordSize + counter, painter);
                                    instanceCounter++;
                                }
                            }
                            if (line.equals("")) counter++;
                            paragraphCounter++;
                        }
                        if (instanceCounter > 0) btnClear.setEnabled(true);
                        tfWord.setText(Integer.toString(instanceCounter));
                    } catch (Exception e) {
                        tfWord.setText("Error!");
                    }
                }
            }
        }
        if(evt.getActionCommand().equals("Clean")) {
            highlighter.removeAllHighlights();
            tfWord.setText("");
            btnClear.setEnabled(false);
        }
    }
}
