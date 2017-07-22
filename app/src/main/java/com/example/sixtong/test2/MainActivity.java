package com.example.sixtong.test2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class MainActivity extends Activity implements View.OnClickListener{

    private Button btnProc;
    private ImageView imageView;
    private Bitmap bmp;

    //OpenCV类库加载并初始化成功后的回调函数，在此我们不进行任何操作
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:{
                } break;
                default:{
                    super.onManagerConnected(status);
                } break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnProc = (Button) findViewById(R.id.button);
        imageView = (ImageView) findViewById(R.id.image_view);
        //将lena图像加载程序中并进行显示
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.beauty);
        imageView.setImageBitmap(bmp);
        btnProc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Mat rgbMat = new Mat();
        Mat grayMat = new Mat();
        //获取lena彩色图像所对应的像素数据
        Utils.bitmapToMat(bmp, rgbMat);
        //将彩色图像数据转换为灰度图像数据并存储到grayMat中
        Imgproc.cvtColor(rgbMat, grayMat, Imgproc.COLOR_RGB2GRAY);
        //创建一个灰度图像
        Bitmap grayBmp = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.RGB_565);
        //将矩阵grayMat转换为灰度图像
        Utils.matToBitmap(grayMat, grayBmp);
        imageView.setImageBitmap(grayBmp);
    }

    public void onResume(){
        super.onResume();
        //通过OpenCV引擎服务加载并初始化OpenCV类库，所谓OpenCV引擎服务即是
        //OpenCV_2.4.3.2_Manager_2.4_*.apk程序包，存在于OpenCV安装包的apk目录中
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);
    }

}
