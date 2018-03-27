/**
 * Created by jsgu on 2017/11/3.
 */
(function (root, factory) {
     if (typeof define === 'function' && define.amd) {
         define([], factory);
     } else if (typeof exports === 'object') {
         module.exports = factory();
     } else {
         root.config = factory();
     }
}(this, function () {

var config={
        name:"无线电项目",
        btn:{
            affirm:"确认",
            cancle:"取消",
            empty:"清空",
            add:"添加",
            update:"更新",
            delete:"删除",
            submit:"提交",
            refresh:"刷新",
            configuration:"配置",
            export:"导出报表",
            frequency:"单频测量",
            locorg:"场强定位选站",
            select:"选择数据"
        },
        info:{
            maplaytit:"场强定位信息",
            laytit:"信息列表",
            laytit1:"重点监测参数配置",
            laytit2:"选择监测站",
            electromagnetic:"电磁覆盖率",
            charttit1:"电平峰值",
            charttit2:"近3个月占用度（按天统计）",
            charttit3:"峰值与日占用度（按24小时统计）",
            charttit4:"小时场强度（按60分钟统计）",
            nodata:"未查询到数据！",
            station:"台站列表"
        },
        waveorder:{
            title:"电波秩序管理",
            title2:"信号监测",
            top1:"总告警数:",
            top2:"未处理告警数:",
            top3:"已处理告警数:",
            top4:"智能识别信号数:",
            top5:"总信号数:",
            tab1:"实时告警未确认",
            tab2:"实时告警已确认",
            tab3:"信号智能识别",
            table1:{
                thead:['频段名称','合法正常信号','合法违规信号','已知信号','非法信号','不明信号','重点监测'],
                laythead:['频率(MHz)','带宽(kHz)','监测站','发射源']
            },
            table2:{
                thead:["频率(MHz)","首次出现时间","最后出现时间","监测站","状态","告警内容","描述"],
                thead1:['频率(MHz)','带宽(kHz)','监测站','发射源']
            },
            laytable:['频率(MHz)','带宽(kHz)','监测站','发射源']
        },
        alarm:{
            title:"告警管理"
        },
        signal:{
            title:"信号管理",
            title1:"数据回放",
            bandwidth:"带宽",
            chartinfo:"未识别调制方式",
            feature:"信号特征",
            laytable:["名称","代码","经度","纬度","电平均值"],
            laytable1:["传感器编号","任务唯一编号","任务开始时间","任务结束时间","中心频率(MHz)","带宽(KHz)","频谱个(或点)数"],
            laytable2:["传感器编号","任务唯一编号","任务开始时间","任务结束时间","中心频率(MHz)","I或Q数据个数"],
            laytable3:["传感器编号","任务唯一编号","任务开始时间","任务结束时间","测量中心频率(MHz)","声音数据长度"]
        }
    };
    return {
        config:config
    }
}));
// console.log({}.name)
//new Vue({
//    el: '#apps',
//    data: {
//        message: 'Hello World!',
//        dd:config
//    }
//})
