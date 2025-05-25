package com.example.businesscardapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.businesscardapp.ui.theme.BusinessCardAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BusinessCardAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BusinessCardApp(

                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun BusinessCardApp(modifier: Modifier) {
    Box(
        modifier
            .fillMaxSize()
            .background(Color(0xFFC5D5C5)),
    ) {
        NameCard(modifier = Modifier.align(Alignment.Center))

        ContactCard(modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun NameCard(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color(0xFF263326))
        ) {
            Image(
                painter = painterResource(R.drawable.android_logo),
                contentDescription = null
            )
        }
        Text(
            text = stringResource(R.string.name),
            modifier = Modifier.padding(12.dp),
            fontSize = 32.sp
        )
        Text(
            text = stringResource(R.string.role),
            color = Color(0xFF2B642D),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ContactCard(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .width(IntrinsicSize.Max),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ContactRow(
            Icons.Filled.Phone,
            "Phone",
            stringResource(R.string.phone_number)
        )
        ContactRow(
            Icons.Filled.Share,
            "Handle",
            stringResource(R.string.handle)
        )
        ContactRow(
            Icons.Filled.Email,
            "Email",
            stringResource(R.string.email)
        )

    }
}

@Composable
fun ContactRow(icon: ImageVector, contentDesc: String, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDesc,
            modifier = Modifier
                .padding(end = 16.dp),
            tint = Color(0xFF006d3b)
        )
        Text(text)
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BusinessCardAppTheme {
        BusinessCardApp(

            modifier = Modifier
        )
    }
}