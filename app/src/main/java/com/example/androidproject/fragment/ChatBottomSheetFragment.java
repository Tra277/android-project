package com.example.androidproject.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.adapter.ChatAdapter;
import com.example.androidproject.model.Message;
import com.example.androidproject.network.GeminiService;
import com.example.androidproject.network.HfService;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class ChatBottomSheetFragment extends BottomSheetDialogFragment {

    private RecyclerView recyclerView;
    private EditText editTextMessage;
    private ImageButton buttonSend;
    private ProgressBar progressBar;
    private ChatAdapter adapter;
    private final List<Message> messages = new ArrayList<>();
    private String currentQuestionText;
    private String currentAnswersText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chat_bottom_sheet, container, false);
    }

    public static ChatBottomSheetFragment newInstance(String currentQuestion, String answersText) {
        ChatBottomSheetFragment f = new ChatBottomSheetFragment();
        Bundle b = new Bundle();
        b.putString("current_question", currentQuestion);
        b.putString("current_answers", answersText);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerViewMessages);
        editTextMessage = view.findViewById(R.id.editTextMessage);
        buttonSend = view.findViewById(R.id.buttonSend);
        progressBar = view.findViewById(R.id.progressBarTyping);

        adapter = new ChatAdapter(messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        if (getArguments() != null) {
            currentQuestionText = getArguments().getString("current_question");
            currentAnswersText = getArguments().getString("current_answers");
        }

        buttonSend.setOnClickListener(v -> sendMessage());
        editTextMessage.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                sendMessage();
                return true;
            }
            return false;
        });
    }

    private void sendMessage() {
        String userInput = editTextMessage.getText().toString().trim();
        if (TextUtils.isEmpty(userInput)) return;

        addMessage(userInput, true);
        editTextMessage.setText("");
        progressBar.setVisibility(View.VISIBLE);

        String promptInput = userInput;
        if (currentQuestionText != null && !currentQuestionText.isEmpty()) {
            promptInput = "Here is the current driving test question: " + currentQuestionText;
            if (currentAnswersText != null && !currentAnswersText.isEmpty()) {
                promptInput += "\nAnswer options: " + currentAnswersText;
            }
            promptInput += "\nUser query: " + userInput;
        }

        final String finalPrompt = promptInput;

        GeminiService.getInstance().ask(finalPrompt, new GeminiService.GeminiCallback() {
            @Override
            public void onSuccess(String reply) {
                progressBar.setVisibility(View.GONE);
                addMessage(reply, false);
            }

            @Override
            public void onError(String error) {
                progressBar.setVisibility(View.GONE);
                if(error.contains("503") || error.contains("429")) {
                    // try fallback
                    HfService.getInstance().ask(finalPrompt, new HfService.HfCallback() {
                        @Override public void onSuccess(String reply) {
                            addMessage(reply + " (HF)", false);
                        }
                        @Override public void onError(String err) {
                            addMessage("Error: " + error + "\nHF: " + err, false);
                        }
                    });
                } else {
                    addMessage("Error: " + error, false);
                }
            }
        });
    }

    private void addMessage(String text, boolean fromUser) {
        messages.add(new Message(text, fromUser));
        adapter.notifyItemInserted(messages.size() - 1);
        recyclerView.scrollToPosition(messages.size() - 1);
    }
} 