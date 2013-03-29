package com.dianping.cat.consumer.sql.model.entity;

import static com.dianping.cat.consumer.sql.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.sql.model.Constants.ENTITY_METHOD;

import java.util.LinkedHashSet;
import java.util.Set;

import com.dianping.cat.consumer.sql.model.BaseEntity;
import com.dianping.cat.consumer.sql.model.IVisitor;

public class Method extends BaseEntity<Method> {
   private String m_id;

   private int m_totalCount;

   private int m_failCount;

   private double m_failPercent;

   private double m_avg;

   private double m_sum;

   private double m_tps;

   private Set<String> m_sqlNames = new LinkedHashSet<String>();

   private double m_totalPercent;

   public Method() {
   }

   public Method(String id) {
      m_id = id;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitMethod(this);
   }

   public Method addSql(String sql) {
      m_sqlNames.add(sql);
      return this;
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

   public Set<String> getSqlNames() {
      return m_sqlNames;
   }

   public double getSum() {
      return m_sum;
   }

   public int getTotalCount() {
      return m_totalCount;
   }

   public double getTotalPercent() {
      return m_totalPercent;
   }

   public double getTps() {
      return m_tps;
   }

   public Method incFailCount() {
      m_failCount++;
      return this;
   }

   public Method incTotalCount() {
      m_totalCount++;
      return this;
   }

   @Override
   public void mergeAttributes(Method other) {
      assertAttributeEquals(other, ENTITY_METHOD, ATTR_ID, m_id, other.getId());

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

      if (other.getTotalPercent() - 1e6 < 0) {
         m_totalPercent = other.getTotalPercent();
      }
   }

   public Method setAvg(double avg) {
      m_avg = avg;
      return this;
   }

   public Method setFailCount(int failCount) {
      m_failCount = failCount;
      return this;
   }

   public Method setFailPercent(double failPercent) {
      m_failPercent = failPercent;
      return this;
   }

   public Method setId(String id) {
      m_id = id;
      return this;
   }

   public Method setSum(double sum) {
      m_sum = sum;
      return this;
   }

   public Method setTotalCount(int totalCount) {
      m_totalCount = totalCount;
      return this;
   }

   public Method setTotalPercent(double totalPercent) {
      m_totalPercent = totalPercent;
      return this;
   }

   public Method setTps(double tps) {
      m_tps = tps;
      return this;
   }

}
