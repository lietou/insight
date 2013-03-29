package com.dianping.cat.consumer.matrix.model.entity;

import static com.dianping.cat.consumer.matrix.model.Constants.ATTR_NAME;
import static com.dianping.cat.consumer.matrix.model.Constants.ENTITY_MATRIX;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dianping.cat.consumer.matrix.model.BaseEntity;
import com.dianping.cat.consumer.matrix.model.IVisitor;

public class Matrix extends BaseEntity<Matrix> {
   private String m_type;

   private String m_name;

   private int m_count;

   private long m_totalTime;

   private String m_url;

   private Map<String, Ratio> m_ratios = new LinkedHashMap<String, Ratio>();

   public Matrix() {
   }

   public Matrix(String name) {
      m_name = name;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitMatrix(this);
   }

   public Matrix addRatio(Ratio ratio) {
      m_ratios.put(ratio.getType(), ratio);
      return this;
   }

   public Ratio findRatio(String type) {
      return m_ratios.get(type);
   }

   public Ratio findOrCreateRatio(String type) {
      Ratio ratio = m_ratios.get(type);

      if (ratio == null) {
         synchronized (m_ratios) {
            ratio = m_ratios.get(type);

            if (ratio == null) {
               ratio = new Ratio(type);
               m_ratios.put(type, ratio);
            }
         }
      }

      return ratio;
   }

   public int getCount() {
      return m_count;
   }

   public String getName() {
      return m_name;
   }

   public Map<String, Ratio> getRatios() {
      return m_ratios;
   }

   public long getTotalTime() {
      return m_totalTime;
   }

   public String getType() {
      return m_type;
   }

   public String getUrl() {
      return m_url;
   }

   public Matrix incCount() {
      m_count++;
      return this;
   }

   @Override
   public void mergeAttributes(Matrix other) {
      assertAttributeEquals(other, ENTITY_MATRIX, ATTR_NAME, m_name, other.getName());

      if (other.getType() != null) {
         m_type = other.getType();
      }

      if (other.getCount() != 0) {
         m_count = other.getCount();
      }

      if (other.getTotalTime() != 0) {
         m_totalTime = other.getTotalTime();
      }

      if (other.getUrl() != null) {
         m_url = other.getUrl();
      }
   }

   public boolean removeRatio(String type) {
      if (m_ratios.containsKey(type)) {
         m_ratios.remove(type);
         return true;
      }

      return false;
   }

   public Matrix setCount(int count) {
      m_count = count;
      return this;
   }

   public Matrix setName(String name) {
      m_name = name;
      return this;
   }

   public Matrix setTotalTime(long totalTime) {
      m_totalTime = totalTime;
      return this;
   }

   public Matrix setType(String type) {
      m_type = type;
      return this;
   }

   public Matrix setUrl(String url) {
      m_url = url;
      return this;
   }

}
