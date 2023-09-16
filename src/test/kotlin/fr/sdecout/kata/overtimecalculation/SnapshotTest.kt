package fr.sdecout.kata.overtimecalculation

import org.approvaltests.combinations.CombinationApprovals.verifyAllCombinations
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

@Tag("Snapshot")
class SnapshotTest {

    @Test
    fun should_calculate_overtime() {
        val valuesForAssignmentDuration = arrayOf(Duration.ZERO, 45.minutes, 1.hours, 5.hours, 6.hours)
        val valuesForOvertimeTotalDuration = arrayOf<Long>(0, 1, 10, 12, 17)
        val valuesForFlag = arrayOf(true, false)

        verifyAllCombinations(
            { assignmentDuration, overtimeTotalDuration, isUnionized, watCode, z3, foreign, hbmo ->
                CompensationCalculator.calculateOvertime(
                    overtimeTotalDuration.hours,
                    Assignment(isUnionized, assignmentDuration),
                    Briefing(watCode, z3, foreign, hbmo)
                )
            },
            valuesForAssignmentDuration,
            valuesForOvertimeTotalDuration,
            valuesForFlag,
            valuesForFlag,
            valuesForFlag,
            valuesForFlag,
            valuesForFlag
        )
    }

}


