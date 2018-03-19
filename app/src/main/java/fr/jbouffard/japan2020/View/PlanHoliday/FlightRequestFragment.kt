package fr.jbouffard.japan2020.View.PlanHoliday

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import fr.jbouffard.japan2020.R

import org.jetbrains.anko.sdk25.coroutines.onClick
import org.joda.time.DateTime

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FlightRequestFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FlightRequestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FlightRequestFragment
    : Fragment(),
        DatetimeSelectInterface {

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_flight_request, container, false)
        val goingBtn = view.findViewById<ImageButton>(R.id.flight_going_btn)
        goingBtn.onClick {
            val dateFragment = DatePickerDialogFragment.newInstance(DateTime.now())
            dateFragment.fragmentListener = this@FlightRequestFragment
            dateFragment.show(fragmentManager, "date")
        }

        return view
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

    override fun onDatetimeSelected(dateTime: DateTime) {
        dateTime
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction()
    }

    companion object {
        fun newInstance(): FlightRequestFragment = FlightRequestFragment()
    }
}// Required empty public constructor
