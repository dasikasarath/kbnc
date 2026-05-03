package student.example.kbnc;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface Sturepo extends JpaRepository<StuEntity,Integer>{
    Optional<StuEntity>findByName(String name);
}
