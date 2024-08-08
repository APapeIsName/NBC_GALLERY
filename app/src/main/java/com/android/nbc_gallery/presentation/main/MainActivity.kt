package com.android.nbc_gallery.presentation.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.android.nbc_gallery.R
import com.android.nbc_gallery.data.database.StorageData
import com.android.nbc_gallery.data.entity.DataModel
import com.android.nbc_gallery.data.repository.UiRepositoryGalleryImpl
import com.android.nbc_gallery.databinding.ActivityMainBinding
import com.android.nbc_gallery.presentation.main.viewmodel.GalleryViewModelFactory
import com.android.nbc_gallery.presentation.main.viewmodel.GalleryViewmodel
import com.android.nbc_gallery.presentation.mapper.toGalleryModel
import com.android.nbc_gallery.presentation.search.SearchFragment
import com.android.nbc_gallery.presentation.storage.StorageFragment
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val uiRepositoryGalleryImpl = UiRepositoryGalleryImpl()
    private val viewModel by viewModels<GalleryViewmodel> {
        GalleryViewModelFactory(uiRepositoryGalleryImpl)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        viewModel.updateList(StorageData.loadDataInLocal(this).filterIsInstance<DataModel.GalleryEntity>().toGalleryModel())

        val searchFragment = SearchFragment.newInstance()
        val storageFragment = StorageFragment.newInstance()

        val fragmentList = listOf(searchFragment, storageFragment)

        val viewPagerAdapter = MainViewPagerAdapter(this)
        viewPagerAdapter.fragments = fragmentList

        binding.vpMain.adapter = viewPagerAdapter

        val tabTitles = listOf(resources.getString(R.string.search_text), resources.getString(R.string.storage_text))
        val tabIcons = listOf(
            AppCompatResources.getDrawable(this, R.drawable.baseline_search_24),
            AppCompatResources.getDrawable(this, R.drawable.baseline_folder_24)
        )

        TabLayoutMediator(binding.tlMain, binding.vpMain) { tab, pos ->
            tab.text = tabTitles[pos]
            tab.icon = tabIcons[pos]
        }.attach()

//        setFragment(searchFragment)
//
//        binding.btnMainSearch.setOnClickListener {
//            setFragment(searchFragment)
//        }
//        binding.btnMainStorage.setOnClickListener {
//            setFragment(storageFragment)
//        }
    }

    fun setFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.vp_main, fragment)
            setReorderingAllowed(true)
//            addToBackStack("")
        }
    }

}