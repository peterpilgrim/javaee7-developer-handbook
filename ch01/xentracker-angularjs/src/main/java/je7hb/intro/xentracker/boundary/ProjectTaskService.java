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

package je7hb.intro.xentracker.boundary;

import je7hb.intro.xentracker.entity.Project;
import je7hb.intro.xentracker.entity.Task;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * The type ProjectTaskService
 *
 * @author Peter Pilgrim (peter)
 */
@Stateless
public class ProjectTaskService {
    @PersistenceContext(unitName = "XenTracker")
    private EntityManager entityManager;

    public void saveProject( Project project ) {
        entityManager.persist(project);
    }

    public void updateProject( Project project ) {
        final Project projectToBeUpdated = entityManager.merge(project);
        entityManager.persist(projectToBeUpdated);
    }

    public void removeProject( Project project ) {
        final Project projectToBeRemoved = entityManager.merge(project);
        entityManager.remove(projectToBeRemoved);
    }

    public List<Project> findAllProjects() {
        final Query query = entityManager.createNamedQuery(
            "Project.findAllProjects");
        return query.getResultList();
    }

    public List<Project> findProjectById( Integer id ) {
        final Query query = entityManager.createNamedQuery(
            "Project.findProjectById")
            .setParameter("id", id );
        return query.getResultList();
    }

    public List<Task> findTaskById( Integer id ) {
        final Query query = entityManager.createNamedQuery(
                "Project.findTaskById")
                .setParameter("id", id );
        return query.getResultList();
    }

    public List<Task> findTasksByProjectId( Integer id ) {
        final Query query = entityManager.createNamedQuery(
                "Project.findTasksByProjectId")
                .setParameter("id", id );
        return query.getResultList();
    }


    public void clearDatabase() {
        entityManager.createQuery("delete from Task").executeUpdate();
        entityManager.createQuery("delete from Project").executeUpdate();
    }
}
