
package com.ute.studentmvvm_doanngoctuong.network

import com.google.gson.annotations.SerializedName
import com.ute.studentmvvm_doanngoctuong.model.Student

data class StudentDto(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("fullName")
    val fullName: String,

    @SerializedName("studentCode")
    val studentCode: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("gpa")
    val gpa: Double,

    @SerializedName("major")
    val major: String = "Công Nghệ Thông Tin"
)

// Chuyen doi du lieu JSON sang Entity Room
fun StudentDto.toEntity(): Student {
    return Student(
        id = id ?: 0,
        fullName = fullName,
        studentCode = studentCode,
        email = email,
        gpa = gpa,
        major = major
    )
}
