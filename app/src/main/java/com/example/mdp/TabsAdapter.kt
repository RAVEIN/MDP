package com.example.mdp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabsAdapter(
    activity: FragmentActivity
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RegistrationFragment()
            1 -> GameFragment()
            2 -> RulesFragment()
            3 -> AuthorsFragment()
            4 -> SettingsFragment()

            else -> RegistrationFragment()
        }
    }
}