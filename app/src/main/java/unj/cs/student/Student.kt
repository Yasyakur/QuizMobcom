package unj.cs.student

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    val ids: String,
    val name: String
//
//    @ColumnInfo(name = "ids") val ids : String,
//    @ColumnInfo(name = "name") val name: String
){
    @PrimaryKey(autoGenerate = true)
        val _id: Int? = null
}
