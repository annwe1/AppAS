package br.edu.up.as34123768

import android.app.Application
import br.edu.up.as34123768.data.AppContainer
import br.edu.up.as34123768.data.AppDataContainer

class InventoryApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
