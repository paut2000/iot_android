package iot.android.client.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import iot.android.client.App;
import iot.android.client.R;
import iot.android.client.databinding.FragmentHomeBinding;
import iot.android.client.model.House;
import iot.android.client.ui.view.DeviceView;
import iot.android.client.ui.view.DevicesView;

import javax.inject.Inject;

public class HomeFragment extends Fragment {

    @Inject
    House house;

    private FrameLayout placeForDevicesContainer;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        FragmentHomeBinding binding = FragmentHomeBinding.bind(view);
        placeForDevicesContainer = binding.placeForDevicesContainer;
        swipeRefreshLayout = binding.swipeRefresh;

        App.getFragmentComponent().inject(this);

        createSwipeRefreshLayout();
        fillDeviceContainer();

        return view;
    }

    private void createSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            house.refresh(() -> {
                refillDeviceContainer();
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

    private void fillDeviceContainer() {
        house.refresh(this::refillDeviceContainer);
    }

    private void refillDeviceContainer() {
        placeForDevicesContainer.removeAllViews();
        placeForDevicesContainer.addView(new DevicesView(getContext(), house.getDevices().values()));
    }

}