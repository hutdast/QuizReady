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

import KeywordsModel.AppModels;
import KeywordsModel.KeywordUser;
import KeywordsModel.Quiz;
import KeywordsModel.Serviceable;

/**
 *
 * @author mayhem
 */
public final class AppOps
{
    public static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/keywords";
    public static final String DB_USERNAME = "********";
    public static final String DB_PASSWORD = "******y*";
    public static final String HOST = "localhost";
    public static final int PORT = 27017;
    public static final String DB = "mydata";
    //******************************** formatTitle(String title) **********************
    //=================================================================================
    /**
     * Format the title of the quiz before it can be joined with author name to
     * form the id. Take away the space That is inside the string and replace
     * it with underscore and if there is an underscore replace with a space.
     *
     * @param title
     * @return String
     */
    public static String formatTitle(String title)
    {
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
    
    //******************************** getModel(AppModels type) **********************
    //=================================================================================
    /**
     * Return a specific entity object Quiz or KeywordUser
     *
     * @param type
     * @return String
     */
    public static Serviceable getModel(AppModels type)
    {
        Serviceable tempObj;
        switch (type) {
            case KEYWORD_USER:
                tempObj = new KeywordUser();
                break;
            case QUIZ:
                tempObj = new Quiz();
                
                break;
            default:
                tempObj = null;
                break;
        }
        
        return tempObj;
    }//end of getModel
    
    public static String verifyEntry(String sentence, String keys){
        String isVerified = "success";
        String delimeter = "\\s|,|\\.";
        String[] splitSentence = sentence.split(" ");
        
        for (String key : keys.split(delimeter))
        {
            for(String element : splitSentence)
            {
                if(!element.equals( key.trim() ))
                {
                    isVerified =  " The key " + key + " doesn't match any word in the corresponding sentence";
                    return isVerified ;
                }
            }
            if (sentence.equals("") || key.equals(""))
            {
                isVerified =  "Sentence and keys cannot be left empty";
                return isVerified ;
            }
        }
        
        System.out.println("verified is about leave and isverified is..: "+isVerified);
        
        return isVerified;
    }
    
    
}//end of AppOps
