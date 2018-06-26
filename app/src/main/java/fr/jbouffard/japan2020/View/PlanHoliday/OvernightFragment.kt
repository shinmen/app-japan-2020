package fr.jbouffard.japan2020.View.PlanHoliday

import android.content.Context
import android.os.Bundle
import android.support.transition.TransitionManager
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stepstone.stepper.BlockingStep
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import fr.jbouffard.japan2020.Domain.Travel.Entity.Holiday
import fr.jbouffard.japan2020.Presenter.OvernightRequestPresenter

import fr.jbouffard.japan2020.R
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject

class OvernightFragment
    : Fragment(), BlockingStep
{
    private var mListener: OnListFragmentInteractionListener? = null
    private val mPresenter: OvernightRequestPresenter by inject()
    private lateinit var mHoliday: Holiday

    override fun onSelected() {
    }

    override fun verifyStep(): VerificationError? {
        return null
    }

    override fun onError(error: VerificationError) {
    }

    override fun onBackClicked(callback: StepperLayout.OnBackClickedCallback?) {
        callback?.goToPrevStep()
    }

    override fun onNextClicked(callback: StepperLayout.OnNextClickedCallback?) {
        launch(UI) {
            mPresenter.finishDay(mHoliday)
            callback?.goToNextStep()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mHoliday = it.getParcelable(OVERNIGHT_ARG)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_overnight_list, container, false)
        val list = view.findViewById<RecyclerView>(R.id.list_visits)

        launch(UI) {
            try {
                TransitionManager.beginDelayedTransition(container!!)
                val offers = mPresenter.requestOvernightsOffers()
                list.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = OvernightRecyclerViewAdapter(offers) { overnight ->
                        val dialog = OvernightDetailDialogFragment.newInstance(overnight)
                        dialog.show(fragmentManager, OvernightDetailDialogFragment.ARG_OVERNIGHT_DETAIL)
                    }
                }
            } catch (e: Exception) {
                toast(e.message.toString())
            }
        }

        return view
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction()
    }

    companion object {
        private val OVERNIGHT_ARG = "holiday_for_overnight"

        fun newInstance(holiday: Holiday): OvernightFragment {
            val args = Bundle().apply {
                putParcelable(OVERNIGHT_ARG, holiday)
            }
            return OvernightFragment().apply { arguments = args }
        }
    }
}
