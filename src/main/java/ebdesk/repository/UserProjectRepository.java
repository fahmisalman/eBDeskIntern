package ebdesk.repository;

import ebdesk.model.UserProject;
import ebdesk.model.UserProjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by asuss on 6/6/2017.
 */
public interface UserProjectRepository extends JpaRepository<UserProject,UserProjectId> {
    @Query(value=" SELECT * FROM USER_PROJECT up JOIN PROJECT p ON up.id_project = p .id WHERE p.id=?1",nativeQuery=true)
    public List<UserProject> findAllByProjectId(int id);
}
