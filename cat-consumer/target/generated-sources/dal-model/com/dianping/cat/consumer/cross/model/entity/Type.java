package com.dianping.cat.consumer.cross.model.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dianping.cat.consumer.cross.model.BaseEntity;
import com.dianping.cat.consumer.cross.model.IVisitor;

public class Type extends BaseEntity<Type> {
   private String m_id;

   private long m_totalCount;

   private int m_failCount;

   private double m_failPercent;

   private double m_avg;

   private double m_sum;

   private double m_tps;

   private Map<String, Name> m_names = new LinkedHashMap<String, Name>();

   public Type() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitType(this);
   }

   public Type addName(Name name) {
      m_names.put(name.getId(), name);
      return this;
   }

   public Name findName(String id) {
      return m_names.get(id);
   }

   public Name findOrCreateName(String id) {
      Name name = m_names.get(id);

      if (name == null) {
         synchronized (m_names) {
            name = m_names.get(id);

            if (name == null) {
               name = new Name(id);
               m_names.put(id, name);
            }
         }
      }

      return name;
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

   public Map<String, Name> getNames() {
      return m_names;
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

   public Type incFailCount() {
      m_failCount++;
      return this;
   }

   public Type incTotalCount() {
      m_totalCount++;
      return this;
   }

   @Override
   public void mergeAttributes(Type other) {
      if (other.getId() != null) {
         m_id = other.getId();
      }

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

   public boolean removeName(String id) {
      if (m_names.containsKey(id)) {
         m_names.remove(id);
         return true;
      }

      return false;
   }

   public Type setAvg(double avg) {
      m_avg = avg;
      return this;
   }

   public Type setFailCount(int failCount) {
      m_failCount = failCount;
      return this;
   }

   public Type setFailPercent(double failPercent) {
      m_failPercent = failPercent;
      return this;
   }

   public Type setId(String id) {
      m_id = id;
      return this;
   }

   public Type setSum(double sum) {
      m_sum = sum;
      return this;
   }

   public Type setTotalCount(long totalCount) {
      m_totalCount = totalCount;
      return this;
   }

   public Type setTps(double tps) {
      m_tps = tps;
      return this;
   }

}
