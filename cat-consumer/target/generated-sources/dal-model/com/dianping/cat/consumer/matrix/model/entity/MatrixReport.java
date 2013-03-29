package com.dianping.cat.consumer.matrix.model.entity;

import static com.dianping.cat.consumer.matrix.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.matrix.model.Constants.ENTITY_MATRIX_REPORT;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.dianping.cat.consumer.matrix.model.BaseEntity;
import com.dianping.cat.consumer.matrix.model.IVisitor;

public class MatrixReport extends BaseEntity<MatrixReport> {
   private String m_domain;

   private java.util.Date m_startTime;

   private java.util.Date m_endTime;

   private Set<String> m_domainNames = new LinkedHashSet<String>();

   private Map<String, Matrix> m_matrixs = new LinkedHashMap<String, Matrix>();

   public MatrixReport() {
   }

   public MatrixReport(String domain) {
      m_domain = domain;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitMatrixReport(this);
   }

   public MatrixReport addDomain(String domain) {
      m_domainNames.add(domain);
      return this;
   }

   public MatrixReport addMatrix(Matrix matrix) {
      m_matrixs.put(matrix.getName(), matrix);
      return this;
   }

   public Matrix findMatrix(String name) {
      return m_matrixs.get(name);
   }

   public Matrix findOrCreateMatrix(String name) {
      Matrix matrix = m_matrixs.get(name);

      if (matrix == null) {
         synchronized (m_matrixs) {
            matrix = m_matrixs.get(name);

            if (matrix == null) {
               matrix = new Matrix(name);
               m_matrixs.put(name, matrix);
            }
         }
      }

      return matrix;
   }

   public String getDomain() {
      return m_domain;
   }

   public Set<String> getDomainNames() {
      return m_domainNames;
   }

   public java.util.Date getEndTime() {
      return m_endTime;
   }

   public Map<String, Matrix> getMatrixs() {
      return m_matrixs;
   }

   public java.util.Date getStartTime() {
      return m_startTime;
   }

   @Override
   public void mergeAttributes(MatrixReport other) {
      assertAttributeEquals(other, ENTITY_MATRIX_REPORT, ATTR_DOMAIN, m_domain, other.getDomain());

      if (other.getStartTime() != null) {
         m_startTime = other.getStartTime();
      }

      if (other.getEndTime() != null) {
         m_endTime = other.getEndTime();
      }
   }

   public boolean removeMatrix(String name) {
      if (m_matrixs.containsKey(name)) {
         m_matrixs.remove(name);
         return true;
      }

      return false;
   }

   public MatrixReport setDomain(String domain) {
      m_domain = domain;
      return this;
   }

   public MatrixReport setEndTime(java.util.Date endTime) {
      m_endTime = endTime;
      return this;
   }

   public MatrixReport setStartTime(java.util.Date startTime) {
      m_startTime = startTime;
      return this;
   }

}
