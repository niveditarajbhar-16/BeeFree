package com.sushant.contactapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class PersonListAdapter extends ListAdapter<Person,PersonListAdapter.PersonViewHolder> {
    private OnItemClickListener onItemClickListener;

    protected PersonListAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Person> DIFF_CALLBACK = new DiffUtil.ItemCallback<Person>() {
        @Override
        public boolean areItemsTheSame(@NonNull Person oldItem, @NonNull Person newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Person oldItem, @NonNull Person newItem) {
            return oldItem.getFirstName().equalsIgnoreCase(newItem.getFirstName())
                    &&oldItem.getLastName().equalsIgnoreCase(newItem.getLastName())
                    &&oldItem.getMobileNo().equalsIgnoreCase(newItem.getMobileNo());
        }
    };

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        String profileWord = getItem(position).getFirstName().substring(0,1);
        holder.personText.setText(getItem(position).getFirstName()+" "+getItem(position).getLastName());
        holder.personProfileWord.setText(profileWord);
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {
        private Button personProfileWord;
        private TextView personText;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            personProfileWord = (Button)itemView.findViewById(R.id.contact_word_btn);
            personText = (TextView)itemView.findViewById(R.id.contact_name_tv);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(getItem(position));
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Person person);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
