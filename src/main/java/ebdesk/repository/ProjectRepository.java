package ebdesk.repository;

import ebdesk.model.Project;
import ebdesk.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by asuss on 6/2/2017.
 */
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    @Query(value="SELECT * FROM PROJECT p JOIN USER_PROJECT up ON p.`id`=up.`id_project` JOIN USER u ON up.`id_user`=u.`id`WHERE u.id=?1",nativeQuery=true)
     public List<Project> findAllByUsersId(int id);



}
