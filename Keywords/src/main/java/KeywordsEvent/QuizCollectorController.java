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

@ManagedBean(name = "QC")
@SessionScoped
public class QuizCollectorController extends DataManagement {
    
    private Serviceable quiz;
    private List<Quiz> quizzes;
    @ManagedProperty(value = "#{QI}")
    private QuizInfoController infoBean;
    private Quiz dbQuiz;
    
    @PostConstruct
    public void init() {
        infoBean.setShowDialog("false");
        String id = infoBean.getQuiz().getId();
        setDbQuiz(mongoQueryQ(id));
        quiz = AppOps.getModel(AppModels.QUIZ);
        
        if (infoBean.isExistingQuiz() == true) {
            getExistingQuiz();
        } else {
            
            dbQuiz.setBundle(new HashMap<>());
            quizzes = new ArrayList<>();
        }
        
    }
    
    public String reinit() {
        screenForDB(quiz.getSentence(), quiz.getKeys());
        quiz = AppOps.getModel(AppModels.QUIZ);
        
        return null;
    }
    
    public List<Quiz> getQuizzes() {
        return quizzes;
    }
    
    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }
    
    public Serviceable getQuiz() {
        return quiz;
    }
    
    public void setQuiz(Serviceable quiz) {
        this.quiz = quiz;
    }
    
    public QuizInfoController getInfoBean() {
        return infoBean;
    }
    
    public void setInfoBean(QuizInfoController infoBean) {
        this.infoBean = infoBean;
    }
    
    
    public Quiz getDbQuiz() {
        return dbQuiz;
    }
    
    public void setDbQuiz(Quiz dbQuiz) {
        this.dbQuiz = dbQuiz;
    }
    
    //****************** screenForDB(String sentence, String keys)  *********************
    //=================================================================================
    /**
     * screenForDB(String sentence, String keys) Save the question set before it
     * enters the mongoDB and alert user about empty fields and requirement
     * violations.
     *
     * @param sentence
     * @param keys
     */
    private void screenForDB(String sentence, String keys) {
        String errMsg = AppOps.verifyEntry(sentence, keys);
        if(!errMsg.equals("success")){
            addMessage("msgs1", errMsg,
                       "Not saved!");
        }else{
            dbQuiz.getBundle().put(sentence, keys);
            mongoStore(dbQuiz);
        }
        
    }
    
    //****************** getExistingQuiz() ********************************************
    //=================================================================================
    /**
     * getExistingQuiz() captures the bundle from the quiz that is in the
     * database (if one is found from infobean) then disassembles it into
     * several quiz objects.
     *
     */
    private void getExistingQuiz() {
        /**
         * The user might be interrupted or did not finish creating the test
         * from last session so the db would be empty therefore bundle would be
         * null if bundle is null then it should be initiated in order to put
         * element in it when screenForDB() is called.
         */
        if (dbQuiz.getBundle() == null) {
            dbQuiz.setBundle(new HashMap<>());
            quizzes = new ArrayList<>();
        } else {
            quizzes = dbQuiz.getBundle().entrySet().stream()
            .map(e
                 -> {
                     return new Quiz(quiz.getId(), e.getKey(), e.getValue());
                 })
            .collect(Collectors.toList());
        }
        
    }
    
    //****************** removeExam(Quiz q) *******************************************
    //=================================================================================
    /**
     * prepBundle(String sentence, String keys) prepares the question set before
     * it enters the mongoDB.
     *
     * @param q
     */
    public void removeExam(Quiz q) {
        quizzes = quizzes.stream()
        .filter(e -> !(e.getSentence().equals(q.getSentence())))
        .collect(Collectors.toList());
        dbQuiz.getBundle().remove(q.getSentence(), q.getKeys());
        mongoStore(dbQuiz);
        
    }//end of removeExam(Quiz q)
    
    public void updateRow(String key, String sentence){
        screenForDB(sentence, key);
        
    }
    
    public void onClick(String sentence){
        
        dbQuiz.getBundle().remove(sentence);
    }
    
}//end of QuizCollectorEvent
