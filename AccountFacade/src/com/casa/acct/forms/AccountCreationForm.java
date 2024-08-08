package com.casa.acct.forms;


import java.util.List;

import com.coopdb.data.Tbdeposit;
import com.coopdb.data.Tbdepositcif;

/**
 * Created by saoDG on 5/27/2018.
 */
public class AccountCreationForm {

    private Tbdeposit tbdeposit;
    private List<Tbdepositcif> ciflist;

    public Tbdeposit getTbdeposit() {
        return tbdeposit;
    }

    public void setTbdeposit(Tbdeposit tbdeposit) {
        this.tbdeposit = tbdeposit;
    }

    public List<Tbdepositcif> getCiflist() {
        return ciflist;
    }

    public void setCiflist(List<Tbdepositcif> ciflist) {
        this.ciflist = ciflist;
    }
}
