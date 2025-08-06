
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
    private List<String> subjects;

    private final StudentDAO studentDAO = new StudentDAO();

    @PostConstruct
    public void init() {
        classSubjectsMap = new HashMap<>();
        classSubjectsMap.put("Science", Arrays.asList("Physics", "Chemistry", "Biology"));
        classSubjectsMap.put("Commerce", Arrays.asList("Accounting", "Business", "Economics"));
        classSubjectsMap.put("Arts", Arrays.asList("History", "Geography", "Sociology"));

        classes = new ArrayList<>(classSubjectsMap.keySet());
        subjects = new ArrayList<>();

        students = studentDAO.findAll();
    }

    public void onClassChange() {
        subjects = student.getStudentClass() != null ? classSubjectsMap.get(student.getStudentClass()) : new ArrayList<>();
    }

    public void saveStudent() {
        studentDAO.save(student);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Student Saved"));
        student = new Student();
        students = studentDAO.findAll();
    }

    public void updateStudent() {
        studentDAO.update(selectedStudent);
        students = studentDAO.findAll();
    }

    public void deleteStudent(Student s) {
        studentDAO.delete(s);
        students = studentDAO.findAll();
    }

    // Getters and Setters

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Student getSelectedStudent() {
        return selectedStudent;
    }

    public void setSelectedStudent(Student selectedStudent) {
        this.selectedStudent = selectedStudent;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    public Map<String, List<String>> getClassSubjectsMap() {
        return classSubjectsMap;
    }

    public void setClassSubjectsMap(Map<String, List<String>> classSubjectsMap) {
        this.classSubjectsMap = classSubjectsMap;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }
}
