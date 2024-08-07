package com.android.nbc_gallery.presentation.storage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.android.nbc_gallery.data.repository.UiRepositoryGalleryImpl
import com.android.nbc_gallery.databinding.FragmentStorageBinding
import com.android.nbc_gallery.presentation.common.GalleryRecyclerViewAdapter
import com.android.nbc_gallery.presentation.main.viewmodel.GalleryViewModelFactory
import com.android.nbc_gallery.presentation.main.viewmodel.GalleryViewmodel
import com.bumptech.glide.Glide

class StorageFragment : Fragment() {
    private val binding by lazy { FragmentStorageBinding.inflate(layoutInflater) }
    private val storageAdapter = GalleryRecyclerViewAdapter(1)
    private val viewModel by activityViewModels<GalleryViewmodel> {
        GalleryViewModelFactory(UiRepositoryGalleryImpl())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
        storageAdapter.itemClick = GalleryRecyclerViewAdapter.ItemClick { id ->
            val index = viewModel.findCorrectId(id)
            if(index != null) viewModel.switchFavorite(index)
        }
        storageAdapter.drawImage = GalleryRecyclerViewAdapter.DrawImage { url ->
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
        fun newInstance() =
            StorageFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            rvStorage.adapter = storageAdapter
            rvStorage.layoutManager = GridLayoutManager(requireContext(), 2)
            storageAdapter.submitList(viewModel.getFavoriteElements())
        }
        viewModel.liveData.observe(viewLifecycleOwner){
            Log.d("스토리지 뷰모델 체크", "${viewModel.liveData.value?.size}, ${viewModel.liveData.value.toString()}")
            storageAdapter.submitList(viewModel.getFavoriteElements())
        }
    }

}