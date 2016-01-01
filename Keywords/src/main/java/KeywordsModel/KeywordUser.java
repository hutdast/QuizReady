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
package KeywordsModel;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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
public class KeywordUser implements Serializable, Serviceable
{
    
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
  @Override
    public String getUserLogin() {
        return userLogin;
    }
  @Override
    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }



     @Override
    public String getPasscode() {
        return passcode;
    }
  @Override
    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }
  @Override
    public String getEmail() {
        return email;
    }
  @Override
    public void setEmail(String email) {
        this.email = email;
    }
  @Override
    public LocalDate getDateofRegistration() {
        return dateofRegistration;
    }
  @Override
    public void setDateofRegistration(LocalDate dateofRegistration) {
        this.dateofRegistration = dateofRegistration;
    }

    
  

    
     @Override
    public String toString() {
        return String.format(
                "User: [username='%s', dateofRegistration='%s']",
                userLogin, dateofRegistration);
    }

    @Override
    public String getSentence() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setSentence(String Sentence) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getKeys() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setKeys(String keys) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, String> getBundle() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setBundle(HashMap<String, String> bundle) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getTitle() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTitle(String title) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCategory() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setCategory(String category) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getQuizDate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setQuizDate(String quizDate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getQuestion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setQuestion(String question) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String> getUsers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setUsers(ArrayList<String> users) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getAnswer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setAnswer(String answer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String> getPossible_answers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setPossible_answers(ArrayList<String> possible_answers) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setId(String _id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
   
    
    
    
    
}
