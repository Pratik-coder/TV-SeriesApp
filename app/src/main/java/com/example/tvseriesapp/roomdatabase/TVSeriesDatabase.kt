package com.example.tvseriesapp.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.tvseriesapp.typeconverter.ListConverter

@Database(entities = [TVDBModel::class,TVSeriesDBModel::class], version = 2, exportSchema = false)
@TypeConverters(ListConverter::class)
abstract class TVSeriesDatabase:RoomDatabase() {

    abstract fun tvDao():TVDao

    companion object{
        @Volatile private var instance:TVSeriesDatabase?=null
        private val LOCK=Any()

        operator fun invoke(context: Context)= instance?: synchronized(LOCK){
           instance?: buildDatabase(context).also {instance=it}
        }

        private fun buildDatabase(context: Context)=
            Room.databaseBuilder(context,TVSeriesDatabase::class.java,"tv_series.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}