package com.example.chatv2.adapter

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.chatv2.databinding.ItemChatForMeBinding
import com.example.chatv2.model.Message

class MessageViewHolder(private val binding: ItemChatForMeBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(message: Message) {
        if (message.isSelf) {
            binding.layoutMsgSelf.isVisible = true
            binding.layoutMsgOther.isVisible = false

            if (message.msg.isEmpty()) {
                binding.img.isVisible = true
                binding.ivDownload.isVisible = true
                binding.ivRemove.isVisible = true

                binding.tvMsgSelf.isVisible = false
            } else {
                binding.img.isVisible = false
                binding.ivDownload.isVisible = false
                binding.ivRemove.isVisible = false

                binding.tvMsgSelf.isVisible = true
                binding.tvMsgSelf.text = message.msg
            }
        } else {
            binding.layoutMsgOther.isVisible = true
            binding.layoutMsgSelf.isVisible = false

            if (message.msg.isEmpty()) {
                binding.imgYou.isVisible = true
                binding.ivDownloadYou.isVisible = true
                binding.ivRemoveYou.isVisible = true

                binding.tvMsgOther.isVisible = false
            } else {
                binding.imgYou.isVisible = false
                binding.ivDownloadYou.isVisible = false
                binding.ivRemoveYou.isVisible = false

                binding.tvMsgOther.isVisible = true
                binding.tvMsgOther.text = message.msg
            }
        }
    }
}