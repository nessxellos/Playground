package com.cos.playground.View.Community.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.playground.Controller.CommentController;
import com.cos.playground.Controller.DTO.CMRespDto;
import com.cos.playground.Controller.DTO.CommentDto;
import com.cos.playground.Controller.DTO.DelCoDto;
import com.cos.playground.Model.Comment;
import com.cos.playground.Model.User;
import com.cos.playground.R;
import com.cos.playground.View.Community.CBoardDetailActivity;
import com.cos.playground.config.SessionUser;
import com.cos.playground.service.CommentService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolders> {

    private static final String TAG = "CommentList";
    private CommentListAdapter commentListAdapter = this;
    private CBoardDetailActivity mContext;

    private CommentController commentController;

    public CommentListAdapter(CBoardDetailActivity mContext) {this.mContext = mContext;}

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
        View view = layoutInflater.inflate(R.layout.comment_list_view, parent, false);

        return new ViewHolders(view);
    }

    @Override
    public void onBindViewHolder(CommentListAdapter.ViewHolders holder, int position) {
        Comment comment = comments.get(position);
        holder.setItem(comment);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {

        TextView commentUser,commentContent,tvCommentUd,tvCommentDel,commentDate,tvCommentId,tvCBoardId;

        public ViewHolders(View itemView){
            super(itemView);
            commentUser = itemView.findViewById(R.id.commentUser);
            commentContent = itemView.findViewById(R.id.commentContent);
            tvCommentUd = itemView.findViewById(R.id.tvCommentUd);
            tvCommentDel = itemView.findViewById(R.id.tvCommentDel);
            commentDate = itemView.findViewById(R.id.commentDate);
            tvCommentId = itemView.findViewById(R.id.tvCommentId);
            tvCBoardId = itemView.findViewById(R.id.tvCBoardId);
            commentController = new CommentController();
            initLr();
        }

        public void setItem(Comment comment){
            commentUser.setText(comment.getUsername());
            commentContent.setText(comment.getContent());
            Date form = comment.getRegdate();
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = transFormat.format(form);
            commentDate.setText(date);
            String cid = String.valueOf(comment.getCid());
            tvCommentId.setText(cid);
            String bid = String.valueOf(comment.getBoardId());
            tvCBoardId.setText(bid);
        }

        private void initLr(){
            if(SessionUser.sessionId==null){
                tvCommentUd.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
                tvCommentDel.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
            }

            tvCommentDel.setOnClickListener(v->{
                int cid = Integer.parseInt(tvCommentId.getText().toString());
                DelCoDto delCoDto = new DelCoDto(SessionUser.user.getId());
                new AlertDialog.Builder(mContext).setTitle("댓글을 삭제하시겠습니까?").setMessage("")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                commentController.deleteByCid(cid, delCoDto).enqueue(new Callback<CMRespDto<User>>() {
                                    @Override
                                    public void onResponse(Call<CMRespDto<User>> call, Response<CMRespDto<User>> response) {
                                        CMRespDto<User> cm = response.body();
                                        if(cm.getCode()==1){
                                            new AlertDialog.Builder(mContext).setTitle("댓글삭제 완료")
                                                    .setMessage("").setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            }).create().show();
                                            Intent intent = new Intent(
                                              mContext,
                                              CBoardDetailActivity.class
                                            );
                                            mContext.startActivity(intent);
                                        } else{
                                            Toast.makeText(mContext, "로그인 정보가 유효하지 않습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<CMRespDto<User>> call, Throwable t) {
                                        t.printStackTrace();
                                    }
                                });
                            };
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }}).show();
                });
            tvCommentUd.setOnClickListener(v->{
                final EditText et = new EditText(mContext);
                FrameLayout container = new FrameLayout(mContext);
                FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                et.setLayoutParams(params);
                container.addView(et);
                final AlertDialog.Builder alt_bld = new AlertDialog.Builder(mContext,R.style.MyAlertDialogStyle);
                alt_bld.setTitle("수정할 내용을 입력하세요").setIcon(R.drawable.ic_insert_comment).setCancelable(false)
                        .setView(container).setPositiveButton("수정하기",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String content = et.getText().toString();
                                Comment comment = new Comment();
                                comment.setContent(content);
                                comment.setUserId(SessionUser.user.getId());
                                int cid = Integer.parseInt(tvCommentId.getText().toString());
                                commentController.updateByCid(cid, comment).enqueue(new Callback<CMRespDto<Comment>>() {
                                    @Override
                                    public void onResponse(Call<CMRespDto<Comment>> call, Response<CMRespDto<Comment>> response) {
                                        CMRespDto<Comment> cm = response.body();
                                        if(cm.getCode()==1){
                                            Toast.makeText(mContext, "댓글 수정 성공.", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(
                                                    mContext,
                                                    CBoardDetailActivity.class
                                            );
                                            mContext.startActivity(intent);                                        } else {
                                            Toast.makeText(mContext, "로그인 정보가 유효하지 않습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<CMRespDto<Comment>> call, Throwable t) {
                                        t.printStackTrace();
                                    }
                                });
                            }
                        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(mContext, "댓글수정 취소.", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alert = alt_bld.create();
                alert.show();
            });
        }
    }
}
