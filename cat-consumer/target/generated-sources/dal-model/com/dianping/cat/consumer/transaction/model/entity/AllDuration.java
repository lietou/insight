package com.dianping.cat.consumer.transaction.model.entity;

import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_VALUE;
import static com.dianping.cat.consumer.transaction.model.Constants.ENTITY_ALL_DURATION;

import com.dianping.cat.consumer.transaction.model.BaseEntity;
import com.dianping.cat.consumer.transaction.model.IVisitor;

public class AllDuration extends BaseEntity<AllDuration> {
   private int m_value;

   private int m_count;

   public AllDuration() {
   }

   public AllDuration(int value) {
      m_value = value;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitAllDuration(this);
   }

   public int getCount() {
      return m_count;
   }

   public int getValue() {
      return m_value;
   }

   public AllDuration incCount() {
      m_count++;
      return this;
   }

   @Override
   public void mergeAttributes(AllDuration other) {
      assertAttributeEquals(other, ENTITY_ALL_DURATION, ATTR_VALUE, m_value, other.getValue());

      if (other.getCount() != 0) {
         m_count = other.getCount();
      }
   }

   public AllDuration setCount(int count) {
      m_count = count;
      return this;
   }

   public AllDuration setValue(int value) {
      m_value = value;
      return this;
   }

}
