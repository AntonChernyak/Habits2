package com.example.habits.data.model

import android.graphics.Color
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity(tableName = "habit_db")
@Parcelize
data class HabitItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = -1,
    val title: String,
    val description: String = "",
    val priority: String,
    @Embedded
    val type: HabitType = HabitType.GOOD_HABIT,
    @ColumnInfo(name = "period_count")
    val periodCount: String,
    @ColumnInfo(name = "period_days")
    val periodDays: String,
    val color: Int = Color.WHITE,
    @ColumnInfo(name = "date_of_creation")
    val dateOfCreation: Long = Date().time,
    @ColumnInfo(name = "is_checked")
    var isChecked: Boolean = false
): Parcelable