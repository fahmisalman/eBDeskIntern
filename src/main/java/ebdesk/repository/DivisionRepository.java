/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ebdesk.repository;

/**
 *
 * @author ACER
 */
import ebdesk.model.Division;
import ebdesk.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DivisionRepository extends JpaRepository<Division, Integer> {
    @Query(value = "SELECT * FROM DIVISION d JOIN USER u ON u.division_id WHERE u.id=?1", nativeQuery = true)
    public Division findByUser(int id);
}
