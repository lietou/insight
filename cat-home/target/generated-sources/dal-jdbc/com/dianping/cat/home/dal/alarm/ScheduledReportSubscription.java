package com.dianping.cat.home.dal.alarm;

import static com.dianping.cat.home.dal.alarm.ScheduledReportSubscriptionEntity.CREATION_DATE;
import static com.dianping.cat.home.dal.alarm.ScheduledReportSubscriptionEntity.KEY_SCHEDULED_REPORT_ID;
import static com.dianping.cat.home.dal.alarm.ScheduledReportSubscriptionEntity.KEY_USER_ID;
import static com.dianping.cat.home.dal.alarm.ScheduledReportSubscriptionEntity.SCHEDULED_REPORT_ID;
import static com.dianping.cat.home.dal.alarm.ScheduledReportSubscriptionEntity.USER_ID;

import org.unidal.dal.jdbc.DataObject;

public class ScheduledReportSubscription extends DataObject {
   private int m_scheduledReportId;

   private int m_userId;

   private java.util.Date m_creationDate;

   private int m_keyScheduledReportId;

   private int m_keyUserId;

   @Override
   public void afterLoad() {
      m_keyScheduledReportId = m_scheduledReportId;
      m_keyUserId = m_userId;
      super.clearUsage();
      }

   public java.util.Date getCreationDate() {
      return m_creationDate;
   }

   public int getKeyScheduledReportId() {
      return m_keyScheduledReportId;
   }

   public int getKeyUserId() {
      return m_keyUserId;
   }

   public int getScheduledReportId() {
      return m_scheduledReportId;
   }

   public int getUserId() {
      return m_userId;
   }

   public void setCreationDate(java.util.Date creationDate) {
      setFieldUsed(CREATION_DATE, true);
      m_creationDate = creationDate;
   }

   public void setKeyScheduledReportId(int keyScheduledReportId) {
      setFieldUsed(KEY_SCHEDULED_REPORT_ID, true);
      m_keyScheduledReportId = keyScheduledReportId;
   }

   public void setKeyUserId(int keyUserId) {
      setFieldUsed(KEY_USER_ID, true);
      m_keyUserId = keyUserId;
   }

   public void setScheduledReportId(int scheduledReportId) {
      setFieldUsed(SCHEDULED_REPORT_ID, true);
      m_scheduledReportId = scheduledReportId;
   }

   public void setUserId(int userId) {
      setFieldUsed(USER_ID, true);
      m_userId = userId;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder(1024);

      sb.append("ScheduledReportSubscription[");
      sb.append("creation-date: ").append(m_creationDate);
      sb.append(", key-scheduled-report-id: ").append(m_keyScheduledReportId);
      sb.append(", key-user-id: ").append(m_keyUserId);
      sb.append(", scheduled-report-id: ").append(m_scheduledReportId);
      sb.append(", user-id: ").append(m_userId);
      sb.append("]");
      return sb.toString();
   }

}
