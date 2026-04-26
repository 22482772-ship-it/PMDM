package com.example.pmdm_eva3.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pmdm_eva3.databinding.ItemLigaBinding
import com.example.pmdm_eva3.ui.model.League

// Adaptador para el RecyclerView que muestra la lista de ligas en MainFragment
class LigaAdapter(
    private val ligas: List<League>,
    private val onClick: (League) -> Unit
) : RecyclerView.Adapter<LigaAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemLigaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemLigaBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val liga = ligas[position]
        holder.binding.ligaName.text = liga.strLeague
        holder.binding.ligaSport.text = liga.strSport
        if (!liga.strLeagueBadge.isNullOrEmpty()) {
            Glide.with(holder.itemView.context).load(liga.strLeagueBadge).into(holder.binding.ligaBadge)
        }
        holder.itemView.setOnClickListener { onClick(liga) }
    }

    override fun getItemCount() = ligas.size
}
