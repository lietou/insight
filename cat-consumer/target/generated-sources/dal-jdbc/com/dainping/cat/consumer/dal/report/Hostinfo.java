package com.dainping.cat.consumer.dal.report;

import static com.dainping.cat.consumer.dal.report.HostinfoEntity.CREATION_DATE;
import static com.dainping.cat.consumer.dal.report.HostinfoEntity.DOMAIN;
import static com.dainping.cat.consumer.dal.report.HostinfoEntity.ID;
import static com.dainping.cat.consumer.dal.report.HostinfoEntity.IP;
import static com.dainping.cat.consumer.dal.report.HostinfoEntity.KEY_ID;
import static com.dainping.cat.consumer.dal.report.HostinfoEntity.LAST_MODIFIED_DATE;

import org.unidal.dal.jdbc.DataObject;

public class Hostinfo extends DataObject {
   private int m_id;

   private String m_ip;

   private String m_domain;

   private java.util.Date m_creationDate;

   private java.util.Date m_lastModifiedDate;

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

   public String getIp() {
      return m_ip;
   }

   public int getKeyId() {
      return m_keyId;
   }

   public java.util.Date getLastModifiedDate() {
      return m_lastModifiedDate;
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

   public void setIp(String ip) {
      setFieldUsed(IP, true);
      m_ip = ip;
   }

   public void setKeyId(int keyId) {
      setFieldUsed(KEY_ID, true);
      m_keyId = keyId;
   }

   public void setLastModifiedDate(java.util.Date lastModifiedDate) {
      setFieldUsed(LAST_MODIFIED_DATE, true);
      m_lastModifiedDate = lastModifiedDate;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder(1024);

      sb.append("Hostinfo[");
      sb.append("creation-date: ").append(m_creationDate);
      sb.append(", domain: ").append(m_domain);
      sb.append(", id: ").append(m_id);
      sb.append(", ip: ").append(m_ip);
      sb.append(", key-id: ").append(m_keyId);
      sb.append(", last-modified-date: ").append(m_lastModifiedDate);
      sb.append("]");
      return sb.toString();
   }

}
