package com.example.pmdm_eva3.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.pmdm_eva3.R
import com.example.pmdm_eva3.databinding.MainFragmentBinding
import com.example.pmdm_eva3.ui.adapter.LigaAdapter
import com.example.pmdm_eva3.ui.model.League
import com.google.gson.Gson
import org.json.JSONObject

// Fragment principal que obtiene las ligas de la API y las muestra en un RecyclerView
class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val ligas = mutableListOf<League>()
    private lateinit var adapter: LigaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = LigaAdapter(ligas) { liga ->
            // Al pulsar una liga navegamos a LigaFragment pasando su ID como argumento
            val bundle = Bundle().apply { putString("leagueId", liga.idLeague) }
            findNavController().navigate(R.id.action_mainFragment_to_ligaFragment, bundle)
        }
        binding.ligaList.adapter = adapter
        binding.ligaList.layoutManager = LinearLayoutManager(requireContext())
        realizarPeticionJSON()
    }

    private fun realizarPeticionJSON() {
        val url = "https://www.thesportsdb.com/api/v1/json/123/all_leagues.php"
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
        val ligasArray = param.getJSONArray("leagues")
        for (i in 0 until ligasArray.length()) {
            val ligaJSON = ligasArray.getJSONObject(i)
            val league = gson.fromJson(ligaJSON.toString(), League::class.java)
            ligas.add(league)
        }
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
