package com.android.nbc_gallery.presentation.storage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.android.nbc_gallery.data.database.StorageData
import com.android.nbc_gallery.data.entity.DataModel
import com.android.nbc_gallery.data.repository.UiRepositoryGalleryImpl
import com.android.nbc_gallery.data.repository.UiRepositoryStorageImpl
import com.android.nbc_gallery.databinding.FragmentStorageBinding
import com.android.nbc_gallery.presentation.common.GalleryRecyclerViewAdapter
import com.android.nbc_gallery.presentation.main.MainActivity
import com.android.nbc_gallery.presentation.main.viewmodel.GalleryViewModelFactory
import com.android.nbc_gallery.presentation.main.viewmodel.GalleryViewmodel
import com.android.nbc_gallery.presentation.mapper.toGalleryModel
import com.android.nbc_gallery.presentation.storage.viewmodel.StorageViewModelFactory
import com.android.nbc_gallery.presentation.storage.viewmodel.StorageViewmodel
import com.bumptech.glide.Glide
import kotlin.properties.Delegates

class StorageFragment : Fragment() {
    private val binding by lazy { FragmentStorageBinding.inflate(layoutInflater) }
    private val storageAdapter = GalleryRecyclerViewAdapter()
    val viewModel by activityViewModels<StorageViewmodel> {
        StorageViewModelFactory(UiRepositoryStorageImpl())
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
        viewModel.updateData()
//        viewModel.updateList(StorageData.loadDataInLocal(requireContext()).filterIsInstance<DataModel.GalleryEntity>().toGalleryModel())
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
            //${viewModel.liveData.value?.size}, ${viewModel.liveData.value.toString()}
            Log.d("스토리지 뷰모델 체크", "${viewModel.getFavoriteElements()} , ")
            storageAdapter.submitList(viewModel.getFavoriteElements())
        }
    }

}