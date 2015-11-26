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
import java.util.ArrayList;

import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.time.LocalDate;
import java.util.Hashtable;
import javax.validation.constraints.NotNull;
/**
 *Quiz is a simple POJO (Plain Old Java Object).
 *Quiz is the entity that represent quiz from the databases.
 * The following methods are available:<br />
 * <ol>
 *  <li>Quiz(String title) -> Constructor</li>
 *  <li>equals(Object obj) -> To compare with another quiz object</li>
 * <li>toString() -> String literal of the object</li> 
 *
 * </ol>
 * @author nikensonmidi
 */
@Entity
public class Quiz implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String _id;
    private String author;
    @NotNull(message = "Cannot leave field empty")
    private String title;

private String question;
    private String answer;
    @NotNull(message = "Cannot leave field empty")
    private String category;
    private String quizDate;
    private String sentence;
        private String keys;
        private Hashtable<String, String> bundle;
    private ArrayList<String> users;
    private ArrayList<String> possible_answers;
    

//******************************** Constructors ***********************************
//================================================================================= 
    public Quiz() {
}

    public Quiz(String title) {
        this.title = title;
    
       
    }
  

  

//******************************** Getters and Setters ****************************
//=================================================================================
      public String getSentence() {
        return sentence;
    }

    public void setSentence(String Sentence) {   
        this.sentence = Sentence;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public Hashtable<String, String> getBundle() {
        return bundle;
    }
    public void setBundle(Hashtable<String, String> bundle) {  
        this.bundle = bundle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuizDate() {
        return quizDate;
    }

    public void setQuizDate(String quizDate) {
        this.quizDate = quizDate;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    

   

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public ArrayList<String> getPossible_answers() {

        return possible_answers;
    }

    public void setPossible_answers(ArrayList<String> possible_answers) {
        this.possible_answers = possible_answers;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
        return "quizproject.quizproject.Quiz[ id=" + _id + " ]";
    }
    
    
    
}// end of class
