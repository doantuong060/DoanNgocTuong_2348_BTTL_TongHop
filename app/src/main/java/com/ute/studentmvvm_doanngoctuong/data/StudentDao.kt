package com.ute.studentmvvm_doanngoctuong.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ute.studentmvvm_doanngoctuong.model.Student
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {

    // Them mot sinh vien
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student)

    // Them danh sach sinh vien
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(students: List<Student>)

    // Cap nhat thong tin sinh vien
    @Update
    suspend fun updateStudent(student: Student)

    // Xoa mot sinh vien
    @Delete
    suspend fun deleteStudent(student: Student)

    // Xoa sinh vien theo ID
    @Query("DELETE FROM students_table WHERE id = :studentId")
    suspend fun deleteStudentById(studentId: Int)

    // Lay danh sach sinh vien va tu dong cap nhat khi du lieu thay doi
    @Query("SELECT * FROM students_table ORDER BY id DESC")
    fun getAllStudents(): Flow<List<Student>>

    // Tim sinh vien theo ma sinh vien
    @Query("SELECT * FROM students_table WHERE student_code = :studentCode LIMIT 1")
    suspend fun getStudentByCode(studentCode: String): Student?
}
