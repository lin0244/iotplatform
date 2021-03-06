/*******************************************************************************
 * Copyright 2017 osswangxining@163.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
/**
 * Copyright © 2016-2017 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.iotp.infomgt.dao.model.sql;

import static org.iotp.infomgt.dao.model.ModelConstants.BOOLEAN_VALUE_COLUMN;
import static org.iotp.infomgt.dao.model.ModelConstants.DOUBLE_VALUE_COLUMN;
import static org.iotp.infomgt.dao.model.ModelConstants.ENTITY_ID_COLUMN;
import static org.iotp.infomgt.dao.model.ModelConstants.ENTITY_TYPE_COLUMN;
import static org.iotp.infomgt.dao.model.ModelConstants.KEY_COLUMN;
import static org.iotp.infomgt.dao.model.ModelConstants.LONG_VALUE_COLUMN;
import static org.iotp.infomgt.dao.model.ModelConstants.STRING_VALUE_COLUMN;
import static org.iotp.infomgt.dao.model.ModelConstants.TS_COLUMN;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.iotp.infomgt.dao.model.ToData;
import org.iotp.infomgt.data.common.ThingType;
import org.iotp.infomgt.data.kv.BasicTsKvEntry;
import org.iotp.infomgt.data.kv.BooleanDataEntry;
import org.iotp.infomgt.data.kv.DoubleDataEntry;
import org.iotp.infomgt.data.kv.KvEntry;
import org.iotp.infomgt.data.kv.LongDataEntry;
import org.iotp.infomgt.data.kv.StringDataEntry;
import org.iotp.infomgt.data.kv.TsKvEntry;

import lombok.Data;

@Data
@Entity
@Table(name = "ts_kv")
@IdClass(TsKvCompositeKey.class)
public final class TsKvEntity implements ToData<TsKvEntry> {

  public TsKvEntity() {
  }

  public TsKvEntity(Double avgLongValue, Double avgDoubleValue) {
    if (avgLongValue != null) {
      this.longValue = avgLongValue.longValue();
    }
    this.doubleValue = avgDoubleValue;
  }

  public TsKvEntity(Long sumLongValue, Double sumDoubleValue) {
    this.longValue = sumLongValue;
    this.doubleValue = sumDoubleValue;
  }

  public TsKvEntity(String strValue, Long longValue, Double doubleValue) {
    this.strValue = strValue;
    this.longValue = longValue;
    this.doubleValue = doubleValue;
  }

  public TsKvEntity(Long booleanValueCount, Long strValueCount, Long longValueCount, Long doubleValueCount) {
    if (booleanValueCount != 0) {
      this.longValue = booleanValueCount;
    } else if (strValueCount != 0) {
      this.longValue = strValueCount;
    } else if (longValueCount != 0) {
      this.longValue = longValueCount;
    } else if (doubleValueCount != 0) {
      this.longValue = doubleValueCount;
    }
  }

  @Id
  @Enumerated(EnumType.STRING)
  @Column(name = ENTITY_TYPE_COLUMN)
  private ThingType entityType;

  @Id
  @Column(name = ENTITY_ID_COLUMN)
  private String entityId;

  @Id
  @Column(name = KEY_COLUMN)
  private String key;

  @Id
  @Column(name = TS_COLUMN)
  private long ts;

  @Column(name = BOOLEAN_VALUE_COLUMN)
  private Boolean booleanValue;

  @Column(name = STRING_VALUE_COLUMN)
  private String strValue;

  @Column(name = LONG_VALUE_COLUMN)
  private Long longValue;

  @Column(name = DOUBLE_VALUE_COLUMN)
  private Double doubleValue;

  @Override
  public TsKvEntry toData() {
    KvEntry kvEntry = null;
    if (strValue != null) {
      kvEntry = new StringDataEntry(key, strValue);
    } else if (longValue != null) {
      kvEntry = new LongDataEntry(key, longValue);
    } else if (doubleValue != null) {
      kvEntry = new DoubleDataEntry(key, doubleValue);
    } else if (booleanValue != null) {
      kvEntry = new BooleanDataEntry(key, booleanValue);
    }
    return new BasicTsKvEntry(ts, kvEntry);
  }

  public boolean isNotEmpty() {
    return strValue != null || longValue != null || doubleValue != null || booleanValue != null;
  }
}
