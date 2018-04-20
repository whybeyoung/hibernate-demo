package com.iflytek.iaas.consts;

/**
 * Created by xwliu on 2018/4/16.
 */
public enum K8sAPPType {
    INDEPENDENT_APP,INTERNAL_SERVICE,EXTERNAL_SERVICE;

    public static K8sAPPType ordinalOf(int ordinal){
        for(K8sAPPType type: K8sAPPType.values()){
            if(type.ordinal() == ordinal)
                return type;
        }
        return INDEPENDENT_APP;
    }
}
