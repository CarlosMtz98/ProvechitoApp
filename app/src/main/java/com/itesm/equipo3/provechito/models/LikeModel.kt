import android.content.Context
import android.util.Log
import com.itesm.equipo3.provechito.api.ApiClient
import com.itesm.equipo3.provechito.interfaces.ILike
import com.itesm.equipo3.provechito.pojo.Like.LikeListResponse
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

}