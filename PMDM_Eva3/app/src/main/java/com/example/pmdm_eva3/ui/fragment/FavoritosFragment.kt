package com.example.pmdm_eva3.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pmdm_eva3.databinding.FavoritosFragmentBinding
import com.example.pmdm_eva3.ui.adapter.EquipoAdapter
import com.example.pmdm_eva3.ui.model.Equipo
import com.google.android.material.snackbar.Snackbar
import org.json.JSONArray

// Fragment que muestra los equipos guardados como favoritos, accesible desde el menú del toolbar
class FavoritosFragment : Fragment() {

    private var _binding: FavoritosFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FavoritosFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.favoritosList.layoutManager = LinearLayoutManager(requireContext())
        cargarFavoritos()
    }

    // Se recarga la lista en onResume por si el usuario añadió favoritos en LigaFragment
    override fun onResume() {
        super.onResume()
        cargarFavoritos()
    }

    private fun cargarFavoritos() {
        val prefs = requireContext().getSharedPreferences("favoritos", Context.MODE_PRIVATE)
        val array = JSONArray(prefs.getString("equipos", "[]"))
        val lista = mutableListOf<Equipo>()

        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            lista.add(Equipo(
                idTeam = obj.optString("idTeam"),
                strTeam = obj.optString("strTeam"),
                strTeamBadge = obj.optString("strTeamBadge"),
                strLeague = obj.optString("strLeague"),
                idLeague = obj.optString("idLeague")
            ))
        }

        binding.favoritosList.adapter = EquipoAdapter(lista, true) { equipo ->
            eliminarFavorito(equipo)
        }
    }

    private fun eliminarFavorito(equipo: Equipo) {
        val prefs = requireContext().getSharedPreferences("favoritos", Context.MODE_PRIVATE)
        val array = JSONArray(prefs.getString("equipos", "[]"))
        val nueva = JSONArray()

        for (i in 0 until array.length()) {
            if (array.getJSONObject(i).optString("idTeam") != equipo.idTeam) {
                nueva.put(array.getJSONObject(i))
            }
        }

        prefs.edit().putString("equipos", nueva.toString()).apply()
        cargarFavoritos()
        Snackbar.make(binding.root, "${equipo.strTeam} eliminado de favoritos", Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
