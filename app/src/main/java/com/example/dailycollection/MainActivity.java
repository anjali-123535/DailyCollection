package com.example.dailycollection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.state.State;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    DatabaseReference reference;
    FirebaseDatabase firebaseDatabase;
    FirebaseRecyclerAdapter<ItemData, ItemDataViewHolder> adapter;
    FirebaseRecyclerOptions<ItemData> recycleroptions;
    RecyclerView recyclerView;
    EditText message;
    private static final int REQ_CODE=45;
    int option;
    String itemid;
    TextView result;
    String strmobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         recyclerView=findViewById(R.id.recyclerview);
         LinearLayoutManager layoutManager=new LinearLayoutManager(this);
         recyclerView.setLayoutManager(layoutManager);
         recyclerView.setHasFixedSize(true);
strmobile=getIntent().getStringExtra("mobile");
auth=FirebaseAuth.getInstance();
firebaseDatabase=FirebaseDatabase.getInstance();
reference=firebaseDatabase.getReference().child("users").child(auth.getCurrentUser().getUid());
Query query = reference.orderByChild("time");
        recycleroptions =
                new FirebaseRecyclerOptions.Builder<ItemData>()
                        .setQuery(query, ItemData.class)
                        .build();
adapter=new FirebaseRecyclerAdapter<ItemData, ItemDataViewHolder>(recycleroptions) {
    @Override
    protected void onBindViewHolder(@NonNull final ItemDataViewHolder holder, int position, @NonNull final ItemData model) {
        if(model.getTodo()==1)
            holder.view.setBackgroundColor(getResources().getColor(R.color.coloradd));
        else
            holder.view.setBackgroundColor(getResources().getColor(R.color.colorsub));
        holder.amount.setText(String.valueOf(model.getAmount()));
        holder.date.setText(model.getDate());
        holder.category.setText(model.getCategory());
        holder.popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu menu=new PopupMenu(MainActivity.this,view);
                menu.getMenuInflater().inflate(R.menu.popup_menu,menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId())
                        {
                            case R.id.menuedit: ;
                                openbox(model.getTodo(), model.getItemid());
                                break;
                            case R.id.menudelete:
                                reference.child(model.getItemid()).removeValue();
                        }
                        return false;
                    }
                });
                menu.show();

            }
        });
    }

    @NonNull
    @Override
    public ItemDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview,parent,false);
        return new ItemDataViewHolder(view);
    }
};

recyclerView.setAdapter(adapter);
    }
 class ItemDataViewHolder extends RecyclerView.ViewHolder{
        TextView amount,date,category;
        View view;
        ImageView popup;
    public ItemDataViewHolder(@NonNull View itemView) {
        super(itemView);
        amount=itemView.findViewById(R.id.txt_amount);
        date=itemView.findViewById(R.id.txt_date);
        category=itemView.findViewById(R.id.txt_category);
        view=itemView.findViewById(R.id.card_item);
        popup=itemView.findViewById(R.id.popup);
    }
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                openbox(1,null);
                break;
            case R.id.subtract:
               openbox(2,null);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adapter!=null)
            adapter.stopListening();
    }
void saveChanges(ItemData model)
{

}
    void openbox(final int i, final String key) {
        //LayoutInflater li = LayoutInflater.from(this);
        View promptsView = getLayoutInflater().inflate(R.layout.custom, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        message = (EditText) promptsView
                .findViewById(R.id.editmessage);
        final EditText value= (EditText) promptsView
                .findViewById(R.id.editvalue);
        ImageButton btn_mike=(ImageButton)promptsView.findViewById(R.id.mikeButton);
        btn_mike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
                try {
                    startActivityForResult(intent, REQ_CODE);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(),
                            "Sorry your device not supported",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                validate(message.getText().toString(),value.getText().toString(),i,key);
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ_CODE) {
            if (resultCode == RESULT_OK && data!=null) {
                ArrayList<String> result = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
               message.append(result.get(0));
            }
        }
    }

    void validate(String message, final String amount, final int i,String key)
{
    int value=Integer.parseInt(amount);
    if(value>0)
    {
        if(message.equals(""))
            message="other";
        Date date = new Date();
        final String date1=new SimpleDateFormat("E, dd MMM yyyy").format(date);
        ItemData itemData=new ItemData();
        itemData.setAmount(value);
        itemData.setTime(System.currentTimeMillis());
        itemData.setCategory(message);
        itemData.setDate(date1);
        itemData.setMobile(strmobile);
        itemData.setUid(auth.getCurrentUser().getUid());
        itemData.setTodo(i);;
        itemData.setStar(false);
 if (key==null) {
     String  key1=reference.push().getKey();
     itemData.setItemid(key1);
             reference.child(key1).setValue(itemData);
 }
 else
 {
     itemData.setItemid(key);

     Map<String,Object> map = itemData.toMap();

     Map<String, Object> childUpdates = new HashMap<>();
     childUpdates.put(key, map);
     reference.updateChildren(childUpdates);
 }
    }
    else
    {
        Toast.makeText(this,"enter valid number",Toast.LENGTH_SHORT).show();
        return;
    }

}
}
