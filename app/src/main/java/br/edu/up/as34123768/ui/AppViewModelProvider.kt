package br.edu.up.as34123768.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import br.edu.up.as34123768.InventoryApplication
import br.edu.up.as34123768.ui.home.HomeViewModel
import br.edu.up.as34123768.ui.item.ItemDetailsViewModel
import br.edu.up.as34123768.ui.item.ItemEditViewModel
import br.edu.up.as34123768.ui.item.ItemEntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ItemEditViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.itemsRepository
            )
        }
        initializer {
            ItemEntryViewModel(inventoryApplication().container.itemsRepository)
        }

        initializer {
            ItemDetailsViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.itemsRepository
            )
        }

        initializer {
            HomeViewModel(inventoryApplication().container.itemsRepository)
        }
    }
}

fun CreationExtras.inventoryApplication(): InventoryApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as InventoryApplication)
