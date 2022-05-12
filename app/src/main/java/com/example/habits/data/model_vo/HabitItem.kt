package com.example.habits.data.model_vo

import android.graphics.Color
import android.os.Parcelable
import androidx.room.*
import com.example.habits.data.database.HabitTypeConverter
import com.example.habits.data.database.ListTypeConverter
import com.example.habits.data.model_vo.HabitItem.Companion.HABITS_TABLE_NAME
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity(tableName = HABITS_TABLE_NAME)
@TypeConverters(HabitTypeConverter::class, HabitTypeConverter::class, ListTypeConverter::class)
@Parcelize
data class HabitItem(
    @PrimaryKey
    val id: String = "-1",
    val title: String ="",
    val description: String = "",
    val priority: PriorityType = PriorityType.HIGH,
    val type: HabitType = HabitType.GOOD_HABIT,
    @ColumnInfo(name = "period_count")
    val periodCount: String,
    @ColumnInfo(name = "period_days")
    val periodDays: String,
    val color: Int = Color.WHITE,
    @ColumnInfo(name = "date_of_creation")
    val dateOfCreation: Long = Date().time,
    @ColumnInfo(name = "is_checked")
    var isChecked: Boolean = false,
    @ColumnInfo(name ="done_dates")
    val doneDates: List<Int> = arrayListOf()
): Parcelable {

    companion object {
        const val HABITS_TABLE_NAME = "habits"
    }
}