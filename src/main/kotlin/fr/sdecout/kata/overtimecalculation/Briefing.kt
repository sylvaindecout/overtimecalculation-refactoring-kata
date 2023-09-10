package fr.sdecout.kata.overtimecalculation

data class Briefing(
    val watCode: Boolean,
    val z3: Boolean,
    val foreign: Boolean,
    val hbmo: Boolean,
) {

    fun allowsExceedingOvertimeHoursRate1(unionizedAssignment: Boolean) =
        if (unionizedAssignment) hbmo || watCode else (!watCode && !z3) || foreign

}
