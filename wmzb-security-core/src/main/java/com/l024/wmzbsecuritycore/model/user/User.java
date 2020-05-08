package com.l024.wmzbsecuritycore.model.user;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 用户模型类
 */
@ApiModel(value = "用户模型类")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true) //支持链式风格访问
public class User {
    @JsonView(UserSimpleView.class)
    @ApiModelProperty(value = "用户账号")
    private String userName;

    @ApiModelProperty(value = "用户密码")
    @JsonView(UserDetailView.class)
    private String password;

    /**
     * jsonview的使用
     *1.声明接口视图
     * 2.在字段上使用注解声明
     * 3.在controller中方法上声明
     */
    //部分字段视图
    public interface UserSimpleView{};
    //所有数据视图
    public interface UserDetailView extends UserSimpleView{};
}

