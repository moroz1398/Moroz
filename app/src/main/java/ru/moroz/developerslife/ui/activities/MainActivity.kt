package ru.moroz.developerslife.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import ru.moroz.developerslife.R
import ru.moroz.developerslife.data.post.PostsCategory
import ru.moroz.developerslife.databinding.ActivityMainBinding
import ru.moroz.developerslife.ui.adapters.ViewPagerAdapter

/**
 * Host activity
 */
class MainActivity : AppCompatActivity() {
    private lateinit var tabLayoutMediator: TabLayoutMediator
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        initTabView()
    }

    private fun initAdapter() {
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.adapter = viewPagerAdapter
    }

    private fun initTabView() {
        tabLayoutMediator = TabLayoutMediator(
            binding.tabView,
            binding.viewPager
        ) { tab, position ->
            tab.text = PostsCategory.values()[position].categoryName
        }
        tabLayoutMediator.attach()
    }
}