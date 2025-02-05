package com.example.feedarticles.network

import com.example.feedarticles.dtos.GetAllItemsDto
import com.example.feedarticles.dtos.GetItemByIdDto
import com.example.feedarticles.dtos.ItemDto
import com.example.feedarticles.dtos.NewItemDto
import com.example.feedarticles.dtos.DeleteUpdateNewResponseDto
import com.example.feedarticles.dtos.RegisterAndLoginDto
import com.example.feedarticles.dtos.RegisterAndLoginResponseDto
import com.example.feedarticles.dtos.UpdateItemDto
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


/*status => 1 : OK
0 : pas de création
-1: problème de paramètre
-5: création non autorisée*/
fun createNewItem(newItemDto: NewItemDto, sendResponseCallback : (Boolean, String?) -> Unit){
    val call : Call<DeleteUpdateNewResponseDto>? = ApiService.getApi().createNewItem(newItemDto)
    call?.enqueue(object: Callback<DeleteUpdateNewResponseDto>{
        override fun onResponse(call: Call<DeleteUpdateNewResponseDto>, response: Response<DeleteUpdateNewResponseDto>) {
            response.body()?.let {
                when(it.status){
                    1 -> sendResponseCallback(true, null)
                    0 -> sendResponseCallback(false, "Article non crée")
                    -1 -> sendResponseCallback(false, "Vérifiez vos paramètres")
                    -5 -> sendResponseCallback(false, "Vous n'avez pas le droit d'effectuer cette action")
                    else -> return
                }
            }
        }

        override fun onFailure(call: Call<DeleteUpdateNewResponseDto>, t: Throwable) {
            sendResponseCallback(false, "Problème avec la base de données")
        }

    })
}

fun updateItem(updateItemDto: UpdateItemDto, sendResponseCall : (Boolean, String) -> Unit){
    val call : Call<DeleteUpdateNewResponseDto>? = ApiService.getApi().updateItem(updateItemDto)
    call?.enqueue(object: Callback<DeleteUpdateNewResponseDto>{
        override fun onResponse(
            call: Call<DeleteUpdateNewResponseDto>,
            response: Response<DeleteUpdateNewResponseDto>
        ) {
            response.body()?.let {
                when(it.status){
                    1 -> sendResponseCall(true, "Les modifications ont bien été prises en compte")
                    0 -> sendResponseCall(false, "pas de modifcation effectuée")
                    -5 -> sendResponseCall(false, "Vous n'avez pas les autorisations pour faire ces modifications")
                    else -> sendResponseCall(false, "Un problème est survenu")
                }
            }
        }

        override fun onFailure(call: Call<DeleteUpdateNewResponseDto>, t: Throwable) {
            sendResponseCall(false, "Un problème est survenu")
        }

    })
}

fun deleteItem(item : ItemDto, user: UserDto, sendResponseCall : (Boolean, String) -> Unit){
    val call : Call<DeleteUpdateNewResponseDto>? = ApiService.getApi().deleteItem(item.id, user.token)
    call?.enqueue(object: Callback<DeleteUpdateNewResponseDto>{
        override fun onResponse(
            call: Call<DeleteUpdateNewResponseDto>,
            response: Response<DeleteUpdateNewResponseDto>
        ) {
            response.body()?.let {
                when(it.status){
                    1 -> sendResponseCall(true, "L'article a bien été supprimé")
                    0 -> sendResponseCall(false, "pas de suppression effectuée")
                    -5 -> sendResponseCall(false, "Vous n'avez pas les autorisations pour faire cette suppression")
                    else -> sendResponseCall(false, "Un problème est survenu")
                }
            }
        }

        override fun onFailure(call: Call<DeleteUpdateNewResponseDto>, t: Throwable) {
            sendResponseCall(false, "Un problème est survenue")
        }

    } )
}