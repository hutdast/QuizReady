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
import javax.faces.application.FacesMessage;
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
    {  System.out.println("init QC is invoked and isNewQuiz() is  ..:"+infoBean.isExistingQuiz());
        this.quiz = AppOps.getModel(AppModels.QUIZ);
        if(infoBean.isExistingQuiz() == true)
        {  
            getExistingQuiz(); 
        }else
        {
           this.quizzes =  new ArrayList<>();  
        }

    }

    public String reinit() 
    {
        System.out.println(" From QC reinit the value of getQuizID()..:");
        saveToDB(quiz.getSentence(), quiz.getKeys());
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

//****************** prepBundle(String sentence, String keys) *********************
//================================================================================= 
    /**
     * prepBundle(String sentence, String keys) prepare the question set before
     * it enters the mongoDB.
     *
     * @param sentence
     * @param keys
     */
    private void saveToDB(String sentence, String keys) 
    {
        String delimeter = "\\s|,|\\.";
        for (String key : keys.split(delimeter)) 
        {

            if (!sentence.contains(key.trim())) 
            {

                addMessage("msgs1", " The key " + key + " doesn't match any word in the corresponding sentence",
                        "Mismatch!");
                return;
            } else if (sentence.equals("") || key.equals(""))
            {
                return;//prevent empty entries into the database

            } else 
            {
                infoBean.getQuiz().getBundle().put(sentence, keys);
                mongoStore((Quiz) infoBean.getQuiz());
            }
        }//end for loop String key

    }
  
private void getExistingQuiz()
 {
     String id = infoBean.getQuiz().getId();
     this.quizzes = mongoQueryQ(id).getBundle().entrySet().stream()
                .map(e -> 
                {
                    return new Quiz(quiz.getId(),e.getKey(), e.getValue());
                })
                .collect(Collectors.toList());
                
              
 }    
    
//****************** prepBundle(String sentence, String keys) *********************
//================================================================================= 
    /**
     * prepBundle(String sentence, String keys) prepare the question set before
     * it enters the mongoDB.
     *
     * @param q
     */
    /**
     * public void removeExam(Quiz q) { System.out.print("removeExam()
     * invoked:.. ");
     *
     * if (storedQuiz.getBundle().containsKey(q.getSentence()) ) {
     *
     *
     * int max = quizs.size(); storedQuiz.getBundle().remove(q.getSentence());
     *
     * quizs = storedQuiz.getBundle().entrySet().stream() .map(e -> {
     *
     * return new Quiz(storedQuiz.getId(), e.getKey(), e.getValue()); })
     * .collect(Collectors.toList());
     *
     * //quizs.remove(rowID);
     *
     * System.out.print("Inside quizs:.. " + quizs.size());
     * System.out.print("Inside q:.. " + q.toString()); System.out.print("size
     * of temp:.. " + temp.size()); System.out.print("size of storedQuiz:.. " +
     * storedQuiz.getBundle().size());
     *
     * }else{ return; }
     *
     * System.out.print("leaving removeExam():.. ");
     *
     * }
     *
     */
}//end of QuizCollectorEvent
