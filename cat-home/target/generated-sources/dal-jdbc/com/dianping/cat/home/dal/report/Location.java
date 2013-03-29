package com.dianping.cat.home.dal.report;

import static com.dianping.cat.home.dal.report.LocationEntity.CREATION_DATE;
import static com.dianping.cat.home.dal.report.LocationEntity.ID;
import static com.dianping.cat.home.dal.report.LocationEntity.KEY_ID;
import static com.dianping.cat.home.dal.report.LocationEntity.LAT;
import static com.dianping.cat.home.dal.report.LocationEntity.LNG;
import static com.dianping.cat.home.dal.report.LocationEntity.TOTAL;
import static com.dianping.cat.home.dal.report.LocationEntity.TRANSACTION_DATE;

import org.unidal.dal.jdbc.DataObject;

public class Location extends DataObject {
   private int m_id;

   private double m_lat;

   private double m_lng;

   private int m_total;

   private java.util.Date m_transactionDate;

   private java.util.Date m_creationDate;

   private int m_keyId;

   @Override
   public void afterLoad() {
      m_keyId = m_id;
      super.clearUsage();
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

   public double getLat() {
      return m_lat;
   }

   public double getLng() {
      return m_lng;
   }

   public int getTotal() {
      return m_total;
   }

   public java.util.Date getTransactionDate() {
      return m_transactionDate;
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

   public void setLat(double lat) {
      setFieldUsed(LAT, true);
      m_lat = lat;
   }

   public void setLng(double lng) {
      setFieldUsed(LNG, true);
      m_lng = lng;
   }

   public void setTotal(int total) {
      setFieldUsed(TOTAL, true);
      m_total = total;
   }

   public void setTransactionDate(java.util.Date transactionDate) {
      setFieldUsed(TRANSACTION_DATE, true);
      m_transactionDate = transactionDate;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder(1024);

      sb.append("Location[");
      sb.append("creation-date: ").append(m_creationDate);
      sb.append(", id: ").append(m_id);
      sb.append(", key-id: ").append(m_keyId);
      sb.append(", lat: ").append(m_lat);
      sb.append(", lng: ").append(m_lng);
      sb.append(", total: ").append(m_total);
      sb.append(", transaction-date: ").append(m_transactionDate);
      sb.append("]");
      return sb.toString();
   }

}
