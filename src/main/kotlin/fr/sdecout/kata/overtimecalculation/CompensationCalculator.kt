package fr.sdecout.kata.overtimecalculation

import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours

object CompensationCalculator {

    fun calculateOvertime(overtimeTotalDuration: Duration, assignment: Assignment, briefing: Briefing): Overtime {
        require(!overtimeTotalDuration.isNegative()) { "Overtime total duration must not be negative ($overtimeTotalDuration)" }
        val overtimeInRate1 = resolveOvertimeInRate1(overtimeTotalDuration, assignment.isUnionized, briefing)
        val overtimeInRate2 = resolveOvertimeInRate2(assignment, overtimeTotalDuration - overtimeInRate1)
        return Overtime(overtimeInRate1, overtimeInRate2)
    }

    private fun resolveOvertimeInRate1(overtimeTotalDuration: Duration, unionizedAssignment: Boolean, briefing: Briefing) =
        if (briefing.allowsExceedingOvertimeLimitInRate1(unionizedAssignment)) overtimeTotalDuration
        else minOf(overtimeTotalDuration, 10.hours)

    private fun resolveOvertimeInRate2(assignment: Assignment, remainingOvertimeDuration: Duration) =
        if (assignment.isUnionized) minOf(remainingOvertimeDuration, assignment.duration.truncateToHours(), 6.hours)
        else remainingOvertimeDuration

    private fun Duration.truncateToHours() = (this.inWholeSeconds / 3600).hours

}