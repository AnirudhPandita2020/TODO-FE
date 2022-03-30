package com.anirudh.todo_fe.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anirudh.todo_fe.databinding.FriendlistBinding
import com.anirudh.todo_fe.databinding.TaskBoxBinding
import com.anirudh.todo_fe.dataclass.FriendList

class FriendAdapter(val list:FriendList):RecyclerView.Adapter<FriendAdapter.viewholder>(){
    class viewholder(val binding:FriendlistBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendAdapter.viewholder {
        return viewholder(FriendlistBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: FriendAdapter.viewholder, position: Int) {
        holder.binding.emailData.text = list[position].email
        holder.binding.usernameData.text = list[position].username

    }

    override fun getItemCount() = list.size
}