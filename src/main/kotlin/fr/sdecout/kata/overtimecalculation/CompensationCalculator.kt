package fr.sdecout.kata.overtimecalculation

import java.math.BigDecimal
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours

object CompensationCalculator {

    val MAX_OVERTIME_HOURS_RATE_1 = BigDecimal.TEN
    const val THRESHOLD_OVERTIME_HOURS_RATE_2 = 6

    fun calculateOvertime(hoursOvertimeTotal: BigDecimal, assignment: Assignment, briefing: Briefing): Overtime {
        var hoursOvertimeRate1 = BigDecimal.ZERO
        var hoursOvertimeRate2 = BigDecimal.ZERO
        val isWatCodeUnion = briefing.watCode && assignment.isUnionized
        val isWatCodeNonUnionForeign = briefing.watCode && !assignment.isUnionized && briefing.foreign
        if (!briefing.watCode && !briefing.z3 && !assignment.isUnionized || briefing.hbmo && assignment.isUnionized || isWatCodeNonUnionForeign || isWatCodeUnion || briefing.foreign && !assignment.isUnionized) {
            hoursOvertimeRate1 = hoursOvertimeTotal
        } else {
            if (hoursOvertimeTotal.compareTo(BigDecimal.ZERO) < 1) {
                return Overtime(hoursOvertimeRate1, hoursOvertimeRate2)
            } else if (hoursOvertimeTotal.compareTo(MAX_OVERTIME_HOURS_RATE_1) < 1) {
                hoursOvertimeRate1 = hoursOvertimeTotal
            } else {
                hoursOvertimeRate1 = MAX_OVERTIME_HOURS_RATE_1
                hoursOvertimeRate2 = hoursOvertimeTotal.subtract(MAX_OVERTIME_HOURS_RATE_1)
                if (assignment.isUnionized) {
                    val threshold = calculateThreshold(assignment, THRESHOLD_OVERTIME_HOURS_RATE_2.toLong())
                    hoursOvertimeRate2 = hoursOvertimeRate2.min(threshold)
                }
            }
        }
        return Overtime(hoursOvertimeRate1, hoursOvertimeRate2)
    }

    private fun calculateThreshold(assignment: Assignment, threshold: Long): BigDecimal {
        val remainder: Duration = assignment.duration - threshold.hours
        return if (remainder.isNegative()) {
            BigDecimal.valueOf(assignment.duration.inWholeSeconds / 3600)
        } else {
            BigDecimal.valueOf(threshold)
        }
    }

}