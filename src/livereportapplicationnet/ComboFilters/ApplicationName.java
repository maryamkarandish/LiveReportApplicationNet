/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package livereportapplicationnet.ComboFilters;

/**
 *
 * @author M_Karandish
 */
public class ApplicationName {
    
    private String appName;
    private String appId;

    public ApplicationName(String appName, String appId) {
        this.appName = appName;
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
    
    
    
}
