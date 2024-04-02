package com.example.chatv2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatv2.databinding.ItemChatForMeBinding
import com.example.chatv2.model.Message

class MessageAdapter(
    private val messages: List<Message>
) : RecyclerView.Adapter<MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding: ItemChatForMeBinding =
            ItemChatForMeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MessageViewHolder(binding)
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(message = messages[position])
    }

}