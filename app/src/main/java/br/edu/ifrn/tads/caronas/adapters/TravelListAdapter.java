package br.edu.ifrn.tads.caronas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import br.edu.ifrn.tads.caronas.R;
import br.edu.ifrn.tads.caronas.data.Travel;

public class TravelListAdapter extends ArrayAdapter<Travel> {
    public TravelListAdapter(Context context) {
        super(context, R.layout.activity_travellist_listview);
    }

    public TravelListAdapter(Context context, Travel[] objects) {
        super(context, R.layout.activity_travellist_listview, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_travellist_listview, null);
            holder = new ListViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ListViewHolder) convertView.getTag();
        }
        holder.fillFromObject(getItem(position));
        return convertView;
    }

    static class ListViewHolder {
        TextView origin;
        TextView destination;
        TextView aperture;
        TextView arrival;
        TextView vacancies;

        public ListViewHolder(View view) {
            origin = (TextView) view.findViewById(R.id.travel_list_text_origin);
            destination = (TextView) view.findViewById(R.id.travel_list_text_destination);
            aperture = (TextView) view.findViewById(R.id.travel_list_text_aperture);
            arrival = (TextView) view.findViewById(R.id.travel_list_text_arrival);
            vacancies = (TextView) view.findViewById(R.id.travel_list_text_vacancies);
        }

        public void fillFromObject(Travel t) {
            origin.setText(t.getOrigin());
            destination.setText(t.getDestination());
            aperture.setText(t.getApertureDate().toString());
            arrival.setText(t.getArrivalDate().toString());
            vacancies.setText(String.valueOf(t.getVacancies()));
        }
    }
}
