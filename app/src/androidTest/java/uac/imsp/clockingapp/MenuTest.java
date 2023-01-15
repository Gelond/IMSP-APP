package uac.imsp.clockingapp;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import uac.imsp.clockingapp.Controller.control.MenuController;
import uac.imsp.clockingapp.View.util.IMenuView;

@RunWith(AndroidJUnit4ClassRunner.class)
public class MenuTest implements IMenuView {
    //private final Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    private final MenuController menuPresenter;
    public MenuTest(){
        menuPresenter=new MenuController(this);
        assertNotNull(menuPresenter);
    }
    @Test
    public void testLanguageMenu(){
        new MenuTest();
        assertNotNull(menuPresenter);
        menuPresenter.onLanguageMenu();
        menuPresenter.onLanguageSelected(0);
    }

    @Override
    public void onLanguageMenu() {

    }

    @Override
    public void changeLanguageTo(String lang) {
        assertNotNull(lang);
        assertNotEquals("",lang);
        //assertEquals("en",lang);

    }
}

