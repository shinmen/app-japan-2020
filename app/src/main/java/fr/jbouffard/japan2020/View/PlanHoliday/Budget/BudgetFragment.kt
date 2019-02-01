package fr.jbouffard.japan2020.View.PlanHoliday.Budget

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.jbouffard.japan2020.Domain.Travel.Entity.Holiday
import fr.jbouffard.japan2020.Presenter.BudgetPresenter
import fr.jbouffard.japan2020.R
import fr.jbouffard.japan2020.View.PlanHoliday.DetailDayRecyclerViewAdapter
import fr.jbouffard.japan2020.View.PlanHoliday.ViewType
import kotlinx.android.synthetic.main.recycler_visit_list.*
import kotlinx.android.synthetic.main.view_loader.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class BudgetFragment: Fragment() {

    private lateinit var mHoliday: Holiday
    private var mListener: OnBudgetListener? = null
    private val mPresenter: BudgetPresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            mHoliday = it.getParcelable(BudgetFragment.ARG_HOLIDAY)!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.recycler_budget_list, container, false)
        if (parentFragment is OnBudgetListener) {
            mListener = parentFragment as OnBudgetListener
        }
        GlobalScope.launch(Dispatchers.Main) {
            loading.visibility = View.VISIBLE
            try {
                val budgets = mapBudget()
                list.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    setHasFixedSize(true)
                    isNestedScrollingEnabled = true
                    val delegates = mPresenter.getDelegateAdapters()

                    adapter = DetailDayRecyclerViewAdapter(budgets, delegates)
                }
            } catch (e: Exception) {
                mListener?.onError(e.message.toString())
            }
            loading.visibility = View.GONE
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mListener = null
    }

    private suspend fun mapBudget(): List<ViewType> {
        val budgetLines = mPresenter.getOnGoingBudget(mHoliday.streamId)

        return mPresenter.mapBudget(budgetLines)
    }

    interface OnBudgetListener {
        fun onError(error: String)
        fun onLoad()
        fun onLoaded()
    }

    companion object {
        const val TAG = "budgetFragment"
        const val ARG_HOLIDAY = "budget_holiday"

        @JvmStatic
        fun newInstance(holiday: Holiday) =
                BudgetFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(ARG_HOLIDAY, holiday)
                    }
                }
    }
}