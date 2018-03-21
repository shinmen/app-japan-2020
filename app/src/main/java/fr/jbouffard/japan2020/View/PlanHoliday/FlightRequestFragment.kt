package fr.jbouffard.japan2020.View.PlanHoliday

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import fr.jbouffard.japan2020.Domain.Travel.Command.FlightRequestCommand
import fr.jbouffard.japan2020.Presenter.FlightRequestPresenter
import fr.jbouffard.japan2020.R
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.joda.time.DateTime

class FlightRequestFragment
    : Fragment(),
        DatetimeSelectInterface {

    private var mListener: OnFragmentInteractionListener? = null
    private lateinit var goingDateInput: TextInputLayout
    private lateinit var returnDateInput: TextInputLayout

    private lateinit var mPresenter: FlightRequestPresenter

    private var mGoingAt: DateTime? = null
    private var mReturnAt: DateTime? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_flight_request, container, false)
        val goingBtn = view.findViewById<ImageButton>(R.id.flight_going_hint)
        goingDateInput = view.findViewById(R.id.flight_going_input)
        returnDateInput = view.findViewById(R.id.flight_return_input)

        mPresenter = FlightRequestPresenter()

        goingBtn.onClick {
            val dateFragment = DatePickerDialogFragment.newInstance(DateTime.now(), GOING_DATE)
            dateFragment.fragmentListener = this@FlightRequestFragment
            dateFragment.show(fragmentManager, "date")
        }

        val returnBtn = view.findViewById<ImageButton>(R.id.flight_return_hint)
        returnBtn.onClick{
            val dateFragment = DatePickerDialogFragment.newInstance(DateTime.now(), RETURN_DATE)
            dateFragment.fragmentListener = this@FlightRequestFragment
            dateFragment.show(fragmentManager, "date")
        }

        val requestBtn = view.findViewById<Button>(R.id.flight_request_btn)
        requestBtn.onClick {
            launch(UI) {
                checkNotNull(mGoingAt) { "date de départ nécessaire" }
                checkNotNull(mReturnAt) { "date de retour nécessaire" }

                val command = FlightRequestCommand(
                        "PAR", mGoingAt!!,
                        "OSA", mReturnAt!!
                )
                val offers = mPresenter.requestFlightPrice(command)
            }
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

    override fun onDatetimeSelected(dateTime: DateTime, view: Int) {
        when(view) {
            GOING_DATE -> {
                    goingDateInput.editText?.text =
                    Editable.Factory.getInstance().newEditable(dateTime.toString("EE d MMM y à H:m"))
            }
            RETURN_DATE -> {
                    returnDateInput.editText?.text =
                    Editable.Factory.getInstance().newEditable(dateTime.toString("EE d MMM y à H:m"))
            }
        }
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction()
    }

    companion object {
        const val GOING_DATE: Int = 1
        const val RETURN_DATE: Int = 2
        fun newInstance(): FlightRequestFragment = FlightRequestFragment()
    }
}// Required empty public constructor
