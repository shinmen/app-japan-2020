package fr.jbouffard.japan2020.View.PlanHoliday.DetailPlan

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.jbouffard.japan2020.Domain.EventStore
import fr.jbouffard.japan2020.Domain.Travel.Entity.Holiday
import fr.jbouffard.japan2020.presenter.DetailPlanPresenter
import fr.jbouffard.japan2020.R
import fr.jbouffard.japan2020.View.PlanHoliday.DetailDayRecyclerViewAdapter
import fr.jbouffard.japan2020.presenter.UpdateHolidayStatPresenter
import kotlinx.android.synthetic.main.fragment_holiday_detail.*
import kotlinx.android.synthetic.main.recycler_holiday_detail_list.*
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import java.lang.Exception

class PlannedDayFragment : Fragment() {

    private lateinit var mHoliday: Holiday

    private var mListener: OnHolidaySaveListener? = null
    private val mDetailPlanPresenter: DetailPlanPresenter by inject()
    private val mHolidayPresenter: UpdateHolidayStatPresenter by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            mHoliday = it.getParcelable(ARG_HOLIDAY)!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_holiday_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*GlobalScope.launch(Dispatchers.Main) {
            try {
                save_holiday_container.visibility = View.VISIBLE
                mHolidayPresenter.updateHolidayStats(mHoliday)

            } catch (e: Exception) {
                mListener?.retry { mHolidayPresenter.updateHolidayStats(mHoliday) }
            } finally {
                save_holiday_container.visibility = View.GONE
            }
        }*/

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val detailDays = mDetailPlanPresenter.getDetailDays(mHoliday)
                with(list) {
                    layoutManager = LinearLayoutManager(context)
                    adapter = DetailDayRecyclerViewAdapter(detailDays, mDetailPlanPresenter.getAdapters())
                }
                save_holiday_container.visibility = View.VISIBLE
                mHolidayPresenter.updateHolidayStats(mHoliday)

            } catch (e: Exception) {
                mListener?.retry { mHolidayPresenter.updateHolidayStats(mHoliday) }
            } finally {
                save_holiday_container.visibility = View.GONE
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnHolidaySaveListener) {
            mListener = context
        } else {
            throw RuntimeException("$context must implement OnHolidaySaveListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnHolidaySaveListener {
        fun retry(tryAgain: suspend () -> Unit)
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
