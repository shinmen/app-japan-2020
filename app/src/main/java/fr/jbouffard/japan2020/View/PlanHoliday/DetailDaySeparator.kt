package fr.jbouffard.japan2020.View.PlanHoliday

class DetailDaySeparator: ViewType {
    override fun getDate(): Long {
        return 0
    }

    override fun getViewType(): Int = VIEW_TYPE

    companion object {
        const val VIEW_TYPE = 1
    }
}