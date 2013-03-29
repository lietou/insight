package com.dianping.cat.home.dal.alarm;

import static com.dianping.cat.home.dal.alarm.AlarmRuleSubscriptionEntity.ALARM_RULE_ID;
import static com.dianping.cat.home.dal.alarm.AlarmRuleSubscriptionEntity.CREATION_DATE;
import static com.dianping.cat.home.dal.alarm.AlarmRuleSubscriptionEntity.KEY_ALARM_RULE_ID;
import static com.dianping.cat.home.dal.alarm.AlarmRuleSubscriptionEntity.KEY_USER_ID;
import static com.dianping.cat.home.dal.alarm.AlarmRuleSubscriptionEntity.USER_ID;

import org.unidal.dal.jdbc.DataObject;

public class AlarmRuleSubscription extends DataObject {
   private int m_alarmRuleId;

   private int m_userId;

   private java.util.Date m_creationDate;

   private int m_keyAlarmRuleId;

   private int m_keyUserId;

   @Override
   public void afterLoad() {
      m_keyAlarmRuleId = m_alarmRuleId;
      m_keyUserId = m_userId;
      super.clearUsage();
      }

   public int getAlarmRuleId() {
      return m_alarmRuleId;
   }

   public java.util.Date getCreationDate() {
      return m_creationDate;
   }

   public int getKeyAlarmRuleId() {
      return m_keyAlarmRuleId;
   }

   public int getKeyUserId() {
      return m_keyUserId;
   }

   public int getUserId() {
      return m_userId;
   }

   public void setAlarmRuleId(int alarmRuleId) {
      setFieldUsed(ALARM_RULE_ID, true);
      m_alarmRuleId = alarmRuleId;
   }

   public void setCreationDate(java.util.Date creationDate) {
      setFieldUsed(CREATION_DATE, true);
      m_creationDate = creationDate;
   }

   public void setKeyAlarmRuleId(int keyAlarmRuleId) {
      setFieldUsed(KEY_ALARM_RULE_ID, true);
      m_keyAlarmRuleId = keyAlarmRuleId;
   }

   public void setKeyUserId(int keyUserId) {
      setFieldUsed(KEY_USER_ID, true);
      m_keyUserId = keyUserId;
   }

   public void setUserId(int userId) {
      setFieldUsed(USER_ID, true);
      m_userId = userId;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder(1024);

      sb.append("AlarmRuleSubscription[");
      sb.append("alarm-rule-id: ").append(m_alarmRuleId);
      sb.append(", creation-date: ").append(m_creationDate);
      sb.append(", key-alarm-rule-id: ").append(m_keyAlarmRuleId);
      sb.append(", key-user-id: ").append(m_keyUserId);
      sb.append(", user-id: ").append(m_userId);
      sb.append("]");
      return sb.toString();
   }

}
