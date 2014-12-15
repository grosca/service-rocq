package fr.inria.rocq.services;

import fr.inria.arles.yarta.resources.Content;
import fr.inria.arles.yarta.resources.Group;
import fr.inria.arles.yarta.resources.Person;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateActivity extends BaseActivity {

	private static final int MENU_ACCEPT = 1;

	private Group group;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem item = menu.add(0, MENU_ACCEPT, 0, R.string.callout_save);
		item.setIcon(R.drawable.icon_accept);
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_ACCEPT:
			group = (Group) getSAM().getResourceByURI(Constants.getGroupId());

			String title = getCtrlText(R.id.title);
			String description = getCtrlText(R.id.description);

			if (title.length() == 0 || description.length() == 0) {
				Toast.makeText(this, R.string.app_all_fields, Toast.LENGTH_LONG)
						.show();
			} else {
				try {
					Person me = getSAM().getMe();
					String format = getString(R.string.app_callout_format);
					String fullcontent = String.format(format, description);

					String predicate = getSpinnerText(R.id.option);
					String category = getSpinnerText(R.id.category);

					Content content = getSAM().createContent();

					content.setTitle(String.format("[%s] %s", predicate, title));
					content.setContent(String.format("#%s\r\n%s", category,
							fullcontent));

					content.setTime(System.currentTimeMillis());

					me.addCreator(content);
					group.addHasContent(content);

					finish();
				} catch (Exception ex) {
					ex.printStackTrace();
					Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG)
							.show();
				}
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private String getSpinnerText(int spinnerId) {
		Spinner sp = (Spinner) findViewById(spinnerId);
		if (sp != null) {
			return sp.getSelectedItem().toString();
		}
		return null;
	}
}
