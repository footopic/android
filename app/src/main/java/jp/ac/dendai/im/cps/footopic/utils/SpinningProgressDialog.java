package jp.ac.dendai.im.cps.footopic.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Created by naoya on 15/12/14.
 * シンプルなProgress用 {@link DialogFragment}
 */
public class SpinningProgressDialog extends DialogFragment {
    private ProgressDialog dialog;
    private String title;
    private String message;

    /**
     * 初期化済みのInstance作成メソッド
     * @param title Dialogに表示するタイトル
     * @param message Dialogに表示するタイトル
     * @return 新しい {@link SpinningProgressDialog} のInstance
     */
    public static SpinningProgressDialog newInstance(String title, String message) {
        SpinningProgressDialog fragment = new SpinningProgressDialog();
        fragment.setTitle(title);
        fragment.setMessage(message);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new ProgressDialog(getActivity());
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        return dialog;
    }

    public SpinningProgressDialog() { }

    private void setTitle(String title) {
        this.title = title;
    }

    private void setMessage(String message) {
        this.message = message;
    }
}
