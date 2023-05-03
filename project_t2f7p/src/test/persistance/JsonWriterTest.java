package persistance;

import model.SwimTeam;
import model.Swimmer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//testing for JsonWriter
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
//inspiration for testing in the link above
public class JsonWriterTest {

    Swimmer drake;
    Swimmer josh;
    Swimmer chad;
    Swimmer bob;
    SwimTeam st;

    @BeforeEach
    void setup() {
        drake = new Swimmer("drake");
        drake.setCaptain(true);
        josh = new Swimmer("josh");
        josh.setMainStroke("butterfly");
        josh.setButterfly50m(30);
        josh.setButterfly100m(65);
        josh.setViceCaptain(true);
        chad = new Swimmer("chad");
        chad.setMainStroke("back stroke");
        chad.setBackStroke50m(30);
        chad.setBackStroke100m(55);
        bob = new Swimmer("bob");
        bob.setMainStroke("breast stroke");
        bob.setButterfly50m(30);
        bob.setButterfly100m(60);
        st = new SwimTeam("UBC Dolphins");
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }


    @Test
    void testWriteEmptySwimTeam() {
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


    @Test
    void testWriteGeneralTeam() {
        try {
            st.addMember(drake);
            st.addMember(josh);
            st.addMember(chad);
            st.addMember(bob);
            st.setMaX(20);
            JsonWriter writer = new JsonWriter("./data/testWriteGeneralTeam.json");
            writer.open();
            writer.write(st);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriteGeneralTeam.json");
            st = reader.read();
            testTeamWritten(st);
            testSwimmersWritten(st);
            testSwimmersWritten(st);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    private void testTeamWritten(SwimTeam st) {
        assertEquals("UBC Dolphins", st.getName());
        assertEquals(4, st.teamSize());
        assertTrue(st.hasCaptain());
        assertTrue(st.hasViceCaptain());
        assertEquals(20, st.getMax());
    }

    private void testSwimmersWritten(SwimTeam st) {
        Swimmer captain = st.getIndexValue(0);
        Swimmer viceCaptain = st.getIndexValue(1);
        assertEquals("drake", captain.getName());
        assertEquals("freestyle", captain.getMainStroke());
        assertEquals(0, captain.getFreeStyle50m());
        assertTrue(captain.isCaptain());
        assertTrue(viceCaptain.isViceCaptain());
        assertEquals(30, st.getIndexValue(2).getBackStroke50m());
        assertEquals("back stroke", st.getIndexValue(2).getMainStroke());
        assertEquals(60, st.getIndexValue(3).getButterfly100m());
        assertFalse(st.getIndexValue(3).isCaptain());
        assertFalse(st.getIndexValue(2).isViceCaptain());
    }


}
