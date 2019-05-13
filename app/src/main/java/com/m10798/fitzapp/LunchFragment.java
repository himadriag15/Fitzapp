package com.m10798.fitzapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LunchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LunchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LunchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;
    String[] FoodName ={"Whey Protein", "Fruit", "Paneer Paratha","Low-fat Yogurt","Sprouts", "Mixed Vegetables salad"};
    String[] Quantity = {"1 scoop", "1 medium size", "3 medium size","1 bowl","2 bowls", "1 bowl"};
    int[] ImagesBreak = {R.drawable.shopping , R.drawable.fruit, R.drawable.paratha, R.drawable.yogurt, R.drawable.sprouts, R.drawable.vsalad};

    public LunchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LunchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LunchFragment newInstance(String param1, String param2) {
        LunchFragment fragment = new LunchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Inflate the layout for this fragment
        Toast.makeText(getContext(), "Toast", Toast.LENGTH_SHORT).show();
        ListView listView = (ListView) view.findViewById(R.id.List);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lunch, container, false);

        // Inflate the layout for this fragment
        Toast.makeText(getContext(), "Toast", Toast.LENGTH_SHORT).show();
        ListView listView = (ListView) view.findViewById(R.id.List);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return ImagesBreak.length;
        }

        @Override
        public Object getItem(int position) {

            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            view = getLayoutInflater().inflate(R.layout.list_layout,null);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            TextView textView_fname = (TextView) view.findViewById(R.id.textView_fnme);
            TextView textView_fquant = (TextView) view.findViewById(R.id.textView_prop);
            imageView.setImageResource(ImagesBreak[position]);
            textView_fname.setText(FoodName[position]);
            textView_fquant.setText((Quantity[position]));
            return view;
        }
    }
}
