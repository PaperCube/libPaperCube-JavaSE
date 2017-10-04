package studio.papercube.library.preferencestorage

import java.io.File

class LocalPreference(
        private val file: File) : PreferenceStorage() {

    companion object {
        fun newLocalPreference(file: File): PreferenceStorage {
            return LocalPreference(file).apply {

            }
        }
    }

    init {

    }

//    fun newProperty(name:String) =
}