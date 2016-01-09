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

import KeywordsModel.DataManagement;
import KeywordsModel.Navigation;
import KeywordsModel.Quiz;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author mayhem
 */
@ManagedBean(name = "TD")
@SessionScoped
public class TreeDisplayController extends DataManagement {

    private TreeNode root;
    private TreeNode selectedNode;
    private Navigation[] destination;// make this an array of destination
    @ManagedProperty(value = "#{LC}")
    private LoginController loginBean;
    private String crossedOver;

    @PostConstruct
    public void init() {
        root = new DefaultTreeNode("Root", null);

        String author = loginBean.getUser().getUserLogin();
        List<Quiz> tempList = ((List<Quiz>) mongoOps(author, "quizzes", null));
        List<String> categories = tempList.stream()
                .map(e -> e.getCategory())
                .distinct()
                .collect(Collectors.toList());
  int track = 0;
   destination = new Navigation[tempList.size()];
        for (String category : categories) {        
            TreeNode node0 = new DefaultTreeNode("Category..: "+category, root);
            for (Quiz quiz : tempList) {
           
                if (quiz.getCategory().equals(category)) {
                   
                    destination[track] = new Navigation(quiz.getTitle(), quiz.getId());//?quizID="+quiz.getId()
                    node0.getChildren().add(new DefaultTreeNode(destination[track]));
                    track++;
                }

            }
            root.getChildren().add(node0);
        }
    }

    
    public TreeNode getRoot() {
        return root;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public LoginController getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginController loginBean) {
        this.loginBean = loginBean;
    }

    public Navigation[] getDestination() {
        return destination;
    }

    public void setDestination(Navigation[] destination) {
        this.destination = destination;
    }

    public String getCrossedOver() {
        return crossedOver;
    }

    public void setCrossedOver(String crossedOver) {
        this.crossedOver = crossedOver;
    }




    public void onNodeSelect(NodeSelectEvent event) throws IOException {
    
          for(Navigation obj: destination){
              if(obj.getName().equals(selectedNode.getData().toString())){
               setCrossedOver(obj.getParamID()) ;
              
FacesContext context = FacesContext.getCurrentInstance();
context.getApplication().getNavigationHandler().handleNavigation(context, null, "quiz_assembler.xhtml");
                  
              }
             
          }
        
     
    }

}//end of class TreeDisplayController
