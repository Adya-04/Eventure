package com.example.eventuree.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.toSize
import com.example.eventuree.ui.theme.Montserrat
import com.example.eventuree.ui.theme.inputBoxShape
import com.example.eventuree.R

@Composable
fun SocietyTypeDropdown(
    modifier: Modifier = Modifier,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var parentSize by remember { mutableStateOf(Size.Zero) }

    Box(modifier = modifier) {
        // Dropdown Anchor - Styled like InputBox
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .onGloballyPositioned {
                    parentSize = it.size.toSize()
                }
                .clickable { expanded = true },
            shape = inputBoxShape.medium,
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(2.dp, Color(0xFFE4DFDF))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.society_type),
                    contentDescription = "Dropdown Icon",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = if (selectedOption.isEmpty()) "Society Type" else selectedOption,
                    color = if (selectedOption.isEmpty()) Color(0xFF747688) else Color(0xFF120D26),
                    fontSize = 18.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown Arrow",
                    tint = Color.Black
                )
            }
        }

        // Dropdown menu matching width of parent
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { parentSize.width.toDp() })
                .background(Color.White)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option,
                            fontSize = 16.sp,
                            fontFamily = Montserrat,
                            color = Color(0xFF4C4C57)
                        )
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SocietyTypeDropdownPreview() {
    var selected by remember { mutableStateOf("") }

    SocietyTypeDropdown(
        selectedOption = selected,
        onOptionSelected = { selected = it },
        options = listOf("Tech", "Cultural", "Literary", "Others"),
        modifier = Modifier.padding(16.dp)
    )
}
