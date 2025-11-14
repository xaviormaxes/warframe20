package com.example.warframeapp20.ui.resources

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.warframeapp20.R
import com.example.warframeapp20.data.CraftingProject

class CraftingProjectsAdapter(
    private val onProjectClick: (CraftingProject) -> Unit,
    private val onToggleProject: (CraftingProject, Boolean) -> Unit = { _, _ -> }
) : RecyclerView.Adapter<CraftingProjectsAdapter.ProjectViewHolder>() {

    private var projects = listOf<CraftingProject>()

    fun updateProjects(newProjects: List<CraftingProject>) {
        projects = newProjects
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_resource, parent, false)
        return ProjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.bind(projects[position])
    }

    override fun getItemCount(): Int = projects.size

    inner class ProjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameText: TextView = itemView.findViewById(R.id.resourceNameText)
        private val statusText: TextView = itemView.findViewById(R.id.resourceStatusText)
        private val amountText: TextView = itemView.findViewById(R.id.resourceAmountText)

        fun bind(project: CraftingProject) {
            nameText.text = project.name
            statusText.text = if (project.isComplete) "Complete" else "In Progress"
            amountText.text = "${(project.totalProgress * 100).toInt()}%"
            
            statusText.setTextColor(
                if (project.isComplete) 
                    android.graphics.Color.GREEN 
                else 
                    android.graphics.Color.YELLOW
            )

            itemView.setOnClickListener {
                onProjectClick(project)
            }
        }
    }
}