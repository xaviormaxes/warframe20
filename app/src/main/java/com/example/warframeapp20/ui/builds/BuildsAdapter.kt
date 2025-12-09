package com.example.warframeapp20.ui.builds

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.warframeapp20.R
import com.example.warframeapp20.data.Build

class BuildsAdapter(
    private val onBuildClick: (Build) -> Unit,
    private val onEditClick: (Build) -> Unit,
    private val onDeleteClick: (Build) -> Unit,
    private val onShareClick: (Build) -> Unit
) : RecyclerView.Adapter<BuildsAdapter.BuildViewHolder>() {

    private var builds = listOf<Build>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateBuilds(newBuilds: List<Build>) {
        builds = newBuilds
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_build, parent, false)
        return BuildViewHolder(view)
    }

    override fun onBindViewHolder(holder: BuildViewHolder, position: Int) {
        holder.bind(builds[position])
    }

    override fun getItemCount(): Int = builds.size

    inner class BuildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameText: TextView = itemView.findViewById(R.id.buildNameText)
        private val warframeText: TextView = itemView.findViewById(R.id.buildWarframeText)
        private val modsText: TextView = itemView.findViewById(R.id.buildModsText)
        private val tagsText: TextView = itemView.findViewById(R.id.buildTagsText)
        private val editButton: ImageView = itemView.findViewById(R.id.buildEditButton)
        private val shareButton: ImageView = itemView.findViewById(R.id.buildShareButton)

        fun bind(build: Build) {
            nameText.text = build.name
            warframeText.text = "Warframe: ${build.warframeId}"
            modsText.text = "${build.mods.size} mods • ${build.formaCount} forma"
            tagsText.text = build.tags.joinToString(", ")

            itemView.setOnClickListener {
                onBuildClick(build)
            }

            editButton.setOnClickListener {
                onEditClick(build)
            }

            shareButton.setOnClickListener {
                onShareClick(build)
            }

            itemView.setOnLongClickListener {
                onDeleteClick(build)
                true
            }
        }
    }
}