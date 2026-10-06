package com.example.mdp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val tabLayout =
            findViewById<TabLayout>(R.id.tabLayout)

        val viewPager =
            findViewById<ViewPager2>(R.id.viewPager)

        viewPager.adapter =
            TabsAdapter(this)

        //viewPager.offscreenPageLimit = 4

        val tabNames = arrayOf(
            "Регистрация",
            "Игра",
            "Правила",
            "Авторы",
            "Настройки"
        )

        TabLayoutMediator(
            tabLayout,
            viewPager
        ) { tab, position ->

            tab.text = tabNames[position]

        }.attach()
    }
}






//package com.example.mdp
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import androidx.appcompat.app.AppCompatActivity
//import com.google.android.material.tabs.TabLayout
//
//class MainActivity : AppCompatActivity() {
//
//    override fun onCreate(
//        savedInstanceState: Bundle?
//    ) {
//        super.onCreate(savedInstanceState)
//
//        setContentView(
//            R.layout.activity_main
//        )
//
//
//        val tabLayout =
//            findViewById<TabLayout>(
//                R.id.tabLayout
//            )
//
//
//        tabLayout.addTab(
//            tabLayout.newTab()
//                .setText("Регистрация")
//        )
//
//        tabLayout.addTab(
//            tabLayout.newTab()
//                .setText("Правила")
//        )
//
//        tabLayout.addTab(
//            tabLayout.newTab()
//                .setText("Авторы")
//        )
//
//        tabLayout.addTab(
//            tabLayout.newTab()
//                .setText("Настройки")
//        )
//
//
//        tabLayout.addOnTabSelectedListener(
//            object : TabLayout.OnTabSelectedListener {
//
//                override fun onTabSelected(
//                    tab: TabLayout.Tab
//                ) {
//
//                    when (tab.position) {
//
//                        0 -> openFragment(
//                            "registration"
//                        ) {
//                            RegistrationFragment()
//                        }
//
//
//                        1 -> openFragment(
//                            "rules"
//                        ) {
//                            RulesFragment()
//                        }
//
//
//                        2 -> openFragment(
//                            "authors"
//                        ) {
//                            AuthorsFragment()
//                        }
//
//
//                        3 -> openFragment(
//                            "settings"
//                        ) {
//                            SettingsFragment()
//                        }
//                    }
//                }
//
//
//                override fun onTabUnselected(
//                    tab: TabLayout.Tab
//                ) {
//                }
//
//
//                override fun onTabReselected(
//                    tab: TabLayout.Tab
//                ) {
//                }
//            }
//        )
//
//
//        if (savedInstanceState == null) {
//
//            openFragment(
//                "registration"
//            ) {
//                RegistrationFragment()
//            }
//        }
//    }
//
//
//    private fun openFragment(
//        tag: String,
//        createFragment: () -> Fragment
//    ) {
//
//        val manager =
//            supportFragmentManager
//
//        val transaction =
//            manager.beginTransaction()
//
//
//        manager.fragments.forEach {
//            transaction.hide(it)
//        }
//
//
//        val existing =
//            manager.findFragmentByTag(tag)
//
//
//        if (existing == null) {
//
//            transaction.add(
//                R.id.fragmentContainer,
//                createFragment(),
//                tag
//            )
//
//        } else {
//
//            transaction.show(
//                existing
//            )
//        }
//
//
//        transaction.commit()
//    }
//}