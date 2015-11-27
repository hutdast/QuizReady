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

import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * QuizManager manages accesses and manipulations of all Quiz objects.
 * QuizManager is a ManagedBean(name=QM) and takes requests from the front end
 * and sends out the proper response. The following methods are available:<br />
 * <ol>
 * <li>{@link quizproject.QuizManager#init()} -> Establishes the initial state
 * of the session (Part of the session chain.</li>
 * <li>{@link quizproject.QuizManager#createNew()} -> Add the previous question
 * object and create a new one (part of the session chain).</li>
 * <li>{@link quizproject.QuizManager#makeAnagram(String word)} -> Return a
 * permutation of a word.</li>
 * <li>{@link quizproject.QuizManager#makeT_or_F()} -> Make true of false
 * question.</li>
 * <li>{@link quizproject.QuizManager#multiple_listGen(int amount, String title)}->
 * Makes a list of multiple-choice question.</li>
 * <li>{@link quizproject.QuizManager#purgeQuiz()} ->Get the first quiz object
 * saved from the session.</li>
 * <li>{@link quizproject.QuizManager#questionGen()} -> Generate one
 * question.</li>
 * <li>{@link quizproject.QuizManager#reinit()} -> Re-establishes state of the
 * session upon saving the quiz object (part of the session chain).</li>
 * <li>{@link quizproject.QuizManager#saveInfo()} -> Save the preliminary info
 * of the quiz.</li>
 * <li>{@link quizproject.QuizManager#saveQ()} -> Save all the questions.</li>
 * <li>{@link quizproject.QuizManager#trueORfalse_listGen(int amount, String title)}
 * -> Makes a list of True or false questions.</li>
 * <li>{@link quizproject.QuizManager#getQuizTitles(java.lang.String)} -> Gets a
 * list of the user's quizzes titles.</li>
 * <li>{@link quizproject.QuizManager#getQuizTitles(String creator)} -> Gets a
 * list of the user's quizzes titles.</li>
 * <li>{@link quizproject.QuizManager#getQuiz_info()} -> A list of the quiz
 * metadata.</li>
 * </ol>
 *
 * @author nikensonmidi
 */
@ManagedBean(name = "QM")
@SessionScoped
public final class QuizManager extends Management implements Serializable {

    /**
     * Injecting Viewmanger in order to access QuizManager
     */
    @ManagedProperty(value = "#{VM}")
    private ViewManager vm;
    /**
     * This quiz object save the set of question per GET request
     */
    private Quiz quiz;//set of a question and its key  
    /**
     * This list of quizzes save the question sets and prepare them for database
     * entry.
     */
    private List<Quiz> quizs;

    /**
     * Supplementary properties for quiz management however cannot be part of
     * QuizManager database entries.
     */
    private Supplement supplement;
    private Random i;

    private  Hashtable<String, String> temp;
   
//******************************** Constructors and initiations********************
//================================================================================= 

    public QuizManager() {

    }

    @PostConstruct
    public void init() {
  quiz = new Quiz();
        quizs = new ArrayList<>();
      
temp = new Hashtable<>();
    }
    
  

    public String reinit() {
        //saveQ();
        System.out.println(" reinit is invoked and quiz key is "+quiz.getKeys());
if(quiz.getKeys() != null || quiz.getSentence() !=null){
    saveQ(quiz.getSentence(),quiz.getKeys());
}
        quiz = new Quiz();

        return null;
    }

//******************************** Getters and setters ****************************
    //================================================================================= 
    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz q) {
        this.quiz = q;
    }

    public List<Quiz> getQuizs() {
        return quizs;
    }

    public void setQuizs(List<Quiz> quizs) {
        this.quizs = quizs;
    }

    public ViewManager getVm() {
        return vm;
    }

    public void setVm(ViewManager vm) {
        this.vm = vm;
    }

  
   
//******************************** saveInfo() *************************************
//================================================================================= 
    /**
     * Save the quiz info in mongoDB. without this information the quiz cannot
     * be saved.
     *
     * @return String
     */
    public String saveInfo() {
        String author = getVm().getUser().getUserLogin();
        if (quiz.getCategory() != null && quiz.getTitle() != null) {

            String title = formatTitle(quiz.getTitle());
            String id = author + title + quiz.getCategory();
            quiz.setId(id);
            quiz.setAuthor(author);
            quiz.setTitle(quiz.getTitle());
            quiz.setCategory(quiz.getCategory());
            quiz.setQuizDate(LocalDate.now().toString());

            if ("success".equals(mongoStore(quiz)) && !checkMongo(id)) {

                getVm().getP()[0].setRendered(true);
                getVm().getP()[0].setStyle("display: block;");
                return null;
            } else if (checkMongo(id)) {
                addMessage("msgs1", " Exam name already exist in your folder",
                        "Failed!");

                quiz = mongoQuery(author).stream()
                        .filter((Quiz e) -> e.getId().equals(id))
                        .findFirst()
                        .get();
                getVm().getP()[0].setRendered(true);
                getVm().getP()[0].setStyle("display: block;");

                return null;
            } else {
                addMessage("msgs1", " Database problems",
                        "Failed!");
                return null;
            }

        } else {
            addMessage("msgs1", " Title and category need to be filled out",
                    "Failed!");
            return null;
        }

    }
//******************************** saveQ() ****************************************
//================================================================================= 

    /**
     * saveQ() saves the question set into the mongoDB.
     * @param sentence
     * @param keys
     */
    public void saveQ(String sentence, String keys) {

            for (String key : keys.split(",")) {
                if (!sentence.contains(key.trim())) {
                    addMessage("msgs1", " Check keys: " + keys, " there is a key that is not in the sentence ");

                } else {

                    temp.put(sentence, keys);
                    quiz.setId(purgeQuiz().getId()); //reseting the id to preserve state
                    quiz.setBundle(temp);
                    mongoStore(quiz);

                }
            }//end for loop String key
  

    }

//******************************** purgeQuiz()*************************************
//================================================================================= 
    /**
     * return the first quiz object in the list of quizzes.
     *
     * @return Quiz
     */
    public Quiz purgeQuiz() {
        Supplier<Quiz> quizSupplier = Quiz::new;
        return quizs.stream()
                .findFirst()
                .orElseGet(quizSupplier);

    }//end of purgeQuiz()

//******************************** makeAnagram(String word)************************
//=================================================================================   
    /**
     * Accept a string parameter and return its anagram.
     *
     * @param <String> word
     * @return String
     */
    public String makeAnagram(String word) {
        i = new Random();
        int old_char = i.nextInt(word.length());
        int new_char = i.nextInt(word.length());
        if (word.charAt(old_char) == word.charAt(new_char)) {
            old_char = (old_char == 0) ? old_char + 1 : old_char - 1;
            return word.replace(word.charAt(old_char), word.charAt(new_char));
        }
        return word.replace(word.charAt(old_char), word.charAt(new_char));
    }//end of makeAnagram(String word)

//******************************** questionGen()***********************************
//=================================================================================  
    /**
     * Generate an array that has the question (String), the answer (String) and
     * the possible answers (List)
     *
     * @return Object[]
     */
    public Object[] questionGen() {
        i = new Random();
        String author = getVm().getUser().getUserLogin();
        Hashtable<String, String> bundle = quiz.getBundle();
        //author = vm.getUser().getUserLogin();
        String id = author + quiz.getTitle() + quiz.getCategory();
        quiz = mongoQuery(author).stream()
                .filter(e -> e.getId().equals(id))//get it from list

                .findFirst()
                .orElse(new Quiz());

        List<String> possible_answers = bundle.values()
                .stream()
                .map(e -> e.split(","))
                .map(e -> e[i.nextInt(e.length)])
                .map(e -> e.trim())
                .sorted()
                .distinct()
                .limit(4)
                .collect(Collectors.toList());

        int rand_of_all = i.nextInt(possible_answers.size());

        CharSequence answer = possible_answers.get(rand_of_all);
        String question = bundle
                .keySet().stream()
                .filter(e -> e.contains(answer))
                .map(e -> e.replace(answer, " ______ "))
                .findAny()
                .orElse("This key: " + answer + " doesn't match any of the sentence");

        //Each question need to have 4 possible answers
        if (possible_answers.size() < 4) {
            for (int complete = 0; complete < 5 - possible_answers.size(); complete++) {
                possible_answers.add(makeAnagram(possible_answers.get(complete)));

            }
        }
        Collections.shuffle(possible_answers);

        quiz = new Quiz();
        quiz.setQuestion(question);
        quiz.setAnswer((String) answer);
        Object[] delivery = {question, answer, possible_answers};
        return delivery;

    }//end of questionGen();

//******************************** makeT_or_F()***********************************
//================================================================================= 
    /**
     * Generates an array that has the question (String) and the boolean answer
     * associated with it.
     *
     * @return Object[]
     */
    public Object[] makeT_or_F() {
        Object[] temp;
        String question;
        temp = questionGen();

        boolean isTrue = i.nextBoolean();
        if (isTrue == true) {

            question = ((String) temp[0]).replace(" ______ ", ((String) temp[1]));//similar space from questionGen()
            Object[] delivery = {question, isTrue};
            return delivery;
        } else {

            question = ((String) temp[0]).replace(" ______ ", makeAnagram(((String) temp[1])));//similar space from questionGen()
            Object[] delivery = {question, isTrue};
            return delivery;
        }

    }//end of makeT_or_F(int amount)
//
//******************************** multiple_listGen(int amount)********************
//=================================================================================  

    /**
     * Generates a list of multiple choice questions based on the value of
     * amount.
     *
     * @param <int> amount
     * @param <String> title
     * @return List(quiz)
     */
    public List<Quiz> multiple_listGen(int amount, String title) {
        quiz.setTitle(title);
        Object[] temp;
        quizs = new ArrayList();
        for (int how_many = 0; how_many < amount; how_many++) {

            temp = questionGen();

            quiz = new Quiz();
            quiz.setQuestion(") " + (String) temp[0]);
            quiz.setAnswer("answer: " + (String) temp[1]);
            quiz.setPossible_answers((ArrayList<String>) temp[2]);
            quizs.add(quiz);
        }
        return quizs;
    }//end of question_listGen()

//******************************** trueORfalse_listGen(int amount)*****************
//=================================================================================
    /**
     * Generates a list of true or false questions based on the value of amount.
     *
     * @param <int> amount
     * @param <String> title
     * @return List(Quiz)
     *
     */
    public List<Quiz> trueORfalse_listGen(int amount, String title) {
        quiz.setTitle(title);
        HashMap<String, Boolean> duplicates = new HashMap();

        quizs = new ArrayList();
        for (int how_many = 0; how_many < amount; how_many++) {
            //Get the questions and the answer
            duplicates.put((String) makeT_or_F()[0], (Boolean) makeT_or_F()[1]);
        }
        quizs = duplicates.keySet().stream()
                .distinct()
                .map(e -> {
                    quiz.setQuestion(") " + e);
                    quiz.setAnswer("answer: " + duplicates.get(e));
                    return quiz;
                }).collect(Collectors.toList());

        return quizs;
    }//end of trueORfalse_listGen(int amount)

//******************************** getQuizTitles(String creator)*******************
//=================================================================================  
    /**
     * Get the titles of the quizzes that belong to this creator.
     *
     * @param <String> creator
     * @return list<String>
     *
     */
    public List<String> getQuizTitles(String creator) {
        String author = getVm().getUser().getUserLogin();
        List<String> titles = mongoQuery(author).stream()
                .map(e -> e.getTitle())
                .collect(Collectors.toList());

        return titles;

    }

//******************************** saveInfo() *************************************
//================================================================================= 
    /**
     * Format the title of the quiz before it can be joined with author name to
     * form the id. Take away the space That is inside the string and remplace
     * it with underscore and if there is an underscore replace with a space.
     *
     * @return String
     */
    public static String formatTitle(String title) {
        char[] temp = title.trim().toCharArray();
        int count = 0;
        for (char c : temp) {
            if (c == ' ') {
                temp[count] = '_';
            } else if (c == '_') {
                temp[count] = ' ';
            }
            count++;
        }

        return String.valueOf(temp);

    }

    /**
     * Extra elements that are not associated with the QuizManager object. These
     * are extra services not provided by QuizManager such as an ArrayList that
     * collect user options for analysis. Consequently, QuizManger properties
     * would not have nullified values such a counter that would be inserted
     * into the mongoDB 4unnecessarily.
     *
     */
    class Supplement {

        /**
         * Use to enumerate or keep score
         */
        private int counter;
        private ArrayList<String> options;

        public Supplement() {
            this.options = new ArrayList<String>();
        }

        public int getCounter() {
            return counter++;
        }

        public ArrayList<String> getOptions() {

            return options;
        }

    }//end of class Supplement

}//end of class QuizManager
