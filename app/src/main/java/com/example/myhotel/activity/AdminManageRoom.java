package com.example.myhotel.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myhotel.R;
import com.example.myhotel.adapter.ManageRoomsAdapter;
import com.example.myhotel.roomapi.RoomFetchData;
import com.example.myhotel.roomapi.RoomModel;
import com.example.myhotel.roomapi.RoomViewFetchMessage;

import java.util.ArrayList;

public class AdminManageRoom extends Activity implements RoomViewFetchMessage {

    private RecyclerView ListDataView;
    private ManageRoomsAdapter manageRoomsAdapter;
    ArrayList<RoomModel> roomModelArrayList = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_activity);


        ListDataView = findViewById(R.id.ListViewRoom);
        TextView title = findViewById(R.id.pageTitle);
        title.setText("Manage Room Record");
        RoomFetchData roomFetchData = new RoomFetchData(this, this);
        RecyclerViewMethods();
        roomFetchData.onSuccessUpdate(this);

    }

    public void RecyclerViewMethods() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        ListDataView.setLayoutManager(manager);
        ListDataView.setHasFixedSize(true);
        manageRoomsAdapter = new ManageRoomsAdapter(this, roomModelArrayList);
        ListDataView.setAdapter(manageRoomsAdapter);
        ListDataView.invalidate();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onUpdateSuccess(RoomModel message) {

        if(message != null){
            RoomModel roomModel = new RoomModel(message.getId(),message.getTitle(),message.getDescription(),message.getIsAvailable(),message.getLocation(),
                    message.getImageUrl(),message.getPrice());

            roomModelArrayList.add(roomModel);
        }
        manageRoomsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onUpdateFailure(String message) {
        Toast.makeText(AdminManageRoom.this, message, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AdminManageRoom.this, AdminPanel.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
