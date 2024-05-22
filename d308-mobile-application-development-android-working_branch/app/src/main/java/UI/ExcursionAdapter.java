package UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308_jordan.R;

import java.util.List;

import entities.Excursion;

    public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {
        private List<Excursion> mExcursions;
        private final Context context;
        private final LayoutInflater mInflater;

        class ExcursionViewHolder extends RecyclerView.ViewHolder{

        private final TextView excursionItemView;


        private ExcursionViewHolder(View itemView){
            super(itemView);
            excursionItemView=itemView.findViewById(R.id.textView6);
//            excursionItemView2=itemView.findViewById(R.id.textView7);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    final Excursion current = mExcursions.get(position);
                    Intent intent = new Intent(context, Excursion_Details.class);
                    intent.putExtra("id", current.getExcursionID());
                    intent.putExtra("name", current.getExcursionName());
                    intent.putExtra("price", current.getPrice());
                    intent.putExtra("vacayID", current.getVacationID());
                    intent.putExtra("excursionDate", current.getExcursionDate());
                    context.startActivity(intent);

                }
            });
        }

    }
    public ExcursionAdapter(Context context){
        mInflater= LayoutInflater.from(context);
        this.context=context;
    }
    @NonNull
    @Override
    public ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView=mInflater.inflate(R.layout.excursion_list_item,parent, false);
        return new ExcursionViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull ExcursionViewHolder holder, int position) {
        if (mExcursions!=null){
            Excursion current= mExcursions.get(position);
            String name=current.getExcursionName();
            int prodID= current.getExcursionID();
            String date = current.getExcursionDate();
            holder.excursionItemView.setText(name);
//            holder.excursionItemView2.setText(Integer.toString(prodID));
        }
        else{
            holder.excursionItemView.setText("No excursion name");
            holder.excursionItemView.setText("No vacation id");
        }
    }
    public void setExcursions(List<Excursion> excursions){
        mExcursions=excursions;
        notifyDataSetChanged();
    }
    public int getItemCount(){
        if (mExcursions!=null) return mExcursions.size();
        else return 0;
    }

}

