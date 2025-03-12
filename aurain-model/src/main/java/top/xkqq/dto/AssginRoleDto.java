package top.xkqq.dto;

import lombok.Data;

import java.util.List;

@Data
public class AssginRoleDto {
    private Long userId;                // 用户的id
    private List<Long> roleIdList;        // 角色id
}
