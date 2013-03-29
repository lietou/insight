package com.dianping.cat.consumer.event.model.entity;

import static com.dianping.cat.consumer.event.model.Constants.ATTR_VALUE;
import static com.dianping.cat.consumer.event.model.Constants.ENTITY_RANGE;

import com.dianping.cat.consumer.event.model.BaseEntity;
import com.dianping.cat.consumer.event.model.IVisitor;

public class Range extends BaseEntity<Range> {
   private int m_value;

   private int m_count;

   private int m_fails;

   public Range() {
   }

   public Range(int value) {
      m_value = value;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitRange(this);
   }

   public int getCount() {
      return m_count;
   }

   public int getFails() {
      return m_fails;
   }

   public int getValue() {
      return m_value;
   }

   public Range incCount() {
      m_count++;
      return this;
   }

   public Range incFails() {
      m_fails++;
      return this;
   }

   @Override
   public void mergeAttributes(Range other) {
      assertAttributeEquals(other, ENTITY_RANGE, ATTR_VALUE, m_value, other.getValue());

      if (other.getCount() != 0) {
         m_count = other.getCount();
      }

      if (other.getFails() != 0) {
         m_fails = other.getFails();
      }
   }

   public Range setCount(int count) {
      m_count = count;
      return this;
   }

   public Range setFails(int fails) {
      m_fails = fails;
      return this;
   }

   public Range setValue(int value) {
      m_value = value;
      return this;
   }

}
