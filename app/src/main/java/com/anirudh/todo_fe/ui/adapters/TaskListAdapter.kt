package com.anirudh.todo_fe.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anirudh.todo_fe.databinding.TaskBoxBinding
import com.anirudh.todo_fe.dataclass.TaskList

class TaskListAdapter(val tasklist:TaskList):RecyclerView.Adapter<TaskListAdapter.viewholder>() {
    class viewholder (val binding:TaskBoxBinding,listener: onItemClickListener):RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }


    private lateinit var mListener:onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener:onItemClickListener){
        mListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        return viewholder(TaskBoxBinding.inflate(LayoutInflater.from(parent.context),parent,false),mListener)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.binding.title.text = tasklist[position].title
        holder.binding.content.text = tasklist[position].content
        if(tasklist[position].is_completed){
            holder.binding.status.text = "Status:Completed"
        }
        else{
            holder.binding.status.text = "Status:Pending"
        }
    }
    override fun getItemCount() = tasklist.size

}