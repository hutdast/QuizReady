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
package BackEnd;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.component.panel.Panel;
import org.primefaces.context.RequestContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;
import org.primefaces.event.CellEditEvent;
/**
 *
 * @author nikensonmidi
 */
@ManagedBean(name = "VM")
@SessionScoped
public final class ViewManager extends Management implements Serializable {
    
      /**
     * The user info
     */
    private KeywordUser user;
   
    
    private MenuModel model;
    private String visibility;
    private String privacyOption;
    private Panel[] p;

    @PostConstruct
    public void init() {
        user = new KeywordUser();  
       p = new Panel[5];
        model = new DefaultMenuModel();

        DefaultMenuItem item = new DefaultMenuItem("Profile");
        item.setOnclick("PF('profile').show();");
        item.setIcon("ui-icon-home");
        model.addElement(item);
        /**
         * Inside quizzes there would be a CRUD functionality Along with various
         * challenges functionality The CRUD functionality would be displayed as
         * default The page That is used to create the new quiz would be the
         * CRUD page The challenges would be displayed in tabs: Test, timed Test
         * and Online challenges: Come at me bro, Roundtable ...
         *
         */
        item = new DefaultMenuItem("Quizzes & Challenges");
        item.setOutcome("profile.xhtml");
        item.setIcon("ui-icon-pencil");
        model.addElement(item);
        /**
         * Messages from other users who would invite you into a challenge,
         * (possible calendar to set challenge date). it would be a dialog
         * displaying all widget modal is false
         */
        item = new DefaultMenuItem("Notifications");
        item.setOnclick("PF('notify').show();");
        item.setIcon("ui-icon-mail-closed");
        model.addElement(item);
        /**
         * Video to explain the webApp
         */
        item = new DefaultMenuItem("Help");
        item.setCommand("#{VM.logout()}");
        item.setIcon("ui-icon-help");
        model.addElement(item);

        item = new DefaultMenuItem("Logout");
        item.setCommand("#{VM.logout()}");
        item.setIcon("ui-icon-mail-closed");
        model.addElement(item);

    }//end of init()

    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }

    public KeywordUser getUser() {
        return user;
    }

    public void setUser(KeywordUser user) {
        this.user = user;
    }

    public String getVisibility() {
        
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getPrivacyOption() {
        return privacyOption;
    }

    public void setPrivacyOption(String privacyOption) {
        this.privacyOption = privacyOption;
    }

    public Panel[] getP() {
         
        for(Panel panel: p){
            panel = new Panel();
        }
        return p;
    }

    public void setP(Panel[] p) {
        this.p = p;
    }

  
   

  
    
        
//******************************** profileLog() ***********************************
//=================================================================================
/**
     *Validate the credential of the user before profile can be retrieved and accessed.
     * @return String
     */     
  public String profileLog() {
        if (logNpass(getUser().getUserLogin(), getUser().getPasscode())) {
            visibility = "true";

            return "profile.xhtml";

        } else {
             addMessage("msgs1", "Fail!", "Wrong username and passwords");
        }
        return null;
    }//end of profileLog()    

//******************************** checkNcreate() ********************************
//================================================================================= 
  /**
     *Create a new user into the system and store a record into the RDBMS
     */ 
public void checkNcreate() {
        if (logNpass(getUser().getUserLogin(), getUser().getPasscode())) {
          addMessage("msgs1", "Fail!", "Choose another username and password");
        } else {

            createNewUser(getUser().getUserLogin(), getUser().getPasscode(),getUser().getEmail());

            addMessage("msgs1", "Success", "Welcome " + getUser());

        }
    }//end of checkNcreate()

//******************************** logout() ***************************************
//================================================================================= 
/**
     * Logging out the current session. 
     *@return String
     */ 
 public String logout() {

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
public void reset() {

        RequestContext.getCurrentInstance().reset("form");
    } //end of reset()
    
       public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    
    
}//end of class ViewManager
