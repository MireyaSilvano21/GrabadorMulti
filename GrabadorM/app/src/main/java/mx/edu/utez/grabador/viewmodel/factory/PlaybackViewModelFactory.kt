package mx.edu.utez.grabador.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mx.edu.utez.grabador.data.repository.SettingsRepository
import mx.edu.utez.grabador.viewmodel.PlaybackViewModel

// --- 10. PlaybackViewModelFactory--- Necesario para pasar el Repositorio al ViewModel
class PlaybackViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlaybackViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            val repository = SettingsRepository(application)
            return PlaybackViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}