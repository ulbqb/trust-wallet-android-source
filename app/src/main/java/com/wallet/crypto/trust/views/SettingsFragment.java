package com.wallet.crypto.trust.views;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import com.wallet.crypto.trust.R;
import com.wallet.crypto.trust.controller.Controller;
import com.wallet.crypto.trust.model.VMNetwork;

import java.util.List;

public class SettingsFragment extends PreferenceFragment {
    private Controller mController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.fragment_settings);

        mController = Controller.get();

        final Preference export = findPreference("pref_export");

        export.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                mController.navigateToExportAccount(getActivity(), mController.getCurrentAccount().getAddress());
                return false;
            }
        });

        final ListPreference listPreference = (ListPreference) findPreference("pref_rpcServer");

        // THIS IS REQUIRED IF YOU DON'T HAVE 'entries' and 'entryValues' in your XML
        setRpcServerPreferenceData(listPreference);

        listPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                setRpcServerPreferenceData(listPreference);
                return false;
            }
        });
    }

    private void setRpcServerPreferenceData(ListPreference lp) {
        List<VMNetwork> networks = mController.getNetworks();

        assert(networks.size() > 0);

        CharSequence[] entries = new CharSequence[networks.size()];
        for (int ii = 0; ii < networks.size(); ii++) {
            entries[ii] = networks.get(ii).getName();
        }

        CharSequence[] entryValues = new CharSequence[networks.size()];
        for (int ii = 0; ii < networks.size(); ii++) {
            entryValues[ii] = networks.get(ii).getName();
        }

        lp.setEntries(entries);
        lp.setDefaultValue(entryValues[0]);
        lp.setEntryValues(entryValues);
    }
}

