package gr.dipae.thesisfitnessapp.framework.db

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.*

/*
@Database(
    entities = [],
    version = 1
)
//@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private const val DB_NAME = "thesis-fitness"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}*/
