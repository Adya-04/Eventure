package com.example.eventuree.repository

import android.util.Log
import com.example.eventuree.api.AuthApi
import com.example.eventuree.models.AuthLoginRequest
import com.example.eventuree.models.AuthLoginResponse
import com.example.eventuree.models.LoginRequest
import com.example.eventuree.models.LoginResponse
import com.example.eventuree.models.ProfileUploadResponse
import com.example.eventuree.models.SendOTPRequest
import com.example.eventuree.models.SendOTPResponse
import com.example.eventuree.models.SignupSocietyRequest
import com.example.eventuree.models.SignupSocietyResponse
import com.example.eventuree.models.SignupStudentRequest
import com.example.eventuree.models.SignupStudentResponse
import com.example.eventuree.models.UpdateOrganiserRequest
import com.example.eventuree.models.UpdateOrganiserResponse
import com.example.eventuree.models.UpdateUserRequest
import com.example.eventuree.models.UpdateUserResponse
import com.example.eventuree.models.VerifyOTPRequest
import com.example.eventuree.models.VerifyOTPResponse
import com.example.eventuree.utils.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MultipartBody
import org.json.JSONObject
import java.net.SocketTimeoutException
import javax.inject.Inject

class AuthRepository @Inject constructor(private val authApi: AuthApi) {

    //Login Flow
    private val _login = MutableStateFlow<NetworkResult<LoginResponse>>(NetworkResult.Start(null))
    val login: StateFlow<NetworkResult<LoginResponse>>
        get() = _login

    //Login with Google Flow
    private val _googleSignIn =
        MutableStateFlow<NetworkResult<AuthLoginResponse>>(NetworkResult.Start(null))
    val googleSignIn: StateFlow<NetworkResult<AuthLoginResponse>>
        get() = _googleSignIn

    // Send OTP Flow
    private val _sendOTP =
        MutableStateFlow<NetworkResult<SendOTPResponse>>(NetworkResult.Start(null))
    val sendOTP: StateFlow<NetworkResult<SendOTPResponse>>
        get() = _sendOTP

    // Verify OTP Flow
    private val _verifyOTP = MutableStateFlow<NetworkResult<VerifyOTPResponse>>(
        NetworkResult.Start(
            null
        )
    )
    val verifyOTP: StateFlow<NetworkResult<VerifyOTPResponse>>
        get() = _verifyOTP

    // Student Signup Flow
    private val _signupStudent = MutableStateFlow<NetworkResult<SignupStudentResponse>>(
        NetworkResult.Start(
            null
        )
    )
    val signupStudent: StateFlow<NetworkResult<SignupStudentResponse>>
        get() = _signupStudent

    // Society Signup Flow
    private val _signupSociety = MutableStateFlow<NetworkResult<SignupSocietyResponse>>(
        NetworkResult.Start(
            null
        )
    )
    val signupSociety: StateFlow<NetworkResult<SignupSocietyResponse>>
        get() = _signupSociety

    //Upload Profile Picture Flow
    private val _profileUpload =
        MutableStateFlow<NetworkResult<ProfileUploadResponse>>(
            NetworkResult.Start(null)
        )
    val profileUpload: StateFlow<NetworkResult<ProfileUploadResponse>>
        get() =  _profileUpload

    //Update User Flow
    private val _updateUser = MutableStateFlow<NetworkResult<UpdateUserResponse>>(NetworkResult.Start(null))
    val updateUser: StateFlow<NetworkResult<UpdateUserResponse>> get() = _updateUser

    //Update Organiser Flow
    private val _updateOrganiser = MutableStateFlow<NetworkResult<UpdateOrganiserResponse>>(NetworkResult.Start(null))
    val updateOrganiser: StateFlow<NetworkResult<UpdateOrganiserResponse>> get() = _updateOrganiser

    fun clearGoogleSignInState() {
        _googleSignIn.value = NetworkResult.Start(null)
    }

    suspend fun login(loginRequest: LoginRequest) {
        _login.value = (NetworkResult.Loading())
        try {
            val response = authApi.login(loginRequest)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Log.d("AuthAPICall", "$responseBody")
                    _login.value = (NetworkResult.Success(responseBody))
                } else {
                    _login.value = (NetworkResult.Error("Response body is null"))
                }
            } else if (response.errorBody() != null) {
                val errObj = JSONObject(response.errorBody()!!.charStream().readText())
                Log.d("AuthAPICall", errObj.toString())
                _login.value = (NetworkResult.Error(errObj.getString("message")))
            } else {
                Log.d("AuthAPICall", response.errorBody().toString())
                _login.value = (NetworkResult.Error("Something went wrong"))
            }
        } catch (e: SocketTimeoutException) {
            Log.d("AuthAPICall", e.toString())
            _login.value = (NetworkResult.Error("Please try again!"))
        } catch (e: Exception) {
            Log.d("AuthAPICall", e.toString())
            _login.value = (NetworkResult.Error("Unexpected error occurred"))
        }
    }

    suspend fun signInWithGoogle(idToken: String) {
        _googleSignIn.value = NetworkResult.Loading()
        try {
            val response = authApi.signInWithGoogle(AuthLoginRequest(idToken))
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Log.d("AuthAPICall", "$responseBody")
                    _googleSignIn.value = NetworkResult.Success(responseBody)
                } else {
                    _googleSignIn.value = NetworkResult.Error("Response body is null")
                }
            } else if (response.errorBody() != null) {
                val errObj = JSONObject(response.errorBody()!!.charStream().readText())
                Log.d("AuthAPICall", errObj.toString())
                _googleSignIn.value = NetworkResult.Error(errObj.getString("message"))
            } else {
                Log.d("AuthAPICall", response.errorBody().toString())
                _googleSignIn.value = NetworkResult.Error("Something went wrong")
            }
        } catch (e: SocketTimeoutException) {
            Log.d("AuthAPICall", e.toString())
            _googleSignIn.value = NetworkResult.Error("Please try again!")
        } catch (e: Exception) {
            Log.d("AuthAPICall", e.toString())
            _googleSignIn.value = NetworkResult.Error("Unexpected error occurred")
        }
    }

    suspend fun sendOTP(sendOTPRequest: SendOTPRequest) {
        _sendOTP.value = NetworkResult.Loading()
        try {
            val response = authApi.sendOTP(sendOTPRequest)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Log.d("AuthAPICall", "$responseBody")
                    _sendOTP.value = NetworkResult.Success(responseBody)
                } else {
                    _sendOTP.value = NetworkResult.Error("Response body is null")
                }
            } else if (response.errorBody() != null) {
                val errObj = JSONObject(response.errorBody()!!.charStream().readText())
                Log.d("AuthAPICall", errObj.toString())
                _sendOTP.value = NetworkResult.Error(errObj.getString("message"))
            } else {
                Log.d("AuthAPICall", response.errorBody().toString())
                _sendOTP.value = NetworkResult.Error("Something went wrong")
            }
        } catch (e: SocketTimeoutException) {
            Log.d("AuthAPICall", e.toString())
            _sendOTP.value = NetworkResult.Error("Please try again!")
        } catch (e: Exception) {
            Log.d("AuthAPICall", e.toString())
            _sendOTP.value = NetworkResult.Error("Unexpected error occurred")
        }
    }

    suspend fun verifyOTP(verifyOTPRequest: VerifyOTPRequest) {
        _verifyOTP.value = NetworkResult.Loading()
        try {
            val response = authApi.verifyOTP(verifyOTPRequest)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Log.d("AuthAPICall", "$responseBody")
                    _verifyOTP.value = NetworkResult.Success(responseBody)
                } else {
                    _verifyOTP.value = NetworkResult.Error("Response body is null")
                }
            } else if (response.errorBody() != null) {
                val errObj = JSONObject(response.errorBody()!!.charStream().readText())
                Log.d("AuthAPICall", errObj.toString())
                _verifyOTP.value = NetworkResult.Error(errObj.getString("message"))
            } else {
                Log.d("AuthAPICall", response.errorBody().toString())
                _verifyOTP.value = NetworkResult.Error("Something went wrong")
            }
        } catch (e: SocketTimeoutException) {
            Log.d("AuthAPICall", e.toString())
            _verifyOTP.value = NetworkResult.Error("Please try again!")
        } catch (e: Exception) {
            Log.d("AuthAPICall", e.toString())
            _verifyOTP.value = NetworkResult.Error("Unexpected error occurred")
        }
    }

    suspend fun signupAsStudent(signupStudentRequest: SignupStudentRequest) {
        _signupStudent.value = NetworkResult.Loading()
        try {
            val response = authApi.signupAsStudent(signupStudentRequest)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Log.d("AuthAPICall", "$responseBody")
                    _signupStudent.value = NetworkResult.Success(responseBody)
                } else {
                    _signupStudent.value = NetworkResult.Error("Response body is null")
                }
            } else if (response.errorBody() != null) {
                val errObj = JSONObject(response.errorBody()!!.charStream().readText())
                Log.d("AuthAPICall", errObj.toString())
                _signupStudent.value = NetworkResult.Error(errObj.getString("message"))
            } else {
                Log.d("AuthAPICall", response.errorBody().toString())
                _signupStudent.value = NetworkResult.Error("Something went wrong")
            }
        } catch (e: SocketTimeoutException) {
            Log.d("AuthAPICall", e.toString())
            _signupStudent.value = NetworkResult.Error("Please try again!")
        } catch (e: Exception) {
            Log.d("AuthAPICall", e.toString())
            _signupStudent.value = NetworkResult.Error("Unexpected error occurred")
        }
    }

    suspend fun signupAsSociety(signupSocietyRequest: SignupSocietyRequest) {
        _signupSociety.value = NetworkResult.Loading()
        try {
            val response = authApi.signupAsSociety(signupSocietyRequest)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Log.d("AuthAPICall", "$responseBody")
                    _signupSociety.value = NetworkResult.Success(responseBody)
                } else {
                    _signupSociety.value = NetworkResult.Error("Response body is null")
                }
            } else if (response.errorBody() != null) {
                val errObj = JSONObject(response.errorBody()!!.charStream().readText())
                Log.d("AuthAPICall", errObj.toString())
                _signupSociety.value = NetworkResult.Error(errObj.getString("message"))
            } else {
                Log.d("AuthAPICall", response.errorBody().toString())
                _signupSociety.value = NetworkResult.Error("Something went wrong")
            }
        } catch (e: SocketTimeoutException) {
            Log.d("AuthAPICall", e.toString())
            _signupSociety.value = NetworkResult.Error("Please try again!")
        } catch (e: Exception) {
            Log.d("AuthAPICall", e.toString())
            _signupSociety.value = NetworkResult.Error("Unexpected error occurred")
        }
    }

    suspend fun uploadProfileImage(image: MultipartBody.Part) {
        _profileUpload.value = NetworkResult.Loading()
        try {
            val response = authApi.uploadProfilePic(image)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    _profileUpload.value = NetworkResult.Success(body)
                } else {
                    _profileUpload.value = NetworkResult.Error("Response body is null")
                }
            } else {
                val errObj = JSONObject(response.errorBody()!!.charStream().readText())
                _profileUpload.value = NetworkResult.Error(errObj.getString("message"))
            }
        } catch (e: SocketTimeoutException) {
            _profileUpload.value = NetworkResult.Error("Please try again!")
        } catch (e: Exception) {
            _profileUpload.value = NetworkResult.Error("Unexpected error occurred")
        }
    }

    suspend fun updateUser(id: String, updateUserRequest: UpdateUserRequest) {
        _updateUser.value = NetworkResult.Loading()
        try {
            val response = authApi.updateUser(id, updateUserRequest)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    _updateUser.value = NetworkResult.Success(body)
                } else {
                    _updateUser.value = NetworkResult.Error("Response body is null")
                }
            } else if (response.errorBody() != null) {
                val errObj = JSONObject(response.errorBody()!!.charStream().readText())
                _updateUser.value = NetworkResult.Error(errObj.getString("message"))
            } else {
                _updateUser.value = NetworkResult.Error("Something went wrong")
            }
        } catch (e: SocketTimeoutException) {
            _updateUser.value = NetworkResult.Error("Please try again!")
        } catch (e: Exception) {
            Log.d("AuthAPICall", e.toString())
            _updateUser.value = NetworkResult.Error("Unexpected error occurred")
        }
    }

    suspend fun updateOrganiser(id: String, updateOrganiserRequest: UpdateOrganiserRequest) {
        _updateOrganiser.value = NetworkResult.Loading()
        try {
            val response = authApi.updateOrganiser(id, updateOrganiserRequest)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    _updateOrganiser.value = NetworkResult.Success(body)
                } else {
                    _updateOrganiser.value = NetworkResult.Error("Response body is null")
                }
            } else if (response.errorBody() != null) {
                val errObj = JSONObject(response.errorBody()!!.charStream().readText())
                _updateOrganiser.value = NetworkResult.Error(errObj.getString("message"))
            } else {
                _updateOrganiser.value = NetworkResult.Error("Something went wrong")
            }
        } catch (e: SocketTimeoutException) {
            _updateOrganiser.value = NetworkResult.Error("Please try again!")
        } catch (e: Exception) {
            Log.d("AuthAPICall", e.toString())
            _updateOrganiser.value = NetworkResult.Error("Unexpected error occurred")
        }
    }

}