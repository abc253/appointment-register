package lw.hospital.cmn.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import lw.hospital.cmn.service.DictService;
import lw.hospital.common.result.Result;
import lw.hospital.model.cmn.Dict;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(value = "数据字典接口")
@RestController
@RequestMapping("/admin/cmn/dict")
@Slf4j
//@CrossOrigin
public class DictController {
    @Resource
    private DictService dictService;

    @ApiOperation(value = "导出数据字典")
    @GetMapping("/exportData")
    public void exportDictData(HttpServletResponse response) {
        log.info("导出数据字典...");
        dictService.exportDictData(response);
    }


    @ApiOperation(value = "导入数据字典")
    @PostMapping("/importData")
    public Result importDictData(MultipartFile file) {
        log.info("导入数据字典...");
        dictService.importDictData(file);
        return Result.ok();
    }


    @ApiOperation(value = "根据数据id查询子数据列表")
    @GetMapping("/findChildData/{id}")
    public Result findChildData(@PathVariable Long id) {
        log.info("根据数据id查询子数据列表...");
        List<Dict> list = dictService.findChildData(id);
        return Result.ok(list);
    }

    //根据dictCode获取下级节点
    @ApiOperation(value = "根据dictCode获取下级节点")
    @GetMapping("findByDictCode/{dictCode}")
    public Result findByDictCode(@PathVariable String dictCode) {
        log.info("根据dictCode获取下级节点...");
        List<Dict> list = dictService.findByDictCode(dictCode);
        return Result.ok(list);
    }

    //根据dictcode和value查询
    @GetMapping("getName/{dictCode}/{value}")
    public String getName(@PathVariable String dictCode,
                          @PathVariable String value) {
        log.info("根据dictcode和value查询...");
        String dictName = dictService.getDictName(dictCode,value);
        return dictName;
    }

    //根据value查询
    @GetMapping("getName/{value}")
    public String getName(@PathVariable String value) {
        log.info("根据value查询...");
        String dictName = dictService.getDictName("",value);
        return dictName;
    }
}
