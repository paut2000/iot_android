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
    private SwipeRefreshLayout swipeRefreshLayout;

    private HomeFragmentVM viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        FragmentHomeBinding binding = FragmentHomeBinding.bind(view);
        placeForDevicesContainer = binding.placeForDevicesContainer;
        swipeRefreshLayout = binding.swipeRefresh;

        viewModel = new ViewModelProvider(this).get(HomeFragmentVM.class);

        createSwipeRefreshLayout(viewModel.getHouseLiveData().getValue());

        viewModel.getHouseLiveData().observe(this, house -> {
            fillDeviceContainer(house);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void createSwipeRefreshLayout(House house) {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            house.refresh(() -> {
                refillDeviceContainer(house);
                swipeRefreshLayout.setRefreshing(false);
            });
            new Handler().postDelayed(() -> {
                if (swipeRefreshLayout.isRefreshing()) {

                    // TODO: обработать ошибку: невозможно получить обновление

                    swipeRefreshLayout.setRefreshing(false);
                }
            }, 3000);
        });
    }

    private void fillDeviceContainer(House house) {
        house.refresh(() -> refillDeviceContainer(house));
    }

    private void refillDeviceContainer(House house) {
        placeForDevicesContainer.removeAllViews();
        placeForDevicesContainer.addView(new DevicesView(getContext(), house.getDevices().values()));
    }

}