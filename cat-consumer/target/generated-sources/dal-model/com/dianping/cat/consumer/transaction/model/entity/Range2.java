package com.dianping.cat.consumer.transaction.model.entity;

import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_VALUE;
import static com.dianping.cat.consumer.transaction.model.Constants.ENTITY_RANGE2;

import com.dianping.cat.consumer.transaction.model.BaseEntity;
import com.dianping.cat.consumer.transaction.model.IVisitor;

public class Range2 extends BaseEntity<Range2> {
   private int m_value;

   private int m_count;

   private double m_sum;

   private double m_avg;

   private int m_fails;

   public Range2() {
   }

   public Range2(int value) {
      m_value = value;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitRange2(this);
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

   public Range2 incCount() {
      m_count++;
      return this;
   }

   public Range2 incFails() {
      m_fails++;
      return this;
   }

   @Override
   public void mergeAttributes(Range2 other) {
      assertAttributeEquals(other, ENTITY_RANGE2, ATTR_VALUE, m_value, other.getValue());

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

   public Range2 setAvg(double avg) {
      m_avg = avg;
      return this;
   }

   public Range2 setCount(int count) {
      m_count = count;
      return this;
   }

   public Range2 setFails(int fails) {
      m_fails = fails;
      return this;
   }

   public Range2 setSum(double sum) {
      m_sum = sum;
      return this;
   }

   public Range2 setValue(int value) {
      m_value = value;
      return this;
   }

}
