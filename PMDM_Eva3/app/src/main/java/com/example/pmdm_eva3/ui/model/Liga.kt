package com.example.pmdm_eva3.ui.model

// Los campos son nullable porque no todas las ligas tienen todos los datos rellenos en la API
data class League(
    val idLeague: String?,
    val strLeague: String?,
    val strSport: String?,
    val strLeagueBadge: String?
)
