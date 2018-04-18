package io.macc.sillyggdx;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;


import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import io.macc.sillyggdx.MyGdxGame;

public class AndroidLauncher extends AndroidApplication {

	SensorManager sensorManager;
	Sensor mag, acc;
	SensorEventListener sensorEventListener;

	float[] magValues, accValues;

	MyGdxGame myGame;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		myGame = new MyGdxGame();

		magValues = new float[3];
		accValues = new float[3];

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		mag = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		acc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


		sensorEventListener = new SensorEventListener() {
			@Override
			public void onSensorChanged(SensorEvent sensorEvent) {

				if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
					System.arraycopy(sensorEvent.values, 0, magValues, 0, magValues.length);

				} else if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
					System.arraycopy(sensorEvent.values, 0, accValues, 0, accValues.length);
				}

				myGame.updateMyLocation(getRotationInfo());
			}


			@Override
			public void onAccuracyChanged(Sensor sensor, int i) {

			}
		};

		initialize(myGame, config);
	}

	public float[] getRotationInfo() {

		float[] r = new float[9], values = new float[3];


		SensorManager.getRotationMatrix(r, null, accValues, magValues);
		SensorManager.getOrientation(r, values);

		return values;
	}

	@Override
	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(sensorEventListener, mag, SensorManager.SENSOR_DELAY_UI);
		sensorManager.registerListener(sensorEventListener, acc, SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	protected void onPause() {
		super.onPause();
		sensorManager.unregisterListener(sensorEventListener);
	}
}
