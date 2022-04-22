package ru.yurii.testingworkshopapp.tasklist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.yurii.testingworkshopapp.R
import ru.yurii.testingworkshopapp.data.Task
import ru.yurii.testingworkshopapp.databinding.TaskListItemBinding

class TaskListAdapter : ListAdapter<Task, TaskListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TaskListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: TaskListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            val context = binding.bullet.context
            val originDrawable = AppCompatResources.getDrawable(context, R.drawable.marker)
            val drawable = DrawableCompat.wrap(originDrawable!!)
            DrawableCompat.setTint(drawable, context.getColor(task.colorRes))

            with(binding) {
                bullet.setImageDrawable(drawable)
                bullet.visibility = task.bulletVisibility
                title.text = task.title
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }

}
