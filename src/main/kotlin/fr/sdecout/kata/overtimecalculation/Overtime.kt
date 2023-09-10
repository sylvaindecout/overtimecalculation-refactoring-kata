package fr.sdecout.kata.overtimecalculation

import kotlin.time.Duration

data class Overtime(
    val hoursRate1: Duration,
    val hoursRate2: Duration,
) {
    override fun toString() = "Overtime(hoursRate1=${hoursRate1.inWholeHours}, hoursRate2=${hoursRate2.inWholeHours})"
}