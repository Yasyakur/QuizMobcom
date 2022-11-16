package unj.cs.student

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import unj.cs.student.databinding.FragmentStudentFormBinding
import kotlin.properties.Delegates

class StudentFormFragment : Fragment() {
    companion object{
        const val INDEX = "index"
        const val BUTTON_TEXT = "button_text"
    }

    private var _binding: FragmentStudentFormBinding? = null
    private val binding get() = _binding!!
    private lateinit var buttonText: String
    private var index by Delegates.notNull<Int>()
    private val viewModel: StudentViewModel by activityViewModels()

    private val studentDao = StudentDatabase.getInstance(requireContext()).studentDao()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idParam = it.getString(ARG_ID)
            nameParam = it.getString(ARG_NAME)
            positionParam = it.getInt(ARG_POS)
            _idParam = it.getInt(ARG_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStudentFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = viewModel
        }
        val idEditLayout: TextInputLayout = binding.idTextLayout
        val nameEditLayout: TextInputLayout = binding.nameTextLayout
        val idEditText: EditText = binding.idTextEdit
        val nameEditText: EditText = binding.nameTextEdit
        val submitButton: Button = binding.submitButton
//        val listStudents = StudentAdapter.dataset

        // Get text from arguments
        submitButton.text = buttonText
        if (index != -1){
            idEditText.setText(viewModel.studentList.value!![index].ids)
            nameEditText.setText(viewModel.studentList.value!![index].name)
            submitButton.setOnClickListener {
                if(idEditText.text.toString() == "" || nameEditText.text.toString() == ""){
                    idEditLayout.error = "IDs can't be empty!"
                    nameEditLayout.error = "Name can't be empty!"
                    Toast.makeText(context, "IDs and Name can't be empty!", Toast.LENGTH_LONG).show()
                } else {
                    val student = Student(
                        idEditText.text.toString(),
                        nameEditText.text.toString()
                    )
                    // listStudents[index] = student
                    viewModel.updateStudent(index, student)
                    lifecycleScope.launch{
                        studentDao.insert(student)
                    }
                    val action: NavDirections = StudentFormFragmentDirections.actionStudentFormFragmentToStudentListFragment()
                    view.findNavController().navigate(action)

                    Toast.makeText(context, "Data was updated!", Toast.LENGTH_LONG).show()
                }

            }
        } else {
            submitButton.setOnClickListener {
                if(idEditText.text.toString() == "" || nameEditText.text.toString() == ""){
                    idEditLayout.error = "IDs can't be empty!"
                    nameEditLayout.error = "Name can't be empty!"
                    Toast.makeText(context, "IDs and Name can't be empty!", Toast.LENGTH_LONG).show()
                } else {
                    val student = Student(
                        idEditText.text.toString(),
                        nameEditText.text.toString()
                    )
                    // listStudents.add(student)
                    viewModel.addStudent(student)
                    val action: NavDirections = StudentFormFragmentDirections.actionStudentFormFragmentToStudentListFragment()
                    view.findNavController().navigate(action)
                    Toast.makeText(context, "${student.name} was added!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}