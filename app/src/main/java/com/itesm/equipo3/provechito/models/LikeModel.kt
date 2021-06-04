import android.content.Context
import android.util.Log
import com.itesm.equipo3.provechito.api.ApiClient
import com.itesm.equipo3.provechito.api.ResponseObjects.DeleteResponse
import com.itesm.equipo3.provechito.interfaces.ILike
import com.itesm.equipo3.provechito.pojo.Like.Like
import com.itesm.equipo3.provechito.pojo.Like.LikeListResponse
import com.itesm.equipo3.provechito.pojo.Like.LikeRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LikeModel(val presenter: ILike.Presenter) : ILike.Model{
    private val apiClient = ApiClient()

    override fun getLikes(context: Context) {
        apiClient.getApiService(context).getLikes()
                .enqueue(object : Callback<LikeListResponse> {
                    override fun onFailure(call: Call<LikeListResponse>, t: Throwable) {
                        Log.e("Category Model", "GetLikes response failed STATUS: ${t.message}")
                    }

                    override fun onResponse(call: Call<LikeListResponse>, response: Response<LikeListResponse>) {
                        val serviceResponse = response.body()
                        if (response.isSuccessful && serviceResponse?.likes != null) {
                            presenter.likesObtained(serviceResponse)
                        } else {
                            Log.e("Category Model", "GetLikes response failed STATUS: ${response.isSuccessful}")
                        }
                    }
                })
    }

    override fun addLike(context: Context, recipeId: String) {
        apiClient.getApiService(context).addLike(LikeRequest(recipeId))
                .enqueue(object : Callback<Like> {
                    override fun onFailure(call: Call<Like>, t: Throwable) {
                        Log.e("LikeModel", "AddLike response failed STATUS: ${t.message}")
                    }

                    override fun onResponse(call: Call<Like>, response: Response<Like>) {
                        val serviceResponse = response.body()
                        if (response.isSuccessful && serviceResponse != null) {
                            Log.i("LikeModel", "AddLike response Success")
                            serviceResponse.recipe?.let { presenter.likeAdded(it) }
                        } else {
                            Log.e("LikeModel", "AddLike response failed STATUS: ${response.isSuccessful}")
                        }
                    }
                })
    }

    override fun removeLike(context: Context, recipeId: String, index: Int) {
        apiClient.getApiService(context).removeLikeByRecipe(recipeId)
                .enqueue(object : Callback<DeleteResponse> {
                    override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
                        Log.e("LikeModel", "RemoveLike response failed STATUS: ${t.message}")
                    }

                    override fun onResponse(call: Call<DeleteResponse>, response: Response<DeleteResponse>) {
                        val serviceResponse = response.body()
                        if (response.isSuccessful) {
                            Log.i("LikeModel", "RemoveLike response Success")
                            presenter.popRemovedLike(index)
                        } else {
                            Log.e("LikeModel", "RemoveLike response failed STATUS: ${response.isSuccessful}")
                        }
                    }
                })
    }
}