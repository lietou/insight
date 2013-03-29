package com.dianping.cat.home.dal.report;

import java.util.List;
import org.unidal.dal.jdbc.DalException;
import org.unidal.dal.jdbc.AbstractDao;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;

public class GraphDao extends AbstractDao {
   public Graph createLocal() {
      Graph proto = new Graph();

      return proto;
   }

   public int deleteByPK(Graph proto) throws DalException {
      return getQueryEngine().deleteSingle(
            GraphEntity.DELETE_BY_PK,
            proto);
   }
   
   public int deleteByDomainNamePeriodIp(Graph proto) throws DalException {
      return getQueryEngine().deleteSingle(
            GraphEntity.DELETE_BY_DOMAIN_NAME_PERIOD_IP,
            proto);
   }
   
   public List<Graph> findByDomainNameIpDuration(java.util.Date startDate, java.util.Date endDate, String ip, String domain, String name, Readset<Graph> readset) throws DalException {
      Graph proto = new Graph();

      proto.setStartDate(startDate);
      proto.setEndDate(endDate);
      proto.setIp(ip);
      proto.setDomain(domain);
      proto.setName(name);

      List<Graph> result = getQueryEngine().queryMultiple(
            GraphEntity.FIND_BY_DOMAIN_NAME_IP_DURATION, 
            proto,
            readset);
      
      return result;
   }
   
   public List<Graph> findIpByDomainNameDuration(java.util.Date startDate, java.util.Date endDate, String domain, String name, Readset<Graph> readset) throws DalException {
      Graph proto = new Graph();

      proto.setStartDate(startDate);
      proto.setEndDate(endDate);
      proto.setDomain(domain);
      proto.setName(name);

      List<Graph> result = getQueryEngine().queryMultiple(
            GraphEntity.FIND_IP_BY_DOMAIN_NAME_DURATION, 
            proto,
            readset);
      
      return result;
   }
   
   public List<Graph> findDomainByNameDuration(java.util.Date startDate, java.util.Date endDate, String name, Readset<Graph> readset) throws DalException {
      Graph proto = new Graph();

      proto.setStartDate(startDate);
      proto.setEndDate(endDate);
      proto.setName(name);

      List<Graph> result = getQueryEngine().queryMultiple(
            GraphEntity.FIND_DOMAIN_BY_NAME_DURATION, 
            proto,
            readset);
      
      return result;
   }
   
   public Graph findByPK(int keyId, Readset<Graph> readset) throws DalException {
      Graph proto = new Graph();

      proto.setKeyId(keyId);

      Graph result = getQueryEngine().querySingle(
            GraphEntity.FIND_BY_PK, 
            proto,
            readset);
      
      return result;
   }
   
   public Graph findSingalByDomainNameIpDuration(java.util.Date startDate, String ip, String domain, String name, Readset<Graph> readset) throws DalException {
      Graph proto = new Graph();

      proto.setStartDate(startDate);
      proto.setIp(ip);
      proto.setDomain(domain);
      proto.setName(name);

      Graph result = getQueryEngine().querySingle(
            GraphEntity.FIND_SINGAL_BY_DOMAIN_NAME_IP_DURATION, 
            proto,
            readset);
      
      return result;
   }
   
   @Override
   protected Class<?>[] getEntityClasses() {
      return new Class<?>[] { GraphEntity.class };
   }

   public int insert(Graph proto) throws DalException {
      return getQueryEngine().insertSingle(
            GraphEntity.INSERT,
            proto);
   }
   
   public int updateByPK(Graph proto, Updateset<Graph> updateset) throws DalException {
      return getQueryEngine().updateSingle(
            GraphEntity.UPDATE_BY_PK,
            proto,
            updateset);
   }
   
}
