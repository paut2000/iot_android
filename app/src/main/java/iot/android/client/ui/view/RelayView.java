package iot.android.client.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import iot.android.client.R;
import iot.android.client.databinding.RelayViewBinding;
import iot.android.client.model.device.actuator.Relay;
import org.jetbrains.annotations.NotNull;

public class RelayView extends ConstraintLayout {

    private final Relay relay;

    private final TextView name;
    private final Switch switchStatus;
    private final ProgressBar progressBar;
    private final TextView errorView;

    public RelayView(@NonNull @NotNull Context context, Relay relay) {
        super(context);

        this.relay = relay;

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.relay_view, this, true);

        RelayViewBinding binding = RelayViewBinding.bind(this);

        name = binding.name;
        switchStatus = binding.switchStatus;
        progressBar = binding.progressBar;
        errorView = binding.errorView;

        init();
    }

    private void init() {
        name.setText(relay.getSerialNumber());
        switchStatus.setChecked(relay.getData().getStatus());
        if (!relay.isAlive()) {
            errorView.setVisibility(VISIBLE);
            switchStatus.setVisibility(INVISIBLE);
            return;
        }

        switchStatus.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            progressBar.setVisibility(VISIBLE);
            compoundButton.setEnabled(false);
            if (isChecked) {
                relay.enable(() -> {
                    progressBar.setVisibility(INVISIBLE);
                    compoundButton.setEnabled(true);
                    compoundButton.setChecked(true);
                });
            } else {
                relay.disable(() -> {
                    progressBar.setVisibility(INVISIBLE);
                    compoundButton.setEnabled(true);
                    compoundButton.setChecked(false);
                });
            }
        });
    }



}
