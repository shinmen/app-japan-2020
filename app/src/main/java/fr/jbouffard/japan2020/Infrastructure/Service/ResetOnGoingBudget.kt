package fr.jbouffard.japan2020.Infrastructure.Service

import android.app.IntentService
import android.content.Context
import android.content.Intent
import fr.jbouffard.japan2020.Infrastructure.LocalPersistence.AppDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get

class ResetOnGoingBudget: IntentService(ResetOnGoingBudget::class.simpleName) {

    override fun onHandleIntent(intent: Intent?) {
        val db : AppDatabase = get()
        GlobalScope.launch {
            db.budgetDao().truncate()
        }
    }

    companion object {
        fun newIntent(packageContext: Context): Intent = Intent(packageContext, ResetOnGoingBudget::class.java)
    }
}