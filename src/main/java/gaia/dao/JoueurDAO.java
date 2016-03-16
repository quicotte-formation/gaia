/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gaia.dao;

import java.io.Serializable;
import org.springframework.data.repository.CrudRepository;
import gaia.entity.Joueur;

/**
 *
 * @author admin
 */
public interface JoueurDAO extends CrudRepository<Joueur, Long>{
    
}
