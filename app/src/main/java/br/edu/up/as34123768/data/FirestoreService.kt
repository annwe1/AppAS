package br.edu.up.as34123768.data

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirestoreService {
    private val db = Firebase.firestore
    private val collectionName = "items"

    private suspend fun getMaxId(): Int {
        val data = db.collection(collectionName).get().await()
        val maxId = data.documents.mapNotNull {
            it.getLong("id")?.toInt()
        }.maxOrNull() ?: 0
        return maxId + 1
    }

    suspend fun createOrUpdateItem(item: Item) {
        val document = if (item.id == 0) db.collection(collectionName).document() else db.collection(collectionName).document(item.id.toString())
        val data = mapOf(
            "id" to item.id,
            "name" to item.name,
            "price" to item.price,
            "quantity" to item.quantity
        )

        document.set(data)
            .addOnSuccessListener {
                println("Documento adicionado com ID: ${data}")
            }
            .addOnFailureListener {
                println("Erro inclusao de documento")
            }
    }

    suspend fun createItem(item: Item){
        try {
            val data = mapOf(
                "id" to getMaxId(),
                "name" to item.name,
                "price" to item.price,
                "quantity" to item.quantity
            )
            val documentReference = db.collection(collectionName).add(data).await()
            println("Documento adicionado com ID: ${documentReference.id}")
        } catch (e: Exception) {
            println("Erro ao adicionar documento: $e")
        }
    }

    fun readAllItems(onComplete: (Boolean, List<Item>?, String?) -> Unit) {
        db.collection(collectionName)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val items = querySnapshot.documents.mapNotNull { document ->
                    document.toObject(Item::class.java)?.copy(id = document.id.toInt())
                }
                onComplete(true, items, null)
            }
            .addOnFailureListener {
                onComplete(false, null, it.message)
            }
    }

    fun getItems() {
        db.collection(collectionName)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    println("${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                println("Erro ao buscar documentos: $exception")
            }
    }

    fun readItemById(id: String, onComplete: (Boolean, Item?, String?) -> Unit) {
        db.collection(collectionName).document(id)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val item = document.toObject(Item::class.java)?.copy(id = document.id.toInt())
                    onComplete(true, item, null)
                } else {
                    onComplete(false, null, "Document not found")
                }
            }
            .addOnFailureListener {
                onComplete(false, null, it.message)
            }
    }

    fun getItemById(id: String) {
        db.collection(collectionName).document(id)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val item = document.toObject(Item::class.java)?.copy(id = document.id.toInt())
                    println(item.toString())
                } else {
                    println("Document not found")
                }
            }
            .addOnFailureListener {
                println("Documento not found")
            }
    }

    fun deleteItemById(id: String, onComplete: (Boolean, String?) -> Unit) {
        db.collection(collectionName).document(id)
            .delete()
            .addOnSuccessListener {
                onComplete(true, null)
            }
            .addOnFailureListener {
                onComplete(false, it.message)
            }
    }

    suspend fun deleteItem(item: Item){
        try {
            val documentReference = db.collection(collectionName).document(item.id.toString()).delete().await()
            println("Documento com ID: ${documentReference.toString()} deletado com sucesso!")
        } catch (e: Exception) {
            println("Erro ao deletar documento: $e")
        }
    }

}