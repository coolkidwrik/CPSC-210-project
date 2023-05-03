package persistence;

import model.SwimTeam;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
//inspiration for JsonWriter in link
//EFFECTS: saves user data by writing to a json file (file field in TeamApp)
public class JsonWriter {

    private static final int TAB = 4; // indent factor, to make file readable
    private String file; // file location, provided in team app
    private PrintWriter writer; // prints formatted representations of objects

    // creates a new JsonWriter object that sets the file location to TeamApp.file
    public JsonWriter(String file) {
        this.file = file;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(file));
    }

    // MODIFIES: this
    // EFFECTS: writes a json representation of swim team to file
    public void write(SwimTeam st) {
        JSONObject teamJson = st.toJson();
        saveToFile(teamJson.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
