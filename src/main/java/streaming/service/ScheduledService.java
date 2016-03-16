/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package streaming.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class ScheduledService {
    
    @Autowired
    private RessourceService ressourceService;
    
//    @Scheduled(fixedDelay = 10000)// 2 sec entre la FIN du précédent et début suivant
    public void batch(){
        
        // Applique morts de famine
        ressourceService.appliqueMortsDeFamine();
        
        // Gère récoltes et naissances
        ressourceService.gererRecoltes();
    }
}
