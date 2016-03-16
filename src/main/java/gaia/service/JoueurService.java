/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gaia.service;

import gaia.dao.JoueurDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import gaia.entity.Joueur;
import gaia.entity.Ressource;

/**
 *
 * @author admin
 */
@Service
public class JoueurService {

    @Autowired
    private JoueurDAO joueurCrudService;

    @Autowired
    private RessourceService ressourceService;

    /**
     * Crée un joueur ainsi que ses 3 blés et 3 carottes et 1 fermier
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void creerJoueur(Joueur joueur) {

        // Persiste joueur
        joueurCrudService.save(joueur);

        // Ajoute 1 fermier
        ressourceService.creerRessource(Ressource.RessourceType.FERMIER, joueur.getId());
        
        // Ajouter 3 blés
        ressourceService.creerRessource(Ressource.RessourceType.BLE, joueur.getId());
        ressourceService.creerRessource(Ressource.RessourceType.BLE, joueur.getId());
        ressourceService.creerRessource(Ressource.RessourceType.BLE, joueur.getId());
        
        // Ajoute 3 carottes
        ressourceService.creerRessource(Ressource.RessourceType.CAROTTE, joueur.getId());
        ressourceService.creerRessource(Ressource.RessourceType.CAROTTE, joueur.getId());
        ressourceService.creerRessource(Ressource.RessourceType.CAROTTE, joueur.getId());
    }
}
