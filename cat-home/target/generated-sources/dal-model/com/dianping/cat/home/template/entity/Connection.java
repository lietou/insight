package com.dianping.cat.home.template.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dianping.cat.home.template.BaseEntity;
import com.dianping.cat.home.template.IVisitor;

public class Connection extends BaseEntity<Connection> {
   private String m_baseUrl;

   private Map<String, Param> m_params = new LinkedHashMap<String, Param>();

   public Connection() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitConnection(this);
   }

   public Connection addParam(Param param) {
      m_params.put(param.getType(), param);
      return this;
   }

   public Param findParam(String type) {
      return m_params.get(type);
   }

   public Param findOrCreateParam(String type) {
      Param param = m_params.get(type);

      if (param == null) {
         synchronized (m_params) {
            param = m_params.get(type);

            if (param == null) {
               param = new Param(type);
               m_params.put(type, param);
            }
         }
      }

      return param;
   }

   public String getBaseUrl() {
      return m_baseUrl;
   }

   public Map<String, Param> getParams() {
      return m_params;
   }

   @Override
   public void mergeAttributes(Connection other) {
      if (other.getBaseUrl() != null) {
         m_baseUrl = other.getBaseUrl();
      }
   }

   public boolean removeParam(String type) {
      if (m_params.containsKey(type)) {
         m_params.remove(type);
         return true;
      }

      return false;
   }

   public Connection setBaseUrl(String baseUrl) {
      m_baseUrl = baseUrl;
      return this;
   }

}
