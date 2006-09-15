package com.aetrion.activerecord.fixture;

import com.aetrion.activerecord.models.Account;

/**
 * 
 */
public class Accounts extends Fixture {

    public Account aetrion;
    public Account unknown;
    public Account rogueCoreAccount;
    public Account lastAccount;
    public Account rogueCoreAccount2;

    public void initialize() {
        aetrion = new Account();
        aetrion.setId(1);
        aetrion.setFirmId(1);
        aetrion.setCreditLimit(50.0);

//        unknown = new Account();
//        unknown.setId(2);
//        unknown.setCreditLimit(50.0);

        rogueCoreAccount = new Account();
        rogueCoreAccount.setId(3);
        rogueCoreAccount.setFirmId(6);
        rogueCoreAccount.setCreditLimit(50.0);

        lastAccount = new Account();
        lastAccount.setId(4);
        lastAccount.setFirmId(6);
        lastAccount.setCreditLimit(60.0);

        rogueCoreAccount2 = new Account();
        rogueCoreAccount2.setId(5);
        rogueCoreAccount2.setFirmId(6);
        rogueCoreAccount2.setCreditLimit(55.0);
    }

}
