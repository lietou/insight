package com.dianping.cat.consumer.transaction.model.entity;

import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.transaction.model.Constants.ENTITY_NAME;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dianping.cat.consumer.transaction.model.BaseEntity;
import com.dianping.cat.consumer.transaction.model.IVisitor;

public class TransactionName extends BaseEntity<TransactionName> {
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

   private List<Range> m_ranges = new ArrayList<Range>();

   private List<Duration> m_durations = new ArrayList<Duration>();

   private double m_totalPercent;

   private double m_tps;

   private double m_line95Value;

   private double m_line95Sum;

   private int m_line95Count;

   private Map<Integer, AllDuration> m_allDurations = new LinkedHashMap<Integer, AllDuration>();

   public TransactionName() {
   }

   public TransactionName(String id) {
      m_id = id;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitName(this);
   }

   public TransactionName addAllDuration(AllDuration allDuration) {
      m_allDurations.put(allDuration.getValue(), allDuration);
      return this;
   }

   public TransactionName addDuration(Duration duration) {
      m_durations.add(duration);
      return this;
   }

   public TransactionName addRange(Range range) {
      m_ranges.add(range);
      return this;
   }

   public AllDuration findAllDuration(int value) {
      return m_allDurations.get(value);
   }

   public Duration findDuration(int value) {
      for (Duration duration : m_durations) {
         if (duration.getValue() != value) {
            continue;
         }

         return duration;
      }

      return null;
   }

   public Range findRange(int value) {
      for (Range range : m_ranges) {
         if (range.getValue() != value) {
            continue;
         }

         return range;
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

   public Duration findOrCreateDuration(int value) {
      synchronized (m_durations) {
         for (Duration duration : m_durations) {
            if (duration.getValue() != value) {
               continue;
            }

            return duration;
         }

         Duration duration = new Duration(value);

         m_durations.add(duration);
         return duration;
      }
   }

   public Range findOrCreateRange(int value) {
      synchronized (m_ranges) {
         for (Range range : m_ranges) {
            if (range.getValue() != value) {
               continue;
            }

            return range;
         }

         Range range = new Range(value);

         m_ranges.add(range);
         return range;
      }
   }

   public Map<Integer, AllDuration> getAllDurations() {
      return m_allDurations;
   }

   public double getAvg() {
      return m_avg;
   }

   public List<Duration> getDurations() {
      return m_durations;
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

   public List<Range> getRanges() {
      return m_ranges;
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

   public double getTotalPercent() {
      return m_totalPercent;
   }

   public double getTps() {
      return m_tps;
   }

   public TransactionName incFailCount() {
      m_failCount++;
      return this;
   }

   public TransactionName incTotalCount() {
      m_totalCount++;
      return this;
   }

   @Override
   public void mergeAttributes(TransactionName other) {
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

      if (other.getTotalPercent() - 1e6 < 0) {
         m_totalPercent = other.getTotalPercent();
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

   public boolean removeDuration(int value) {
      int len = m_durations.size();

      for (int i = 0; i < len; i++) {
         Duration duration = m_durations.get(i);

         if (duration.getValue() != value) {
            continue;
         }

         m_durations.remove(i);
         return true;
      }

      return false;
   }

   public boolean removeRange(int value) {
      int len = m_ranges.size();

      for (int i = 0; i < len; i++) {
         Range range = m_ranges.get(i);

         if (range.getValue() != value) {
            continue;
         }

         m_ranges.remove(i);
         return true;
      }

      return false;
   }

   public TransactionName setAvg(double avg) {
      m_avg = avg;
      return this;
   }

   public TransactionName setFailCount(long failCount) {
      m_failCount = failCount;
      return this;
   }

   public TransactionName setFailMessageUrl(String failMessageUrl) {
      m_failMessageUrl = failMessageUrl;
      return this;
   }

   public TransactionName setFailPercent(double failPercent) {
      m_failPercent = failPercent;
      return this;
   }

   public TransactionName setId(String id) {
      m_id = id;
      return this;
   }

   public TransactionName setLine95Count(int line95Count) {
      m_line95Count = line95Count;
      return this;
   }

   public TransactionName setLine95Sum(double line95Sum) {
      m_line95Sum = line95Sum;
      return this;
   }

   public TransactionName setLine95Value(double line95Value) {
      m_line95Value = line95Value;
      return this;
   }

   public TransactionName setMax(double max) {
      m_max = max;
      return this;
   }

   public TransactionName setMin(double min) {
      m_min = min;
      return this;
   }

   public TransactionName setStd(double std) {
      m_std = std;
      return this;
   }

   public TransactionName setSuccessMessageUrl(String successMessageUrl) {
      m_successMessageUrl = successMessageUrl;
      return this;
   }

   public TransactionName setSum(double sum) {
      m_sum = sum;
      return this;
   }

   public TransactionName setSum2(double sum2) {
      m_sum2 = sum2;
      return this;
   }

   public TransactionName setTotalCount(long totalCount) {
      m_totalCount = totalCount;
      return this;
   }

   public TransactionName setTotalPercent(double totalPercent) {
      m_totalPercent = totalPercent;
      return this;
   }

   public TransactionName setTps(double tps) {
      m_tps = tps;
      return this;
   }

}
