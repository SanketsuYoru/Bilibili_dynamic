package com.example.monent_kotlin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.monent_kotlin.databinding.FragmentNewsBinding
import com.example.monent_kotlin.datautil.DataViewModel
import com.example.monent_kotlin.datautil.RecAdapter
import com.example.monent_kotlin.datautil.loadDataUtil
import com.example.monent_kotlin.datautil.volleySingleton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_news.*
class NewsFragment : Fragment() {
    private lateinit var binding:FragmentNewsBinding
    private lateinit var viewModel: DataViewModel


    override fun onDestroy() {
        super.onDestroy()
        Log.e("news_fragment","onDestroy")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel= ViewModelProvider(requireActivity(),ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(DataViewModel::class.java)
        binding= FragmentNewsBinding.inflate(inflater, container, false)
        binding.data= viewModel
        binding.setLifecycleOwner { lifecycle }
        Log.e("news_fragment","onCreateView")
        // Inflate the layout for this fragment
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //viewModel
        requireActivity().toolBar_mainActivity.title=getString(R.string.news_title)
        Log.e("news_fragment","onViewCreated")
        val recAdapter = RecAdapter()
        recyclyerView_news.apply {
            adapter = recAdapter
            layoutManager = GridLayoutManager(requireContext(),1)
        }

        viewModel.content.observe(requireActivity(), Observer {
            recAdapter.submitList(it)
            if(swipe_news!=null)
            swipe_news.isRefreshing=false

            if(progressBar_news!=null)
            progressBar_news.visibility=ProgressBar.INVISIBLE
        })

        // get data
        viewModel.content.value?:loadDataUtil.fatchData(viewModel)
        //refresh
        swipe_news.setOnRefreshListener {
            loadDataUtil.fatchData(viewModel)
        }
    }
}