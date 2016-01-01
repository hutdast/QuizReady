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
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.HashMap;
import javax.validation.constraints.NotNull;

/**
 * Quiz is a simple POJO (Plain Old Java Object). Quiz is the entity that
 * represent quiz from the databases. The following methods are available:<br />
 * <ol>
 * <li>Quiz(String title) -> Constructor</li>
 * <li>equals(Object obj) -> To compare with another quiz object</li>
 * <li>toString() -> String literal of the object</li>
 *
 * </ol>
 *
 * @author nikensonmidi
 */
@Entity
public class Quiz implements Serializable, Serviceable {

    private static final long serialVersionUID = 1L;

    @Id
    private String _id;
    @NotNull(message = "Cannot leave field empty")
    private String title;
private String userLogin;
    private String question;
    private String answer;
    @NotNull(message = "Cannot leave field empty")
    private String category;
    private String quizDate;
    @NotNull(message = "Cannot leave field empty")
    private String sentence;
    @NotNull(message = "Cannot leave field empty")
    private String keys;
    private HashMap<String, String> bundle;
    private ArrayList<String> users;
    private ArrayList<String> possible_answers;

//******************************** Constructors ***********************************
//================================================================================= 
    public Quiz() 
    {
    }

    public Quiz(String _id, String sentence, String keys) {
        this._id = _id;
        this.sentence = sentence;
        this.keys = keys;
    }


//******************************** Getters and Setters ****************************
//=================================================================================
     @Override
     public String getId() {
        return _id;
    }
  @Override
    public void setId(String _id) {
        this._id = _id;
    }
       @Override
    public String getSentence() {
        return sentence;
    }
   @Override
    public void setSentence(String Sentence) {
        this.sentence = Sentence;
    }
   @Override
    public String getKeys() {
        return keys;
    }
   @Override
    public void setKeys(String keys) {
        this.keys = keys;
    }
   @Override
    public HashMap<String, String> getBundle() {
        return bundle;
    }
   @Override
    public void setBundle(HashMap<String, String> bundle) {
        this.bundle = bundle;
    }
   @Override
    public String getTitle() {
        return title;
    }
   @Override
    public void setTitle(String title) {
        this.title = title;
    }
   @Override
    public String getCategory() {
        return category;
    }
   @Override
    public void setCategory(String category) {
        this.category = category;
    }
   @Override
    public String getQuizDate() {
        return quizDate;
    }
   @Override
    public void setQuizDate(String quizDate) {
        this.quizDate = quizDate;
    }
 
    public String getQuestion() {
        return question;
    }
   @Override
    public void setQuestion(String question) {
        this.question = question;
    }
   @Override
    public ArrayList<String> getUsers() {
        return users;
    }
   @Override
    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }
   @Override
    public String getAnswer() {
        return answer;
    }
   @Override
    public void setAnswer(String answer) {
        this.answer = answer;
    }
   @Override
    public ArrayList<String> getPossible_answers() {

        return possible_answers;
    }
   @Override
    public void setPossible_answers(ArrayList<String> possible_answers) {
        this.possible_answers = possible_answers;
    }
 
    
 

//********************************  equals(Object obj) ****************************
//=================================================================================     
    /**
     * the equal and the hash have to be tailored in order for the datatable in
     * the front end to work.
     *
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Quiz)) {
            return false;
        }

        Quiz quiz = (Quiz) obj;

        return (quiz.getKeys() != null && quiz.getKeys().equals(keys))
                && (quiz.getQuestion() != null && quiz.getQuestion().equals(question));
    }

//********************************  hashCode() ************************************
//=================================================================================  
    @Override
    public int hashCode() {
        int hash = 1;

        if (keys != null) {
            hash = hash * 31 + keys.hashCode();
        }

        if (question != null) {
            hash = hash * 29 + question.hashCode();
        }

        return hash;
    }

//********************************  toString() ************************************
//=================================================================================      
    @Override
    public String toString() {
        return "Keywords.KeywordsModel.Quiz[ title=" + title + " sentence= "+sentence+" ]";
    }

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setPasscode(String passcode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getEmail() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setEmail(String email) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LocalDate getDateofRegistration() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setDateofRegistration(LocalDate dateofRegistration) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  

}// end of class
