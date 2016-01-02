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
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.component.submenu.UISubmenu;
import org.primefaces.component.menubar.Menubar;
import org.primefaces.component.menuitem.UIMenuItem;
/**
 *
 * @author mayhem
 */
@ManagedBean(name="MB")
@ViewScoped
public class MenuBarController extends DataManagement
{
    private Menubar menubar;
    
    public Menubar getMenubar()
    {
        return menubar;
    }
    
    public void setMenubar(Menubar menubar)
    {
        this.menubar = menubar;
    }
    
    
    
    @PostConstruct
    public void init()
    {
        menubar = new Menubar();
        UISubmenu sub = new UISubmenu();
        sub.setLabel("Navigation");
        UIMenuItem item = new UIMenuItem();
        item.setValue("Dashboard");
        // setUrl is for outside address setOutcome for internal address
        item.setOutcome("profile.xhtml");
        sub.getChildren().add(item);
        menubar.getChildren().add(sub);
        
        
    }//end of init()
    
    
    
}//end of class
