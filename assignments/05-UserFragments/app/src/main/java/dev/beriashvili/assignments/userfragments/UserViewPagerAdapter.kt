package dev.beriashvili.assignments.userfragments

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class UserViewPagerAdapter(
    fm: FragmentManager,
    private val users: MutableList<UserModel.Data>,
    private val origin: Context
) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        val userFragment = UserFragment(origin)

        userFragment.user = users[position]

        return userFragment
    }

    override fun getCount(): Int = users.size
}