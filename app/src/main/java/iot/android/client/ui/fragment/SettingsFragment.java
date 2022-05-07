package iot.android.client.ui.fragment;

import android.content.Context;
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
import androidx.lifecycle.MutableLiveData;
import iot.android.client.R;
import iot.android.client.databinding.FragmentSettingsBinding;
import iot.android.client.util.pinger.AddressPinger;
import iot.android.client.util.pinger.IpUtil;

public class SettingsFragment extends Fragment {

    private Button findServerButton;
    private TextView serverIpText;
    private TextView serverIpMaskText;
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
        serverIpMaskText = binding.serverIpMaskText;
        reachedServerLabel = binding.reachedServerLabel;

        init();

        return view;
    }

    private void init() {
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
        reachedServerLabel.setVisibility(View.VISIBLE);

        Button server = new Button(getContext());
        server.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        server.setTextSize(14);
        server.setText(IpUtil.byteArrayIpToStringIp(address) + ":8080");

        server.setOnClickListener(view -> {

        });

        placeForServers.addView(server);
    }

}