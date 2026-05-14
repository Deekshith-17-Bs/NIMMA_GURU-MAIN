package com.example.nimmaguru.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nimmaguru.data.model.Session
import com.example.nimmaguru.databinding.ItemSessionBinding

class SessionAdapter : ListAdapter<Session, SessionAdapter.SessionViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        val binding = ItemSessionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SessionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SessionViewHolder(private val binding: ItemSessionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(session: Session) {
            binding.tvSessionTitle.text = session.title
            binding.tvSessionSubject.text = "Subject: ${session.subject}"
            binding.tvSessionDate.text = session.date
            binding.tvSessionTime.text = "${session.startTime} - ${session.endTime}"
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Session>() {
        override fun areItemsTheSame(oldItem: Session, newItem: Session) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Session, newItem: Session) = oldItem == newItem
    }
}
