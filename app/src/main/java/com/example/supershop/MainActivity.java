package com.example.supershop;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.supershop.data.Book;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int MENU_ID_ADD = 1;
    public static final int MENU_ID_UPDATE = 2;
    public static final int MENU_ID_DELETE = 3;

    private final String[] Books = {"红楼梦", "活着", "狂人日记"};
    private SearchView SearchView;
    private android.widget.ListView ListView;

    private ArrayList<Book> books;   //书籍列表
    private MainRecycleViewAdapter mainRecycleViewAdapter;

    //添加操作详情
    private ActivityResultLauncher<Intent> addDataLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
            ,result ->{
                if(null!=result){
                    Intent intent=result.getData();
                    if(result.getResultCode()==InputBookActivity.RESULT_CODE_SUCCESS) {
                        Bundle bundle = intent.getExtras();
                        String title = bundle.getString("title");
                        String time = bundle.getString("time");
                        int position=bundle.getInt("position");
                        //ddd
                        books.add(1, new Book(R.drawable.ic_launcher_background, title,time));
                        //item.getOrder()在对应位置上添加数据
                        mainRecycleViewAdapter.notifyItemInserted(1);
                    }
                }
            });
    //更新操作详情
    private ActivityResultLauncher<Intent> updateDataLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
            ,result ->{
                if(null!=result){
                    Intent intent=result.getData();
                    if(result.getResultCode()==InputBookActivity.RESULT_CODE_SUCCESS) {
                        Bundle bundle = intent.getExtras();
                        String title = bundle.getString("title");
                        String time = bundle.getString("time");
                        int position=bundle.getInt("position");
                        books.get(position).setTitle("title");
                        books.get(position).setTime("time");
                        books.add(1, new Book(R.drawable.ic_launcher_background, title,time));
                        //item.getOrder()在对应位置上添加数据
                        mainRecycleViewAdapter.notifyItemChanged(position);
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerViewMain=findViewById(R.id.recyclerview_main);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(linearLayoutManager);

        books =new ArrayList<>();

        //添加书籍
        books.add(new Book(R.drawable.photo_1,"红楼梦","1953年12月"));
        books.add(new Book(R.drawable.photo_2,"活着","1992年6月"));
        books.add(new Book(R.drawable.photo_3,"狂人日记","1918年5月"));
        //String []mainDataSet=new String[]{
        //       "iterm 1","iterm 2","iterm 3","iterm 4",
        //        "iterm 5","iterm 6","iterm 7","iterm 8"
        //};
        mainRecycleViewAdapter= new MainRecycleViewAdapter(books);
        recyclerViewMain.setAdapter(mainRecycleViewAdapter);

        initFloatActionButton();//悬浮按钮
        //搜索操作实现
//            setContentView(R.layout.activity_main);
//            SearchView = (SearchView) findViewById(R.id.search_view);
//            ListView = (ListView) findViewById(R.id.listView);
//            ListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Books));
//            ListView.setTextFilterEnabled(true);
//            // 设置搜索文本监听
//            SearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                // 当点击搜索按钮时触发该方法
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    return false;
//                }

                // 当搜索内容改变时触发该方法
//                @Override
//                public boolean onQueryTextChange(String newText) {
//                    if (!TextUtils.isEmpty(newText)){
//                        ListView.setFilterText(newText);
//                    }else{
//                        ListView.clearTextFilter();//                   }
//                    return false;
//                }
//            });

    }
    //悬浮按钮响应操作
    public void initFloatActionButton(){
        FloatingActionButton button=findViewById(R.id.add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this ,InputBookActivity.class) ;
                intent.putExtra("position",books.size());
                addDataLauncher.launch(intent);

            }
        });
    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case MENU_ID_ADD:
                Intent intent=new Intent(this,InputBookActivity.class);
                intent.putExtra("position",item.getOrder());
                addDataLauncher.launch(intent);    //启动InputBookActivity
                //books.add(item.getOrder(),new Book(R.drawable.ic_launcher_background,"书名"));
                //item.getOrder()在对应位置上添加数据
                //mainRecycleViewAdapter.notifyItemInserted(item.getOrder());
                break;
            case MENU_ID_UPDATE:
                //books.get(item.getOrder()).setTitle("update");
                //mainRecycleViewAdapter.notifyItemChanged(item.getOrder());
                Intent intentUpdate=new Intent(this,InputBookActivity.class);
                intentUpdate.putExtra("position",item.getOrder());
                intentUpdate.putExtra("title",books.get(item.getOrder()).getTitle());
                intentUpdate.putExtra("time",books.get(item.getOrder()).getTime());
                updateDataLauncher.launch(intentUpdate);
                break;
            case MENU_ID_DELETE:
                AlertDialog alertDialog=new AlertDialog.Builder(this)
                        .setTitle(R.string.confirmation)
                        .setMessage(R.string.sure_to_delete)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                books.remove(item.getOrder());
                                mainRecycleViewAdapter.notifyItemRemoved(item.getOrder());
                            }
                        }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create();
                alertDialog.show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public class MainRecycleViewAdapter extends RecyclerView.Adapter<MainRecycleViewAdapter.ViewHolder>{
        private ArrayList<Book> localDataSet;


        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textViewTitle;
            private final ImageView imageViewImage;
            private final TextView textViewTime;

            public ViewHolder(View view){
                super(view);

                imageViewImage=view.findViewById(R.id.image);
                textViewTitle =view.findViewById(R.id.text);
                textViewTime = view.findViewById(R.id.time);

                view.setOnCreateContextMenuListener(this);
            }

            public TextView getTextViewTitle() {
                return textViewTitle;
            }

            public ImageView getImageViewImage() {
                return imageViewImage;
            }

            public TextView getTextViewTime(){return textViewTime;}


            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(0,MENU_ID_ADD, getAdapterPosition(),"Add "+getAdapterPosition());
                contextMenu.add(0,MENU_ID_UPDATE, getAdapterPosition(),"Update "+getAdapterPosition());
                contextMenu.add(0,MENU_ID_DELETE, getAdapterPosition(),"Delete "+getAdapterPosition());
            }
        }
        public MainRecycleViewAdapter(ArrayList<Book> dataSet){
            localDataSet=dataSet;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view= LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_main,viewGroup,false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder( ViewHolder viewHolder,final int position) {
            viewHolder.getImageViewImage().setImageResource(localDataSet.get(position).getImage());
            viewHolder.getTextViewTitle().setText(localDataSet.get(position).getTitle());
            viewHolder.getTextViewTime().setText(localDataSet.get(position).getTime());
        }

        @Override
        public int getItemCount() {
            return localDataSet.size();
        }

    }

}