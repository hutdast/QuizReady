/*
 * Copyright 2016 nikensonmidi.
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
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * A Test object has a question with a corresponding answer.
 * This object may also has several other Test objects, i.e. 
 * Test T and Test t are created. T is the main object which encapsulate
 * multiple t's with each t has a question and a corresponding answer and no
 * its test property is null. While T will capture all t's and its question
 * and answer properties are both null.Following methods available for Test are:<br />
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
public class Test implements Serializable{
    @Id
    private String _id;
    /**
     * Test title
     */
    private String title;
    /**
     * Test question
     */
    private String question;
    /**
     * Test answer
     */
    private String ans;
    /**
     * Test node
     */
    private Test test;

   
    

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }
    
    
    
    
    
    
    
    
}
