package com.FCI.HHK.SocialNetwork;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.FCI.HHK.Controllers.Application;
import com.FCI.HHK.Controllers.UserController;
import com.FCI.HHK.SocialNetwork.R;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class tab_request extends Fragment implements View.OnClickListener {
    TextView tv[] = new TextView[100];
    Button B[] = new Button[100];
    TableLayout requesttable;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.tab_request, container, false);
        Button request = (Button) V.findViewById(R.id.refresh);
        request.setOnClickListener(this);
        request.setId(0);
        if (HomeActivity.extras.getString("service").equals("viewrequest")) {

            requesttable = (TableLayout) V.findViewById(R.id.requesttable);

            int size = Integer.valueOf(HomeActivity.extras.getString("arraysize"));
            //    String name = HomeActivity.extras.getString("vfriendname");
            //    String welcome = HomeActivity.extras.getString("status");
            //    System.out.println(welcome + " " + name);
            try {
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(HomeActivity.extras.getString("array"));
                JSONArray object = (JSONArray) obj;

                for (int i = 1; i <= size; i++) {
                    JSONObject ret = (JSONObject) object.get(i - 1);

                    TableRow row = new TableRow(getActivity());
                    row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT));

                    // inner for loop

                    tv[i] = new TextView(getActivity());
                    tv[i].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT));

                    tv[i].setPadding(15, 15, 0, 0);
                    tv[i].setTextSize(25);
                    tv[i].setText(ret.get("friend_name").toString());
                    tv[i].setId(Integer.valueOf(ret.get("friend_id").toString()));
                    row.addView(tv[i]);


                    B[i] = new Button(getActivity());
                    B[i].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    B[i].setPadding(5, 5, 5, 5);
                    B[i].setText("Accept");
                    B[i].setId(Integer.valueOf(String.valueOf(i)));
                    requesttable.addView(row);
                    row.addView(B[i]);
                    B[i].setOnClickListener(this);


                }
            }catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        return V;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == 0) {
            UserController controller = Application.getUserController();
            controller.viewRequest(controller.GetActiveUserId());
        } else {
            UserController controller = Application.getUserController();

            controller.acceptRequest(controller.GetActiveUserId(), String.valueOf((tv[view.getId()].getId())));
        }

    }
}
