package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Tests for PromptLibrary class
class PromptLibraryTest {
    PromptLibrary plTest;
    Prompt testPrompt1;
    Prompt testPrompt2;

    @BeforeEach
    public void runBefore() {
        plTest = new PromptLibrary();
        testPrompt1 = new Prompt("Test1");
        testPrompt2 = new Prompt("Test2");
    }

    @Test
    public void constructorTest() {
        assertEquals(0, plTest.getSize());
    }

    @Test
    public void addPromptTest() {
        plTest.addPrompt(testPrompt1);
        assertEquals(1, plTest.getSize());
        plTest.addPrompt(testPrompt2);
        assertEquals(2, plTest.getSize());

        assertEquals(1, testPrompt1.getPromptNum());
        assertEquals(2, testPrompt2.getPromptNum());
    }

    @Test
    public void retrievePromptTest() {
        plTest.addPrompt(testPrompt1);
        assertEquals(testPrompt1, plTest.retrievePrompt(1));

        plTest.addPrompt(testPrompt2);
        assertEquals(testPrompt2, plTest.retrievePrompt(2));

        assertEquals("ERROR: PROMPT_NOT_FOUND", plTest.retrievePrompt(3).getText());
    }

    @Test
    public void getAllPlayerNamesTest() {
        assertEquals("", plTest.getAllPromptText());

        plTest.addPrompt(testPrompt1);
        plTest.addPrompt(testPrompt2);

        assertEquals("Test1\n Test2", plTest.getAllPromptText());
    }
}