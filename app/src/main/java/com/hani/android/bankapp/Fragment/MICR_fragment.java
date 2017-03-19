package com.hani.android.bankapp.Fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.hani.android.bankapp.BankModel;
import com.hani.android.bankapp.Bank_Adapter_;
import com.hani.android.bankapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
public class MICR_fragment extends Fragment {
    private Button btnSubmit;
    private EditText etIfsc;
    private HttpURLConnection connection;
    private BufferedReader reader;
    private Context context;
    private LinearLayout ll;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.micr_fragment, container, false);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
        etIfsc = (EditText) view.findViewById(R.id.etmicr);
        listView = (ListView) view.findViewById(R.id.lvBank);

        ll = (LinearLayout) view.findViewById(R.id.LL);

        context = getActivity();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ifsc = etIfsc.getText().toString();
                if (!TextUtils.isEmpty(ifsc)) {
                    new BankData().execute("http://api.techm.co.in/api/v1/micr/" + ifsc);

                } else {
                    etIfsc.setError("Enter MICR Code ");
                    Toast.makeText(context, "Enter MICR Code", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    public class BankData extends AsyncTask<String, List<BankModel>, List<BankModel>> {
        List<BankModel> list;
        @Override
        protected List<BankModel> doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream input = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(input));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String Final = buffer.toString();

                JSONObject parentObj = new JSONObject(Final);
                JSONObject data = parentObj.getJSONObject("data");

                list = new ArrayList<>();
                BankModel model = new BankModel();
                model.set_id(data.getString("_id"));
                model.setSTATE(data.getString("STATE"));
                model.setBANK(data.getString("BANK"));
                model.setIFSC(data.getString("IFSC"));
                model.setMICRCODE(data.getString("MICRCODE"));
                model.setBRANCH(data.getString("BRANCH"));
                model.setADDRESS(data.getString("ADDRESS"));
                model.setCONTACT(data.getString("CONTACT"));
                model.setCITY(data.getString("CITY"));
                model.setDISTRICT(data.getString("DISTRICT"));

                list.add(model);
                return list;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<BankModel> result) {
            super.onPostExecute(result);
            // ll.setVisibility(View.VISIBLE);
            if(result!=null) {
                Bank_Adapter_ adapter = new Bank_Adapter_(context, R.layout.bank_row, result);
                listView.setAdapter(adapter);
                etIfsc.setText("");
            }
            else{
                etIfsc.setError("invalid IFSC COde");
            }
        }
    }

}
