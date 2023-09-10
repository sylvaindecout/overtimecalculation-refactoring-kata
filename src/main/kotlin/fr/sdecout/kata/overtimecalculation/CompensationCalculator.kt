package fr.sdecout.kata.overtimecalculation

import java.math.BigDecimal
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours

object CompensationCalculator {

    val MAX_OVERTIME_HOURS_RATE_1 = 10.hours
    val THRESHOLD_OVERTIME_HOURS_RATE_2 = 6.hours

    fun calculateOvertime(hoursOvertimeTotal: BigDecimal, assignment: Assignment, briefing: Briefing): Overtime {
        val overtimeTotalDuration = hoursOvertimeTotal.toLong().hours
        require(!overtimeTotalDuration.isNegative()) { "Overtime total duration must not be negative ($overtimeTotalDuration)" }
        val hoursOvertimeRate1 = resolveOvertimeHoursRate1(overtimeTotalDuration, assignment.isUnionized, briefing)
        val hoursOvertimeRate2 = resolveOvertimeHoursRate2(assignment, overtimeTotalDuration - hoursOvertimeRate1)
        return Overtime(hoursOvertimeRate1, hoursOvertimeRate2)
    }

    private fun resolveOvertimeHoursRate1(overtimeTotalDuration: Duration, unionizedAssignment: Boolean, briefing: Briefing) =
        if (briefing.allowsExceedingOvertimeHoursRate1(unionizedAssignment)) overtimeTotalDuration
        else minOf(overtimeTotalDuration, MAX_OVERTIME_HOURS_RATE_1)

    private fun resolveOvertimeHoursRate2(assignment: Assignment, remainingOvertimeDuration: Duration) =
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