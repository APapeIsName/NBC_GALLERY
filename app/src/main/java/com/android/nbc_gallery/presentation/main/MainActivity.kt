package com.android.nbc_gallery.presentation.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.android.nbc_gallery.R
import com.android.nbc_gallery.databinding.ActivityMainBinding
import com.android.nbc_gallery.presentation.main.viewmodel.MainViewModelFactory
import com.android.nbc_gallery.presentation.main.viewmodel.MainViewmodel
import com.android.nbc_gallery.presentation.search.SearchFragment

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MainViewmodel> {
        MainViewModelFactory()
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

        val a = SearchFragment().newInstance("", "")

        supportFragmentManager.commit {
            replace(R.id.fl_main, a)
        }

    }
}