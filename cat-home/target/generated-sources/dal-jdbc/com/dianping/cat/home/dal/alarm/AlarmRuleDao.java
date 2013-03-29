package com.dianping.cat.home.dal.alarm;

import java.util.List;
import org.unidal.dal.jdbc.DalException;
import org.unidal.dal.jdbc.AbstractDao;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;

public class AlarmRuleDao extends AbstractDao {
   public AlarmRule createLocal() {
      AlarmRule proto = new AlarmRule();

      return proto;
   }

   public int deleteByPK(AlarmRule proto) throws DalException {
      return getQueryEngine().deleteSingle(
            AlarmRuleEntity.DELETE_BY_PK,
            proto);
   }
   
   public List<AlarmRule> findAllAlarmRule(Readset<AlarmRule> readset) throws DalException {
      AlarmRule proto = new AlarmRule();

      List<AlarmRule> result = getQueryEngine().queryMultiple(
            AlarmRuleEntity.FIND_ALL_ALARM_RULE, 
            proto,
            readset);
      
      return result;
   }
   
   public List<AlarmRule> findAllAlarmRuleByTemplateId(int templateId, Readset<AlarmRule> readset) throws DalException {
      AlarmRule proto = new AlarmRule();

      proto.setTemplateId(templateId);

      List<AlarmRule> result = getQueryEngine().queryMultiple(
            AlarmRuleEntity.FIND_ALL_ALARM_RULE_BY_TEMPLATE_ID, 
            proto,
            readset);
      
      return result;
   }
   
   public AlarmRule findByPK(int keyId, Readset<AlarmRule> readset) throws DalException {
      AlarmRule proto = new AlarmRule();

      proto.setKeyId(keyId);

      AlarmRule result = getQueryEngine().querySingle(
            AlarmRuleEntity.FIND_BY_PK, 
            proto,
            readset);
      
      return result;
   }
   
   @Override
   protected Class<?>[] getEntityClasses() {
      return new Class<?>[] { AlarmRuleEntity.class };
   }

   public int insert(AlarmRule proto) throws DalException {
      return getQueryEngine().insertSingle(
            AlarmRuleEntity.INSERT,
            proto);
   }
   
   public int updateByPK(AlarmRule proto, Updateset<AlarmRule> updateset) throws DalException {
      return getQueryEngine().updateSingle(
            AlarmRuleEntity.UPDATE_BY_PK,
            proto,
            updateset);
   }
   
}
