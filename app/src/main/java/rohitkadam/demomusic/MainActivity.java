package rohitkadam.demomusic;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    MediaPlayer mediaPlayer;
    MusicAdapter musicAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.listView);
        checkPermission();

        final ArrayList<MyMusic> musicArrayList=getAll();;

        musicAdapter=new MusicAdapter(MainActivity.this,musicArrayList);
        listView.setAdapter(musicAdapter);
        musicAdapter.notifyDataSetChanged();


        /*String name[]=new String[musicArrayList.size()];

        for(int i=0;i < musicArrayList.size(); i++){
            name[i]= musicArrayList.get(i).getTitle();
        }*/


        /*ArrayAdapter<String> adapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,name);

        listView.setAdapter(adapter);*/

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyMusic myMusic=musicArrayList.get(i);

                if(mediaPlayer != null && mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer=null;
                }
                mediaPlayer=new MediaPlayer();

                try {
                    mediaPlayer.setDataSource(myMusic.getDisc());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                mediaPlayer.stop();
                return true;
            }
        });


    }

    public ArrayList<MyMusic> getAll(){

        ArrayList<MyMusic> myMusicsList=new ArrayList<>();
        ContentResolver contentResolver=getContentResolver();
        Uri uri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI; //path for music
        /*Uri uri1= ContactsContract.CommonDataKinds.Phone.CONTENT_URI;*/ // path for contacts
        Cursor cursor=contentResolver.query(uri,null,null,null,null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            MyMusic myMusic=new MyMusic();
            String title=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
            String disc=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));

            myMusic.setTitle(title);
            myMusic.setDisc(disc);

            myMusicsList.add(myMusic);
            cursor.moveToNext();
        }
        cursor.close();

        return myMusicsList;
    }

    public void checkPermission(){
        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            String[] permission={Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(MainActivity.this,permission,123);

            return;
        }
    }

}
