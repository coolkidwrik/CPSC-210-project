package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SwimmerTest {

    Swimmer drake;
    Swimmer josh;
    Swimmer chad;
    Swimmer bob;

    @BeforeEach
    public void setup() {
        drake = new Swimmer("drake"); //endurance
        josh = new Swimmer("josh"); //sprinter
        josh.setMainStroke("butterfly");
        josh.setButterfly50m(30);
        josh.setButterfly100m(65);
        chad = new Swimmer("chad"); //endurance
        chad.setMainStroke("back stroke");
        chad.setBackStroke50m(30);
        chad.setBackStroke100m(55);
        bob = new Swimmer("bob"); //endurance
        bob.setMainStroke("breast stroke");
        bob.setButterfly50m(30);
        bob.setButterfly100m(60);
    }

    @Test
    public void checkNamesSetCorrect() {
        drake.setName("Drake");
        assertEquals("Drake", drake.getName());
        drake.setName("Chad");
        assertEquals("Chad", drake.getName());
    }

    @Test
    public void checkSetMainStrokes() {
        assertEquals("freestyle", drake.getMainStroke());
        drake.setMainStroke("butterfly");
        assertEquals("butterfly", drake.getMainStroke());
        assertEquals("butterfly", josh.getMainStroke());
        josh.setMainStroke("breast stroke");
        assertEquals("breast stroke", josh.getMainStroke());
    }

    @Test
    public void setGetFree() {
        drake.setFreeStyle50m(30);
        drake.setFreeStyle100m(60);
        assertEquals(30, drake.getFreeStyle50m());
        assertEquals(60, drake.getFreeStyle100m());
    }

    @Test
    public void setGetBreast() {
        drake.setBreastStroke50m(30);
        drake.setBreastStroke100m(60);
        assertEquals(30, drake.getBreastStroke50m());
        assertEquals(60, drake.getBreastStroke100m());
    }

    @Test
    public void setGetBack() {
        drake.setBackStroke50m(30);
        drake.setBackStroke100m(60);
        assertEquals(30, drake.getBackStroke50m());
        assertEquals(60, drake.getBackStroke100m());
    }

    @Test
    public void setGetFly() {
        drake.setButterfly50m(30);
        drake.setButterfly100m(60);
        assertEquals(30, drake.getButterfly50m());
        assertEquals(60, drake.getButterfly100m());
    }



    @Test
    public void sprinterOrEnduranceSwimmerFreeStyleTest() {
        assertEquals("freestyle", drake.getMainStroke());
        assertEquals("Endurance Swimmer", drake.sprinterOrEnduranceSwimmer());
        drake.setFreeStyle100m(50);
        assertEquals("Sprinter", drake.sprinterOrEnduranceSwimmer());
    }

    @Test
    public void sprinterOrEnduranceSwimmerBreastStrokeTest() {
        assertEquals("breast stroke", bob.getMainStroke());
        assertEquals("Endurance Swimmer", bob.sprinterOrEnduranceSwimmer());
        bob.setBreastStroke100m(70);
        assertEquals("Sprinter", bob.sprinterOrEnduranceSwimmer());
    }

    @Test
    public void sprinterOrEnduranceSwimmerBackStrokeTest() {
        assertEquals("back stroke", chad.getMainStroke());
        assertEquals("Endurance Swimmer", chad.sprinterOrEnduranceSwimmer());
        chad.setBackStroke100m(70);
        assertEquals("Sprinter", chad.sprinterOrEnduranceSwimmer());
    }

    @Test
    public void sprinterOrEnduranceSwimmerButterflyTest() {
        assertEquals("butterfly", josh.getMainStroke());
        assertEquals("Sprinter", josh.sprinterOrEnduranceSwimmer());
        josh.setButterfly100m(40);
        assertEquals("Endurance Swimmer", josh.sprinterOrEnduranceSwimmer());
    }
}
