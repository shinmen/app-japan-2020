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
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError
import fr.jbouffard.japan2020.Presenter.OvernightRequestPresenter

import fr.jbouffard.japan2020.R
import kotlinx.android.synthetic.main.fragment_overnight.view.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject

class OvernightFragment
    : Fragment(), Step
{
    private var mListener: OnListFragmentInteractionListener? = null
    private val mPresenter: OvernightRequestPresenter by inject()

    override fun onSelected() {
    }

    override fun verifyStep(): VerificationError? {
        return null
    }

    override fun onError(error: VerificationError) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_overnight_list, container, false)
        val list = view.findViewById<RecyclerView>(R.id.list)

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

        private val ARG_OVERNIGHT = "overnight"

        fun newInstance(): OvernightFragment {
            val args = Bundle().apply {
            }
            return OvernightFragment().apply { arguments = args }
        }
    }
}
