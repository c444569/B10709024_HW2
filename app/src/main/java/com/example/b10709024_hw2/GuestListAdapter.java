package com.example.b10709024_hw2;


import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.ShapeDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b10709024_hw2.data.WaitlistContract;


class GuestListAdapter extends RecyclerView.Adapter<GuestListAdapter.ViewHolder> {
    private Context mContext;
    private Cursor mCursor;
    private ShapeDrawable s;

    public GuestListAdapter(Context context, Cursor cursor, ShapeDrawable s) {
        this.mContext = context;
        this.mCursor = cursor;
        this.s = s;
    }

    public void reset(Cursor c) {
        if (this.mCursor != null)
            this.mCursor.close();
        this.mCursor = c;
        if (this.mCursor != null)
            this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_layout,parent,false);

        return new GuestListAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position))
            return;
        String name = mCursor.getString(mCursor.getColumnIndex(

                WaitlistContract.WaitlistEntry.COLUMN_NAME));
        int partySize = mCursor.getInt(mCursor.getColumnIndex(
                WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE));
        long id = mCursor.getLong(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry._ID));

        holder.partySizeTextView.setText(partySize+"");
        holder.partySizeTextView.setBackground(this.s);

        holder.nameTextView.setText(name);

        holder.itemView.setTag(id);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView, partySizeTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.ntv);
            partySizeTextView = itemView.findViewById(R.id.ptv);
        }
    }

    public void setColor(int color) {
        this.s.getPaint().setColor(color);
        notifyDataSetChanged();
    }
}
