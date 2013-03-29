package com.dianping.cat.home.dal.alarm;

import java.util.List;
import org.unidal.dal.jdbc.DalException;
import org.unidal.dal.jdbc.AbstractDao;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;

public class AlarmTemplateDao extends AbstractDao {
   public AlarmTemplate createLocal() {
      AlarmTemplate proto = new AlarmTemplate();

      return proto;
   }

   public int deleteByPK(AlarmTemplate proto) throws DalException {
      return getQueryEngine().deleteSingle(
            AlarmTemplateEntity.DELETE_BY_PK,
            proto);
   }
   
   public List<AlarmTemplate> findAllAlarmTemplate(Readset<AlarmTemplate> readset) throws DalException {
      AlarmTemplate proto = new AlarmTemplate();

      List<AlarmTemplate> result = getQueryEngine().queryMultiple(
            AlarmTemplateEntity.FIND_ALL_ALARM_TEMPLATE, 
            proto,
            readset);
      
      return result;
   }
   
   public AlarmTemplate findByPK(int keyId, Readset<AlarmTemplate> readset) throws DalException {
      AlarmTemplate proto = new AlarmTemplate();

      proto.setKeyId(keyId);

      AlarmTemplate result = getQueryEngine().querySingle(
            AlarmTemplateEntity.FIND_BY_PK, 
            proto,
            readset);
      
      return result;
   }
   
   public AlarmTemplate findAlarmTemplateByName(String name, Readset<AlarmTemplate> readset) throws DalException {
      AlarmTemplate proto = new AlarmTemplate();

      proto.setName(name);

      AlarmTemplate result = getQueryEngine().querySingle(
            AlarmTemplateEntity.FIND_ALARM_TEMPLATE_BY_NAME, 
            proto,
            readset);
      
      return result;
   }
   
   @Override
   protected Class<?>[] getEntityClasses() {
      return new Class<?>[] { AlarmTemplateEntity.class };
   }

   public int insert(AlarmTemplate proto) throws DalException {
      return getQueryEngine().insertSingle(
            AlarmTemplateEntity.INSERT,
            proto);
   }
   
   public int updateByPK(AlarmTemplate proto, Updateset<AlarmTemplate> updateset) throws DalException {
      return getQueryEngine().updateSingle(
            AlarmTemplateEntity.UPDATE_BY_PK,
            proto,
            updateset);
   }
   
}
