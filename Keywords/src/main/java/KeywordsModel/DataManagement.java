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

import KeywordsOp.MongoHelper;
import KeywordsOp.AppOps;
import com.mongodb.MongoClient;
import java.io.Serializable;
import java.net.UnknownHostException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

/**
 * Management is the entrypoint into all the databases. Management is the parent
 * of {@link quizproject.QuizManager},
 * {@link quizproject.UserValidator} and {@link quizproject.ViewChanger}. The
 * following methods are available:<br />
 * <ol>
 * <li>{@link quizproject.Management#createNewUser(String username, String password,String email)}
 * -> Create a new user.</li>
 * <li>{@link quizproject.Management#logNpass(String username,String password)}
 * -> Check if the user is in the database.</li>
 * <li>{@link quizproject.Management#mongoQuery(String creator)} -> Requests a
 * list of all the quizzes created by the user.</li>
 * <li>{@link quizproject.Management#mongoStore(String _id, String creator, Quiz quiz, String option)
 * } -> Stores the new/updated quiz.</li>
 * <li>{@link quizproject.Management#addMessage(String component, String summary, String detail)}
 * -> Generates front end messages.</li>
 * </ol>
 *
 * @author nikensonmidi
 * @param <T>
 */
public class DataManagement implements Serializable
{
    
    
    private  static MongoClient mongo;
    private  Morphia morphia;
    private java.sql.Connection connection;
    
    
    
    
    //******************************** logNpass ***************************************
    //=================================================================================
    /**
     * Validate the authenticity of the user in the MSQLDB give him/her access
     * into the program.
     *
     * @param ulogin
     * @param upass
     * @return boolean
     */
    protected boolean logNpass(String ulogin, String upass)
    {
        
        Statement statement;
        boolean isCorrect = false;
        List<String> name;
        
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(AppOps.DB_URL, AppOps.DB_USERNAME, AppOps.DB_PASSWORD);
            statement = (Statement) connection.createStatement();
            ResultSet take = statement.executeQuery("call verify('" + ulogin + "', '" + upass + "');");
            name = new ArrayList();
            while (take.next()) {
                name.add(take.getString("x"));
            }
            
            isCorrect = name.stream()
            .anyMatch(e -> e.equalsIgnoreCase("match"));
            
            connection.close();
        } catch (SQLException e)
        {
            addMessage(null, "DB problem", "cannot log into database" + e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataManagement.class.getName()).log(Level.SEVERE, null, ex);
            addMessage(null, "DB problem", "cannot log into database" + ex.getMessage());
        }
        
        return isCorrect;
        
    }//end of logNpass()
    
    //******************************** createNew **************************************
    //=================================================================================
    /**
     * Create a new user and store the info into the MSQLDB. It Creates a new
     * user by calling the MSQL function createUser with these informations the
     * username (log), the newly created password (pass) and the provided email
     * (email).
     *
     * @param log
     * @param pass
     * @param email
     */
    protected void createNewUser(String log, String pass, String email)
    {
        
        String query = "call createUser('" + log + "', '" + pass + "' ,'" + email + "');";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(AppOps.DB_URL, AppOps.DB_USERNAME, AppOps.DB_PASSWORD);
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.execute();
            connection.close();
        } catch (SQLException e)
        {
            
            addMessage(null, "DB problem", "cannot log into database" + e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//end of createNew()
    
    
    //******************************** addMessage *************************************
    //=================================================================================
    /**
     * The error message to call for client side errors
     *
     * @param component
     * @param summary
     * @param detail
     */
    protected void addMessage(String component, String summary, String detail)
    {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(component, message);
        
    }// end of addMessage
    
    //******************************** mongoOps()************************************
    //=================================================================================
    /**
     * Module in charge of all Mongo db operations, in this way there is one instance
     * of MongoClient is created.
     *
     * @param searchKey
     * @param option
     * @param quiz
     * @return List(QuizManager)
     */
    protected  Object mongoOps(String searchKey, String option, Quiz quiz)
    {
        Object result = null;
        try {
            mongo = new MongoClient(AppOps.HOST, AppOps.PORT);
            morphia = new Morphia();
            morphia.mapPackage("quizproject");//map to the index of the entity
            Datastore datastore = morphia.createDatastore(mongo, AppOps.DB);
            Query query;
            switch(option){
                case "quiz":
                    query = datastore.createQuery(Quiz.class).field("_id").equal(searchKey);
                    result = (Quiz) query.get();
                    break;
                case "quizzes":
                    query = datastore.createQuery(Quiz.class).field("userLogin").equal(searchKey);
                    result = query.asList();
                    break;
                case "save":
                    datastore.ensureIndexes();
                    datastore.save(quiz);
                    result = "success";
            }
            mongo.close();
            return result;
            
        } catch (UnknownHostException e)
        {
            Logger.getLogger(Quiz.class.getName()).log(Level.SEVERE, null, e);
            addMessage(null, "DB problem", "cannot log into database" + e.getMessage());
            return null;
        }
        
    }//end of mongoQuery
    
    
    
    
    
}//end of class
