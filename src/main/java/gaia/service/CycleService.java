/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gaia.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class CycleService {

    private int cycleActuel;

    @Scheduled(fixedDelay = 60000)
    public void schedule() {

        cycleActuel++;
    }

    public CycleService() {

        cycleActuel = 1;
    }

    public int getCycleActuel() {
        return cycleActuel;
    }
}
