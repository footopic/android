package jp.ac.dendai.im.cps.footopic.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
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
    private Fragment[] emptyFragments;
    private String[] titles;

    private static final String PARAM_TITLES = "titles";
    private static final String PARAM_FRAGMENTS = "fragments";
    private static final String PARAM_BUNDLE_KEYS = "bundleKeys";
    private static final String PARAM_BUNDLE = "bundle";

    private static final String PARAM_ID = "id";

    /**
     * 引数にある配列のサイズは揃えなくてはならない
     * @param titles 表示するタイトルのString配列
     * @param fragments AttachするFragmentのId(FragmentEnum)
     * @param bundleKeys Bundleをセットする場合のKey配列
     * @param inArgs Bundleにセットする値は単一のInt型(各EntityのIdにあたる)のみ
     * @return A new instance of fragment ViewPagerFragment
     */
    public static ViewPagerFragment newInstance(String[] titles, int[] fragments, String[] bundleKeys, Bundle inArgs) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        Bundle args = new Bundle();
        args.putStringArray(PARAM_TITLES, titles);
        args.putIntArray(PARAM_FRAGMENTS, fragments);
        if (bundleKeys != null) {
            args.putStringArray(PARAM_BUNDLE_KEYS, bundleKeys);
        }
        if (inArgs != null) {
            args.putBundle(PARAM_BUNDLE, inArgs);
        }
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

            titles = getArguments().getStringArray(PARAM_TITLES);
            fragments = new Fragment[fragmentsKey.length];
            emptyFragments = new Fragment[fragmentsKey.length];
            String[] bundleKey = getArguments().getStringArray(PARAM_BUNDLE_KEYS);
            Bundle outArgs = getArguments().getBundle(PARAM_BUNDLE);

            for (int i = 0; i < fragments.length; i++) {
                Bundle args = new Bundle();
                fragments[i] = FragmentEnum.getEmptyInstance(fragmentsKey[i]);
                emptyFragments[i] = FragmentEnum.getEmptyInstance(fragmentsKey[i]);

                if (bundleKey != null) {
                    args.putInt(PARAM_ID, outArgs.getInt(bundleKey[i]));
                    fragments[i].setArguments(args);
                }
            }
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnViewPagerFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement onViewPagerFragmentInteraction");
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

//        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) v.findViewById(R.id.tab_strip);
//        tabs.setViewPager(viewPager);
        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tab_strip);
        tabLayout.setupWithViewPager(viewPager);
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
        public void onViewPagerFragmentInteraction(Fragment[] fragments);
    }

}
