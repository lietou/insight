package com.dianping.cat.consumer.sql.model.entity;

import static com.dianping.cat.consumer.sql.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.sql.model.Constants.ENTITY_TABLE;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dianping.cat.consumer.sql.model.BaseEntity;
import com.dianping.cat.consumer.sql.model.IVisitor;

public class Table extends BaseEntity<Table> {
   private String m_id;

   private int m_totalCount;

   private int m_failCount;

   private double m_failPercent;

   private double m_avg;

   private double m_sum;

   private double m_tps;

   private Map<String, Method> m_methods = new LinkedHashMap<String, Method>();

   private double m_totalPercent;

   public Table() {
   }

   public Table(String id) {
      m_id = id;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitTable(this);
   }

   public Table addMethod(Method method) {
      m_methods.put(method.getId(), method);
      return this;
   }

   public Method findMethod(String id) {
      return m_methods.get(id);
   }

   public Method findOrCreateMethod(String id) {
      Method method = m_methods.get(id);

      if (method == null) {
         synchronized (m_methods) {
            method = m_methods.get(id);

            if (method == null) {
               method = new Method(id);
               m_methods.put(id, method);
            }
         }
      }

      return method;
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

   public Map<String, Method> getMethods() {
      return m_methods;
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

   public Table incFailCount() {
      m_failCount++;
      return this;
   }

   public Table incTotalCount() {
      m_totalCount++;
      return this;
   }

   @Override
   public void mergeAttributes(Table other) {
      assertAttributeEquals(other, ENTITY_TABLE, ATTR_ID, m_id, other.getId());

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

   public boolean removeMethod(String id) {
      if (m_methods.containsKey(id)) {
         m_methods.remove(id);
         return true;
      }

      return false;
   }

   public Table setAvg(double avg) {
      m_avg = avg;
      return this;
   }

   public Table setFailCount(int failCount) {
      m_failCount = failCount;
      return this;
   }

   public Table setFailPercent(double failPercent) {
      m_failPercent = failPercent;
      return this;
   }

   public Table setId(String id) {
      m_id = id;
      return this;
   }

   public Table setSum(double sum) {
      m_sum = sum;
      return this;
   }

   public Table setTotalCount(int totalCount) {
      m_totalCount = totalCount;
      return this;
   }

   public Table setTotalPercent(double totalPercent) {
      m_totalPercent = totalPercent;
      return this;
   }

   public Table setTps(double tps) {
      m_tps = tps;
      return this;
   }

}
