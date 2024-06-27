package com.example.kimsearappfinal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private List<MessageModel> messageList;

    private EditText messageInput;
    private ImageView sendButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        messageInput = view.findViewById(R.id.messageInput);
        sendButton = view.findViewById(R.id.sendButton);

        messageList = new ArrayList<>();
        adapter = new MessageAdapter(messageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        sendButton.setOnClickListener(v -> {
            String text = messageInput.getText().toString().trim();
            if (!text.isEmpty()) {
                MessageModel message = new MessageModel(text, "User");
                messageList.add(message);
                adapter.notifyDataSetChanged();
                messageInput.setText("");
                recyclerView.smoothScrollToPosition(messageList.size() - 1);
            }
        });

        // Initialize with static messages
        initializeStaticMessages();

        return view;
    }

    private void initializeStaticMessages() {
        messageList.add(new MessageModel("Hello!", "User"));
        messageList.add(new MessageModel("Hi there!", "Other"));
        messageList.add(new MessageModel("How are you?", "User"));
        messageList.add(new MessageModel("I'm good, thanks!", "Other"));
        adapter.notifyDataSetChanged();
    }
}