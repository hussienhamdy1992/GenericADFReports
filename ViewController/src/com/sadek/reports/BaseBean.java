package com.sadek.reports;

import java.net.MalformedURLException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class BaseBean {
    public BaseBean() {
        super();
    }
    
    public String getArialFontPath(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String path="";
        try {
            path = ec.getResource("/font-awesome-4.3.0/fonts/arial.ttf").getPath();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return path;
    }
    
    public String getMomraLogoImgPath(String imagePath){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String path="";
        try {
            path = ec.getResource("/Image/"+imagePath).getPath();//"/Image/momra.jpeg").getPath();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return path;
    }
}
