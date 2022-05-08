package iot.android.client.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import iot.android.client.App;
import iot.android.client.R;
import iot.android.client.databinding.FragmentSettingsBinding;
import iot.android.client.preference.AppPreferences;
import iot.android.client.util.pinger.AddressPinger;
import iot.android.client.util.pinger.IpUtil;

public class SettingsFragment extends Fragment {

    private Button findServerButton;
    private TextView serverIpText;
    private LinearLayout placeForServers;
    private TextView reachedServerLabel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        FragmentSettingsBinding binding = FragmentSettingsBinding.bind(view);

        findServerButton = binding.findServerButton;
        serverIpText = binding.serverIpText;
        placeForServers = binding.placeForServers;
        reachedServerLabel = binding.reachedServerLabel;

        init();

        return view;
    }

    private void init() {
        SharedPreferences preferences =
                getContext().getSharedPreferences(AppPreferences.FILE_NAME, Context.MODE_PRIVATE);

        if (preferences.contains(AppPreferences.SERVER_IP_ADDRESS)) {
            String serverIp = preferences.getString(AppPreferences.SERVER_IP_ADDRESS, "");
            serverIpText.setText(serverIp);
        }

        findServerButton.setOnClickListener(view -> {
            byte[] subnet = IpUtil.getSubnet(requireContext().getApplicationContext());

            MutableLiveData<byte[]> liveData = new MutableLiveData<>();
            for (int i = 1; i < 255; i++) {
                new AddressPinger(new byte[]{ subnet[0], subnet[1], subnet[2], (byte) i },
                        8080, 300, liveData).start();
            }

            liveData.observe(this, address -> {
                onServerFind(address);
            });

        });
    }

    private void onServerFind(byte[] address) {
        String addressString = IpUtil.byteArrayIpToStringIp(address) + ":8080";
        reachedServerLabel.setVisibility(View.VISIBLE);

        Button server = new Button(getContext());
        server.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        server.setTextSize(14);
        server.setText(addressString);

        server.setOnClickListener(view -> {
            App.changeServerAddress("http://" + addressString, getActivity().getBaseContext());
            TextView dialogText = new TextView(getContext());
            dialogText.setText("* Теперь вы подключены к серверу " + addressString +
                    "\n* Чтобы просмотреть список доступных устройств перейдите во вкладку \"Дом\"");
            dialogText.setTextSize(14);
            dialogText.setPadding(50,50,50,50);
            new AlertDialog.Builder(getContext())
                    .setTitle("Подключено")
                    .setView(dialogText)
                    .setPositiveButton("Окей", (dialogInterface, i) -> {})
                    .create()
                    .show();
        });

        placeForServers.addView(server);
    }

}