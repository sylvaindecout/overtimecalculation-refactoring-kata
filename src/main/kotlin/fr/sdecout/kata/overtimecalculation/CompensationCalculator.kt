package fr.sdecout.kata.overtimecalculation

import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours

object CompensationCalculator {

    val MAX_OVERTIME_HOURS_RATE_1 = 10.hours
    val THRESHOLD_OVERTIME_HOURS_RATE_2 = 6.hours

    fun calculateOvertime(overtimeTotalDuration: Duration, assignment: Assignment, briefing: Briefing): Overtime {
        require(!overtimeTotalDuration.isNegative()) { "Overtime total duration must not be negative ($overtimeTotalDuration)" }
        val overtimeInRate1 = resolveOvertimeInRate1(overtimeTotalDuration, assignment.isUnionized, briefing)
        val overtimeInRate2 = resolveOvertimeInRate2(assignment, overtimeTotalDuration - overtimeInRate1)
        return Overtime(overtimeInRate1, overtimeInRate2)
    }

    private fun resolveOvertimeInRate1(overtimeTotalDuration: Duration, unionizedAssignment: Boolean, briefing: Briefing) =
        if (briefing.allowsExceedingOvertimeLimitInRate1(unionizedAssignment)) overtimeTotalDuration
        else minOf(overtimeTotalDuration, MAX_OVERTIME_HOURS_RATE_1)

    private fun resolveOvertimeInRate2(assignment: Assignment, remainingOvertimeDuration: Duration) =
        if (assignment.isUnionized) minOf(
            remainingOvertimeDuration,
            calculateThreshold(assignment, THRESHOLD_OVERTIME_HOURS_RATE_2)
        )
        else remainingOvertimeDuration

    private fun calculateThreshold(assignment: Assignment, thresholdForUnionizedAssignments: Duration) = assignment
        .let { assignment.duration - thresholdForUnionizedAssignments }
        .let { remainder ->
            if (remainder.isNegative()) (assignment.duration.inWholeSeconds / 3600).hours
            else thresholdForUnionizedAssignments
        }

}