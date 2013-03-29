package com.dainping.cat.consumer.dal.report;

import java.util.List;
import org.unidal.dal.jdbc.DalException;
import org.unidal.dal.jdbc.AbstractDao;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;

public class ReportDao extends AbstractDao {
   public Report createLocal() {
      Report proto = new Report();

      return proto;
   }

   public int deleteByPK(Report proto) throws DalException {
      return getQueryEngine().deleteSingle(
            ReportEntity.DELETE_BY_PK,
            proto);
   }
   
   public List<Report> findAllByDomainNameDuration(java.util.Date startDate, java.util.Date endDate, String domain, String name, Readset<Report> readset) throws DalException {
      Report proto = new Report();

      proto.setStartDate(startDate);
      proto.setEndDate(endDate);
      proto.setDomain(domain);
      proto.setName(name);

      List<Report> result = getQueryEngine().queryMultiple(
            ReportEntity.FIND_ALL_BY_DOMAIN_NAME_DURATION, 
            proto,
            readset);
      
      return result;
   }
   
   public List<Report> findAllByPeriodDomainName(java.util.Date period, String domain, String name, Readset<Report> readset) throws DalException {
      Report proto = new Report();

      proto.setPeriod(period);
      proto.setDomain(domain);
      proto.setName(name);

      List<Report> result = getQueryEngine().queryMultiple(
            ReportEntity.FIND_ALL_BY_PERIOD_DOMAIN_NAME, 
            proto,
            readset);
      
      return result;
   }
   
   public List<Report> findAllByPeriodDomainTypeName(java.util.Date period, String domain, int type, String name, Readset<Report> readset) throws DalException {
      Report proto = new Report();

      proto.setPeriod(period);
      proto.setDomain(domain);
      proto.setType(type);
      proto.setName(name);

      List<Report> result = getQueryEngine().queryMultiple(
            ReportEntity.FIND_ALL_BY_PERIOD_DOMAIN_TYPE_NAME, 
            proto,
            readset);
      
      return result;
   }
   
   public List<Report> findAllByPeriodTypeName(java.util.Date period, int type, String name, Readset<Report> readset) throws DalException {
      Report proto = new Report();

      proto.setPeriod(period);
      proto.setType(type);
      proto.setName(name);

      List<Report> result = getQueryEngine().queryMultiple(
            ReportEntity.FIND_ALL_BY_PERIOD_TYPE_NAME, 
            proto,
            readset);
      
      return result;
   }
   
   public List<Report> findDatabaseAllByDomainNameDuration(java.util.Date startDate, java.util.Date endDate, String domain, String name, Readset<Report> readset) throws DalException {
      Report proto = new Report();

      proto.setStartDate(startDate);
      proto.setEndDate(endDate);
      proto.setDomain(domain);
      proto.setName(name);

      List<Report> result = getQueryEngine().queryMultiple(
            ReportEntity.FIND_DATABASE_ALL_BY_DOMAIN_NAME_DURATION, 
            proto,
            readset);
      
      return result;
   }
   
   public List<Report> findDatabaseAllByPeriodDomainName(java.util.Date period, String domain, String name, Readset<Report> readset) throws DalException {
      Report proto = new Report();

      proto.setPeriod(period);
      proto.setDomain(domain);
      proto.setName(name);

      List<Report> result = getQueryEngine().queryMultiple(
            ReportEntity.FIND_DATABASE_ALL_BY_PERIOD_DOMAIN_NAME, 
            proto,
            readset);
      
      return result;
   }
   
   public List<Report> findDatabaseAllByPeriodDomainTypeName(java.util.Date period, String domain, int type, String name, Readset<Report> readset) throws DalException {
      Report proto = new Report();

      proto.setPeriod(period);
      proto.setDomain(domain);
      proto.setType(type);
      proto.setName(name);

      List<Report> result = getQueryEngine().queryMultiple(
            ReportEntity.FIND_DATABASE_ALL_BY_PERIOD_DOMAIN_TYPE_NAME, 
            proto,
            readset);
      
      return result;
   }
   
   public List<Report> findDatabaseAllByPeriodTypeName(java.util.Date period, int type, String name, Readset<Report> readset) throws DalException {
      Report proto = new Report();

      proto.setPeriod(period);
      proto.setType(type);
      proto.setName(name);

      List<Report> result = getQueryEngine().queryMultiple(
            ReportEntity.FIND_DATABASE_ALL_BY_PERIOD_TYPE_NAME, 
            proto,
            readset);
      
      return result;
   }
   
   public Report findByPK(int keyId, Readset<Report> readset) throws DalException {
      Report proto = new Report();

      proto.setKeyId(keyId);

      Report result = getQueryEngine().querySingle(
            ReportEntity.FIND_BY_PK, 
            proto,
            readset);
      
      return result;
   }
   
   @Override
   protected Class<?>[] getEntityClasses() {
      return new Class<?>[] { ReportEntity.class };
   }

   public int insert(Report proto) throws DalException {
      return getQueryEngine().insertSingle(
            ReportEntity.INSERT,
            proto);
   }
   
   public int updateByPK(Report proto, Updateset<Report> updateset) throws DalException {
      return getQueryEngine().updateSingle(
            ReportEntity.UPDATE_BY_PK,
            proto,
            updateset);
   }
   
}
