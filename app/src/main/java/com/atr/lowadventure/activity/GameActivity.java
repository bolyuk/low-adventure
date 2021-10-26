package com.atr.lowadventure.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.*;

import java.util.*;

import android.widget.LinearLayout;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import com.atr.lowadventure.FileUtil;
import com.atr.lowadventure.GameScreen;
import com.atr.lowadventure.R;
import com.atr.lowadventure.User;

public class GameActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	GameScreen screen;
	public LinearLayout gamelinear;
	private Calendar calendar = Calendar.getInstance();
	private TimerTask t;

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.game);
		com.google.firebase.FirebaseApp.initializeApp(this);
		gamelinear = (LinearLayout) findViewById(R.id.gamelinear);
		initializeLogic();
	}


	private void initializeLogic() {
		t = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						screen = new GameScreen(GameActivity.this, GameActivity.this, gamelinear);
						screen.user = new User();
						screen.user.gm = screen;
						screen.mapId ="test";
						screen.screen=screen;
						screen.start();
					}
				});
			}
		};
		_timer.schedule(t, (int)(2000));
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(screen != null && screen.drawThread != null)
		{
			screen.drawThread.pausedFlag=false;
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if(screen != null && screen.drawThread != null)
		{
			screen.drawThread.pausedFlag=false;
		}
	}

	public void _WriteLogInFile (final String _text) {
		calendar = Calendar.getInstance();
		if (FileUtil.isExistFile(FileUtil.getPackageDataDir(getApplicationContext()).concat("/log.txt"))) {
			FileUtil.writeFile(FileUtil.getPackageDataDir(getApplicationContext()).concat("/log.txt"), FileUtil.readFile(FileUtil.getPackageDataDir(getApplicationContext()).concat("/log.txt")).concat(new SimpleDateFormat("\n[dd/MM HH:mm] ").format(calendar.getTime()).concat(_text)));
		}
		else {
			FileUtil.writeFile(FileUtil.readFile(FileUtil.getPackageDataDir(getApplicationContext()).concat("/log.txt")), new SimpleDateFormat("[dd/MM HH:mm] ").format(calendar.getTime()).concat(_text));
		}
	}

	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
}
