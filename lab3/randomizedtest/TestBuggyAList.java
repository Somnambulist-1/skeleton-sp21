package randomizedtest;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        BuggyAList<Integer> Testee = new BuggyAList<>();
        AListNoResizing<Integer> Tester = new AListNoResizing<>();

        Testee.addLast(4);
        Testee.addLast(5);
        Testee.addLast(6);

        Tester.addLast(4);
        Tester.addLast(5);
        Tester.addLast(6);

        assertEquals(Testee.size(),Tester.size());

        assertEquals(Testee.removeLast(), Tester.removeLast());
        assertEquals(Testee.removeLast(), Tester.removeLast());
        assertEquals(Testee.removeLast(), Tester.removeLast());
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> B = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                B.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                assertEquals(size, B.size());
            } else if (L.size() == 0) {
                continue;
            } else if (operationNumber == 2) {
                int blast = B.getLast();
                int llast = L.getLast();
                assertEquals(blast, llast);
            } else if (operationNumber == 3) {
                int blast = B.removeLast();
                int llast = L.removeLast();
                assertEquals(B.size(), L.size());
                assertEquals(blast, llast);
            }
        }
    }
}
