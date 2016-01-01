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
import KeywordsOp.AppOps;
import KeywordsModel.AppModels;
import KeywordsModel.DataManagement;
import KeywordsModel.Quiz;
import KeywordsModel.Serviceable;
import java.time.LocalDate;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author mayhem
 */
@ManagedBean(name="QI")
@SessionScoped
public class QuizInfoController extends DataManagement
{
   
 private Serviceable quiz;
 @ManagedProperty(value="#{LC}")
 private LoginController loginBean;
 private String privacyOption;
 private String showDialog;
 private boolean existingQuiz;


  
   @PostConstruct
    public void init() 
    {
        //use this for instance variable because an object is created for each request 
           this.quiz = AppOps.getModel(AppModels.QUIZ);
    }
    
    public String getPrivacyOption()
    {
        return privacyOption;
    }

    public void setPrivacyOption(String privacyOption) 
    {
        this.privacyOption = privacyOption;
    }


    public Serviceable getQuiz() 
    {
        return quiz;
    }

    public void setQuiz(Serviceable quiz) 
    {
        this.quiz = quiz;
    }
  

    public LoginController getLoginBean() 
    {
        return loginBean;
    }

    public void setLoginBean(LoginController loginBean) 
    {
        this.loginBean = loginBean;
    }

    public String getShowDialog()
    {
        return showDialog;
    }

    public void setShowDialog(String showDialog) 
    {
        this.showDialog = showDialog;
    }

    public boolean isExistingQuiz() {
        return existingQuiz;
    }

    public void setExistingQuiz(boolean existingQuiz) {
        this.existingQuiz = existingQuiz;
    }

    

  
  
  
  
  
 public String saveInfo() 
 {
        if (quiz.getCategory() != null && quiz.getTitle() != null) 
        {  
            String author = loginBean.getUser().getUserLogin();
            String title = quiz.getTitle();
            String category = quiz.getCategory();
            String id = AppOps.formatTitle(author+title+category);
            
            this.quiz.setId(id);
           this.quiz.setUserLogin(author);
            this.quiz.setQuizDate(LocalDate.now().toString());    
            if (!checkMongo(id) && "success".equals(mongoStore((Quiz) this.quiz))) 
            { 
               return "quiz_assembler.xhtml";
            } else if (checkMongo(id)) 
            { 
                //set the attribute oncomplete of began exam creation btn
                setShowDialog("PF('existing').show();");
              setExistingQuiz(true);
          return null;
             
            } else 
            {
                addMessage("msgs1", " Database problems",
                           "Failed!");
                return null;
            }
            
        } else 
        {
            addMessage("msgs1", " Title and category need to be filled out",
                       "Failed!");
            return null;
        }
        
    }//ed of saveinfo
 

 
 
 
 
 
    
    
}//end of QuizInfoController
