package ebdesk.controller;

import ebdesk.model.User;
import ebdesk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by asuss on 5/26/2017.
 */
@Controller
@Scope("session")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/viewAllEmployees",method = RequestMethod.GET)
    public String viewAllEmployees(Model m,HttpSession s){
        return userService.viewAllEmployees(m,s);
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){
        return userService.viewLogin();
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String tologin(@RequestParam String email, @RequestParam String password, Model m,HttpSession s){
        System.out.println("PAssword nya = "+password);
        return userService.goLogin(email,password,m,s);
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String getRegister(){
        return userService.viewRegister();
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)

    public String postRegister(Model m ,@RequestParam String email,  @RequestParam String password,@RequestParam String first_name,@RequestParam String last_name,@RequestParam String phone){
        User u = new User(email,password,first_name,last_name,phone,4);
        return  userService.goRegister(u,m);
    }

    @RequestMapping(value = "/viewEmployee/{id_user}",method = RequestMethod.GET)
    public String viewEmployee(@PathVariable String id_user, Model m, HttpSession s){
        int id = Integer.parseInt(id_user);
        System.out.println("ID USER = "+id);
        return userService.viewEmployee(m,id,s);
    }

}
