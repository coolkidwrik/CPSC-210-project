package ui;

import model.Event;
import model.EventLog;
import model.SwimTeam;
import model.Swimmer;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// runs the main console that the user interacts with (for console based interface)
// calls appropriate methods from other classes (for GUI)
public class TeamApp {

    private static final String file = "./data/team.json";
    private SwimTeam team;
    private boolean hasTeam = false;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS: runs console, and sets up json reader and writer to save and load data
    public TeamApp() {
        jsonWriter = new JsonWriter(file);
        jsonReader = new JsonReader(file);
        //console(); // only for console based application
    }


    //EFFECTS: runs console while local variable, active, is true
    //          creates new scanner to be used throughout the class
    //          active == false when user input is d
    private void console() {
        boolean active = true;
        String option = "";
        input = new Scanner(System.in);

        while (active) {
            if (!hasTeam) {
                loadOptions();
                String in = input.next();
                processLoadOptions(in);
            }
            printMainScreen();
            option = input.next();
            option = option.toLowerCase();
            if (option.equals("e")) {
                active = false;
            } else {
                processMainInput(option);
            }
        }
        System.out.println("\nGoodbye");
    }

    //EFFECTS: prints initial user options to create new team, or load old team
    private void loadOptions() {
        System.out.println("a) Create new Team");
        System.out.println("b) Load old team");
        System.out.print("Choose an option: ");
    }

    //EFFECTS: takes in the user input and prints out one of the following depending on input
    //          a - creates a new team that the user will update
    //          b - call to a method that loads an existing team
    //       else - print out an error message and call method again
    private void processLoadOptions(String in) {
        if (in.equals("a")) {
            createNewTeam();
        } else if (in.equals("b")) {
            loadTeam();
        } else {
            processLoadOptions(errorCall());
        }
    }

    //MODIFIES: this and SwimTeam
    //EFFECTS: creates a new swim team with a name (for console)
    private void createNewTeam() {
        System.out.println("Create new team");
        System.out.print("Give your team a name:\t");
        String name = input.nextLine();
        name = input.nextLine(); // so it doesn't skip input line
        team = new SwimTeam(name);
        hasTeam = true;
    }

    //MODIFIES: this and SwimTeam
    //EFFECTS: creates a new swim team with a name (for GUI)
    public void createNewTeamGUI(String name) {
        team = new SwimTeam(name);
    }

    //EFFECTS: saves team to the file
    public void saveTeam() {
        try {
            jsonWriter.open();
            jsonWriter.write(team);
            jsonWriter.close();
            System.out.println("Saved " + team.getName() + " to " + file);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + file);
        }
    }

    //MODIFIES: this
    //EFFECTS: loads team from the file
    public void loadTeam() {
        try {
            team = jsonReader.read();
            System.out.println("Loaded " + team.getName() + " from " + file);
            hasTeam = true;
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + file);
        }
    }



    //EFFECTS: prints main user options to the console
    private void printMainScreen() {
        System.out.println("\nChoose one of the following options");
        System.out.println("\ta) View team members");
        System.out.println("\tb) Edit Team");
        System.out.println("\tc) Edit statistics");
        System.out.println("\td) Save team");
        System.out.println("\te) Quit app");
    }


    //EFFECTS: takes in the user input and prints out one of the following depending on input
    //          a - print out the team on console
    //          b - print out the edit team console
    //          c - print out the edit statistics console
    //          d - quit the program
    //       else - print out an error message and call method again
    private void processMainInput(String in) {
        if (in.equals("a")) {
            team.viewTeam();
            System.out.print("enter anything to return: ");
            input.next();
        } else if (in.equals("b")) {
            editTeamConsole();
        } else if (in.equals("c")) {
            statsConsole();
        } else if (in.equals("d")) {
            saveTeam();
        } else if (in.equals("e")) {
            return;
        } else {
            processMainInput(errorCall());
        }
    }


    //EFFECTS: gives user options to edit team and creates a user input to operate on
    //          returns to main console if inputs g
    private void editTeamConsole() {
        String option;
        printEditTeamScreen();
        option = input.next();
        option = option.toLowerCase();
        if (option.equals("g")) {
            return;
        } else {
            processEditTeam(option);
        }
    }

    //EFFECTS: prints team edit user options to the console
    private void printEditTeamScreen() {
        System.out.println("\n\ta) Change team name");
        System.out.println("\tb) Add member to team");
        System.out.println("\tc) Remove member from team");
        System.out.println("\td) Choose new team captain");
        System.out.println("\te) Choose new team vice-captain");
        System.out.println("\tf) Set new Team size");
        System.out.println("\tg) Back to main menu");
    }


    //EFFECTS: takes in the user input and prints out one of the following depending on input
    //          a - call to change name of the team
    //          b - call to add new member
    //          c - call to remove a member
    //          d - call to change team captains
    //          e - call to change team vice-captains
    //          f - call to change the maximum size of the team
    //          g - return to main console
    //       else - print out an error message and call method again
    private void processEditTeam(String in) {
        if (in.equals("a")) {
            changeName();
        } else if (in.equals("b")) {
            newMember();
        } else if (in.equals("c")) {
            removeAMember();
        } else if (in.equals("d")) {
            newCaptain();
        } else if (in.equals("e")) {
            newViceCaptain();
        } else if (in.equals("f")) {
            newMax();
        } else if (in.equals("g")) {
            return;
        } else {
            processEditTeam(errorCall());
        }
    }


    //MODIFIES: this and SwimTeam
    //EFFECTS: changes the name of the swim team
    private void changeName() {
        System.out.print("enter new team name:\n");
        String nameInput = input.nextLine();
        nameInput = input.nextLine(); // second input needed, or else skips the input stage on console
        team.setName(nameInput);
    }

    //REQUIRES: user input should be a name not in the team
    //MODIFIES: this, SwimTeam and Swimmer
    //EFFECTS:second user input should be y or n only
    private void newMember() {
        System.out.println("enter name of new member:\t");
        String nameInput = input.next();
        Swimmer member = new Swimmer(nameInput);
        wantToAddMainStroke(member);
        if (!team.hasCaptain()) {
            wantToMakeCaptain(member);
        }
        if (!team.hasViceCaptain()) {
            wantToMakeViceCaptain(member);
        }
        if (!team.addMember(member)) {
            System.out.println("member not added");
            if (team.isFull()) {
                System.out.println("Team is full");
                return;
            } else {
                System.out.println("try adding member again");
                newMember();
            }
        }
        wantToAddAnotherMember();
    }

    //MODIFIES: Swimmer
    //EFFECTS: adds main stroke if y, else if n, do nothing
    private void wantToAddMainStroke(Swimmer swimmer) {
        System.out.println("does the swimmer have a main stroke? (y/n)");
        String in = input.next();
        if (in.equals("y")) {
            System.out.println("Enter swimmers main stroke");
            printMainStrokes();
            in = input.next();
            swimmer.setMainStroke(chooseStroke(in));
        } else if (in.equals("n")) {
            return;
        } else {
            System.out.println("Error - Invalid input");
            wantToAddMainStroke(swimmer);
        }
    }

    //EFFECTS: prints out all swim styles as options
    private void printMainStrokes() {
        System.out.println("Choose a swim style: ");
        System.out.println("a) freestyle");
        System.out.println("b) breast stroke");
        System.out.println("c) back stroke");
        System.out.println("d) butterfly");
    }

    //EFFECTS: returns one of the swim style name strings based on previous user input
    private String chooseStroke(String in) {
        if (in.equals("a")) {
            return "freestyle";
        } else if (in.equals("b")) {
            return "breast stroke";
        } else if (in.equals("c")) {
            return "back stroke";
        } else if (in.equals("d")) {
            return "butterfly";
        } else {
            return chooseStroke(errorCall());
        }
    }


    //MODIFIES: Swimmer
    //EFFECTS: makes captain if user input is y, else if n, do nothing
    private void wantToMakeCaptain(Swimmer swimmer) {
        System.out.println("Want to make Captain? (y/n)");
        String in = input.next();
        if (in.equals("y")) {
            swimmer.setCaptain(true);
        } else if (in.equals("n")) {
            return;
        } else {
            System.out.println("Error - Invalid input\n");
            wantToMakeCaptain(swimmer);
        }
    }

    //MODIFIES: Swimmer
    //EFFECTS: makes vice-captain if user input is y
    private void wantToMakeViceCaptain(Swimmer swimmer) {
        System.out.println("Want to make Vice-Captain? (y/n)");
        String in = input.next();
        if (in.equals("y")) {
            swimmer.setViceCaptain(true);
        } else if (in.equals("n")) {
            return;
        } else {
            System.out.println("Error - Invalid input\n");
            wantToMakeViceCaptain(swimmer);
        }
    }

    //EFFECTS: calls newMember again if user input is y
    //          if n, then return to edit team console
    //          print error message and call itself again
    private void wantToAddAnotherMember() {
        System.out.println("Want to add another member? (y/n)");
        String in = input.next();
        if (in.equals("y")) {
            newMember();
        } else if (in.equals("n")) {
            return;
        } else {
            System.out.println("Error - Invalid input\n");
            wantToAddAnotherMember();
        }
    }


    //MODIFIES: this and SwimTeam
    //EFFECTS: removes a swimmer from the swim team
    private void removeAMember() {
        System.out.print("Who do you want to remove ?\n");
        String memberName = input.next();
        if (!(findSwimmer(memberName) == null)) {
            team.removeMember(findSwimmer(memberName));
        } else {
            System.out.println("Member not on team");
            return;
        }
    }


    //MODIFIES: SwimTeam
    //EFFECTS: create user input to enter a swimmer name
    //          makes chosen person the team captain
    private void newCaptain() {
        System.out.println("enter name to make captain:\t");
        String nameInput = input.next();
        if (!(findSwimmer(nameInput) == null)) {
            team.chooseCaptain(findSwimmer(nameInput));
        } else {
            System.out.println("Member not on team");
            return;
        }
    }

    //MODIFIES: SwimTeam
    //EFFECTS: create user input to enter a swimmer name
    //          makes chosen person the team vice-captain
    private void newViceCaptain() {
        System.out.println("enter name to make vice-captain:\t");
        String nameInput = input.next();
        if (!(findSwimmer(nameInput) == null)) {
            team.chooseViceCaptain(findSwimmer(nameInput));
        } else {
            System.out.println("Member not on team");
            return;
        }
    }

    //REQUIRES: user input should be a number
    //MODIFIES: this and SwimTeam
    //EFFECTS: makes chosen person the team vice-captain
    private void newMax() {
        System.out.println("enter a new max size:\t");
        String size = input.next();
        int num = Integer.parseInt(size);
        team.setMaX(num);
    }


    //EFFECTS: gives user options to edit stats and creates a user input to operate on
    //          returns to main console if inputs c
    private void statsConsole() {
        String option;
        printStatsScreen();
        option = input.next();
        if (option.equals("c")) {
            return;
        } else {
            processDisplayStats(option);
        }
    }

    //EFFECTS: prints team stat user options to the console
    private void printStatsScreen() {
        System.out.println("\n\ta) Display team stats");
        System.out.println("\tb) Check member stats");
        System.out.println("\tc) Back to main menu");
    }

    //EFFECTS: takes in the user input and prints out one of the following depending on input
    //          a - displays team statistics
    //          b - displays member statistics
    //          c - return to main console
    //       else - print out an error message and call method again
    private void processDisplayStats(String in) {
        if (in.equals("a")) {
            displayTeamStats();
        } else if (in.equals("b")) {
            editMemberStats();
        } else if (in.equals("c")) {
            return;
        } else {
            processDisplayStats(errorCall());
        }
    }


    //EFFECTS: displays on the console the average statistic of the swim time fields of the swimmer class
    //          also number of people in each main stroke
    //          and the fastest swimmer in each stroke
    private void displayTeamStats() {
        if (team.getTeam().isEmpty()) {
            System.out.println("Nothing to display");
        } else {
            System.out.println(team.getName() + " Team Statistics\n");
            collectAndDisplayStats();
            System.out.println("\n enter anything to return :");
            input.next();
        }
    }


    //EFFECTS: collects and calculates stats for all strokes for the team
    //          and displays them on the console
    private void collectAndDisplayStats() {
        collectFreeStats();
        collectBreastStrokeStats();
        collectBackStrokeStats();
        collectButterflyStats();

    }

    //EFFECTS: collects, and calculates the following statistics from the team:
    //          - number of members with freestyle main
    //          - average of 50m freestyle timings
    //          - average of 100m freestyle timings
    private void collectFreeStats() {
        int mainFree = 0;   //number of swimmers with main freestyle
        double sum50Free = 0;   //sum of all 50m free times
        double count50 = 0;    //number of swimmers with 50m timing
        double sum100Free = 0;   //sum of all 100m free times
        double count100 = 0;    //number of swimmers with 100m timing
        for (Swimmer s : team.getTeam()) {
            if (s.getMainStroke().equals("freestyle")) {
                mainFree++;
            }
            if (s.getFreeStyle50m() != 0) {
                sum50Free += s.getFreeStyle50m();
                count50++;
            }
            if (s.getFreeStyle100m() != 0) {
                sum100Free += s.getFreeStyle100m();
                count100++;
            }
        }
        System.out.println("Free Style Statistics");
        System.out.println("Members with main stroke freestyle : " + mainFree);
        System.out.println("Team average 50m freestyle timing : " + sum50Free / count50);
        System.out.println("Team average 100m freestyle timing : " + sum100Free / count100 + "\n");
    }

    //EFFECTS: collects, and calculates the following statistics from the team:
    //          - number of members with breast stroke main
    //          - average of 50m breast stroke timings
    //          - average of 100m breast stroke timings
    private void collectBreastStrokeStats() {
        int mainBreast = 0;   //number of swimmers with main breast stroke
        double sum50Breast = 0;   //sum of all 50m breast stroke times
        double count50 = 0;    //number of swimmers with 50m timing
        double sum100Breast = 0;   //sum of all 100m breast stroke times
        double count100 = 0;    //number of swimmers with 100m timing
        for (Swimmer s : team.getTeam()) {
            if (s.getMainStroke().equals("breast stroke")) {
                mainBreast++;
            }
            if (s.getBreastStroke50m() != 0) {
                sum50Breast += s.getBreastStroke50m();
                count50++;
            }
            if (s.getBreastStroke100m() != 0) {
                sum100Breast += s.getBreastStroke100m();
                count100++;
            }
        }
        System.out.println("Breast Stroke Statistics");
        System.out.println("Members with main stroke breast stroke : " + mainBreast);
        System.out.println("Team average 50m breast stroke timing : " + sum50Breast / count50);
        System.out.println("Team average 100m breast stroke timing : " + sum100Breast / count100 + "\n");
    }

    //EFFECTS: collects, and calculates the following statistics from the team:
    //          - number of members with back stroke main
    //          - average of 50m back stroke timings
    //          - average of 100m back stroke timings
    private void collectBackStrokeStats() {
        int mainBack = 0;   //number of swimmers with main back stroke
        double sum50Back = 0;   //sum of all 50m back stroke times
        double count50 = 0;    //number of swimmers with 50m timing
        double sum100Back = 0;   //sum of all 100m back stroke times
        double count100 = 0;    //number of swimmers with 100m timing
        for (Swimmer s : team.getTeam()) {
            if (s.getMainStroke().equals("back stroke")) {
                mainBack++;
            }
            if (s.getBackStroke50m() != 0) {
                sum50Back += s.getBackStroke50m();
                count50++;
            }
            if (s.getBackStroke100m() != 0) {
                sum100Back += s.getBackStroke100m();
                count100++;
            }
        }
        System.out.println("Back Stroke Statistics");
        System.out.println("Members with main stroke back stroke : " + mainBack);
        System.out.println("Team average 50m back stroke timing : " + sum50Back / count50);
        System.out.println("Team average 100m back stroke timing : " + sum100Back / count100 + "\n");
    }

    //EFFECTS: collects, and calculates the following statistics from the team:
    //          - number of members with butterfly main
    //          - average of 50m butterfly timings
    //          - average of 100m butterfly timings
    private void collectButterflyStats() {
        int mainFly = 0;   //number of swimmers with main butterfly
        double sum50Fly = 0;   //sum of all 50m butterfly times
        double count50 = 0;    //number of swimmers with 50m timing
        double sum100Fly = 0;   //sum of all 100m butterfly times
        double count100 = 0;    //number of swimmers with 100m timing
        for (Swimmer s : team.getTeam()) {
            if (s.getMainStroke().equals("butterfly")) {
                mainFly++;
            }
            if (s.getButterfly50m() != 0) {
                sum50Fly += s.getButterfly50m();
                count50++;
            }
            if (s.getButterfly100m() != 0) {
                sum100Fly += s.getButterfly100m();
                count100++;
            }
        }
        System.out.println("Butterfly Statistics");
        System.out.println("Members with main stroke butterfly : " + mainFly);
        System.out.println("Team average 50m butterfly timing : " + sum50Fly / count50);
        System.out.println("Team average 100m butterfly timing : " + sum100Fly / count100 + "\n");
    }


    //EFFECTS: if team is empty, the prints "No one on the team" and returns to the main console
    //          else creates a user input to for the name of member to be edited
    //          then calls method to display and edit member statistics
    private void editMemberStats() {
        if (team.getTeam().isEmpty()) {
            System.out.println("No one on the team");
            return;
        }
        System.out.println("Enter name of a member to check their statistics:\n");
        String name = input.next();
        displayMemberStats(name);
    }

    //EFFECTS: displays all the member's name and stats on console if name is found
    //          then gives the option to edit stats
    private void displayMemberStats(String name) {
        Swimmer member = findSwimmer(name);
        if (member == null) {
            System.out.println(name + " not found, try again");
            editMemberStats();
        } else {
            printMemberStats(member);
            editStats(member);
        }
    }

    //EFFECTS: prints out all the users statistics and details
    private void printMemberStats(Swimmer swimmer) {
        System.out.println("Stats for : " + swimmer.getName() + "\n");
        if (swimmer.isCaptain()) {
            System.out.println("Is the team captain");
        }
        if (swimmer.isViceCaptain()) {
            System.out.println("Is the team vice-captain");
        }
        if (swimmer.sprinterOrEnduranceSwimmer().equals("Sprinter")) {
            System.out.println(swimmer.getName() + " is a " + swimmer.sprinterOrEnduranceSwimmer() + "\n");
        } else {
            System.out.println(swimmer.getName() + " is an " + swimmer.sprinterOrEnduranceSwimmer() + "\n");
        }
        printMemberDetails(swimmer);
    }

    //EFFECTS: prints all Swimmer details(main stroke and stroke timings)
    private void printMemberDetails(Swimmer swimmer) {
        System.out.println("\tSwimmer's main stroke : " + swimmer.getMainStroke());
        System.out.println("\t50m freestyle timing : " + swimmer.getFreeStyle50m());
        System.out.println("\t100m freestyle timing : " + swimmer.getFreeStyle100m());
        System.out.println("\t50m breast stroke timing : " + swimmer.getBreastStroke50m());
        System.out.println("\t100m breast stroke timing : " + swimmer.getBreastStroke100m());
        System.out.println("\t50m back stroke timing : " + swimmer.getBackStroke50m());
        System.out.println("\t100m back stroke timing : " + swimmer.getBackStroke100m());
        System.out.println("\t50m butterfly timing : " + swimmer.getButterfly50m());
        System.out.println("\t100m butterfly timing : " + swimmer.getButterfly100m());
    }


    //EFFECTS: creates a user input, based on input, will do one of the following
    //          y - prints out options to edit statistics, creates new user input, and calls to edit member stats
    //          n - returns to main console
    //       else - prints an error message and calls method again
    private void editStats(Swimmer swimmer) {
        System.out.println("Do you want to edit this members stats? (y/n)");
        String in = input.next();
        if (in.equals("y")) {
            editStatsOptions();
            in = input.next();
            processEditStats(in, swimmer);
        } else if (in.equals("n")) {
            return;
        } else {
            System.out.println("Error - Invalid input\n");
            editStats(swimmer);
        }
    }

    //EFFECTS: prints options to edit a swimmer
    private void editStatsOptions() {
        System.out.println("a) Edit swimmer name");
        System.out.println("b) Edit swimmer main stroke");
        System.out.println("c) Edit swimmer swim stats");
    }


    //EFFECTS: takes in the user input and does one of the following depending on input
    //          a - calls to change swimmers name
    //          b - calls to change swimmers main stroke
    //          c - prints swimmer stats edit options, creates new user input to choose, and calls to process input
    //       else - print out an error message and call method again
    private void processEditStats(String in, Swimmer swimmer) {
        if (in.equals("a")) {
            newSwimmerName(swimmer);
        } else if (in.equals("b")) {
            newSwimmerMainStroke(swimmer);
        } else if (in.equals("c")) {
            swimStatsOptions();
            in = input.next();
            processSwimStatsOptions(in, swimmer);
        } else {
            processEditStats(errorCall(), swimmer);
        }
    }

    //MODIFIES: Swimmer
    //EFFECTS: creates new user input to enter a new name, and changes swimmer's name to that
    private void newSwimmerName(Swimmer swimmer) {
        System.out.print("Enter swimmer's name: ");
        String in = input.next();
        swimmer.setName(in);
        System.out.print("Name changed\n");
    }

    //MODIFIES: Swimmer
    //EFFECTS: creates new user input to enter a new main stroke, and changes swimmer's main stroke to that
    private void newSwimmerMainStroke(Swimmer swimmer) {
        printMainStrokes();
        String in = input.next();
        swimmer.setMainStroke(chooseStroke(in));
        System.out.print("Main stroke changed\n");
    }


    //EFFECTS: prints options to edit a swimmer's swim stats
    private void swimStatsOptions() {
        System.out.println("a) Edit freestyle timing");
        System.out.println("b) Edit breast stroke timing");
        System.out.println("c) Edit back stroke timing");
        System.out.println("d) Edit butterfly timing");
    }

    //EFFECTS: takes in the user input and does one of the following depending on input
    //          a - prints options between 50 and 100, creates new user input, and calls to change freestyle stats
    //          b - prints options between 50 and 100, creates new user input, and calls to change breast stroke stats
    //          c - prints options between 50 and 100, creates new user input, and calls to change back stroke stats
    //          d - prints options between 50 and 100, creates new user input, and calls to change butterfly stats
    //       else - print out an error message and call method again
    private void processSwimStatsOptions(String in, Swimmer swimmer) {
        if (in.equals("a")) {
            swimOptions();
            in = input.next();
            editFreeStats(in, swimmer);
        } else if (in.equals("b")) {
            swimOptions();
            in = input.next();
            editBreastStats(in, swimmer);
        } else if (in.equals("c")) {
            swimOptions();
            in = input.next();
            editBackStats(in, swimmer);
        } else if (in.equals("d")) {
            swimOptions();
            in = input.next();
            editFlyStats(in, swimmer);
        } else {
            processSwimStatsOptions(errorCall(), swimmer);
        }
    }

    //EFFECTS: prints options to for 50m or 100m
    private void swimOptions() {
        System.out.println("a) 50m");
        System.out.println("b) 100m");
    }


    //REQUIRES: user input for timing should be a number > 0
    //MODIFIES: Swimmer
    //EFFECTS: changes swim timing for freestyle 50/100 m to user input
    private void editFreeStats(String in, Swimmer swimmer) {
        if (in.equals("a")) {
            System.out.println("enter new freestyle 50m timing: ");
            in = input.next();
            swimmer.setFreeStyle50m(Double.parseDouble(in));
        } else if (in.equals("b")) {
            System.out.println("enter new freestyle 100m timing: ");
            in = input.next();
            swimmer.setFreeStyle100m(Double.parseDouble(in));
        } else {
            editFreeStats(errorCall(), swimmer);
        }
    }

    //REQUIRES: user input for timing should be a number > 0
    //MODIFIES: Swimmer
    //EFFECTS: changes swim timing for breast stroke 50/100 m to user input
    private void editBreastStats(String in, Swimmer swimmer) {
        if (in.equals("a")) {
            System.out.println("enter new breast stroke 50m timing: ");
            in = input.next();
            swimmer.setBreastStroke50m(Double.parseDouble(in));
        } else if (in.equals("b")) {
            System.out.println("enter new breast stroke 100m timing: ");
            in = input.next();
            swimmer.setBreastStroke100m(Double.parseDouble(in));
        } else {
            editBreastStats(errorCall(), swimmer);
        }
    }

    //REQUIRES: user input for timing should be a number > 0
    //MODIFIES: Swimmer
    //EFFECTS: changes swim timing for back stroke 50/100 m to user input
    private void editBackStats(String in, Swimmer swimmer) {
        if (in.equals("a")) {
            System.out.println("enter new back stroke 50m timing: ");
            in = input.next();
            swimmer.setBackStroke50m(Double.parseDouble(in));
        } else if (in.equals("b")) {
            System.out.println("enter new back stroke 100m timing: ");
            in = input.next();
            swimmer.setBackStroke100m(Double.parseDouble(in));
        } else {
            editBackStats(errorCall(), swimmer);
        }
    }

    //REQUIRES: user input for timing should be a number > 0
    //MODIFIES: Swimmer
    //EFFECTS: changes swim timing for butterfly 50/100 m to user input
    private void editFlyStats(String in, Swimmer swimmer) {
        if (in.equals("a")) {
            System.out.println("enter new butterfly 50m timing: ");
            in = input.next();
            swimmer.setButterfly50m(Double.parseDouble(in));
        } else if (in.equals("b")) {
            System.out.println("enter new butterfly 100m timing: ");
            in = input.next();
            swimmer.setButterfly100m(Double.parseDouble(in));
        } else {
            editFlyStats(errorCall(), swimmer);
        }
    }







    //EFFECTS: prints out an error and creates next user input
    private String errorCall() {
        System.out.println("\nError - Invalid input");
        System.out.print("please re-enter option:\t");
        String in = input.next();
        in = in.toLowerCase();
        return in;
    }

    //EFFECTS: finds swimmer with inputted name and returns that swimmer
    //          else returns null
    public Swimmer findSwimmer(String toFind) {
        for (Swimmer s : team.getTeam()) {
            if (s.getName().equals(toFind)) {
                return s;
            }
        }
        return null;
    }

    //EFFECTS: creates an Array of Strings(names of members on the team)
    public String[] teamNames() {
        String[] members = new String[team.teamSize()];
        int counter = 0;
        for (Swimmer s: team.getTeam()) {
            members[counter] = s.getName();
            counter++;
        }
        return members;
    }

    //getter for team
    public SwimTeam getTeam() {
        return team;
    }

    //EFFECTS: prints out event log to the console
    public void printLog(EventLog el) {
        for (Event e : el) {
            System.out.println(e.toString() + "\n");
        }
    }
}
