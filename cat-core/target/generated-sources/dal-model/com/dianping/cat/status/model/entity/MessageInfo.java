package com.dianping.cat.status.model.entity;

import com.dianping.cat.status.model.BaseEntity;
import com.dianping.cat.status.model.IVisitor;

public class MessageInfo extends BaseEntity<MessageInfo> {
   private long m_produced;

   private long m_overflowed;

   private long m_bytes;

   public MessageInfo() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitMessage(this);
   }

   public long getBytes() {
      return m_bytes;
   }

   public long getOverflowed() {
      return m_overflowed;
   }

   public long getProduced() {
      return m_produced;
   }

   @Override
   public void mergeAttributes(MessageInfo other) {
      if (other.getProduced() != 0) {
         m_produced = other.getProduced();
      }

      if (other.getOverflowed() != 0) {
         m_overflowed = other.getOverflowed();
      }

      if (other.getBytes() != 0) {
         m_bytes = other.getBytes();
      }
   }

   public MessageInfo setBytes(long bytes) {
      m_bytes = bytes;
      return this;
   }

   public MessageInfo setOverflowed(long overflowed) {
      m_overflowed = overflowed;
      return this;
   }

   public MessageInfo setProduced(long produced) {
      m_produced = produced;
      return this;
   }

}
