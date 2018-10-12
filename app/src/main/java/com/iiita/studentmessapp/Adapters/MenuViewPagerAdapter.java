package com.iiita.studentmessapp.Adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iiita.studentmessapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MenuViewPagerAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private String jsonResponse;
    public MenuViewPagerAdapter(Context context) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        jsonResponse = getJson();
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.menu_page, container, false);
        view = populate(view, position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    private View populate(View view, int position) {

        if (jsonResponse != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONObject weekDayMenu = jsonObject.getJSONObject(getDays(position));
                String breakfast = weekDayMenu.getString("breakfast");
                String lunch = weekDayMenu.getString("lunch");
                String dinner = weekDayMenu.getString("dinner");
                String addon1 = weekDayMenu.getString("addon1");
                String addon2 = weekDayMenu.getString("addon2");
                lunch = lunch + "\n" + addon1;
                dinner = dinner + "\n" + addon2;
                TextView weekdayTv = view.findViewById(R.id.tv_weekdays);
                TextView breakfastTv = view.findViewById(R.id.tv_breakfast);
                TextView lunchTv = view.findViewById(R.id.tv_lunch);
                TextView dinnerTv = view.findViewById(R.id.tv_dinner);
                weekdayTv.setText(getDays(position));
                breakfastTv.setText(breakfast);
                lunchTv.setText(lunch);
                dinnerTv.setText(dinner);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return view;
    }

    private String getDays(int position) {
        switch (position) {
            case 0:
                return "Monday";
            case 1:
                return "Tuesday";
            case 2:
                return "Wednesday";
            case 3:
                return "Thursday";
            case 4:
                return "Friday";
            case 5:
                return "Saturday";
            case 6:
                return "Sunday";
        }
        return null;
    }

    private String getJson() {
        String json = null;
        try {
            InputStream inputStream = context.getAssets().open("menu.json");
            StringBuilder buf = new StringBuilder();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String str;

            while ((str = in.readLine()) != null) {
                buf.append(str);
            }

            in.close();

            json = buf.toString();

            Log.d("json", "loadJSONFromAsset: " + json);
        } catch (IOException e) {
            Log.d("error", "loadJSONFromAsset: " + e.toString());
            e.printStackTrace();
            return null;
        }
        return json;
    }
}
