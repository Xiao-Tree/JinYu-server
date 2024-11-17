package com.xiaotree.jinyuserver.domain.vo;

import cn.hutool.system.oshi.CpuInfo;
import cn.hutool.system.oshi.OshiUtil;
import lombok.Getter;
import oshi.software.os.OperatingSystem;


@Getter
public class SystemInfo {
    private final String osVersion;
    private final String cpuName;

    public SystemInfo(){
        CpuInfo cpuInfo = OshiUtil.getCpuInfo();
        OperatingSystem os = OshiUtil.getOs();
        this.osVersion=os.getVersionInfo().getVersion();
        this.cpuName=cpuInfo.getCpuModel();
        cpuInfo.getTicks();


        OshiUtil.getMemory();
        OshiUtil.getHardware();
    }


}
