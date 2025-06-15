package com.psycicproductions.contractioncounter

import android.content.Context
import android.icu.text.DateFormat
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.psycicproductions.contractioncounter.data.Contraction
import com.psycicproductions.contractioncounter.ui.ContractionViewModel
import com.psycicproductions.contractioncounter.ui.theme.ContractionCounterTheme
import java.util.Date

private val GlobalDateFormat: DateFormat =
    DateFormat.getPatternInstance(DateFormat.NUM_MONTH_DAY + DateFormat.HOUR_MINUTE_SECOND)
private val StartIcon: ImageVector
    get() {
        if (_StartIcon == null) {
            _StartIcon = ImageVector.Builder(
                name = "Start",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 960f,
                viewportHeight = 960f
            ).apply {
                path(
                    fill = SolidColor(Color(0xFF000000))
                ) {
                    moveTo(320f, 760f)
                    verticalLineToRelative(-560f)
                    lineToRelative(440f, 280f)
                    close()
                    moveToRelative(80f, -146f)
                    lineToRelative(210f, -134f)
                    lineToRelative(-210f, -134f)
                    close()
                }
            }.build()
        }

        return _StartIcon!!
    }
private var _StartIcon: ImageVector? = null
private val StopIcon: ImageVector
    get() {
        if (_StopIcon == null) {
            _StopIcon = ImageVector.Builder(
                name = "Stop",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 960f,
                viewportHeight = 960f
            ).apply {
                path(
                    fill = SolidColor(Color(0xFF000000))
                ) {
                    moveTo(320f, 320f)
                    verticalLineToRelative(320f)
                    close()
                    moveToRelative(-80f, 400f)
                    verticalLineToRelative(-480f)
                    horizontalLineToRelative(480f)
                    verticalLineToRelative(480f)
                    close()
                    moveToRelative(80f, -80f)
                    horizontalLineToRelative(320f)
                    verticalLineToRelative(-320f)
                    horizontalLineTo(320f)
                    close()
                }
            }.build()
        }

        return _StopIcon!!
    }
private var _StopIcon: ImageVector? = null

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContractionCounterTheme {
                ContractionCounterApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContractionCounterApp() {
    val contractionViewModel: ContractionViewModel = viewModel()
    val contractions by contractionViewModel.allContractions.observeAsState(emptyList())
    contractions.isEmpty() // placeholder to prevent code cleanup
    val context = LocalContext.current
    val testingList = true

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contraction Counter") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(
                        onClick = { deleteAllContractions(context) },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete all contractions"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (contractions.isEmpty() && !testingList) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No contractions recorded",
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (testingList) {
                        items(50) { index ->
                            Text(
                                text = "Item $index",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                        }
                    } else {
                        items(contractions) { contraction ->
                            ContractionItem(
                                contraction = contraction
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { contractionStart(context) },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.size(80.dp)
                ) {
                    Icon(
                        imageVector = StartIcon,
                        contentDescription = "Start",
                        modifier = Modifier.size(48.dp)
                    )
                }
                Button(
                    onClick = { contractionEnd(context) },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF44336),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.size(80.dp)
                ) {
                    Icon(
                        imageVector = StopIcon,
                        contentDescription = "Stop",
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ContractionItem(contraction: Contraction) {
    TODO("Not yet implemented")
}

fun contractionStart(context: Context) {
    Toast.makeText(context, "Contraction Started!", Toast.LENGTH_SHORT).show()
}

fun contractionEnd(context: Context) {
    Toast.makeText(context, "Contraction Ended!", Toast.LENGTH_SHORT).show()
}

fun deleteAllContractions(context: Context) {
    Toast.makeText(context, "All Contractions Deleted!", Toast.LENGTH_SHORT).show()
}

fun formatDate(timestamp: Long, dateFormat: DateFormat = GlobalDateFormat): String {
    return dateFormat.format(Date(timestamp))
}