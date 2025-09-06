package com.example.eventuree.data.api

import com.example.eventuree.data.models.AuthLoginRequest
import com.example.eventuree.data.models.AuthLoginResponse
import com.example.eventuree.data.models.LoginRequest
import com.example.eventuree.data.models.LoginResponse
import com.example.eventuree.data.models.ProfileUploadResponse
import com.example.eventuree.data.models.SendOTPRequest
import com.example.eventuree.data.models.SendOTPResponse
import com.example.eventuree.data.models.SignupSocietyRequest
import com.example.eventuree.data.models.SignupSocietyResponse
import com.example.eventuree.data.models.SignupStudentRequest
import com.example.eventuree.data.models.SignupStudentResponse
import com.example.eventuree.data.models.UpdateOrganiserRequest
import com.example.eventuree.data.models.UpdateOrganiserResponse
import com.example.eventuree.data.models.UpdateUserRequest
import com.example.eventuree.data.models.UpdateUserResponse
import com.example.eventuree.data.models.VerifyOTPRequest
import com.example.eventuree.data.models.VerifyOTPResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("auth/send-otp")
    suspend fun sendOTP(@Body sendOTPRequest: SendOTPRequest): Response<SendOTPResponse>

    @POST("auth/verify-otp")
    suspend fun verifyOTP(@Body verifyOTPRequest: VerifyOTPRequest): Response<VerifyOTPResponse>

    @POST("auth/register")
    suspend fun signupAsStudent(@Body signupStudentRequest: SignupStudentRequest): Response<SignupStudentResponse>

    @POST("auth/organizer/register")
    suspend fun signupAsSociety(@Body signupStudentRequest: SignupSocietyRequest): Response<SignupSocietyResponse>

    @POST("auth/google")
    suspend fun signInWithGoogle(@Body authLoginRequest: AuthLoginRequest): Response<AuthLoginResponse>

    @Multipart
    @POST("user/upload")
    suspend fun uploadProfilePic(@Part image: MultipartBody.Part): Response<ProfileUploadResponse>

    @PATCH("user/{id}")
    suspend fun updateUser(
        @Path("id") id: String,
        @Body updateUserRequest: UpdateUserRequest
    ): Response<UpdateUserResponse>

    @PATCH("user/organiser/{id}")
    suspend fun updateOrganiser(
        @Path("id") id: String,
        @Body updateOrganiserRequest: UpdateOrganiserRequest
    ): Response<UpdateOrganiserResponse>
}