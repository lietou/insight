package com.dianping.cat.home.dal.report;

import java.util.List;
import org.unidal.dal.jdbc.DalException;
import org.unidal.dal.jdbc.AbstractDao;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;

public class DailygraphDao extends AbstractDao {
   public Dailygraph createLocal() {
      Dailygraph proto = new Dailygraph();

      return proto;
   }

   public int deleteByPK(Dailygraph proto) throws DalException {
      return getQueryEngine().deleteSingle(
            DailygraphEntity.DELETE_BY_PK,
            proto);
   }
   
   public int deleteByDomainNamePeriodIp(Dailygraph proto) throws DalException {
      return getQueryEngine().deleteSingle(
            DailygraphEntity.DELETE_BY_DOMAIN_NAME_PERIOD_IP,
            proto);
   }
   
   public List<Dailygraph> findByDomainNameIpDuration(java.util.Date startDate, java.util.Date endDate, String ip, String domain, String name, Readset<Dailygraph> readset) throws DalException {
      Dailygraph proto = new Dailygraph();

      proto.setStartDate(startDate);
      proto.setEndDate(endDate);
      proto.setIp(ip);
      proto.setDomain(domain);
      proto.setName(name);

      List<Dailygraph> result = getQueryEngine().queryMultiple(
            DailygraphEntity.FIND_BY_DOMAIN_NAME_IP_DURATION, 
            proto,
            readset);
      
      return result;
   }
   
   public List<Dailygraph> findIpByDomainNameDuration(java.util.Date startDate, java.util.Date endDate, String domain, String name, Readset<Dailygraph> readset) throws DalException {
      Dailygraph proto = new Dailygraph();

      proto.setStartDate(startDate);
      proto.setEndDate(endDate);
      proto.setDomain(domain);
      proto.setName(name);

      List<Dailygraph> result = getQueryEngine().queryMultiple(
            DailygraphEntity.FIND_IP_BY_DOMAIN_NAME_DURATION, 
            proto,
            readset);
      
      return result;
   }
   
   public List<Dailygraph> findDomainByNameDuration(java.util.Date startDate, java.util.Date endDate, String name, Readset<Dailygraph> readset) throws DalException {
      Dailygraph proto = new Dailygraph();

      proto.setStartDate(startDate);
      proto.setEndDate(endDate);
      proto.setName(name);

      List<Dailygraph> result = getQueryEngine().queryMultiple(
            DailygraphEntity.FIND_DOMAIN_BY_NAME_DURATION, 
            proto,
            readset);
      
      return result;
   }
   
   public Dailygraph findByPK(int keyId, Readset<Dailygraph> readset) throws DalException {
      Dailygraph proto = new Dailygraph();

      proto.setKeyId(keyId);

      Dailygraph result = getQueryEngine().querySingle(
            DailygraphEntity.FIND_BY_PK, 
            proto,
            readset);
      
      return result;
   }
   
   public Dailygraph findSingalByDomainNameIpDuration(java.util.Date startDate, String ip, String domain, String name, Readset<Dailygraph> readset) throws DalException {
      Dailygraph proto = new Dailygraph();

      proto.setStartDate(startDate);
      proto.setIp(ip);
      proto.setDomain(domain);
      proto.setName(name);

      Dailygraph result = getQueryEngine().querySingle(
            DailygraphEntity.FIND_SINGAL_BY_DOMAIN_NAME_IP_DURATION, 
            proto,
            readset);
      
      return result;
   }
   
   @Override
   protected Class<?>[] getEntityClasses() {
      return new Class<?>[] { DailygraphEntity.class };
   }

   public int insert(Dailygraph proto) throws DalException {
      return getQueryEngine().insertSingle(
            DailygraphEntity.INSERT,
            proto);
   }
   
   public int updateByPK(Dailygraph proto, Updateset<Dailygraph> updateset) throws DalException {
      return getQueryEngine().updateSingle(
            DailygraphEntity.UPDATE_BY_PK,
            proto,
            updateset);
   }
   
}
