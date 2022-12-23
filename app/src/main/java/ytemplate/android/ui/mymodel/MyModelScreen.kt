package ytemplate.android.ui.mymodel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import ytemplate.android.ui.theme.YTemplateTheme

@Composable
fun MyModelScreen(viewModel: MyModelViewModel = hiltViewModel()) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    var name by remember {
        mutableStateOf("")
    }
    val items by produceState<MyModelUiState>(
        initialValue = MyModelUiState.Loading,
        key1 = lifecycle,
        key2 = viewModel,
        producer = {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    value = it
                }
            }
        })

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    , horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                OutlinedTextField(value = name, onValueChange = {
                    name = it
                }, modifier = Modifier.testTag("name_field"))
                Button(onClick = { viewModel.addModel(name) }, modifier = Modifier.testTag("add_button")) {
                    Text(text = "Add")
                }
            }

            if (items is MyModelUiState.Success) {
                (items as MyModelUiState.Success).data.forEach {
                    Text(text = it)
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview(){
    YTemplateTheme {
        MyModelScreen()
    }
}