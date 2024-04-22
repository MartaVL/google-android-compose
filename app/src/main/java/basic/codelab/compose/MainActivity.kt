package basic.codelab.compose

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.material3.Card
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import basic.codelab.compose.ui.theme.BasicCodeLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicCodeLabTheme {
                MyApp(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier) {
        if(shouldShowOnboarding) {
            OnBoardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            Greetings(modifier = modifier)
        }
    }
}

@Composable
fun Greetings(modifier: Modifier = Modifier,
               names: List<String> =  List(1000) { "$it" }) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

@Composable
fun OnBoardingScreen(modifier: Modifier = Modifier, onContinueClicked: () -> Unit) {
    Column(modifier = modifier.fillMaxWidth(),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Text(text = "Welcome to the Basics Codelab!!")
        ElevatedButton(colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = modifier.padding(vertical = 24.dp),
            onClick =  onContinueClicked
        ) {
            Text(text ="Continue")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name)
    }
}

@Composable
fun CardContent(name: String) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Row (modifier = Modifier
        .padding(12.dp)
        .animateContentSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    ) {
        Column(modifier = Modifier
            .weight(1f)
            .padding(12.dp)
        ) {
            Text(text = "Hello")
            Text(text = name, style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.ExtraBold
            ))
            if (expanded) {
                Text(
                    text = ("Composem ipsum color sit lazy, " +
                            "padding theme elit, sed do bouncy. ").repeat(4),
                )
            }
        }
        IconButton (
            onClick = { expanded = !expanded }
        ) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun MyAppPreview() {
    BasicCodeLabTheme {
        MyApp(Modifier.fillMaxWidth())
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "GreetingPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingPreview() {
    BasicCodeLabTheme {
        Greetings()
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    BasicCodeLabTheme {
        OnBoardingScreen(onContinueClicked = {})
    }
}
