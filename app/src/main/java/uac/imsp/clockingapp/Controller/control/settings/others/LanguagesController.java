package uac.imsp.clockingapp.Controller.control.settings.others;

import uac.imsp.clockingapp.Controller.util.settings.others.ILanguagesController;
import uac.imsp.clockingapp.View.util.settings.others.ILanguagesView;

public class LanguagesController implements ILanguagesController {
	ILanguagesView languagesView;

	public LanguagesController(ILanguagesView languagesView)
	{
		this.languagesView=languagesView;


	}
	@Override
	public void onLanguageChanged(String lang) {
		languagesView.onLanguageChanged(lang);


	}
}
