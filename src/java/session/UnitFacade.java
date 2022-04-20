/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Unit;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author aleksei
 */
@Stateless
public class UnitFacade extends AbstractFacade<Unit> {

    @PersistenceContext(unitName = "web2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UnitFacade() {
        super(Unit.class);
    }
    public List<Unit> findByID(long id) {
        try {
            return (List<Unit>) em.createQuery("SELECT u FROM Unit u WHERE u.user.id=:id").setParameter("id", id).getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<String> findSize() {
        try {
            return (List<String>) em.createQuery("SELECT DISTINCT u.size FROM Unit u WHERE u.status = 1").getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    public Unit findid(int id) {
        try {
            return (Unit) em.createQuery("SELECT u FROM Unit u WHERE u.id=:id").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    public List<Unit> findNotDeleted() {
        try {
            return (List<Unit>) em.createQuery("SELECT u FROM Unit u WHERE u.status = 1").getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    public List<Unit> findDeleted() {
        try {
            return (List<Unit>) em.createQuery("SELECT u FROM Unit u WHERE u.status = 0").getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
