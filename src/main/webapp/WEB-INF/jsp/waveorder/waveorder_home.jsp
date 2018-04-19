<%@ page language="java" isThreadSafe="true" pageEncoding="utf8" %>
<%@ page contentType="text/html; charset=utf8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>电波秩序管理</title>
    <meta name="description" content=""/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1"/>
    <link href='3.9/js/dojo/library/bootstrap/css/bootstrap.min.css'
          rel='stylesheet'/>
    <link href="3.9/js/dojo/library/bootstrap-table/bootstrap-table.min.css"
          rel="stylesheet">
    <link href='3.9/js/dojo/library/font-awesome/css/font-awesome.min.css'
          rel='stylesheet'/>
    <link
            href='3.9/js/dojo/library/bootstrap/css/awesome-bootstrap-checkbox.css'
            rel='stylesheet'/>
    <link href='3.9/js/dojo/library/select2/select2.min.css'
          rel='stylesheet'/>
    <link href='css/common.css' rel='stylesheet'/>
    <link href='css/wave_order.css' rel='stylesheet'/>
    <link rel="stylesheet" href="3.9/js/esri/css/esri.css">
    <link rel="stylesheet" href="3.9/js/dojo/dijit/themes/tundra/tundra.css">
    <link rel="stylesheet"
          href="3.9/js/dojo/webgis/widgets/themes/darkangel/darkangel.css">
    <link rel="stylesheet"
          href="3.9/js/dojo/webgis/widgets/themes/darkangel/override.css">
    <link
            href='3.9/js/dojo/library/timepicker/css/bootstrap-datetimepicker.min.css'
            rel='stylesheet'/>
    <link href='3.9/js/dojo/library/layer/layer.css' rel='stylesheet'/>
    <link href='css/waveOrder_map_to_sifon.css' rel='stylesheet'/>

    <style>
        .dijitTooltipContainer {
            background: #fff;
            box-shadow: 0 0 5px #888;
            left: 1em;
            padding: 0.5em;
            position: absolute;
            top: 1em;
            z-index: 40;
        }
        .box {
            position: relative;
        }

        .levelsColor{
            position:absolute;top:10px;right:20px;height:22px
        }
        .levelsColor input[type="number"]{
            width:40px;
            border: 1px solid #DAE5F0;
            border-radius: 4px;
        }.levelsColor input[type="number"],.levelsColor a,.levelsColor img{
             display:inline-block;*display:inline;*zoom:1;height:22px;float:left
         }
        .levelsColor img{
            padding-top:0px
        border: 1px solid #DAE5F0;
            border-radius: 4px;
        }
        #valCtrl{
            border: 1px solid #DAE5F0;
            border-radius: 4px;
            height:23px;
            padding:0px 15px;
            background:#6F6FF2;
            color:#fff;
            font-size:12px;
            text-decoration:none;
            line-height:23px
        }
    </style>
    <link href='3.9/js/dojo/library/element-ui/lib/theme-chalk/index.css' rel='stylesheet'/>
    <%--<script src="3.9/vue.js"></script>--%>
    <script src="3.9/js/dojo/library/vue/dist/vue.js"></script>
    <script src="3.9/js/dojo/library/element-ui/lib/index.js"></script>
    <script src="3.9/js/dojo/library/moment/moment-with-locales.js"></script>
</head>

<body id='waveOrder' class="tundra">
<div id="apps">
    <input style="display: none" id='mapUrl' value="${mapUrl}"/>
    <input value="${areaCode}" hidden="true" id="areaCode"/>
    <!--header-->
    <div class='header-bar order-header'>
        <div class="nav-left">

            <span class='module-name' v-text="dd.waveorder.title"></span>

            <div class='search-filters'>
                <select id='area_select' class='city-list select2-picker'>
                </select>

            </div>
        </div>
        <!-- 统计数据 -->
        <div class="nav-right" style="height:100%">
            <div class="col-xs-2">
                <span class="tit " v-text="dd.waveorder.top1"></span>
                <span id="alarmTotalCount"class="numFont pt top1 "></span>
            </div>
            <div class="col-xs-2">
                <span class="tit" v-text="dd.waveorder.top2"></span>
                <span id="undealedAlarmCounts" class="numFont top2 "></span>
            </div>
            <div class="col-xs-2">
                <span class="tit" v-text="dd.waveorder.top3"></span>
                <span id="dealedAlarmCounts" class="numFont top3 "></span>
            </div>
            <div class="col-xs-2">
                <span class="tit" v-text="dd.waveorder.top4"></span>
                <span id="autoConfirmcounts" class="numFont top4 "></span>
            </div>
            <div class="col-xs-2">
                <span class="tit" v-text="dd.waveorder.top5"></span>
                <span id="signalCounts" class="numFont pt top5 "></span>
            </div>
        </div>
        <!-- 暂时隐藏 -->
         <div class="config pull-right">
            <a class="btn btn-default btn-evaluate" data-toggle="modal"
                data-target="#modalEvaluate"> <i class="fa fa-eye"></i>&nbsp;&nbsp;电磁环境评估
            </a>
        </div>
    </div>

    <!--content-->
    <div class='content-wrap'>
        <!--频段状态-->
        <section class='flex-row'>
            <div class='box right10 table-radio '
                 style="min-height: 300px; max-height: 330px;overflow-y: auto">
                <table class="table table-striped text-center" id="table-radio"  >
                </table>
            </div>
        </section>
        <section>
            <div class="box box-tabs flex-column" style=" max-height: 670px;overflow-y: auto">
                <ul class="nav nav-tabs" id="tabs">
                    <li id='warningEnsure' role="presentation" class="active"><a
                            href="#undeal" v-text="dd.waveorder.tab1"></a></li>
                    <li id='warningUnsure' role="presentation"><a href="#dealed" v-text="dd.waveorder.tab2"></a></li>
                    <li  role="presentation"><a href="#auto_confirm" v-text="dd.waveorder.tab3"></a>
                    </li>
                    <li id='searchFre' role="presentation" class="alarm_filter">
                            <%--<div >--%>
                                <el-radio :label="0" v-model="searchFre" name="searchFre" @change="searchFreChange()">频率(MHz)：</el-radio>
                                <input type="text" id="searchFremin"class="searchFre" value="87" placeholder="最小值"
                                       style="width: 80px; line-height: 1;">
                                <em style="margin: 0px 5px;">-</em>
                                <input type="text" id="searchFremax" class="searchFre" value="108" placeholder="最大值"
                                   style="width: 80px; line-height: 1;">
                                <%--<el-radio :label="1" v-model="searchFre" name="searchFre" @change="searchFreChange()">频段(MHz)：</el-radio>--%>
                                <%--<select  class='city-list select2-picker'>--%>
                                <%--</select>--%>
                                <%--<div class="searchBrand-layout">--%>
                                    <%--<el-input--%>
                                            <%--size="small"--%>
                                            <%--placeholder="请输入内容"--%>
                                            <%--suffix-icon="el-icon-search"--%>
                                            <%--@focus="showBrandInfo()"--%>
                                            <%--v-model="searchBrand">--%>
                                    <%--</el-input>--%>
                                    <%--<div class="searchBrand" v-if="showOrHideBrandInfo">--%>
                                        <%--<el-input--%>
                                                <%--placeholder="输入关键字进行过滤"--%>
                                                <%--v-model="filterText">--%>
                                        <%--</el-input>--%>
                                        <%--<el-tree--%>
                                                <%--class="filter-tree"--%>
                                                <%--:data="data2"--%>
                                                <%--default-expand-all--%>
                                                <%--:filter-node-method="filterNode"--%>
                                                <%--@node-click="nodeClick"--%>
                                                <%--ref="tree2">--%>
                                        <%--</el-tree>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                                <%--<el-radio :label="2" v-model="searchFre" name="searchFre" @change="searchFreChange()">是否重点监测数据</el-radio>--%>
                                <%--<el-radio :label="3" v-model="searchFre" name="searchFre" @change="searchFreChange()">全部</el-radio>--%>
                                <button id="filterFrequeryBand"class="btn btn-info btn-refresh-table">查询</button>

                            <%--</div>--%>
                    </li>
                    <li class="pull-right" id="minutes-li">
                        <input class="minutes"
                            placeholder='更新间隔/分钟' id="minutes"
                            type="number"
                            style="IME-MODE: disabled; width: 100px;line-height: 1.8;"
                            onkeyup="this.value=this.value.replace(/\D/g,'')"
                            onafterpaste="this.value=this.value.replace(/\D/g,'')"
                            maxlength="2" max="30" type="number" oninvalid="setCustomValidity('刷新范围1-30分钟');" oninput="setCustomValidity('');"/>
                        <button id="minutesButton" class="btn btn-info btn-refresh-table">
                            <%--<img src="images/refresh.png">&nbsp;&nbsp;--%>
                            确认
                        </button>
                        <%--<button class="btn" style="height: 28px;">刷新</button>--%>
                        <%--<button class="btn btn-default btn-refresh">--%>
                            <%--&lt;%&ndash;<img src="images/refresh.png">&nbsp;&nbsp;&ndash;%&gt;--%>
                            <%--刷新--%>
                        <%--</button>--%>
                    </li>
                </ul>
                <div class="tab-content flex1">
                    <div role="tabpanel" class="tab-pane active table-alarm-undeal" id='undeal'>
                        <table class="table table-striped text-center"
                               id='table-alarm-undeal'>
                        </table>
                    </div>
                    <div role="tabpanel" class="tab-pane table-alarm-dealed" id='dealed'>
                        <table class="table table-striped text-center"
                               id='table-alarm-dealed'>
                        </table>
                    </div>
                    <div role="tabpanel" class="tab-pane radio_auto_confirm" id='auto_confirm'>
                        <table class="table table-striped text-center"
                               id='radio_auto_confirm'>
                        </table>
                    </div>
                </div>
            </div>
        </section>

        <!--信号监测-->
        <section class='flex-row'>
            <div class='box'>
                <div id='redioType'></div>
                <div id="mapDiv1" style="padding: 10px; height: 960px"></div>
            </div>
        </section>


    </div>

    <!-- Modal Station 信号分类列表点击告警数量列弹出相应的告警列表tab-->
    <div class="modal fade" id="modalAlarm" tabindex="-1" role="dialog"
         aria-labelledby="modalAlarmLabel">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="modalAlarmLabel">告警列表</h4>
                </div>
                <div class="modal-body padding20">
                    <table class="table table-striped" id='table-Alarm-list'>

                    </table>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal Signal-->
    <div class="modal fade" id="modalSignal" tabindex="-1" role="dialog"
         aria-labelledby="modalSignalLabel">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="modalSignalLabel" v-text="dd.info.laytit">信号列表</h4>
                </div>
                <div class="modal-body padding20 table-signal-list">
                    <table class="table table-striped" id='table-signal-list'>

                    </table>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal SignalsOnMonitors-->
    <div class="modal fade" id="modalSignalsOnMonitors" tabindex="-1" role="dialog"
         aria-labelledby="modalSignalLabel">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="modalSignalLabel1" v-text="dd.info.laytit">信号列表</h4>
                </div>
                <div class="modal-body padding20 table-signalsOnMonitors-list">
                    <table class="table table-striped " id='table-signalsOnMonitors-list'>

                    </table>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal Evaluate-->
    <div class="modal fade" id="modalEvaluate" tabindex="-1" role="dialog"
         aria-labelledby="modalEvaluateLabel">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="modalEvaluateLabel">电磁环境评估</h4>
                </div>
                <div class="modal-body " style="padding:0;">
                    <%--<div class="cover-info">电磁覆盖率：<span>90%</span></div>--%>
                    <%--<div class="prev-month arrow-wrap">--%>
                        <%--<div class="arrow-box">--%>
                            <%--<i class="fa fa-chevron-left"></i>--%>

                            <%--<p>上一月</p>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="next-month arrow-wrap">--%>
                        <%--<div class="arrow-box">--%>
                            <%--<i class="fa fa-chevron-right"></i>--%>

                            <%--<p>下一月</p>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                        <%--播放器start--%>
                        <el-row>
                            <el-col :span="6">
                                    <el-date-picker
                                            v-model="startTime"
                                            type="datetime"
                                            placeholder="开始时间"
                                            prefix-icon="el-icon-date"
                                            align="right"
                                            size="mini"
                                            @focus ="timefocus()"
                                            @change="startTimeChange()"
                                            <%--:picker-options="pickerOptions2"--%>
                                            >
                                    </el-date-picker>
                            </el-col>
                            <el-col :span="1"><span  style="margin: 10px 0 0 10px;">~</span></el-col>
                            <el-col :span="6">
                                <el-date-picker
                                        v-model="endTime"
                                        type="datetime"
                                        placeholder="结束时间"
                                        prefix-icon="el-icon-date"
                                        align="right"
                                        size="mini"
                                        @focus ="timefocus()"
                                        @change="endTimeChange()"
                                        <%--:picker-options="pickerOptions2"--%>
                                        >
                                </el-date-picker>
                            </el-col>
                            <el-col :span="6">
                                <el-select
                                        v-model="playType"
                                        size="mini"
                                        @change="selectChange()"
                                        placeholder="请选择">
                                    <el-option
                                            v-for="item in options"
                                            :key="item.value"
                                            :label="item.label"
                                            :value="item.value">
                                    </el-option>
                                </el-select>
                            </el-col>
                        </el-row>
                        <el-row style="font-size:12px">
                            <el-col :span="3" style="margin:7px 0 0 10px;font-size:14px">
                                <a id="btn-pre" title="上一条"@click="playPre()"style="margin-right: 8px;"><i class="fa fa-step-backward "></i></a>
                                <a id="btn-play" title="播放/暂停" @click="play()" style="margin-right: 8px;"><i class="fa fa-play-circle"></i></a>
                                <a id="btn-next" title="下一条" @click="playNext()" style="margin-right: 8px;"><i class="fa fa-step-forward "></i></a>
                                <a id="btn-stop" title="停止" @click="stop()" style="margin-right: 8px;"><i class="fa fa-stop"></i></a>
                            </el-col>
                            <el-col :span="6">
                                <a><el-slider v-model="sliderProgress" :step="step" :show-tooltip="false" @change="changeSlider()"></el-slider></a>
                            </el-col>
                            <el-col :span="5" style="margin: 10px 0 0 10px;">
                                <span v-text="currentTimeProgress"></span>
                            </el-col>
                            <el-col :span="3" style="margin: 5px 20px 0 0 ;float: right;">
                                <span v-text="dd.info.electromagnetic">电磁覆盖率</span>
                                <span class='coverage-number'></span>
                            </el-col>
                        </el-row>
                        <%--播放器-end--%>
                    <section class='flex-row'>
                        <div class='box'>
                            <div id="mapDiv2"></div>
                            <%--控件--%>
                            <div id="levelsColor" class="levelsColor">
                                <%--<el-button type="default" size="small">--%>
                                    <%--综合态势--%>
                                <%--</el-button>--%>
                                <%--&lt;%&ndash;表单配置项start&ndash;%&gt;--%>
                                <%--<el-button type="primary" size="small" @click="showForm()">--%>
                                    <%--<i v-if="showOrHide"class="el-icon-arrow-down el-icon--right"></i><i v-if="!showOrHide" class="el-icon-arrow-up el-icon--right"></i>&nbsp;设置--%>
                                <%--</el-button>--%>
                                <%--<el-form ref="form" :model="normalparamform" class="normalparamform" :style="showOrHideClass">--%>
                                    <%--<el-form-item label="显示设置"></el-form-item>--%>
                                    <%--<el-form-item class="el-form-item-border">--%>
                                        <%--<el-row :gutter="20">--%>
                                            <%--<el-col :span="4">--%>
                                                <%--<el-input v-model="normalparamform.colorMinValue" placeholder="请输入内容"></el-input>--%>
                                            <%--</el-col>--%>
                                            <%--<el-col :span="12">--%>
                                                <%--<img src="images/color.png" style="height: 40px;width: 100%;">--%>
                                            <%--</el-col>--%>
                                            <%--<el-col :span="4">--%>
                                                <%--<el-input v-model="normalparamform.colorMaxValue" placeholder="请输入内容"></el-input>--%>
                                            <%--</el-col>--%>
                                            <%--<el-col :span="4">--%>
                                                <%--<el-button type="primary"><i class="fa fa-repeat"></i> </el-button>--%>
                                            <%--</el-col>--%>
                                        <%--</el-row>--%>
                                    <%--</el-form-item>--%>
                                    <%--<el-form-item class="el-form-item-border">--%>
                                        <%--<el-row >--%>
                                            <%--<el-col :span="4">--%>
                                                <%--透明度：--%>
                                            <%--</el-col>--%>
                                            <%--<el-col :span="20">--%>
                                                <%--<el-slider v-model="normalparamform.opercityValue" ></el-slider>--%>
                                            <%--</el-col>--%>
                                        <%--</el-row>--%>
                                    <%--</el-form-item>--%>
                                    <%--<el-form-item label="监测站显示/隐藏" class="el-form-item-border">--%>
                                        <%--<el-switch style="float:right" v-model="normalparamform.MorStationisShow"></el-switch>--%>
                                    <%--</el-form-item>--%>
                                    <%--<el-form-item v-if="normalparamform.MorStationisShow">--%>
                                        <%--<el-radio-group v-model="normalparamform.morStationIcon">--%>
                                            <%--<el-radio label="图标显示方式" name="morStationIcon"></el-radio>--%>
                                            <%--<el-radio label="点显示方式" name="morStationIcon" ></el-radio>--%>
                                        <%--</el-radio-group>--%>
                                    <%--</el-form-item>--%>
                                    <%--&lt;%&ndash;<el-form-item v-if="normalparamform.MorStationisShow">&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;<el-checkbox-group v-model="normalparamform.type">&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;<el-checkbox label="显示监测站能量值" name="type"></el-checkbox>&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;<el-checkbox label="只显示超过门限的监测站" name="type"></el-checkbox>&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;</el-checkbox-group>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;</el-form-item>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<el-form-item label="业务设置" class="el-form-item-border"></el-form-item>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<el-form-item class="el-form-item-border">&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;<a @click="dialogTableVisible = true"><span>电平门限：分析时段：2017-12-13至2017-12-14 区域：成都市</span></a>&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;<el-dialog title="设置" :visible.sync="dialogTableVisible">&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;<el-form :model="form">&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;<el-form-item label="电平门限：" :label-width="formLabelWidth">&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;<el-input v-model="form.maxValue" auto-complete="off"></el-input>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;</el-form-item>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;<el-form-item label="时间：" :label-width="formLabelWidth">&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;<el-date-picker&ndash;%&gt;--%>
                                                            <%--&lt;%&ndash;v-model="form.time"&ndash;%&gt;--%>
                                                            <%--&lt;%&ndash;type="datetimerange"&ndash;%&gt;--%>
                                                            <%--&lt;%&ndash;align="right"&ndash;%&gt;--%>
                                                            <%--&lt;%&ndash;unlink-panels&ndash;%&gt;--%>
                                                            <%--&lt;%&ndash;prefix-icon="el-icon-date"&ndash;%&gt;--%>
                                                            <%--&lt;%&ndash;range-separator="-"&ndash;%&gt;--%>
                                                            <%--&lt;%&ndash;start-placeholder="开始日期"&ndash;%&gt;--%>
                                                            <%--&lt;%&ndash;end-placeholder="结束日期"&ndash;%&gt;--%>
                                                            <%--&lt;%&ndash;:picker-options="pickerOptions2">&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;</el-date-picker>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;</el-form-item>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;<el-form-item label="区域：" :label-width="formLabelWidth">&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;<el-select v-model="form.region" placeholder="请选择区域">&ndash;%&gt;--%>
                                                        <%--&lt;%&ndash;<el-option label="成都" value=chengdu></el-option>&ndash;%&gt;--%>
                                                        <%--&lt;%&ndash;<el-option label="绵阳" value="mianyang"></el-option>&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;</el-select>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;</el-form-item>&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;</el-form>&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;<div slot="footer" class="dialog-footer">&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;<el-button @click="dialogTableVisible = false">取 消</el-button>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;<el-button type="primary" @click="dialogTableVisible = false">确 定</el-button>&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;</el-dialog>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;</el-form-item>&ndash;%&gt;--%>
                                    <%--<el-form-item label="插值设置" class="el-form-item-border"></el-form-item>--%>
                                    <%--<el-form-item label="是否显示未插值站" class="el-form-item-border">--%>
                                        <%--<el-switch v-model="normalparamform.isShowNoValueMorStation"style="float:right"></el-switch>--%>
                                    <%--</el-form-item>--%>
                                    <%--<el-form-item label="监测站列表:">--%>
                                        <%--<el-checkbox-group v-model="normalparamform.morStationListType">--%>
                                            <%--<el-checkbox label="移动" name="morStationListType"></el-checkbox>--%>
                                            <%--<el-checkbox label="固定" name="morStationListType"></el-checkbox>--%>
                                        <%--</el-checkbox-group>--%>
                                    <%--</el-form-item>--%>
                                    <%--<el-form-item >--%>
                                        <%--<el-checkbox-group v-model="normalparamform.morStationList" class="el-form-item-border">--%>
                                            <%--<el-checkbox v-for="station in normalparamform.morStationAllList" :label="station.code" :key="station.code">{{station.name}}</el-checkbox>--%>
                                        <%--</el-checkbox-group>--%>
                                    <%--</el-form-item>--%>
                                    <%--<el-form-item>--%>
                                        <%--<el-button>取消</el-button>--%>
                                        <%--<el-button type="primary" @click="onSubmit">确认</el-button>--%>
                                    <%--</el-form-item>--%>
                                <%--</el-form>--%>
                                <%--表单配置项end--%>
                                <form method="#">
                                <input type="number" name="opVal" min="0" max="1" id="opCtrl" value="0.7" alt="请输入透明度范围值0~1"
                                title="请输入透明度范围值0~1" style="margin-right: 5px">
                                <input type="number" name="startVal" min="1" id="minCtrl" value="-40" alt="请输入最小值"
                                title="请输入最小值">
                                <img src="images/a.png" alt="">
                                <input type="number" name="endVal" max="10" id="maxCtrl" value="120" alt="请输入最大值"
                                title="请输入最大值">
                                <a href="#" id="valCtrl" v-text="dd.btn.affirm">确认</a>
                                </form>
                            </div>
                            <%--控件 end--%>
                        </div>
                    </section>
                </div>
            </div>
        </div>
    </div>


    <!-- Modal 重点监测参数配置-->
    <div class="modal fade" id="modalConfig" tabindex="-1" role="dialog"
         aria-labelledby="modalConfigLabel">
        <div class="modal-dialog modal-width " role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="modalConfigLabel" v-text="dd.info.laytit1">重点监测参数配置</h4>
                </div>
                <div class="modal-body">
                    <div role="tabpanel" class="tab-pane active ">
                        <div class='flex-row'>
                            <div class='flex1 config-left' id="important_monitor"></div>
                        </div>

                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
<script src="3.9/init.js"></script>
<script type="text/javascript">
    require(
            ["jquery", "dojo/domReady!", "layer"],
            function () {
                require(
                        ["bootstrap", "bootstrapTable", "select2", "datetimepicker"],
                        function () {
                            require(
                                    ["bootstrap_table_cn", "datetimepicker_cn"],
                                    function () {
                                        require(
                                                ["home/waveorder/waveorder_manage"],
                                                function (wave_order) {
                                                    wave_order.init();
                                                })
                                    })
                        })
            });
</script>
<%--<script src="config.js"></script>--%>
</body>
</html>