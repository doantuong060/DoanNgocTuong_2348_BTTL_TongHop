
package com.ute.studentmvvm_doanngoctuong.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ute.studentmvvm_doanngoctuong.model.Student
import com.ute.studentmvvm_doanngoctuong.repository.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentViewModel(
    private val repository: StudentRepository
) : ViewModel() {

    // Chuyen Flow thanh LiveData
    val allStudents: LiveData<List<Student>> =
        repository.allStudents.asLiveData(Dispatchers.IO)

    // Thong bao trang thai
    private val _statusMessage = MutableLiveData<String>()
    val statusMessage: LiveData<String>
        get() = _statusMessage

    // Them sinh vien
    fun insert(student: Student) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.insert(student)
                _statusMessage.postValue(
                    "Đã thêm sinh viên: ${student.fullName}"
                )
            } catch (e: Exception) {
                _statusMessage.postValue(
                    "Lỗi khi thêm: ${e.message}"
                )
            }
        }

    // Cap nhat sinh vien
    fun update(student: Student) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.update(student)
                _statusMessage.postValue(
                    "Đã cập nhật sinh viên: ${student.fullName}"
                )
            } catch (e: Exception) {
                _statusMessage.postValue(
                    "Lỗi khi cập nhật: ${e.message}"
                )
            }
        }

    // Xoa sinh vien
    fun delete(student: Student) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.delete(student)
                _statusMessage.postValue(
                    "Đã xóa sinh viên: ${student.fullName}"
                )
            } catch (e: Exception) {
                _statusMessage.postValue(
                    "Lỗi khi xóa: ${e.message}"
                )
            }
        }

    // Xoa sinh vien theo ID
    fun deleteById(id: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.deleteById(id)
                _statusMessage.postValue("Đã xóa sinh viên")
            } catch (e: Exception) {
                _statusMessage.postValue(
                    "Lỗi khi xóa: ${e.message}"
                )
            }
        }
}
