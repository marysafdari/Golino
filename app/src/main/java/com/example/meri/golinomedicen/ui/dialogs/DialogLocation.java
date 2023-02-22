package com.example.meri.golinomedicen.ui.dialogs;

import android.app.AlertDialog;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.meri.golinomedicen.R;
import com.example.meri.golinomedicen.ui.activities.MyActivity;

public class DialogLocation {

    private static final String TAG = DialogLocation.class.getSimpleName();

    @BindView(R.id.btn_setting)
    Button btn_setting;

    private AlertDialog dialog;

    public DialogLocation(MyActivity activity, ClickListener listener) {
        View mView = activity.getLayoutInflater().inflate(R.layout.view_dialog_location, null);

        ButterKnife.bind(this, mView);

        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            builder = new AlertDialog.Builder(activity, android.R.style.Theme_Material_Light_Dialog_Alert);
        else
            builder = new AlertDialog.Builder(activity);

        builder.setView(mView);

        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);

        btn_setting.setOnClickListener(v -> {
            Log.e(TAG, "onClick: ");
            listener.setting();
            dialog.dismiss();
        });

        dialog.setOnCancelListener(dialog -> {
            Log.e(TAG, "onCancel: ");
            listener.onDismiss();
        });

    }

    public void show() {

        dialog.show();
        if (dialog.getWindow() != null)
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


    }

    public interface ClickListener {
        void setting();

        void onDismiss();
    }
}
