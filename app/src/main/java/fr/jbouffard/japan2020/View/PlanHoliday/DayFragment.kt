package fr.jbouffard.japan2020.View.PlanHoliday

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.jbouffard.japan2020.Infrastructure.Command.FlightRequestCommand

import fr.jbouffard.japan2020.R

class DayFragment : Fragment() {

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_day, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        private const val ARG_DAY = "command"

        fun newInstance(): DayFragment {
            val args = Bundle().apply {
                //putParcelable(ARG_DAY, command)
            }
            return DayFragment().apply { arguments = args }
        }
    }
}// Required empty public constructor
