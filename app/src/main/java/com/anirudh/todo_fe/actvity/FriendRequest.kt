package com.anirudh.todo_fe.actvity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.anirudh.todo_fe.MainViewModel.ViewModel
import com.anirudh.todo_fe.MainViewModel.ViewModelFactory
import com.anirudh.todo_fe.R
import com.anirudh.todo_fe.Repo.Repo
import com.anirudh.todo_fe.Repo.Response
import com.anirudh.todo_fe.api.RetrofitInstance
import com.anirudh.todo_fe.api.TODOBE
import com.anirudh.todo_fe.databinding.ActivityFriendRequestBinding
import com.anirudh.todo_fe.dataclass.FriendList
import com.anirudh.todo_fe.ui.adapters.FriendAdapter

class FriendRequest : AppCompatActivity() {
    private lateinit var binding:ActivityFriendRequestBinding
    private lateinit var mainviewmodel:ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFriendRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val retservice = RetrofitInstance.getInstance().create(TODOBE::class.java)
        val repo = Repo(retservice)
        mainviewmodel = ViewModelProvider(this, ViewModelFactory(repo)).get(ViewModel::class.java)
        binding.searchBarFr.addTextChangedListener {
            if (it.isNullOrBlank()){
                val list = FriendList()
                list.clear()
                binding.suggestion.adapter = FriendAdapter(list)
            }
            else{
                mainviewmodel.get_friend_list(it.toString())
            }
        }
        mainviewmodel.friendlist.observe(this, Observer {
            when(it){
                is Response.Success ->{
                    val list = it.data!!
                    if(list.isEmpty()){

                        binding.suggestion.adapter = FriendAdapter(list)

                    }
                    else {
                        binding.suggestion.layoutManager = LinearLayoutManager(this)
                        binding.suggestion.adapter = FriendAdapter(list)
                    }
                }
                is Response.Error -> {
                    Toast.makeText(this,it.error,Toast.LENGTH_SHORT).show()
                }
                is Response.Loading ->{}
            }
        })

    }
}