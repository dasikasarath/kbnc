package student.example.kbnc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.*;




@RestController
public class StuControl {

    private final Sturepo sturepo;
    public StuControl(Sturepo sturepo){
        this.sturepo=sturepo;
    }

    @Autowired
    private stuService service;

     @Autowired
     private JavaMailSender mail; //basic mail di attachments di tarvata//


    @GetMapping("user/{rollno}/viewall")
    public List<StuEntity> getAllstudents(@PathVariable int rollno) {
      
        boolean is=service.setIsSame(rollno);
        if(!is){
            throw new RuntimeException("unable to access others data");
       
        }
        else{
             return sturepo.findAll();
        }
        
    }
    
   // @PostMapping("/addstudent")
    //public StuEntity postMethodName(@RequestBody StuEntity add) {

        
        
      //  return sturepo.save(add);
    //}

    @PutMapping("user/update/{id}")
    public String getUpdate(@PathVariable int id, @RequestBody StuEntity newobj){
        StuEntity obj=sturepo.findById(id).orElse(null);
          boolean is=service.setIsSame(id); //id ,rollno same //
        if(!is){
            throw new RuntimeException("unable to access others data");
       
        }
 
        else if(obj!=null && is){
            obj.setName(newobj.getName());
            obj.setCollege(newobj.getCollege());
            obj.setBranch(newobj.getBranch());
            obj.setMarks(newobj.getMarks());
            obj.setAge(newobj.getAge());
            sturepo.save(obj);
            return "student data updated sucessfully!";


        }
        return "data with your id doesnot exist!";


    }

    @DeleteMapping("user/delete/{id}")
    public String getDelete(@PathVariable int id){
        StuEntity obj=sturepo.findById(id).orElse(null);
         boolean is=service.setIsSame(id); //id ,rollno same //
        if(!is){
            throw new RuntimeException("unable to access others data");
       
        }

        
        else if(obj!=null){
            sturepo.delete(obj);
            return "student deleted successfully! ";
        }
        return "student not found!";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("admin/makeadmin/{username}")
    public String setAdmin(@PathVariable String username){
        return service.setAdmins(username);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("admin/removeadmin/{username}")
    public String setremoveaadmin(@PathVariable String username){
        return service.setremoveadmin(username);
    }

    //password login ki use cheyyali like forgot password //
    @PostMapping("user/mail")
    public String setMail(){
       SimpleMailMessage msg=new SimpleMailMessage();
       try{
        msg.setFrom("sarathdasika@gmail.com");
        msg.setTo("dasikasarath@gmail.com");
        msg.setSubject("hi");
        msg.setText("hello");

        mail.send(msg);

        return "message sent successfully";
       }
       catch(Exception e){
        return "faild to send";
       }



    }

    
    
}
