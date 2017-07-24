/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ebdesk.model;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;
/**
 *
 * @author ACER
 */
@Entity
@Table(name = "percentage")
public class Percentage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @NotNull
    private int percentage_1;
    
    @NotNull
    private int percentage_2;
    
    @NotNull
    private int percentage_3;
    
    @NotNull
    private int percentage_4;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_project")
    private Project project;
    
    

    public Percentage() {
    }

    public Percentage(int id, int percentage_1, int percentage_2, int percentage_3, int percentage_4, Project project) {
        this.id = id;
        this.percentage_1 = percentage_1;
        this.percentage_2 = percentage_2;
        this.percentage_3 = percentage_3;
        this.percentage_4 = percentage_4;
        this.project = project;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPercentage_1() {
        return percentage_1;
    }

    public void setPercentage_1(int percentage_1) {
        this.percentage_1 = percentage_1;
    }

    public int getPercentage_2() {
        return percentage_2;
    }

    public void setPercentage_2(int percentage_2) {
        this.percentage_2 = percentage_2;
    }

    public int getPercentage_3() {
        return percentage_3;
    }

    public void setPercentage_3(int percentage_3) {
        this.percentage_3 = percentage_3;
    }

    public int getPercentage_4() {
        return percentage_4;
    }

    public void setPercentage_4(int percentage_4) {
        this.percentage_4 = percentage_4;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    
    

    
    
    
    
    
    
    
    
    
    
    
}
