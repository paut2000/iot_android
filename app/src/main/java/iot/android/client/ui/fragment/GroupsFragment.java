package iot.android.client.ui.fragment;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import iot.android.client.App;
import iot.android.client.R;
import iot.android.client.databinding.FragmentGroupsBinding;
import iot.android.client.model.House;
import iot.android.client.model.device.AbstractDevice;
import iot.android.client.model.group.DeviceGroup;
import iot.android.client.ui.view.DeviceView;
import iot.android.client.ui.view.GroupView;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GroupsFragment extends Fragment {

    @Inject
    House house;

    private LinearLayout groupsContainer;
    private TextView noGroupsView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groups, container, false);

        FragmentGroupsBinding binding = FragmentGroupsBinding.bind(view);
        groupsContainer = binding.groupsContainer;
        noGroupsView = binding.noGroupsView;

        App.getFragmentComponent().inject(this);

        init();

        return view;
    }

    public void init() {
        DeviceGroup group = new DeviceGroup();
        group.setName("Cool group");
        try {
            group.addDevice(house.getDevice("device-1"));
            group.addDevice(house.getDevice("device-2"));
            group.addDevice(house.getDevice("device-3"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        DeviceGroup group2 = new DeviceGroup();
        group2.setName("Fuck group");
        try {
            group2.addDevice(house.getDevice("device-2"));
            group2.addDevice(house.getDevice("device-1"));
            group2.addDevice(house.getDevice("device-4"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        DeviceGroup group3 = new DeviceGroup();
        group3.setName("Yeeeeeh group");
        try {
            group3.addDevice(house.getDevice("device-1"));
            group3.addDevice(house.getDevice("device-4"));
            group3.addDevice(house.getDevice("device-5"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        groupsContainer.addView(new GroupView(getContext(), group));
        groupsContainer.addView(new GroupView(getContext(), group2));
        groupsContainer.addView(new GroupView(getContext(), group3));
    }

}