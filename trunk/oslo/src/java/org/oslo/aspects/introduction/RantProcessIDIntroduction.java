package org.oslo.aspects.introduction;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: May 23, 2003
 * Time: 12:09:20 AM
 * To change this template use Options | File Templates.
 */
public interface RantProcessIDIntroduction extends Serializable {

    void setRantProcessID(String processId);

    String getRantProcessID();

    void setRantSequenceID(long sequenceId);

    long getRantSequenceID();
}
