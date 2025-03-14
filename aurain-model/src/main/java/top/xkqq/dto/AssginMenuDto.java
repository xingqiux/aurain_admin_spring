package top.xkqq.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AssginMenuDto {
    private Long roleId;
    private List<Map<String, Number>> menuIdList;    // 选中菜单的集合
}
