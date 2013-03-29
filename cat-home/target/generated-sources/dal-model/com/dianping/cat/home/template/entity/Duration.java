package com.dianping.cat.home.template.entity;

import static com.dianping.cat.home.template.Constants.ATTR_ID;
import static com.dianping.cat.home.template.Constants.ENTITY_DURATION;

import com.dianping.cat.home.template.BaseEntity;
import com.dianping.cat.home.template.IVisitor;

public class Duration extends BaseEntity<Duration> {
   private String m_id;

   private Integer m_min;

   private Integer m_max;

   private Integer m_interval;

   private String m_alarm;

   private Integer m_alarmInterval;

   public Duration() {
   }

   public Duration(String id) {
      m_id = id;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitDuration(this);
   }

   public String getAlarm() {
      return m_alarm;
   }

   public Integer getAlarmInterval() {
      return m_alarmInterval;
   }

   public String getId() {
      return m_id;
   }

   public Integer getInterval() {
      return m_interval;
   }

   public Integer getMax() {
      return m_max;
   }

   public Integer getMin() {
      return m_min;
   }

   @Override
   public void mergeAttributes(Duration other) {
      assertAttributeEquals(other, ENTITY_DURATION, ATTR_ID, m_id, other.getId());

      if (other.getMin() != null) {
         m_min = other.getMin();
      }

      if (other.getMax() != null) {
         m_max = other.getMax();
      }

      if (other.getInterval() != null) {
         m_interval = other.getInterval();
      }

      if (other.getAlarm() != null) {
         m_alarm = other.getAlarm();
      }

      if (other.getAlarmInterval() != null) {
         m_alarmInterval = other.getAlarmInterval();
      }
   }

   public Duration setAlarm(String alarm) {
      m_alarm = alarm;
      return this;
   }

   public Duration setAlarmInterval(Integer alarmInterval) {
      m_alarmInterval = alarmInterval;
      return this;
   }

   public Duration setId(String id) {
      m_id = id;
      return this;
   }

   public Duration setInterval(Integer interval) {
      m_interval = interval;
      return this;
   }

   public Duration setMax(Integer max) {
      m_max = max;
      return this;
   }

   public Duration setMin(Integer min) {
      m_min = min;
      return this;
   }

}
