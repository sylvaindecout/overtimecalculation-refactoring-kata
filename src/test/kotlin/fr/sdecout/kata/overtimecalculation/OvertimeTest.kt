package fr.sdecout.kata.overtimecalculation

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import kotlin.time.Duration.Companion.hours

class OvertimeTest : ShouldSpec({

    should("fail to initialize from negative hours rate 1") {
        shouldThrow<IllegalArgumentException> {
            Overtime((-1).hours, 1.hours)
        }.message shouldBe "Hours rate 1 must not be negative (-1h)"
    }

    should("fail to initialize from negative hours rate 2") {
        shouldThrow<IllegalArgumentException> {
            Overtime(1.hours, (-1).hours)
        }.message shouldBe "Hours rate 2 must not be negative (-1h)"
    }

})