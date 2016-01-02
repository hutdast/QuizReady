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

/**
 *
 * @author mayhem
 */
import KeywordsModel.AppModels;
import KeywordsModel.DataManagement;
import KeywordsModel.Quiz;
import KeywordsModel.Serviceable;
import KeywordsOp.AppOps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "QC")
@SessionScoped
public class QuizCollectorController extends DataManagement
{
    
    private Serviceable quiz;
    private List<Quiz> quizzes;
    @ManagedProperty(value = "#{QI}")
    private QuizInfoController infoBean;
    private String quizID;
    
    @PostConstruct
    public void init()
    {
        quiz = AppOps.getModel(AppModels.QUIZ);
        infoBean.getQuiz().setBundle(new HashMap<>());//Initialize the infobean quiz table
        if(infoBean.isExistingQuiz() == true)
        {
            getExistingQuiz();
        }else
        {
            quizzes =  new ArrayList<>();
        }
        
    }
    
    public String reinit()
    {
        screenForDB(quiz.getSentence(), quiz.getKeys());
        quiz = AppOps.getModel(AppModels.QUIZ);
        return null;
    }
    
    public List<Quiz> getQuizzes()
    {
        return quizzes;
    }
    
    public void setQuizzes(List<Quiz> quizzes)
    {
        this.quizzes = quizzes;
    }
    
    public Serviceable getQuiz()
    {
        return quiz;
    }
    
    public void setQuiz(Serviceable quiz)
    {
        this.quiz = quiz;
    }
    
    public QuizInfoController getInfoBean()
    {
        return infoBean;
    }
    
    public void setInfoBean(QuizInfoController infoBean)
    {
        this.infoBean = infoBean;
    }
    
    public String getQuizID()
    {
        return quizID;
    }
    
    public void setQuizID(String quizID)
    {
        this.quizID = quizID;
    }
    
    //****************** screenForDB(String sentence, String keys)  *********************
    //=================================================================================
    /**
     * screenForDB(String sentence, String keys)  Save the question set before
     * it enters the mongoDB and alert user about empty fields and requirement
     * violations.
     *
     * @param sentence
     * @param keys
     */
    private void screenForDB(String sentence, String keys)
    {
        String delimeter = "\\s|,|\\.";
        for (String key : keys.split(delimeter))
        {
            
            if (!sentence.contains(key.trim()))
            {
                
                addMessage("msgs1", " The key " + key + " doesn't match any word in the corresponding sentence",
                           "Not saved!");
                return;
            } else if (sentence.equals("") || key.equals(""))
            {
                addMessage("msgs1", "Sentence and keys cannot be left empty",
                           "Not saved!");
                return;//prevent empty entries into the database
                
            } else
            {
                
                infoBean.getQuiz().getBundle().put(sentence, keys);
                mongoStore((Quiz) infoBean.getQuiz());
            }
        }//end for loop String key
        
    }
    //****************** getExistingQuiz() ********************************************
    //=================================================================================
    /**
     * getExistingQuiz() captures the bundle from the quiz that is in the database
     * (if one is found from infobean) then disassembles it into several quiz objects.
     *
     */
    private void getExistingQuiz()
    {
        String id = infoBean.getQuiz().getId();
        if(mongoQueryQ(id).getBundle().isEmpty())
        {
            System.out.print(" ... ");
            return;
        }  else
        {
            quizzes = mongoQueryQ(id).getBundle().entrySet().stream()
            .map(e ->
                 {
                     return new Quiz(quiz.getId(),e.getKey(), e.getValue());
                 })
            .collect(Collectors.toList());
        }
        
        
        
    }
    
    //****************** removeExam(Quiz q) *******************************************
    //=================================================================================
    /**
     * prepBundle(String sentence, String keys) prepare the question set before
     * it enters the mongoDB.
     *
     * @param q
     */
    
    public void removeExam(Quiz q)
    {
        
        System.out.print("removeExam() is ivoked, and the new list is..:");
        quizzes =   quizzes.stream()
        .filter(e -> !(e.getSentence().equals(q.getSentence())))
        .collect(Collectors.toList());
        infoBean.getQuiz().getBundle().remove(q.getSentence(), q.getKeys());
        mongoStore((Quiz) infoBean.getQuiz());
        
        
        
        
    }
    
    
    
    
    
}//end of QuizCollectorEvent
