/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package streaming.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import streaming.dao.JoueurDAO;
import streaming.dao.RessourceDAO;
import streaming.entity.Joueur;
import streaming.entity.Ressource;
import streaming.exception.NourritureIncompatibleException;
import streaming.exception.RessourcesManquantesException;

/**
 *
 * @author admin
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class RessourceService {

    @Autowired
    private JoueurDAO joueurDao;

    @Autowired
    private RessourceDAO dao;

    @Autowired
    private CycleService cycleService;

    public void appliqueMortsDeFamine() {

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    private void supprimeResourcesJoueur(long joueurId, Ressource.RessourceType type, int quantite) throws RessourcesManquantesException {

        // Récupère les ressources voulue et exception si pas assez
        List<Ressource> ressources = dao.findByJoueurIdAndRessourceType(joueurId, type);
        if (ressources.size() < quantite) {
            throw new RessourcesManquantesException();
        }

        // Supprime la quantite voulue de ressources
        for (int i = 0; i < quantite; i++) {

            dao.delete(ressources.get(i).getId());
        }
    }

    public void nourir(long userId, long targetRessourceId, Ressource.RessourceType ressourceType) throws RessourcesManquantesException, NourritureIncompatibleException {

        /**
         * Utilisateur, mouton 1 Utilisateur, fromage 2 Utilisateur, carote 3
         * Utilisateur, blé 4
         *
         * Mouton, blé 1 Mouton, carote 2
         */
        // Récupère resource cible et joueur
        Ressource ressourceCible = dao.findOne(targetRessourceId);
        Joueur j = joueurDao.findOne(userId);

        // Définit map de nutrition en fonction de la ressource cible
        Map<Ressource.RessourceType, Integer> mapNutrition = new HashMap();

        switch (ressourceCible.getRessourceType()) {

            case FERMIER:

                mapNutrition.put(Ressource.RessourceType.CHEVRE, 1);
                mapNutrition.put(Ressource.RessourceType.FROMAGE, 2);
                mapNutrition.put(Ressource.RessourceType.CAROTTE, 3);
                mapNutrition.put(Ressource.RessourceType.BLE, 4);
                break;

            case CHEVRE:
                mapNutrition.put(Ressource.RessourceType.BLE, 1);
                mapNutrition.put(Ressource.RessourceType.CAROTTE, 1);
                break;

            default:
                throw new RuntimeException("Cette ressource n'a pas besoin de nourriture");

        }

        // Supprime le stock
        Integer nbASupprimer = mapNutrition.get(ressourceType);
        if( nbASupprimer==null )
            throw new NourritureIncompatibleException();
        supprimeResourcesJoueur(userId, ressourceType, nbASupprimer);

        // Nourrit la ressource
        ressourceCible.setProchainCycleMortDeFaim(cycleService.getCycleActuel() + 3);
        dao.save(ressourceCible);
    }

    public void planter(long resourceId) {

        Ressource r = dao.findOne(resourceId);

        if (r.getOccupe()) {
            throw new RuntimeException("Ressource déjà plantée");
        }

        switch (r.getRessourceType()) {
            case BLE:
            case CAROTTE:
                r.setProchainCycleRecolte(cycleService.getCycleActuel() + 3);
                r.setOccupe(true);
                dao.save(r);
                break;
            case CHEVRE:
                r.setProchainCycleRecolte(cycleService.getCycleActuel() + 5);
                r.setOccupe(true);
                dao.save(r);
                break;
            default:
                throw new RuntimeException("Ressource non plantable");
        }
    }

    public Ressource creerRessource(Ressource.RessourceType ressourceType, long joueurId) {

        // Recupère joueur
        Joueur j = joueurDao.findOne(joueurId);

        // Persiste la ressource
        Ressource ressource = new Ressource();
        ressource.setRessourceType(ressourceType);
        ressource.setOccupe(false);
        ressource.setProchainCycleRecolte(0);
        ressource.setJoueur(j);
        j.getRessources().add(ressource);

        switch (ressourceType) {
            case FERMIER:
            case CHEVRE:
                ressource.setProchainCycleMortDeFaim(3);
                break;
            default:
                ressource.setProchainCycleMortDeFaim(0);
                break;
        }
        return dao.save(ressource);
    }

    void gererRecoltes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
