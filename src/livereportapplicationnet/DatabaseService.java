/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package livereportapplicationnet;

import java.util.ArrayList;
import livereportapplicationnet.entities.AccountTotal;
import livereportapplicationnet.entities.TxnDetails;

/**
 *
 * @author M_Karandish
 */
public interface DatabaseService {
    public void SaveDataTxn(ArrayList<TxnDetails> txnDetails,long count);
    public void SaveData(ArrayList<AccountTotal> accountTotals,long count);
    
}
