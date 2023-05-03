package ui;

import model.Event;
import model.EventLog;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//creates a new frame for the purpose of entering a new Team name
public class EnterNameFrame extends JFrame {

    private JTextField teamName;
    private JPanel teamNameFrame;
    private TeamApp app;
    private String name;

    //MODIFIES: this
    //EFFECTS: creates new frame with a label and a text field for entering a name
    public EnterNameFrame() {
        setFrame();
        teamName.addKeyListener(new KeyAdapter() {
            //MODIFIES: this, MainFrame, TeamApp
            //EFFECTS: creates new KeyEvent for the text field. When pressed enter, will open new MainFrame
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (teamName.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "have to enter a name",
                                "invalid name",JOptionPane.WARNING_MESSAGE);
                    } else {
                        name = teamName.getText();
                        app = new TeamApp();
                        app.createNewTeamGUI(name);
                        EventLog.getInstance().logEvent(new Event("team " + name + " created"));
                        EnterNameFrame.super.setVisible(false);
                        new MainFrame(app);
                    }
                }
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: creates new frame and panel that places all elements required for user interaction
    private void setFrame() {
        this.setResizable(false);
        this.setVisible(true);
        setBackground(Color.CYAN);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 349, 102);
        teamNameFrame = new JPanel();
        teamNameFrame.setBackground(new Color(175, 238, 238));
        teamNameFrame.setBorder(new LineBorder(new Color(102, 205, 170), 3, true));
        setContentPane(teamNameFrame);
        teamNameFrame.setLayout(null);
        JLabel newTeamName = new JLabel("Give your team a name:");
        newTeamName.setBounds(6, 6, 154, 16);
        teamNameFrame.add(newTeamName);
        teamName = new JTextField();
        teamName.setBounds(5, 30, 280, 25);
        teamNameFrame.add(teamName);
        teamName.setColumns(10);
    }


}


