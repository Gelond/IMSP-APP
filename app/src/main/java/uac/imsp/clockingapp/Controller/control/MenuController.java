package uac.imsp.clockingapp.Controller.control;

import uac.imsp.clockingapp.Controller.util.IMenuController;
import uac.imsp.clockingapp.View.util.IMenuView;

public class MenuController implements IMenuController {
    IMenuView menuView;

    public MenuController(IMenuView menuView){
        this.menuView=menuView;

    }

    @Override
    public void onParametersMenu() {

    }

    @Override
    public void onDarkMode() {

    }

    @Override
    public void onLanguageMenu() {
        menuView.onLanguageMenu();

    }

    @Override
    public void onFreeMemory() {

    }

    @Override
    public void onReportProblem() {

    }

    @Override
    public void onLeave() {

    }

    @Override
    public void onLanguageSelected(int which) {
        if(which==0)
            menuView.changeLanguageTo("en");
        else if(which==1)
            menuView.changeLanguageTo("fr");


    }
}
