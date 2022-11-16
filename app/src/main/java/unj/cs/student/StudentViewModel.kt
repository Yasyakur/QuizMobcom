package unj.cs.student

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class StudentViewModel : ViewModel() {
    private val _studentList: MutableLiveData<List<Student>>()

    val studentList: LiveData<List<Student>> = _studentList

    fun addStudent(student: Student){
        _studentList.value!!.add(student)
    }

//    fun updateStudent(index: Int, student: Student){
//        _studentList.value!![index] = student
//}

    fun setStudent(student: Student, dao: StudentDao){
        viewModelScope.launch {
            dao.update(student)
            _studentList.value = dao.getAll()
        }
    }
}