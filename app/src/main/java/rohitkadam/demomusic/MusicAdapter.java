package rohitkadam.demomusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by 170840521012 on 29-11-2017.
 */

public class MusicAdapter extends BaseAdapter {
    ArrayList<MyMusic> myMusicArrayList;
    Context context;

    public MusicAdapter(Context context,ArrayList<MyMusic> list){
        this.context=context;
        myMusicArrayList=list;
    }

    @Override
    public int getCount() {
        return myMusicArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return myMusicArrayList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View rootView=layoutInflater.inflate(R.layout.list_item,viewGroup,false);

        TextView name=rootView.findViewById(R.id.textViewName);
        TextView path=rootView.findViewById(R.id.textViewPath);

        MyMusic myMusic=myMusicArrayList.get(i);
        name.setText(myMusic.getTitle());
        path.setText(myMusic.getDisc());

        return rootView;
    }
}
