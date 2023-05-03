package ui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

// Creates the first frame that the user will interact with.
// will display 2 options: to load old team, or to create a new one
public class FirstFrame extends JFrame {

    private ActionListener createTeamListener;
    private ActionListener loadTeamListener;
    private JPanel firstPane;
    private TeamApp app;

    //MODIFIES: this
    //EFFECTS: sets all the details of the frame necessary for user interaction
    public FirstFrame() {
        setFrame();
        createTitle();
        setImage();
        JButton newTeam = createButton("Create new team", 125);
        newTeam.addActionListener(createTeamListener);
        JButton oldTeamButton = createButton("Load old team", 150);
        oldTeamButton.addActionListener(loadTeamListener);
        this.setResizable(false);
        this.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: creates new frame and panel to place all elements on. Sets the size and positioning of the frame,
    //          and sets all the button actions
    private void setFrame() {
        setBackground(Color.CYAN);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 550, 375);
        firstPane = new JPanel();
        firstPane.setBackground(new Color(175, 238, 238));
        firstPane.setBorder(new LineBorder(new Color(102, 205, 170), 3, true));
        setContentPane(firstPane);
        firstPane.setLayout(null);

        setActionListeners();
    }

    //MODIFIES: this
    //EFFECTS: creates and places "./data/SwimmerImage.png" image onto firstPane
    private void setImage() {
        ImageIcon image = new ImageIcon("./data/SwimmerImage.png");
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(image);
        imageLabel.setBounds(275, 175, 400, 200);
        firstPane.add(imageLabel);
    }

    //MODIFIES: this
    //EFFECTS: creates and places the title of the frame onto the panel.
    private void createTitle() {
        JLabel tittle = new JLabel("Swimteam Tracker");
        tittle.setFont(new Font("MV Boli", Font.BOLD, 30));
        tittle.setBounds(130, 20, 300, 56);
        firstPane.add(tittle);
    }

    //MODIFIES: this
    //EFFECTS: creates new buttons with variable label and y-coordinate, and places them onto the firstPane
    private JButton createButton(String label, int valueY) {
        JButton newButton = new JButton(label);
        newButton.setBounds(160, valueY, 210, 30);
        firstPane.add(newButton);
        return newButton;
    }


    //EFFECTS: sets all the button actions
    private void setActionListeners() {
        setLoadTeamListener();
        setCreateTeamListener();
    }

    //MODIFIES: this
    //EFFECTS: sets action to load in old team from ".\data\team.json" for load button
    private void setLoadTeamListener() {
        loadTeamListener = e -> {
            JOptionPane.showMessageDialog(null, "Old team has been loaded",
                    "Team Loaded",JOptionPane.INFORMATION_MESSAGE);
            this. setVisible(false);
            app = new TeamApp();
            app.loadTeam();
            new MainFrame(app);
        };
    }

    //MODIFIES: this
    //EFFECTS: sets action to create new team for create team button
    private void setCreateTeamListener() {
        createTeamListener = e -> {
            this. setVisible(false);
            new EnterNameFrame();
        };
    }
}
