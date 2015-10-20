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
package Functionalities;

import java.io.Serializable;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.primefaces.context.RequestContext;

/**
 * UserValidator authenticates or registers user to gain access to the
 * application. UserValidator is a ManagedBean(name=UV) and takes requests from
 * the front end and sends out the proper response. The following methods are
 * available:<br />
 * <ol>
 * <li>{@link quizproject.UserValidator#checkNcreate()}  -> Creates a new User.</li>
 * <li>{@link quizproject.UserValidator#init()}   -> initial state of the session.</li>
 * <li>{@link quizproject.UserValidator#logout()}   -> Logs the user out of the program.</li>
 * <li>{@link quizproject.UserValidator#profileLog() }  -> Validates and redirects user to the next phase.</li>
 * <li>{@link quizproject.UserValidator#reset()}   -> Resets forms.</li>
 *
 * </ol>
 *
 * @author nikensonmidi
 */
public final class UserValidator extends Management implements Serializable{
    
/**
 * The login name for the user can not be more than 50 characters long
 * or less than 2 characters.
 */    
@NotNull(message = "Cannot leave field empty")
    @Size(min = 2, max = 50, message = "have a minimum of 2")
    public String user;    
/**
 * The password required for the user to login into the Application
 * cannot be less than 6 characters long and more than 50 characters.
 */ 
@NotNull(message = "Cannot leave field empty")
    @Size(min = 6, max = 50, message = "Not a strong password")
    private String passcode;

/**
 * The date the account is created.
 */ 
@NotNull(message = "Cannot leave field empty")
    private Date dateofRegistration;
/**
 * The email address associated with the user.
 */ 
@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`"
            + "{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9]"
            + "(?:[a-z0-9-]*[a-z0-9])?", message = "Invalid email")
    @NotNull(message = "Cannot leave field empty")
    private String email;

//******************************** Getters and setters ****************************
//================================================================================= 


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

   
    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
//******************************** profileLog() ***********************************
//=================================================================================
/**
     *Validate the credential of the user before profile can be retrieved and accessed.
     * @return String
     */     
  public String profileLog() {
        if (logNpass(getUser(), getPasscode())) {
            //ViewChanger.renderEastSide();

            return "test.xhtml";

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
        if (logNpass(getUser(), getPasscode())) {
          addMessage("msgs1", "Fail!", "Choose another username and password");
        } else {

            createNewUser(getUser(), getPasscode(), getEmail());

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

        return "error.xhtml";
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
  
}//end of class UserValidator
