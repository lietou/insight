package com.dianping.cat.home.dal.alarm;

import static com.dianping.cat.home.dal.alarm.ScheduledReportEntity.CREATION_DATE;
import static com.dianping.cat.home.dal.alarm.ScheduledReportEntity.DOMAIN;
import static com.dianping.cat.home.dal.alarm.ScheduledReportEntity.ID;
import static com.dianping.cat.home.dal.alarm.ScheduledReportEntity.KEY_ID;
import static com.dianping.cat.home.dal.alarm.ScheduledReportEntity.MODIFY_DATE;
import static com.dianping.cat.home.dal.alarm.ScheduledReportEntity.NAMES;

import org.unidal.dal.jdbc.DataObject;

public class ScheduledReport extends DataObject {
   private int m_id;

   private String m_domain;

   private String m_names;

   private java.util.Date m_creationDate;

   private java.util.Date m_modifyDate;

   private int m_keyId;

   @Override
   public void afterLoad() {
      m_keyId = m_id;
      super.clearUsage();
      }

   public java.util.Date getCreationDate() {
      return m_creationDate;
   }

   public String getDomain() {
      return m_domain;
   }

   public int getId() {
      return m_id;
   }

   public int getKeyId() {
      return m_keyId;
   }

   public java.util.Date getModifyDate() {
      return m_modifyDate;
   }

   public String getNames() {
      return m_names;
   }

   public void setCreationDate(java.util.Date creationDate) {
      setFieldUsed(CREATION_DATE, true);
      m_creationDate = creationDate;
   }

   public void setDomain(String domain) {
      setFieldUsed(DOMAIN, true);
      m_domain = domain;
   }

   public void setId(int id) {
      setFieldUsed(ID, true);
      m_id = id;
   }

   public void setKeyId(int keyId) {
      setFieldUsed(KEY_ID, true);
      m_keyId = keyId;
   }

   public void setModifyDate(java.util.Date modifyDate) {
      setFieldUsed(MODIFY_DATE, true);
      m_modifyDate = modifyDate;
   }

   public void setNames(String names) {
      setFieldUsed(NAMES, true);
      m_names = names;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder(1024);

      sb.append("ScheduledReport[");
      sb.append("creation-date: ").append(m_creationDate);
      sb.append(", domain: ").append(m_domain);
      sb.append(", id: ").append(m_id);
      sb.append(", key-id: ").append(m_keyId);
      sb.append(", modify-date: ").append(m_modifyDate);
      sb.append(", names: ").append(m_names);
      sb.append("]");
      return sb.toString();
   }

}
