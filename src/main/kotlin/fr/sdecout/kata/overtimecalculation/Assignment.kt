package fr.sdecout.kata.overtimecalculation

import kotlin.time.Duration

data class Assignment(
    val isUnionized: Boolean,
    val duration: Duration,
)