package UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308_jordan.R;

import java.util.List;

import entities.VacationEntity;

public class VacationAdapter extends RecyclerView.Adapter<VacationAdapter.VacationViewHolder>{
    private List<VacationEntity> mVacations;
    private final Context context;

    private final LayoutInflater mInflater;
    public VacationAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context=context;
    }

    public class VacationViewHolder extends RecyclerView.ViewHolder {
        private final TextView vacationItemView;


        public VacationViewHolder(@NonNull View itemView) {
            super(itemView);
            vacationItemView=itemView.findViewById(R.id.textView5);
            vacationItemView.setTextColor(ContextCompat.getColor(itemView.getContext(), android.R.color.holo_blue_dark));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    final VacationEntity current=mVacations.get(position);
                    Intent intent=new Intent(context,Vacation_Details.class);
                    intent.putExtra("id", current.getVacationID());
                    intent.putExtra("name", current.getVacationName());
                    intent.putExtra("hotelName", current.getHotelName());
                    intent.putExtra("price", current.getPrice());
                    intent.putExtra("startDate", current.getVacationStart());
                    intent.putExtra("endDate", current.getVacationEnd());
                    context.startActivity(intent);
                    context.startActivity(intent);
                }
            });

        }
    }
    @NonNull
    @Override
    public VacationAdapter.VacationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.vacation_list_item,parent,false);
        return new VacationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VacationAdapter.VacationViewHolder holder, int position) {
        if (mVacations!=null){
            VacationEntity current=mVacations.get(position);
            String name=current.getVacationName();
            holder.vacationItemView.setText(name);
        }
        else{
            holder.vacationItemView.setText("No vacation name");
        }

    }

    @Override
    public int getItemCount() {
        if (mVacations!=null){
            return mVacations.size();
        }
        else return 0;
    }
    public void setVacations(List<VacationEntity> vacations){
        mVacations=vacations;
        notifyDataSetChanged();
    }


}
