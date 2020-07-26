package demo.controller;

import demo.entity.Student;
import demo.entity.StudentNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


//Exception Handlers in this REST controller works only for this controller
@RestController
@RequestMapping("/test")
public class DemoController {
    private List<Student> studentList;

    @PostConstruct
    public void loadData() {
        studentList = new ArrayList<Student>();
        studentList.add(new Student("Jake", "Peralta"));
        studentList.add(new Student("Amy", "Santiago"));
        studentList.add(new Student("Boyle", "Jake"));
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello-World";
    }

    @GetMapping("students")
    public List<Student> getStudents() {
        return studentList;
    }

    @GetMapping("/students/{studentNo}")
    public Student getStudent(@PathVariable int studentNo) {
        if (studentNo < 0 || studentNo >= studentList.size())
            throw new StudentNotFoundException("Student with ID : " + studentNo + " not found");
        return studentList.get(studentNo);
    }

    /*//Handle a specific exception
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException exc) {
        StudentErrorResponse error = new StudentErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exc.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    //Handle generic exceptions
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleGenericException(Exception exc) {
        StudentErrorResponse error = new StudentErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exc.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }*/
}
