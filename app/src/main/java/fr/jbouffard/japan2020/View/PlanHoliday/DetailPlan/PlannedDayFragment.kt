package fr.jbouffard.japan2020.View.PlanHoliday.DetailPlan

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.jbouffard.japan2020.Domain.Travel.Entity.Holiday
import fr.jbouffard.japan2020.Infrastructure.Adapter.DetailDayAdapter
import fr.jbouffard.japan2020.Infrastructure.Adapter.FlightAdapter
import fr.jbouffard.japan2020.Presenter.DetailPlanPresenter
import fr.jbouffard.japan2020.R
import fr.jbouffard.japan2020.View.PlanHoliday.DetailDayRecyclerViewAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

class PlannedDayFragment : Fragment() {

    private lateinit var holiday: Holiday

    private var listener: OnListFragmentInteractionListener? = null
    private val mPresenter: DetailPlanPresenter by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            holiday = it.getParcelable(ARG_HOLIDAY)!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.recycler_planneddaytype_list, container, false)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the adapter
        if (view is RecyclerView) {
            // Retrieve a Presenter instance
            GlobalScope.launch(Dispatchers.Main) {
                val detailDays = mPresenter.getDetailDays(holiday)
                with(view) {
                    layoutManager = LinearLayoutManager(context)
                    adapter = DetailDayRecyclerViewAdapter(detailDays, mPresenter.getAdapters())
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            //listener = context
        } else {
            //throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction()
    }

    companion object {
        const val TAG = "planned_day_type_fragment"
        const val ARG_HOLIDAY = "planned_day_type_holiday_arg"

        @JvmStatic
        fun newInstance(holiday: Holiday) =
                PlannedDayFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(ARG_HOLIDAY, holiday)
                    }
                }
    }
}
