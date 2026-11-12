package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * all-DU-pairs coverage for DataFlowTargets.gcd (lines 13-21).
 * Pair IDs (G1..G13) match the gcd DU table in README.md.
 */
class GcdTest {

    @Test
    @DisplayName("G-T1 gcd(7,0)=7: x 13->20, y 13->(15,F) [loop skipped]")
    void gcdYZeroSkipsLoop() {
        assertEquals(7, DataFlowTargets.gcd(7, 0));
    }

    @Test
    @DisplayName("G-T2 gcd(12,8)=4, 2 iterations: x 13->16, 17->16, 17->20; y 13->(15,T),16,17; y 18->(15,T),(15,F),16,17; tmp 16->18")
    void gcdTwoIterations() {
        assertEquals(4, DataFlowTargets.gcd(12, 8));
    }

    @Test
    @DisplayName("G-T3 gcd(0,5)=5, 1 iteration: x 17->20 directly, y 18->(15,F)")
    void gcdOneIterationXZero() {
        assertEquals(5, DataFlowTargets.gcd(0, 5));
    }
}
