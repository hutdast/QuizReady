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
package KeywordsOp;


import KeywordsModel.Quiz;
import KeywordsModel.DataManagement;
import KeywordsModel.Serviceable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
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

public final class QuizManager extends DataManagement 
{


//******************************** Constructors and initiations********************
//================================================================================= 

    public QuizManager() {

    }

 
  


//******************************** makeAnagram(String word)************************
//=================================================================================   
    /**
     * Accept a string parameter and return its anagram.
     *
     * @param word
     * @return String
     */
    /**
    private void saveExam(Quiz q) {
        System.out.print("saveExam() invoked:.. ");
        prepBundle(q.getSentence(), q.getKeys());
        mongoStore(storedQuiz);


        System.out.print("Inside quizs:.. " + quizs.size());

        System.out.print("size of temp:.. " + temp.size());
        System.out.print("size of storedQuiz:.. " + storedQuiz.getBundle().size());
        System.out.print("leaving saveExam():.. ");
    }
//******************************** makeAnagram(String word)************************
//=================================================================================   

    /**
     * Accept a string parameter and return its anagram.
     *
     * @param word
     * @return String
     */
    /**
    private String makeAnagram(String word) {
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
    /**
    private Object[] questionGen() {
        i = new Random();
        String author = storedQuiz.getAuthor();
        HashMap<String, String> bundle = storedQuiz.getBundle();
        String title = formatTitle(storedQuiz.getTitle());
        String category = formatTitle(storedQuiz.getCategory());
        String id = author + title + category;
        quiz = mongoQuery(author).parallelStream()
                .filter(e -> e.getId().equals(id))//get it from list

                .findFirst()
                .orElse(new Quiz());//Program crash prevention

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
                .orElse("This key: " + answer + " doesn't match any word of the sentence");

        //Each question need to have 4 possible answers
        if (possible_answers.size() < 4) {
            for (int currentIndex = 0; currentIndex < 5 - possible_answers.size(); currentIndex++) {
                possible_answers.add(makeAnagram(possible_answers.get(currentIndex)));

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
    /**
    private Object[] makeT_or_F() {
        Object[] tempLocal;
        String question;
        tempLocal = questionGen();

        boolean isTrue = i.nextBoolean();
        if (isTrue == true) {

            question = ((String) tempLocal[0]).replace(" ______ ", ((String) tempLocal[1]));//similar space from questionGen()
            Object[] delivery = {question, isTrue};
            return delivery;
        } else {

            question = ((String) tempLocal[0]).replace(" ______ ", makeAnagram(((String) tempLocal[1])));//similar space from questionGen()
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
     * @param amount
     * @param title
     * @return List(quiz)
     */
    /**
    public List<Quiz> multiple_listGen(int amount, String title) {
        quiz.setTitle(title);
        Object[] tempLocal;
        quizs = new ArrayList();
        for (int how_many = 0; how_many < amount; how_many++) {

            tempLocal = questionGen();

            quiz = new Quiz();
            quiz.setQuestion(") " + (String) tempLocal[0]);
            quiz.setAnswer("answer: " + (String) tempLocal[1]);
            quiz.setPossible_answers((ArrayList<String>) tempLocal[2]);
            quizs.add(quiz);
        }
        return quizs;
    }//end of question_listGen()

//******************************** trueORfalse_listGen(int amount)*****************
//=================================================================================
    /**
     * Generates a list of true or false questions based on the value of amount.
     *
     * @param amount
     * @param title
     * @return List(Quiz)
     *
     */
    /**
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
     * @param creator
     * @return list<String>
     *
     */
    /**
    public List<String> getQuizTitles(String creator) {
        String author = getVm().getUser().getUserLogin();
        List<String> titles = mongoQuery(author).parallelStream()
                .map(e -> e.getTitle())
                .collect(Collectors.toList());

        return titles;

    }
*/
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
            this.options = new ArrayList<>();
        }

        public int getCounter() {
            return counter++;
        }

        public ArrayList<String> getOptions() {

            return options;
        }

    }//end of class Supplement

}//end of class QuizManager
