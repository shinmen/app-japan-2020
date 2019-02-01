package fr.jbouffard.japan2020.View.PlanHoliday.Overnight

import android.os.Bundle
import android.support.transition.TransitionManager
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.jbouffard.japan2020.Domain.DomainException
import fr.jbouffard.japan2020.Domain.Travel.Entity.Holiday
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.City
import fr.jbouffard.japan2020.Infrastructure.DTO.OvernightOffer
import fr.jbouffard.japan2020.presenter.OvernightRequestPresenter
import fr.jbouffard.japan2020.R
import kotlinx.android.synthetic.main.recycler_overnight_list.*
import kotlinx.android.synthetic.main.view_loader.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class OvernightFragment : Fragment(),
        OvernightDetailDialogFragment.OnOvernightPlaceChoice
{
    private var mListener: OnOvernightListener? = null
    private val mPresenter: OvernightRequestPresenter by inject()
    private lateinit var mHoliday: Holiday
    private lateinit var mCity: City

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mHoliday = it.getParcelable(ARG_OVERNIGHT)!!
            mCity = it.getParcelable(ARG_CITY)!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.recycler_overnight_list, container, false)
        if (parentFragment is OnOvernightListener) {
            mListener = parentFragment as OnOvernightListener
        }
        GlobalScope.launch(Dispatchers.Main) {
            try {
                TransitionManager.beginDelayedTransition(container!!)
                mListener?.onLoad()
                loading.visibility = View.VISIBLE
                val offers = mPresenter.requestOvernightsOffers(mHoliday.currentDate!!, mCity)
                list.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = OvernightRecyclerViewAdapter(offers) { overnight ->
                        val dialog = OvernightDetailDialogFragment.newInstance(overnight)
                        dialog.fragmentListener = this@OvernightFragment
                        dialog.show(fragmentManager, OvernightDetailDialogFragment.ARG_OVERNIGHT_DETAIL)
                    }
                }
                loading.visibility = View.GONE
                mListener?.onLoaded()
            } catch (e: Exception) {
                mListener?.onError(e.message.toString())
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mListener = null
    }

    override fun onOvernightPlaceChosen(overnight: OvernightOffer) {
        try {
            GlobalScope.launch(Dispatchers.Main) {
                val sleep = async {
                    mPresenter.sleepIn(mHoliday, overnight)
                }
                mListener?.onSleptIn(overnight)
                sleep.await()
                mListener?.onDayEnded()
            }
        } catch (e: DomainException) {
            mListener?.onError(e.message.toString())
        }
    }

    interface OnOvernightListener {
        fun onError(error: String)
        fun onLoad()
        fun onLoaded()
        fun onSleptIn(overnight: OvernightOffer)
        fun onDayEnded()
    }

    companion object {
        const val TAG = "overnightFragment"
        const val ARG_OVERNIGHT = "holiday_overnight"
        const val ARG_CITY = "overnight_city"

        fun newInstance(holiday: Holiday, city: City): OvernightFragment {
            val args = Bundle().apply {
                putParcelable(ARG_OVERNIGHT, holiday)
                putParcelable(ARG_CITY, city)
            }
            return OvernightFragment().apply { arguments = args }
        }
    }
}
