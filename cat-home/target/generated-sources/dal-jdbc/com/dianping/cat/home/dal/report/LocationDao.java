package com.dianping.cat.home.dal.report;

import org.unidal.dal.jdbc.DalException;
import org.unidal.dal.jdbc.AbstractDao;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;

public class LocationDao extends AbstractDao {
   public Location createLocal() {
      Location proto = new Location();

      return proto;
   }

   public int deleteByPK(Location proto) throws DalException {
      return getQueryEngine().deleteSingle(
            LocationEntity.DELETE_BY_PK,
            proto);
   }
   
   public Location findByPK(int keyId, Readset<Location> readset) throws DalException {
      Location proto = new Location();

      proto.setKeyId(keyId);

      Location result = getQueryEngine().querySingle(
            LocationEntity.FIND_BY_PK, 
            proto,
            readset);
      
      return result;
   }
   
   @Override
   protected Class<?>[] getEntityClasses() {
      return new Class<?>[] { LocationEntity.class };
   }

   public int insert(Location proto) throws DalException {
      return getQueryEngine().insertSingle(
            LocationEntity.INSERT,
            proto);
   }
   
   public int updateByPK(Location proto, Updateset<Location> updateset) throws DalException {
      return getQueryEngine().updateSingle(
            LocationEntity.UPDATE_BY_PK,
            proto,
            updateset);
   }
   
}
