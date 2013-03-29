package com.dianping.cat.status.model.entity;

import com.dianping.cat.status.model.BaseEntity;
import com.dianping.cat.status.model.IVisitor;

public class RuntimeInfo extends BaseEntity<RuntimeInfo> {
   private long m_startTime;

   private long m_upTime;

   private String m_javaVersion;

   private String m_userName;

   private String m_userDir;

   private String m_javaClasspath;

   public RuntimeInfo() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitRuntime(this);
   }

   public String getJavaClasspath() {
      return m_javaClasspath;
   }

   public String getJavaVersion() {
      return m_javaVersion;
   }

   public long getStartTime() {
      return m_startTime;
   }

   public long getUpTime() {
      return m_upTime;
   }

   public String getUserDir() {
      return m_userDir;
   }

   public String getUserName() {
      return m_userName;
   }

   @Override
   public void mergeAttributes(RuntimeInfo other) {
      if (other.getStartTime() != 0) {
         m_startTime = other.getStartTime();
      }

      if (other.getUpTime() != 0) {
         m_upTime = other.getUpTime();
      }

      if (other.getJavaVersion() != null) {
         m_javaVersion = other.getJavaVersion();
      }

      if (other.getUserName() != null) {
         m_userName = other.getUserName();
      }
   }

   public RuntimeInfo setJavaClasspath(String javaClasspath) {
      m_javaClasspath = javaClasspath;
      return this;
   }

   public RuntimeInfo setJavaVersion(String javaVersion) {
      m_javaVersion = javaVersion;
      return this;
   }

   public RuntimeInfo setStartTime(long startTime) {
      m_startTime = startTime;
      return this;
   }

   public RuntimeInfo setUpTime(long upTime) {
      m_upTime = upTime;
      return this;
   }

   public RuntimeInfo setUserDir(String userDir) {
      m_userDir = userDir;
      return this;
   }

   public RuntimeInfo setUserName(String userName) {
      m_userName = userName;
      return this;
   }

}
