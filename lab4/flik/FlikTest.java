package flik;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FlikTest {
    @Test
    public void Test() {
        for (int i = 0; i < 5000; i++){
            assertTrue(Flik.isSameNumber(i, i));
            assertFalse(Flik.isSameNumber(i, i + 1));
        }
    }
}
