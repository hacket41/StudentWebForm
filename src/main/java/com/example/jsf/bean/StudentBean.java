
package com.example.jsf.bean;

import com.example.jsf.dao.StudentDAO;
import com.example.jsf.entity.Student;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.*;

@ManagedBean(name = "studentBean")
@ViewScoped
public class StudentBean implements Serializable {

    private Student student = new Student();
    private Student selectedStudent;
    private List<Student> students;

    private List<String> classes;
    private Map<String, List<String>> classSubjectsMap;
    private List<String> subjects;        // for add form
    private List<String> editSubjects;    // for edit dialog

    private final StudentDAO studentDAO = new StudentDAO();

    @PostConstruct
    public void init() {
        classSubjectsMap = new HashMap<>();
        classSubjectsMap.put("Science", Arrays.asList("Physics", "Chemistry", "Biology"));
        classSubjectsMap.put("Commerce", Arrays.asList("Accounting", "Business", "Economics"));
        classSubjectsMap.put("Arts", Arrays.asList("History", "Geography", "Sociology"));

        classes = new ArrayList<>(classSubjectsMap.keySet());
        subjects = new ArrayList<>();
        editSubjects = new ArrayList<>();

        students = studentDAO.findAll();

        selectedStudent = new Student(); // initialize to prevent null
    }

    public Student getSelectedStudent() {
        if (selectedStudent == null) {
            selectedStudent = new Student();
        }
        return selectedStudent;
    }

    public void setSelectedStudent(Student selectedStudent) {
        this.selectedStudent = selectedStudent;
        onClassChangeEdit();  // populate editSubjects immediately when set
    }

    public void onClassChange() {
        if (student.getStudentClass() != null) {
            subjects = classSubjectsMap.get(student.getStudentClass());
        } else {
            subjects = new ArrayList<>();
        }
        student.setSubject(null);  // reset subject when class changes
    }

    public void onClassChangeEdit() {
        if (selectedStudent != null && selectedStudent.getStudentClass() != null) {
            editSubjects = classSubjectsMap.get(selectedStudent.getStudentClass());
        } else {
            editSubjects = new ArrayList<>();
        }
        if (selectedStudent != null) {
            selectedStudent.setSubject(null);  // reset subject when class changes
        }
    }

    public void saveStudent() {
        studentDAO.save(student);
        students = studentDAO.findAll();

        student = new Student(); // reset form
        student.setStudentClass(null);
        student.setSubject(null);

        subjects = new ArrayList<>();
    }

    public void updateStudent() {
        if (selectedStudent == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No student selected to update", ""));
            return;
        }

        studentDAO.update(selectedStudent);
        students = studentDAO.findAll();

        selectedStudent = new Student();  // reset selectedStudent after update
        editSubjects = new ArrayList<>();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Student updated successfully"));
    }

    public void deleteStudent(Student s) {
        studentDAO.delete(s);
        students = studentDAO.findAll();
        if (selectedStudent != null && selectedStudent.equals(s)) {
            selectedStudent = new Student();
        }
    }

    // Getters and Setters

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<String> getClasses() {
        return classes;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public List<String> getEditSubjects() {
        return editSubjects;
    }

    public Map<String, List<String>> getClassSubjectsMap() {
        return classSubjectsMap;
    }
}
