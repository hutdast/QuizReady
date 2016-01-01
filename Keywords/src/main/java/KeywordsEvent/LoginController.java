/*
 * Copyright 2015 nikensonmidi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package KeywordsEvent;

import KeywordsModel.AppModels;
import KeywordsModel.DataManagement;
import KeywordsModel.Serviceable;
import KeywordsOp.AppOps;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.context.RequestContext;

/**
 *
 * @author mayhem
 */
@ManagedBean(name="LC")
@SessionScoped
public class LoginController extends DataManagement
{
    private Serviceable user;
     private String eastVisibility;
     private String menuVisibility;
     
@PostConstruct
    public void init() 
    {
         user = AppOps.getModel(AppModels.KEYWORD_USER);
         
    }     

    public String getEastVisibility() 
    {
        return eastVisibility;
    }

    public void setEastVisibility(String eastVisibility) 
    {
        this.eastVisibility = eastVisibility;
    }

    public String getMenuVisibility() 
    {
        return menuVisibility;
    }

    public void setMenuVisibility(String menuVisibility) 
    {
        this.menuVisibility = menuVisibility;
    }
     
    public Serviceable getUser() 
    {
        
        return user;
    }

    public void setUser(Serviceable user) 
    {
        this.user = user;
    }

    
   
    
    
//******************************** profileLog() ***********************************
//=================================================================================
/**
     *Validate the credential of the user before profile can be retrieved and accessed.
     * @return String
     */     
  public String profileLog() 
  {
        if (logNpass(user.getUserLogin(), user.getPasscode())) 
        {
           // setLoginName(user.getUserLogin());
            menuVisibility = "true";
           eastVisibility = "true";
            return "profile.xhtml";

        } else 
        {
             menuVisibility = "false";
            eastVisibility = "false";
             addMessage("msgs1", "Fail!", "Wrong username and passwords");
        }
        return null;
    }//end of profileLog()    

 //******************************** checkNcreate() ********************************
//================================================================================= 
  /**
     *Create a new user into the system and store a record into the RDBMS
     */ 
public void checkNcreate() 
{
        if (logNpass(user.getUserLogin(), user.getPasscode()))
        {
          addMessage("msgs1", "Fail!", "Choose another username and password");
        } else 
        {
            createNewUser(user.getUserLogin(), user.getPasscode(),user.getEmail());
            addMessage("msgs1", "Success", "Welcome " + user.getUserLogin());

        }
    }//end of checkNcreate()

//******************************** logout() ***************************************
//================================================================================= 
/**
     * Logging out the current session. 
     *@return String
     */ 
 public String logout()
 {

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
                .getResponse();

        FacesContext.getCurrentInstance().getExternalContext()
                .invalidateSession();
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate,"
                + "post-check=0, no-cache='set-cookie'");
        response.setHeader("Pragma", "no-cache");
        //response.setHeader("Refresh","1");refresh every second
        response.setDateHeader("Experises", -1);

        return "template.xhtml";
    }//end of logout()
//******************************** logout() ***************************************
//================================================================================= 
/**
     * Reseting forms to their initial state nullified all previous values.
     *
     */ 
public void reset()
{
        RequestContext.getCurrentInstance().reset("form");
    } //end of reset()
    


      
    
    
}//end of LoginController
