package com.dianping.cat.home.dal.report;

import org.unidal.dal.jdbc.DalException;
import org.unidal.dal.jdbc.AbstractDao;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;

public class MonthreportDao extends AbstractDao {
   public Monthreport createLocal() {
      Monthreport proto = new Monthreport();

      return proto;
   }

   public int deleteByPK(Monthreport proto) throws DalException {
      return getQueryEngine().deleteSingle(
            MonthreportEntity.DELETE_BY_PK,
            proto);
   }
   
   public Monthreport findByPK(int keyId, Readset<Monthreport> readset) throws DalException {
      Monthreport proto = new Monthreport();

      proto.setKeyId(keyId);

      Monthreport result = getQueryEngine().querySingle(
            MonthreportEntity.FIND_BY_PK, 
            proto,
            readset);
      
      return result;
   }
   
   public Monthreport findReportByDomainNamePeriod(java.util.Date period, String domain, String name, Readset<Monthreport> readset) throws DalException {
      Monthreport proto = new Monthreport();

      proto.setPeriod(period);
      proto.setDomain(domain);
      proto.setName(name);

      Monthreport result = getQueryEngine().querySingle(
            MonthreportEntity.FIND_REPORT_BY_DOMAIN_NAME_PERIOD, 
            proto,
            readset);
      
      return result;
   }
   
   @Override
   protected Class<?>[] getEntityClasses() {
      return new Class<?>[] { MonthreportEntity.class };
   }

   public int insert(Monthreport proto) throws DalException {
      return getQueryEngine().insertSingle(
            MonthreportEntity.INSERT,
            proto);
   }
   
   public int updateByPK(Monthreport proto, Updateset<Monthreport> updateset) throws DalException {
      return getQueryEngine().updateSingle(
            MonthreportEntity.UPDATE_BY_PK,
            proto,
            updateset);
   }
   
}
