package com.example.eventuree.ui.onboarding

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.eventuree.ui.components.InputBox
import com.example.eventuree.ui.theme.Montserrat
import com.example.eventuree.models.SignupSocietyRequest
import com.example.eventuree.models.SignupStudentRequest
import com.example.eventuree.ui.components.MultilineInputBox
import com.example.eventuree.ui.components.NextButton
import com.example.eventuree.ui.components.PasswordInputBox
import com.example.eventuree.ui.components.SocietyTypeDropdown
import com.example.eventuree.utils.NetworkResult
import com.example.eventuree.viewmodels.AuthViewModel
import com.example.eventuree.R
import com.example.eventuree.models.UpdateOrganiserRequest
import com.example.eventuree.models.UpdateUserRequest
import com.example.eventuree.viewmodels.PrefsViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

enum class UserType { STUDENT, ORGANISER }

//@Preview(showBackground = true)
//@Composable
//fun SignupDetailsScreenPreview() {
//    SignUpDetailsScreen(
//        email = "",
//        onDetailsSubmit = {},
//        authViewModel = hiltViewModel()
//    )
//}

@Composable
fun SignUpDetailsScreen(
    email: String,
    initialToken: String? = null, // for Google Sign-In flow
    userId: String? = null, // for Google Sign-In flow
    name: String? = null, // for Google Sign-In flow
    profilePicUri: String? = null, // for Google Sign-In flow
    onDetailsSubmit: () -> Unit,
    authViewModel: AuthViewModel,
    prefsViewModel: PrefsViewModel
) {

    var selectedTab by remember { mutableStateOf(UserType.STUDENT) }
    var isButtonEnabled by remember { mutableStateOf(true) }
    val context = LocalContext.current

    // State for student form
    var studentName by remember { mutableStateOf("") }
    var admissionNumber by remember { mutableStateOf("") }
    var studentPassword by remember { mutableStateOf("") }
    var aboutMe by remember { mutableStateOf("") }

    // State for organizer form
    var societyName by remember { mutableStateOf("") }
    var adminName by remember { mutableStateOf("") }
    var societyType by remember { mutableStateOf("") }
    var adminAdmissionNo by remember { mutableStateOf("") }
    var organizerPassword by remember { mutableStateOf("") }
    var societyDescription by remember { mutableStateOf("") }

    // Collect signup states
    val studentSignupState by authViewModel.signupStudentLiveData.collectAsState()
    val societySignupState by authViewModel.signupSocietyLiveData.collectAsState()

    val updateUserState by authViewModel.updateUserLiveData.collectAsState()
    val updateOrganiserState by authViewModel.updateOrganiserLiveData.collectAsState()

    // State for profile image
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }

    //Session id state
    var sessionId by remember { mutableStateOf<String?>(null) }

    // State for Google Sign-In flow
    val isGoogleSignInFlow = userId != null
//    var googleProfilePicUri by remember { mutableStateOf(profilePicUri) }

    // Handle image display for Google Sign-In flow
    val googleProfileImage by remember(profilePicUri) {
        derivedStateOf {
            profilePicUri?.let { Uri.parse(it) }
        }
    }

    // Handle image selection
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            profileImageUri = it
            // Upload the image when selected
            val imageFile = context.contentResolver.openInputStream(it)?.use { inputStream ->
                val file = File.createTempFile("profile", ".jpg", context.cacheDir).apply {
                    outputStream().use { output -> inputStream.copyTo(output) }
                }
                file
            }

            imageFile?.let { file ->
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val imagePart = MultipartBody.Part.createFormData(
                    "image",
                    file.name,
                    requestFile
                )
                if (!isGoogleSignInFlow) {
                    // For regular flow, upload new image
                    authViewModel.uploadProfileImage(imagePart)
                } else {
                    //we have to show the image uri passes from back screen
                }
            }
        }
    }
    // Observe upload state
    val uploadState by authViewModel.profileUploadLiveData.collectAsState()

    LaunchedEffect(uploadState) {
        when (uploadState) {
            is NetworkResult.Loading -> {
                // Show loading if needed
            }

            is NetworkResult.Success -> {
                uploadState.data?.let { response ->
                    sessionId = response.sessionId // Store the sessionId
                    Toast.makeText(context, "Photo uploaded successfully", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            is NetworkResult.Error -> {
                Toast.makeText(
                    context,
                    uploadState.message ?: "Failed to upload photo",
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> Unit
        }
    }

    LaunchedEffect(updateUserState) {
        when (updateUserState) {
            is NetworkResult.Loading -> isButtonEnabled = false
            is NetworkResult.Success -> {
                isButtonEnabled = true
                updateUserState.data?.let {
                    // If initialToken is present (from Google Sign-In), save it
                    initialToken?.let { token -> prefsViewModel.saveToken(token) }
                    authViewModel.setLogin(true)
                    Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT)
                        .show()
                    onDetailsSubmit()
                }
            }

            is NetworkResult.Error -> {
                isButtonEnabled = true
                Toast.makeText(
                    context,
                    updateUserState.message ?: "Failed to update profile",
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> Unit
        }
    }

    // Handle signup states
    LaunchedEffect(studentSignupState) {
        when (studentSignupState) {
            is NetworkResult.Loading -> isButtonEnabled = false
            is NetworkResult.Success -> {
                isButtonEnabled = true
                studentSignupState.data?.let {
                    // If initialToken is present (from Google Sign-In), save it. Else, save the one from backend response.
                    prefsViewModel.saveToken(initialToken ?: it.accessToken)
                    authViewModel.setLogin(true)
                }
                Toast.makeText(context, "Signup successful", Toast.LENGTH_SHORT).show()
                onDetailsSubmit()
            }

            is NetworkResult.Error -> {
                isButtonEnabled = true
                Toast.makeText(
                    context,
                    studentSignupState.message ?: "Signup failed",
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> Unit
        }
    }

    LaunchedEffect(societySignupState) {
        when (societySignupState) {
            is NetworkResult.Loading -> isButtonEnabled = false
            is NetworkResult.Success -> {
                isButtonEnabled = true
                societySignupState.data?.let {
                    initialToken?.let { token -> prefsViewModel.saveToken(token) }
                    authViewModel.setLogin(true)
                }
                Toast.makeText(context, "Signup successful", Toast.LENGTH_SHORT).show()
                onDetailsSubmit()
            }

            is NetworkResult.Error -> {
                isButtonEnabled = true
                Toast.makeText(
                    context,
                    societySignupState.message ?: "Signup failed",
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> Unit
        }
    }

    LaunchedEffect(updateOrganiserState) {
        when (updateOrganiserState) {
            is NetworkResult.Loading -> isButtonEnabled = false
            is NetworkResult.Success -> {
                isButtonEnabled = true
                updateOrganiserState.data?.let {
                    // Handle successful update
                    initialToken?.let { token -> prefsViewModel.saveToken(token) }
                    authViewModel.setLogin(true)
                    Toast.makeText(context, "Organiser updated successfully", Toast.LENGTH_SHORT)
                        .show()
                    onDetailsSubmit()
                }
            }

            is NetworkResult.Error -> {
                isButtonEnabled = true
                Toast.makeText(
                    context,
                    updateOrganiserState.message ?: "Failed to update organiser",
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> Unit
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.app_bg),
            contentDescription = "App background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(26.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(12.dp))

            Text(
                text = "Enter your details for\nSign up",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = Montserrat
                ),
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(Modifier.height(22.dp))
            // Custom Tab Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(128.dp))
                    .background(Color(0xFFEEEEF0)),
                horizontalArrangement = Arrangement.Center
            )
            {
                // Student Tab
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(28.dp))
                        .background(
                            if (selectedTab == UserType.STUDENT) Color(0xFF3578FF)
                            else Color.Transparent
                        )
                        .clickable { selectedTab = UserType.STUDENT }
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Student",
                        color = if (selectedTab == UserType.STUDENT) Color.White
                        else Color(0xFF4C4C57),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(28.dp))
                        .background(
                            if (selectedTab == UserType.ORGANISER) Color(0xFF3578FF)
                            else Color.Transparent
                        )
                        .clickable { selectedTab = UserType.ORGANISER }
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Organiser",
                        color = if (selectedTab == UserType.ORGANISER) Color.White
                        else Color(0xFF4C4C57),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Similar for Organiser tab
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Show loading indicator when processing
            when {
                studentSignupState is NetworkResult.Loading && selectedTab == UserType.STUDENT -> {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(16.dp),
                        color = Color(0xFF5669FF)
                    )
                }

                societySignupState is NetworkResult.Loading && selectedTab == UserType.ORGANISER -> {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(16.dp),
                        color = Color(0xFF5669FF)
                    )
                }

                else -> {
                    // Form content based on selected tab
                    when (selectedTab) {
                        UserType.STUDENT -> StudentSignUpForm(
                            name = if (isGoogleSignInFlow) name!! else studentName,
                            onNameChange = { studentName = it },
                            admissionNumber = admissionNumber,
                            onAdmissionNumberChange = { admissionNumber = it },
                            password = studentPassword,
                            onPasswordChange = { studentPassword = it },
                            onSubmit = {
                                if (isGoogleSignInFlow) {
                                    // Google Sign-In flow - update existing user
                                    authViewModel.updateUser(
                                        userId,
                                        UpdateUserRequest(
                                            name = studentName,
                                            email = email,
                                            admissionNumber = admissionNumber,
                                            aboutMe = aboutMe
                                        )
                                    )
                                } else {
                                    // Regular flow - create new user
                                    if (validateStudentForm(
                                            studentName,
                                            admissionNumber,
                                            studentPassword,
                                            sessionId != null,
                                            isGoogleSignInFlow,
                                            context
                                        )
                                    ) {
                                            authViewModel.signupAsStudent(
                                                SignupStudentRequest(
                                                    aboutMe = aboutMe,
                                                    admissionNumber = admissionNumber,
                                                    email = email,
                                                    name = studentName,
                                                    password = studentPassword,
                                                    sessionId = sessionId!!
                                                )
                                            )
                                    }
                                }
                            },
                            isButtonEnabled = isButtonEnabled,
                            profileImageUri = googleProfileImage ?: profileImageUri,
                            showPasswordField = !isGoogleSignInFlow,
                            onProfileImageClick = {
                                launcher.launch("image/*")
                            }
                        )

                        UserType.ORGANISER -> OrganiserSignUpForm(
                            societyName = societyName,
                            onSocietyNameChange = { societyName = it },
                            adminName = adminName,
                            onAdminNameChange = { adminName = it },
                            societyType = societyType,
                            onSocietyTypeChange = { societyType = it },
                            adminAdmissionNo = adminAdmissionNo,
                            onAdminAdmissionNoChange = { adminAdmissionNo = it },
                            password = organizerPassword,
                            onPasswordChange = { organizerPassword = it },
                            societyDescription = societyDescription,
                            onSocietyDescriptionChange = { societyDescription = it },
                            onSubmit = {
                                val societyTypeEnum = when (societyType) {
                                    "Technical" -> "technical"
                                    "Cultural" -> "cultural"
                                    "Literary" -> "literary"
                                    "Others" -> "others"
                                    else -> "Society"
                                }
                                if (isGoogleSignInFlow) {
                                    // Google Sign-In flow - update existing user
                                    authViewModel.updateOrganiser(
                                        userId,
                                        UpdateOrganiserRequest(
                                            name = studentName,
                                            email = email,
                                            admissionNumber = admissionNumber,
                                            societyName = societyName,
                                            societyType = societyTypeEnum,
                                            societyDescription = societyDescription
                                        )
                                    )
                                } else {
                                    // Regular flow - create new user
                                    if (validateOrganizerForm(
                                            societyName,
                                            adminName,
                                            societyType,
                                            adminAdmissionNo,
                                            organizerPassword,
                                            societyDescription,
                                            context
                                        )
                                    ) {
                                        authViewModel.signupAsSociety(
                                            SignupSocietyRequest(
                                                admissionNumber = adminAdmissionNo,
                                                email = email,
                                                name = adminName,
                                                password = organizerPassword,
                                                societyDescription = societyDescription,
                                                societyName = societyName,
                                                societyType = societyTypeEnum
                                            )
                                        )
                                    }
                                }
                            },
                            isButtonEnabled = isButtonEnabled
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StudentSignUpForm(
    name: String,
    onNameChange: (String) -> Unit,
    admissionNumber: String,
    onAdmissionNumberChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    showPasswordField: Boolean = true,
    onSubmit: () -> Unit,
    isButtonEnabled: Boolean,
    profileImageUri: Uri?,
    onProfileImageClick: () -> Unit
) {
    Spacer(Modifier.height(16.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Circular Profile Image
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable {
                    onProfileImageClick()
                },
            contentAlignment = Alignment.Center
        ) {
            if (profileImageUri != null) {
                Image(
                    painter = rememberImagePainter(profileImageUri),
                    contentDescription = "Profile",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.profile_icon),
                    contentDescription = "Profile",
                    modifier = Modifier.size(60.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Form fields
        InputBox(
            value = name,
            onValueChange = onNameChange,
            placeholder = "Full name",
            icon = R.drawable.profile_icon
        )
        Spacer(modifier = Modifier.height(4.dp))

        InputBox(
            value = admissionNumber,
            onValueChange = onAdmissionNumberChange,
            placeholder = "Admission Number",
            icon = R.drawable.adm_num_icon
        )

        if (showPasswordField) {
            Spacer(modifier = Modifier.height(4.dp))

            PasswordInputBox(
                value = password,
                onValueChange = onPasswordChange,
                placeholder = "Password"
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        NextButton(
            text = "SIGN UP", onClick = onSubmit,
            enabled = isButtonEnabled
        )
    }
}

@Composable
fun OrganiserSignUpForm(
    societyName: String,
    onSocietyNameChange: (String) -> Unit,
    adminName: String,
    onAdminNameChange: (String) -> Unit,
    societyType: String,
    onSocietyTypeChange: (String) -> Unit,
    adminAdmissionNo: String,
    onAdminAdmissionNoChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    societyDescription: String,
    onSocietyDescriptionChange: (String) -> Unit,
    onSubmit: () -> Unit,
    isButtonEnabled: Boolean
) {
    val scrollState = rememberScrollState()

    Spacer(Modifier.height(16.dp))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Circular Profile Image
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.profile_icon),
                contentDescription = "Profile",
                modifier = Modifier.size(60.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Form fields
        InputBox(
            value = societyName,
            onValueChange = onSocietyNameChange,
            placeholder = "Society name",
            icon = R.drawable.society_icon
        )
        InputBox(
            value = adminName,
            onValueChange = onAdminNameChange,
            placeholder = "Admin Name",
            icon = R.drawable.profile_icon
        )
        InputBox(
            value = adminAdmissionNo,
            onValueChange = onAdminAdmissionNoChange,
            placeholder = "Admin Admission No",
            icon = R.drawable.adm_num_icon
        )
        SocietyTypeDropdown(
            options = listOf("Technical", "Cultural", "Literary", "Others"),
            selectedOption = societyType,
            onOptionSelected = onSocietyTypeChange
        )
        PasswordInputBox(
            value = password,
            onValueChange = onPasswordChange,
            placeholder = "Password"
        )
        MultilineInputBox(
            value = societyDescription,
            onValueChange = onSocietyDescriptionChange,
            placeholder = "Society Description",
            icon = R.drawable.society_descp,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.weight(1f))

        NextButton(
            text = "SIGN UP",
            enabled = isButtonEnabled,
            onClick = onSubmit
        )
    }
}

private fun validateStudentForm(
    name: String,
    admissionNumber: String,
    password: String,
    hasProfilePicture: Boolean,
    isGoogleFlow: Boolean,
    context: android.content.Context
): Boolean {
    return when {
        name.isBlank() -> {
            Toast.makeText(context, "Name cannot be empty", Toast.LENGTH_SHORT).show()
            false
        }

        name.length < 3 -> {
            Toast.makeText(context, "Name is too short", Toast.LENGTH_SHORT).show()
            false
        }

        admissionNumber.isBlank() -> {
            Toast.makeText(context, "Admission number cannot be empty", Toast.LENGTH_SHORT).show()
            false
        }

        !admissionNumber.matches(Regex("[A-Za-z0-9]+")) -> {
            Toast.makeText(context, "Invalid admission number", Toast.LENGTH_SHORT).show()
            false
        }

        !isGoogleFlow && password.isBlank() -> {
            Toast.makeText(context, "Password cannot be empty", Toast.LENGTH_SHORT).show()
            false
        }

        !isGoogleFlow && password.length < 6 -> {
            Toast.makeText(context, "Password must be at least 6 characters", Toast.LENGTH_SHORT)
                .show()
            false
        }

        !isGoogleFlow && !hasProfilePicture -> {
            Toast.makeText(context, "Please upload a profile picture", Toast.LENGTH_SHORT).show()
            false
        }

        else -> true
    }
}

private fun validateOrganizerForm(
    societyName: String,
    adminName: String,
    societyType: String,
    adminAdmissionNo: String,
    password: String,
    societyDescription: String,
    context: android.content.Context
): Boolean {
    return when {
        societyName.isBlank() -> {
            Toast.makeText(context, "Society name cannot be empty", Toast.LENGTH_SHORT).show()
            false
        }

        adminName.isBlank() -> {
            Toast.makeText(context, "Admin name cannot be empty", Toast.LENGTH_SHORT).show()
            false
        }

        societyType.isBlank() -> {
            Toast.makeText(context, "Society type cannot be empty", Toast.LENGTH_SHORT).show()
            false
        }

        adminAdmissionNo.isBlank() -> {
            Toast.makeText(context, "Admin admission number cannot be empty", Toast.LENGTH_SHORT)
                .show()
            false
        }

        !adminAdmissionNo.matches(Regex("[A-Za-z0-9]+")) -> {
            Toast.makeText(context, "Invalid admission number", Toast.LENGTH_SHORT).show()
            false
        }

        password.isBlank() -> {
            Toast.makeText(context, "Password cannot be empty", Toast.LENGTH_SHORT).show()
            false
        }

        password.length < 6 -> {
            Toast.makeText(context, "Password must be at least 6 characters", Toast.LENGTH_SHORT)
                .show()
            false
        }

        societyDescription.isBlank() -> {
            Toast.makeText(context, "Society description cannot be empty", Toast.LENGTH_SHORT)
                .show()
            false
        }

        societyDescription.length < 10 -> {
            Toast.makeText(
                context,
                "Description must be at least 10 characters",
                Toast.LENGTH_SHORT
            ).show()
            false
        }

        else -> true
    }
}