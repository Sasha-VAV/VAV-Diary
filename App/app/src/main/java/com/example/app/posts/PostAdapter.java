package com.example.app.posts;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.Pages.DiaryFragment;
import com.example.app.R;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {

    ArrayList<Post> posts = new ArrayList<>();
    Application application;

    public PostAdapter(ArrayList<Post> posts, Application application) {
        this.posts = posts;
        this.application = application;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.one_post, parent, false);

        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        Post post = posts.get(position);
        holder.head.setText(post.getHead());
        holder.time.setText(post.getTime());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaryFragment.currentPost = post;
                DiaryFragment.postIsClicked = true;
                Navigation.findNavController(DiaryFragment.view).navigate(R.id.action_diaryFragment_to_postFragment2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class PostHolder extends RecyclerView.ViewHolder {
        TextView time, head;
        LinearLayout linearLayout;
        public PostHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.textTime);
            head = itemView.findViewById(R.id.header_title);
            linearLayout = itemView.findViewById(R.id.LL);
        }
    }
}
