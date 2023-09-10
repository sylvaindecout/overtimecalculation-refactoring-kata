package fr.sdecout.kata.overtimecalculation

data class Briefing(
    val watCode: Boolean,
    val z3: Boolean,
    val foreign: Boolean,
    val hbmo: Boolean,
) {

    fun allowsExceedingOvertimeHoursRate1(unionizedAssignment: Boolean) = (!watCode && !z3 && !unionizedAssignment)
            || (hbmo && unionizedAssignment)
            || (watCode && !unionizedAssignment && foreign)
            || (watCode && unionizedAssignment)
            || (foreign && !unionizedAssignment)

}
