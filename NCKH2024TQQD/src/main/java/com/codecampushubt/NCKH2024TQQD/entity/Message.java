package com.codecampushubt.NCKH2024TQQD.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MessageID")
    private Long messageId;

//    @ManyToOne
//    @JoinColumn(name = "ConversationID")
//    private Conversation conversation;
//
//    @ManyToOne
//    @JoinColumn(name = "SenderID")
//    private User sender;

    @Column(name = "Type", length = 20)
    private String type = "text";

    @Column(name = "Content", columnDefinition = "NVARCHAR(MAX)")
    private String content;

    @Column(name = "MediaUrl", length = 255)
    private String mediaUrl;

    @Column(name = "MediaType", length = 20)
    private String mediaType;

    @ManyToOne
    @JoinColumn(name = "ReplyToMessageID")
    private Message replyToMessage;

    @Column(name = "IsEdited")
    private boolean isEdited = false;

    @Column(name = "IsDeleted")
    private boolean isDeleted = false;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "UpdatedAt")
    private LocalDateTime updatedAt;

    @Column(name = "DeletedAt")
    private LocalDateTime deletedAt;

//    public Message() {
//    }
//
//    public Message(Conversation conversation, User sender, String content) {
//        this.conversation = conversation;
//        this.sender = sender;
//        this.content = content;
//    }
//
//    // equals, hashCode, and toString methods
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Message message = (Message) o;
//        return messageId.equals(message.messageId);
//    }
//
//    @Override
//    public int hashCode() {
//        return java.util.Objects.hash(messageId);
//    }
//
//    @Override
//    public String toString() {
//        return "Message{" +
//                "messageId=" + messageId +
//                ", conversation=" + conversation +
//                ", sender=" + sender +
//                ", type='" + type + '\'' +
//                ", content='" + content + '\'' +
//                ", mediaUrl='" + mediaUrl + '\'' +
//                ", mediaType='" + mediaType + '\'' +
//                ", replyToMessage=" + replyToMessage +
//                ", isEdited=" + isEdited +
//                ", isDeleted=" + isDeleted +
//                ", createdAt=" + createdAt +
//                ", updatedAt=" + updatedAt +
//                ", deletedAt=" + deletedAt +
//                '}';
//    }
}
