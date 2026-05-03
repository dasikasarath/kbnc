package student.example.kbnc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.security.core.Authentication;

import java.util.*;

@Service
public class stuService {

    @Autowired
    private Sturepo repo;

    public String setRegister(StuEntity reg){
        String reqname=reg.getName();
        Optional<StuEntity> dbname=repo.findByName(reqname);
        if(dbname.isPresent()){
            return "user with same username already exist";
        }
       else{
        reg.setRole("USER");//astadigbandam//
        repo.save(reg);
        return "regestred successfully";
       }
        

    }



    public boolean getLogin(StuEntity reg){
        String reqname=reg.getName();
    

        Optional<StuEntity> dbname=repo.findByName(reqname);
        if(dbname.isPresent()){
            return dbname.get().getPassword().equals(reg.getPassword());
        }
        return false;

    }

  public String setAdmins(String username){
    StuEntity dbdata =repo.findByName(username).orElse(null);
    if(dbdata!=null && !dbdata.getRole().equals("ADMIN") ){
        dbdata.setRole("ADMIN");
        repo.save(dbdata);
        return "role changed to admin successfully!";
     }
     return "failed to change role to admin!";

  }



     public String setremoveadmin(String username){
    StuEntity dbdata =repo.findByName(username).orElse(null);
    if(dbdata!=null && dbdata.getRole().equals("ADMIN") && username!="dasika"){
        dbdata.setRole("USER");
        repo.save(dbdata);
        return "role changed to user successfully! and removed as admin";
     }
     return "failed to change role to User!";

  }

public boolean setIsSame(int rollno){
     Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
    String dbname= authentication.getName();
    StuEntity obj =repo.findByName(dbname).orElse(null);
    if(obj.getRollno()!=rollno){
        return false;

    }
    return true;
}



    
}
