package ca.university.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import ca.university.myapplication.model.Game;
import ca.university.myapplication.model.GameConfig;
import ca.university.myapplication.model.GameConfigManager;

public class AchievementStatsActivity extends AppCompatActivity {
	private static final String EXTRA_GAME_CONFIG_INDEX = "extra_game_config_index";
	private GameConfigManager manager;
	private GameConfig gameConfig;
	private List<Game> gameList;
	private int gameConfigIndex;
	private int countLevel0, countLevel1, countLevel2, countLevel3, countLevel4
			, countLevel5, countLevel6, countLevel7, countLevel8;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_achievement_stats);

		getSupportActionBar().setTitle("Achievement Statistics");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		manager = GameConfigManager.getInstance();
		extractGameConfigExtra();
		getStats();
		fillHistogram();
	}

	public static Intent makeIntent(Context context, int gameConfigIndex) {
		Intent intent = new Intent(context, AchievementStatsActivity.class);
		intent.putExtra(EXTRA_GAME_CONFIG_INDEX, gameConfigIndex);
		return intent;
	}

	private void extractGameConfigExtra() {
		Intent intent = getIntent();
		gameConfigIndex = intent.getIntExtra(EXTRA_GAME_CONFIG_INDEX, 0);
		gameConfig = manager.getConfig(gameConfigIndex);
		gameList = gameConfig.getGames();
	}

	private void getStats() {
		for (int i = 0; i < gameConfig.totalGames(); i++) {
			int level = gameList.get(i).getAchievementLevel();
			switch(level) {
				case 0:
					countLevel0++;
					break;
				case 1:
					countLevel1++;
					break;
				case 2:
					countLevel2++;
					break;
				case 3:
					countLevel3++;
					break;
				case 4:
					countLevel4++;
					break;
				case 5:
					countLevel5++;
					break;
				case 6:
					countLevel6++;
					break;
				case 7:
					countLevel7++;
					break;
				case 8:
					countLevel8++;
					break;
			}
		}
	}

	private void fillHistogram() {
		BarChart histogram = findViewById(R.id.achievement_stats_histogram);
		ArrayList<BarEntry> stats = new ArrayList<>();
		stats.add(new BarEntry(1,countLevel0));
		stats.add(new BarEntry(2,countLevel1));
		stats.add(new BarEntry(3,countLevel2));
		stats.add(new BarEntry(4,countLevel3));
		stats.add(new BarEntry(5,countLevel4));
		stats.add(new BarEntry(6,countLevel5));
		stats.add(new BarEntry(7,countLevel6));
		stats.add(new BarEntry(8,countLevel7));
		stats.add(new BarEntry(9,countLevel8));

		BarDataSet barDataSet = new BarDataSet(stats,"Achievement Levels");
		barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
		barDataSet.setDrawValues(false);

		BarData barData = new BarData(barDataSet);
		histogram.setFitBars(true);
		histogram.setData(barData);

		// x-axis format
		XAxis xAxis = histogram.getXAxis();
		xAxis.setGranularity(1f);
		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
		xAxis.setAxisMinimum(0);
		xAxis.setDrawGridLines(false);
		xAxis.setDrawGridLinesBehindData(false);
		xAxis.setValueFormatter(new IndexAxisValueFormatter
				(getResources().getStringArray(R.array.histogram_xaxis_labels)));
		xAxis.setLabelCount(9,false);

		// left y-axis format
		YAxis leftYAxis = histogram.getAxisLeft();
		leftYAxis.setDrawGridLines(false);
		leftYAxis.setDrawGridLinesBehindData(false);
		leftYAxis.setAxisMinimum(0);
		leftYAxis.setGranularity(1f);

		// right y-axis format
		YAxis rightYAxis = histogram.getAxisRight();
		rightYAxis.setDrawGridLines(false);
		rightYAxis.setDrawGridLinesBehindData(false);
		rightYAxis.setAxisMinimum(0);
		rightYAxis.setGranularity(1f);

		histogram.getDescription().setEnabled(false);
		histogram.animateY(1500);
	}
}