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
        var hoursOvertimeRate1 = Duration.ZERO
        var hoursOvertimeRate2 = Duration.ZERO
        if (canExceedMaxOvertimeHoursRate1(briefing, assignment)) {
            hoursOvertimeRate1 = overtimeTotalDuration
        } else if (overtimeTotalDuration <= Duration.ZERO) {
            return Overtime(hoursOvertimeRate1, hoursOvertimeRate2)
        } else if (overtimeTotalDuration <= MAX_OVERTIME_HOURS_RATE_1) {
            hoursOvertimeRate1 = overtimeTotalDuration
        } else {
            hoursOvertimeRate1 = MAX_OVERTIME_HOURS_RATE_1
            hoursOvertimeRate2 = overtimeTotalDuration - MAX_OVERTIME_HOURS_RATE_1
            if (assignment.isUnionized) {
                val threshold = calculateThreshold(assignment, THRESHOLD_OVERTIME_HOURS_RATE_2)
                hoursOvertimeRate2 = minOf(hoursOvertimeRate2, threshold)
            }
        }
        return Overtime(hoursOvertimeRate1, hoursOvertimeRate2)
    }

    private fun canExceedMaxOvertimeHoursRate1(briefing: Briefing, assignment: Assignment) =
        (!briefing.watCode && !briefing.z3 && !assignment.isUnionized)
                || (briefing.hbmo && assignment.isUnionized)
                || (briefing.watCode && !assignment.isUnionized && briefing.foreign)
                || (briefing.watCode && assignment.isUnionized)
                || (briefing.foreign && !assignment.isUnionized)

    private fun calculateThreshold(assignment: Assignment, threshold: Duration): Duration {
        val remainder: Duration = assignment.duration - threshold
        return if (remainder.isNegative()) {
            (assignment.duration.inWholeSeconds / 3600).hours
        } else {
            threshold
        }
    }

}