package fr.sdecout.kata.overtimecalculation

import kotlin.time.Duration

data class Overtime(
    val timeInRate1: Duration,
    val timeInRate2: Duration = Duration.ZERO,
) {
    init {
        require(!timeInRate1.isNegative()) { "Time in rate 1 must not be negative ($timeInRate1)" }
        require(!timeInRate2.isNegative()) { "Time in rate 2 must not be negative ($timeInRate2)" }
    }
}