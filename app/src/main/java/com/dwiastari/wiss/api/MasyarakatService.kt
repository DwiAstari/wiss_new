package com.dwiastari.wiss.api

import com.dwiastari.wiss.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MasyarakatService{
    @GET("artikel/data_artikel.php")
    suspend fun getArticle() : Response<ArtikelResponse>
    
    @FormUrlEncoded
    @POST("artikel/delete_artikel.php")
    suspend fun deleteArticle(@Field("id_artikel") id_artikel: String) : Response<DefaultResponse>
    
    @Multipart
    @POST("artikel/create_artikel.php")
    suspend fun createArticle(
        @Part("judul_artikel") judul: RequestBody,
        @Part("tanggal_artikel") tanggal_artikel: RequestBody,
        @Part("isi_artikel") isi_artikel: RequestBody,
        @Part("area") area: RequestBody,
        @Part("penulis") penulis: RequestBody,
        @Part("status") status: RequestBody,
        @Part foto_kegiatan: MultipartBody.Part
    ): Response<DefaultResponse>
    
    @Multipart
    @POST("artikel/update_artikel.php")
    suspend fun updateArticle(
        @Part("id_artikel") id_artikel: RequestBody,
        @Part("judul_artikel") judul: RequestBody,
        @Part("tanggal_artikel") tanggal_artikel: RequestBody,
        @Part("isi_artikel") isi_artikel: RequestBody,
        @Part("area") area: RequestBody,
        @Part("penulis") penulis: RequestBody,
        @Part("status") status: RequestBody,
        @Part foto_kegiatan: MultipartBody.Part?
    ): Response<DefaultResponse>

    @GET("layananharian/data_layanan.php")
    suspend fun getLayanan() : Response<LayananResponse>
    
    @FormUrlEncoded
    @POST("layananharian/update_layanan.php")
    suspend fun updateLayanan(
        @Field("id_hari") id_hari: String,
        @Field("layanan") layanan: String
    ): Response<DefaultResponse>

    @GET("slides/data_slides.php")
    suspend fun getSlide() : Response<SlideResponse>
    
    @GET("slides/active_slides.php")
    suspend fun getActiveSlide() : Response<SlideResponse>
    
    @Multipart
    @POST("slides/create_slides.php")
    suspend fun createSlides(
        @Part("judul_slide") judul: RequestBody,
        @Part("status") status: RequestBody,
        @Part foto_slide: MultipartBody.Part
    ): Response<DefaultResponse>
    
    @FormUrlEncoded
    @POST("slides/update_slides.php")
    suspend fun updateSlides(
        @Field("id_slide") id_slide: String,
        @Field("judul_slide") judul_slide: String,
        @Field("status") status_slide: String
    ): Response<DefaultResponse>
    
    @FormUrlEncoded
    @POST("slides/delete_slides.php")
    suspend fun deleteSlides(@Field("id_slide") id_slide: String) : Response<DefaultResponse>
    
    @GET("video/data_video.php")
    suspend fun getVideo() : Response<VideoResponse>
    
    @FormUrlEncoded
    @POST("video/delete_video.php")
    suspend fun deleteVideo(@Field("id_video") id_video: String) : Response<DefaultResponse>
    
    @FormUrlEncoded
    @POST("video/create_video.php")
    suspend fun createVideo(
        @Field("judul_video") judul: String,
        @Field("link_video") status: String
    ): Response<DefaultResponse>
    
    @FormUrlEncoded
    @POST("video/update_video.php")
    suspend fun updateVideo(
        @Field("id_video") id: String,
        @Field("judul_video") judul: String,
        @Field("link_video") link: String
    ): Response<DefaultResponse>
    
    @GET("ebook/data_ebook.php")
    suspend fun getEbook() : Response<EbookResponse>
    
    @FormUrlEncoded
    @POST("ebook/delete_ebook.php")
    suspend fun deleteEbook(@Field("id_ebook") id_ebook: String) : Response<DefaultResponse>
    
    @FormUrlEncoded
    @POST("ebook/create_ebook.php")
    suspend fun createEbook(
        @Field("judul_ebook") judul: String,
        @Field("link_ebook") status: String
    ): Response<DefaultResponse>
    
    @FormUrlEncoded
    @POST("ebook/update_ebook.php")
    suspend fun updateEbook(
        @Field("id_ebook") id: String,
        @Field("judul_ebook") judul: String,
        @Field("link_ebook") link: String
    ): Response<DefaultResponse>
    
    @FormUrlEncoded
    @POST("loginuser/loginuser.php")
    fun loginUser(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<ResponseLogin>
    
    @FormUrlEncoded
    @POST("masyarakat/create_masyarakat.php")
    fun registerUser(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("nama_masyarakat") name: String,
        @Field("email") email: String,
        @Field("hp") phone: String,
        @Field("umur") age: String,
        @Field("domisili") address: String,
        ): Call<DefaultResponse>
    
    @FormUrlEncoded
    @POST("masyarakat/masyarakat_by_id.php")
    suspend fun getMasyarakatById(
        @Field("username") username: String
    ): Response<MasyarakatResponse>
    
    @FormUrlEncoded
    @POST("akun_konselor/konselor_by_id.php")
    suspend fun getKonselorById(
        @Field("username") username: String
    ): Response<AkunKonselorResponse>
    
    @GET("loginuser/usercount.php")
    fun getUserCount(): Call<UserCountResponse>
    
    @GET("loginuser/usercount.php")
    suspend fun getKunjungan(): Response<UserCountResponse>
    
    @GET("akun_konselor/data_akun.php")
    suspend fun getKonselor(): Response<KonselorResponse>
    
    @FormUrlEncoded
    @POST("loginuser/add_token.php")
    fun addToken(
        @Field("username") username: String,
        @Field("token") token: String
    ): Call<DefaultResponse>
    
    @FormUrlEncoded
    @POST("loginuser/delete_token.php")
    suspend fun deleteToken(
        @Field("username") username: String,
        @Field("token") token: String
    ): Response<DefaultResponse>
    
    @FormUrlEncoded
    @POST("loginuser/get_token.php")
    fun getToken(
        @Field("username") username: String
    ): Call<DefaultResponse>
    
    @FormUrlEncoded
    @POST("loginuser/send_message.php")
    fun sendMessage(
        @Field("sender") sender: String,
        @Field("to") to: String,
        @Field("message") message: String,
        @Field("title") title: String
    ): Call<SendNotificationResponse>
    
    @Multipart
    @POST("akun_konselor/create_akun.php")
    suspend fun createKonselor(
        @Part("username") username: RequestBody,
        @Part("password") password: RequestBody,
        @Part("nama") nama: RequestBody,
        @Part("bidang") bidang: RequestBody,
        @Part foto: MultipartBody.Part?
    ): Response<DefaultResponse>
    
    @Multipart
    @POST("akun_konselor/update_akun.php")
    suspend fun updateKonselor(
        @Part("username") username: RequestBody,
        @Part("nama") nama: RequestBody,
        @Part("bidang") bidang: RequestBody,
        @Part foto: MultipartBody.Part?
    ): Response<DefaultResponse>
    
    @FormUrlEncoded
    @POST("akun_konselor/change_password.php")
    suspend fun changePasswordKonselor(
        @Field("username") username: String,
        @Field("old_password") oldPassword: String,
        @Field("new_password") newPassword: String
    ): Response<DefaultResponse>
    
    @FormUrlEncoded
    @POST("akun_konselor/delete_akun.php")
    suspend fun deleteKonselor(@Field("username") username: String) : Response<DefaultResponse>
    
    @Multipart
    @POST("masyarakat/update_masyarakat.php")
    suspend fun updateMasyarakat(
        @Part("username") username: RequestBody,
        @Part("nama") nama: RequestBody,
        @Part("umur") umur: RequestBody,
        @Part("hp") hp: RequestBody,
        @Part("domisili") domisili: RequestBody,
        @Part foto: MultipartBody.Part?
    ): Response<DefaultResponse>
    
    @FormUrlEncoded
    @POST("masyarakat/change_password.php")
    suspend fun changePasswordMasyarakat(
        @Field("username") username: String,
        @Field("old_password") oldPassword: String,
        @Field("new_password") newPassword: String
    ): Response<DefaultResponse>
    
    @FormUrlEncoded
    @POST("loginuser/change_password.php")
    suspend fun changePasswordAdmin(
        @Field("username") username: String,
        @Field("old_password") oldPassword: String,
        @Field("new_password") newPassword: String
    ): Response<DefaultResponse>
}
