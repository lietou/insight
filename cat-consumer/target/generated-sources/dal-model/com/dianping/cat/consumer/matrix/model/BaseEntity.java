package com.dianping.cat.consumer.matrix.model;

import java.util.Formattable;
import java.util.Formatter;

import com.dianping.cat.consumer.matrix.model.transform.DefaultXmlBuilder;
import com.dianping.cat.consumer.matrix.model.transform.DefaultJsonBuilder;

public abstract class BaseEntity<T> implements IEntity<T>, Formattable {

   public static final String JSON = "%2s";

   public static final String JSON_COMPACT = "%2.0s";

   public static final String XML = "%1s";
   
   public static final String XML_COMPACT = "%1.0s";
   
   protected void assertAttributeEquals(Object instance, String entityName, String name, Object expectedValue, Object actualValue) {
      if (expectedValue == null && actualValue != null || expectedValue != null && !expectedValue.equals(actualValue)) {
         throw new IllegalArgumentException(String.format("Mismatched entity(%s) found! Same %s attribute is expected! %s: %s.", entityName, name, entityName, instance));
      }
   }

   @Override
   public void formatTo(Formatter formatter, int flags, int width, int precision) {
      boolean useJson = (width == 2);
      boolean compact = (precision == 0);
      
      if (useJson) {
         DefaultJsonBuilder builder = new DefaultJsonBuilder(compact);

         formatter.format(builder.buildJson(this));
      } else {
         DefaultXmlBuilder builder = new DefaultXmlBuilder(compact);

         formatter.format(builder.buildXml(this));
      }
   }

   @Override
   public String toString() {
      return new DefaultXmlBuilder().buildXml(this);
   }
}
