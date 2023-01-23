package uac.imsp.clockingapp.View.activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.ToastMessage;

public class MainActivity extends AppCompatActivity {
	private TextView mTextView;
	private BluetoothAdapter mBluetoothAdapter;
	private BluetoothSocket mmSocket;
	private BluetoothDevice mmDevice;
	private InputStream mmInputStream;
	private static final int REQUEST_BLUETOOTH = 2;
	private final StringBuilder sb = new StringBuilder();
	private static final UUID MY_UUID = UUID.fromString
			("00001101-0000-1000-8000-00805F9B34FB");

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);
		mTextView = findViewById(R.id.textView);

		// Get the default Bluetooth adapter
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		if (mBluetoothAdapter == null) {
			// device doesn't support Bluetooth

			new ToastMessage(this,"device doesn't support Bluetooth");
		}


		// Get the Bluetooth device using the device's MAC address
		/*mmDevice = mBluetoothAdapter.
				getRemoteDevice("20:26:81:7D:19:DA"/*"98:D3:31:FB:3E:6E");*/

		mmDevice = mBluetoothAdapter.
				getRemoteDevice("98:D3:31:FB:3E:6E");
		// replace with the actual MAC address of your Bluetooth module

		// Connect to the device
		//new ConnectBT().execute();
		//if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
			checkBluetoothPermission();
		//}
		/*else {
			new ToastMessage(this,"Bluetooth connection is needed");
		}*/
	}
	private void checkBluetoothPermission() {
		if (ActivityCompat.checkSelfPermission
				(this, Manifest.permission.BLUETOOTH) !=
				PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.BLUETOOTH},
					REQUEST_BLUETOOTH);
		} else {
			// permission is already granted, start the connection
			new ConnectBT().execute();
		}
	}

	@Override
	public void onRequestPermissionsResult

			(int requestCode, @NonNull String[] permissions, @NonNull int[]
					grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == REQUEST_BLUETOOTH) {
			if (grantResults.length > 0 && grantResults[0]

					== PackageManager.PERMISSION_GRANTED) {
				// permission was granted, start the connection
				new ConnectBT().execute();
			} else {
				// permission denied, show a message to the user
				new ToastMessage(this, "Bluetooth permission is required to receive" +
						" fingerprint data");
			}
		}
	}




	private class ConnectBT extends AsyncTask<Void, Void, Void> {
		private boolean ConnectSuccess = true;

		@Override
		protected void onPreExecute() {
			//show a progress dialog
		}

		@Override
		protected Void doInBackground(Void... devices) {
			try {
				// Create a Bluetooth socket to connect with the device
				if (ActivityCompat.checkSelfPermission(getApplicationContext(),
						Manifest.permission.BLUETOOTH) !=
						PackageManager.PERMISSION_GRANTED) {
					//new ToastMessage(MainActivity.this,"OOOOOH");
					// TODO: Consider calling
					//    ActivityCompat#requestPermissions
					// here to request the missing permissions, and then overriding
					//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
					//                                          int[] grantResults)
					// to handle the case where the user grants the permission. See the documentation
					// for ActivityCompat#requestPermissions for more details.
					return null;
				}
				mmSocket = mmDevice.createRfcommSocketToServiceRecord(MY_UUID);

				// Connect to the device
				mmSocket.connect();

				// Get the input stream to receive data
				mmInputStream = mmSocket.getInputStream();
			} catch (IOException e) {
				ConnectSuccess = false;
			}
			return null;
		}




		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			if (!ConnectSuccess) {
				//show a message that the connection failed
			} else {
				//start listening to the input stream
				listenForData();
			}
		}
	}

	private void listenForData() {
		final byte delimiter = 10; // This is the ASCII
		//start a new thread to listen to the input stream
		new Thread(new Runnable() {
			public void run() {
				while (!Thread.currentThread().isInterrupted()) {
					try {
						int bytesAvailable = mmInputStream.available();
						if (bytesAvailable > 0) {
							byte[] packetBytes = new byte[bytesAvailable];
							mmInputStream.read(packetBytes);
							for (int i = 0; i < bytesAvailable; i++) {
								byte b = packetBytes[i];
								if (b == delimiter) {
									final String data = sb.toString();
									sb.setLength(0);
									runOnUiThread(new Runnable() {
										@Override
										public void run() {
											//update the TextView with the received data
											mTextView.setText(data);
										}
									});
								} else {
									sb.append((char) b);
								}
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			//close the socket
			mmSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
