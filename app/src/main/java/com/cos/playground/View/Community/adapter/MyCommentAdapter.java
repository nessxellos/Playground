package com.cos.playground.View.Community.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.playground.Controller.CommentController;
import com.cos.playground.Controller.DTO.CMRespDto;
import com.cos.playground.Controller.DTO.DelCoDto;
import com.cos.playground.Model.Comment;
import com.cos.playground.Model.User;
import com.cos.playground.R;
import com.cos.playground.View.Community.CBoardDetailActivity;
import com.cos.playground.View.User.MyCommentActivity;
import com.cos.playground.config.SessionUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCommentAdapter extends RecyclerView.Adapter<MyCommentAdapter.ViewHolders> {

    private static final String TAG = "MyComment";
    private MyCommentAdapter myCommentAdapter = this;
    private MyCommentActivity mContext;

    private CommentController commentController;

    public MyCommentAdapter(MyCommentActivity mContext) {this.mContext = mContext;}

    private List<Comment> comments = new ArrayList<>();

    public void addItems(List<Comment> comments){
        this.comments = comments;
        notifyDataSetChanged();
    }

    public void addItem(Comment comment){
        this.comments.add(comment);
        notifyDataSetChanged();
    }

    public List<Comment> getItems(){
        return comments;
    }

    public void removeItem(int index){
        comments.remove(index);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.mycomment_list, parent, false);

        return new ViewHolders(view);
    }

    @Override
    public void onBindViewHolder(MyCommentAdapter.ViewHolders holder, int position) {
        Comment comment = comments.get(position);
        holder.setItem(comment);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {

        TextView tvCommentId,tvCBoardId,tvComment,tvCommentDate;
        Button btnGoCboard;

        public ViewHolders(View itemView){
            super(itemView);
            tvCommentId = itemView.findViewById(R.id.tvCommentId);
            tvCBoardId = itemView.findViewById(R.id.tvCBoardId);
            tvComment = itemView.findViewById(R.id.tvComment);
            tvCommentDate = itemView.findViewById(R.id.tvCommentDate);
            btnGoCboard = itemView.findViewById(R.id.btnGoCboard);
            commentController = new CommentController();
            initLr();
        }

        public void setItem(Comment comment){
            Log.d(TAG, "setItem: "+comment);
            String cid = String.valueOf(comment.getCid());
            String bid = String.valueOf(comment.getBoardId());
            Date form = comment.getRegdate();
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = transFormat.format(form);
            tvCommentId.setText(cid);
            tvCBoardId.setText(bid);
            tvComment.setText("내용 : "+comment.getContent());
            tvCommentDate.setText(date);
        }

        private void initLr(){
            btnGoCboard.setOnClickListener(v->{

        });
        }
    }
}
