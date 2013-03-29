package com.dianping.cat.home.template.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dianping.cat.home.template.BaseEntity;
import com.dianping.cat.home.template.IVisitor;

public class ThresholdTemplate extends BaseEntity<ThresholdTemplate> {
   private Connection m_connection;

   private Map<String, Duration> m_durations = new LinkedHashMap<String, Duration>();

   public ThresholdTemplate() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitThresholdTemplate(this);
   }

   public ThresholdTemplate addDuration(Duration duration) {
      m_durations.put(duration.getId(), duration);
      return this;
   }

   public Duration findDuration(String id) {
      return m_durations.get(id);
   }

   public Duration findOrCreateDuration(String id) {
      Duration duration = m_durations.get(id);

      if (duration == null) {
         synchronized (m_durations) {
            duration = m_durations.get(id);

            if (duration == null) {
               duration = new Duration(id);
               m_durations.put(id, duration);
            }
         }
      }

      return duration;
   }

   public Connection getConnection() {
      return m_connection;
   }

   public Map<String, Duration> getDurations() {
      return m_durations;
   }

   @Override
   public void mergeAttributes(ThresholdTemplate other) {
   }

   public boolean removeDuration(String id) {
      if (m_durations.containsKey(id)) {
         m_durations.remove(id);
         return true;
      }

      return false;
   }

   public ThresholdTemplate setConnection(Connection connection) {
      m_connection = connection;
      return this;
   }

}
