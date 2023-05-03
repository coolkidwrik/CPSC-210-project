package model;

import org.json.JSONObject;
import persistence.Writable;

//represents all the information to do with swimmers within a team
public class Swimmer implements Writable {
    private String name;
    private boolean captain = false;
    private boolean viceCaptain = false;
    private String mainStroke = "freestyle";
    private double freeStyle50m = 0;
    private double freeStyle100m = 0;
    private double breastStroke50m = 0;
    private double breastStroke100m = 0;
    private double backStroke50m = 0;
    private double backStroke100m = 0;
    private double butterfly50m = 0;
    private double butterfly100m = 0;

    //EFFECTS: creates a swimmer
    public Swimmer(String name) {
        this.name = name;
    }


    //getter for swimmer name
    public String getName() {
        return name;
    }


    //setter for swimmer name
    public void setName(String name) {
        this.name = name;
    }

    //getter for captain status
    public boolean isCaptain() {
        return captain;
    }

    //setter for captain status
    public void setCaptain(boolean captain) {
        this.captain = captain;
    }


    //getter for vice-captain status
    public boolean isViceCaptain() {
        return viceCaptain;
    }


    //setter for vice-captain status
    public void setViceCaptain(boolean viceCaptain) {
        this.viceCaptain = viceCaptain;
    }

    //getter for swimmer's main stroke
    public String getMainStroke() {
        return mainStroke;
    }

    //setter for swimmer's main stroke
    public void setMainStroke(String mainStroke) {
        this.mainStroke = mainStroke;
    }

    //getter for 50m freestyle
    public double getFreeStyle50m() {
        return freeStyle50m;
    }

    //setter for 50m freestyle
    public void setFreeStyle50m(double freeStyle50m) {
        this.freeStyle50m = freeStyle50m;
    }

    //getter for 100m freestyle
    public double getFreeStyle100m() {
        return freeStyle100m;
    }

    //setter for 100m freestyle
    public void setFreeStyle100m(double freeStyle100m) {
        this.freeStyle100m = freeStyle100m;
    }

    //getter for 50m breast stroke
    public double getBreastStroke50m() {
        return breastStroke50m;
    }

    //setter for 50m breast stroke
    public void setBreastStroke50m(double breastStroke50m) {
        this.breastStroke50m = breastStroke50m;
    }

    //getter for 100m breast stroke
    public double getBreastStroke100m() {
        return breastStroke100m;
    }

    //setter for 100m breast stroke
    public void setBreastStroke100m(double breastStroke100m) {
        this.breastStroke100m = breastStroke100m;
    }

    //getter for 50m back stroke
    public double getBackStroke50m() {
        return backStroke50m;
    }

    //setter for 50m back stroke
    public void setBackStroke50m(double backStroke50m) {
        this.backStroke50m = backStroke50m;
    }

    //getter for 100m back stroke
    public double getBackStroke100m() {
        return backStroke100m;
    }

    //setter for 100m back stroke
    public void setBackStroke100m(double backStroke100m) {
        this.backStroke100m = backStroke100m;
    }

    //getter for 50m butterfly
    public double getButterfly50m() {
        return butterfly50m;
    }

    //setter for 50m butterfly
    public void setButterfly50m(double butterfly50m) {
        this.butterfly50m = butterfly50m;
    }

    //getter for 100m butterfly
    public double getButterfly100m() {
        return butterfly100m;
    }

    //setter for 100m butterfly
    public void setButterfly100m(double butterfly100m) {
        this.butterfly100m = butterfly100m;
    }



    //EFFECTS: decides if swimmer is an Endurance Swimmer or a Sprinter
    public String sprinterOrEnduranceSwimmer() {
        if (mainStroke.equals("freestyle")) {
            return freestyleSOrE();
        } else if (mainStroke.equals("breast stroke")) {
            return breastStrokeSOrE();
        } else if (mainStroke.equals("back stroke")) {
            return backStrokeSOrE();
        } else {
            return butterflySOrE();
        }
    }

    //EFFECTS: if 50m freestyle timing is faster than half 100m timing, then sprinter, else endurance
    private String freestyleSOrE() {
        if (freeStyle50m < (freeStyle100m / 2)) {
            return "Sprinter";
        } else {
            return "Endurance Swimmer";
        }
    }

    //EFFECTS: if 50m breast stroke timing is faster than half 100m timing, then sprinter, else endurance
    private String breastStrokeSOrE() {
        if (breastStroke50m < (breastStroke100m / 2)) {
            return "Sprinter";
        } else {
            return "Endurance Swimmer";
        }
    }

    //EFFECTS: if 50m back stroke timing is faster than half 100m timing, then sprinter, else endurance
    private String backStrokeSOrE() {
        if (backStroke50m < (backStroke100m / 2)) {
            return "Sprinter";
        } else {
            return "Endurance Swimmer";
        }
    }

    //EFFECTS: if 50m butterfly timing is faster than half 100m timing, then sprinter, else endurance
    private String butterflySOrE() {
        if (butterfly50m < (butterfly100m / 2)) {
            return "Sprinter";
        } else {
            return "Endurance Swimmer";
        }
    }


    @Override
    public JSONObject toJson() {
        JSONObject jsonSwimmer = new JSONObject();
        jsonSwimmer.put("name", name);
        jsonSwimmer.put("captain", captain);
        jsonSwimmer.put("vice-captain", viceCaptain);
        jsonSwimmer.put("main stroke", mainStroke);
        jsonSwimmer.put("freestyle 50m", freeStyle50m);
        jsonSwimmer.put("freestyle 100m", freeStyle100m);
        jsonSwimmer.put("breast stroke 50m", breastStroke50m);
        jsonSwimmer.put("breast stroke 100m", breastStroke100m);
        jsonSwimmer.put("back stroke 50m", backStroke50m);
        jsonSwimmer.put("back stroke 100m", backStroke100m);
        jsonSwimmer.put("butterfly 50m", butterfly50m);
        jsonSwimmer.put("butterfly 100m", butterfly100m);
        return jsonSwimmer;
    }
}
