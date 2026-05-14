package com.example.nimmaguru.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nimmaguru.data.model.GuruWithProfile
import com.example.nimmaguru.databinding.ItemGuruBinding

class GuruAdapter(private val onGuruClick: (GuruWithProfile) -> Unit) :
    ListAdapter<GuruWithProfile, GuruAdapter.GuruViewHolder>(GuruDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuruViewHolder {
        val binding = ItemGuruBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GuruViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GuruViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class GuruViewHolder(private val binding: ItemGuruBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GuruWithProfile) {
            val guru = item.user
            val profile = item.profile
            
            binding.tvGuruName.text = guru.fullName
            binding.tvGuruVillage.text = "Village: ${guru.village}"
            
            binding.tvGuruSkills.text = if (!profile?.skills.isNullOrEmpty()) {
                "Skills: ${profile?.skills}"
            } else {
                "Expertise: General Mentor"
            }
            
            binding.root.setOnClickListener { onGuruClick(item) }
            binding.btnConnect.setOnClickListener { onGuruClick(item) }
        }

    }

    class GuruDiffCallback : DiffUtil.ItemCallback<GuruWithProfile>() {
        override fun areItemsTheSame(oldItem: GuruWithProfile, newItem: GuruWithProfile): Boolean {
            return oldItem.user.id == newItem.user.id
        }

        override fun areContentsTheSame(oldItem: GuruWithProfile, newItem: GuruWithProfile): Boolean {
            return oldItem == newItem
        }
    }
}
