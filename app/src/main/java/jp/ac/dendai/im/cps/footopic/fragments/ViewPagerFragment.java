package jp.ac.dendai.im.cps.footopic.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

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

    private FragmentManager manager;
    private OnViewPagerFragmentInteractionListener mListener;
    private Fragment[] fragments;
    private String[] titles;

    /**
     * @return A new instance of fragment ArticleListFragment.
     */
    public static ViewPagerFragment newInstance(FragmentManager manager, Fragment[] fragments, String[] titles) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.setFragmentManager(manager);
        fragment.setFragments(fragments);
        fragment.setTitles(titles);
        return fragment;
    }

    public ViewPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
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

        final DonFragmentPagerAdapter adapter = new DonFragmentPagerAdapter(manager, fragments, titles);
        viewPager.setAdapter(adapter);

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) v.findViewById(R.id.tab_strip);
        tabs.setViewPager(viewPager);
    }

    /**
     * @param manager
     */
    private void setFragmentManager(FragmentManager manager) {
        this.manager = manager;
    }

    private void setFragments(Fragment[] fragments) {
        this.fragments = fragments;
    }

    private void setTitles(String[] titles) {
        this.titles = titles;
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
