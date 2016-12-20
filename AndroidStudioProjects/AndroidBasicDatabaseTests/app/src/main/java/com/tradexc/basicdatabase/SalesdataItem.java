package com.tradexc.basicdatabase;

/**
 * Created by Spreetrip on 12/15/2016.
 */

public class SalesdataItem {
    public String salesdataCompany, salesdataDate, salesdataValue,salesdataComplete, salesdataNotes;

    public SalesdataItem(String salesdataCompany, String salesdataDate, String salesdataValue,
                         String salesdataComplete, String salesdataNotes) {
        this.salesdataCompany = salesdataCompany;
        this.salesdataDate = salesdataDate;
        this.salesdataValue = salesdataValue;
        this.salesdataComplete = salesdataComplete;
        this.salesdataNotes = salesdataNotes;
    }
}
