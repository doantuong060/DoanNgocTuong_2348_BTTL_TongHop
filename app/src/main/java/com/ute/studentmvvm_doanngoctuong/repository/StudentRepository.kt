
package com.ute.studentmvvm_doanngoctuong.repository

import com.ute.studentmvvm_doanngoctuong.data.StudentDao
import com.ute.studentmvvm_doanngoctuong.model.Student
import kotlinx.coroutines.flow.Flow

class StudentRepository(
    private val studentDao: StudentDao
) {

    // Quan sat danh sach sinh vien tu Room
    val allStudents: Flow<List<Student>> =
        studentDao.getAllStudents()

    // Them sinh vien
    suspend fun insert(student: Student): Long {
        return studentDao.insertStudent(student)
    }

    // Cap nhat sinh vien
    suspend fun update(student: Student) {
        studentDao.updateStudent(student)
    }

    // Xoa sinh vien
    suspend fun delete(student: Student) {
        studentDao.deleteStudent(student)
    }

    // Xoa sinh vien theo ID
    suspend fun deleteById(id: Int) {
        studentDao.deleteStudentById(id)
    }

    // Tim sinh vien theo ma sinh vien
    suspend fun getStudentByCode(code: String): Student? {
        return studentDao.getStudentByCode(code)
    }
}
