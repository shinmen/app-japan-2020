package fr.jbouffard.japan2020.View.PlanHoliday

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import fr.jbouffard.japan2020.R
import android.support.v4.app.FragmentStatePagerAdapter
import me.relex.circleindicator.CircleIndicator
import android.support.v4.view.ViewPager

class PlanningActivity
    : AppCompatActivity(), DayFragment.OnFragmentInteractionListener
{
    override fun onFragmentInteraction(uri: Uri) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planning)

        val viewpager = findViewById(R.id.pager) as ViewPager
        val indicator = findViewById(R.id.indicator) as CircleIndicator
        viewpager.adapter = ScreenSlidePagerAdapter(supportFragmentManager)
        indicator.setViewPager(viewpager)
    }

    companion object {
        fun newIntent(packageContext: Context): Intent {
            return Intent(packageContext, PlanningActivity::class.java)
        }
    }

    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return DayFragment.newInstance()
        }

        override fun getCount(): Int {
            return 5
        }
    }

}
