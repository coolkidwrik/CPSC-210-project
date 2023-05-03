package ui;

import model.EventLog;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

//Creates a new frame with main user options: show team, edit team, save team, quit app
public class MainFrame extends JFrame {

    private JPanel mainPane;
    private TeamApp app;

    private ActionListener viewTeamListener;
    private ActionListener editTeamListener;
//    private ActionListener editStatsListener;
    private ActionListener saveTeamListener;
    private ActionListener quitListener;

    //EFFECTS: creates a new frame meant to present the main options to user.
    //          adds and places all the necessary components required for interaction.
    public MainFrame(TeamApp app) {
        this.app = app;
        this.setResizable(false);
        this.setVisible(true);
        setActionListeners();
        setFrame();
        setImage();
        createTitle();

        JButton viewTeamButton = createButton("View Team Members", 100);
        viewTeamButton.addActionListener(viewTeamListener);
        JButton editTeamButton = createButton("Edit team", 140);
        editTeamButton.addActionListener(editTeamListener);
        //JButton editStatsButton = createButton("Edit statistics", 180);
        JButton saveTeamButton = createButton("Save team", 220 - 40);
        saveTeamButton.addActionListener(saveTeamListener);
        JButton quitTeamButton = createButton("Quit app", 260 - 40);
        quitTeamButton.addActionListener(quitListener);
    }

    //MODIFIES: this
    //EFFECTS: creates and sets all details and components of the frame
    private void setFrame() {
        setBackground(Color.CYAN);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 550, 400);
        mainPane = new JPanel();
        mainPane.setForeground(Color.WHITE);
        mainPane.setBackground(new Color(175, 238, 238));
        mainPane.setBorder(new LineBorder(new Color(102, 205, 170), 2, true));
        setContentPane(mainPane);
        mainPane.setLayout(null);
        JLabel chooseOption = new JLabel("Choose one of the following options:");
        chooseOption.setBounds(20, 65, 241, 16);
        mainPane.add(chooseOption);
    }


    //EFFECTS: creates label that is the title on the frame, and placed it on the panel
    private void createTitle() {
        JLabel tittle = new JLabel("Swimteam Tracker");
        tittle.setFont(new Font("Lucida Grande", Font.BOLD, 20));
        tittle.setBounds(160, 0, 210, 55);
        mainPane.add(tittle);
    }

    //EFFECTS: creates a new button with a variable name and y-coordinate
    private JButton createButton(String label, int valueY) {
        JButton newButton = new JButton(label);
        newButton.setBounds(50, valueY, 217, 30);
        mainPane.add(newButton);
        return newButton;
    }

    //MODIFIES: this
    //EFFECTS: sets image "./data/womanSwimming.png" onto the panel
    private void setImage() {
        ImageIcon image = new ImageIcon("./data/womanSwimming.png");
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(image);
        imageLabel.setBounds(323, 83, 450, 300);
        mainPane.add(imageLabel);
    }

    //MODIFIES: this, ViewFrame, TeamApp, SwimTeam, EditTeamFrame
    //EFFECTS: sets actions to perform for each button
    private void setActionListeners() {
        //MODIFIES: this, ViewFrame
        //EFFECTS: sets actions to create new ViewFrame and set this to invisible
        viewTeamListener = e -> {
            this.setVisible(false);
            new ViewFrame(app, this);
        };
        //EFFECTS: saves current team to ".\data\team.json"
        saveTeamListener = e -> {
            app.saveTeam();
            JOptionPane.showMessageDialog(null, "Team has been saved",
                    "saved team",JOptionPane.INFORMATION_MESSAGE);
        };
        //MODIFIES: EditTeam
        //EFFECTS: sets actions to create new EditTeamFrame
        editTeamListener = e -> new EditTeamFrame(app, this);

        //EFFECTS: sets actions to quit program
        quitListener = e -> {
            System.out.println("\n\n");
            app.printLog(EventLog.getInstance());
            System.exit(0);
        };
    }
}
