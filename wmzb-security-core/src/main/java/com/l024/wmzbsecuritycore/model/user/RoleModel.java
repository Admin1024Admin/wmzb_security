package com.l024.wmzbsecuritycore.model.user;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "角色类")
@Data
public class RoleModel implements Serializable{
    private Long rId;
    private String roleName;
    private String roleNote;
    private Long permissionId;
    private List<PermissionModel> permissionModels;
}
