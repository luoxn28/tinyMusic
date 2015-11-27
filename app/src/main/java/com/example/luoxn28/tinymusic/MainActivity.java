package com.example.luoxn28.tinymusic;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends ActionBarActivity implements View.OnClickListener{
    // 用于输出调试信息，表明当前在那个活动中
    private static final String TAG = "MainActivity";

    private Button play_music;
    //private Button pause;
    //private Button stop;

    private boolean isSinging = false;
    private MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play_music = (Button) findViewById(R.id.play_music);
        play_music.setOnClickListener(this);

        initMediaPlayer(); // 初始化播放
    }

    private void initMediaPlayer() {
        try {
            /* getExternalStorageDirectory()获取SD卡完整路径，比如/storage/emulated/0，music.mp3是存放在手机内存卡的根目录下的
             * 测试手机为魅蓝note
             */
            File file = new File(Environment.getExternalStorageDirectory(), "music.mp3");
            Log.d(TAG, file.getPath()); // 打印文件完整路径

            mediaPlayer.setDataSource(file.getPath()); // 指定音频文件路径
            mediaPlayer.prepare(); // 进入准备状态
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_music:
                if (!isSinging) {
                    isSinging = true;
                    mediaPlayer.start(); // 开始或继续播放
                    play_music.setBackgroundResource(R.drawable.pause_music); // 变换背景图片
                }
                else {
                    isSinging = false;
                    mediaPlayer.pause(); // 暂停播放
                    play_music.setBackgroundResource(R.drawable.play_music);
                }
                break;
            /*
            case R.id.pause:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause(); // 暂停播放
                }
                break;

            case R.id.stop:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.reset(); // 停止播放
                    initMediaPlayer();
                }
                break;
            */
            default:
                break;
        }
    }

    /*
     * 在onDestroy()方法中将与MediaPlayer相关的资源释放掉
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
