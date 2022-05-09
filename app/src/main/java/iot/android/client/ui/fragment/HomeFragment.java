package iot.android.client.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import iot.android.client.App;
import iot.android.client.R;
import iot.android.client.databinding.FragmentHomeBinding;
import iot.android.client.model.House;
import iot.android.client.ui.view.device.DevicesView;

import javax.inject.Inject;

public class HomeFragment extends Fragment {

    private FrameLayout placeForDevicesContainer;

    private HomeFragmentVM viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        FragmentHomeBinding binding = FragmentHomeBinding.bind(view);
        placeForDevicesContainer = binding.placeForDevicesContainer;

        viewModel = new ViewModelProvider(this).get(HomeFragmentVM.class);

        viewModel.getHouseLiveData().observe(this, house -> {
            refillDeviceContainer(house);
        });

        return view;
    }

    private void refillDeviceContainer(House house) {
        placeForDevicesContainer.removeAllViews();
        placeForDevicesContainer.addView(new DevicesView(getContext(), house.getDevices().values()));
    }

}