package lw.hospital.hosp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import lw.hospital.common.result.Result;
import lw.hospital.common.utils.MD5;
import lw.hospital.hosp.service.HospitalSetService;
import lw.hospital.model.hosp.HospitalSet;
import lw.hospital.vo.hosp.HospitalSetQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;


@Api(tags = "医院设置管理")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
@Slf4j
//@CrossOrigin //解决跨域问题
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;


    //1 查询医院设置表所有信息
    @ApiOperation(value = "获取所有医院设置")
    @GetMapping("findAll")
    public Result findAllHospitalSet() {
        log.info("获取所有医院设置...");
        //调用service方法
        List<HospitalSet> list = hospitalSetService.list();
        return Result.ok(list);
    }

    //2 根据id获取医院设置
    @GetMapping("getHospSet/{id}")
    public Result getHospitalSetById(@PathVariable long id) {
        log.info("根据id获取医院设置...");
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }


    //3 条件查询带分页
    @ApiOperation(value = "条件查询带分页")
    @PostMapping("findPageHospSet/{current}/{limit}")
    public Result findPageHospitalSet(@PathVariable long current,
                                      @PathVariable long limit,
                                      @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo) {
        log.info("医院设置(条件查询带分页)...");
        //创建page对象，传递当前页，每页记录数
        Page<HospitalSet> page = new Page<>(current,limit);
        //构建条件
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        String hosname = hospitalSetQueryVo.getHosname();
        String hoscode = hospitalSetQueryVo.getHoscode();

        if (!StringUtils.isEmpty(hosname)) {
            wrapper.like("hosname",hosname);
        }
        if (!StringUtils.isEmpty(hoscode)) {
            wrapper.eq("hoscode",hoscode);
        }
        //调用方法实现分页查询
        Page<HospitalSet> hospitalSetIPage = hospitalSetService.page(page,wrapper);
        //返回结果
        return Result.ok(hospitalSetIPage);
    }

    //4 添加医院设置
    @ApiOperation(value = "添加医院设置")
    @PostMapping("saveHospitalSet")
    public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet) {
        log.info("添加医院设置...");
        //设置状态 1 使用 0 不能使用
        hospitalSet.setStatus(1);
        //签名秘钥
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis()+""+random.nextInt(1000)));
        //调用service

        boolean flag = hospitalSetService.save(hospitalSet);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //5 修改医院设置
    @ApiOperation(value = "修改医院设置")
    @PutMapping("updateHospitalSet")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet) {
        log.info("修改医院设置...");
        boolean flag = hospitalSetService.updateById(hospitalSet);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //6 逻辑删除医院设置
    @ApiOperation(value = "逻辑删除医院设置")
    @DeleteMapping("{id}")
    public Result removeHospitalSet(@PathVariable Long id) {
        log.info("逻辑删除医院设置...");
        boolean flag = hospitalSetService.removeById(id);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //7 批量删除医院设置
    @ApiOperation(value = "批量删除医院设置")
    @DeleteMapping("batchRemove")
    public Result batchRemoveHospitalSet(@RequestBody List<Long> idList) {
        log.info("批量删除医院设置...");
        hospitalSetService.removeByIds(idList);
        return Result.ok();
    }

    //8 医院设置锁定和解锁
    @ApiOperation(value = "医院设置锁定和解锁")
    @PutMapping("lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable long id,
                                  @PathVariable Integer status) {
        log.info("医院设置锁定和解锁...");
        //根据id查询医院设置信息
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        //更改状态
        hospitalSet.setStatus(status);
        //调用方法
        hospitalSetService.updateById(hospitalSet);
        //返回结果
        return Result.ok();
    }

    //9 发送签名秘钥
    @ApiOperation(value = "发送签名秘钥")
    @PutMapping("sendKey/{id}")
    public Result sendKey(@PathVariable long id) {
        log.info("发送签名秘钥...");
        //根据id获取医院设置信息
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String hoscode = hospitalSet.getHoscode();
        String hosname = hospitalSet.getHosname();
        //TODO
        return Result.ok();
    }
}
