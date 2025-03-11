package top.xkqq.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xkqq.dto.SysUserDto;
import top.xkqq.entity.system.SysUser;
import top.xkqq.service.SysUserService;
import top.xkqq.vo.common.Result;
import top.xkqq.vo.common.ResultCodeEnum;

@RestController
@RequestMapping("/admin/system/sysUser")
@Tag(name = " 用户接口")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 前端发送 get 请求，路径携带分页数据和大小
     * 前端还会发送一个 dto 对象，用来发送给后端模糊搜索
     */
    @Schema(description = "访问分页 findByPage")
    @GetMapping("/findByPage/{pageNum}/{pageSize}")
    public Result<SysUser> findByPage(@PathVariable Integer pageNum,
                                      @PathVariable Integer pageSize,
                                      @RequestParam(required = false) SysUserDto sysUserDto) {

        Page<SysUser> userPageInfo = sysUserService.findByPage(pageNum, pageSize, sysUserDto);
        return Result.build(userPageInfo, ResultCodeEnum.SUCCESS);
    }

    @Schema(description = "添加用户 saveSysUser")
    @PostMapping("/saveSysUser")
    public Result<SysUser> saveSysUser(@RequestBody SysUser sysUser) {
        sysUserService.save(sysUser);
        return Result.build(sysUser, ResultCodeEnum.SUCCESS);
    }

    @Schema(description = "修改用户 updateSysUser")
    @PutMapping("/updateSysUser")
    public Result<SysUser> updateSysUser(@RequestBody SysUser sysUser) {
        sysUserService.updateById(sysUser);
        return Result.build(sysUser, ResultCodeEnum.SUCCESS);
    }

    @Schema(description = "删除用户 deleteSysUser")
    @DeleteMapping("deleteById/{id}")
    public Result deleteById(@PathVariable("id") Long id) {
        sysUserService.removeById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}
