import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.SpringWebPro.SpringWebPro.models.Users;
import com.SpringWebPro.SpringWebPro.repository.UserRepo;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    public Users Register(Users user)
    {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
}
