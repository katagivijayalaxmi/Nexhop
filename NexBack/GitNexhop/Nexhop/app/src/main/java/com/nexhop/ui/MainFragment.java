package com.nexhop.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.nexhop.MainActivity;
import com.nexhop.ProductDetail;
import com.nexhop.R;
import com.nexhop.common.MyProgress;
import com.nexhop.custom.CustomFragment;
import com.nexhop.model.Data;

import java.util.ArrayList;

/**
 * The Class MainFragment is the base fragment that shows the list of various
 * products. You can add your code to do whatever you want related to products
 * for your app.
 */
public class MainFragment extends CustomFragment {
    /**
     * The product list.
     */
    private ArrayList<Data> iList, iList1;
    RecyclerView list0, list1, list2, list3, list4, list5,list6;
    private String[] categoryName = {"Electronics", "Home & Living", "Lifestyle", "Automotive Accessories", "Books & Entertainment","Shop Corner"};
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */

    ViewPager pager;
    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_container, null);
        ((MainActivity) getActivity()).toolbar.findViewById(
                R.id.spinner_toolbar).setVisibility(View.VISIBLE);
        setHasOptionsMenu(true);
        setupView(v);
        return v;
    }

    /* (non-Javadoc)
	 * @see com.whatshere.custom.CustomFragment#onClick(android.view.View)
	 */
    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    /**
     * Setup the view components for this fragment. You write your code for
     * initializing the views, setting the adapters, touch and click listeners
     * etc.
     *
     * @param v the base view of fragment
     */
    private void setupView(View v) {


//		loadDummyData();
//        loadDummyData1();
        initPager(v);
    }

    /**
     * Inits the pager view.
     *
     * @param v the root view
     */
    private void initPager(View v) {
        pager = (ViewPager) v.findViewById(R.id.pager);
        pager.setPageMargin(10);
        pager.setAdapter(new PageAdapter());
        iList=new ArrayList<Data>();
    }

    /**
     * The Class PageAdapter is adapter class for ViewPager and it simply holds
     * a RecyclerView with dummy images. You need to write logic for loading
     * actual images.
     */
    private class PageAdapter extends PagerAdapter {
        /* (non-Javadoc)
		 * @see android.support.v4.view.PagerAdapter#getCount()
		 */
        @Override
        public int getCount() {
            return categoryName.length;
        }

        /* (non-Javadoc)
		 * @see android.support.v4.view.PagerAdapter#instantiateItem(android.view.ViewGroup, int)
		 */
        @Override
        public Object instantiateItem(ViewGroup container, int pos) {
            final View v = getLayoutInflater(null).inflate(R.layout.pager_card_view, null);
            if(!MyProgress.isShowingProgress()){
            MyProgress.show(getActivity(), "", "");}

            switch (pos) {
                case 0:
                    list0 = (RecyclerView) v.findViewById(R.id.cardList);
                    list0.setHasFixedSize(true);
                    LinearLayoutManager llm0 = new LinearLayoutManager(getActivity());
                    llm0.setOrientation(LinearLayoutManager.VERTICAL);
                    list0.setLayoutManager(llm0);
                    new ProductCategory(getActivity(),list0,0).execute();
                    break;
                case 1:
                    list1 = (RecyclerView) v.findViewById(R.id.cardList);
                    list1.setHasFixedSize(true);
                    LinearLayoutManager llm1 = new LinearLayoutManager(getActivity());
                    llm1.setOrientation(LinearLayoutManager.VERTICAL);
                    list1.setLayoutManager(llm1);
                    new ProductCategory(getActivity(), list1,1).execute();
                    break;

                case 2:
                    list2 = (RecyclerView) v.findViewById(R.id.cardList);
                    list2.setHasFixedSize(true);
                    LinearLayoutManager llm2 = new LinearLayoutManager(getActivity());
                    llm2.setOrientation(LinearLayoutManager.VERTICAL);
                    list2.setLayoutManager(llm2);
                    new ProductCategory(getActivity(), list2,2).execute();
                    break;

                case 3:
                    list3 = (RecyclerView) v.findViewById(R.id.cardList);
                    list3.setHasFixedSize(true);
                    LinearLayoutManager llm3 = new LinearLayoutManager(getActivity());
                    llm3.setOrientation(LinearLayoutManager.VERTICAL);
                    list3.setLayoutManager(llm3);
                    new ProductCategory(getActivity(), list3,3).execute();
                    break;

                case 4:
                    list4 = (RecyclerView) v.findViewById(R.id.cardList);
                    list4.setHasFixedSize(true);
                    LinearLayoutManager llm4 = new LinearLayoutManager(getActivity());
                    llm4.setOrientation(LinearLayoutManager.VERTICAL);
                    list4.setLayoutManager(llm4);
                    new ProductCategory(getActivity(), list4,4).execute();
                    break;

                case 5:
                    list5 = (RecyclerView) v.findViewById(R.id.cardList);
                    list5.setHasFixedSize(true);
                    LinearLayoutManager llm5 = new LinearLayoutManager(getActivity());
                    llm5.setOrientation(LinearLayoutManager.VERTICAL);
                    list5.setLayoutManager(llm5);
                    new ProductCategory(getActivity(), list5,5).execute();
                    break;


            }


            container.addView(v, android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT);
            return v;
        }

        /* (non-Javadoc)
		 * @see android.support.v4.view.PagerAdapter#destroyItem(android.view.ViewGroup, int, java.lang.Object)
		 */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            try {
//				super.destroyItem(container, position, object);
//				if(container.getChildAt(position)!=null)
//				container.removeViewAt(position);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        /* (non-Javadoc)
		 * @see android.support.v4.view.PagerAdapter#isViewFromObject(android.view.View, java.lang.Object)
		 */
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

       /* @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }*/

        /* (non-Javadoc)
		 * @see android.support.v4.view.PagerAdapter#getPageTitle(int)
		 */
        @Override
        public CharSequence getPageTitle(int position) {
            return categoryName[position];
        }
    }

    /**
     * The Class CardAdapter is the adapter for showing products in Card format
     * inside the RecyclerView. It shows dummy product image and dummy contents,
     * so you need to display actual contents as per your need.
     */
    private class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
        ArrayList<Data> iList=new ArrayList<>();
        int pos;
        CardAdapter(ArrayList<Data> list,int pos) {
            this.iList = list;
            this.pos=pos;
        }

        /* (non-Javadoc)
		 * @see android.support.v7.widget.RecyclerView.Adapter#getItemCount()
		 */
        @Override
        public int getItemCount() {
            return iList.size();
        }

        /* (non-Javadoc)
		 * @see android.support.v7.widget.RecyclerView.Adapter#onBindViewHolder(android.support.v7.widget.RecyclerView.ViewHolder, int)
		 */
        @Override
        public void onBindViewHolder(CardViewHolder vh, int i) {
            Data d = iList.get(i);
            vh.lbl1.setText(d.getTexts()[0]);
            vh.lbl1.setVisibility(d.getTexts()[0] == null ? View.GONE : View.VISIBLE);
            vh.lbl2.setText(d.getTexts()[1]);
            vh.lbl3.setText(d.getTexts()[2]);
            vh.lbl4.setText(d.getTexts()[3]);
            vh.img.setImageResource(d.getResources()[0]);
        }

        /* (non-Javadoc)
		 * @see android.support.v7.widget.RecyclerView.Adapter#onCreateViewHolder(android.view.ViewGroup, int)
		 */
        @Override
        public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView=null;
            if(pos<5)
            {
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item, viewGroup, false);
            }else {

                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shopcorner_item, viewGroup, false);
            }

            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), ProductDetail.class));
                }
            });
            return new CardViewHolder(itemView);
        }

        /**
         * The Class CardViewHolder is the View Holder class for Adapter views.
         */
        public class CardViewHolder extends RecyclerView.ViewHolder {

            /**
             * The textviews.
             */
            protected TextView lbl1, lbl2, lbl3, lbl4;

            /**
             * The img.
             */
            protected ImageView img;

            /**
             * Instantiates a new card view holder.
             *
             * @param v the v
             */
            public CardViewHolder(View v) {
                super(v);
                lbl1 = (TextView) v.findViewById(R.id.lbl1);
                lbl2 = (TextView) v.findViewById(R.id.lbl2);
                lbl3 = (TextView) v.findViewById(R.id.lbl3);
                lbl4 = (TextView) v.findViewById(R.id.lbl4);
                img = (ImageView) v.findViewById(R.id.img);
            }
        }
    }

    /**
     * Load dummy product data for displaying on the RecyclerView. You need to
     * write your own code for loading real products from Web-service or API and
     * displaying them on RecyclerView.
     */
    private ArrayList<Data> loadDummyData() {
        ArrayList<Data> al = new ArrayList<Data>();
        iList.add(new Data(new String[]{null,
                "Ally Capellino Frank Brown - Fott Shop 2014", "$200-$400",
                "Shop.fott.com"}, new int[]{R.drawable.popularity_img1}));
        iList.add(new Data(new String[]{"50%\nOFF", "Tap & DYE Legacy", "$67",
                "Tapanddye"}, new int[]{R.drawable.popularity_img2}));
        iList.add(new Data(new String[]{null, "Piper Felt Hat by Brixton",
                "$94", "Tapanddye"}, new int[]{R.drawable.popularity_img3}));
        iList.add(new Data(new String[]{null, "HIKE Abysss Stone", "$42",
                "Handwers"}, new int[]{R.drawable.popularity_img4}));
        iList.add(new Data(new String[]{"20%\nOFF", "Lenovo Leather Belt",
                "$12", "Lenovo"}, new int[]{R.drawable.popularity_img5}));

        /*for (int i = 0; i < 10000; i++) {
            al.add(new Data(new String[]{null,
                    "Ally Capellino Frank Brown - Fott Shop 2014", "$200-$400",
                    "Shop.fott.com"}, new int[]{R.drawable.popularity_img5}));
        }*/
//        iList = new ArrayList<Data>(al);
        return iList;

    }


    private ArrayList<Data> storeDummyData() {
        ArrayList<Data> al = new ArrayList<Data>();
        al.add(new Data(new String[]{"Reliance Fresh",
                "#18/1 2nd cross, 4th main ,Madiwala Banglore 580020", "ph :99990112234",
                "Shop.fott.com"}, new int[]{R.drawable.popularity_img1}));
        al.add(new Data(new String[]{"Kirthi Store",
                "#18/1 4nd cross, 4th main ,MAruthi Nagar ,Madiwala Banglore-580068", "ph :99990112234",
                "Shop.fott.com"}, new int[]{R.drawable.popularity_img2}));
        al.add(new Data(new String[]{"Reliance Fresh",
                "#18/1 2nd cross, 4th main ,Madiwala Banglore", "ph :99990112234",
                "Shop.fott.com"}, new int[]{R.drawable.popularity_img3}));
        al.add(new Data(new String[]{"Neha General Stores",
                "#18/1 2nd cross, 4th main ,Madiwala Banglore", "ph :99990112234",
                "Shop.fott.com"}, new int[]{R.drawable.popularity_img4}));
        al.add(new Data(new String[]{"Reliance Fresh",
                "#18/1 2nd cross, 4th main ,Madiwala Banglore", "ph :99990112234",
                "Shop.fott.com"}, new int[]{R.drawable.popularity_img5}));

        /*for (int i = 0; i < 10000; i++) {
            al.add(new Data(new String[]{"Pick n Save",
                    "Madiwala Police Station ,Near Wipro", "ph :99990112234",
                    "Shop.fott.com"}, new int[]{R.drawable.popularity_img5}));
        }*/
        iList = new ArrayList<Data>(al);
        return iList;

    }





    private ArrayList<Data> loadDummyData1() {
        ArrayList<Data> al = new ArrayList<Data>();
        al.add(
                new Data(
                        new String[]{null,
                "Ally Capellino Frank Brown - Fott Shop 2014", "$200-$400",
                "Shop.fott.com"}, new int[]{R.drawable.popularity_img5}));
        al.add(new Data(new String[]{null,
                "Ally Capellino Frank Brown - Fott Shop 2014", "$200-$400",
                "Shop.fott.com"}, new int[]{R.drawable.popularity_img5}));
        al.add(new Data(new String[]{null,
                "Ally Capellino Frank Brown - Fott Shop 2014", "$200-$400",
                "Shop.fott.com"}, new int[]{R.drawable.popularity_img5}));
        al.add(new Data(new String[]{null,
                "Ally Capellino Frank Brown - Fott Shop 2014", "$200-$400",
                "Shop.fott.com"}, new int[]{R.drawable.popularity_img5}));
        al.add(new Data(new String[]{null,
                "Ally Capellino Frank Brown - Fott Shop 2014", "$200-$400",
                "Shop.fott.com"}, new int[]{R.drawable.popularity_img5}));
        al.add(new Data(new String[]{null,
                "Ally Capellino Frank Brown - Fott Shop 2014", "$200-$400",
                "Shop.fott.com"}, new int[]{R.drawable.popularity_img5}));
        al.add(new Data(new String[]{null,
                "Ally Capellino Frank Brown - Fott Shop 2014", "$200-$400",
                "Shop.fott.com"}, new int[]{R.drawable.popularity_img5}));
        al.add(new Data(new String[]{null,
                "Ally Capellino Frank Brown - Fott Shop 2014", "$200-$400",
                "Shop.fott.com"}, new int[]{R.drawable.popularity_img5}));
        al.add(new Data(new String[]{null,
                "Ally Capellino Frank Brown - Fott Shop 2014", "$200-$400",
                "Shop.fott.com"}, new int[]{R.drawable.popularity_img5}));



        iList1 = new ArrayList<Data>(al);
        return iList1;
        /*iList.addAll(al);
        iList.addAll(al);
        iList.addAll(al);
        iList.addAll(al);
        iList.addAll(al);
        iList.addAll(al);
        iList.addAll(al);*/


    }

    /* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateOptionsMenu(android.view.Menu, android.view.MenuInflater)
	 */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.search_exp, menu);
        final MenuItem item = menu.findItem(R.id.menu_search);
        final SearchView  sv = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
        sv.setIconifiedByDefault(false);
        MenuItemCompat.setActionView(item, sv);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
            public boolean onQueryTextSubmit(String query) {
                new ProductCategory(getActivity(), list0, 0).execute();
                System.out.println("search query submit" + pager.getCurrentItem());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("search query Text change" + pager.getCurrentItem());
                return true;
            }
        });

        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                System.out.println("search query collapsed" + pager.getCurrentItem());
                sv.clearFocus();
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                System.out.println("search query expanded" + pager.getCurrentItem());
                sv.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                return true;  // Return true to expand action view
            }
        });

        super.onCreateOptionsMenu(menu, inflater);


    }




    private class ProductCategory extends AsyncTask<Context, Integer, ArrayList<Data>> {
        String url;
        Context ctx;
        CardAdapter adapter;
        RecyclerView recycl;
        ArrayList<Data> al;
        int pos;


        ProductCategory(Context ctx, RecyclerView recycl, int pos) {
            this.ctx = ctx;
            this.recycl = recycl;
            this.pos = pos;

        }

        @Override
        protected ArrayList<Data> doInBackground(Context... params) {

            switch (pos) {
                case 0:
                    al = loadDummyData();
                    break;
                case 1:
                    al = loadDummyData1();
                    break;
                case 2:
                    al = loadDummyData();
                    break;
                case 3:
                    al = loadDummyData1();
                    break;
                case 4:
                    al = loadDummyData();
                    break;
                case 5:
                    al = storeDummyData();
                    break;

            }

            return al;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();



        }

        @Override
        protected void onPostExecute(ArrayList<Data> s) {
            super.onPostExecute(s);

            Log.d("List", "::::::::::" + s.toString());

            if (MyProgress.isShowingProgress()) {
                MyProgress.CancelDialog();
            }

            CardAdapter ca = new CardAdapter(s,pos);
            recycl.setAdapter(ca);
        }

    }
}


