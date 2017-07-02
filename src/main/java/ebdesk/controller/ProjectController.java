package ebdesk.controller;

import ebdesk.model.Project;
import ebdesk.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by asuss on 5/26/2017.
 */
@Controller
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @RequestMapping(value = "/viewAllProjects",method = RequestMethod.GET)
    public String viewAllProjects(Model m, HttpSession s) {

        return projectService.viewAllProjects(m,s);
    }

    @RequestMapping(value = "/viewProject/{id_project}",method = RequestMethod.GET)
    public String viewProject(@PathVariable String id_project,Model m,HttpSession s){
        int id = Integer.parseInt(id_project);
        return projectService.viewProject(m,id,s);
    }

    @RequestMapping(value = "/viewMyProjects/{id_user}",method = RequestMethod.GET)
    public String viewMyProjects(@PathVariable String id_user,Model m,HttpSession s){
        int id = Integer.parseInt(id_user);
        return projectService.viewMyProjects(m,id,s);
    }
    
    @RequestMapping(value = "/viewPercentages/{id_project}",method = RequestMethod.GET)
    public String viewPercentages(@PathVariable String id_project,Model m,HttpSession s){
        int id = Integer.parseInt(id_project);
        return projectService.viewPercentage(m,id,s);
    }
    @RequestMapping(value = "/viewPercentage",method = RequestMethod.GET)
    public String viewPercentage(Model m, HttpSession s) {

        return projectService.viewAllPercentages(m,s);
    }
    

    @RequestMapping(value = "/addNewProject",method = RequestMethod.GET)
    public String addNewProject(Model m, HttpSession s){
        return projectService.viewNewProject(m,s);
    }



    @RequestMapping(value = "/addNewProject",method = RequestMethod.POST)
    public String processNewProject(Model m, HttpSession s, @RequestParam String projectname ,@RequestParam int  projectsize, @RequestParam int projectprice,
                                    @RequestParam int projectwaktu_1, @RequestParam String projectwaktu_2, @RequestParam int projectidleader){

        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        Date dateobj = new Date();

        Project pro = new Project();
        pro.setPrice(projectprice);
        pro.setName(projectname);
        pro.setSize(projectsize);
        pro.setStatus(1);
        pro.setTime(projectwaktu_1 + " " + projectwaktu_2);
        pro.setStart_time(df.format(dateobj));

        return projectService.postNewProject(m,s,pro,projectidleader);
    }

    @RequestMapping(value = "/updateProjectSettings/{id_project}",method = RequestMethod.POST)
    public String updateProjectSettings(Model m, HttpSession s,@PathVariable String id_project, @RequestParam String projectname ,@RequestParam int  projectsize, @RequestParam int projectprice,
                                    @RequestParam int projectwaktu_1, @RequestParam String projectwaktu_2, @RequestParam int projectidleader, @RequestParam int projectidleaderbefore){
        int project_id = Integer.parseInt(id_project);
        Project pro = new Project();
        pro.setId(project_id);
        pro.setPrice(projectprice);
        pro.setName(projectname);
        pro.setSize(projectsize);
        pro.setStatus(1);
        pro.setTime(projectwaktu_1 + " " + projectwaktu_2);
        return projectService.updateProjectSetting(m,s,pro,projectidleader,projectidleaderbefore);
    }

    @RequestMapping(value = "/updateProjectSkills/{id_project}",method = RequestMethod.POST)
    public String updateProjectSkills(Model m, HttpSession s,@PathVariable String id_project, @RequestParam int projectidskill){
        int project_id = Integer.parseInt(id_project);

        return projectService.updateProjectSkill(m,s,project_id,projectidskill);
    }

    @RequestMapping(value = "/updateProjectMembers/{id_project}",method = RequestMethod.POST)
    public String updateProjectMembers(Model m, HttpSession s,@PathVariable String id_project, @RequestParam int projectidmember){
        int project_id = Integer.parseInt(id_project);

        return projectService.updateProjectMember(m,s,project_id,projectidmember);
    }

    @RequestMapping(value = "/deleteUserFromProject/{id_project}",method = RequestMethod.POST)
    public String deleteProjectMember(Model m, HttpSession s,@PathVariable String id_project, @RequestParam String id_user){
        int project_id = Integer.parseInt(id_project);
        int user_id = Integer.parseInt(id_user);

        return projectService.deleteProjectMember(m,s,project_id,user_id);
    }

    @RequestMapping(value = "/deleteSkillFromProject/{id_project}",method = RequestMethod.POST)
    public String deleteProjectSkill(Model m, HttpSession s,@PathVariable String id_project, @RequestParam String id_skill){
        int project_id = Integer.parseInt(id_project);
        int skill_id = Integer.parseInt(id_skill);

        return projectService.deleteProjectSkill(m,s,project_id,skill_id);
    }
}
