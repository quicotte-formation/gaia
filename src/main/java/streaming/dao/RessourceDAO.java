/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package streaming.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import streaming.entity.Ressource;

/**
 *
 * @author admin
 */
public interface RessourceDAO extends CrudRepository<Ressource, Long>{
    
    List<Ressource> findByJoueurIdAndRessourceType(long joueurId, Ressource.RessourceType type);
}
