package com.simple.app.ocbc_project_task

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.simple.app.ocbc_project_task.features.login.ui.LoginFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val backStateName: String = "MainActivity"
//        val manager: FragmentManager = supportFragmentManager
//        val fragmentPopped: Boolean = manager.popBackStackImmediate(backStateName, 0)
//        if (!fragmentPopped) {
//            val ft: FragmentTransaction = manager.beginTransaction()
//            ft.replace(R.id.container, LoginFragment())
//            ft.addToBackStack(backStateName)
//            ft.commit()
//        }
    }
}