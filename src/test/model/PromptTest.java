package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PromptTest {

    Prompt promptTest;

    @BeforeEach
    public void runBefore() {
        promptTest = new Prompt("Test");
    }

    @Test
    public void constructorTest() {
        assertEquals("Test", promptTest.getText());
        assertEquals(0, promptTest.getPromptNum());
        assertFalse(promptTest.getUsed());
    }


}
