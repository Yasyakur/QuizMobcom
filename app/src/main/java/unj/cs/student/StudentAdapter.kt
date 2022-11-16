package unj.cs.student

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(val viewModel: StudentViewModel) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>(){

//    private val studentList: MutableList<Student> = viewModel.studentList.value!!
    private val viewModel: StudentViewModel = viewModel
    private val studentDao = StudentDatabase.getInstance(requireContext()).studentDao()
    init {

    }


    class StudentViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val idTextView : TextView = view.findViewById(R.id.student_id)
        val nameTextView : TextView = view.findViewById(R.id.student_name)
        val studentArea : ConstraintLayout = view.findViewById(R.id.clickable_area)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
//        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.student_view, parent, false)
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_view_student, parent, false)
        viewModel.loadStudent(StudentDao)
        return StudentViewHolder(layout)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        // val student: Student = dataset[position]
        val _id: Int? = viewModel.studentList.value!![position]._id
        val ids:String = viewModel.studentList.value!![position].ids
        val name:String = viewModel.studentList.value!![position].name
        holder.idTextView.text = ids
        holder.nameTextView.text = name
        holder.studentArea.setOnClickListener {
            val action: NavDirections = StudentListFragmentDirections.actionStudentListFragmentToStudentFormFragment(position, "Update Student")
            holder.view.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return viewModel.studentList.value!!.size
    }
}