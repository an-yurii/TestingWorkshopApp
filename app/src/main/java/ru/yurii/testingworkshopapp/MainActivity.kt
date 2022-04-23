package ru.yurii.testingworkshopapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.yurii.testingworkshopapp.tasklist.TaskListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            routeToTaskList()
        }
    }

    private fun routeToTaskList() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                TaskListFragment(),
                TaskListFragment::class.simpleName
            )
            .commit()
    }
}
