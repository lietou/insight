package com.dianping.cat.home.dal.alarm;

import static com.dianping.cat.home.dal.alarm.AlarmRuleEntity.CONTENT;
import static com.dianping.cat.home.dal.alarm.AlarmRuleEntity.CREATION_DATE;
import static com.dianping.cat.home.dal.alarm.AlarmRuleEntity.DOMAIN;
import static com.dianping.cat.home.dal.alarm.AlarmRuleEntity.ID;
import static com.dianping.cat.home.dal.alarm.AlarmRuleEntity.KEY_ID;
import static com.dianping.cat.home.dal.alarm.AlarmRuleEntity.MODIFY_DATE;
import static com.dianping.cat.home.dal.alarm.AlarmRuleEntity.TEMPLATE_ID;

import org.unidal.dal.jdbc.DataObject;

public class AlarmRule extends DataObject {
   private int m_id;

   private int m_templateId;

   private String m_domain;

   private String m_content;

   private java.util.Date m_creationDate;

   private java.util.Date m_modifyDate;

   private int m_keyId;

   @Override
   public void afterLoad() {
      m_keyId = m_id;
      super.clearUsage();
      }

   public String getContent() {
      return m_content;
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

   public int getTemplateId() {
      return m_templateId;
   }

   public void setContent(String content) {
      setFieldUsed(CONTENT, true);
      m_content = content;
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

   public void setTemplateId(int templateId) {
      setFieldUsed(TEMPLATE_ID, true);
      m_templateId = templateId;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder(1024);

      sb.append("AlarmRule[");
      sb.append("content: ").append(m_content);
      sb.append(", creation-date: ").append(m_creationDate);
      sb.append(", domain: ").append(m_domain);
      sb.append(", id: ").append(m_id);
      sb.append(", key-id: ").append(m_keyId);
      sb.append(", modify-date: ").append(m_modifyDate);
      sb.append(", template-id: ").append(m_templateId);
      sb.append("]");
      return sb.toString();
   }

}
