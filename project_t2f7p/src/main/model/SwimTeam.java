package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;

//represents all the information to with team itself
public class SwimTeam implements Writable {

    private int maxSize = 10;
    private String name;
    private boolean hasCaptain;
    private boolean hasViceCaptain;
    private LinkedList<Swimmer> team;


    //getter for team list
    public LinkedList<Swimmer> getTeam() {
        return team;
    }


    //EFFECTS: creates a new empty swim team
    public SwimTeam(String name) {
        team = new LinkedList<Swimmer>();
        this.name = name;
    }

    //getter for max size
    public int getMax() {
        return maxSize;
    }

    //setter for max size
    public void setMaX(int maxSize) {
        if (maxSize >= this.maxSize) {
            this.maxSize = maxSize;
            System.out.println("New team size set");
        } else {
            System.out.println("Can't reduce size");
        }
    }

    //getter for team name
    public String getName() {
        return name;
    }

    //setter for team name
    public void setName(String name) {
        this.name = name;
    }

    //getter for has Captain
    public boolean hasCaptain() {
        return hasCaptain;
    }

    //setter for max size
    public void setHasCaptain(boolean hasCaptain) {
        this.hasCaptain = hasCaptain;
    }

    //getter for has Vice-Captain
    public boolean hasViceCaptain() {
        return hasViceCaptain;
    }

    //setter for has Captain
    public void setHasViceCaptain(boolean hasViceCaptain) {
        this.hasViceCaptain = hasViceCaptain;
    }


    //MODIFIES: this
    //EFFECTS: if list does not have captain, adds captain to the front of the list and returns true
    //          and sets team has captain to false
    //          else, prints "team already has a captain" and returns false
    private boolean addCaptain(Swimmer swimmer) {
        if (!hasCaptain) {
            team.add(0, swimmer);
            setHasCaptain(true);
            System.out.println("New Captain, " + swimmer.getName() + ", successfully added");
            return true;
        } else {
            System.out.println("team already has a captain");
            return false;
        }
    }


    //MODIFIES: this
    //EFFECTS: if team does not have a vice captain then:
    //              if team has captain, input new vice captain at index 1
    //              else, input new vice captain at index 0
    //          return true and set team has vice captain to true
    //          else return false and prints "team already has a vice-captain"
    private boolean addViceCaptain(Swimmer swimmer) {
        if (!hasViceCaptain) {
            if (hasCaptain) {
                team.add(1, swimmer);
            } else {
                team.add(0, swimmer);
            }
            setHasViceCaptain(true);
            System.out.println("New Vice-Captain, " + swimmer.getName() + ", successfully added");
            return true;
        } else {
            System.out.println("team already has a vice-captain");
            return false;
        }
    }

    //MODIFIES: this
    //EFFECTS: adds a new swimmer to the list, and returns true
    //          (returns false in the method being tested, if team is full)
    private boolean addSwimmer(Swimmer swimmer) {
        team.addLast(swimmer);
        System.out.println("New member, " + swimmer.getName() + ", successfully added");
        return true;
    }


    //REQUIRES: name must be unique from the names on the team list
    //MODIFIES: this
    //EFFECTS: adds a new member to the team if not full and returns true
    //          else, false
    public boolean addMember(Swimmer swimmer) {
        if (isFull()) {
            System.out.println("team is full. Can't add new members");
            return false;
        } else {
            if (swimmer.isCaptain()) {
                return addCaptain(swimmer);
            } else if (swimmer.isViceCaptain()) {
                return addViceCaptain(swimmer);
            } else {
                return addSwimmer(swimmer);
            }
        }
    }


    //MODIFIES: this
    //EFFECTS: removes captain and sets has captain to false, then returns true
    //          (returns false in the method it's called, when team is empty, or person not found)
    private boolean removeCaptain(Swimmer swimmer) {
        team.remove(swimmer);
        setHasCaptain(false);
        System.out.println(swimmer.getName() + " has been removed from the team");
        System.out.println("captain position available");
        return true;
    }


    //MODIFIES: this
    //EFFECTS: removes vice-captain and sets has vice captain to false, then returns true
    //          (returns false in the method it's called, when team is empty, or person not found)
    private boolean removeViceCaptain(Swimmer swimmer) {
        team.remove(swimmer);
        setHasViceCaptain(false);
        System.out.println(swimmer.getName() + " has been removed from the team");
        System.out.println("vice captain position available");
        return true;
    }


    //MODIFIES: this
    //EFFECTS: removes swimmer from the team and returns true
    //          (returns false in the method it's called, when team is empty, or person not found)
    private boolean removeSwimmer(Swimmer swimmer) {
        team.remove(swimmer);
        System.out.println(swimmer.getName() + " has been removed from the team");
        return true;
    }


    //MODIFIES:this
    //EFFECTS: removes swimmer from the team is not empty and returns true
    //          else false
    public boolean removeMember(Swimmer swimmer) {
        if (team.isEmpty() || !team.contains(swimmer)) {
            System.out.println(swimmer.getName() + " is not on the team");
            return false;
        } else {
            if (swimmer.isCaptain()) {
                return removeCaptain(swimmer);
            } else if (swimmer.isViceCaptain()) {
                return removeViceCaptain(swimmer);
            } else {
                return removeSwimmer(swimmer);
            }
        }
    }



    //EFFECTS: returns the size of the team
    public int teamSize() {
        return team.size();
    }


    //EFFECTS: returns true if the team size == maxSize
    public boolean isFull() {
        return teamSize() == maxSize;
    }

    //EFFECTS: returns swimmer in the given position
    public Swimmer getIndexValue(int index) {
        return team.get(index);
    }


    //REQUIRES: swimmer should be on the team
    //MODIFIES: this, and Swimmer
    //EFFECTS: chooses someone from the team to be a captain
    //          if there is a captain, replaces them in title
    //          and swimmer is moved to the front of the list
    public void chooseCaptain(Swimmer swimmer) {
        if (hasCaptain()) {
            team.get(0).setCaptain(false);
        }
        swimmer.setCaptain(true);
        team.remove(swimmer);
        team.addFirst(swimmer);
        System.out.println(swimmer.getName() + " is the new team captain");
        setHasCaptain(true);
    }


    //REQUIRES: swimmer should be on the team
    //MODIFIES: this and Swimmer
    //EFFECTS: chooses someone from the team to be a v.captain
    //          if there is already a v.captain, replace them
    //          and moves them to the front of the list if hasCaptain() is false
    //          else moves swimmer to index 1
    public void chooseViceCaptain(Swimmer swimmer) {
        if (hasCaptain) {
            if (hasViceCaptain()) {
                team.get(1).setViceCaptain(false);
            }
            swimmer.setViceCaptain(true);
            team.remove(swimmer);
            team.add(1, swimmer);
        } else {
            if (hasViceCaptain()) {
                team.get(0).setViceCaptain(false);
            }
            swimmer.setViceCaptain(true);
            team.remove(swimmer);
            team.add(0, swimmer);
        }
        System.out.println(swimmer.getName() + " is the new team vice-captain");
        setHasViceCaptain(true);
    }



    //EFFECTS: if team is empty, returns false
    //          else prints out each member of the team. Captains and vice-captains will be highlighted
    //          and returns true
    public boolean viewTeam() {
        System.out.println(name + " Swim Team");
        if (team.isEmpty()) {
            System.out.println("No members on team");
            return false;
        } else {
            for (Swimmer s : team) {
                if (s.isCaptain()) {
                    System.out.println("Team captain: " + s.getName());
                } else if (s.isViceCaptain()) {
                    System.out.println("Vice-captain: " + s.getName());
                } else {
                    System.out.println("              " + s.getName());
                }
            }
            return true;
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonSwimTeam = new JSONObject();
        jsonSwimTeam.put("max size", maxSize);
        jsonSwimTeam.put("name", name);
//        jsonSwimTeam.put("has captain", hasCaptain);
//        jsonSwimTeam.put("has vice-captain", hasViceCaptain);
        jsonSwimTeam.put("team", teamToJson());
        return jsonSwimTeam;
    }

    //EFFECTS: returns swimmers in the team as a json array
    public JSONArray teamToJson() {
        JSONArray teamJson = new JSONArray();
        for (Swimmer s: team) {
            teamJson.put(s.toJson());
        }

        return teamJson;
    }
}
