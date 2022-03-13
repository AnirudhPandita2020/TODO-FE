package com.anirudh.todo_fe.ui.fragment


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences


import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.anirudh.todo_fe.MainViewModel.ViewModel
import com.anirudh.todo_fe.MainViewModel.ViewModelFactory
import com.anirudh.todo_fe.R

import com.anirudh.todo_fe.Repo.Repo
import com.anirudh.todo_fe.Repo.Response
import com.anirudh.todo_fe.actvity.EditActivity
import com.anirudh.todo_fe.api.RetrofitInstance
import com.anirudh.todo_fe.api.TODOBE
import com.anirudh.todo_fe.databinding.FragmentHomeBinding
import com.anirudh.todo_fe.dataclass.TaskList
import com.anirudh.todo_fe.dataclass.TaskListItem
import com.anirudh.todo_fe.network.NetworkHelper

import com.anirudh.todo_fe.ui.adapters.TaskListAdapter
import com.google.android.material.progressindicator.BaseProgressIndicator


class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private lateinit var datastore:SharedPreferences
    lateinit var token:String
    lateinit var mainviewmodel: ViewModel
    lateinit var taskadapter:TaskListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        datastore = requireActivity().getSharedPreferences("Token",Context.MODE_PRIVATE)
        token = datastore.getString("access_token","").toString()
        val retservice = RetrofitInstance.getInstance().create(TODOBE::class.java)
        val repo = Repo(retservice)
        mainviewmodel = ViewModelProvider(this, ViewModelFactory(repo)).get(ViewModel::class.java)
        if(NetworkHelper.isNetworkConnected(requireContext())){
            setUpTaskList(token)
        }
        else{
            binding.nointernet.visibility = View.VISIBLE
            binding.textView3.text = "Connect to the internet and tap on the home button twice"
            binding.notaskload.visibility = View.GONE
        }
        return binding.root
    }
    private fun setUpTaskList(token:String){
        mainviewmodel.getTask("Bearer $token")
        mainviewmodel.res_task.observe(requireActivity(), Observer {
            when(it){
                is Response.Success ->{
                    val list = it.data
                    if(list!!.size != 0){
                        binding.timeout.visibility = View.GONE
                        binding.notaskload.visibility = View.GONE
                        binding.textView3.visibility = View.GONE
                        binding.tasklist.visibility = View.VISIBLE
                        binding.tasklist.layoutManager = GridLayoutManager(requireContext(),2)
                        taskadapter = TaskListAdapter(list!!)
                        binding.tasklist.adapter = taskadapter
                        taskadapter.setOnItemClickListener(object:TaskListAdapter.onItemClickListener{
                            override fun onItemClick(position: Int) {
                                val data = list[position]
                                val intent = setIntent(data)
                                startActivity(intent)
                            }
                        })
                    }
                }
                is Response.Error ->{
                    if(it.error.equals("timeout")){
                        binding.nointernet.visibility = View.GONE
                        binding.timeout.visibility = View.VISIBLE
                        binding.notaskload.visibility = View.GONE
                        binding.textView3.text = "Oops Time out.Tap the home button again"
                    }else
                    {
                        binding.notaskload.visibility = View.GONE
                        binding.nointernet.visibility = View.GONE
                        binding.session.visibility = View.VISIBLE
                        binding.textView3.text= "Session Expired.Please login Again"
                    }
                }
                else -> {}
            }
        })
    }

    private fun setIntent(data:TaskListItem): Intent {
        val intent = Intent(context,EditActivity::class.java)
        intent.apply {
            putExtra("id",data.id)
            putExtra("Title",data.title)
            putExtra("content",data.content)
            putExtra("is_completed",data.is_completed)
        }
        return intent
    }

    override fun onResume() {
        super.onResume()
        if (NetworkHelper.isNetworkConnected(requireContext())) {
            mainviewmodel.getTask("Bearer $token")
        }
    }

}