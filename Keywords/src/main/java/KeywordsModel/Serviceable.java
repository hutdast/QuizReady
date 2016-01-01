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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author nikensonmidi
 */
public interface Serviceable 
{
     String getId(); 
    
     void setId(String _id);
     
     String getUserLogin();
 
     void setUserLogin(String userLogin);
    
     String getPasscode();

     void setPasscode(String passcode);

     String getEmail();

     void setEmail(String email);

     LocalDate getDateofRegistration();

     void setDateofRegistration(LocalDate dateofRegistration);
    
       String getSentence();

     void setSentence(String Sentence);

     String getKeys();

     void setKeys(String keys);

     HashMap<String, String> getBundle();

     void setBundle(HashMap<String, String> bundle);

     String getTitle();

     void setTitle(String title);

     String getCategory();

     void setCategory(String category);

     String getQuizDate();

     void setQuizDate(String quizDate);

     String getQuestion();

     void setQuestion(String question);

     ArrayList<String> getUsers();

     void setUsers(ArrayList<String> users);

     String getAnswer();

     void setAnswer(String answer);

     ArrayList<String> getPossible_answers();

     void setPossible_answers(ArrayList<String> possible_answers);
    
   
     
    
}//end of interface
