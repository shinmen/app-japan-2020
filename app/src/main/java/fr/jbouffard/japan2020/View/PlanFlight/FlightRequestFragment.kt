package fr.jbouffard.japan2020.View.PlanFlight

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RadioButton
import fr.jbouffard.japan2020.Domain.Travel.Entity.AirTransportation
import fr.jbouffard.japan2020.Infrastructure.Command.FlightRequestCommand
import fr.jbouffard.japan2020.Infrastructure.DTO.CityCodeMapper
import fr.jbouffard.japan2020.R
import kotlinx.android.synthetic.main.fragment_flight_request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.joda.time.DateTime
import org.joda.time.Period

class FlightRequestFragment
    : Fragment(), DatetimeSelectInterface {

    private var mListener: OnFlightRequestListener? = null
    private lateinit var goingDateInput: TextInputLayout
    private lateinit var returnDateInput: TextInputLayout

    private var mGoingAt: DateTime? = null
    private var mReturnAt: DateTime? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        goingDateInput = flight_going_input
        returnDateInput = flight_return_input

        showGoingReturnCalendar(view)

        flight_request_btn.onClick {
            GlobalScope.launch(Dispatchers.Main) {
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
                    mListener?.onFlightPlanSelected(command)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_flight_request, container, false)
    }

    private fun isValid(): Boolean
    {
        if (mGoingAt == null) {
            goingDateInput.error = resources.getString(R.string.error_going_date_required)
            return false
        }

        if (mGoingAt!!.isBefore(DateTime())) {
            goingDateInput.error = resources.getString(R.string.back_to_the_future)
            return false
        }

        if (mGoingAt!!.isBefore(DateTime().plus(Period.days(AirTransportation.MIN_DAY_BEFORE_DEPARTURE)))) {
            goingDateInput.error =  resources.getString(R.string.going_date_too_near)
            return false
        }

        if (mReturnAt == null) {
            returnDateInput.error = resources.getString(R.string.error_return_date_required)
            return false
        }

        if (mGoingAt!!.isAfter(mReturnAt)) {
            goingDateInput.error = resources.getString(R.string.error_going_date_lt_return)
            returnDateInput.error = resources.getString(R.string.error_going_date_lt_return)
            return false
        }

        return true
    }

    private fun showGoingReturnCalendar(view: View)
    {
        val goingBtn = view.findViewById<ImageButton>(R.id.flight_going_hint)
        goingBtn.onClick {
            val startGoingDate = if (mReturnAt == null)  DateTime().plus(Period.days(AirTransportation.MIN_DAY_BEFORE_DEPARTURE + 1)) else mReturnAt!!.minus(Period.days(5))
            val dateFragment = DatePickerDialogFragment.newInstance(startGoingDate, GOING_DATE)
            dateFragment.fragmentListener = this@FlightRequestFragment
            dateFragment.show(fragmentManager, "date")
        }

        val returnBtn = view.findViewById<ImageButton>(R.id.flight_return_hint)
        returnBtn.onClick{
            val startReturnDate = if (mGoingAt == null)  DateTime.now() else mGoingAt!!.plus(Period.days(5))
            val dateFragment = DatePickerDialogFragment.newInstance(startReturnDate!!, RETURN_DATE)
            dateFragment.fragmentListener = this@FlightRequestFragment
            dateFragment.show(fragmentManager, "date")
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFlightRequestListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFlightRequestListener")
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

    interface OnFlightRequestListener {
        fun onFlightPlanSelected(command: FlightRequestCommand)
    }

    companion object {
        const val GOING_DATE: Int = 1
        const val RETURN_DATE: Int = 2
        fun newInstance(): FlightRequestFragment = FlightRequestFragment()
    }
}// Required empty public constructor
