package fr.sdecout.kata.overtimecalculation

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import kotlin.time.Duration.Companion.hours

class OvertimeTest : ShouldSpec({

    should("fail to initialize from negative time in rate 1") {
        shouldThrow<IllegalArgumentException> {
            Overtime((-1).hours, 1.hours)
        }.message shouldBe "Time in rate 1 must not be negative (-1h)"
    }

    should("fail to initialize from negative time in rate 2") {
        shouldThrow<IllegalArgumentException> {
            Overtime(1.hours, (-1).hours)
        }.message shouldBe "Time in rate 2 must not be negative (-1h)"
    }

})