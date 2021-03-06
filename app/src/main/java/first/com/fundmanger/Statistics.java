package first.com.fundmanger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


public class Statistics extends AppCompatActivity {

    BarChart chart;
    TextView heading, subheading;
    String[] monthly_expenditures;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);


        heading = (TextView) findViewById(R.id.heading5);
        subheading = (TextView) findViewById(R.id.subheading5);

        DBHandler db = new DBHandler(getApplicationContext());
        monthly_expenditures = db.getMonthlyExpenditures();
        count = db.getStatsRowCount();

        List<BarEntry> entries = new ArrayList<>();
        if(count<5) {

            for (int i = 0; i < count; i++) {
                entries.add(new BarEntry(i, Float.valueOf(monthly_expenditures[count - 1 - i])));
            }
        }
        else {
            for (int i = 0; i < 5; i++) {
                entries.add(new BarEntry(i, Float.valueOf(monthly_expenditures[count - 1 - i])));
            }
        }
        if(count==0){
            AlertDialog ad = new AlertDialog.Builder(this)
                    .create();
            ad.setCancelable(false);
            ad.setTitle("No Records!!");
            ad.setMessage("You haven't completed any day yet... Complete a month to view daily Expenditures.");
            ad.setButton(this.getString(R.string.ok), new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    Intent intent = new Intent(getApplicationContext(), MainApp.class);
                    startActivity(intent);
                    finish();

                    dialog.dismiss();
                }
            });
            ad.show();
        }
        else {
            BarDataSet set = new BarDataSet(entries, "BarDataSet");


            List<String> labels = new ArrayList<String>();
            labels.add("January");
            labels.add("February");
            labels.add("March");
            labels.add("April");
            labels.add("May");
            labels.add("June");

            chart = (BarChart) findViewById(R.id.chart1);

            BarData data = new BarData(set);
            data.setBarWidth(0.9f); // set custom bar width
            chart.setData(data);
            chart.setFitBars(true); // make the x-axis fit exactly all bars
            chart.invalidate(); // refresh
            set.setColors(ColorTemplate.JOYFUL_COLORS);

            chart.setTouchEnabled(false);
            chart.animateY(2000);

        }






    }
}