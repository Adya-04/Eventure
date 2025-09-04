package com.example.eventuree.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventuree.ui.theme.Montserrat
import io.github.boguszpawlowski.composecalendar.*
import io.github.boguszpawlowski.composecalendar.day.DayState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.example.eventuree.R

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
fun EventCalendar() {
    val events = remember {
        mapOf(
            LocalDate.of(2025, 7, 21) to "Meeting",
            LocalDate.of(2025, 7, 31) to "Birthday"
        )
    }

    val calendarState = rememberSelectableCalendarState()
    val selectedDate = calendarState.selectionState.selection.firstOrNull()

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    )  {
        Column(modifier = Modifier.padding(16.dp)) {
            CalendarHeader(calendarState)

            SelectableCalendar(
                calendarState = calendarState,
                monthHeader = {},
                dayContent = { dayState ->
                    CustomDayView(
                        dayState = dayState,
                        events = events,
                        selectedDate = selectedDate
                    )
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomDayView(
    dayState: DayState<*>,
    events: Map<LocalDate, String>,
    selectedDate: LocalDate?
) {
    val date = dayState.date
    val isCurrentMonth = dayState.isFromCurrentMonth
    val isSelected = date == selectedDate
    val isToday = date == LocalDate.now()
    val hasEvent = events.containsKey(date)

    val backgroundColor = when {
        isSelected -> Color(0xFF4A43EC) // selected
        isToday -> Color(0xFF4A43EC)    // today
        else -> Color.Transparent
    }

    val textColor = when {
        isSelected || isToday -> Color.White
        !isCurrentMonth -> Color.Gray
        else -> Color.Black
    }

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(4.dp)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = if (isToday) FontWeight.Bold else FontWeight.Medium,
                    color = textColor
                )
            )

            if (hasEvent && !isSelected && !isToday) {
                // Show dot only if it has event and isn't selected/today (just like image)
                Spacer(modifier = Modifier.height(2.dp))
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(Color.Black)
                )
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarHeader(calendarState: CalendarState<*>) {
    val currentMonth = calendarState.monthState.currentMonth
    val formatter = remember { DateTimeFormatter.ofPattern("MMMM yyyy") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButton(onClick = { calendarState.monthState.currentMonth = currentMonth.minusMonths(1) }) {
            Icon(
                painter = painterResource(id = R.drawable.left_arrow),
                contentDescription = "Previous month",
                tint = Color.Black
            )
        }

        Text(
            text = currentMonth.format(formatter),
            style = TextStyle(
                color = Color.Black,
                fontSize = 20.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.SemiBold
            )
        )

        TextButton(onClick = { calendarState.monthState.currentMonth = currentMonth.plusMonths(1) }) {
            Icon(
                painter = painterResource(R.drawable.right_arrow),
                contentDescription = "Month",
                tint = Color.Black
            )
        }
    }
}
