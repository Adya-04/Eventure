package com.example.eventuree.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventuree.ui.theme.Montserrat
import com.example.eventuree.ui.theme.inputBoxShape
import com.example.eventuree.R

@Composable
@Preview
fun PasswordInputBoxPreview(){
    PasswordInputBox(
        value = "",
        onValueChange = {},
        placeholder = "Your password"
    )
}
@Composable
fun PasswordInputBox(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = inputBoxShape.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(2.dp, Color(0xFFE4DFDF))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.password_icon),
                contentDescription = "Password icon",
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.width(16.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = TextStyle(
                        color = Color(0xFF120D26),
                        fontSize = 18.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Medium
                    ),
                    cursorBrush = SolidColor(Color(0xFF120D26)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    decorationBox = { innerTextField ->
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                color = Color(0xFF747688),
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontFamily = Montserrat,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                        innerTextField()
                    },
                    singleLine = true,
                    enabled = enabled
                )
            }

            Spacer(Modifier.width(8.dp))

            val eyeIcon = if (passwordVisible) R.drawable.visibility_on else R.drawable.visibility_off
            Image(
                painter = painterResource(eyeIcon),
                contentDescription = if (passwordVisible) "Hide password" else "Show password",
                modifier = Modifier
                    .fillMaxHeight()
                    .size(24.dp)
                    .align(Alignment.CenterVertically)
                    .clickable { passwordVisible = !passwordVisible }
            )
        }
    }
}
