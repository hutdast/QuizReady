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
import java.time.LocalDate;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/**
 * KeywordUser authenticates or registers user to gain access to the
 application. KeywordUser is a ManagedBean(name=UV) and takes requests from
 the front end and sends out the proper response. The following methods are
 available:<br />
 * <ol>
 * <li>{@link quizproject.KeywordUser#checkNcreate()}  -> Creates a new User.</li>
 * <li>{@link quizproject.KeywordUser#init()}   -> initial state of the session.</li>
 * <li>{@link quizproject.KeywordUser#logout()}   -> Logs the user out of the program.</li>
 * <li>{@link quizproject.KeywordUser#profileLog() }  -> Validates and redirects user to the next phase.</li>
 * <li>{@link quizproject.KeywordUser#reset()}   -> Resets forms.</li>
 *
 * </ol>
 *
 * @author nikensonmidi
 */
@Entity
public class KeywordUser implements Serializable {
    
    /**
 * The login name for the user can not be more than 50 characters long
 * or less than 2 characters.
 */    
@NotNull(message = "Cannot leave field empty")
    @Size(min = 2, max = 50, message = "have a minimum of 2")
@Id
    private String userLogin;    
/**
 * The password required for the user to login into the Application
 * cannot be less than 6 characters long and more than 50 characters.
 */ 
@NotNull(message = "Cannot leave field empty")
    @Size(min = 6, max = 50, message = "password discrepancy")
    private String passcode;

/**
 * The date the account is created.
 */ 
@NotNull(message = "Cannot leave field empty")
    private LocalDate dateofRegistration;
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

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
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

    public LocalDate getDateofRegistration() {
        return dateofRegistration;
    }

    public void setDateofRegistration(LocalDate dateofRegistration) {
        this.dateofRegistration = dateofRegistration;
    }

   
    
     @Override
    public String toString() {
        return String.format(
                "Customer[username='%s', dateofRegistration='%s']",
                userLogin, dateofRegistration);
    }

}//end of class KeywordUser
