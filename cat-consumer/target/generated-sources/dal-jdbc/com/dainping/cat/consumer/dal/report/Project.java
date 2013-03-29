package com.dainping.cat.consumer.dal.report;

import static com.dainping.cat.consumer.dal.report.ProjectEntity.CREATION_DATE;
import static com.dainping.cat.consumer.dal.report.ProjectEntity.DEPARTMENT;
import static com.dainping.cat.consumer.dal.report.ProjectEntity.DOMAIN;
import static com.dainping.cat.consumer.dal.report.ProjectEntity.EMAIL;
import static com.dainping.cat.consumer.dal.report.ProjectEntity.ID;
import static com.dainping.cat.consumer.dal.report.ProjectEntity.KEY_ID;
import static com.dainping.cat.consumer.dal.report.ProjectEntity.MODIFY_DATE;
import static com.dainping.cat.consumer.dal.report.ProjectEntity.OWNER;
import static com.dainping.cat.consumer.dal.report.ProjectEntity.PROJECT_LINE;

import org.unidal.dal.jdbc.DataObject;

public class Project extends DataObject {
   private int m_id;

   private String m_domain;

   private String m_projectLine;

   private String m_department;

   private String m_owner;

   private String m_email;

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

   public String getDepartment() {
      return m_department;
   }

   public String getDomain() {
      return m_domain;
   }

   public String getEmail() {
      return m_email;
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

   public String getOwner() {
      return m_owner;
   }

   public String getProjectLine() {
      return m_projectLine;
   }

   public void setCreationDate(java.util.Date creationDate) {
      setFieldUsed(CREATION_DATE, true);
      m_creationDate = creationDate;
   }

   public void setDepartment(String department) {
      setFieldUsed(DEPARTMENT, true);
      m_department = department;
   }

   public void setDomain(String domain) {
      setFieldUsed(DOMAIN, true);
      m_domain = domain;
   }

   public void setEmail(String email) {
      setFieldUsed(EMAIL, true);
      m_email = email;
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

   public void setOwner(String owner) {
      setFieldUsed(OWNER, true);
      m_owner = owner;
   }

   public void setProjectLine(String projectLine) {
      setFieldUsed(PROJECT_LINE, true);
      m_projectLine = projectLine;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder(1024);

      sb.append("Project[");
      sb.append("creation-date: ").append(m_creationDate);
      sb.append(", department: ").append(m_department);
      sb.append(", domain: ").append(m_domain);
      sb.append(", email: ").append(m_email);
      sb.append(", id: ").append(m_id);
      sb.append(", key-id: ").append(m_keyId);
      sb.append(", modify-date: ").append(m_modifyDate);
      sb.append(", owner: ").append(m_owner);
      sb.append(", project-line: ").append(m_projectLine);
      sb.append("]");
      return sb.toString();
   }

}
