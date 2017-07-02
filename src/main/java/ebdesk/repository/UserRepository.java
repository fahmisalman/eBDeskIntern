package ebdesk.repository;

import ebdesk.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by asuss on 5/27/2017.
 */
public interface UserRepository  extends JpaRepository<User, Integer> {
    public User findByEmail(String email);
    public List<User> findAllByRole(int role);

    @Query(value="SELECT * FROM USER u JOIN USER_PROJECT up ON u.`id`=up.`id_user` JOIN PROJECT p ON up.`id_project`=p.`id`WHERE p.id=?1",nativeQuery=true)
    public List<User> findAllByProjectsId(int id);

    @Query(value="SELECT CONCAT(FIRST_NAME , ' ' ,LAST_NAME) AS NAME FROM USER u  JOIN USER_PROJECT up ON u.id = up.id_user  JOIN PROJECT p ON up.id_project = p.id WHERE u.role= ?1 AND p.id = ?2",nativeQuery=true)
    public List<User> findAllByRoleAndProjectId(@Param("role") int role,@Param("idProject")  int idProject);

    @Query(value=" SELECT * FROM USER u LEFT JOIN USER_PROJECT up ON u.id = up.id_user WHERE u.role =?1 AND NOT EXISTS (SELECT * FROM USER_PROJECT up WHERE u.id = up.`id_user` AND up.id_project=?2)",nativeQuery=true)
    public List<User> findAllByRoleNotExistInUserProject(int role,int projectID);

    //buat cari leader
    @Query(value="SELECT * FROM USER u  JOIN USER_PROJECT up ON u.id = up.id_user  JOIN PROJECT p ON up.id_project = p.id WHERE up.roles= ?1 AND p.id = ?2",nativeQuery=true)
    public User findByUserProjectRoleAndProjectId(@Param("roles") int role,@Param("idProject")  int idProject);

}
