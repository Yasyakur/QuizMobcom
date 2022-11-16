package unj.cs.student

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface StudentDao {
    @Query("SELECT * FROM students")
    fun getAll(): List<Student>

    @Insert
    fun insert(vararg student: Student)

    @Update
    fun update(student: Student)

    @Delete
    fun delete(student: Student)

    @Query("SELECT * FROM students WHERE ids = :ids")
    fun getStudentByIds(ids: String): LiveData<Student>

    @Query("SELECT * FROM students WHERE name = :name")
    fun getStudentByName(name: String): LiveData<Student>

    @Query("SELECT * FROM students WHERE ids = :ids OR name = :name")
    fun getStudentOr(ids: String, name: String): LiveData<Student>
}