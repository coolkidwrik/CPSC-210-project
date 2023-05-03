package persistence;

import model.SwimTeam;
import model.Swimmer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
//inspiration for JsonReader in link
//EFFECTS: reads from json file to recover previous user data
public class JsonReader {

    private String file;

    //EFFECTS: constructs a reader to read from the file
    public JsonReader(String file) {
        this.file = file;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public SwimTeam read() throws IOException {
        String jsonData = readFile(file);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSwimTeam(jsonObject);
    }

    //EFFECTS: reads file as string and returns it
    private String readFile(String file) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(file), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    //MODIFIES: SwimTeam
    // EFFECTS: parses workroom from JSON object and returns it
    private SwimTeam parseSwimTeam(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int maxSize = jsonObject.getInt("max size");
//        boolean hasCaptain = jsonObject.getBoolean("has captain");
//        boolean hasViceCaptain = jsonObject.getBoolean("has vice-captain");
        SwimTeam st = new SwimTeam(name);
        st.setMaX(maxSize);
//        st.setHasCaptain(hasCaptain);
//        st.setHasViceCaptain(hasViceCaptain);
        addTeam(st, jsonObject);
        return st;
    }

    // MODIFIES: SwimTeam
    // EFFECTS: parses swimmers on team from JSON object and adds them to swim team
    private void addTeam(SwimTeam st, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("team");
        for (Object json : jsonArray) {
            JSONObject nextSwimmer = (JSONObject) json;
            addSwimmer(st, nextSwimmer);
        }
    }

    // MODIFIES: SwimTeam and Swimmer
    // EFFECTS: parses Swimmer from JSON object and adds it to workroom
    private void addSwimmer(SwimTeam st, JSONObject jsonObject) {
        Swimmer sw = new Swimmer(jsonObject.getString("name"));
        sw.setCaptain(jsonObject.getBoolean("captain"));
        sw.setViceCaptain(jsonObject.getBoolean("vice-captain"));
        sw.setMainStroke(jsonObject.getString("main stroke"));
        sw.setFreeStyle50m(jsonObject.getDouble("freestyle 50m"));
        sw.setFreeStyle100m(jsonObject.getDouble("freestyle 100m"));
        sw.setBreastStroke50m(jsonObject.getDouble("breast stroke 50m"));
        sw.setBreastStroke100m(jsonObject.getDouble("breast stroke 100m"));
        sw.setBackStroke50m(jsonObject.getDouble("back stroke 50m"));
        sw.setBackStroke100m(jsonObject.getDouble("back stroke 100m"));
        sw.setButterfly50m(jsonObject.getDouble("butterfly 50m"));
        sw.setButterfly100m(jsonObject.getDouble("butterfly 100m"));
        st.addMember(sw);
    }

}
