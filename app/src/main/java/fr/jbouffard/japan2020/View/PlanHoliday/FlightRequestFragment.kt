package fr.jbouffard.japan2020.View.PlanHoliday

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.transition.TransitionManager
import android.support.v4.app.Fragment
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.TextView
import fr.jbouffard.japan2020.Domain.Travel.Command.FlightRequestCommand
import fr.jbouffard.japan2020.Infrastructure.DTO.CityCodeMapper
import fr.jbouffard.japan2020.Presenter.FlightRequestPresenter
import fr.jbouffard.japan2020.R
import kotlinx.android.synthetic.main.fragment_flight_request.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textView
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
        goingDateInput = view.findViewById(R.id.flight_going_input)
        returnDateInput = view.findViewById(R.id.flight_return_input)

        mPresenter = FlightRequestPresenter()

        toggleGoingReturnInput(view)
        showGoingReturnCalendar(view)


        val requestBtn = view.findViewById<Button>(R.id.flight_request_btn)
        requestBtn.onClick {
            launch(UI) {
                if (isValid()) {
                    val originId = radio_group_origin.checkedRadioButtonId
                    val origin = view.findViewById<RadioButton>(originId).text

                    val destinationId = radio_group_destination.checkedRadioButtonId
                    val destination = view.findViewById<RadioButton>(destinationId).text
                    val cityMapper = CityCodeMapper()

                    val command = FlightRequestCommand(
                            cityMapper.getCodeByFrenchCity(origin.toString()), mGoingAt!!,
                            cityMapper.getCodeByJapanCity(destination.toString()), mReturnAt!!
                    )
                    val offers = mPresenter.requestFlightPrice(command)
                }
            }
        }

        return view
    }

    private fun isValid(): Boolean
    {
        if (mGoingAt == null) {
            goingDateInput.error = "date d'aller nécessaire"
            return false
        }

        if (mReturnAt == null) {
            returnDateInput.error = "date de retour nécessaire"
            return false
        }

        if (mGoingAt!!.isAfter(mReturnAt)) {
            goingDateInput.error = "date d'aller supérieure à celle de retour"
            returnDateInput.error = "date d'aller supérieure à celle de retour"
            return false
        }

        return true
    }

    private fun showGoingReturnCalendar(view: View)
    {
        val goingBtn = view.findViewById<ImageButton>(R.id.flight_going_hint)
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
    }

    private fun toggleGoingReturnInput(view: View)
    {
        val returnText = view.findViewById<TextView>(R.id.return_text_line)

        returnText.onClick {
            TransitionManager.beginDelayedTransition(transition_container)
            return_container.visibility = View.VISIBLE
            going_container.visibility = View.GONE
        }

        val goingText = view.findViewById<TextView>(R.id.going_text_line)

        goingText.onClick {
            TransitionManager.beginDelayedTransition(transition_container)
            return_container.visibility = View.GONE
            going_container.visibility = View.VISIBLE
        }
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
                    Editable.Factory.getInstance().newEditable(dateTime.toString("EE d MMM y à H:mm"))
                    mGoingAt = dateTime
            }
            RETURN_DATE -> {
                    returnDateInput.editText?.text =
                    Editable.Factory.getInstance().newEditable(dateTime.toString("EE d MMM y à H:mm"))
                    mReturnAt = dateTime
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
