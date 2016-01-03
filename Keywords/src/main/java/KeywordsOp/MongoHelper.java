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

import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 *
 * @author mayhem
 */
public class MongoHelper 
{
       
        private static MongoClient mongo = null;
   protected MongoHelper() 
   {
      // Exists only to defeat instantiation.
   }
   
   
   public static MongoClient getInstance() throws UnknownHostException
   {
      if(mongo == null)
      { 
               mongo = new MongoClient(AppOps.HOST, AppOps.PORT);
      }
      return mongo;
   }
    
    
}//end of MongoHelper
