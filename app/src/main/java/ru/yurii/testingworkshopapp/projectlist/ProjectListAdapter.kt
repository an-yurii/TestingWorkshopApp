package ru.yurii.testingworkshopapp.projectlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.yurii.testingworkshopapp.data.Project
import ru.yurii.testingworkshopapp.databinding.ChooseProjectDialogItemBinding

class ProjectListAdapter(
    private val onItemClick: (project: Project) -> Unit
) : ListAdapter<Project, ProjectListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ChooseProjectDialogItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClick)
    }

    inner class ViewHolder(private val binding: ChooseProjectDialogItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val contentView: TextView = binding.title

        fun bind(project: Project, onItemClick: (project: Project) -> Unit) {
            binding.itemNumber.text = "${project.id}"
            binding.title.text = project.title
            binding.projectItem.setOnClickListener {
                onItemClick(project)
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Project>() {
        override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem == newItem
        }
    }
}
