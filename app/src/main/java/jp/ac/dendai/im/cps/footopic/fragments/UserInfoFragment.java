package jp.ac.dendai.im.cps.footopic.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import jp.ac.dendai.im.cps.footopic.R;
import jp.ac.dendai.im.cps.footopic.entities.User;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserInfoFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private static final String PARAM_SCREEN_NAME = "screen_name";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_IMAGE_URL = "image";

    public static UserInfoFragment newInstance(User user) {
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_NAME, user.getName());
        bundle.putString(PARAM_SCREEN_NAME, user.getScreen_name());
        bundle.putString(PARAM_IMAGE_URL, user.getImage().getUrl());
        UserInfoFragment fragment = new UserInfoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public UserInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_info, container, false);

        Uri uri = Uri.parse(getArguments().getString(PARAM_IMAGE_URL));
        ((SimpleDraweeView) v.findViewById(R.id.user_detail_thumb)).setImageURI(uri);
        ((TextView) v.findViewById(R.id.user_detail_screen_name)).setText(getArguments().getString(PARAM_SCREEN_NAME));
        ((TextView) v.findViewById(R.id.user_detail_name)).setText(getArguments().getString(PARAM_NAME));

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
