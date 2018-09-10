package edu.gatech.mas.letsteach.utility;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.gatech.mas.letsteach.R;


public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {


    RecyclerView mRecyclerView;
    OnItemClickListener lItemClickListener;


    public interface OnItemClickListener {

        public void onItemClick(View view, int position);

        public void onItemLongClick(View view, int position);

        public void onOverflowMenuClick(View v, final int position);
    }

    public void setOnItemClickListener(final OnItemClickListener lItemClickListener) {
        this.lItemClickListener = lItemClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        public TextView nameTextView;
        public TextView nameDescView;
        public TextView quantityDescView;
        public ImageView vMenu;
        public ImageView img_icon;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.minCardView);
            nameTextView = (TextView) itemView.findViewById(R.id.title);
            nameDescView = (TextView) itemView.findViewById(R.id.description);
            quantityDescView = (TextView) itemView.findViewById(R.id.listqty);
            vMenu=(ImageView) itemView.findViewById(R.id.overflow);
            img_icon=(ImageView) itemView.findViewById(R.id.img_icon);

            if(vMenu!=null){
                vMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (lItemClickListener!=null){
                            lItemClickListener.onOverflowMenuClick(v,getAdapterPosition());
                        }
                    }
                });
            }

            if(img_icon!=null){
                img_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (lItemClickListener!=null){
                            lItemClickListener.onOverflowMenuClick(v,getAdapterPosition());
                        }
                    }
                });
            }


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (lItemClickListener != null) {
                        lItemClickListener.onItemLongClick(v, getAdapterPosition());
                    }
                    return true;
                }

            });

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (lItemClickListener != null) {
                        lItemClickListener.onItemClick(v, getAdapterPosition());
                    }

                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (lItemClickListener != null) {
                        lItemClickListener.onItemLongClick(v, getAdapterPosition());
                    }
                    return true;
                }

            });


            if(vMenu!=null){
                vMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (lItemClickListener!=null){
                            lItemClickListener.onOverflowMenuClick(v,getAdapterPosition());
                        }
                    }
                });
            }
        }
    }

        List<ListBean> listItemView ;

    public void setListForAdapter( List<ListBean> list )
    {
        listItemView = list;
    }

        private Context lContext;
        public MainListAdapter(Context context, List<ListBean> listItems) {
            listItemView = listItems;
            lContext = context;
        }
        private Context getContext() {
            return lContext;
        }

        @Override
        public MainListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            final View contactView = inflater.inflate(R.layout.cardview, parent, false);
                mRecyclerView = (RecyclerView) contactView.findViewById(R.id.listView);

            MainListAdapter.ViewHolder viewHolder = new MainListAdapter.ViewHolder(contactView);
            return viewHolder;
        }

        // Involves populating data into the item through holder
        @Override
        public void onBindViewHolder(MainListAdapter.ViewHolder viewHolder, int position) {


            TextView textView = viewHolder.nameTextView;
            textView.setText(listItemView.get(position).getListName());

            TextView textViewDesc = viewHolder.nameDescView;
            textViewDesc.setText(listItemView.get(position).getListDescription());

            //Item inside List to be added later after Integration
            TextView textViewQty = viewHolder.quantityDescView;
            textViewQty.setVisibility(View.INVISIBLE);
            //textViewQty.setText(listItemView.get(position).getListId());

            ImageView vMenu = viewHolder.img_icon;
            if(listItemView.get(position).getImage()!=null)
            vMenu.setImageDrawable(listItemView.get(position).getImage());

        }

        @Override
        public int getItemCount() {
            return listItemView.size();
        }

    }

