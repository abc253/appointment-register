package lw.hospital.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import lw.hospital.common.result.Result;
import lw.hospital.model.user.UserInfo;
import lw.hospital.user.service.UserInfoService;
import lw.hospital.vo.user.UserInfoQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/admin/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    //用户列表（条件查询带分页）
    @GetMapping("{page}/{limit}")
    public Result list(@PathVariable Long page,
                       @PathVariable Long limit,
                       UserInfoQueryVo userInfoQueryVo) {
        log.info("用户列表（条件查询带分页）...");
        Page<UserInfo> pageParam = new Page<>(page,limit);
        IPage<UserInfo> pageModel =
                userInfoService.selectPage(pageParam,userInfoQueryVo);
        return Result.ok(pageModel);
    }

    //用户锁定
    @GetMapping("lock/{userId}/{status}")
    public Result lock(@PathVariable Long userId,@PathVariable Integer status) {
        log.info("用户锁定...");
        userInfoService.lock(userId,status);
        return Result.ok();
    }

    //用户详情
    @GetMapping("show/{userId}")
    public Result show(@PathVariable Long userId) {
        log.info("用户详情...");
        Map<String,Object> map = userInfoService.show(userId);
        return Result.ok(map);
    }

    //认证审批
    @GetMapping("approval/{userId}/{authStatus}")
    public Result approval(@PathVariable Long userId,@PathVariable Integer authStatus) {
        log.info("认证审批...");
        userInfoService.approval(userId,authStatus);
        return Result.ok();
    }
}
