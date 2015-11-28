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
public abstract class Management implements Serializable {
    
    private java.sql.Connection connection;//static class variables it belongs to all objects of that class
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/keywords";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "*****************";
    private static final String HOST = "localhost";
    private static final int PORT = 27017;
    private static final String DB = "mydata";
    private MongoClient mongo;
    private Morphia morphia;
    
    
    
    
    
    
    
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
    public boolean logNpass(String ulogin, String upass) {
        
        Statement statement;
        boolean isCorrect = false;
        List<String> name;
        
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME,DB_PASSWORD);
            statement = (Statement) connection.createStatement();
            ResultSet take = statement.executeQuery("call verify('" + ulogin + "', '" + upass + "');");
            name = new ArrayList();
            while (take.next()) {
                name.add(take.getString("x"));
            }
            
            isCorrect = name.stream()
            .anyMatch(e -> e.equalsIgnoreCase("match"));
            
            connection.close();
        } catch (SQLException e) {
            addMessage(null, "DB problem", "cannot log into database" + e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Management.class.getName()).log(Level.SEVERE, null, ex);
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
    public void createNewUser(String log, String pass, String email) {
        
        String query = "call createUser('" + log + "', '" + pass + "' ,'" + email + "');";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME,DB_PASSWORD);
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.execute();
            connection.close();
        } catch (SQLException e) {
            
            addMessage(null, "DB problem", "cannot log into database" + e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Management.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//end of createNew()
    
    //******************************** mongoQuery()************************************
    //=================================================================================
    /**
     * Return a list of quizzes that belong to the creator.
     *
     * @param creator
     * @return List(Quiz)
     */
    public List<Quiz> mongoQuery(String creator) {
        
        try {
            mongo = new MongoClient(HOST, PORT);
            Datastore datastore = new Morphia().createDatastore(mongo, DB);
            
            Query query = datastore.createQuery(Quiz.class).field("_id").contains(creator);
            
            List<Quiz> q = query.asList();
            mongo.close();
            
            return q;
            
        } catch (UnknownHostException e) {
            addMessage(null, "DB problem", "cannot log into database" + e.getMessage());
            return null;
        }
        
    }//end of mongoQuery
    
    //******************************** mongoQuery()************************************
    //=================================================================================
    /**
     * Return a list of quizzes that belong to the creator.
     *
     * @param id
     * @return List(QuizManager)
     */
    public Quiz mongoQueryQ(String id) {
        try {
            mongo = new MongoClient(HOST, PORT);
            Datastore datastore = new Morphia().createDatastore(mongo, DB);
            
            Query query = datastore.createQuery(Quiz.class).field("_id").equal(id);
            
            Quiz q = (Quiz) query.asList().get(0);
            mongo.close();
            return q;
            
        } catch (UnknownHostException e) {
            addMessage(null, "DB problem", "cannot log into database" + e.getMessage());
            return null;
        }
        
    }//end of mongoQuery
    
    //******************************** mongoStore I ***********************************
    //=================================================================================
    /**
     * Creates a new quiz or updates an existing quiz from mongoDB.<br />
     * _id is the MongoDb id and it is the combination of creator+quiz
     * title.<br />
     * The <b>option</b> can be <b>new</b> or <b>update</b>. The <b>new</b> sets
     * a new id, the creator name and the quiz object. The <b>update</b>
     * retrieves the quiz object, saves it then deletes the query. The newly
     * saved item is then updated and stored back into mongoDB.
     *
     *
     * @param quiz
     * @return
     */
    public String mongoStore(Quiz quiz) {
        
        try {
            
            mongo = new MongoClient(HOST, PORT);
            morphia = new Morphia();
            morphia.mapPackage("quizproject");//map to the index of the entity
            Datastore datastore = morphia.createDatastore(mongo, DB);
            datastore.ensureIndexes();
            
            datastore.save(quiz);
            
            mongo.close();
            return "success";
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(Quiz.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("there is a problem in mongoStore()");
            addMessage(null, "Error",
                       "the errror is: " + ex.getMessage());
            return ex.getMessage();
            
        }
    }//end of mongoStore
    
    //******************************** addMessage *************************************
    //=================================================================================
    /**
     * The error message to call for client side errors
     *
     * @param component
     * @param summary
     * @param detail
     */
    public void addMessage(String component, String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(component, message);
        
    }// end of addMessage
    
    //******************************** addMessage *************************************
    //=================================================================================
    /**
     * Check mongoDb if the quiz is already in there
     *
     * @param id
     * @return
     */
    public boolean checkMongo(String id) {
        boolean isThere;
        try {
            
            mongo = new MongoClient(HOST, PORT);
            Datastore datastore = new Morphia().createDatastore(mongo, DB);
            
            Query query = datastore.createQuery(Quiz.class).field("_id").equal(id);
            
            List<Quiz> q = query.asList();
            mongo.close();
            isThere = q.parallelStream()
            .anyMatch(e -> (e.getId() == null ? id == null : e.getId().equals(id)));
            return isThere;
            
        } catch (UnknownHostException e) {
            addMessage(null, "DB problem", "cannot log into database" + e.getMessage());
            isThere = false;
            return isThere;
        }
        
    }//end of checkMongo
    
}//end of class
