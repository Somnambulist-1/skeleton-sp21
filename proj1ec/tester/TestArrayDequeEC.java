package tester;

import static org.junit.Assert.*;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import student.StudentArrayDeque;

public class TestArrayDequeEC {
    @Test
    public void Testing() {
        StudentArrayDeque<Integer> stu = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ans = new ArrayDequeSolution<>();

        int N = 5000;
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < N; i++) {
            int oper = StdRandom.uniform(4);
            if (oper == 0) {
                int item = StdRandom.uniform(1000);
                message.append("addFirst(").append(item).append(")\n");
                stu.addFirst(item);
                ans.addFirst(item);
            } else if (oper == 1) {
                int item = StdRandom.uniform(1000);
                message.append("addLast(").append(item).append(")\n");
                stu.addLast(item);
                ans.addLast(item);
            } else if (!ans.isEmpty()) {
                if (oper == 2) {
                    message.append("removeFirst()\n");
                    assertEquals(message.toString(), stu.removeFirst(), ans.removeFirst());
                } else if (oper == 3) {
                    message.append("removeLast()\n");
                    assertEquals(message.toString(), stu.removeLast(), ans.removeLast());
                }
            }
        }
    }
}
