package ebdesk.service;

import ebdesk.model.User;
import ebdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by asuss on 5/27/2017.
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    public String viewAllEmployees(Model model,HttpSession session ){
        ArrayList<User> userList = (ArrayList<User>) userRepo.findAll();
//        ArrayList<User> userLeaderList = new ArrayList<>();
        model.addAttribute("users", userList);
        model.addAttribute("user",session.getAttribute("user"));
        return "employees/view_all_employees";
    }

    public String viewLogin(){
        return"index/login";
    }

    public String goLogin(String email, String password, Model model,HttpSession session ){
        BCryptPasswordEncoder passwordDecoder = new BCryptPasswordEncoder();
//        if((userRepo.findByEmail(email)!=null)&&(userRepo.findByEmail(email).getPassword().equals(password))){
        if((userRepo.findByEmail(email)!=null)&&(passwordDecoder.matches(password,userRepo.findByEmail(email).getPassword()))){
            session.setAttribute("user",userRepo.findByEmail(email));
            model.addAttribute("user",session.getAttribute("user"));
            return "index/index";
        }else{
            model.addAttribute("salahPassword", "salah");

            return "index/login";
        }
    }

    public String viewRegister(){
        return "index/register";
    }

    public String goRegister(User user,Model model){
        if(userRepo.findByEmail(user.getEmail())==null){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//            System.out.println("Pass si baru = "+user.getPassword());
//            System.out.println("INIIIIII"+passwordEncoder.encode(user.getPassword()));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(3);
            userRepo.save(user);
            model.addAttribute("msg","Register Success");
            model.addAttribute("namaRe", user.getFirst_name());
            model.addAttribute("salahPassword", "buat");
            System.out.println("HEHEHEHEHEHEHHEHEHEHEHHE");
            return "index/login";
        }else{
            model.addAttribute("msg","Email already registered");
            return "index/register";
        }
    }

    public String viewEmployee(Model model, int id, HttpSession session) {
        model.addAttribute("userPilih",userRepo.findOne(id));
        model.addAttribute("user",session.getAttribute("user"));
        return "employees/profile";
    }


}
