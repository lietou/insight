package com.dianping.cat.job.sql.dal;

import static com.dianping.cat.job.sql.dal.LocationRecordEntity.CREATION_DATE;
import static com.dianping.cat.job.sql.dal.LocationRecordEntity.FROM_DATE;
import static com.dianping.cat.job.sql.dal.LocationRecordEntity.FROM_LAT;
import static com.dianping.cat.job.sql.dal.LocationRecordEntity.FROM_LNG;
import static com.dianping.cat.job.sql.dal.LocationRecordEntity.ID;
import static com.dianping.cat.job.sql.dal.LocationRecordEntity.KEY_ID;
import static com.dianping.cat.job.sql.dal.LocationRecordEntity.LAT;
import static com.dianping.cat.job.sql.dal.LocationRecordEntity.LNG;
import static com.dianping.cat.job.sql.dal.LocationRecordEntity.TO_DATE;
import static com.dianping.cat.job.sql.dal.LocationRecordEntity.TO_LAT;
import static com.dianping.cat.job.sql.dal.LocationRecordEntity.TO_LNG;
import static com.dianping.cat.job.sql.dal.LocationRecordEntity.TOTAL;
import static com.dianping.cat.job.sql.dal.LocationRecordEntity.TRANSACTION_DATE;

import org.unidal.dal.jdbc.DataObject;

public class LocationRecord extends DataObject {
   private int m_id;

   private double m_lat;

   private double m_lng;

   private int m_total;

   private java.util.Date m_transactionDate;

   private java.util.Date m_creationDate;

   private int m_keyId;

   private java.util.Date m_fromDate;

   private java.util.Date m_toDate;

   private double m_fromLat;

   private double m_toLat;

   private double m_fromLng;

   private double m_toLng;

   @Override
   public void afterLoad() {
      m_keyId = m_id;
      super.clearUsage();
      }

   public java.util.Date getCreationDate() {
      return m_creationDate;
   }

   public java.util.Date getFromDate() {
      return m_fromDate;
   }

   public double getFromLat() {
      return m_fromLat;
   }

   public double getFromLng() {
      return m_fromLng;
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

   public java.util.Date getToDate() {
      return m_toDate;
   }

   public double getToLat() {
      return m_toLat;
   }

   public double getToLng() {
      return m_toLng;
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

   public void setFromDate(java.util.Date fromDate) {
      setFieldUsed(FROM_DATE, true);
      m_fromDate = fromDate;
   }

   public void setFromLat(double fromLat) {
      setFieldUsed(FROM_LAT, true);
      m_fromLat = fromLat;
   }

   public void setFromLng(double fromLng) {
      setFieldUsed(FROM_LNG, true);
      m_fromLng = fromLng;
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

   public void setToDate(java.util.Date toDate) {
      setFieldUsed(TO_DATE, true);
      m_toDate = toDate;
   }

   public void setToLat(double toLat) {
      setFieldUsed(TO_LAT, true);
      m_toLat = toLat;
   }

   public void setToLng(double toLng) {
      setFieldUsed(TO_LNG, true);
      m_toLng = toLng;
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

      sb.append("LocationRecord[");
      sb.append("creation-date: ").append(m_creationDate);
      sb.append(", from-date: ").append(m_fromDate);
      sb.append(", from-lat: ").append(m_fromLat);
      sb.append(", from-lng: ").append(m_fromLng);
      sb.append(", id: ").append(m_id);
      sb.append(", key-id: ").append(m_keyId);
      sb.append(", lat: ").append(m_lat);
      sb.append(", lng: ").append(m_lng);
      sb.append(", to-date: ").append(m_toDate);
      sb.append(", to-lat: ").append(m_toLat);
      sb.append(", to-lng: ").append(m_toLng);
      sb.append(", total: ").append(m_total);
      sb.append(", transaction-date: ").append(m_transactionDate);
      sb.append("]");
      return sb.toString();
   }

}
