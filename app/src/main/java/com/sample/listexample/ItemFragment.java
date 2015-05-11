package com.sample.listexample;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import android.content.Loader;
import android.app.LoaderManager.LoaderCallbacks;

import org.json.JSONObject;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class ItemFragment extends Fragment implements ListView.OnItemClickListener, LoaderCallbacks<List<JSONObject>> {
    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    //private ListAdapter mAdapter;
    private InfoAdapter mAdapter;

    List<JSONObject> data = null;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new InfoAdapter(getActivity(), R.layout.fragment_item);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById( R.id.listFinal);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            JSONObject datum = data.get(position);
            String link = datum.optString("link");
            mListener.onFragmentInteraction(link);
        }
    }

    @Override
    public Loader<List<JSONObject>> onCreateLoader(int id, Bundle args) {
        return new InfoLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<JSONObject>> loader, List<JSONObject> data) {
        this.data=data;
        mAdapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<List<JSONObject>> loader) {
        mAdapter.setData(null);
    }


    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String id);
    }

}
