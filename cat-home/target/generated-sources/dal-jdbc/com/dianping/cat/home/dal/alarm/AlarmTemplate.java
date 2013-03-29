package com.dianping.cat.home.dal.alarm;

import static com.dianping.cat.home.dal.alarm.AlarmTemplateEntity.CONTENT;
import static com.dianping.cat.home.dal.alarm.AlarmTemplateEntity.CREATION_DATE;
import static com.dianping.cat.home.dal.alarm.AlarmTemplateEntity.ID;
import static com.dianping.cat.home.dal.alarm.AlarmTemplateEntity.KEY_ID;
import static com.dianping.cat.home.dal.alarm.AlarmTemplateEntity.MODIFY_DATE;
import static com.dianping.cat.home.dal.alarm.AlarmTemplateEntity.NAME;

import org.unidal.dal.jdbc.DataObject;

public class AlarmTemplate extends DataObject {
   private int m_id;

   private String m_name;

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

   public int getId() {
      return m_id;
   }

   public int getKeyId() {
      return m_keyId;
   }

   public java.util.Date getModifyDate() {
      return m_modifyDate;
   }

   public String getName() {
      return m_name;
   }

   public void setContent(String content) {
      setFieldUsed(CONTENT, true);
      m_content = content;
   }

   public void setCreationDate(java.util.Date creationDate) {
      setFieldUsed(CREATION_DATE, true);
      m_creationDate = creationDate;
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

   public void setName(String name) {
      setFieldUsed(NAME, true);
      m_name = name;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder(1024);

      sb.append("AlarmTemplate[");
      sb.append("content: ").append(m_content);
      sb.append(", creation-date: ").append(m_creationDate);
      sb.append(", id: ").append(m_id);
      sb.append(", key-id: ").append(m_keyId);
      sb.append(", modify-date: ").append(m_modifyDate);
      sb.append(", name: ").append(m_name);
      sb.append("]");
      return sb.toString();
   }

}
