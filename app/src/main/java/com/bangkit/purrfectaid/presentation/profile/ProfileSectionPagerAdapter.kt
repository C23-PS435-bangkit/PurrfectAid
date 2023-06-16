package com.bangkit.purrfectaid.presentation.profile

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

private const val PAGE_SECTION = 3

class ProfileSectionPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return PAGE_SECTION
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? =null
        when(position){
            0-> fragment = DummyFragment()
            1-> fragment =  DummyFragment()
            2 -> fragment = DummyFragment()
        }
        return fragment as Fragment
    }
}