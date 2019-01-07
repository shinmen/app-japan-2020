package fr.jbouffard.japan2020.Infrastructure.Utils

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.jbouffard.japan2020.Infrastructure.LocalPersistence.Dao.BudgetDao
import fr.jbouffard.japan2020.Infrastructure.LocalPersistence.Entity.Budget
import kotlinx.coroutines.async

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}
