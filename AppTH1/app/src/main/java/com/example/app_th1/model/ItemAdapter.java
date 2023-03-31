package com.example.app_th1.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_th1.R;
import com.example.app_th1.dbFake.TblItem;
import com.example.app_th1.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{

    private static final String NOTIFY = "Thông báo";
    private static final String MESSAGE_NOTIFY_DELETE = "Xác nhận xóa item";

    private Context context;
    private List<Item> items;
    private ItemListener itemListener;

    public ItemAdapter(Context context) {
        items = new ArrayList<>();
        this.context = context;
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Item getItem (int position) {return items.get(position);}

    public void addItem (Item item) {
        if (item != null) TblItem.addData(item);
    }

    public void setItem (Item item) {
        if (item != null) TblItem.updateData(item);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = items.get(position);
        if (item == null) return;
        holder.imageView.setImageResource(item.getImg());
        holder.textView.setText(String.format("%s\n%s\n%s", item.getName(), item.getDate(), item.getDescription()));
        holder.radio.setChecked(true);
        holder.radio.setText(item.getGender());
        item.getLanguages().forEach(language -> {
            if ("Java".equals(language)) {
                holder.java.setChecked(true);
            }
            if ("Python".equals(language)) {
                holder.python.setChecked(true);
            }
            if ("Golang".equals(language)) {
                holder.golang.setChecked(true);
            }
        });
        holder.btnDelete.setOnClickListener(view -> removeItem(position));
    }

    public void removeItem (int position) {
        Item item = items.get(position);
        AlertDialog.Builder builder = DialogUtils.createAlertDialog(context, NOTIFY,
                String.format("%s: %s", MESSAGE_NOTIFY_DELETE, item.getName()), R.drawable.icon_notify);
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            TblItem.deleteData(items.remove(position).getId());
            notifyDataSetChanged();
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {});
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imageView;
        private TextView textView;
        private ImageButton btnDelete;
        private RadioButton radio;
        private CheckBox java, python, golang;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
            itemView.setOnClickListener(this);
        }

        private void initView (View view) {
            imageView = view.findViewById(R.id.itemImage);
            textView = view.findViewById(R.id.info);
            btnDelete = view.findViewById(R.id.btnRemove);
            radio = view.findViewById(R.id.radio);
            java = view.findViewById(R.id.java);
            python = view.findViewById(R.id.python);
            golang = view.findViewById(R.id.golang);
        }

        @Override
        public void onClick(View view) {
            if (itemListener != null)
                itemListener.onClickItem(view, getAdapterPosition());
        }
    }

    public interface ItemListener {
        void onClickItem (View view, int position);
    }
}
