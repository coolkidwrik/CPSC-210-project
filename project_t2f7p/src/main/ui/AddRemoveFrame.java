package ui;

import model.EventLog;
import model.Event;
import model.Swimmer;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

//creates a new frame where the user can add and remove members. When adding, can give the swimmer a
//          main stroke, and make vice/captain.
public class AddRemoveFrame extends JFrame {

    private JPanel addRemovePane;
    private JTextField addMemberTextField;
    private JRadioButton captainRadioButton;
    private JRadioButton viceCaptainRadioButton;
    private JComboBox removeMemberComboBox;
    private JComboBox strokeComboBox;
    private TeamApp app;
    private JFrame mainFrame;
    private final String[] styles = {"freestyle", "breast stroke", "back stroke", "butterfly"};

    private ActionListener returnListener;
    private ActionListener addMemberListener;
    private ActionListener removeMemberListener;

    //MODIFIES: this
    //EFFECTS: creates new frame with all necessary elements required for user interaction
    public AddRemoveFrame(TeamApp app, JFrame mainFrame) {
        setFrame(app, mainFrame);
        setActionListeners();
        setLabels();
        setAllButtons();
        setAllRadioButtons();
        setComboBoxes();
        setTextField();
    }

    //MODIFIES: this
    //EFFECTS: creates a frame and panel
    private void setFrame(TeamApp app, JFrame mainFrame) {
        this.app = app;
        this.mainFrame = mainFrame;
        this.setResizable(false);
        this.setVisible(true);
        setBackground(Color.CYAN);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 370);
        addRemovePane = new JPanel();
        addRemovePane.setForeground(Color.WHITE);
        addRemovePane.setBackground(new Color(175, 238, 238));
        addRemovePane.setBorder(new LineBorder(new Color(102, 205, 170), 2, true));
        setContentPane(addRemovePane);
        addRemovePane.setLayout(null);
    }

    //MODIFIES: this
    //EFFECTS: sets and places all necessary labels onto the addRemovePanel
    private void setLabels() {
        JLabel addMemberLabel = new JLabel("Add new team member:");
        addMemberLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        addMemberLabel.setBounds(20, 65, 192, 16);
        addRemovePane.add(addMemberLabel);

        JLabel removeMemberLabel = new JLabel("Remove member:");
        removeMemberLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        removeMemberLabel.setBounds(473, 65, 220, 16);
        addRemovePane.add(removeMemberLabel);

        JLabel addRemoveLabel = new JLabel("Add/Remove menu");
        addRemoveLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
        addRemoveLabel.setBounds(240, 20, 249, 16);
        addRemovePane.add(addRemoveLabel);

        JLabel swimmerNameLabel = new JLabel("Enter swimmer name: ");
        swimmerNameLabel.setBounds(20, 98, 141, 16);
        addRemovePane.add(swimmerNameLabel);

        JLabel lblChooseMainStroke = new JLabel("Choose main stroke:");
        lblChooseMainStroke.setBounds(20, 135, 141, 16);
        addRemovePane.add(lblChooseMainStroke);
    }


    //MODIFIES: this
    //EFFECTS: creates new buttons with variable labels and x-coordinates, and places them onto the frame
    private JButton createButton(String label, int valueX) {
        JButton newButton = new JButton(label);
        newButton.setBounds(valueX, 245, 120, 30);
        addRemovePane.add(newButton);
        return newButton;
    }

    //MODIFIES: this
    //EFFECTS: creates and sets all buttons for user interaction
    private void setAllButtons() {
        JButton addButton = createButton("Add", 45);
        addButton.addActionListener(addMemberListener);
        JButton removeButton = createButton("Remove", 515);
        removeButton.addActionListener(removeMemberListener);

        JButton returnButton = new JButton("Return to main menu");
        returnButton.setBounds(254, 304, 176, 29);
        returnButton.addActionListener(returnListener);
        addRemovePane.add(returnButton);
    }

    //MODIFIES: this
    //EFFECTS: creates new radio button with variable label and y-coordinate
    private JRadioButton createRadioButton(String label, int valueY) {
        JRadioButton newRadioButton = new JRadioButton(label);
        newRadioButton.setBounds(20, valueY, 160, 25);
        addRemovePane.add(newRadioButton);
        return newRadioButton;
    }

    //MODIFIES: this
    //EFFECTS:creates and sets radio buttons and places them onto panel.
    //          disables captain radiobutton if team has a captain, likewise for vice captain
    private void setAllRadioButtons() {
        captainRadioButton = createRadioButton("Make captain", 165);
        if (app.getTeam().hasCaptain()) {
            captainRadioButton.setEnabled(false);
            JLabel captainInfoMessage = new JLabel("- Team already has a captain");
            captainInfoMessage.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
            captainInfoMessage.setBounds(155, 170, 220, 15);
            addRemovePane.add(captainInfoMessage);
        }
        viceCaptainRadioButton = createRadioButton("Make vice-captain", 205);
        if (app.getTeam().hasViceCaptain()) {
            viceCaptainRadioButton.setEnabled(false);
            JLabel viceCaptainInfoMessage = new JLabel("- Team already has a vice-captain");
            viceCaptainInfoMessage.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
            viceCaptainInfoMessage.setBounds(175, 211, 220, 15);
            addRemovePane.add(viceCaptainInfoMessage);
        }
    }

    //MODIFIES: this
    //EFFECTS: sets comboBoxes onto the panel with:
    //          - team members names (Strings)
    //          - swim styles (Strings)
    private void setComboBoxes() {
        removeMemberComboBox = new JComboBox(app.teamNames());
        removeMemberComboBox.setBounds(473, 131, 199, 27);
        addRemovePane.add(removeMemberComboBox);

        strokeComboBox = new JComboBox(styles);
        strokeComboBox.setBounds(164, 131, 199, 27);
        addRemovePane.add(strokeComboBox);
    }

    //MODIFIES:  this
    //EFFECTS:creates new text field where new member names are added
    private void setTextField() {
        addMemberTextField = new JTextField();
        addMemberTextField.setBounds(164, 93, 199, 26);
        addRemovePane.add(addMemberTextField);
        addMemberTextField.setColumns(10);
    }


    //EFFECTS: sets all actions for each button
    private void setActionListeners() {
        setReturnListener();
        setAddMemberListener();
        setRemoveMemberListener();
    }

    //MODIFIES: this, MainFrame
    //EFFECTS: creates action for return button
    //          returns to main manu
    private void setReturnListener() {
        returnListener = e -> {
            this.setVisible(false);
            mainFrame.setVisible(true);
        };
    }

    //MODIFIES: this, SwimTeam
    //EFFECTS: creates action for add button
    //          if team size == max size, shows popup saying "Can't add more members!"
    //          else
    //              if name entered into text field is empty, then shows popup saying "have to enter a name"
    //              else creates and adds new member to the swim team. Then updates frame by calling a new one
    private void setAddMemberListener() {
        addMemberListener = e -> {
            if (app.getTeam().isFull()) {
                JOptionPane.showMessageDialog(null, "Can't add more members!",
                        "Team full!", JOptionPane.WARNING_MESSAGE);
            } else {
                if (addMemberTextField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "have to enter a name",
                            "Invalid name", JOptionPane.WARNING_MESSAGE);
                } else {
                    Swimmer newSwimmer = createSwimmer();
                    app.getTeam().addMember(newSwimmer);
                    EventLog.getInstance().logEvent(new Event(newSwimmer.getName() + " added to the team"));
                    this.setVisible(false);
                    new AddRemoveFrame(app, mainFrame);
                    JOptionPane.showMessageDialog(null, "Success",
                            "New member added!", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        };
    }


    //MODIFIES: Swimmer
    //EFFECTS: creates a new swimmer with name in the text field.
    //          chooses swimmer main stroke from the style dropdown
    //          if captain radio button is selected, makes captain
    //          else if vice captain radio button is selected, makes vice captain
    private Swimmer createSwimmer() {
        Swimmer newSwimmer = new Swimmer(addMemberTextField.getText());
        if (captainRadioButton.isSelected()) {
            newSwimmer.setCaptain(true);
        }
        if (viceCaptainRadioButton.isSelected()) {
            newSwimmer.setViceCaptain(true);
        }
        newSwimmer.setMainStroke((String) strokeComboBox.getSelectedItem());
        return newSwimmer;
    }

    //MODIFIES: this, SwimTeam
    //EFFECTS:  creates action for remove button
    //          if team is empty, shows popup saying "No one to remove"
    //          else removes the swimmer from the team
    private void setRemoveMemberListener() {
        removeMemberListener = e -> {
            if (app.getTeam().teamSize() == 0) {
                JOptionPane.showMessageDialog(null, "No one to remove",
                        "Empty team!", JOptionPane.WARNING_MESSAGE);
            } else {
                Swimmer toRemove = app.findSwimmer((String) removeMemberComboBox.getSelectedItem());
                app.getTeam().removeMember(toRemove);
                EventLog.getInstance().logEvent(new Event(removeMemberComboBox.getSelectedItem()
                        + " removed from the team"));
                this.setVisible(false);
                new AddRemoveFrame(app, mainFrame);
                JOptionPane.showMessageDialog(null, "Success",
                        "Member removed", JOptionPane.INFORMATION_MESSAGE);
            }
        };
    }


}
