package jp.ac.dendai.im.cps.footopic.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import jp.ac.dendai.im.cps.footopic.R;
import jp.ac.dendai.im.cps.footopic.entities.User;
import jp.ac.dendai.im.cps.footopic.network.DonApiClient;


/**
 * BundleにKeyが「id」がセットされている場合のみidでサーバーからデータを取得し、表示する
 */
public class UserInfoFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private static final String PARAM_SCREEN_NAME = "screen_name";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_IMAGE_URL = "image";
    private static final String PARAM_SERIALIZABLE = "serializable";

    private static final String PARAM_ID = "id";

    private User user;
    private int userId = 0;

    private Handler handler = new Handler();


    /**
     *
     * @param user 表示したいUserのオブジェクト
     * @return A new instance of UserInfoFragment
     */
    public static UserInfoFragment newInstance(User user) {
        Bundle args = new Bundle();
        UserInfoFragment fragment = new UserInfoFragment();
        args.putSerializable(PARAM_SERIALIZABLE, user);
        fragment.setArguments(args);
        return fragment;
    }

    public UserInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            if (getArguments().getInt(PARAM_ID) > 0 ) {
                userId = getArguments().getInt(PARAM_ID);
            } else {
                user = (User) getArguments().getSerializable(PARAM_SERIALIZABLE);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_user_info, container, false);

        if (userId > 0) {
            DonApiClient request = new DonApiClient() {
                @Override
                public void onFailure(Request request, IOException e) {
                    Log.e("onFailure", "dame", e.fillInStackTrace());
//                progressDialog.dismiss();
                    Toast.makeText(getActivity(), "ユーザーを読み込めませんでした\nresponse: " + request.body().toString(), Toast.LENGTH_SHORT);
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    final String responseCode = response.body().string();

                    Log.d("onPostCompleted", "ok");
                    Log.d("onPostCompleted", responseCode);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                User user = new ObjectMapper().readValue(responseCode, new TypeReference<User>(){});

                                Uri uri = Uri.parse(user.getImage().getUrl());
                                ((SimpleDraweeView) v.findViewById(R.id.user_detail_thumb)).setImageURI(uri);
                                ((TextView) v.findViewById(R.id.user_detail_screen_name)).setText(user.getScreen_name());
                                ((TextView) v.findViewById(R.id.user_detail_name)).setText(user.getName());
//                            progressDialog.dismiss();
                            } catch (IOException e) {

                                e.printStackTrace();
                            }
                        }
                    });
                }
            };

            request.getUser(userId);
        } else {
            Uri uri = Uri.parse(user.getImage().getUrl());
            ((SimpleDraweeView) v.findViewById(R.id.user_detail_thumb)).setImageURI(uri);
            ((TextView) v.findViewById(R.id.user_detail_screen_name)).setText(user.getScreen_name());
            ((TextView) v.findViewById(R.id.user_detail_name)).setText(user.getName());
        }

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
