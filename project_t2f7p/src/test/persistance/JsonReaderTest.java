package persistance;

import model.SwimTeam;
import model.Swimmer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

//testing for JsonReader
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
//inspiration for testing in the link above
public class JsonReaderTest {

    SwimTeam st;

    @BeforeEach
    void setup() {
        st = new SwimTeam("UBC Dolphins");
    }

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            SwimTeam st = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            JsonWriter writer = new JsonWriter("./data/testEmptySwimTeam.json");
            writer.open();
            writer.write(st);
            writer.close();

            JsonReader reader = new JsonReader("./data/testEmptySwimTeam.json");
            st = reader.read();
            assertEquals("UBC Dolphins", st.getName());
            assertEquals(0, st.teamSize());
            assertFalse(st.hasCaptain());
            assertFalse(st.hasViceCaptain());
            assertEquals(10, st.getMax());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
