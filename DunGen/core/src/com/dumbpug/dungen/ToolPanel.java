package com.dumbpug.dungen;

import com.dumbpug.dungen.src.Configuration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * The main tool panel.
 */
public class ToolPanel extends JFrame {
    /**
     * Creates a new instance of the ToolPanel class.
     */
    public ToolPanel() {
        // Create the main panel.
        JPanel mainPanel = new JPanel();
        this.add(mainPanel);

        //##############################################################
        // Create the first panel.
        // This panel holds fields used to populate the generator config.
        //##############################################################
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        mainPanel.add(leftPanel);

        Configuration config = new Configuration();

        // Add the SEED field.
        leftPanel.add(new JLabel("SEED: "));
        JTextField seedField = new JTextField(20);
        seedField.setText(Long.toString(config.seed));
        leftPanel.add(seedField);

        // Add the DUNGEON WIDTH field.
        leftPanel.add(new JLabel("DUNGEON WIDTH: "));
        JTextField dungeonWidthField = new JTextField(20);
        dungeonWidthField.setText(Integer.toString(config.width));
        leftPanel.add(dungeonWidthField);

        // Add the DUNGEON HEIGHT field.
        leftPanel.add(new JLabel("DUNGEON HEIGHT: "));
        JTextField dungeonHeightField = new JTextField(10);
        dungeonHeightField.setText(Integer.toString(config.height));
        leftPanel.add(dungeonHeightField);

        // Add the MAX ROOM SIZE field.
        leftPanel.add(new JLabel("MAX ROOM SIZE: "));
        JTextField maxRoomSizeField = new JTextField(20);
        maxRoomSizeField.setText(Integer.toString(config.maxRoomSize));
        leftPanel.add(maxRoomSizeField);

        // Add the MIN ROOM SIZE field.
        leftPanel.add(new JLabel("MIN ROOM SIZE: "));
        JTextField minRoomSizeField = new JTextField(20);
        minRoomSizeField.setText(Integer.toString(config.minRoomSize));
        leftPanel.add(minRoomSizeField);

        // Add the ROOM BUFFER field.
        leftPanel.add(new JLabel("ROOM BUFFER: "));
        JTextField roomBufferField = new JTextField(20);
        roomBufferField.setText(Integer.toString(config.roomBuffer));
        leftPanel.add(roomBufferField);

        // Add the ROOM COUNT field.
        leftPanel.add(new JLabel("ROOM COUNT: "));
        JTextField roomCountField = new JTextField(20);
        roomCountField.setText(Integer.toString(config.rooms));
        leftPanel.add(roomCountField);

        // Add the CORRIDOR WIDTH field.
        leftPanel.add(new JLabel("CORRIDOR WIDTH: "));
        JTextField corridorWidthField = new JTextField(20);
        corridorWidthField.setText(Integer.toString(config.corridorWidth));
        leftPanel.add(corridorWidthField);

        leftPanel.add(new JLabel(" "));

        // Add the generate button.
        JButton generateButton = new JButton("Generate!");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onGenerateButtonClick();
            }
        });
        leftPanel.add(generateButton);

        //##############################################################

        //##############################################################
        // Create the second panel.
        //##############################################################
        JPanel centerPanel = new JPanel();
        mainPanel.add(centerPanel);



        //##############################################################

        //##############################################################
        // Create the third panel.
        //##############################################################
        JPanel rightPanel = new JPanel();
        mainPanel.add(rightPanel);

        seedField = new JTextField(20);
        rightPanel.add(seedField);

        //##############################################################

        this.setTitle("DunGen Editor");
        this.pack();
        this.setVisible(true);
    }

    private void onGenerateButtonClick() {
        System.out.println("wowerz!");
    }
}
