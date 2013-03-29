package com.dianping.cat.consumer.matrix.model.entity;

import static com.dianping.cat.consumer.matrix.model.Constants.ATTR_TYPE;
import static com.dianping.cat.consumer.matrix.model.Constants.ENTITY_RATIO;

import com.dianping.cat.consumer.matrix.model.BaseEntity;
import com.dianping.cat.consumer.matrix.model.IVisitor;

public class Ratio extends BaseEntity<Ratio> {
   private String m_type;

   private int m_min;

   private int m_max;

   private int m_totalCount;

   private long m_totalTime;

   public Ratio() {
   }

   public Ratio(String type) {
      m_type = type;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitRatio(this);
   }

   public int getMax() {
      return m_max;
   }

   public int getMin() {
      return m_min;
   }

   public int getTotalCount() {
      return m_totalCount;
   }

   public long getTotalTime() {
      return m_totalTime;
   }

   public String getType() {
      return m_type;
   }

   public Ratio incTotalCount() {
      m_totalCount++;
      return this;
   }

   @Override
   public void mergeAttributes(Ratio other) {
      assertAttributeEquals(other, ENTITY_RATIO, ATTR_TYPE, m_type, other.getType());

      if (other.getMin() != 0) {
         m_min = other.getMin();
      }

      if (other.getMax() != 0) {
         m_max = other.getMax();
      }

      if (other.getTotalCount() != 0) {
         m_totalCount = other.getTotalCount();
      }

      if (other.getTotalTime() != 0) {
         m_totalTime = other.getTotalTime();
      }
   }

   public Ratio setMax(int max) {
      m_max = max;
      return this;
   }

   public Ratio setMin(int min) {
      m_min = min;
      return this;
   }

   public Ratio setTotalCount(int totalCount) {
      m_totalCount = totalCount;
      return this;
   }

   public Ratio setTotalTime(long totalTime) {
      m_totalTime = totalTime;
      return this;
   }

   public Ratio setType(String type) {
      m_type = type;
      return this;
   }

}
