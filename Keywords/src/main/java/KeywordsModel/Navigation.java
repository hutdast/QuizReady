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

import java.io.Serializable;
/**
 *
 * @author mayhem
 */
public class Navigation  implements Serializable, Comparable<Navigation> {
    
    
  private String name;
     
    private String paramID;

    public Navigation(String name) {
        this.name = name;
    }
   
    public Navigation(String name, String paramID) {
        this.name = name;
        this.paramID = paramID;

    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }

    public String getParamID() {
        return paramID;
    }

    public void setParamID(String paramID) {
        this.paramID = paramID;
    }

  
 

    //Eclipse Generated hashCode and equals
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((paramID == null) ? 0 : paramID.hashCode());
       
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
       Navigation other = (Navigation) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (paramID == null) {
            if (other.paramID != null)
                return false;
        } else if (!paramID.equals(other.paramID))
            return false;
      
        return true;
    }
 
    @Override
    public String toString() {
        return name;
    }
 
  @Override
    public int compareTo(Navigation document) {
        return this.getName().compareTo(document.getName());
    }
   
    
    
}//end of navigation
