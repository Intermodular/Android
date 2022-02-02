package sainero.dani.intermodular

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.viewmodel.compose.viewModel
import sainero.dani.intermodular.Api.MainViewModel
import sainero.dani.intermodular.ViewModels.ViewModelUsers
import sainero.dani.intermodular.Navigation.NavigationHost
import sainero.dani.intermodular.Utils.MainViewModelSearchBar

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()
    private val viewModelUsers by viewModels<ViewModelUsers>()


    private val mainViewModelSearchBar: MainViewModelSearchBar by viewModels()

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            viewModelUsers.getUserList()
            NavigationHost(mainViewModelSearchBar, mainViewModel,viewModelUsers)
        }
    }
}
