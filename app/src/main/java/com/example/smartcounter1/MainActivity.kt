package com.example.smartcounter1

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartcounter1.ui.theme.SmartTasbihTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartTasbihTheme {
                SmartTasbihApp()
            }
        }
    }
}

@Composable
fun SmartTasbihApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "tasbih") {
        composable("tasbih") { TasbihScreen(navController = navController) }
        composable("about") { AboutScreen(navController = navController) }
        composable("privacy") { PrivacyPolicyScreen(navController = navController) }
        composable("donation") { DonationScreen(navController = navController) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasbihScreen(navController: NavController) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    val sharedPreferences = remember { context.getSharedPreferences("TasbihPrefs", Context.MODE_PRIVATE) }
    var count by remember { mutableStateOf(sharedPreferences.getInt("count", 0)) }

    fun saveCount(newCount: Int) {
        sharedPreferences.edit().putInt("count", newCount).apply()
    }

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFFE0F7FA), Color(0xFFFFF9C4))
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("تسبیح هوشمند", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { navController.navigate("about") }) {
                        Icon(Icons.Default.Info, contentDescription = "درباره")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBrush)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "$count",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.animateContentSize()
            )
            Spacer(Modifier.height(48.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedButton(onClick = {
                    count = 0
                    saveCount(count)
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                }) {
                    Text("صفر کردن")
                }
                Spacer(Modifier.width(16.dp))
                Button(
                    onClick = { 
                        count++
                        saveCount(count)
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    },
                    modifier = Modifier.size(120.dp).clip(CircleShape),
                    elevation = ButtonDefaults.buttonElevation(8.dp)
                ) {
                    Text("ذکر", style = MaterialTheme.typography.headlineSmall)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavController) {
    val context = LocalContext.current
    val gradientBrush = Brush.verticalGradient(colors = listOf(Color(0xFFE0F7FA), Color(0xFFFFF9C4)))

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("درباره برنامه") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "بازگشت")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBrush)
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(elevation = CardDefaults.cardElevation(4.dp)) {
                Column(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("تقدیم به فرشته‌ای که به او مادر می‌گویم و تمام مادران دنیا", textAlign = TextAlign.Center, style = MaterialTheme.typography.titleMedium)
                }
            }
            Spacer(Modifier.height(24.dp))
            Card(elevation = CardDefaults.cardElevation(4.dp)) {
                Column(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("سازنده", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Text("سید رضا یعقوب پور", style = MaterialTheme.typography.headlineSmall)
                    Text("دانشجوی دکترای ریاضی محض", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.secondary)
                    Text("دارای مهندسی کامپیوتر", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.secondary)
                    Spacer(Modifier.height(16.dp))
                    TextButton(onClick = { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/reza-ygb"))) }) {
                        Text("GitHub: reza-ygb")
                    }
                }
            }
            Spacer(Modifier.weight(1f))
            Button(onClick = { navController.navigate("donation") }) {
                Text("حمایت از پژوهش و تدریس")
            }
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = { navController.navigate("privacy") }) {
                Text("سیاست حریم خصوصی")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonationScreen(navController: NavController) {
    val cardNumber = "6219-8619-3996-7195"
    val clipboardManager = LocalClipboardManager.current
    val gradientBrush = Brush.verticalGradient(colors = listOf(Color(0xFFE0F7FA), Color(0xFFFFF9C4)))

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("حمایت مالی") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "بازگشت")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBrush)
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(elevation = CardDefaults.cardElevation(4.dp)) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "این برنامه هدیه‌ای است به تمام ایران زمین. اگر مایل به سهیم شدن در این مسیر هستید، حمایت شما صرفاً برای کمک به افراد نیازمند در زمینه پژوهش و تدریس استفاده خواهد شد.",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(24.dp))
                    
                    Text("شماره کارت توسعه‌دهنده:", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    
                    Surface(shape = MaterialTheme.shapes.medium, border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)) {
                        Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text(cardNumber, style = MaterialTheme.typography.headlineSmall, modifier = Modifier.weight(1f))
                            IconButton(onClick = { 
                                clipboardManager.setText(AnnotatedString(cardNumber))
                            }) {
                                Icon(Icons.Default.ContentCopy, contentDescription = "کپی کردن شماره کارت")
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("سیاست حریم خصوصی") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "بازگشت")
                    }
                }
            )
        }
    ) { paddingValues ->
        AndroidView(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            factory = { context ->
                WebView(context).apply {
                    loadUrl("file:///android_asset/privacy_policy.html")
                }
            }
        )
    }
}


@Preview(showBackground = true, name = "صفحه اصلی")
@Composable
fun TasbihScreenPreview() {
    SmartTasbihTheme { TasbihScreen(rememberNavController()) }
}

@Preview(showBackground = true, name = "درباره من")
@Composable
fun AboutScreenPreview() {
    SmartTasbihTheme { AboutScreen(rememberNavController()) }
}

@Preview(showBackground = true, name = "صفحه حمایت")
@Composable
fun DonationScreenPreview() {
    SmartTasbihTheme { DonationScreen(rememberNavController()) }
}
