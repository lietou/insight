package com.dianping.cat.job.sql.dal;

import java.util.List;
import org.unidal.dal.jdbc.DalException;
import org.unidal.dal.jdbc.AbstractDao;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;

public class LocationRecordDao extends AbstractDao {
   public LocationRecord createLocal() {
      LocationRecord proto = new LocationRecord();

      return proto;
   }

   public int deleteByPK(LocationRecord proto) throws DalException {
      return getQueryEngine().deleteSingle(
            LocationRecordEntity.DELETE_BY_PK,
            proto);
   }
   
   public List<LocationRecord> findAllByTransactionDateLatLngRange(java.util.Date fromDate, java.util.Date toDate, double fromLat, double toLat, double fromLng, double toLng, Readset<LocationRecord> readset) throws DalException {
      LocationRecord proto = new LocationRecord();

      proto.setFromDate(fromDate);
      proto.setToDate(toDate);
      proto.setFromLat(fromLat);
      proto.setToLat(toLat);
      proto.setFromLng(fromLng);
      proto.setToLng(toLng);

      List<LocationRecord> result = getQueryEngine().queryMultiple(
            LocationRecordEntity.FIND_ALL_BY_TRANSACTION_DATE_LAT_LNG_RANGE, 
            proto,
            readset);
      
      return result;
   }
   
   public LocationRecord findByPK(int keyId, Readset<LocationRecord> readset) throws DalException {
      LocationRecord proto = new LocationRecord();

      proto.setKeyId(keyId);

      LocationRecord result = getQueryEngine().querySingle(
            LocationRecordEntity.FIND_BY_PK, 
            proto,
            readset);
      
      return result;
   }
   
   @Override
   protected Class<?>[] getEntityClasses() {
      return new Class<?>[] { LocationRecordEntity.class };
   }

   public int[] insert(LocationRecord[] protos) throws DalException {
      return getQueryEngine().insertBatch(
            LocationRecordEntity.INSERT,
            protos);
   }
   
   public int insert(LocationRecord proto) throws DalException {
      return getQueryEngine().insertSingle(
            LocationRecordEntity.INSERT,
            proto);
   }
   
   public int updateByPK(LocationRecord proto, Updateset<LocationRecord> updateset) throws DalException {
      return getQueryEngine().updateSingle(
            LocationRecordEntity.UPDATE_BY_PK,
            proto,
            updateset);
   }
   
}
