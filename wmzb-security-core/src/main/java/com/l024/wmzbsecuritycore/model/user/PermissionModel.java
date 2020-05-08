package com.l024.wmzbsecuritycore.model.user;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "权限类")
@Data
public class PermissionModel implements Serializable {
    private Long pId;
    private String permissionName;
    private String permissionUrl;
    private String permissionNote;
}
