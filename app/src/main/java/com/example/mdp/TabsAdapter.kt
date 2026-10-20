package com.example.mdp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabsAdapter(
    activity: FragmentActivity
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 6
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RegistrationFragment()
            1 -> GameFragment()
            2 -> RecordsFragment()
            3 -> RulesFragment()
            4 -> AuthorsFragment()
            5 -> SettingsFragment()

            else -> RegistrationFragment()
        }
    }
}