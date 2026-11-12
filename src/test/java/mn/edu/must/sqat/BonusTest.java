package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Stage 1: ALL-DEFS for DataFlowTargets.bonus (lines 31-57).
 * Minimum is 2 tests: score def 49 reaches a use only when (50,F),
 * score def 51 exists only when (50,T) and kills def 49 -> two executions.
 */
class BonusTest {

    @Test
    @DisplayName("AD1 bonus([1,0,1],2)=5: present 35->40,40->50; streak 36->41,41->42,46->41; best 37->42,43->53; score 51->56; a 38->39")
    void allDefsMixedAttendance() {
        assertEquals(5, DataFlowTargets.bonus(new int[] {1, 0, 1}, 2));
    }

    @Test
    @DisplayName("AD2 bonus([],1)=0: score 49->56 via (50,F),(53,F)")
    void allDefsEmptyArray() {
        assertEquals(0, DataFlowTargets.bonus(new int[] {}, 1));
    }
}
