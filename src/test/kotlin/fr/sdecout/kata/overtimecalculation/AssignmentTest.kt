package fr.sdecout.kata.overtimecalculation

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import kotlin.time.Duration.Companion.hours

class AssignmentTest : ShouldSpec({

    should("fail to initialize from negative duration") {
        shouldThrow<IllegalArgumentException> {
            Assignment(false, (-1).hours)
        }.message shouldBe "Duration must not be negative (-1h)"
    }

})