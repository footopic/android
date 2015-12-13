package jp.ac.dendai.im.cps.footopic;

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

import jp.ac.dendai.im.cps.footopic.adapter.DonFragmentPagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link jp.ac.dendai.im.cps.footopic.ArticleListFragment.OnArticleListFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ArticleListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleListFragment extends Fragment {

    private FragmentManager manager;
    private OnArticleListFragmentInteractionListener mListener;

    /**
     * @return A new instance of fragment ArticleListFragment.
     */
    public static ArticleListFragment newInstance(FragmentManager manager) {
        ArticleListFragment fragment = new ArticleListFragment();
        fragment.setFragmentManager(manager);
        return fragment;
    }

    public ArticleListFragment() {
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
        View v = inflater.inflate(R.layout.fragment_article_list, container, false);

        initPager(v);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onArticleListFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnArticleListFragmentInteractionListener) activity;
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
     * @param v
     */
    private void initPager(View v) {
        final ViewPager viewPager = (ViewPager) v.findViewById(R.id.pager);

        final DonFragmentPagerAdapter adapter = new DonFragmentPagerAdapter(manager);
        viewPager.setAdapter(adapter);

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) v.findViewById(R.id.tab_strip);
        tabs.setViewPager(viewPager);
    }

    public void setFragmentManager(FragmentManager manager) {
        this.manager = manager;
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
    public interface OnArticleListFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onArticleListFragmentInteraction(Uri uri);
    }

}
