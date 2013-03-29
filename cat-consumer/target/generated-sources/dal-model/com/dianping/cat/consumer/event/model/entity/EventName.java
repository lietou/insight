package com.dianping.cat.consumer.event.model.entity;

import static com.dianping.cat.consumer.event.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.event.model.Constants.ENTITY_NAME;

import java.util.ArrayList;
import java.util.List;

import com.dianping.cat.consumer.event.model.BaseEntity;
import com.dianping.cat.consumer.event.model.IVisitor;

public class EventName extends BaseEntity<EventName> {
   private String m_id;

   private long m_totalCount;

   private long m_failCount;

   private double m_failPercent;

   private String m_successMessageUrl;

   private String m_failMessageUrl;

   private List<Range> m_ranges = new ArrayList<Range>();

   private double m_tps;

   private double m_totalPercent;

   public EventName() {
   }

   public EventName(String id) {
      m_id = id;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitName(this);
   }

   public EventName addRange(Range range) {
      m_ranges.add(range);
      return this;
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

   public List<Range> getRanges() {
      return m_ranges;
   }

   public String getSuccessMessageUrl() {
      return m_successMessageUrl;
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

   public EventName incFailCount() {
      m_failCount++;
      return this;
   }

   public EventName incTotalCount() {
      m_totalCount++;
      return this;
   }

   @Override
   public void mergeAttributes(EventName other) {
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

      if (other.getTps() - 1e6 < 0) {
         m_tps = other.getTps();
      }

      if (other.getTotalPercent() - 1e6 < 0) {
         m_totalPercent = other.getTotalPercent();
      }
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

   public EventName setFailCount(long failCount) {
      m_failCount = failCount;
      return this;
   }

   public EventName setFailMessageUrl(String failMessageUrl) {
      m_failMessageUrl = failMessageUrl;
      return this;
   }

   public EventName setFailPercent(double failPercent) {
      m_failPercent = failPercent;
      return this;
   }

   public EventName setId(String id) {
      m_id = id;
      return this;
   }

   public EventName setSuccessMessageUrl(String successMessageUrl) {
      m_successMessageUrl = successMessageUrl;
      return this;
   }

   public EventName setTotalCount(long totalCount) {
      m_totalCount = totalCount;
      return this;
   }

   public EventName setTotalPercent(double totalPercent) {
      m_totalPercent = totalPercent;
      return this;
   }

   public EventName setTps(double tps) {
      m_tps = tps;
      return this;
   }

}
