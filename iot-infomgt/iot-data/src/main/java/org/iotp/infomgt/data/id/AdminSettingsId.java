package org.iotp.infomgt.data.id;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AdminSettingsId extends UUIDBased {

  /**
   * 
   */
  private static final long serialVersionUID = -8967100038259904224L;

  @JsonCreator
  public AdminSettingsId(@JsonProperty("id") UUID id) {
    super(id);
  }

}
