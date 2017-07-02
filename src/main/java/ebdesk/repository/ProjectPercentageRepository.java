/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ebdesk.repository;

import ebdesk.model.Percentage;
import ebdesk.model.Project;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author ACER
 */
public interface ProjectPercentageRepository extends JpaRepository<Percentage, Integer>{
    @Query(value="SELECT * FROM PERCENTAGE p JOIN PROJECT up ON p.id_project = up.id WHERE up.id=?1",nativeQuery=true)
     public List<Percentage> findAllbyProjects(int id);
     
}
