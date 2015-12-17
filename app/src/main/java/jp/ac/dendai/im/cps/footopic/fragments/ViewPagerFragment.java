package jp.ac.dendai.im.cps.footopic.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

import jp.ac.dendai.im.cps.footopic.FragmentEnum;
import jp.ac.dendai.im.cps.footopic.R;
import jp.ac.dendai.im.cps.footopic.adapters.DonFragmentPagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link jp.ac.dendai.im.cps.footopic.fragments.ViewPagerFragment.OnViewPagerFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewPagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewPagerFragment extends Fragment {

    private OnViewPagerFragmentInteractionListener mListener;
    private Fragment[] fragments;
    private String[] titles;

    private static final String PARAM_TITLES = "titles";
    private static final String PARAM_FRAGMENTS = "fragments";

    /**
     * @return A new instance of fragment ArticleListFragment.
     */
    public static ViewPagerFragment newInstance(String[] titles, int[] fragments) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        Bundle args = new Bundle();
        args.putStringArray(PARAM_TITLES, titles);
        args.putIntArray(PARAM_FRAGMENTS, fragments);
        fragment.setArguments(args);
        return fragment;
    }

    public ViewPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int[] fragmentsKey = getArguments().getIntArray(PARAM_FRAGMENTS);
            fragments = new Fragment[fragmentsKey.length];
            for (int i = 0; i < fragments.length; i++) {
                fragments[i] = FragmentEnum.getEmptyInstance(fragmentsKey[i]);
            }
            titles = getArguments().getStringArray(PARAM_TITLES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_pager, container, false);

        initPager(v);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onViewPagerFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnViewPagerFragmentInteractionListener) activity;
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
     * Pagerの初期化
     * @param v 初期化する {@link PagerSlidingTabStrip} がある {@link ViewGroup}
     */
    private void initPager(View v) {
        final ViewPager viewPager = (ViewPager) v.findViewById(R.id.pager);

        final DonFragmentPagerAdapter adapter = new DonFragmentPagerAdapter(getFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) v.findViewById(R.id.tab_strip);
        tabs.setViewPager(viewPager);
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
    public interface OnViewPagerFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onViewPagerFragmentInteraction(Uri uri);
    }

}
