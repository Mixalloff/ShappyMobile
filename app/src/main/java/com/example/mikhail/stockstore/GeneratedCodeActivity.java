package com.example.mikhail.stockstore;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mikhail.stockstore.Classes.CommonFunctions;
import com.example.mikhail.stockstore.Constants.CommonConstants;
import com.example.mikhail.stockstore.Entities.Stock;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.QRCode;

public class GeneratedCodeActivity extends AppCompatActivity {

    ImageView codeImage;
    int QRWidth = 300;
    int QRHeight = 300;
    TextView textCode;
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generated_code);

        CommonFunctions.setToolbar(this, R.id.toolbar, CommonConstants.TOOLBAR_NAV_CLOSE);

        code = getIntent().getStringExtra("code");

        textCode = (TextView)findViewById(R.id.text_code);
        textCode.setText(code);

        codeImage = (ImageView) findViewById(R.id.qr_code_place);
        codeImage.setImageBitmap(GenerateQRCode(textCode.getText().toString()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_generated_code, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Генерирует изображение qr-кода по строке
    public Bitmap GenerateQRCode(String encodedString) {
        try {
            BitMatrix matrix = new QRCodeWriter().encode(encodedString, BarcodeFormat.QR_CODE, QRWidth, QRHeight);
            Bitmap bm = matrixToBitmap(matrix);
            if (bm != null) {
                return bm;
            }
        } catch (WriterException e) {
            return null;
        }
        return null;
    }

    // Конвертация битовой матрицы в изображение
    public static Bitmap matrixToBitmap(BitMatrix matrix){
        int height = matrix.getHeight();
        int width = matrix.getWidth();
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                bmp.setPixel(x, y, matrix.get(x,y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
    }
}
