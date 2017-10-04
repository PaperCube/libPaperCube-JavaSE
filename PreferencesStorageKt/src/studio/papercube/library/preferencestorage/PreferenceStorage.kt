package studio.papercube.library.preferencestorage

abstract class PreferenceStorage {
    abstract class ObjectProperty<T>(private val name: String) {
        abstract var value: T
        abstract val defaultValue: T
    }

    abstract class Property(private val name: String) : ObjectProperty<String>(name) {

    }

}