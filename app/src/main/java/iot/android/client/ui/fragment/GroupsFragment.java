package iot.android.client.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import iot.android.client.App;
import iot.android.client.R;
import iot.android.client.dao.GroupDao;
import iot.android.client.databinding.FragmentGroupsBinding;
import iot.android.client.model.House;
import iot.android.client.model.group.DeviceGroup;
import iot.android.client.ui.view.group.GroupsView;

import javax.inject.Inject;

public class GroupsFragment extends Fragment {

    @Inject
    House house;

    @Inject
    GroupDao groupDao;

    private LinearLayout groupsContainer;
    private Button addGroupButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groups, container, false);

        FragmentGroupsBinding binding = FragmentGroupsBinding.bind(view);
        groupsContainer = binding.groupsContainer;
        addGroupButton = binding.addGroupButton;

        App.getFragmentComponent().inject(this);

        init();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fillGroupsContainer();
    }

    public void init() {
        addGroupButton.setOnClickListener(view -> {
            createAddGroupDialog().show();
        });

        fillGroupsContainer();
    }

    private void fillGroupsContainer() {
        groupsContainer.removeAllViews();
        groupDao.readAll().forEach(group -> {
            groupsContainer.addView(new GroupsView(getContext(), group));
        });
    }

    private AlertDialog createAddGroupDialog() {
        EditText editText = new EditText(getContext());
        return new AlertDialog.Builder(getContext())
                .setTitle("Введите название группы")
                .setView(editText)
                .setPositiveButton("Создать", (dialogInterface, i) -> {
                    DeviceGroup deviceGroup = new DeviceGroup();
                    deviceGroup.setName(editText.getText().toString());
                    groupDao.create(deviceGroup);
                    fillGroupsContainer();
                })
                .create();
    }

}