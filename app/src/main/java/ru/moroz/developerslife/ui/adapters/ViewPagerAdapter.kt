package ru.moroz.developerslife.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.moroz.developerslife.data.post.PostsCategory
import ru.moroz.developerslife.ui.fragments.HotPostsFragment
import ru.moroz.developerslife.ui.fragments.LatestPostsFragment
import ru.moroz.developerslife.ui.fragments.TopPostsFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = PostsCategory.values().size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LatestPostsFragment()
            1 -> TopPostsFragment()
            2 -> HotPostsFragment()
            else -> throw IllegalStateException("Unknown position")
        }
    }
}