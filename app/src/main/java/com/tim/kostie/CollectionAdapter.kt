package com.tim.kostie
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tim.kostie.Kelembapan
import com.tim.kostie.Suhu

class CollectionAdapter(fm: FragmentActivity) :
    FragmentStateAdapter(fm) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Suhu()
            1 -> Kelembapan()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}