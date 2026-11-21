package com.ute.studentmvvm_doanngoctuong.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ute.studentmvvm_doanngoctuong.R
import com.ute.studentmvvm_doanngoctuong.data.AppDatabase
import com.ute.studentmvvm_doanngoctuong.databinding.FragmentStudentListBinding
import com.ute.studentmvvm_doanngoctuong.model.Student
import com.ute.studentmvvm_doanngoctuong.repository.StudentRepository
import com.ute.studentmvvm_doanngoctuong.ui.adapter.StudentAdapter
import com.ute.studentmvvm_doanngoctuong.viewmodel.StudentViewModel
import com.ute.studentmvvm_doanngoctuong.viewmodel.StudentViewModelFactory

class StudentListFragment : Fragment() {

    private var _binding: FragmentStudentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: StudentViewModel
    private lateinit var studentAdapter: StudentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentListBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = AppDatabase.getDatabase(requireContext())
        val repository = StudentRepository(database.studentDao())
        val factory = StudentViewModelFactory(repository)

        viewModel = ViewModelProvider(
            requireActivity(),
            factory
        )[StudentViewModel::class.java]

        setupRecyclerView()
        observeViewModel()
        setupListeners()
    }

    private fun setupRecyclerView() {
        studentAdapter = StudentAdapter(
            onStudentClick = { student: Student ->
                showStudentDetail(
                    student.fullName,
                    student.studentCode
                )
            },
            onDeleteClick = { student: Student ->
                showDeleteConfirmation(student.fullName) {
                    viewModel.delete(student)
                }
            }
        )

        binding.rvStudents.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = studentAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.allStudents.observe(viewLifecycleOwner) { students ->

            studentAdapter.submitList(students)

            binding.tvStudentCountBadge.text = getString(
                R.string.student_count,
                students.size
            )

            if (students.isEmpty()) {
                binding.rvStudents.visibility = View.GONE
                binding.layoutEmpty.visibility = View.VISIBLE
            } else {
                binding.rvStudents.visibility = View.VISIBLE
                binding.layoutEmpty.visibility = View.GONE
            }
        }

        viewModel.statusMessage.observe(viewLifecycleOwner) { message ->
            if (!message.isNullOrEmpty()) {
                Toast.makeText(
                    requireContext(),
                    message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupListeners() {
        binding.fabAdd.setOnClickListener {
            Toast.makeText(
                requireContext(),
                getString(R.string.add_student_coming_soon),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun showStudentDetail(
        fullName: String,
        studentCode: String
    ) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.student_detail_title))
            .setMessage(
                getString(
                    R.string.student_detail_message,
                    fullName,
                    studentCode
                )
            )
            .setPositiveButton(getString(R.string.close), null)
            .show()
    }

    private fun showDeleteConfirmation(
        fullName: String,
        onConfirm: () -> Unit
    ) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.delete_student_title))
            .setMessage(
                getString(R.string.delete_student_message, fullName)
            )
            .setNegativeButton(getString(R.string.cancel), null)
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                onConfirm()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
