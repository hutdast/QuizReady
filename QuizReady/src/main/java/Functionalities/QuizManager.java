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
package Functionalities;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;

/**
 * QuizManager manages accesses and manipulations of all Quiz objects.
 * QuizManager is a ManagedBean(name=QM) and takes requests from the front end
 * and sends out the proper response. The following methods are available:<br />
 * <ol>
 * <li>{@link quizproject.QuizManager#init()} -> Establishes the initial state of the session (Part of the session chain.</li>
 * <li>{@link quizproject.QuizManager#createNew()} -> Add the previous question object and create a new one (part of the session chain).</li>
 * <li>{@link quizproject.QuizManager#makeAnagram(String word)} -> Return a permutation of a word.</li>
 * <li>{@link quizproject.QuizManager#makeT_or_F()} -> Make true of false question.</li>
 * <li>{@link quizproject.QuizManager#multiple_listGen(int amount, String title)}-> Makes a list of multiple-choice question.</li>
 * <li>{@link quizproject.QuizManager#purgeQuiz()}  ->Get the first quiz object saved from the session.</li>
 * <li>{@link quizproject.QuizManager#questionGen()} -> Generate one question.</li>
 * <li>{@link quizproject.QuizManager#reinit()} -> Re-establishes state of the session upon saving the quiz object (part of the session chain).</li>
 * <li>{@link quizproject.QuizManager#saveInfo()} ->  Save the preliminary info of the quiz.</li>
 * <li>{@link quizproject.QuizManager#saveQ()}  ->  Save all the questions.</li>
 * <li>{@link quizproject.QuizManager#trueORfalse_listGen(int amount, String title)} -> Makes a list of True or false questions.</li>
 * <li>{@link quizproject.QuizManager#getQuizTitles(java.lang.String)}   ->  Gets a list of the user's quizzes titles.</li>
 * <li>{@link quizproject.QuizManager#getQuizTitles(String creator)}   ->  Gets a list of the user's quizzes titles.</li>
 * <li>{@link quizproject.QuizManager#getQuiz_info()}  ->  A list of the quiz metadata.</li>
 * </ol>
 *
 * @author nikensonmidi
 */
public final class QuizManager extends Management implements Serializable{
 /**
     * the _id get each individual quiz from mongoDB
     */
    private String _id;
    /**
     * Creator that is associated with a specific list the quizzes
     */
    private final String creator;
      /**
     *This quiz object save the set of question per GET request
     */
    private Quiz quiz;//set of a question and its key  
     /**
     *This list of quizzes save the question sets and prepare them for database entry.
     */
    private List<Quiz> quizs;
     /**
     *This list of quizzes save the question info and store it into MongoDB.
     */
    private List<Quiz> quiz_info;
     /**
     *Supplementary properties for quiz management however cannot be part of
     * QuizManager database entries.
     */
    private Supplement supplement;
    private Random i; 
    
    
//******************************** Constructors and initiations********************
//================================================================================= 

    public QuizManager(String creator) {
        this.creator = creator;
    }

    @PostConstruct
    public void init() {

        quiz = new Quiz();
        quizs = new ArrayList<Quiz>();

    }

    public void createNew() {

        quizs.add(quiz);
        quiz = new Quiz();

    }//end of createNew

    public String reinit() {

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

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    

    public List<Quiz> getQuiz_info() {
        return quiz_info;
    }

    public void setQuiz_info(List<Quiz> quiz_info) {
        this.quiz_info = quiz_info;
    }

    public String getCreator() {
        return creator;
    }

  
    

//******************************** saveInfo() *************************************
//================================================================================= 
    /**
     *Save the quiz info in mongoDB. without this information the quiz cannot be 
     * saved.
     *
     * @return String
     */    
    public String saveInfo() {
        if (quiz.getCategory() != null && quiz.getTitle() != null) {
            quiz_info = new ArrayList<Quiz>();
            quiz_info.add(quiz);
            quiz.setDate(LocalDate.now().toString());
           

            mongoStore(getCreator() + quiz.getTitle(), getCreator(), quiz, "new");

            return "PF('panel2').toggle()";
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
     *
     * @return String
     */    
    public String saveQ() {
        HashMap<String, String> temp = new HashMap();

        if (quizs.isEmpty()) {
            addMessage("msgs1", "Failure ", "There are no questions or keys entered ");
            return null;
        } else {
            int numberAt = 0;

            for (Quiz element : quizs) {
                numberAt++;

                for (String key : element.getKey().split(",")) {
                    if (!element.getQuestion().contains(key.toLowerCase().trim())) {

                        addMessage("msgs1", " Check keys at:" + numberAt, " there is a key that is not in the sentence ");
                        return null;
                    }
                }
                temp.put(element.getQuestion(), element.getKey());
            }

            quiz.setQuestion(null);//interference with mongo entry--empty quiz object before saving
            quiz.setKey(null);//interference with mongo entry--empty quiz object before saving

            quiz.setQuestionSet(temp);

            mongoStore(getCreator() + quiz_info.get(0).getTitle(), null, quiz, "update");
            return "quiztest.xhtml";
        }

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
     *@param <String> word
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
     * Generate an array that has the question (String), the answer (String)
     * and the possible answers (List)
     *
     * @return Object[]
     */
    public Object[] questionGen() {
        i = new Random();

        quiz = mongoQuery(getCreator()).stream()
                .filter(e -> e.getId().equals(getCreator() + quiz.getTitle()))//get it from list
                .map(e -> e.getQuiz())
                .findFirst()
                .orElse(new Quiz());

        List<String> possible_answers = quiz.getQuestionSet().values()
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
        String question = quiz
                .getQuestionSet().keySet().stream()
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
     * Generates a list of multiple choice questions based on the value
     * of amount.
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
     * Generates a list of true or false questions based on the value
     * of amount.
     *
     * @param <int> amount
     * @param <String> title
     * @return List(Quiz)
     * */
    
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
     * @param <String> creator
     * @return list(Quiz)
     * */  
    public List<String> getQuizTitles(String creator) {
        List<String> titles = mongoQuery(getCreator() + quiz.getTitle()).stream()
                .map(e -> e.getQuizs())
                .findAny()
                .orElse(new ArrayList<Quiz>())
                .stream()
                .map(e -> e.getTitle())
                .collect(Collectors.toList());

        return titles;

    }



    /**
     * Extra elements that are not associated with the QuizManager object. These
     * are extra services not provided by QuizManager such as an ArrayList that
     * collect user options for analysis. Consequently, QuizManger properties
     * would not have nullified values such a counter that would be inserted into the
     * mongoDB 4unnecessarily.
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

    }
    
    
    
}//end of class QuizManager
