package com.aetrion.activerecord.fixture;

import com.aetrion.activerecord.models.Client;
import com.aetrion.activerecord.models.Firm;
import com.aetrion.activerecord.models.DependentFirm;
import com.aetrion.activerecord.models.Company;

/**
 * 
 */
public class Companies extends Fixture {
    public Firm firstFirm;
    public Client firstClient;
    public Client secondClient;
    public Firm anotherFirm;
    public Client anotherClient;
    public DependentFirm rogueCore;
    public Company leetsoft;
    public Company jadedpixel;

    public void initialize() {
        firstFirm = new Firm();
        firstFirm.setId(1);
        firstFirm.setName("Aetrion LLC");

        firstClient = new Client();
        firstClient.setId(2);
        firstClient.setFirmId(1);
        firstClient.setClientOf(2);
        firstClient.setName("Summit");

        secondClient = new Client();
        secondClient.setId(3);
        secondClient.setFirmId(1);
        secondClient.setClientOf(1);
        secondClient.setName("Microsoft");

        anotherFirm = new Firm();
        anotherFirm.setId(4);
        anotherFirm.setName("Flamboyant Software");

        anotherClient = new Client();
        anotherClient.setId(5);
        anotherClient.setFirmId(4);
        anotherClient.setClientOf(4);
        anotherClient.setName("Ex Nihilo");

        rogueCore = new DependentFirm();
        rogueCore.setId(6);
        rogueCore.setName("Rogue Core");

        leetsoft = new Company();
        leetsoft.setId(7);
        leetsoft.setName("Leetsoft");
        leetsoft.setClientOf(6);

        jadedpixel = new Company();
        jadedpixel.setId(8);
        jadedpixel.setName("Jadedpixel");
        jadedpixel.setClientOf(6);
    }

}
