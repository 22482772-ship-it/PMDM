package com.example.pmdm_eva3.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pmdm_eva3.databinding.ItemEquipoBinding
import com.example.pmdm_eva3.ui.model.Equipo

// Adaptador reutilizado en LigaFragment (añadir favorito) y FavoritosFragment (eliminar favorito)
class EquipoAdapter(
    private val equipos: List<Equipo>,
    private val modoFavoritos: Boolean,
    private val onAccionClick: (Equipo) -> Unit
) : RecyclerView.Adapter<EquipoAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemEquipoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemEquipoBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val equipo = equipos[position]
        holder.binding.equipoName.text = equipo.strTeam
        if (!equipo.strTeamBadge.isNullOrEmpty()) {
            Glide.with(holder.itemView.context).load(equipo.strTeamBadge).into(holder.binding.equipoBadge)
        }
        // Icono estrella llena si ya es favorito, vacía si no lo es
        val icon = if (modoFavoritos) android.R.drawable.btn_star_big_on else android.R.drawable.btn_star_big_off
        holder.binding.favoritoButton.setImageResource(icon)
        holder.binding.favoritoButton.setOnClickListener { onAccionClick(equipo) }
    }

    override fun getItemCount() = equipos.size
}
