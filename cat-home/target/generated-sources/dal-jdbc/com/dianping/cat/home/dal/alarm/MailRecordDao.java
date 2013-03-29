package com.dianping.cat.home.dal.alarm;

import java.util.List;
import org.unidal.dal.jdbc.DalException;
import org.unidal.dal.jdbc.AbstractDao;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;

public class MailRecordDao extends AbstractDao {
   public MailRecord createLocal() {
      MailRecord proto = new MailRecord();

      return proto;
   }

   public int deleteByPK(MailRecord proto) throws DalException {
      return getQueryEngine().deleteSingle(
            MailRecordEntity.DELETE_BY_PK,
            proto);
   }
   
   public List<MailRecord> findAlarmRecordByRuleId(int[] ruleIds, Readset<MailRecord> readset) throws DalException {
      MailRecord proto = new MailRecord();

      proto.setRuleIds(ruleIds);

      List<MailRecord> result = getQueryEngine().queryMultiple(
            MailRecordEntity.FIND_ALARM_RECORD_BY_RULE_ID, 
            proto,
            readset);
      
      return result;
   }
   
   public List<MailRecord> findReportRecordByRuleId(int[] ruleIds, Readset<MailRecord> readset) throws DalException {
      MailRecord proto = new MailRecord();

      proto.setRuleIds(ruleIds);

      List<MailRecord> result = getQueryEngine().queryMultiple(
            MailRecordEntity.FIND_REPORT_RECORD_BY_RULE_ID, 
            proto,
            readset);
      
      return result;
   }
   
   public MailRecord findByPK(int keyId, Readset<MailRecord> readset) throws DalException {
      MailRecord proto = new MailRecord();

      proto.setKeyId(keyId);

      MailRecord result = getQueryEngine().querySingle(
            MailRecordEntity.FIND_BY_PK, 
            proto,
            readset);
      
      return result;
   }
   
   public MailRecord findLastReportRecord(Readset<MailRecord> readset) throws DalException {
      MailRecord proto = new MailRecord();

      MailRecord result = getQueryEngine().querySingle(
            MailRecordEntity.FIND_LAST_REPORT_RECORD, 
            proto,
            readset);
      
      return result;
   }
   
   @Override
   protected Class<?>[] getEntityClasses() {
      return new Class<?>[] { MailRecordEntity.class };
   }

   public int insert(MailRecord proto) throws DalException {
      return getQueryEngine().insertSingle(
            MailRecordEntity.INSERT,
            proto);
   }
   
   public int updateByPK(MailRecord proto, Updateset<MailRecord> updateset) throws DalException {
      return getQueryEngine().updateSingle(
            MailRecordEntity.UPDATE_BY_PK,
            proto,
            updateset);
   }
   
}
