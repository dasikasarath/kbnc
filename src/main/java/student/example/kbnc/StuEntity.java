package student.example.kbnc;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class StuEntity {

    @Id
    @GeneratedValue
    private int rollno;
    private String name;
    private String password;
    private String college;
    private String branch;
    private int age;
    private int marks;
    private String role="USER";

    public StuEntity(){}

    public int getRollno(){return rollno;}
    public String getName(){return name;}
    public String getPassword(){return password;}
    public String getCollege(){return college;}
    public String getBranch(){return branch;}
    public int getAge(){return age;}
    public int getMarks(){return marks;}
    public String getRole(){return role;}


    public void setName(String name){this.name=name;}
    public void setPassword(String password){this.password=password;}
    public void setCollege(String college){this.college=college;}
    public void setBranch(String branch){this.branch=branch;}
    public void setAge(int age){this.age=age;}
    public void setMarks(int marks){this.marks=marks;}
    public void setRole(String role){this.role=role;}
    
}
