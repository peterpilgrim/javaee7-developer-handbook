/*******************************************************************************
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013,2014 by Peter Pilgrim, Addiscombe, Surrey, XeNoNiQUe UK
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPL v3.0
 * which accompanies this distribution, and is available at:
 * http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Developers:
 * Peter Pilgrim -- design, development and implementation
 *               -- Blog: http://www.xenonique.co.uk/blog/
 *               -- Twitter: @peter_pilgrim
 *
 * Contributors:
 *
 *******************************************************************************/

package je7hb.intro.xentracker.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * The type Project
 *
 * @author Peter Pilgrim (peter)
 */
@NamedQueries({
        @NamedQuery(name="Project.findAllProjects",
                query = "select p from Project p order by p.name"),
        @NamedQuery(name="Project.findProjectById",
                query = "select p from Project p where p.id = :id"),
        @NamedQuery(name="Project.findTaskById",
                query = "select t from Task t where t.id = :id"),
        @NamedQuery(name="Project.findTasksByProjectId",
                query = "select t from Project p, Task t " +
                        "where p.id = :id and t.project = p"),
})
@Entity
public class Project {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="PROJECT_ID") private Integer id;

    @NotEmpty @Size(max=64) private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project",
        fetch = FetchType.EAGER)
    private List<Task> tasks = new ArrayList<>();

    public Project() { /* Required for JPA */ }
    public Project(String name) { this.name = name; }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Task> getTasks() { return tasks; }
    public void setTasks(List<Task> tasks) { this.tasks = tasks; }

    public boolean addTask( Task task ) {
        if ( ! tasks.contains( task) ) {
            Project oldProject = task.getProject();
            if ( oldProject != null ) {
                removeTask( task );
            }
            tasks.add(task);
            return true;
        } else { return false; }
    }

    public boolean removeTask( Task task ) {
        if ( tasks.contains( task) ) {
            tasks.remove(task);
            task.setProject(null);
            return true;
        } else { return false; }
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tasks=" + tasks +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;

        Project project = (Project) o;

        if (id != null ? !id.equals(project.id) : project.id != null) return false;
        if (name != null ? !name.equals(project.name) : project.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
