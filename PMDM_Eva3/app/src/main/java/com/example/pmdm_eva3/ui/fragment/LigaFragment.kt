package com.example.pmdm_eva3.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.pmdm_eva3.databinding.LigaFragmentBinding
import com.example.pmdm_eva3.ui.adapter.EquipoAdapter
import com.example.pmdm_eva3.ui.model.Equipo
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

// Fragment que muestra los equipos de la liga seleccionada y permite añadirlos a favoritos
class LigaFragment : Fragment() {

    private var _binding: LigaFragmentBinding? = null
    private val binding get() = _binding!!
    private val equipos = mutableListOf<Equipo>()
    private lateinit var adapter: EquipoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LigaFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = EquipoAdapter(equipos, false) { equipo ->
            guardarFavorito(equipo)
        }
        binding.equipoList.adapter = adapter
        binding.equipoList.layoutManager = LinearLayoutManager(requireContext())
        val leagueId = arguments?.getString("leagueId") ?: ""
        realizarPeticionJSON(leagueId)
    }

    private fun realizarPeticionJSON(leagueId: String) {
        val url = "https://www.thesportsdb.com/api/v1/json/123/search_all_teams.php?id=$leagueId"
        val request = JsonObjectRequest(url, {
            Log.v("conexion", "Conexion correcta")
            procesarRespuesta(it)
        }, {
            Log.v("conexion", "Error en la conexion con el servidor")
        })
        Volley.newRequestQueue(requireContext()).add(request)
    }

    private fun procesarRespuesta(param: JSONObject) {
        val gson = Gson()
        val teamsArray = param.optJSONArray("teams") ?: return
        for (i in 0 until teamsArray.length()) {
            val equipoJSON = teamsArray.getJSONObject(i)
            val equipo = gson.fromJson(equipoJSON.toString(), Equipo::class.java)
            equipos.add(equipo)
        }
        adapter.notifyDataSetChanged()
    }

    private fun guardarFavorito(equipo: Equipo) {
        val prefs = requireContext().getSharedPreferences("favoritos", Context.MODE_PRIVATE)
        val array = JSONArray(prefs.getString("equipos", "[]"))

        // Comprobamos que el equipo no esté ya en favoritos
        for (i in 0 until array.length()) {
            if (array.getJSONObject(i).optString("idTeam") == equipo.idTeam) {
                Snackbar.make(binding.root, "${equipo.strTeam} ya está en favoritos", Snackbar.LENGTH_SHORT).show()
                return
            }
        }

        val obj = JSONObject().apply {
            put("idTeam", equipo.idTeam)
            put("strTeam", equipo.strTeam)
            put("strTeamBadge", equipo.strTeamBadge)
            put("strLeague", equipo.strLeague)
            put("idLeague", equipo.idLeague)
        }
        array.put(obj)
        prefs.edit().putString("equipos", array.toString()).apply()
        Snackbar.make(binding.root, "${equipo.strTeam} añadido a favoritos", Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
