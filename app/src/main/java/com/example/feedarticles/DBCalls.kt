package com.example.feedarticles

import com.example.feedarticles.dtos.RegisterDto
import com.example.feedarticles.dtos.RegisterResponseDto
import com.example.feedarticles.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun registerUser(user : RegisterDto, sendStatusMessageCallback : (String, Int) -> Unit){
    val call : Call<RegisterResponseDto>? = ApiService.getApi().registerUser(user.login, user.mdp)
    call?.enqueue(object : Callback<RegisterResponseDto>{
        override fun onResponse(
            call: Call<RegisterResponseDto>,
            response: Response<RegisterResponseDto>
        ) {
            response.body()?.let{
                when(it.status){
                    5 -> "Le nom d'utilisateur est déjà utilisé"
                    0 -> "Le compte n'a pas été crée"
                    -1 -> "Le format des champs n'est pas bon"
                    else -> "Votre compte a bien été créé"
                }.let { message ->
                    sendStatusMessageCallback(message, it.status)
                }

            }
        }

        override fun onFailure(call: Call<RegisterResponseDto>, t: Throwable ) {
            sendStatusMessageCallback("Il y a un problème de connexion avec la base de données", -2)
        }

    })
}