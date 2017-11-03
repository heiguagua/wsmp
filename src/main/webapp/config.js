/**
 * Created by jsgu on 2017/11/3.
 */
// (function (root, factory) {
//     if (typeof define === 'function' && define.amd) {
//         define([], factory);
//     } else if (typeof exports === 'object') {
//         module.exports = factory();
//     } else {
//         root.config = factory();
//     }
// }(this, function () {
//     return {
var config={
        name:"无线电项目",
        btn:{
            affirm:"确认sss",
            cancle:"取消xxxxxxxxx",
            empty:"清空xxx",
            add:"添加xx",
            update:"更新sss",
            delete:"删除ssss",
            submit:"提交xxxxxxxx",
            refresh:"刷新",
            configuration:"配置xx",
            export:"导出报表",
            frequency:"单频测量",
            locorg:"场强定位选站xxx",
            select:"选择数据xxx"
        },
        info:{
            maplaytit:"场强定位信息xxx",
            laytit:"信息列表xxx",
            laytit1:"重点监测参数配置xxx",
            laytit2:"选择监测站xxx",
            electromagnetic:"电磁覆盖率xxx",
            charttit1:"电平峰值xxx",
            charttit2:"近3个月占用度（按天统计）xxx",
            charttit3:"峰值与日占用度（按24小时统计）xxx",
            charttit4:"小时场强度（按60分钟统计）xxxx",
            nodata:"未查询到数据！ccc",
            station:"台站列表xx"
        },
        waveorder:{
            title:"电波秩序管理",
            title2:"信号监测",
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
            title:"告警管理xxx",
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
// }));
// console.log({}.name)
new Vue({
    el: '#apps',
    data: {
        message: 'Hello World!',
        dd:config
    }
})
