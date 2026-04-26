package com.example.pmdm_eva3.ui.model

// Se usa para mostrar equipos en LigaFragment y para guardar favoritos en SharedPreferences
data class Equipo(
    val idTeam: String?,
    val strTeam: String?,
    val strTeamBadge: String?,
    val strLeague: String?,
    val idLeague: String?
)
