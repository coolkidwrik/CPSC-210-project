package ui;

import model.Event;
import model.EventLog;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

//generates when user presses edit team button on MainFrame.
//provides user with a list of options to edit team: change name/size, choose vice/captain,
// add and remove members, and return to main options
public class EditTeamFrame extends JFrame {

    private JPanel editTeamPane;
    private TeamApp app;
    private JFrame mainFrame;
    private JTextField changeNameTextField;
    private JTextField changeSizeTextField;
    private JRadioButton captainRadioButton;
    private JRadioButton viceCaptainRadioButton;
    private JComboBox memberOptions;

    private ActionListener returnListener;
    private ActionListener sizeListener;
    private ActionListener changeNameListener;
    private ActionListener chooseCaptainsListener;
    private ActionListener addRemoveListener;


    //EFFECTS: creates a new frame meant to present the edit team options to user.
    //          adds and places all the necessary components required for interaction.
    public EditTeamFrame(TeamApp app, JFrame mainFrame) {
        setFrame(app, mainFrame);
        setImage();
        setLabels();
        setRadioButtons();
        setActionListeners();
        createDropDown();
        setAllButtons();
        setTextFields();
    }

    //MODIFIES: this
    //EFFECTS: places all components to edit team onto panel
    private void setFrame(TeamApp app, JFrame mainFrame) {
        this.app = app;
        this.mainFrame = mainFrame;
        this.setResizable(false);
        this.setVisible(true);
        mainFrame.setVisible(false);
        setBackground(Color.CYAN);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 650, 700);
        editTeamPane = new JPanel();
        editTeamPane.setBackground(new Color(175, 238, 238));
        editTeamPane.setBorder(new LineBorder(new Color(102, 205, 170), 2, true));
        setContentPane(editTeamPane);
        editTeamPane.setLayout(null);
    }


    //MODIFIES: this
    //EFFECTS: creates and adds all labels onto the panel
    private void setLabels() {
        JLabel tittle = new JLabel("Swimteam Tracker");
        tittle.setFont(new Font("Lucida Grande", Font.BOLD, 30));
        tittle.setBounds(150, 6, 289, 44);
        editTeamPane.add(tittle);

        JLabel chooseOption = new JLabel("Choose one of the following options:");
        chooseOption.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        chooseOption.setBounds(6, 46, 299, 16);
        editTeamPane.add(chooseOption);

        JLabel changeNameLabel = new JLabel("Change team's name");
        changeNameLabel.setBounds(16, 84, 241, 16);
        editTeamPane.add(changeNameLabel);

        JLabel changeSizeLabel = new JLabel("Change team's size");
        changeSizeLabel.setBounds(16, 137, 241, 16);
        editTeamPane.add(changeSizeLabel);

        JLabel chooseNewCaptainsLabel = new JLabel("Choose new Captains");
        chooseNewCaptainsLabel.setBounds(16, 208, 241, 16);
        editTeamPane.add(chooseNewCaptainsLabel);
    }

    //MODIFIES: this
    //EFFECTS: creates and adds all radio buttons onto the panel
    private void setRadioButtons() {
        captainRadioButton = new JRadioButton("Captain");
        captainRadioButton.setBounds(285, 225, 140, 20);
        editTeamPane.add(captainRadioButton);

        viceCaptainRadioButton = new JRadioButton("Vice-Captain");
        viceCaptainRadioButton.setBounds(285, 260, 140, 20);
        editTeamPane.add(viceCaptainRadioButton);
    }

    //MODIFIES: this
    //EFFECTS: creates buttons with variable labels and y-coordinates and adds it onto panel
    private JButton createButton(String label, int valueY) {
        JButton newButton = new JButton(label);
        newButton.setBounds(285, valueY, 240, 30);
        editTeamPane.add(newButton);
        return newButton;
    }

    //MODIFIES: this
    //EFFECTS: creates all necessary buttons for interaction and sets their actions
    private void setAllButtons() {
        JButton changeNameButton = createButton("Change team name", 100);
        changeNameButton.addActionListener(changeNameListener);
        JButton addRemoveButton = createButton("Add/Remove member", 350);
        addRemoveButton.addActionListener(addRemoveListener);
        JButton chooseCaptainsButton = createButton("Choose Captain/Vice-Captain", 290);
        chooseCaptainsButton.addActionListener(chooseCaptainsListener);
        JButton changeSizeButton = createButton("Change team size", 150);
        changeSizeButton.addActionListener(sizeListener);
        JButton returnButton = createButton("Back to main menu", 420);
        returnButton.addActionListener(returnListener);
    }


    //MODIFIES: this
    //EFFECTS: creates new text fields with variable initial text and y-coordinates, and adds onto the pane
    private JTextField createTextField(int valueY, String initialText) {
        JTextField newTextField = new JTextField();
        newTextField.setText(initialText);
        newTextField.setBounds(15, valueY, 240, 25);
        editTeamPane.add(newTextField);
        newTextField.setColumns(10);
        return newTextField;
    }


    //MODIFIES: this
    //EFFECTS:sets all necessary text fields for user interaction
    private void setTextFields() {
        changeNameTextField = createTextField(100, app.getTeam().getName());
        changeSizeTextField = createTextField(150, Integer.toString(app.getTeam().getMax()));
    }

    //MODIFIES: this
    //EFFECTS: creates and adds drop down menu to the pane
    private void createDropDown() {
        memberOptions = new JComboBox(app.teamNames());
        memberOptions.setBounds(15, 225, 240, 30);
        editTeamPane.add(memberOptions);
    }

    //EFFECTS:sets all the button actions
    private void setActionListeners() {
        setReturnListener();
        setSizeListener();
        setChangeNameListener();
        setChooseCaptainsListener();
        setAddRemoveListener();
    }

    //MODIFIES: this and MainFrame
    //EFFECTS: returns user back to main menu(sets visibility for this to false and mainframe to true)
    private void setReturnListener() {
        returnListener = e -> {
            this.setVisible(false);
            mainFrame.setVisible(true);
        };
    }

    //MODIFIES: SwimTeam
    //EFFECTS: tries to change team size to the input in changeSizeTextField
    //          if not an integer, will show popup saying "Input must be an integer"
    //              else will change the size of the team if the size is greater than current.
    private void setSizeListener() {
        sizeListener = e -> {
            try {
                int newSize = Integer.parseInt(changeSizeTextField.getText());
                if (newSize < app.getTeam().getMax()) {
                    JOptionPane.showMessageDialog(null, "Can't reduce size",
                            "Invalid input",JOptionPane.WARNING_MESSAGE);
                } else {
                    app.getTeam().setMaX(newSize);
                    EventLog.getInstance().logEvent(new Event("team max size changed to "
                            + newSize));
                    JOptionPane.showMessageDialog(null, "Size successfully changed",
                            "size changed",JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Input must be an integer",
                        "Invalid input",JOptionPane.WARNING_MESSAGE);
            }
        };
    }


    //MODIFIES: SwimTeam
    //EFFECTS: if changeNameTextField has no text, shows popup saying "have to enter a name"
    //          else will change the name of the team to the text in the text field.
    private void setChangeNameListener() {
        changeNameListener = e -> {
            if (changeNameTextField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "have to enter a name",
                        "invalid name",JOptionPane.WARNING_MESSAGE);
            } else {
                app.getTeam().setName(changeNameTextField.getText());
                EventLog.getInstance().logEvent(new Event("team name changed to "
                        + changeNameTextField.getText()));
                JOptionPane.showMessageDialog(null, "Name successfully changed",
                        "name changed",JOptionPane.INFORMATION_MESSAGE);
            }
        };
    }

    //MODIFIES: SwimTeam
    //EFFECTS: creates an action for chooseCaptainsButton.
    //          if there are no members on the team, then shows popup saying "No members on team!"
    //          else
    //              if the captain or vice captain button is selected, will make
    //                  the member in the dropdown vice/captain accordingly
    //              else popup saying "option not selected"
    private void setChooseCaptainsListener() {
        chooseCaptainsListener = e -> {
            if (app.getTeam().teamSize() == 0) {
                JOptionPane.showMessageDialog(null, "No members on team!",
                        "nothing to choose",JOptionPane.WARNING_MESSAGE);
            } else {
                if (captainRadioButton.isSelected()) {
                    String newCaptain = (String) memberOptions.getSelectedItem();
                    app.getTeam().chooseCaptain(app.findSwimmer(newCaptain));
                    JOptionPane.showMessageDialog(null, "New captain set!",
                            "captain set",JOptionPane.INFORMATION_MESSAGE);

                } else if (viceCaptainRadioButton.isSelected()) {
                    String newViceCaptain = (String) memberOptions.getSelectedItem();
                    app.getTeam().chooseViceCaptain(app.findSwimmer(newViceCaptain));
                    JOptionPane.showMessageDialog(null, "New vice-captain set!",
                            "vice-captain set",JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Choose Captain or Vice-Captain",
                            "option not selected",JOptionPane.WARNING_MESSAGE);
                }
            }
        };
    }

    //MODIFIES: this, and AddRemoveFrame
    //EFFECTS: creates new AddRemoveFrame and sets visibility of this to false
    private void setAddRemoveListener() {
        addRemoveListener = e -> {
            new AddRemoveFrame(app, mainFrame);
            this.setVisible(false);
        };
    }


    //MODIFIES: this
    //EFFECTS: sets images and adds them onto panel
    private void setImage() {
        ImageIcon image1 = new ImageIcon("./data/swimmerStanding1.png");
        ImageIcon image2 = new ImageIcon("./data/swimmerStanding2.png");
        JLabel imageLabel1 = new JLabel();
        imageLabel1.setIcon(image1);
        JLabel imageLabel2 = new JLabel();
        imageLabel2.setIcon(image2);
        imageLabel1.setBounds(50, 240, 300, 500);
        imageLabel2.setBounds(340, 420, 400, 300);
        editTeamPane.add(imageLabel1);
        editTeamPane.add(imageLabel2);
    }
}
