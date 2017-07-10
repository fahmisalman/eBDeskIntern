package ebdesk.service;

import ebdesk.model.*;
import ebdesk.repository.ProjectPercentageRepository;
import ebdesk.repository.ProjectRepository;
import ebdesk.repository.SkillRepository;
import ebdesk.repository.UserProjectRepository;
import ebdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by asuss on 6/2/2017.
 */

@Service
public class ProjectService {

    @Autowired
    ProjectRepository projectRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    SkillRepository skillRepo;
    @Autowired
    UserProjectRepository upRepo;
    @Autowired
    ProjectPercentageRepository percentRepo;
    

    public String viewAllProjects(Model model, HttpSession session){

        ArrayList<Project> projectList = (ArrayList<Project>) projectRepo.findAll();
//        ArrayList<User> userLeaderList = new ArrayList<>();
        model.addAttribute("projects", projectList);
        model.addAttribute("user",session.getAttribute("user"));
////        model.addAttribute("leader",userRepo.findByRoleAndProjectId(3,1));
//        for(int i = 0 ; i<projectList.size();i++){
////            User u = userRepo.findAllByRoleAndProjectId(3,projectList.get(i).getId());
//            User u = userRepo.findAllByRoleAndProjectId(3,projectList.get(i).getId());
//            userLeaderList.add(u);
//        }
//        model.addAttribute("leaders",userLeaderList);

        return "projects/view_all_projects";
    }

    public String viewMyProjects(Model model,int id ,HttpSession session){
        model.addAttribute("projects", projectRepo.findAllByUsersId(id));
        model.addAttribute("user",session.getAttribute("user"));
        return "projects/view_all_projects";
    }

    public String viewNewProject(Model model, HttpSession session){
        model.addAttribute("user",session.getAttribute("user"));
        model.addAttribute("users",userRepo.findAllByRole(3));
        return "projects/new_project";
    }
    public String viewAllPercentages(Model model, HttpSession session){

        ArrayList<Project> projectList = (ArrayList<Project>) projectRepo.findAll();
//        ArrayList<User> userLeaderList = new ArrayList<>();
        model.addAttribute("projects", projectList);
        model.addAttribute("user",session.getAttribute("user"));
////        model.addAttribute("leader",userRepo.findByRoleAndProjectId(3,1));
//        for(int i = 0 ; i<projectList.size();i++){
////            User u = userRepo.findAllByRoleAndProjectId(3,projectList.get(i).getId());
//            User u = userRepo.findAllByRoleAndProjectId(3,projectList.get(i).getId());
//            userLeaderList.add(u);
//        }
//        model.addAttribute("leaders",userLeaderList);

        return "projects/view_percentage";
    }
    public String viewPercentages(Model model, HttpSession session){

        ArrayList<Project> projectList = (ArrayList<Project>) projectRepo.findAll();
        ArrayList<Percentage> percentList = (ArrayList<Percentage>) percentRepo.findAll();
//        ArrayList<User> userLeaderList = new ArrayList<>();
        model.addAttribute("projects", projectList);
        model.addAttribute("percentages", percentList);
        model.addAttribute("user",session.getAttribute("user"));
////        model.addAttribute("leader",userRepo.findByRoleAndProjectId(3,1));
//        for(int i = 0 ; i<projectList.size();i++){
////            User u = userRepo.findAllByRoleAndProjectId(3,projectList.get(i).getId());
//            User u = userRepo.findAllByRoleAndProjectId(3,projectList.get(i).getId());
//            userLeaderList.add(u);
//        }
//        model.addAttribute("leaders",userLeaderList);

        return "projects/view_all_percentage";
    }
    public String viewPercentage(Model model,int id, HttpSession session){
        model.addAttribute("project",projectRepo.findOne(id));
        model.addAttribute("leader",userRepo.findByUserProjectRoleAndProjectId(1,id));
        model.addAttribute("all_users",userRepo.findAllByRole(3));
        model.addAttribute("users",userRepo.findAllByRoleNotExistInUserProject(3,id));
        model.addAttribute("members",userRepo.findAllByProjectsId(id));
        model.addAttribute("requiredskills",skillRepo.findAllByProjectsId(id));
        model.addAttribute("skills",skillRepo.findAllNotExistInProjectSkills());
        model.addAttribute("percentages",percentRepo.findAllbyProjects(id));
        model.addAttribute("user",session.getAttribute("user"));
        return "projects/view_percentages";
    }
    
    

    public String postNewProject(Model model, HttpSession session, Project project, int idLeader){
        User user = userRepo.findOne(idLeader);
        project.setUserProjects(new HashSet<UserProject>());

        UserProject userProject = new UserProject();
        

        userProject.setUser(user);
        userProject.setProject(project);
        userProject.setRoles(1);

        project.setCurrent(1);
        project.getUserProjects().add(userProject);
        
        Percentage ps = new Percentage();
        ps.setProjects(new HashSet<Project>());
        ps.setPercentage_1(0);
        ps.setPercentage_2(0);
        ps.setPercentage_3(0);
        ps.setPercentage_4(0);
        ps.getProjects().add(project);
        project.setPercentage(ps);
        projectRepo.save(project);
        percentRepo.save(ps);
       

        model.addAttribute("projects", projectRepo.findAll());
        model.addAttribute("user",session.getAttribute("user"));
        return "projects/view_all_projects";
    }


    public String viewProject(Model model, int id, HttpSession session) {
        model.addAttribute("project",projectRepo.findOne(id));
        model.addAttribute("leader",userRepo.findByUserProjectRoleAndProjectId(1,id));
        model.addAttribute("all_users",userRepo.findAllByRole(3));
        model.addAttribute("users",userRepo.findAllByRoleNotExistInUserProject(3,id));
        model.addAttribute("members",userRepo.findAllByProjectsId(id));
        model.addAttribute("requiredskills",skillRepo.findAllByProjectsId(id));
        model.addAttribute("skills",skillRepo.findAllNotExistInProjectSkills());
        model.addAttribute("percentages",percentRepo.findAllbyProjects(id));
        model.addAttribute("user",session.getAttribute("user"));
        return "projects/view_project";
    }

    public String updateProjectSetting(Model model, HttpSession session, Project project, int idLeader,int idLeaderBefore) {

        User before = userRepo.findOne(idLeaderBefore);
        User after = userRepo.findOne(idLeader);
        Project pro = projectRepo.findOne(project.getId());

        UserProject delete = new UserProject();
        delete.setUser(before);
        delete.setProject(pro);
        //asumsi project leader sebelum di delete
        delete.setRoles(1);
        pro.setCurrent(pro.getCurrent()-1);
        upRepo.delete(delete);

        //asumsi project leader sebelum di jadiin member biasa
//        delete.setRoles(2);
//        upRepo.save(delete);

        int check1 = upRepo.findAllByProjectId(pro.getId()).size();
        UserProject add = new UserProject();
        add.setUser(after);
        add.setProject(pro);
        add.setRoles(1);
        upRepo.save(add);
        int check2 = upRepo.findAllByProjectId(pro.getId()).size();

        if(check1!=check2){
            pro.setCurrent(pro.getCurrent()+1);
        }
        projectRepo.save(pro);

        model.addAttribute("project",projectRepo.findOne(project.getId()));
        model.addAttribute("leader",userRepo.findByUserProjectRoleAndProjectId(1,project.getId()));
        model.addAttribute("all_users",userRepo.findAllByRole(3));
        model.addAttribute("users",userRepo.findAllByRoleNotExistInUserProject(3,project.getId()));
        model.addAttribute("members",userRepo.findAllByProjectsId(project.getId()));
        model.addAttribute("requiredskills",skillRepo.findAllByProjectsId(project.getId()));
        model.addAttribute("skills",skillRepo.findAllNotExistInProjectSkills());
        model.addAttribute("projects", projectRepo.findAll());
        model.addAttribute("user",session.getAttribute("user"));
        return "projects/view_project";

    }
    public String updateProjectSetting2(Model model, HttpSession session, int project_id, int percentage1,int percentage2,int percentage3,int percentage4){
        Set<Project> projects;
        Percentage percentage = percentRepo.findAllbyProjects(project_id).get(0);
        Project p = projectRepo.findOne(project_id);
        percentage.setPercentage_1(percentage1);
        percentage.setPercentage_2(percentage2);
        percentage.setPercentage_3(percentage3);
        percentage.setPercentage_4(percentage4);
        percentage.getProjects().add(p);
        p.setPercentage(percentage);
        
        
        percentRepo.save(percentage);
        model.addAttribute("project",projectRepo.findOne(p.getId()));
        model.addAttribute("leader",userRepo.findByUserProjectRoleAndProjectId(1,p.getId()));
        model.addAttribute("all_users",userRepo.findAllByRole(3));
        model.addAttribute("users",userRepo.findAllByRoleNotExistInUserProject(3,p.getId()));
        model.addAttribute("members",userRepo.findAllByProjectsId(p.getId()));
        model.addAttribute("requiredskills",skillRepo.findAllByProjectsId(p.getId()));
        model.addAttribute("skills",skillRepo.findAllNotExistInProjectSkills());
        model.addAttribute("percentages",percentRepo.findAllbyProjects(p.getId()));
        model.addAttribute("user",session.getAttribute("user"));
        return "projects/view_project";
    }

    public String updateProjectSkill(Model model, HttpSession session, int project_id, int projectidskill) {
        Set<Skill> skills;
        Set<Project> projects;
        Project p = projectRepo.findOne(project_id);
        if(p.getProject_skills()!=null){

            skills = p.getProject_skills();

        }else{

            skills = new HashSet<Skill>();
        }
        skills.add(skillRepo.findOne(projectidskill));
        p.setProject_skills(skills);

        Skill s = skillRepo.findOne(projectidskill);
        if(s.getProjects()!=null){

            projects = s.getProjects();
        }else{

            projects = new HashSet<Project>();
        }
        projects.add(projectRepo.findOne(project_id));
        s.setProjects(projects);

        projectRepo.save(p);
        skillRepo.save(s);


        model.addAttribute("project",projectRepo.findOne(p.getId()));
        model.addAttribute("leader",userRepo.findByUserProjectRoleAndProjectId(1,p.getId()));
        model.addAttribute("all_users",userRepo.findAllByRole(3));
        model.addAttribute("users",userRepo.findAllByRoleNotExistInUserProject(3,p.getId()));
        model.addAttribute("members",userRepo.findAllByProjectsId(p.getId()));
        model.addAttribute("skills",skillRepo.findAllNotExistInProjectSkills());
        model.addAttribute("requiredskills",skillRepo.findAllByProjectsId(p.getId()));
        model.addAttribute("projects", projectRepo.findAll());
        model.addAttribute("user",session.getAttribute("user"));
        return "projects/view_project";

    }

    public String updateProjectMember(Model model, HttpSession session, int project_id, int projectidmember) {



        User u = userRepo.findOne(projectidmember);
        Project p = projectRepo.findOne(project_id);

        if(p.getCurrent() < p.getSize()){
            UserProject userProject = new UserProject();

            userProject.setUser(u);
            userProject.setProject(p);
            userProject.setRoles(2);

            p.getUserProjects().add(userProject);
            p.setCurrent(p.getCurrent()+1);
            projectRepo.save(p);
        }else{
            model.addAttribute("projectFull",true);
        }



        model.addAttribute("project",projectRepo.findOne(p.getId()));
        model.addAttribute("leader",userRepo.findByUserProjectRoleAndProjectId(1,p.getId()));
        model.addAttribute("all_users",userRepo.findAllByRole(3));
        model.addAttribute("users",userRepo.findAllByRoleNotExistInUserProject(3,p.getId()));
        model.addAttribute("members",userRepo.findAllByProjectsId(p.getId()));
        model.addAttribute("skills",skillRepo.findAllNotExistInProjectSkills());
        model.addAttribute("requiredskills",skillRepo.findAllByProjectsId(p.getId()));
        model.addAttribute("projects", projectRepo.findAll());
        model.addAttribute("user",session.getAttribute("user"));
        return "projects/view_project";

    }

    public String deleteProjectMember(Model model, HttpSession session, int project_id, int user_id) {

        Project p = projectRepo.getOne(project_id);
        User u = userRepo.getOne(user_id);
        List<UserProject> userProject = upRepo.findAllByProjectId(project_id);
        for(int i = 0 ; i < userProject.size();i++){
            if (userProject.get(i).getUser().equals(u)){
                if(userProject.get(i).getRoles()!=1){
                    UserProject delete = new UserProject();
                    delete.setUser(u);
                    delete.setProject(p);
                    //asumsi project leader sebelum di delete
                    delete.setRoles(2);
                    p.setCurrent(p.getCurrent()-1);
                    upRepo.delete(delete);
                }else{
                    model.addAttribute("deleteFail",true);
                }
            }
        }
        projectRepo.save(p);
        userRepo.save(u);

        model.addAttribute("project",projectRepo.findOne(p.getId()));
        model.addAttribute("leader",userRepo.findByUserProjectRoleAndProjectId(1,p.getId()));
        model.addAttribute("all_users",userRepo.findAllByRole(3));
        model.addAttribute("users",userRepo.findAllByRoleNotExistInUserProject(3,p.getId()));
        model.addAttribute("members",userRepo.findAllByProjectsId(p.getId()));
        model.addAttribute("skills",skillRepo.findAllNotExistInProjectSkills());
        model.addAttribute("requiredskills",skillRepo.findAllByProjectsId(p.getId()));
        model.addAttribute("projects", projectRepo.findAll());
        model.addAttribute("user",session.getAttribute("user"));
        return "projects/view_project";


    }

    public String deleteProjectSkill(Model model, HttpSession session, int project_id, int skill_id) {

        Project p = projectRepo.getOne(project_id);
        Skill s = skillRepo.getOne(skill_id);

        p.getProject_skills().remove(s);
        s.getProjects().remove(p);

        projectRepo.save(p);
        skillRepo.save(s);

        model.addAttribute("project",projectRepo.findOne(p.getId()));
        model.addAttribute("leader",userRepo.findByUserProjectRoleAndProjectId(1,p.getId()));
        model.addAttribute("all_users",userRepo.findAllByRole(3));
        model.addAttribute("users",userRepo.findAllByRoleNotExistInUserProject(3,p.getId()));
        model.addAttribute("members",userRepo.findAllByProjectsId(p.getId()));
        model.addAttribute("skills",skillRepo.findAllNotExistInProjectSkills());
        model.addAttribute("requiredskills",skillRepo.findAllByProjectsId(p.getId()));
        model.addAttribute("projects", projectRepo.findAll());
        model.addAttribute("user",session.getAttribute("user"));
        return "projects/view_project";
    }
}
