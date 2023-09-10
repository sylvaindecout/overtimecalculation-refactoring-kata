package fr.sdecout.kata.overtimecalculation

import kotlin.time.Duration

data class Overtime(
    val hoursRate1: Duration,
    val hoursRate2: Duration,
) {
    init {
        require(!hoursRate1.isNegative()) { "Hours rate 1 must not be negative ($hoursRate1)" }
        require(!hoursRate2.isNegative()) { "Hours rate 2 must not be negative ($hoursRate2)" }
    }
}