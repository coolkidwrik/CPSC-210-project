package ui;

import model.Swimmer;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// creates a new frame that will display all the members of the team
public class ViewFrame extends JFrame {

    private JPanel viewPanel;
    private TeamApp app;
    private JFrame mainFrame;
    private int frameHeight = 400;

    //MODIFIES: this
    //EFFECTS: creates a new JFrame that displays all team members on the team
    public ViewFrame(TeamApp app, JFrame mainFrame) {
        setFrame(app, mainFrame);
        setLabels();
        setImage();
        this.addKeyListener(new KeyListener() {
            //Modifies: this, MainFrame
            //EFFECTS: creates a new key event, when pressed enter, returns to main menu and makes this invisible
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    ViewFrame.super.setVisible(false);
                    mainFrame.setVisible(true);
                }
            }
            //EFFECTS: does nothing

            @Override
            public void keyTyped(KeyEvent e) {}
            //EFFECTS: does nothing

            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }

    //MODIFIES: this
    //EFFECTS: creates and sets all details and components of the frame
    private void setFrame(TeamApp app, JFrame mainFrame) {
        this.app = app;
        this.mainFrame = mainFrame;
        setResizable(false);
        setVisible(true);
        setBackground(Color.CYAN);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, frameHeight);
        viewPanel = new JPanel();
        viewPanel.setBackground(new Color(175, 238, 238));
        viewPanel.setBorder(new LineBorder(new Color(102, 205, 170), 2, true));
        setContentPane(viewPanel);
        viewPanel.setLayout(null);
    }

    //MODIFIES: this
    //EFFECTS: sets image "./data/girlDiving.png" onto the panel
    private void setImage() {
        ImageIcon image = new ImageIcon("./data/girlDiving.png");
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(image);
        imageLabel.setBounds(270, frameHeight - 325, 450, 300);
        viewPanel.add(imageLabel);
    }

    //MODIFIES: this
    //EFFECTS: creates and adds all labels onto the panel
    private void setLabels() {
        JLabel lblNewLabel = new JLabel(app.getTeam().getName() + " Swim Team");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 30));
        lblNewLabel.setBounds(5, 5, 400, 35);
        viewPanel.add(lblNewLabel);

        createMemberLabels();
    }

    //MODIFIES: this
    //EFFECTS: creates new labels with team members' names
    //          and changes the bounds of the frame to fit list
    private void createMemberLabels() {
        int valueY = 75;
        if (app.getTeam().teamSize() == 0) {
            createLabel("No members on team", valueY);
            createLabel("Press 'enter' to return", valueY + 30);
        } else {
            createLabel("Team Members: ", valueY);
            valueY += 50;
            for (Swimmer s : app.getTeam().getTeam()) {
                if (s.isCaptain()) {
                    createLabel("Team captain: " + s.getName(), valueY);
                } else if (s.isViceCaptain()) {
                    createLabel("Vice-captain: " + s.getName(), valueY);
                } else {
                    createLabel("                    " + s.getName(), valueY);
                }
                valueY += 40;
            }
            createLabel("Press 'enter' to return", valueY - 10);
            changeBounds(valueY);
        }
    }

    //MODIFIES: this
    //EFFECTS: creates and adds a new labels onto the panel
    private void createLabel(String member, int valueY) { //75
        JLabel newLabel = new JLabel(member);
        newLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        newLabel.setBounds(5, valueY, 190, 15);
        viewPanel.add(newLabel);
    }

    //MODIFIES: this
    //EFFECTS: changes the bounds of the frame to fit list
    private void changeBounds(int valueY) {
        if (valueY > 400) {
            frameHeight = valueY + 60;
            setBounds(100, 100, 500, frameHeight);
        }
    }
}
