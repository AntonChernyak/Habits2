package com.example.habits.data.model_vo

import android.graphics.Color
import android.os.Parcelable
import androidx.room.*
import com.example.habits.data.database.HabitTypeConverter
import com.example.habits.data.model_vo.HabitItem.Companion.HABITS_TABLE_NAME
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity(tableName = HABITS_TABLE_NAME)
@TypeConverters(HabitTypeConverter::class)
@Parcelize
data class HabitItem(
    @PrimaryKey(autoGenerate = true)
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
    var isChecked: Boolean = false
): Parcelable {

    companion object {
        const val HABITS_TABLE_NAME = "habits"
    }
}