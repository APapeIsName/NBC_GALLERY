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
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.nbc_gallery.data.database.APIDataStorage
import com.android.nbc_gallery.data.database.StorageData
import com.android.nbc_gallery.data.repository.UiRepositoryGalleryImpl
import com.android.nbc_gallery.databinding.FragmentSearchBinding
import com.android.nbc_gallery.presentation.common.GalleryRecyclerViewAdapter
import com.android.nbc_gallery.presentation.main.viewmodel.GalleryViewModelFactory
import com.android.nbc_gallery.presentation.main.viewmodel.GalleryViewmodel
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

const val MAX_PAGE = 15

class SearchFragment : Fragment() {
    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
//    private var keyword: String? = null
    private val searchAdapter = GalleryRecyclerViewAdapter()
    private val viewModel by activityViewModels<GalleryViewmodel> {
        GalleryViewModelFactory(UiRepositoryGalleryImpl())
    }
    private var pageNum: Int = 1

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
        searchAdapter.currentListChange = GalleryRecyclerViewAdapter.CurrentListChange { last ->
//            binding.rvSearch.scrollToPosition(last)
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
                pageNum = 1
                viewModel.getDataFromAPI(binding.etSearch.text.toString(), page = if(pageNum <= MAX_PAGE) pageNum++ else pageNum)
                val inputMethodManager = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                val savedKeyword = requireContext().getSharedPreferences("keyword", MODE_PRIVATE)
                val keywordEditor = savedKeyword.edit()
                keywordEditor.putString("word", binding.etSearch.text.toString())
                keywordEditor.apply()
            }
            rvSearch.adapter = searchAdapter
            rvSearch.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
            (rvSearch.layoutManager as StaggeredGridLayoutManager).gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
//            rvSearch.layoutManager = GridLayoutManager(requireContext(), 2)
//            rvSearch.itemAnimator = null

            rvSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val last by lazy {
                        if(viewModel.liveData.value?.size != null) {
                            viewModel.liveData.value?.size!!.minus(1)
                        } else 0
                    }
                    val lastItems = (rvSearch.layoutManager as StaggeredGridLayoutManager).findLastCompletelyVisibleItemPositions(null)
                    val isLast = (lastItems[0] == last) || (lastItems[1] == last)
                    if(
                        isLast
//                        !rvSearch.canScrollVertically(1)
//                        &&
                            ) {
                        viewModel.getDataFromAPI(binding.etSearch.text.toString(), page = if(pageNum <= MAX_PAGE) pageNum++ else pageNum)
                        Log.d("최하단 도착", "최하단 도착")
                    }
                    Log.d("최하단 사이즈", "${(rvSearch.layoutManager as StaggeredGridLayoutManager)
                        .findLastCompletelyVisibleItemPositions(null)[0]} , " +
                            "${(rvSearch.layoutManager as StaggeredGridLayoutManager)
                                .findLastCompletelyVisibleItemPositions(null)[1]} , ${viewModel.liveData.value?.size}")
                }
            })
            searchAdapter.submitList(listOf())
        }
        viewModel.liveData.observe(viewLifecycleOwner){ livedata ->
            Log.d("뷰모델 체크", "${viewModel.liveData.value?.size}, ${viewModel.liveData.value.toString()}")
            searchAdapter.submitList(viewModel.liveData.value)
            StorageData.saveDataInLocal(requireContext(), viewModel.getFavoriteElements())
//            binding.rvSearch.scrollToPosition((viewModel.liveData.value?.size?.minus(1))?: 0)
        }
    }

}