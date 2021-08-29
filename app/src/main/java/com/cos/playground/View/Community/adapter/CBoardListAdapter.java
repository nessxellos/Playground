package com.cos.playground.View.Community.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cos.playground.Model.CBoard;
import com.cos.playground.R;
import com.cos.playground.View.Community.CBoardDetailActivity;
import com.cos.playground.View.Community.CBoardListActivity;

import java.util.ArrayList;
import java.util.List;

public class CBoardListAdapter extends RecyclerView.Adapter<CBoardListAdapter.MyViewHolder> {

    private static final String TAG = "CBoardAdapter";
    private CBoardListAdapter CBoardListAdapter = this;
    private CBoardListActivity mContext;

    public CBoardListAdapter(CBoardListActivity mContext){
        this.mContext = mContext;
    }

    private List<CBoard> cBoards = new ArrayList<>();

    public void addItems(List<CBoard> cBoards){
        this.cBoards = cBoards;
        Log.d(TAG, "addItems: size : "+cBoards.size());
        notifyDataSetChanged();
    }

    public void addItem(CBoard cBoard){
        this.cBoards.add(cBoard);
        notifyDataSetChanged();
    }

    public List<CBoard> getItems(){
        return cBoards;
    }

    public void removeItem(int index){
        cBoards.remove(index);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =  layoutInflater.inflate(R.layout.cboard_list_item, parent, false);

        return new MyViewHolder(view);
    }

    // ViewHolder 데이터 갈아끼우는 친구
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CBoard cBoard = cBoards.get(position);
        holder.setItem(cBoard);
    }

    // 어댑터가 알아서 호출해서 사이즈 2?
    // 화면크기가 600
    // 아이템 크기가 200
    @Override
    public int getItemCount() {
        return cBoards.size();
    }

    // 1. 뷰홀더 만들기 - 데이터 갈아 끼우는 친구!!
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvCBoardNum, tvCBoardTitle;

        // 앱 구동시에 발동
        public MyViewHolder(View itemView) {
            super(itemView);
            tvCBoardNum = itemView.findViewById(R.id.tvCBoardNum);
            tvCBoardTitle = itemView.findViewById(R.id.tvCBoardTitle);

            initLr();
        }

        private void initLr(){
            itemView.setOnClickListener(v -> {
//                Log.d(TAG, "initLr: 클릭됨 "+getAdapterPosition());
                CBoard cBoard = cBoards.get(getAdapterPosition());
//                Log.d(TAG, "initLr: cBoard.getId() : "+cBoard.getId());
                Intent intent = new Intent(
                        mContext,
                        CBoardDetailActivity.class
                );
                intent.putExtra("cBoardId", cBoard.getId());
                mContext.startActivity(intent);
            });
        }

        // 앱 구동시에 발동 + 스크롤할 때 발동
        public void setItem(CBoard cBoard){
            tvCBoardNum.setText(cBoard.getId()+"");
            tvCBoardTitle.setText(cBoard.getTitle());
        }
    }
}
