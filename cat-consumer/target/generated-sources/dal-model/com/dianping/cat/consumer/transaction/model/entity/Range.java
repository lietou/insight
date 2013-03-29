package com.dianping.cat.consumer.transaction.model.entity;

import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_VALUE;
import static com.dianping.cat.consumer.transaction.model.Constants.ENTITY_RANGE;

import com.dianping.cat.consumer.transaction.model.BaseEntity;
import com.dianping.cat.consumer.transaction.model.IVisitor;

public class Range extends BaseEntity<Range> {
   private int m_value;

   private int m_count;

   private double m_sum;

   private double m_avg;

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

   public double getAvg() {
      return m_avg;
   }

   public int getCount() {
      return m_count;
   }

   public int getFails() {
      return m_fails;
   }

   public double getSum() {
      return m_sum;
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

      if (other.getSum() - 1e6 < 0) {
         m_sum = other.getSum();
      }

      if (other.getAvg() - 1e6 < 0) {
         m_avg = other.getAvg();
      }

      if (other.getFails() != 0) {
         m_fails = other.getFails();
      }
   }

   public Range setAvg(double avg) {
      m_avg = avg;
      return this;
   }

   public Range setCount(int count) {
      m_count = count;
      return this;
   }

   public Range setFails(int fails) {
      m_fails = fails;
      return this;
   }

   public Range setSum(double sum) {
      m_sum = sum;
      return this;
   }

   public Range setValue(int value) {
      m_value = value;
      return this;
   }

}
