package uac.imsp.clockingapp.View.activity.settings;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import uac.imsp.clockingapp.R;

public class ShareAppViaQRCode extends AppCompatActivity {


	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share_app_via_qrcode);
		// calling the action bar
		ActionBar actionBar = getSupportActionBar();
// showing the back button in action bar
		assert actionBar != null;
		actionBar.setDisplayHomeAsUpEnabled(true);
		generateQRCode();
	}

		public void generateQRCode() {
			String url = "https://play.google.com/store/apps/details?id=com.example.myapp";
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			try {
				BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 500, 500);
				int width = bitMatrix.getWidth();
				int height = bitMatrix.getHeight();
				Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {
						bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
					}
				}
				ImageView imageView = findViewById(R.id.app_via_qr_img);

				imageView.setImageBitmap(bmp);

			} catch (WriterException e) {
				e.printStackTrace();
			}
		}
	}

