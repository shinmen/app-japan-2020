package fr.jbouffard.japan2020.View.PlanHoliday

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import fr.jbouffard.japan2020.R

import fr.jbouffard.japan2020.View.PlanHoliday.VisitFragment.OnListFragmentInteractionListener


class VisitRecyclerViewAdapter(private val mValues: List<Int>, private val mListener: OnListFragmentInteractionListener?) : RecyclerView.Adapter<VisitRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_visit, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]

        holder.mView.setOnClickListener {
            mListener?.onListFragmentInteraction()
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: Int? = null

        init {
            //mIdView = mView.findViewById<View>(R.id.id) as TextView
            //mContentView = mView.findViewById<View>(R.id.content) as TextView
        }

        override fun toString(): String {
            return super.toString()
        }
    }
}
