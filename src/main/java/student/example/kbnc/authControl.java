package student.example.kbnc;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
@RequestMapping("/auth")
public class authControl {

    @Autowired
    private stuService service;

    @Autowired
    private Sturepo repo;

    @PostMapping("/register")
    public String getRegister(@RequestBody StuEntity reg) {
        
        
        return service.setRegister(reg);
    }


    @PostMapping("/login")
    public String postLogin(@RequestBody StuEntity reg) {

        
        
        boolean pre= service.getLogin(reg);
        if(pre){

            StuEntity obj=repo.findByName(reg.getName()).orElse(null);
            if(obj!=null){
            return jwtUtil.generateToken(obj);
            }
            return "invalid!!";

        }
        return "invalid credentials! ";
    }
    
    
    
    
}
