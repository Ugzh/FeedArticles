package com.example.feedarticles.network

import com.example.feedarticles.dtos.GetAllItemsDto
import com.example.feedarticles.dtos.GetItemByIdDto
import com.example.feedarticles.dtos.ItemDto
import com.example.feedarticles.dtos.RegisterAndLoginDto
import com.example.feedarticles.dtos.RegisterAndLoginResponseDto
import com.example.feedarticles.dtos.UserDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun registerUser(user : RegisterAndLoginDto, sendStatusMessageCallback : (String, Int?) -> Unit){
    val call : Call<RegisterAndLoginResponseDto>? = ApiService.getApi().registerUser(user.login, user.password)
    call?.enqueue(object : Callback<RegisterAndLoginResponseDto>{
        override fun onResponse(
            call: Call<RegisterAndLoginResponseDto>,
            response: Response<RegisterAndLoginResponseDto>
        ) {
            response.body()?.let{
                when(it.status){
                    5 -> "Le nom d'utilisateur est déjà utilisé"
                    0 -> "Le compte n'a pas été crée"
                    -1 -> "Le format des champs n'est pas bon"
                    1 -> "Votre compte a bien été créé"
                    else -> return
                }.let { message ->
                    sendStatusMessageCallback(message, it.status)
                }

            }
        }

        override fun onFailure(call: Call<RegisterAndLoginResponseDto>, t: Throwable ) {
            sendStatusMessageCallback("Il y a un problème de connexion avec la base de données", null)
        }

    })
}

fun loginUser(user : RegisterAndLoginDto, sendUserOrMessageCallback : (UserDto?, String?) -> Unit){
    val call : Call<RegisterAndLoginResponseDto>? = ApiService.getApi().loginUser(user.login, user.password)
    call?.enqueue(object : Callback<RegisterAndLoginResponseDto>{
        override fun onResponse(
            call: Call<RegisterAndLoginResponseDto>,
            response: Response<RegisterAndLoginResponseDto>
        ) {
            response.body()?.let{
                when(it.status){
                    1 -> sendUserOrMessageCallback(UserDto(it.idUser, user.login, user.password, it.token!!), null)
                    0 -> sendUserOrMessageCallback(null, "Utilisateur inconnu")
                    else -> sendUserOrMessageCallback(null, "Une erreur est survenue veuillez réessayer ultérieurement")
                }

            }
        }

        override fun onFailure(call: Call<RegisterAndLoginResponseDto>, t: Throwable ) {
            sendUserOrMessageCallback(null, "Il y a un problème de connexion avec la base de données")
        }

    })
}

fun getAllItems(user : UserDto, sendItemsOrMessageCallback: (List<ItemDto>?, String?) -> Unit){
    val call : Call<GetAllItemsDto>? = ApiService.getApi().getAllItems(user.token)
    call?.enqueue(object : Callback<GetAllItemsDto>{
        override fun onResponse(call: Call<GetAllItemsDto>, response: Response<GetAllItemsDto>) {
            response.body()?.let {
                when(it.status){
                    "ok" -> sendItemsOrMessageCallback(it.items, null)
                    "unauthorized" -> sendItemsOrMessageCallback(null, "Non autorisé")
                    "error_param" -> sendItemsOrMessageCallback(null, "Erreur de paramètre")
                    else -> sendItemsOrMessageCallback(null, "Problème avec la base de données")
                }


            }
        }

        override fun onFailure(call: Call<GetAllItemsDto>, t: Throwable) {
            sendItemsOrMessageCallback(null, "Problème avec la base de données")
        }
    })
}

fun getItemById(item: ItemDto, user: UserDto, sendItemOrMessageCallback: (ItemDto?, String?) -> Unit){
    val call : Call<GetItemByIdDto>? = ApiService.getApi().getItemById(item.id, user.token)
    call?.enqueue(object: Callback<GetItemByIdDto>{
        override fun onResponse(call: Call<GetItemByIdDto>, response: Response<GetItemByIdDto>) {
            response.body()?.let {
                when(it.status){
                    "ok" -> sendItemOrMessageCallback(it.item, null)
                    "unauthorized" -> sendItemOrMessageCallback(null, "Non autorisé")
                    "error_param" -> sendItemOrMessageCallback(null, "Erreur de paramètre")
                    else -> sendItemOrMessageCallback(null, "Problème avec la base de données")
                }
            }
        }

        override fun onFailure(call: Call<GetItemByIdDto>, t: Throwable) {
            sendItemOrMessageCallback(null, "Problème avec la base de données")
        }

    })
}