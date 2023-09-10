package fr.sdecout.kata.overtimecalculation

import kotlin.time.Duration

data class Assignment(
    val isUnionized: Boolean,
    val duration: Duration,
) {
    init {
        require(!duration.isNegative()) { "Duration must not be negative ($duration)" }
    }
}