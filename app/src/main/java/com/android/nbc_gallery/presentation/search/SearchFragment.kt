package com.android.nbc_gallery.presentation.search

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.android.nbc_gallery.data.database.APIDataStorage
import com.android.nbc_gallery.data.repository.UiRepositoryGalleryImpl
import com.android.nbc_gallery.databinding.FragmentSearchBinding
import com.android.nbc_gallery.presentation.common.GalleryRecyclerViewAdapter
import com.android.nbc_gallery.presentation.main.viewmodel.GalleryViewModelFactory
import com.android.nbc_gallery.presentation.main.viewmodel.GalleryViewmodel
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class SearchFragment : Fragment() {
    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
//    private var keyword: String? = null
    private val searchAdapter = GalleryRecyclerViewAdapter(0)
    private val viewModel by activityViewModels<GalleryViewmodel> {
        GalleryViewModelFactory(UiRepositoryGalleryImpl())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
        searchAdapter.itemClick = GalleryRecyclerViewAdapter.ItemClick { id ->
            val index = viewModel.findCorrectId(id)
            if(index != null) viewModel.switchFavorite(index)
        }
        searchAdapter.drawImage = GalleryRecyclerViewAdapter.DrawImage { url ->
            Glide.with(this).load(url)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(): SearchFragment {
            return SearchFragment().apply {
                arguments = Bundle().apply {}
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            etSearch.setText(requireContext().getSharedPreferences("keyword", MODE_PRIVATE).getString("word", ""))
            btnSearch.setOnClickListener {
                getDataFromAPI(binding.etSearch.text.toString())
                val inputMethodManager = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                val savedKeyword = requireContext().getSharedPreferences("keyword", MODE_PRIVATE)
                val keywordEditor = savedKeyword.edit()
                keywordEditor.putString("word", binding.etSearch.text.toString())
                keywordEditor.apply()
            }
            rvSearch.adapter = searchAdapter
            rvSearch.layoutManager = GridLayoutManager(requireContext(), 2)
            searchAdapter.submitList(listOf())
        }
        viewModel.liveData.observe(viewLifecycleOwner){
            Log.d("뷰모델 체크", "${viewModel.liveData.value?.size}, ${viewModel.liveData.value.toString()}")
            searchAdapter.submitList(viewModel.liveData.value)
        }
    }

    fun getDataFromAPI(
        query: String,
        page: Int = 1,
    ) = runBlocking {
        viewLifecycleOwner.lifecycleScope.launch {
            APIDataStorage.getDataFromApi(query, page = page)
            viewModel.updateData()
        }
    }

}