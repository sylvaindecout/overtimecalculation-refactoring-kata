package fr.sdecout.kata.overtimecalculation

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.boolean
import io.kotest.property.checkAll

class BriefingTest : ShouldSpec({

    context("assignment is unionized") {
        should("allow exceeding overtime limit in rate 1 when hbmo flag is true") {
            checkAll(Arb.boolean(), Arb.boolean(), Arb.boolean())
            { watCode, z3, foreign ->
                val briefing = Briefing(watCode = watCode, z3 = z3, foreign = foreign, hbmo = true)
                briefing.allowsExceedingOvertimeLimitInRate1(unionizedAssignment = true) shouldBe true
            }
        }

        should("allow exceeding overtime limit in rate 1 when watCode flag is true") {
            checkAll(Arb.boolean(), Arb.boolean(), Arb.boolean())
            { z3, foreign, hbmo ->
                val briefing = Briefing(watCode = true, z3 = z3, foreign = foreign, hbmo = hbmo)
                briefing.allowsExceedingOvertimeLimitInRate1(unionizedAssignment = true) shouldBe true
            }
        }

        should("not allow exceeding overtime limit in rate 1 when watCode and hbmo flags are false") {
            checkAll(Arb.boolean(), Arb.boolean())
            { z3, foreign ->
                val briefing = Briefing(watCode = false, z3 = z3, foreign = foreign, hbmo = false)
                briefing.allowsExceedingOvertimeLimitInRate1(unionizedAssignment = true) shouldBe false
            }
        }
    }

    context("assignment is not unionized") {
        should("allow exceeding overtime limit in rate 1 when watCode and z3 flags are false") {
            checkAll(Arb.boolean(), Arb.boolean())
            { foreign, hbmo ->
                val briefing = Briefing(watCode = false, z3 = false, foreign = foreign, hbmo = hbmo)
                briefing.allowsExceedingOvertimeLimitInRate1(unionizedAssignment = false) shouldBe true
            }
        }

        should("allow exceeding overtime limit in rate 1 when foreign flag is true") {
            checkAll(Arb.boolean(), Arb.boolean(), Arb.boolean())
            { watCode, z3, hbmo ->
                val briefing = Briefing(watCode = watCode, z3 = z3, foreign = true, hbmo = hbmo)
                briefing.allowsExceedingOvertimeLimitInRate1(unionizedAssignment = false) shouldBe true
            }
        }

        should("not allow exceeding overtime limit in rate 1 when watCode flag is true and foreign flag is false") {
            checkAll(Arb.boolean(), Arb.boolean())
            { z3, hbmo ->
                val briefing = Briefing(watCode = true, z3 = z3, foreign = false, hbmo = hbmo)
                briefing.allowsExceedingOvertimeLimitInRate1(unionizedAssignment = false) shouldBe false
            }
        }

        should("not allow exceeding overtime limit in rate 1 when z3 flag is true and foreign flag is false") {
            checkAll(Arb.boolean(), Arb.boolean())
            { watCode, hbmo ->
                val briefing = Briefing(watCode = watCode, z3 = true, foreign = false, hbmo = hbmo)
                briefing.allowsExceedingOvertimeLimitInRate1(unionizedAssignment = false) shouldBe false
            }
        }
    }

})