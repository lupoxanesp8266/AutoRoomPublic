package lupoxan.autoroom.com.autoroom11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author lupo.xan
 * @since 09/10/2019
 * @version 0.0.1
 */
public class MyCustomAdapter extends BaseAdapter {
    private ArrayList<Valores> valores;
    private Context context;

    public MyCustomAdapter(Context context, ArrayList<Valores> valores){
        this.context = context;
        this.valores = valores;
    }

    @Override
    public int getCount() {
        return valores.size();
    }

    @Override
    public Object getItem(int position) {
        return valores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.my_adapter_layout, parent, false);
        }

        TextView hora = convertView.findViewById(R.id.horaLabel);
        TextView mov = convertView.findViewById(R.id.movLabel);
        TextView interior = convertView.findViewById(R.id.intLabel);
        TextView exterior = convertView.findViewById(R.id.extLabel);

        Valores temp = valores.get(position);

        hora.setText(temp.getHora());
        mov.setText(temp.getPir());
        interior.setText(temp.getInterior());
        exterior.setText(temp.getExterior());

        return convertView;
    }
}
