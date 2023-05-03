package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SwimTeamTest {

    SwimTeam st;
    Swimmer drake;
    Swimmer josh;
    Swimmer chad;
    Swimmer bob;
    Swimmer dash;

    @BeforeEach
    public void setup() {
        st = new SwimTeam("test team");
        drake = new Swimmer("drake");
        drake.setCaptain(true);
        josh = new Swimmer("josh");
        josh.setViceCaptain(true);
        chad = new Swimmer("chad");
        bob = new Swimmer("bob");
        dash = new Swimmer("dash");
    }

    @Test
    public void checkChangeName() {
        assertEquals("test team", st.getName());
        st.setName("Test team");
        assertEquals("Test team", st.getName());
    }


    @Test
    public void increaseTeamSizeTest() {
        st.setMaX(100);
        assertEquals(100, st.getMax());
    }

    @Test
    public void decreaseTeamSizeTest() {
        st.setMaX(5);
        assertEquals(10, st.getMax());
    }

    @Test
    public void addOneMemberTest() {
        assertTrue(st.addMember(drake));
        assertEquals(1,st.teamSize());
    }

    @Test
    public void addMultipleMemberTest() {
        assertTrue(st.addMember(drake));
        assertTrue(st.addMember(josh));
        assertEquals(2,st.teamSize());
    }

    @Test
    public void addCaptainTest() {
        assertFalse(st.hasCaptain());
        assertTrue(st.addMember(chad));
        assertFalse(st.hasCaptain());
        assertTrue(st.addMember(drake));
        assertEquals(drake,st.getIndexValue(0));
        assertTrue(st.hasCaptain());
    }

    @Test
    public void addTwoCaptainsTest() {
        assertFalse(st.hasCaptain());
        assertTrue(st.addMember(drake));
        assertEquals(drake,st.getIndexValue(0));
        assertTrue(st.hasCaptain());
        assertFalse(st.addMember(drake));
    }

    @Test
    public void addViceCaptainTest() {
        assertFalse(st.hasViceCaptain());
        assertTrue(st.addMember(chad));
        assertFalse(st.hasViceCaptain());
        assertTrue(st.addMember(josh));
        assertEquals(josh,st.getIndexValue(0));
        assertEquals(chad,st.getIndexValue(1));
        assertTrue(st.hasViceCaptain());
    }

    @Test
    public void addTwoViceCaptainsTest() {
        assertFalse(st.hasViceCaptain());
        assertTrue(st.addMember(josh));
        assertEquals(josh,st.getIndexValue(0));
        assertTrue(st.hasViceCaptain());
        assertFalse(st.addMember(josh));
    }

    @Test
    public void addViceCaptainAndCaptainTest() {
        assertFalse(st.hasViceCaptain());
        assertFalse(st.hasCaptain());
        assertTrue(st.addMember(chad));
        assertFalse(st.hasViceCaptain());
        assertFalse(st.hasCaptain());
        assertTrue(st.addMember(josh));
        assertTrue(st.addMember(drake));
        assertEquals(drake,st.getIndexValue(0));
        assertEquals(josh,st.getIndexValue(1));
        assertEquals(chad,st.getIndexValue(2));
        assertTrue(st.hasViceCaptain());
        assertTrue(st.hasCaptain());
    }

    @Test
    public void addNormalSwimmersTest() {
        st.addMember(chad);
        st.addMember(bob);
        assertFalse(st.hasCaptain());
        assertFalse(st.hasViceCaptain());
        assertEquals(chad,st.getIndexValue(0));
        assertEquals(bob,st.getIndexValue(1));
        assertFalse(st.hasCaptain());
        assertFalse(st.hasViceCaptain());
    }


    @Test
    public void checkAddToFullTeamTest() {
        for (int i = 0; i<10; i++) {
            st.addMember(chad);
        }
        assertFalse(st.addMember(josh));
    }

    @Test
    public void removeOneMemberTest() {
        st.addMember(drake);
        assertTrue(st.removeMember(drake));
        assertEquals(0, st.teamSize());
    }

    @Test
    public void removeMultipleMemberTest() {
        st.addMember(drake);
        st.addMember(josh);
        st.addMember(chad);
        assertTrue(st.removeMember(drake));
        assertEquals(2, st.teamSize());
        assertTrue(st.removeMember(chad));
        assertEquals(1, st.teamSize());
        assertTrue(st.removeMember(josh));
        assertEquals(0, st.teamSize());
    }

    @Test
    public void checkRemoveSomeoneNotThereTest() {
        st.addMember(drake);
        st.addMember(josh);
        assertFalse(st.removeMember(chad));
        assertTrue(st.removeMember(drake));
        assertTrue(st.removeMember(josh));
        assertFalse(st.removeMember(josh));
    }

    @Test
    public void RemoveCaptainTest() {
        st.addMember(drake);
        st.addMember(josh);
        st.addMember(chad);
        assertTrue(st.hasCaptain());
        st.removeMember(drake);
        assertFalse(st.hasCaptain());
        assertEquals(josh, st.getIndexValue(0));
        assertEquals(chad, st.getIndexValue(1));
    }

    @Test
    public void RemoveViceCaptainTest() {
        st.addMember(drake);
        st.addMember(josh);
        st.addMember(chad);
        assertTrue(st.hasViceCaptain());
        st.removeMember(josh);
        assertFalse(st.hasViceCaptain());
        assertEquals(drake, st.getIndexValue(0));
        assertEquals(chad, st.getIndexValue(1));
    }

    @Test
    public void RemoveViceCaptainAndCaptainTest() {
        st.addMember(drake);
        st.addMember(josh);
        st.addMember(chad);
        assertTrue(st.hasViceCaptain());
        assertTrue(st.hasCaptain());
        st.removeMember(josh);
        assertFalse(st.hasViceCaptain());
        assertTrue(st.hasCaptain());
        st.removeMember(drake);
        assertFalse(st.hasCaptain());
        assertEquals(chad, st.getIndexValue(0));
    }

    @Test
    public void chooseACaptain() {
        st.addMember(chad);
        st.addMember(bob);
        st.addMember(dash);
        assertFalse(st.hasCaptain());
        assertEquals(chad, st.getIndexValue(0));
        assertEquals(dash, st.getIndexValue(2));
        st.chooseCaptain(dash);
        assertTrue(st.hasCaptain());
        assertEquals(dash, st.getIndexValue(0));
        assertEquals(chad, st.getIndexValue(1));
        assertEquals(bob, st.getIndexValue(2));
    }

    @Test
    public void chooseNewCaptain() {
        st.addMember(drake);
        st.addMember(josh);
        st.addMember(chad);
        assertTrue(drake.isCaptain());
        assertFalse(chad.isCaptain());
        assertEquals(chad, st.getIndexValue(2));
        st.chooseCaptain(chad);
        assertFalse(drake.isCaptain());
        assertTrue(chad.isCaptain());
        assertEquals(chad, st.getIndexValue(0));
    }

    @Test
    public void chooseAViceCaptain() {
        st.addMember(chad);
        st.addMember(bob);
        st.addMember(dash);
        assertFalse(st.hasViceCaptain());
        assertEquals(chad, st.getIndexValue(0));
        assertEquals(dash, st.getIndexValue(2));
        st.chooseViceCaptain(dash);
        assertTrue(st.hasViceCaptain());
        assertEquals(dash, st.getIndexValue(0));
        assertEquals(chad, st.getIndexValue(1));
        assertEquals(bob, st.getIndexValue(2));
    }

    @Test
    public void chooseAViceCaptainWithCaptainInTeam() {
        st.addMember(drake);
        st.addMember(bob);
        st.addMember(chad);
        assertTrue(st.hasCaptain());
        assertFalse(st.hasViceCaptain());
        st.chooseViceCaptain(chad);
        assertTrue(st.hasCaptain());
        assertTrue(st.hasViceCaptain());
        assertEquals(drake, st.getIndexValue(0));
        assertEquals(chad, st.getIndexValue(1));
        assertEquals(bob, st.getIndexValue(2));
    }

    @Test
    public void chooseNewViceCaptain() {
        st.addMember(chad);
        st.addMember(bob);
        st.addMember(josh);
        assertEquals(josh, st.getIndexValue(0));
        assertTrue(st.hasViceCaptain());
        assertTrue(josh.isViceCaptain());
        st.chooseViceCaptain(bob);
        assertTrue(st.hasViceCaptain());
        assertFalse(josh.isViceCaptain());
        assertTrue(bob.isViceCaptain());
        assertEquals(bob, st.getIndexValue(0));
        assertEquals(josh, st.getIndexValue(1));
        assertEquals(chad, st.getIndexValue(2));
    }

    @Test
    public void chooseNewViceCaptainWithCaptainInTeam() {
        st.addMember(drake);
        st.addMember(josh);
        st.addMember(chad);
        assertTrue(st.hasCaptain());
        assertTrue(st.hasViceCaptain());
        assertTrue(josh.isViceCaptain());
        st.chooseViceCaptain(chad);
        assertTrue(st.hasCaptain());
        assertTrue(st.hasViceCaptain());
        assertEquals(drake, st.getIndexValue(0));
        assertEquals(chad, st.getIndexValue(1));
        assertEquals(josh, st.getIndexValue(2));
        assertFalse(josh.isViceCaptain());
    }

    @Test
    public void viewTeamTest() {
        assertFalse(st.viewTeam());
        st.addMember(chad);
        assertTrue(st.viewTeam());
    }

    @Test
    public void viewTeamWithCaptainTest() {
        assertFalse(st.viewTeam());
        st.addMember(drake);
        assertTrue(st.viewTeam());
    }

    @Test
    public void viewTeamWithViceCaptainTest() {
        assertFalse(st.viewTeam());
        st.addMember(josh);
        assertTrue(st.viewTeam());
    }

    @Test
    public void viewTeamWithViceCaptainAndCaptainTest() {
        assertFalse(st.viewTeam());
        st.addMember(josh);
        assertTrue(st.viewTeam());
        st.addMember(drake);
        assertTrue(st.viewTeam());
    }

    @Test
    public void checkGetTeam() {
        assertEquals(0, st.getTeam().size());
        assertFalse(st.hasCaptain());
        st.addMember(drake);
        assertEquals(1, st.getTeam().size());
        assertEquals(drake, st.getTeam().get(0));
        assertTrue(st.hasCaptain());
    }

}