package com.ekpro.weightbalance

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.currentCoroutineContext
import java.io.Serializable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

@Database(entities = [Aircraft::class], version = 1)
abstract class AircraftDatabase : RoomDatabase() {
    abstract fun aircraftDao(): AircraftDao
    companion object {
        // marking the instance as volatile to ensure atomic access to the variable
        @Volatile
        private var INSTANCE: AircraftDatabase? = null
        private const val NUMBER_OF_THREADS = 4
        val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(
            NUMBER_OF_THREADS
        )

        fun getDatabase(context: Context): AircraftDatabase? {
            if (INSTANCE == null) {
                synchronized(AircraftDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AircraftDatabase::class.java, "aircraft_specs"
                        )
                            .addCallback(sRoomDatabaseCallback)
                            .allowMainThreadQueries()
                                // Allow until workaround is not needed
                            .build()
                    }
                }
            }
            return INSTANCE
        }

        /**
         * Override the onCreate method to populate the database.
         * For this sample, we clear the database every time it is created.
         */
        private val sRoomDatabaseCallback: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                databaseWriteExecutor.execute {

                    // Populate the database in the background.
                    // If you want to start with more words, just add them.
//                    val dao: AircraftDao = INSTANCE!!.aircraftDao()
//                    dao.deleteAll()
//                    var word: Aircraft? = Aircraft(0, "Piper PA-28-181", )
//                    dao.insertAll(word)
                }
            }
        }
    }
}

@Entity
data class Aircraft(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "makemodel") val makemodel: String?,
    @ColumnInfo(name = "weight_empty") val weight_empty: Float?,
    @ColumnInfo(name = "arm_pilot") val weight_pilot: Float?,
    @ColumnInfo(name = "arm_row1") val weight_row1: Float?,
    @ColumnInfo(name = "arm_fuel") val weight_fuel: Float?,
    @ColumnInfo(name = "arm_baggage") val weight_baggage: Float?
) : Serializable

@Dao
interface AircraftDao {
    @Query("SELECT * FROM aircraft")
    fun getAll(): LiveData<List<Aircraft>>

    @Query("SELECT * FROM aircraft WHERE uid IN (:model)")
    fun loadAllByIds(model: FloatArray): List<Aircraft>

    @Query("SELECT * FROM aircraft WHERE makemodel LIKE :makemodel LIMIT 1")
    fun findByName(makemodel: String): Aircraft

    @Insert
    fun insertAll(vararg users: Aircraft)

    @Delete
    fun delete(user: Aircraft)

    @Query("DELETE FROM aircraft")
    fun deleteAll()
}
