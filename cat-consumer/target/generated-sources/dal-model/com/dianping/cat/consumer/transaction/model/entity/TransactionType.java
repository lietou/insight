package com.dianping.cat.consumer.transaction.model.entity;

import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.transaction.model.Constants.ENTITY_TYPE;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dianping.cat.consumer.transaction.model.BaseEntity;
import com.dianping.cat.consumer.transaction.model.IVisitor;

public class TransactionType extends BaseEntity<TransactionType> {
   private String m_id;

   private long m_totalCount;

   private long m_failCount;

   private double m_failPercent;

   private double m_min = 86400000d;

   private double m_max = -1d;

   private double m_avg;

   private double m_sum;

   private double m_sum2;

   private double m_std;

   private String m_successMessageUrl;

   private String m_failMessageUrl;

   private Map<String, TransactionName> m_names = new LinkedHashMap<String, TransactionName>();

   private double m_tps;

   private double m_line95Value;

   private double m_line95Sum;

   private int m_line95Count;

   private List<Range2> m_range2s = new ArrayList<Range2>();

   private Map<Integer, AllDuration> m_allDurations = new LinkedHashMap<Integer, AllDuration>();

   public TransactionType() {
   }

   public TransactionType(String id) {
      m_id = id;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitType(this);
   }

   public TransactionType addAllDuration(AllDuration allDuration) {
      m_allDurations.put(allDuration.getValue(), allDuration);
      return this;
   }

   public TransactionType addName(TransactionName name) {
      m_names.put(name.getId(), name);
      return this;
   }

   public TransactionType addRange2(Range2 range2) {
      m_range2s.add(range2);
      return this;
   }

   public AllDuration findAllDuration(int value) {
      return m_allDurations.get(value);
   }

   public TransactionName findName(String id) {
      return m_names.get(id);
   }

   public Range2 findRange2(int value) {
      for (Range2 range2 : m_range2s) {
         if (range2.getValue() != value) {
            continue;
         }

         return range2;
      }

      return null;
   }

   public AllDuration findOrCreateAllDuration(int value) {
      AllDuration allDuration = m_allDurations.get(value);

      if (allDuration == null) {
         synchronized (m_allDurations) {
            allDuration = m_allDurations.get(value);

            if (allDuration == null) {
               allDuration = new AllDuration(value);
               m_allDurations.put(value, allDuration);
            }
         }
      }

      return allDuration;
   }

   public TransactionName findOrCreateName(String id) {
      TransactionName name = m_names.get(id);

      if (name == null) {
         synchronized (m_names) {
            name = m_names.get(id);

            if (name == null) {
               name = new TransactionName(id);
               m_names.put(id, name);
            }
         }
      }

      return name;
   }

   public Range2 findOrCreateRange2(int value) {
      synchronized (m_range2s) {
         for (Range2 range2 : m_range2s) {
            if (range2.getValue() != value) {
               continue;
            }

            return range2;
         }

         Range2 range2 = new Range2(value);

         m_range2s.add(range2);
         return range2;
      }
   }

   public Map<Integer, AllDuration> getAllDurations() {
      return m_allDurations;
   }

   public double getAvg() {
      return m_avg;
   }

   public long getFailCount() {
      return m_failCount;
   }

   public String getFailMessageUrl() {
      return m_failMessageUrl;
   }

   public double getFailPercent() {
      return m_failPercent;
   }

   public String getId() {
      return m_id;
   }

   public int getLine95Count() {
      return m_line95Count;
   }

   public double getLine95Sum() {
      return m_line95Sum;
   }

   public double getLine95Value() {
      return m_line95Value;
   }

   public double getMax() {
      return m_max;
   }

   public double getMin() {
      return m_min;
   }

   public Map<String, TransactionName> getNames() {
      return m_names;
   }

   public List<Range2> getRange2s() {
      return m_range2s;
   }

   public double getStd() {
      return m_std;
   }

   public String getSuccessMessageUrl() {
      return m_successMessageUrl;
   }

   public double getSum() {
      return m_sum;
   }

   public double getSum2() {
      return m_sum2;
   }

   public long getTotalCount() {
      return m_totalCount;
   }

   public double getTps() {
      return m_tps;
   }

   public TransactionType incFailCount() {
      m_failCount++;
      return this;
   }

   public TransactionType incTotalCount() {
      m_totalCount++;
      return this;
   }

   @Override
   public void mergeAttributes(TransactionType other) {
      assertAttributeEquals(other, ENTITY_TYPE, ATTR_ID, m_id, other.getId());

      if (other.getTotalCount() != 0) {
         m_totalCount = other.getTotalCount();
      }

      if (other.getFailCount() != 0) {
         m_failCount = other.getFailCount();
      }

      if (other.getFailPercent() - 1e6 < 0) {
         m_failPercent = other.getFailPercent();
      }

      if (other.getMin() - 1e6 < 0) {
         m_min = other.getMin();
      }

      if (other.getMax() - 1e6 < 0) {
         m_max = other.getMax();
      }

      if (other.getAvg() - 1e6 < 0) {
         m_avg = other.getAvg();
      }

      if (other.getSum() - 1e6 < 0) {
         m_sum = other.getSum();
      }

      if (other.getSum2() - 1e6 < 0) {
         m_sum2 = other.getSum2();
      }

      if (other.getStd() - 1e6 < 0) {
         m_std = other.getStd();
      }

      if (other.getTps() - 1e6 < 0) {
         m_tps = other.getTps();
      }

      if (other.getLine95Value() - 1e6 < 0) {
         m_line95Value = other.getLine95Value();
      }

      if (other.getLine95Sum() - 1e6 < 0) {
         m_line95Sum = other.getLine95Sum();
      }

      if (other.getLine95Count() != 0) {
         m_line95Count = other.getLine95Count();
      }
   }

   public boolean removeAllDuration(int value) {
      if (m_allDurations.containsKey(value)) {
         m_allDurations.remove(value);
         return true;
      }

      return false;
   }

   public boolean removeName(String id) {
      if (m_names.containsKey(id)) {
         m_names.remove(id);
         return true;
      }

      return false;
   }

   public boolean removeRange2(int value) {
      int len = m_range2s.size();

      for (int i = 0; i < len; i++) {
         Range2 range2 = m_range2s.get(i);

         if (range2.getValue() != value) {
            continue;
         }

         m_range2s.remove(i);
         return true;
      }

      return false;
   }

   public TransactionType setAvg(double avg) {
      m_avg = avg;
      return this;
   }

   public TransactionType setFailCount(long failCount) {
      m_failCount = failCount;
      return this;
   }

   public TransactionType setFailMessageUrl(String failMessageUrl) {
      m_failMessageUrl = failMessageUrl;
      return this;
   }

   public TransactionType setFailPercent(double failPercent) {
      m_failPercent = failPercent;
      return this;
   }

   public TransactionType setId(String id) {
      m_id = id;
      return this;
   }

   public TransactionType setLine95Count(int line95Count) {
      m_line95Count = line95Count;
      return this;
   }

   public TransactionType setLine95Sum(double line95Sum) {
      m_line95Sum = line95Sum;
      return this;
   }

   public TransactionType setLine95Value(double line95Value) {
      m_line95Value = line95Value;
      return this;
   }

   public TransactionType setMax(double max) {
      m_max = max;
      return this;
   }

   public TransactionType setMin(double min) {
      m_min = min;
      return this;
   }

   public TransactionType setStd(double std) {
      m_std = std;
      return this;
   }

   public TransactionType setSuccessMessageUrl(String successMessageUrl) {
      m_successMessageUrl = successMessageUrl;
      return this;
   }

   public TransactionType setSum(double sum) {
      m_sum = sum;
      return this;
   }

   public TransactionType setSum2(double sum2) {
      m_sum2 = sum2;
      return this;
   }

   public TransactionType setTotalCount(long totalCount) {
      m_totalCount = totalCount;
      return this;
   }

   public TransactionType setTps(double tps) {
      m_tps = tps;
      return this;
   }

}
