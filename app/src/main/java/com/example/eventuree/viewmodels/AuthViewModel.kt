package com.example.eventuree.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import com.example.eventuree.data.repository.AuthRepository
import com.example.eventuree.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

interface IAuthViewModel {
    val loginLiveData: StateFlow<NetworkResult<LoginResponse>>
    val sendOTPLiveData: StateFlow<NetworkResult<SendOTPResponse>>
    val verifyOTPLiveData: StateFlow<NetworkResult<VerifyOTPResponse>>
    val signupStudentLiveData: StateFlow<NetworkResult<SignupStudentResponse>>
    val signupSocietyLiveData: StateFlow<NetworkResult<SignupSocietyResponse>>
    val isLogin: Boolean
    val googleSignInLiveData: StateFlow<NetworkResult<AuthLoginResponse>>
    val profileUploadLiveData: StateFlow<NetworkResult<ProfileUploadResponse>>
    val updateUserLiveData: StateFlow<NetworkResult<UpdateUserResponse>>
    val updateOrganiserLiveData: StateFlow<NetworkResult<UpdateOrganiserResponse>>

    fun updateOrganiser(id: String, updateOrganiserRequest: UpdateOrganiserRequest)
    fun updateUser(id: String, updateUserRequest: UpdateUserRequest)
    fun uploadProfileImage(image: MultipartBody.Part)
    fun signInWithGoogle(idToken: String)
    fun setLogin(value: Boolean)
    fun clearGoogleSignInState()
    fun login(loginRequest: LoginRequest)
    fun sendOTP(sendOTPRequest: SendOTPRequest)
    fun verifyOTP(verifyOTPRequest: VerifyOTPRequest)
    fun signupAsStudent(signupStudentRequest: SignupStudentRequest)
    fun signupAsSociety(signupSocietyRequest: SignupSocietyRequest)
}
class AuthViewModelPreview : ViewModel(), IAuthViewModel {
    override val loginLiveData: StateFlow<NetworkResult<LoginResponse>> =
        MutableStateFlow(NetworkResult.Start(null))
    override val sendOTPLiveData: StateFlow<NetworkResult<SendOTPResponse>> =
        MutableStateFlow(NetworkResult.Start(null))
    override val verifyOTPLiveData: StateFlow<NetworkResult<VerifyOTPResponse>> =
        MutableStateFlow(NetworkResult.Start(null))
    override val signupStudentLiveData: StateFlow<NetworkResult<SignupStudentResponse>> =
        MutableStateFlow(NetworkResult.Start(null))
    override val signupSocietyLiveData: StateFlow<NetworkResult<SignupSocietyResponse>> =
        MutableStateFlow(NetworkResult.Start(null))
    override val googleSignInLiveData: StateFlow<NetworkResult<AuthLoginResponse>> =
        MutableStateFlow(NetworkResult.Start(null))
    override val profileUploadLiveData: StateFlow<NetworkResult<ProfileUploadResponse>> =
        MutableStateFlow(NetworkResult.Start(null))
    override val updateUserLiveData: StateFlow<NetworkResult<UpdateUserResponse>> =
        MutableStateFlow(NetworkResult.Start(null))
    override val updateOrganiserLiveData: StateFlow<NetworkResult<UpdateOrganiserResponse>> =
        MutableStateFlow(NetworkResult.Start(null))


    override val isLogin: Boolean = false

    override fun updateOrganiser(id: String, updateOrganiserRequest: UpdateOrganiserRequest) {}
    override fun updateUser(id: String, updateUserRequest: UpdateUserRequest) {}
    override fun setLogin(value: Boolean) {}
    override fun clearGoogleSignInState() {}
    override fun uploadProfileImage(image: MultipartBody.Part) {}
    override fun login(loginRequest: LoginRequest) {}
    override fun signInWithGoogle(idToken: String) {}
    override fun sendOTP(sendOTPRequest: SendOTPRequest) {}
    override fun verifyOTP(verifyOTPRequest: VerifyOTPRequest) {}
    override fun signupAsStudent(signupStudentRequest: SignupStudentRequest) {}
    override fun signupAsSociety(signupSocietyRequest: SignupSocietyRequest) {}
}
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel(), IAuthViewModel {
    override val loginLiveData: StateFlow<NetworkResult<LoginResponse>>
        get() = authRepository.login

    override val sendOTPLiveData: StateFlow<NetworkResult<SendOTPResponse>>
        get() = authRepository.sendOTP

    override val verifyOTPLiveData: StateFlow<NetworkResult<VerifyOTPResponse>>
        get() = authRepository.verifyOTP

    override val signupStudentLiveData: StateFlow<NetworkResult<SignupStudentResponse>>
        get() = authRepository.signupStudent

    override val signupSocietyLiveData: StateFlow<NetworkResult<SignupSocietyResponse>>
        get() = authRepository.signupSociety

    override val googleSignInLiveData: StateFlow<NetworkResult<AuthLoginResponse>>
        get() = authRepository.googleSignIn

    override val profileUploadLiveData: StateFlow<NetworkResult<ProfileUploadResponse>>
        get() = authRepository.profileUpload

    override val updateUserLiveData: StateFlow<NetworkResult<UpdateUserResponse>>
        get() = authRepository.updateUser

    override val updateOrganiserLiveData: StateFlow<NetworkResult<UpdateOrganiserResponse>>
        get() = authRepository.updateOrganiser

    private var _isLogin: Boolean = false
    override val isLogin: Boolean get() = _isLogin

    override fun setLogin(value: Boolean) {
        _isLogin = value
    }

    override fun clearGoogleSignInState() {
        viewModelScope.launch {
            authRepository.clearGoogleSignInState()
        }
    }

    override fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            authRepository.login(loginRequest)
        }
    }

    override fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            authRepository.signInWithGoogle(idToken)
        }
    }

    override fun sendOTP(sendOTPRequest: SendOTPRequest) {
        viewModelScope.launch {
            authRepository.sendOTP(sendOTPRequest)
        }
    }

    override fun verifyOTP(verifyOTPRequest: VerifyOTPRequest) {
        viewModelScope.launch {
            authRepository.verifyOTP(verifyOTPRequest)
        }
    }

    override fun signupAsStudent(signupStudentRequest: SignupStudentRequest) {
        viewModelScope.launch {
            authRepository.signupAsStudent(signupStudentRequest)
        }
    }

    override fun signupAsSociety(signupSocietyRequest: SignupSocietyRequest) {
        viewModelScope.launch {
            authRepository.signupAsSociety(signupSocietyRequest)
        }
    }

    override fun uploadProfileImage(image: MultipartBody.Part) {
        viewModelScope.launch {
            authRepository.uploadProfileImage(image)
        }
    }

    override fun updateUser(id: String, updateUserRequest: UpdateUserRequest) {
        viewModelScope.launch {
            authRepository.updateUser(id, updateUserRequest)
        }
    }

    override fun updateOrganiser(id: String, updateOrganiserRequest: UpdateOrganiserRequest) {
        viewModelScope.launch {
            authRepository.updateOrganiser(id, updateOrganiserRequest)
        }
    }
}

