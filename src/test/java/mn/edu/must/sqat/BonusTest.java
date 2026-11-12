package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Stage 1: ALL-DEFS (AD1, AD2) -> Stage 2: ALL-DU-PAIRS (DU1..DU9).
 * Pair IDs (P1..P33) match the bonus DU table in README.md.
 * Minimum ALL-DEFS is 2 tests: score def 49 reaches a use only when (50,F),
 * score def 51 exists only when (50,T) and kills def 49 -> two executions.
 */
class BonusTest {

    // ---------- Stage 1: ALL-DEFS ----------

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

    // ---------- Stage 2: ALL-DU-PAIRS ----------

    @Test
    @DisplayName("DU1 bonus(null,0) throws: attendance 31->(32,T) [P1]")
    void nullAttendanceThrows() {
        assertThrows(IllegalArgumentException.class, () -> DataFlowTargets.bonus(null, 0));
    }

    @Test
    @DisplayName("DU2 bonus([1],-1) throws: threshold 31->(32,T) [P5]")
    void negativeThresholdThrows() {
        assertThrows(IllegalArgumentException.class, () -> DataFlowTargets.bonus(new int[] {1}, -1));
    }

    @Test
    @DisplayName("DU3 bonus([],0)=5: present 35->(50,T), threshold 31->(50,T) [P10,P7]")
    void emptyArrayZeroThreshold() {
        assertEquals(5, DataFlowTargets.bonus(new int[] {}, 0));
    }

    @Test
    @DisplayName("DU4 bonus([0,0,0],0)=5: all absent, a 38->(39,F), present 35->(50,T) [P33,P10]")
    void allAbsentZeroThreshold() {
        assertEquals(5, DataFlowTargets.bonus(new int[] {0, 0, 0}, 0));
    }

    @Test
    @DisplayName("DU5 bonus([1,1,1,1,1],5)=8: streak 41->41, best 43->(42,T),(53,T), score 51->54, 54->56 [P16,P23,P25,P29,P31]")
    void fiveStreakMeetsThreshold() {
        assertEquals(8, DataFlowTargets.bonus(new int[] {1, 1, 1, 1, 1}, 5));
    }

    @Test
    @DisplayName("DU6 bonus([1,1,1,1,1],10)=3: score 49->54, present 40->(50,F) [P27,P14]")
    void fiveStreakBelowThreshold() {
        assertEquals(3, DataFlowTargets.bonus(new int[] {1, 1, 1, 1, 1}, 10));
    }

    @Test
    @DisplayName("DU7 bonus([1],2)=0: present 40->(50,F), score 49->56 with a present day [P14,P28]")
    void singlePresentBelowThreshold() {
        assertEquals(0, DataFlowTargets.bonus(new int[] {1}, 2));
    }

    @Test
    @DisplayName("DU8 bonus([1,1,1,1,0,1,1,1,1],8)=5: long run broken in middle, best 43->(42,F),(53,F)")
    void brokenLongStreakNoStreakBonus() {
        assertEquals(5, DataFlowTargets.bonus(new int[] {1, 1, 1, 1, 0, 1, 1, 1, 1}, 8));
    }

    @Test
    @DisplayName("DU9 bonus([1,1,1,1,1,1,1],3)=8: streak longer than 5, both bonuses")
    void longerThanFiveStreak() {
        assertEquals(8, DataFlowTargets.bonus(new int[] {1, 1, 1, 1, 1, 1, 1}, 3));
    }

    // ---------- Part C: M2 killer ----------

    @Test
    @DisplayName("K1 bonus([1,0,1,1,1,1],10)=0: best must be max STREAK (4), not present count (5) - kills M2")
    void m2KillShortRunThenLongerRun() {
        assertEquals(0, DataFlowTargets.bonus(new int[] {1, 0, 1, 1, 1, 1}, 10));
    }
}
