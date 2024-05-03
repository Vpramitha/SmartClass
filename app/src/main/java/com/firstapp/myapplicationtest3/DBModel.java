package com.firstapp.myapplicationtest3;

public class DBModel {
    private String StudentId;
    private String StudentName;
    private int StudentGrade;
    private String StudentClass;
    private String StudentContact;
    private String StudentPassword;

    public DBModel(String StudentId,String StudentName,int StudentGrade,String StudentClass,String StudentContact,String StudentPassword){
        this.StudentId = StudentId;
        this.StudentName = StudentName;
        this.StudentGrade = StudentGrade;
        this.StudentClass = StudentClass;
        this.StudentContact = StudentContact;
        this.StudentPassword = StudentPassword;
    }

    public String getStudentId(){
        return this.StudentId;
    }
    public void setStudentId(String StudentId){
        this.StudentId=StudentId;
    }

    public String getStudentName(){
        return this.StudentName;
    }
    public void setStudentName(String StudentName){
        this.StudentName=StudentName;
    }

    public int getStudentGrade(){
        return this.StudentGrade;
    }
    public void setStudentGrade(int StudentGrade){
        this.StudentGrade=StudentGrade;
    }

    public String getStudentClass(){
        return this.StudentClass;
    }
    public void setStudentClass(String StudentClass){
        this.StudentClass=StudentClass;
    }


    public String getStudentContact(){
        return this.StudentContact;
    }
    public void setStudentContact(String StudentContact){
        this.StudentContact=StudentContact;
    }
    public String getStudentPassword(){
        return this.StudentPassword;
    }
    public void setStudentPassword(String StudentPassword){
        this.StudentPassword=StudentPassword;
    }
}
