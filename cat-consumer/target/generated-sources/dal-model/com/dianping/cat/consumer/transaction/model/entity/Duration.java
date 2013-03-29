package com.dianping.cat.consumer.transaction.model.entity;

import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_VALUE;
import static com.dianping.cat.consumer.transaction.model.Constants.ENTITY_DURATION;

import com.dianping.cat.consumer.transaction.model.BaseEntity;
import com.dianping.cat.consumer.transaction.model.IVisitor;

public class Duration extends BaseEntity<Duration> {
   private int m_value;

   private int m_count;

   public Duration() {
   }

   public Duration(int value) {
      m_value = value;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitDuration(this);
   }

   public int getCount() {
      return m_count;
   }

   public int getValue() {
      return m_value;
   }

   public Duration incCount() {
      m_count++;
      return this;
   }

   @Override
   public void mergeAttributes(Duration other) {
      assertAttributeEquals(other, ENTITY_DURATION, ATTR_VALUE, m_value, other.getValue());

      if (other.getCount() != 0) {
         m_count = other.getCount();
      }
   }

   public Duration setCount(int count) {
      m_count = count;
      return this;
   }

   public Duration setValue(int value) {
      m_value = value;
      return this;
   }

}
