package com.dianping.cat.consumer.cross.model.entity;

import static com.dianping.cat.consumer.cross.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.cross.model.Constants.ENTITY_NAME;

import com.dianping.cat.consumer.cross.model.BaseEntity;
import com.dianping.cat.consumer.cross.model.IVisitor;

public class Name extends BaseEntity<Name> {
   private String m_id;

   private long m_totalCount;

   private int m_failCount;

   private double m_failPercent;

   private double m_avg;

   private double m_sum;

   private double m_tps;

   public Name() {
   }

   public Name(String id) {
      m_id = id;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitName(this);
   }

   public double getAvg() {
      return m_avg;
   }

   public int getFailCount() {
      return m_failCount;
   }

   public double getFailPercent() {
      return m_failPercent;
   }

   public String getId() {
      return m_id;
   }

   public double getSum() {
      return m_sum;
   }

   public long getTotalCount() {
      return m_totalCount;
   }

   public double getTps() {
      return m_tps;
   }

   public Name incFailCount() {
      m_failCount++;
      return this;
   }

   public Name incTotalCount() {
      m_totalCount++;
      return this;
   }

   @Override
   public void mergeAttributes(Name other) {
      assertAttributeEquals(other, ENTITY_NAME, ATTR_ID, m_id, other.getId());

      if (other.getTotalCount() != 0) {
         m_totalCount = other.getTotalCount();
      }

      if (other.getFailCount() != 0) {
         m_failCount = other.getFailCount();
      }

      if (other.getFailPercent() - 1e6 < 0) {
         m_failPercent = other.getFailPercent();
      }

      if (other.getAvg() - 1e6 < 0) {
         m_avg = other.getAvg();
      }

      if (other.getSum() - 1e6 < 0) {
         m_sum = other.getSum();
      }

      if (other.getTps() - 1e6 < 0) {
         m_tps = other.getTps();
      }
   }

   public Name setAvg(double avg) {
      m_avg = avg;
      return this;
   }

   public Name setFailCount(int failCount) {
      m_failCount = failCount;
      return this;
   }

   public Name setFailPercent(double failPercent) {
      m_failPercent = failPercent;
      return this;
   }

   public Name setId(String id) {
      m_id = id;
      return this;
   }

   public Name setSum(double sum) {
      m_sum = sum;
      return this;
   }

   public Name setTotalCount(long totalCount) {
      m_totalCount = totalCount;
      return this;
   }

   public Name setTps(double tps) {
      m_tps = tps;
      return this;
   }

}
