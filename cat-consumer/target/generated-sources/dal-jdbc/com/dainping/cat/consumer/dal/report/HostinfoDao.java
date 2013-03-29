package com.dainping.cat.consumer.dal.report;

import java.util.List;
import org.unidal.dal.jdbc.DalException;
import org.unidal.dal.jdbc.AbstractDao;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;

public class HostinfoDao extends AbstractDao {
   public Hostinfo createLocal() {
      Hostinfo proto = new Hostinfo();

      return proto;
   }

   public int deleteByPK(Hostinfo proto) throws DalException {
      return getQueryEngine().deleteSingle(
            HostinfoEntity.DELETE_BY_PK,
            proto);
   }
   
   public List<Hostinfo> findAllIp(Readset<Hostinfo> readset) throws DalException {
      Hostinfo proto = new Hostinfo();

      List<Hostinfo> result = getQueryEngine().queryMultiple(
            HostinfoEntity.FIND_ALL_IP, 
            proto,
            readset);
      
      return result;
   }
   
   public Hostinfo findByPK(int keyId, Readset<Hostinfo> readset) throws DalException {
      Hostinfo proto = new Hostinfo();

      proto.setKeyId(keyId);

      Hostinfo result = getQueryEngine().querySingle(
            HostinfoEntity.FIND_BY_PK, 
            proto,
            readset);
      
      return result;
   }
   
   public Hostinfo findByIp(String ip, Readset<Hostinfo> readset) throws DalException {
      Hostinfo proto = new Hostinfo();

      proto.setIp(ip);

      Hostinfo result = getQueryEngine().querySingle(
            HostinfoEntity.FIND_BY_IP, 
            proto,
            readset);
      
      return result;
   }
   
   @Override
   protected Class<?>[] getEntityClasses() {
      return new Class<?>[] { HostinfoEntity.class };
   }

   public int insert(Hostinfo proto) throws DalException {
      return getQueryEngine().insertSingle(
            HostinfoEntity.INSERT,
            proto);
   }
   
   public int updateByPK(Hostinfo proto, Updateset<Hostinfo> updateset) throws DalException {
      return getQueryEngine().updateSingle(
            HostinfoEntity.UPDATE_BY_PK,
            proto,
            updateset);
   }
   
}
