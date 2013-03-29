package com.dianping.cat.configuration.server.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dianping.cat.configuration.server.BaseEntity;
import com.dianping.cat.configuration.server.IVisitor;

public class StorageConfig extends BaseEntity<StorageConfig> {
   private String m_localBaseDir = "target/bucket";

   private Boolean m_hdfsDisabled;

   private Map<String, HdfsConfig> m_hdfses = new LinkedHashMap<String, HdfsConfig>();

   private Map<String, Property> m_properties = new LinkedHashMap<String, Property>();

   public StorageConfig() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitStorage(this);
   }

   public StorageConfig addHdfs(HdfsConfig hdfs) {
      m_hdfses.put(hdfs.getId(), hdfs);
      return this;
   }

   public StorageConfig addProperty(Property property) {
      m_properties.put(property.getName(), property);
      return this;
   }

   public HdfsConfig findHdfs(String id) {
      return m_hdfses.get(id);
   }

   public Property findProperty(String name) {
      return m_properties.get(name);
   }

   public Boolean getHdfsDisabled() {
      return m_hdfsDisabled;
   }

   public Map<String, HdfsConfig> getHdfses() {
      return m_hdfses;
   }

   public String getLocalBaseDir() {
      return m_localBaseDir;
   }

   public Map<String, Property> getProperties() {
      return m_properties;
   }

   public boolean isHdfsDisabled() {
      return m_hdfsDisabled != null && m_hdfsDisabled.booleanValue();
   }

   @Override
   public void mergeAttributes(StorageConfig other) {
      if (other.getLocalBaseDir() != null) {
         m_localBaseDir = other.getLocalBaseDir();
      }

      if (other.getHdfsDisabled() != null) {
         m_hdfsDisabled = other.getHdfsDisabled();
      }
   }

   public boolean removeHdfs(String id) {
      if (m_hdfses.containsKey(id)) {
         m_hdfses.remove(id);
         return true;
      }

      return false;
   }

   public boolean removeProperty(String name) {
      if (m_properties.containsKey(name)) {
         m_properties.remove(name);
         return true;
      }

      return false;
   }

   public StorageConfig setHdfsDisabled(Boolean hdfsDisabled) {
      m_hdfsDisabled = hdfsDisabled;
      return this;
   }

   public StorageConfig setLocalBaseDir(String localBaseDir) {
      m_localBaseDir = localBaseDir;
      return this;
   }

}
