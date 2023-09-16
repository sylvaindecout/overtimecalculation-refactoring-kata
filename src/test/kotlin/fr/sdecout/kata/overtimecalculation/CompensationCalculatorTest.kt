package fr.sdecout.kata.overtimecalculation

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.assume
import kotlin.time.Duration.Companion.hours

class CompensationCalculatorTest : ShouldSpec({

    should("take all hours with rate 1 if briefing allows exceeding overtime limit in rate 1") {
        val assignment = Assignment(isUnionized = false, duration = 1.hours)
        val briefing = Briefing(watCode = false, z3 = false, foreign = false, hbmo = false)
        assume(briefing.allowsExceedingOvertimeLimitInRate1(assignment.isUnionized))

        val overtime = CompensationCalculator.calculateOvertime(12.hours, assignment, briefing)

        overtime shouldBe Overtime(timeInRate1 = 12.hours)
    }

    should("take all hours with rate 1 if overtime duration is < 10h") {
        val assignment = Assignment(isUnionized = false, duration = 1.hours)
        val briefing = Briefing(watCode = false, z3 = true, foreign = false, hbmo = false)
        assume(!briefing.allowsExceedingOvertimeLimitInRate1(assignment.isUnionized))

        val overtime = CompensationCalculator.calculateOvertime(2.hours, assignment, briefing)

        overtime shouldBe Overtime(timeInRate1 = 2.hours)
    }

    should("split hours between rate 1 and rate 2 if overtime duration is > 10h and assignment is not unionized") {
        val overtimeTotalDuration = 12.hours
        val assignment = Assignment(isUnionized = false, duration = 1.hours)
        val briefing = Briefing(watCode = false, z3 = true, foreign = false, hbmo = false)
        assume(!briefing.allowsExceedingOvertimeLimitInRate1(assignment.isUnionized))

        val overtime = CompensationCalculator.calculateOvertime(overtimeTotalDuration, assignment, briefing)

        overtime shouldBe Overtime(timeInRate1 = 10.hours, timeInRate2 = 2.hours)
    }

    should("split hours between rate 1 and rate 2 if overtime duration is > 10h and unionized assignment duration is > 6h") {
        val overtimeTotalDuration = 12.hours
        val assignment = Assignment(isUnionized = true, duration = 8.hours)
        val briefing = Briefing(watCode = false, z3 = true, foreign = false, hbmo = false)
        assume(!briefing.allowsExceedingOvertimeLimitInRate1(assignment.isUnionized))

        val overtime = CompensationCalculator.calculateOvertime(overtimeTotalDuration, assignment, briefing)

        overtime shouldBe Overtime(timeInRate1 = 10.hours, timeInRate2 = 2.hours)
    }

    should("split hours between rate 1 and rate 2 if overtime duration is > 10h and unionized assignment duration is < 6h") {
        val overtimeTotalDuration = 12.hours
        val assignment = Assignment(isUnionized = true, duration = 3.hours)
        val briefing = Briefing(watCode = false, z3 = true, foreign = false, hbmo = false)
        assume(!briefing.allowsExceedingOvertimeLimitInRate1(assignment.isUnionized))

        val overtime = CompensationCalculator.calculateOvertime(overtimeTotalDuration, assignment, briefing)

        overtime shouldBe Overtime(timeInRate1 = 10.hours, timeInRate2 = 2.hours)
    }

    should("cap hours at rate 2 to assignment duration if overtime duration is > 10h and unionized assignment duration is < 6h") {
        val overtimeTotalDuration = 12.hours
        val assignment = Assignment(isUnionized = true, duration = 1.hours)
        val briefing = Briefing(watCode = false, z3 = true, foreign = false, hbmo = false)
        assume(!briefing.allowsExceedingOvertimeLimitInRate1(assignment.isUnionized))

        val overtime = CompensationCalculator.calculateOvertime(overtimeTotalDuration, assignment, briefing)

        overtime shouldBe Overtime(timeInRate1 = 10.hours, timeInRate2 = 1.hours)
    }

})