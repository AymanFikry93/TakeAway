package com.example.srccode.takeawayproject.Activities;


import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.srccode.takeawayproject.AdapterSpinner;
import com.example.srccode.takeawayproject.Classes.ClassArea;
import com.example.srccode.takeawayproject.Classes.ClassCity;
import com.example.srccode.takeawayproject.WebServices.AreaJSONAsync;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.example.srccode.takeawayproject.Global.GlopalClass.GlobalRegionID;
import static com.example.srccode.takeawayproject.Global.GlopalClass.HostName;
import static com.example.srccode.takeawayproject.Global.GlopalClass.RegionId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Regionname;
import static com.example.srccode.takeawayproject.Global.GlopalClass._regionname;
import static com.example.srccode.takeawayproject.Global.GlopalClass.cTimer;
import static com.example.srccode.takeawayproject.Global.GlopalClass.citySelectedID;
import static com.example.srccode.takeawayproject.Global.GlopalClass.cityspinner;
import static com.example.srccode.takeawayproject.Global.GlopalClass.cityvalueslist;
import static com.example.srccode.takeawayproject.Global.GlopalClass.classeslistCity;
import static com.example.srccode.takeawayproject.Global.GlopalClass.classeslistcountry;
import static com.example.srccode.takeawayproject.Global.GlopalClass.countrySelectedId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.countryspinner;
import static com.example.srccode.takeawayproject.Global.GlopalClass.countryvalueslist;
import static com.example.srccode.takeawayproject.Global.GlopalClass.customadapter;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantmapflag;
import static com.example.srccode.takeawayproject.Global.GlopalClass.streetSelected;
import static com.example.srccode.takeawayproject.Global.GlopalClass.streetclasslist;
import static com.example.srccode.takeawayproject.Global.GlopalClass.streetspinner;
import static com.example.srccode.takeawayproject.Global.GlopalClass.streetvalues;
import static com.example.srccode.takeawayproject.Global.GlopalClass.tableCityDB;
import static com.example.srccode.takeawayproject.Global.GlopalClass.tableRegionDB;
import static com.example.srccode.takeawayproject.Global.GlopalClass.typeface;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    TextView findbymap;

    Button button;
//    private ViewPager mViewPager;
//    private TabLayout tabLayout;
    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ActiveAndroid.initialize(getContext());

        tableCityDB = new Select().from(ClassCity.class).execute();
       // countryspinner = (Spinner) v.findViewById(R.id.countryspinnerId);
        cityspinner = (Spinner) v.findViewById(R.id.spinner_city);
        streetspinner = (Spinner) v.findViewById(R.id.spinner_Street);

//        customadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, countryvalueslist);
//        customadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
       // countryspinner.setAdapter(customadapter);

//        countryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, final int position, final long id) {

                classeslistCity = new ArrayList<ClassCity>();
                countrySelectedId =1;// Integer.valueOf(classeslistcountry.get(position).getcountryId());
                if (tableCityDB.size() != 0) {
                    cityvalueslist.clear();
                    cityvalueslist.add(0, getString(R.string.selectancity));
                    for (int i = 0; i < tableCityDB.size(); i++) {
                        classeslistCity.add(tableCityDB.get(i));
                        cityvalueslist.add(i + 1, classeslistCity.get(i).getCityname());
                    }
                } else {
                    new AreaJSONAsync(getContext()).execute("http://"+ HostName+"/api/Cities?CountryID=" + countrySelectedId);
                }

                customadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, cityvalueslist);
                customadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       // AdapterSpinner  adapter = new AdapterSpinner(getActivity(), R.layout.spinner_row, cityvalueslist);

        cityspinner.setAdapter(customadapter);
                cityspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        streetclasslist = new ArrayList<ClassArea>();

                        if (position != 0) {

                            int realposition = position - 1;
                            tableRegionDB = new Select().from(ClassArea.class).where("cityId = ?", classeslistCity.get(realposition).getcityId()).execute();

                            if (tableRegionDB.size() != 0) {
                                streetvalues.clear();
                                streetvalues.add(0, getString(R.string.selectanarea));
                                for (int i = 0; i < tableRegionDB.size(); i++) {
                                    streetclasslist.add(tableRegionDB.get(i));
                                    streetvalues.add(i + 1, streetclasslist.get(i).getStreetname());
                                }

                            } else {
                                citySelectedID = Integer.valueOf(classeslistCity.get(realposition).getcityId());
                                new AreaJSONAsync(getContext()).execute("http://"+ HostName+"/api/Regions?CityID=" + citySelectedID);
                            }


                            customadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, streetvalues);
                            customadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                            streetspinner.setVisibility(view.VISIBLE);
                            streetspinner.setAdapter(customadapter);
                            streetspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if (position != 0) {
                                        int realposition = position - 1;
                                        // int realposition = position ;

                                        streetSelected = streetclasslist.get(realposition).getregionId();
                                        _regionname = streetclasslist.get(realposition).getStreetname();
                                        GlobalRegionID= Integer.parseInt(streetSelected);

                                    } else {
                                        streetSelected = "";
                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });

                        } else {
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                Toast.makeText(getContext(), getString(R.string.selectancity), Toast.LENGTH_LONG).show();
//
//            }
//        });
        findbymap = (TextView)v.findViewById(R.id.findbymap);
        findbymap.setTypeface(typeface);
        findbymap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resturantmapflag=1;
                Intent it = new Intent(getContext(),ActivityMaps.class);
                startActivity(it);
            }
        });

        // Find the View that shows the Resturants
        Button Resturants = (Button) v.findViewById(R.id.Resturantsbutton);
        Resturants.setTypeface(typeface);

        // Set a click listener on that View
        Resturants.setOnClickListener(new View.OnClickListener() {
            //  The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                if (streetSelected != "") {
                    RegionId = Integer.parseInt(streetSelected);
                    SharedPreferences sharedPreferences_save = getContext().getSharedPreferences("resturantdata", MODE_PRIVATE);
                    SharedPreferences.Editor editor_save = sharedPreferences_save.edit();
                    editor_save.putInt("RegionId", RegionId);  // to sava the data
                    editor_save.commit();
                    Regionname = _regionname;
                    GlobalRegionID=RegionId;
//                    if(cTimer!=null)
//                        cTimer.cancel();
                    Intent ResturantsIntent = new Intent(getContext(), ActivityResturants.class);
                    startActivity(ResturantsIntent);

                } else if (citySelectedID == 0) {
                    Toast.makeText(getContext(), getString(R.string.selectancity), Toast.LENGTH_LONG).show();


                } else {
                    Toast.makeText(getContext(), getString(R.string.selectanarea), Toast.LENGTH_LONG).show();

                }
            }


        });

//        Button gotolastorder = (Button) v.findViewById(R.id.lastorderbutton);
//        gotolastorder.setTypeface(typeface);
//
////         Set a click listener on that View
//        gotolastorder.setOnClickListener(new View.OnClickListener() {
//            //  The code in this method will be executed when the numbers category is clicked on.
//            @Override
//            public void onClick(View view) {
//               // Intent gotolastorder = new Intent(getContext(), ActivityLastOrder.class);ActivityRegister ActivityRestOffer
//                Intent gotolastorder = new Intent(getContext(),ActivityLogin.class);//ActivityMaps ActivityLogin ActivityClientView
//                startActivity(gotolastorder);         //ActivityCobon
//
//            }
//
//
//        });

        ImageButton gotolastorder = (ImageButton) v.findViewById(R.id.lastorderbutton);
//        gotolastorder.setTypeface(typeface);
        gotolastorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent gotolastorder = new Intent(getContext(),ActivityLastOrder.class);
                startActivity(gotolastorder);

            }


        });
        ImageButton gotositting= (ImageButton) v.findViewById(R.id.sittingbutton);
//        gotositting.setTypeface(typeface);
        gotositting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //final gotositting.setBackground();
                Intent gotositting = new Intent(getContext(),SittingActivity.class);
                startActivity(gotositting);

            }


        });


        return v;

    }

}
