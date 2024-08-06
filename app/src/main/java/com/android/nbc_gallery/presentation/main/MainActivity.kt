package com.android.nbc_gallery.presentation.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.android.nbc_gallery.R
import com.android.nbc_gallery.data.repository.UiRepositoryGalleryImpl
import com.android.nbc_gallery.databinding.ActivityMainBinding
import com.android.nbc_gallery.presentation.GalleryViewModelFactory
import com.android.nbc_gallery.presentation.GalleryViewmodel
import com.android.nbc_gallery.presentation.search.SearchFragment
import com.android.nbc_gallery.presentation.storage.StorageFragment

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

        val searchFragment = SearchFragment.newInstance()
        val storageFragment = StorageFragment.newInstance("", "")

        setFragment(searchFragment)

        binding.btnMainSearch.setOnClickListener {
            setFragment(searchFragment)
        }
        binding.btnMainStorage.setOnClickListener {
            setFragment(storageFragment)
        }

    }

    fun setFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fl_main, fragment)
            setReorderingAllowed(true)
//            addToBackStack("")
        }
    }

}