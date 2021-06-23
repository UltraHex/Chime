package net.ultrahex.wear.chime;

import android.os.*;
import android.view.*;

import androidx.annotation.*;
import androidx.preference.*;

public class SettingsFragment extends PreferenceFragmentCompat {

	@Override
	public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
		setPreferencesFromResource(R.xml.root_preferences, rootKey);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		getListView().setPadding(0, 50, 0, 100);
	}
}