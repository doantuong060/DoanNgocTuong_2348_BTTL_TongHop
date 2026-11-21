package com.ute.studentmvvm_doanngoctuong

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ute.studentmvvm_doanngoctuong.data.AppDatabase
import com.ute.studentmvvm_doanngoctuong.repository.StudentRepository
import com.ute.studentmvvm_doanngoctuong.ui.StudentListFragment
import com.ute.studentmvvm_doanngoctuong.viewmodel.StudentViewModel
import com.ute.studentmvvm_doanngoctuong.viewmodel.StudentViewModelFactory

class MainActivity : AppCompatActivity() {

    private val database by lazy { AppDatabase.getDatabase(this) }
    private val repository by lazy { StudentRepository(database.studentDao()) }
    private val viewModel: StudentViewModel by viewModels {
        StudentViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val container = findViewById<View>(R.id.fragment_container_view)
        ViewCompat.setOnApplyWindowInsetsListener(container) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, StudentListFragment())
                .commit()
        }
    }
}