package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Tests for PromptLibrary class
class PromptLibraryTest {
    PromptLibrary plTest;

    @BeforeEach
    public void runBefore() {
        plTest = new PromptLibrary();
    }

    @Test
    public void constructorTest() {
        assertEquals(0, plTest.getSize());
    }

    @Test
    public void addPromptTest() {
        Prompt testPrompt1 = new Prompt("Test1");
        Prompt testPrompt2 = new Prompt("Test2");

        plTest.addPrompt(testPrompt1);
        assertEquals(1, plTest.getSize());

        plTest.addPrompt(testPrompt2);
        assertEquals(2, plTest.getSize());
    }

    @Test
    public void retrievePromptTest() {
        Prompt testPrompt1 = new Prompt("Test1");
        Prompt testPrompt2 = new Prompt("Test2");

        plTest.addPrompt(testPrompt1);
        assertEquals("Test1", plTest.retrievePrompt(1));

        plTest.addPrompt(testPrompt2);
        assertEquals("Test2", plTest.retrievePrompt(2));
    }


}