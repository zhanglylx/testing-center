package com.testing.center.web.common.cache;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by kai on 16/4/1.
 */
@JsonIgnoreProperties
public final class EmptyCacheObject implements Serializable {

    public static EmptyCacheObject instance = new EmptyCacheObject();

}
