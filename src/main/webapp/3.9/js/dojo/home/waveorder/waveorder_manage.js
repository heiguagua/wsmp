define(	["ajax", "dojo/parser", "esri/map",
				"esri/layers/ArcGISTiledMapServiceLayer", "dojo/request",
				"esri/layers/GraphicsLayer", "esri/dijit/Scalebar",
				"esri/symbols/TextSymbol", "esri/geometry/Point",
				"esri/graphic", "esri/symbols/Font",
				"esri/symbols/SimpleMarkerSymbol",
				"esri/symbols/PictureMarkerSymbol","dijit/popup","dijit/TooltipDialog","esri/lang","library/map/appMap","config"], function(ajax, parser,
				Map, ArcGISTiledMapServiceLayer, request, GraphicsLayer,
				Scalebar, TextSymbol, Point, graphic, Font, SimpleMarkerSymbol,
				PictureMarkerSymbol,dijitPopup,TooltipDialog,esriLang,AppMap,config) {
			//全局变量
			var MAP1 = null;
			var AREACODE = null;
			var MONITORS = null;
			var INTERVAL_warning = null;
	        var app =null;

			//初始化
			function wo_init() {

					var vm =new Vue({
						el: '#apps',
						data: function() {
							var currentDate =new Date();
							var start =(currentDate - 3600 * 1000 * 24 *30);//默认设置为近一月
							var currentTimeProgress =moment(start).format("YYYY-MM-DD");
							return {
								dd:config.config,
								pickerOptions2: {
									shortcuts: [{
										text: '最近一周',
										onClick:function(picker) {
											const end = new Date();
											const start = new Date();
											start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
											picker.$emit('pick', [start, end]);
										}
									}, {
										text: '最近一个月',
										onClick:function(picker) {
											const end = new Date();
											const start = new Date();
											start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
											picker.$emit('pick', [start, end]);
										}
									}, {
										text: '最近三个月',
										onClick:function(picker) {
											const end = new Date();
											const start = new Date();
											start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
											picker.$emit('pick', [start, end]);
										}
									}]
								},
								//time: [start, currentDate],
								startTime:start,
								endTime:currentDate,
								options: [{
									value: 'hour',
									label: '小时/帧'
								}, {
									value: 'date',
									label: '天/帧'
								}, {
								value: 'month',
									label: '月/帧'
							     }],
								playType: 'date',
								currentTimeProgress:'',
								sliderProgress:1,
								step:100/30,//步长
								//配置项
								showOrHide:true,
								showOrHideClass: { //显示还是弹出表单
									display:"none"
								},
								normalparamform: {
									colorMinValue:-75,//颜色最小值
									colorMaxValue:74,//颜色最大值
									opercityValue: 30,//透明度 ，0为不透明，100为完全透明
									morStationisShow:true,//监测站显示或者隐藏
									morStationIcon:'图标显示方式' ,//站点图标显示方式
									type:['显示监测站能量值','只显示超过门限的监测站'],//显示监测站能量值,只显示超过门限的监测站

									//插值设置
									isShowNoValueMorStation:false ,//是否显示未插值站
									morStationListType:['移动'] ,//监测站列表类型 移动/固定
									morStationAllList:getstationsInfo().stations,//所有的监测站列表
									//morStationList:["123监测站","川大花园监测站1","龙泉山监测站"] //监测站列表
									morStationList:["51010123","51010026","51010020"] //监测站列表
								},

								//业务设置 设置电平门限，时间段，城市
								dialogTableVisible:false,
								form: {
									maxValue:12,
									time: [],
									region: ''
								},
								formLabelWidth: '120px',
								//告警信息列表,系统配置频段
								searchFre:0,
								searchBrand:'',
								filterText: '',
								showOrHideBrandInfo: false,//显示还是隐藏频段信息
								data2: [{
									id: 1,
									label: '一级 1',
									children: [{
										id: 4,
										label: '二级 1-1',
										children: [{
											id: 9,
											label: '三级 1-1-1'
										}, {
											id: 10,
											label: '三级 1-1-2'
										}]
									}]
								}, {
									id: 2,
									label: '一级 2',
									children: [{
										id: 5,
										label: '二级 2-1'
									}, {
										id: 6,
										label: '二级 2-2'
									}]
								}, {
									id: 3,
									label: '一级 3',
									children: [{
										id: 7,
										label: '二级 3-1'
									}, {
										id: 8,
										label: '二级 3-2'
									}]
								}],
								defaultProps: {
									children: 'children',
									label: 'label'
								}


							}
						},
						methods:{
							//日期选择器输入框获得焦点时，停止播放
							timefocus: function() {
								this.stop();
							},
							//日期选择器输入框改变值时，继续播放
							startTimeChange: function(){
								vm.endTime ='';
							},
							//日期选择器输入框改变值时，继续播放
							endTimeChange: function(){
								console.log(vm.endTime-vm.startTime)
								if((vm.endTime-vm.startTime)<0){
									vm.endTime ='';
									layer.msg("结束时间不能小于开始时间")
								}else{
									this.autoPlay();
								}

							},
							//改变选择播放帧；按原始数据，按小时，按天,按月
							selectChange: function(){
								console.log(vm.playType)
								this.stop();
								if(vm.playType=='hour'){//默认设置为近一天
                                     vm.startTime = vm.endTime - 3600 * 1000 * 24;
								}else if(vm.playType=='date'){//默认设置为近一月
									vm.startTime = vm.endTime - 3600 * 1000 * 24*30;
								}else if(vm.playType=='month'){//默认设置为近一年
									vm.startTime = vm.endTime - 3600 * 1000 * 24*365;
								}
								this.autoPlay();

							},
							//拖动进度条或点击进度条位置来切换到指定帧，继续播放
							changeSlider: function(){
								console.log(vm.sliderProgress);
								this.autoPlay();
							},
							//自动播放，自动修改进度条值，即修改某帧的值，自动调用态势图的接口数据
							autoPlay:function(){
								var count= 0;
								var int = setInterval(function(){
									//console.log(vm.sliderProgress)
									//if(vm.sliderProgress>=100){
									//	clearInterval(int);
									//	vm.sliderProgress=0;
									//}
									if(vm.playType=='hour'){
										//默认设置为近一天
										var hours =(vm.endTime-vm.startTime)/(3600*1000);
										//hours =24;
										console.log("按小时/帧播放，共："+hours+"小时")
										if(count>hours-1){
											clearInterval(int);
											vm.sliderProgress=0;
											count= 0;
											return;
										}
										//console.log(count)
										vm.step=100/hours; //步长
										vm.sliderProgress =vm.sliderProgress+100/hours;
										var dd =(vm.endTime-vm.startTime)/hours;
										vm.currentTimeProgress =moment(moment(vm.startTime)+3600*1000*count++).format("YYYY-MM-DD HH");
										console.log(moment(vm.currentTimeProgress).format("YYYYMMDDHH"));

									}else if(vm.playType=='date'){
										//默认设置为近一月
										var dates =(vm.endTime-vm.startTime)/(3600*1000*24);
										//dates =30;
										console.log("按天/帧播放，共："+dates+"天")
										if(count>dates-1){
											clearInterval(int);
											vm.sliderProgress=0;
											count= 0;
											return;
										}

										vm.step=100/dates; //步长
										vm.sliderProgress =vm.sliderProgress+100/dates;
										var dd =(vm.endTime-vm.startTime)/dates;
										vm.currentTimeProgress =moment(moment(vm.startTime)+3600*1000*24*count++).format("YYYY-MM-DD");
										console.log(moment(vm.currentTimeProgress).format("YYYYMMDD"));
										//baseMapInit();

									}else if(vm.playType=='month'){
										//默认设置为近一年
										var months =(vm.endTime-vm.startTime)/(3600*1000*24*30);
										//months =12;
										console.log("按月/帧播放，共："+months+"月")
										if(count>months-1){
											clearInterval(int);
											vm.sliderProgress=0;
											count= 0;
											return;
										}
										vm.step=100/months; //步长
										vm.sliderProgress =vm.sliderProgress+100/months;
										var dd =(vm.endTime-vm.startTime)/months;
										vm.currentTimeProgress =moment(moment(vm.startTime)+3600*1000*24*30*count++).format("YYYY-MM");
										console.log(moment(vm.currentTimeProgress).format("YYYYMM"));

									}else{
										vm.step=100; //步长
										vm.sliderProgress =vm.sliderProgress+1;
										var dd =(vm.endTime-vm.startTime);
										vm.currentTimeProgress =moment(moment(vm.startTime)+vm.sliderProgress*dd).format("YYYY-MM-DD HH:mm:ss");
									}

								},2000)
								sessionStorage.setItem('evaluatePlay',int);
							},
							//点击播放按钮，需要换成暂停图标，暂停播放
							play :function() {
								var str =$("#btn-play").html();
								if(str=='<i class="fa fa-play-circle"></i>'){//播放状态：需要暂停；
									$("#btn-play").html('<i class="fa fa-pause-circle"></i>');
									var int =sessionStorage.getItem("evaluatePlay");
									clearInterval(int);

								}else {//暂停状态：
									$("#btn-play").html('<i class="fa fa-play-circle"></i>');
									this.autoPlay();
								}
							},
							//播放上一条
							playPre: function(){
								if(vm.sliderProgress<=0){
									vm.sliderProgress=100;
								}
								vm.sliderProgress =vm.sliderProgress-1;
								var dd =(vm.endTime-vm.startTime)/100;
								if(vm.value=='h'){
									vm.currentTimeProgress =moment(moment(vm.startTime)+vm.sliderProgress*dd).format("YYYY-MM-DD HH");
								}else if(vm.value=='d'){
									vm.currentTimeProgress =moment(moment(vm.startTime)+vm.sliderProgress*dd).format("YYYY-MM-DD");
								}else{
									vm.currentTimeProgress =moment(moment(vm.startTime)+vm.sliderProgress*dd).format("YYYY-MM-DD HH:mm:ss");
								}
							},
							//播放下一条
							playNext: function(){
								if(vm.sliderProgress>=100){
									clearInterval(int);
									vm.sliderProgress=0;
									return;
								}
								vm.sliderProgress =vm.sliderProgress+1;
								var dd =(vm.endTime-vm.startTime)/100;
								if(vm.value=='h'){
									vm.currentTimeProgress =moment(moment(vm.startTime)+vm.sliderProgress*dd).format("YYYY-MM-DD HH");
								}else if(vm.value=='d'){
									vm.currentTimeProgress =moment(moment(vm.startTime)+vm.sliderProgress*dd).format("YYYY-MM-DD");
								}else{
									vm.currentTimeProgress =moment(moment(vm.startTime)+vm.sliderProgress*dd).format("YYYY-MM-DD HH:mm:ss");
								}

							},
							//停止播放
							stop: function(){
								vm.sliderProgress=0;
								var int =sessionStorage.getItem("evaluatePlay");
								clearInterval(int);
							},
							//配置项
							showForm: function() {
								if(vm.showOrHide){
									vm.showOrHide =false;
									vm.showOrHideClass ={
										display:"block"
									}
								}else{
									vm.showOrHide =true;
									vm.showOrHideClass ={
										display:"none"
									}
								}

							},
							onSubmit: function () {
								console.log(vm.normalparamform);
			//                if(vm.form.maxValue&&vm.form.time.length&&vm.form.region){
			//
			//                }
								this.showForm();
							},
							//告警信息列表,系统配置频段,选中频率或频段
							 searchFreChange: function(){
								 console.log(vm.searchFre)
								 if(this.searchFre !=1){
									 this.showOrHideBrandInfo =false
								 }

							 },
							filterNode:function(value, data) {
								if (!value) return true;
								return data.label.indexOf(value) !== -1;
							},
							nodeClick: function(data){
								console.log(data)
								vm.searchBrand =data.label;
								this.showOrHideBrandInfo =false;//隐藏频段信息
							},
							//显示频段信息
							showBrandInfo: function() {
								if(this.searchFre ==1){
									this.showOrHideBrandInfo =true
								}else{
									this.showOrHideBrandInfo =false
								}
							}


						},
						watch: {
							filterText:function(val) {
								this.$refs.tree2.filter(val);
							}
						},
						mounted:function(){
							//this.autoPlay();

							//console.log(this.morStationAllList)
						}
					});

                 //统计数据，电波秩序总览中，显示各列中每种信号类型的总数
				getSignalCounts();


				// 地图初始化
				MAP1 = mapInit();
				// 得到区域信息
				getArea();
				// 下拉框初始化
				selector_init();
				// 时间选择器初始化
				datetimepicker_init();
				// 告警定时更新初始化
				refresh_timer_init(3);

				// 监听下拉框点击事件
				$(".select2-picker").on("select2:select", function(e) {
							var areaCode = e.target.value;
							select2_change(areaCode);
						});

				// 切换页面点击事件
				$("#tabs a").click(function(e) {
							e.preventDefault();
							$(this).tab('show');
						});

				// 信号类型切换点击事件
				$("#redioType").on("click", "input", function(e) {

					$("#redioType label").removeClass("color").removeAttr("style");
					$("#"+e.toElement.id).siblings("label").css("color","#409EFF")
					var typeCode = Number(e.target.value)
					var isSubType = e.target.getAttribute("issubtype");
					addSignalCountOnMonitors(MONITORS, typeCode, isSubType);
				});

						
				// 时间选择器点击事件
				$("#modalConfig").on("click",".time-picker",function (e) {
					$(e.currentTarget).datetimepicker("show");
					$(e.currentTarget).datetimepicker("setStartDate", new Date());
				})
						
				// 重点监测点击事件
				$("#modalConfig").on("show.bs.modal", function(e) {
					if(typeof e.relatedTarget == "undefined") {
						return null;
					}
					var a = $(e.relatedTarget);
					var beginFreq = a.data('beginfreq');
					var endFreq = a.data('endfreq');
					console.log(beginFreq + '-' + endFreq);
					var data = {};
					data.beginFreq = beginFreq;
					data.endFreq = endFreq;
					var str = JSON.stringify(data);
					$.ajax({
								url : 'waveorder/importantMonitor',
								type : 'post',
								data : str,// 传输数据
								contentType : 'application/json',// 传输数据类型
								dataType : 'html',// 返回数据类型
								success : function(html) {
									$("#important_monitor").html(html);
								}
							})
				});
                 //信号分类统计列表点击频段名称跳转至单频率
				$('#table-radio').on("click",".redioNameA",function(e){
					var freq = e.target.text;
					const urlObj = {
						ServerName : 'host1',
						DisplayName : '单频率',
						MultiTabable : false,
						ReflushIfExist : true,
						Url : '#/FrequencySingle/' + freq
					};
					Binding.openUrl(JSON.stringify(urlObj));
				})
//				// 信号统计表 频段名称链接点击事件
//				$("#table-radio").on("click", ".redioNameA",
//						function(e) {
//							console.log("click");
////							console.log(e);
////							var freq = e.target.getAttribute("centorfreq");
////							var signalId = e.target.getAttribute("signalid");
////							console.log(freq);
////							console.log(signalId);
////							const urlObj = {
////								ServerName : 'host2',
////								DisplayName : '信号管理',
////								MultiTabable : false,
////								ReflushIfExist : true,
////								Url : 'radio/app/signal?id=sefon&cenFreg='
////										+ freq + '&signalId=' + signalId
////							};
////							Binding.openUrl(JSON.stringify(urlObj));
//			})
						
				// 信号详情 频率链接点击事件
				$("#table-signal-list").on("click", ".centerFreqA",
						function(e) {
							var freq = e.target.text;
							const urlObj = {
								ServerName : 'host1',
								DisplayName : '单频率',
								MultiTabable : false,
								ReflushIfExist : true,
								Url : '#/FrequencySingle/' + freq
							};
							Binding.openUrl(JSON.stringify(urlObj));

						})
						
				// 信号自动确认 频率链接点击事件
				$("#radio_auto_confirm").on("click", ".centerFreqA",
						function(e) {
							var freq = e.target.text;
							const urlObj = {
								ServerName : 'host1',
								DisplayName : '单频率',
								MultiTabable : false,
								ReflushIfExist : true,
								Url : '#/FrequencySingle/' + freq
							};
							Binding.openUrl(JSON.stringify(urlObj));

						})
						
				// 监测站所监测到的信号列表 频率链接点击事件
				$("#table-signalsOnMonitors-list").on("click", ".centerFreqA",
						function(e) {
							var freq = e.target.text;
							const urlObj = {
								ServerName : 'host1',
								DisplayName : '单频率',
								MultiTabable : false,
								ReflushIfExist : true,
								Url : '#/FrequencySingle/' + freq
							};
							Binding.openUrl(JSON.stringify(urlObj));

						})

				// 信号详情 查看链接点击事件
				$("#table-signal-list").on("click", ".signalManageA",
						function(e) {
							console.log(e);
							var freq = e.target.getAttribute("centorfreq");
							var signalId = e.target.getAttribute("signalid");
							console.log(freq);
							console.log(signalId);
							const urlObj = {
								ServerName : 'host2',
								DisplayName : '信号管理',
								MultiTabable : false,
								ReflushIfExist : true,
								Url : 'radio/app/signal?id=sefon&cenFreg='
										+ freq + '&signalId=' + signalId
							};
							Binding.openUrl(JSON.stringify(urlObj));
						})
						
				// 监测站所监测到的信号列表 查看链接点击事件
				$("#table-signalsOnMonitors-list").on("click", ".signalManageA",
						function(e) {
							console.log(e);
							var freq = e.target.getAttribute("centorfreq");
							var signalId = e.target.getAttribute("signalid");
							console.log(freq);
							console.log(signalId);
							const urlObj = {
								ServerName : 'host2',
								DisplayName : '信号管理',
								MultiTabable : false,
								ReflushIfExist : true,
								Url : 'radio/app/signal?id=sefon&cenFreg='
										+ freq + '&signalId=' + signalId
							};
							Binding.openUrl(JSON.stringify(urlObj));
						})
				// 自动确认信号 查看链接点击事件
				$("#radio_auto_confirm").on("click", ".signalManageA",
						function(e) {
							console.log(e);
							var freq = e.target.getAttribute("centorfreq");
							var signalId = e.target.getAttribute("signalid");
							console.log(freq);
							console.log(signalId);
							const urlObj = {
								ServerName : 'host2',
								DisplayName : '信号管理',
								MultiTabable : false,
								ReflushIfExist : true,
								Url : 'radio/app/signal?id=sefon&cenFreg='
										+ freq + '&signalId=' + signalId
							};
							Binding.openUrl(JSON.stringify(urlObj));
						})
				// 告警未处理频率链接点击事件
				$("#table-alarm-undeal").on("click", ".centerFreqA",
						function(e) {
							var freq = e.target.text;
							const urlObj = {
								ServerName : 'host1',
								DisplayName : '单频率',
								MultiTabable : false,
								ReflushIfExist : true,
								Url : '#/FrequencySingle/' + freq
							};
							Binding.openUrl(JSON.stringify(urlObj));

						})

				// 告警处理频率链接点击事件
				$("#table-alarm-dealed").on("click", ".centerFreqA",
						function(e) {
							var freq = e.target.text;
							const urlObj = {
								ServerName : 'host1',
								DisplayName : '单频率',
								MultiTabable : false,
								ReflushIfExist : true,
								Url : '#/FrequencySingle/' + freq
							};
							Binding.openUrl(JSON.stringify(urlObj));
						})

				// 告警未处理状态链接点击事件
				$("#table-alarm-undeal").on("click", ".alarmManageA",
						function(e) {
							console.log(e);
							var freq = e.target.getAttribute("centorfreq");
							var freqid = e.target.getAttribute("freqid");
							console.log(freq);
							console.log(freqid);
							const urlObj = {
								ServerName : 'host2',
								DisplayName : '告警管理',
								MultiTabable : false,
								ReflushIfExist : true,
								Url : 'radio/app/alarmmanage?id=QZ&cenFreg='+ freq + '&warningId=' + freqid
							};
							Binding.openUrl(JSON.stringify(urlObj));
						})

				// 重点监测更新点击事件
				$("#important_monitor").on("click", "#buttonUpdate",
						function(e) {
							var valid = beforeSubmit(document.importantMonitorForm);
							if (valid) {
								var str = $("#important-monitor-form")
										.serialize();
								$.ajax({
									url : 'waveorder/importantMonitorCreateOrUpdate',
									type : 'post',
									data : str,
									dataType : 'text',// 只返回bool值
									success : function() {
										layer.msg("更新成功！");
										/*$("#important_monitor").html(html);
										$("#modalConfig").find(".time-picker")
												.datetimepicker({});*/
										$("#modalConfig").modal('hide');
									},
									error : function(text) {
										str = text.responseText;
										jsonObject = JSON.parse(str);
										layer.alert(jsonObject.message);
									}
								})
							} else {
								//layer.msg('不能为空!')
							}
						});

				// 重点监测添加点击事件
				$("#important_monitor").on("click", "#buttonInsert",
						function(e) {
							var valid = beforeSubmit(document.importantMonitorForm);
							if (valid) {
								var str = $("#important-monitor-form")
										.serialize();
								$.ajax({
									url : 'waveorder/importantMonitorCreateOrUpdate',
									type : 'post',
									data : str,
									dataType : 'text',// 只返回bool值
									success : function() {
										layer.msg("添加成功！");
										/*$("#important_monitor").html(html);
										$("#modalConfig").find(".time-picker")
												.datetimepicker({});*/
										$("#modalConfig").modal('hide');
										$('#table-radio').bootstrapTable(
												"refresh", {
													silent : true
												});

									},
									error : function(text) {
										str = text.responseText;
										jsonObject = JSON.parse(str);
										layer.alert(jsonObject.message);
									}
								})
							} else {
								//layer.msg('不能为空!')
							}
						});

				// 重点监测删除点击事件
				$("#important_monitor").on("click", "#buttonDelete",
						function(e) {
							// 确实是否删除
							// layer.confirm('is not?', {icon: 3, title:'提示'},
							// function(index){
							// console.log(index);
							// layer.close(index);
							// });
								var str = $("#important-monitor-form")
										.serialize();
								$.ajax({
											url : 'waveorder/importantMonitorDelete',
											type : 'post',
											data : str,
											dataType : 'html',// 只返回bool值
											success : function() {
												layer.msg("删除成功!");
												/*$("#important_monitor")
														.html(html);
												$("#modalConfig")
														.find(".time-picker")
														.datetimepicker({});*/
												$("#modalConfig").modal('hide');
												$('#table-radio')
														.bootstrapTable(
																"refresh", {
																	silent : true
																});
											},
											error : function(text) {
											str = text.responseText;
											jsonObject = JSON.parse(str);
											layer.alert(jsonObject.message);
										}
										})
						});

				// 信号统计点击进入详情页事件
				$("#modalSignal").on("show.bs.modal", function(e) {
					var a = $(e.relatedTarget);
					var beginFreq = a.data('beginfreq');// data()函数里面要取小写
					var endFreq = a.data('endfreq');
					var radioType = a.data('radiotype');
					var monitorsID = new Array();
					for (var i = 0; i < MONITORS.length; i++) {
						monitorsID[i] = MONITORS[i].Num;
					}
					var isSubType = a.data('issubtype');
					$('#table-signal-list').bootstrapTable("destroy");
					$('#table-signal-list').bootstrapTable({
						method : 'post',
						cache : false,
						contentType : "application/json",
						url : "data/waveorder/radioDetail",
						striped : true, // 是否显示行间隔色
						dataField : "data",
						detailView : false,
						pageNumber : 1, // 初始化加载第一页，默认第一页
						pagination : true, // 是否分页
						queryParamsType : 'limit', // 查询参数组织方式
						queryParams : function(params) {
							params.beginFreq = beginFreq;
							params.endFreq = endFreq;
							params.radioType = radioType;
							params.monitorsID = monitorsID;
							params.isSubType = isSubType;
							return params
						}, // 请求服务器时所传的参数
						sidePagination : 'client', // 指定服务器端分页
						pageSize : 10, // 单页记录数
						pageList : [5, 10, 20, 30], // 分页步进值
						clickToSelect : true, // 是否启用点击选中行
						search : true,
						searchAlign:"left",
//						searchOnEnterKey : true,
						strictSearch : true,
//						searchText : "搜索信号",
						responseHandler : function(res) {
							return res;
						},
						columns : [{
							class : "sortTable3",
							searchable : true,
							searchFormatter : false,
							field : 'centor',
							title : '频率(MHz)' +"<img src='images/arrow-both.png'width='24'/> ",
							titleTooltip : "频率(MHz)",
							sortable : true,
							sortName : "centor",
							width : '15%',
							formatter : function(value, row, index) {
								return '<a class="centerFreqA">' + value
										+ '</a>';
							}
						}, {
							class : "sortTable3",
							searchable : false,
							field : 'band',
							title : '带宽(kHz)' +"<img src='images/arrow-both.png'width='24'/> ",
							width : '15%',
							titleTooltip : "带宽(kHz)",
							sortable : true
						}, {
							class : "sortTable3",
							searchable : false,
							visible : false,
							field : 'success_rate',
							title : '监测发射功率' +"<img src='images/arrow-both.png'width='24'/> ",
							width : '18%',
							titleTooltip : "监测发射功率",
							sortable : true
						}, {
							class : "sortTable3",
							searchable : false,
							field : 'monitorID',
							title : '监测站' +"<img src='images/arrow-both.png'width='24'/> ",
							width : '30%',
							titleTooltip : "监测站",
							sortable : true,
							sortName : "monitorID",
							formatter : function(value, row, index) {
								var monitors = getMonitors(AREACODE);
								var content = "";
									var value1 = [];
									for (var i = 0; i < value.length; i++) {
										for (var j = 0; j < monitors.length; j++) {
											if (value[i] == monitors[j].Num) {
												value1[i] = monitors[j].Name;
												var sub_content = "<div class='popover-item'>"
														+ value1[i] + "</div>";
												content += sub_content;
											}
										}
									}
									return '<div class="dpopover" data-container="body"  data-placement="top"  data-toggle="popover" data-trigger="hover" data-content="'
											+ content + '">' + value1 + '</div>';
							},
							events : {

							}
						}, {
							class : "sortTable3",
							searchable : false,
							field : 'station',
							title : '发射源' +"<img src='images/arrow-both.png'width='24'/> ",
							width : '30%',
							titleTooltip : "发射源",
							sortable : true,
							sortName : "station",
							formatter : function(value, row, index) {
								value = value == null ? "-" : value;
								return value;
							}
						}, {
							searchable : false,
							field : 'id',
							visible : false,
							formatter : function(value, row, index) {
								return value;
							}
						}, {
							searchable : false,
							field : "signalManage",
							formatter : function(value, row, index) {
								return '<a signalId=' + row.id
										+ ' class="signalManageA" centorFreq='
										+ row.centor + '>查看</a>';
							}
						}],
						onLoadSuccess : function() {
							$("#table-signal-list").find(".dpopover").popover({
										html : true
									});
							$(".sortTable3").on("click",function(e){
							$("th.sortTable3").find('img').attr("src","images/arrow-both.png");
							if($(this).children().attr("class").match("desc")){
								$(this).find('img').attr("src","images/arrow-up.png");
							}else{
								$(this).find('img').attr("src","images/arrow-bottom.png");
							}
						});
						},
						onAll:function(){
							$("#table-signal-list").find(".dpopover").popover({
								html : true
							});
						}
					});
				});
				//信号分类统计列表 点击告警数量弹出告警列表tab(未完成)
				$("#modalAlarm").on("show.bs.modal", function(e) {
					var a = $(e.relatedTarget);
					var beginFreq = a.data('beginfreq');// data()函数里面要取小写
					var endFreq = a.data('endfreq');
					var radioType = a.data('radiotype');
					var monitorsID = new Array();
					for (var i = 0; i < MONITORS.length; i++) {
						monitorsID[i] = MONITORS[i].Num;
					}
					var isSubType = a.data('issubtype');
					$('#table-Alarm-list').bootstrapTable("destroy");
					$('#table-Alarm-list').bootstrapTable({});

				});

				//地图刷新按钮点击事件
				$("#redioType").on("click", ".btn", function(e) {
							// 重置地图
							// 并不是resize或者reposition
							// 应是重置center和zoom
							// 根据监测站数组的平均位置，得到中心定位
							//暂时取第一个监测站的位置
							var center = new Point({
										"x" : MONITORS[0].Longitude,
										"y" : MONITORS[0].Latitude
									});
							MAP1.setZoom(8);
							MAP1.centerAt(center);
							redioType(MONITORS);
							addSignalCountOnMonitors(MONITORS, 1, "false");
						});

				// 初始化电磁环境评估地图事件
				$('#modalEvaluate').on('shown.bs.modal', function(e) {

							//var mapUrl = Binding.getMapUrl();
							//var url = mapUrl;
							//var map2 = new Map("mapDiv2", {
							//		center :[MONITORS[0].Longitude,MONITORS[0].Latitude],
							//		zoom : 8,
							//		logo : false
							//	});
							//var agoLayer2 = new ArcGISTiledMapServiceLayer(url,
							//		{
							//			id : "街道地图1",
                             //           showAttribution:false
							//		});
							//var glayer2 = new GraphicsLayer({
							//	id : "glayer2"
							//});
							//map2.addLayer(agoLayer2);
							//map2.addLayer(glayer2);
							//addAreaBoundary(map2);
					      vm.autoPlay();
					      baseMapInit();




						});

				//过滤重点监测频段
				$('#importantMonitor_filter').on('click', function(e) {
							if (e.target.checked == true) {
								$('#table-radio').bootstrapTable("filterBy", {
											importantMonitor : true
										});
							} else {
								$('#table-radio').bootstrapTable("filterBy");
							}
						});

				// 监听更新时间回车按下事件
				$("#minutes").keyup(function(e) {
							if (e.keyCode == 13) {
								layer.msg("设置更新时间成功！时间：" + e.target.value
										+ "分钟");
								refresh_timer_init(e.target.value);
								$("#minutes").val("");
							}
						});
						
				// 监听更新按钮点击事件
				$("#minutesButton").click(function(){
					var value = $("#minutes").val();
					if(value){
						layer.msg("设置更新时间成功！时间：" +value
						+ "分钟");
						refresh_timer_init(value);
						$("#minutes").val("");
					}
				});
				
				// 监听更新输入框点击事件
				$("#minutes").click(function(){
					layer.tips('默认更新时间为1分钟', '#minutes-li', {
								tips : [1, '#FF5722'],
								time : 2000
							});
				});
				
				// 监测站图标点击进入模态框出现事件
				$("#modalSignalsOnMonitors").on('show.bs.modal', function(e) {
					var a = e.relatedTarget;
					var monitorID = a.monitorid;// data()函数里面要取小写
					var signalType = a.signaltype;
					var isSubType = a.issubtype;
					$('#table-signalsOnMonitors-list').bootstrapTable("destroy");
					$('#table-signalsOnMonitors-list').bootstrapTable({
						method : 'post',
						cache : false,
						contentType : "application/json",
						url : "data/waveorder/SignalsOnMonitors",
						striped : true, // 是否显示行间隔色
						dataField : "data",
						detailView : false,
						pageNumber : 1, // 初始化加载第一页，默认第一页
						pagination : true, // 是否分页
						queryParamsType : 'limit', // 查询参数组织方式
						queryParams : function(params) {
							params.monitorID = monitorID;
							params.signalType = signalType;
							params.isSubType = isSubType;
							return params
						}, // 请求服务器时所传的参数
						sidePagination : 'client', // 指定服务器端分页
						pageSize : 10, // 单页记录数
						pageList : [5, 10, 20, 30], // 分页步进值
						clickToSelect : true, // 是否启用点击选中行
						responseHandler : function(res) {
							return res;
						},
						columns : [{
							class : "sortTable4",
							field : 'centor',
							title : '频率(MHz)' +"<img src='images/arrow-both.png'width='24'/> ",
							titleTooltip : "频率(MHz)",
							sortable : true,
							sortName : "centor",
							width : '15%',
							formatter : function(value, row, index) {
								return '<a class="centerFreqA">' + value
										+ '</a>';
							}
						}, {
							class : "sortTable4",
							field : 'band',
							title : '带宽(kHz)' +"<img src='images/arrow-both.png'width='24'/> ",
							width : '15%',
							titleTooltip : "带宽(kHz)",
							sortable : true
						}, {
							class : "sortTable4",
							visible : false,
							field : 'success_rate',
							title : '监测发射功率' +"<img src='images/arrow-both.png'width='24'/> ",
							width : '18%',
							titleTooltip : "监测发射功率",
							sortable : true
						}, {
							class : "sortTable4",
							field : 'monitorID',
							title : '监测站' +"<img src='images/arrow-both.png'width='24'/> ",
							width : '30%',
							titleTooltip : "监测站",
							sortable : true,
							sortName : "monitorID",
							formatter : function(value, row, index) {
								var monitors = getMonitors(AREACODE);
								var content = "";
									var value1 = [];
									for (var i = 0; i < value.length; i++) {
										for (var j = 0; j < monitors.length; j++) {
											if (value[i] == monitors[j].Num) {
												value1[i] = monitors[j].Name;
												var sub_content = "<div class='popover-item'>"
														+ value1[i] + "</div>";
												content += sub_content;
											}
										}
									}
									return '<div class="dpopover" data-container="body"  data-placement="top"  data-toggle="popover" data-trigger="hover" data-content="'
											+ content + '">' + value1 + '</div>';
							},
							events : {

							}
						}, {
							class : "sortTable4",
							field : 'station',
							title : '发射源' +"<img src='images/arrow-both.png'width='24'/> ",
							width : '30%',
							titleTooltip : "发射源",
							sortable : true,
							sortName : "station",
							formatter : function(value, row, index) {
								value = value == null ? "-" : value;
								return value;
							}
						}, {
							field : 'id',
							visible : false,
							formatter : function(value, row, index) {
								return value;
							}
						}, {
							field : "signalManage",
							formatter : function(value, row, index) {
								return '<a signalId=' + row.id
										+ ' class="signalManageA" centorFreq='
										+ row.centor + '>查看</a>';
							}
						}],
						onLoadSuccess : function() {
							$("#table-signalsOnMonitors-list").find(".dpopover").popover({
										html : true
									});
							$(".sortTable4").on("click",function(e){
							$("th.sortTable4").find('img').attr("src","images/arrow-both.png");
							if($(this).children().attr("class").match("desc")){
								$(this).find('img').attr("src","images/arrow-up.png");
							}else{
								$(this).find('img').attr("src","images/arrow-bottom.png");
							}
						});
						},
						onAll:function(){
							$("#table-signalsOnMonitors-list").find(".dpopover").popover({
								html : true
							});
						}
					});
				})
				
			}
	       //电波秩序总览中，显示每种信号类型的总数
			function getSignalCounts(){
				ajax.post("data/waveorder/statisticsForSingnalsAndWarnings",null,function(result){
					console.log(result)
					$('#alarmTotalCount').text(result.alarmTotalCount);//总告警数
					$('#undealedAlarmCounts').text(result.alarmUnconfiredCount);//未处理告警数
					$('#dealedAlarmCounts').text(result.alarmConfirmedCount);//已处理告警数
					$('#autoConfirmcounts').text(result.signalAutoCount);//智能识别信号数
					$('#signalCounts').text(result.signalCount);//总信号数
				});
			}
	       //获取所有监测站列表信息
			function getstationsInfo(){
				var info = Binding.getUser();
				info = JSON.parse(info);
				var code = info.Area.Code;
				var stationObj = Binding.getMonitorNodes(code);
				//console.log(stationObj);
				stationObj = JSON.parse(stationObj);
				//[{Latitude:30.755066,Longitude:103.98112,Name:"123监测站",Num:"51010123",StationType:"7",StationTypeName:"超短波移动监测车"}]
				var codes = [];
				var stationsName = [];
				var stations = [];
				for (var index = 0; index < stationObj.length; index++) {
					codes.push(stationObj[index].Num);
					stationsName.push(stationObj[index].Name);
					stations.push({"code":stationObj[index].Num,"name":stationObj[index].Name});
				}
				//console.log(stations)
				return {
					codes:codes,
					stationsName:stationsName,
					stations:stations
				}
			}
	       //态势图的渲染
	        function baseMapInit(){
				app = new AppMap('mapDiv2', {
					// center: [104.360, 33.360],
					//maxZoom: 12
					minZoom: 10 //禁止缩放，就把maxZoom 和minZoom弄成一样的，10
				});
				//初始化所有图层
				app.polygonLayer();
				app.situationLayer();
				app.stationsLayer();

				$.get('cache/data/mapdata', function (data) {
					app.polygonLayer({
						data: data
					});
				});
				var codes =getstationsInfo().codes;
				var data = {
					"stationCodes": codes
					//playType:"hour",
					//currentTime:"2018030810"

					//"frequency": centorFreq,
					//"beginTime": beginTime
				};

				ajax.post("data/alarm/getStationVersion2", data, function (result) {
					var stations = result.stationPiont,
						data = result.kriking3.result;
//                    console.log(stations)//[{x:25515.12, y:25326, count: , stationId}]
					// 设置默认
					Array.prototype.max = function() {
						return Math.max.apply({}, this)
					}
					Array.prototype.min = function() {
						return Math.min.apply({}, this)
					}
					if (stations.length) {
						var countArr = [];
						for (var i = 0; i < stations.length; i++) {
							countArr.push(parseInt(stations[i].count));
						}
						document.getElementById("minCtrl").value = countArr.min();
						document.getElementById("maxCtrl").value = countArr.max();
					}
					app.update('situation', {
						data: data,
						opacity: 0.7
					});
					//配置项在stationsLayer的里，透明度是situation层，其他都在stationsLayer
					app.stationsLayer({
						data: stations
					});
					$(".coverage-number").html(result.electrCoverage * 100 + "%");
				});

			}
			
			 //频段名称排序
			function freqNameSorter(a, b) {
				var matcha = a.match(/^[0-9\.\-]+/);
				var matchb = b.match(/^[0-9\.\-]+/);
				a = matcha == null ? "9999" : a.substring(0,a.indexOf("-"));
				b = matchb == null ? "9999" : b.substring(0,b.indexOf("-"));
				return a - b;
			}
			 //频段排序
			function freqRangeSorter(a, b) {
				return a - b;
			}
			
			// 得到区域的边界
			function addAreaBoundary(map) {
				var glayer1 = map.getLayer("glayer1");
				ajax.get("cache/data/mapdata",null,function(result){
                    var sfs = new esri.symbol.SimpleFillSymbol(
									esri.symbol.SimpleFillSymbol.STYLE_SOLID,
									null,
									new dojo.Color([135, 206, 250, 0.3]));
                    var polygon =new esri.geometry.Polygon(result);
                    var Citygraphic = new esri.Graphic(polygon, sfs);
                    glayer1.add(Citygraphic);
                });
			}
			// 表单提交前的验证
			function beforeSubmit(form) {
				if(parseInt(document.importantMonitorForm.audioTimespan.value) > parseInt(document.importantMonitorForm.duration.value)) {
					$("#audioTimespan").focus();
					$("#audioTimespan").val("");
					layer.tips('声音采集时间 不能大于 执行时长 !', '#audioTimespan', {
								tips : [1, '#FF5722'],
								time : 4000
							});
					console.log("111");
					return false;
				}
				
//				var date = new Date();
//				if(date > document.importantMonitorForm.endTime.value) {
//					alert("!");
//					$("#endTime").focus();
//					$("#endTime").val("");
//					layer.tips('结束时间 不能小于 当前系统时间 !', '#endTime', {
//								tips : [1, '#FF5722'],
//								time : 4000
//							});
//					return false;
//				}
				
				if(document.importantMonitorForm.beginTime.value > document.importantMonitorForm.endTime.value) {
					$("#endTime").focus();
					$("#endTime").val("");
					layer.tips('结束时间 不能小于 开始时间 !', '#endTime', {
								tips : [1, '#FF5722'],
								time : 4000
							});
					return false;
				}
				
				/*if(document.importantMonitorForm.IQCount.value > document.importantMonitorForm.totalIQCount.value) {
					$("#totalIQCount").focus();
					$("#totalIQCount").val("");
					layer.tips('采集总数 不能小于 单次采集次数 !', '#totalIQCount', {
								tips : [1, '#FF5722'],
								time : 4000
							});
					return false;
				}
				if(document.importantMonitorForm.specCount.value > document.importantMonitorForm.totalSpecCount.value) {
					$("#totalSpecCount").focus();
					$("#totalSpecCount").val("");
					layer.tips('采集总数 不能小于 单次采集次数 !', '#totalSpecCount', {
								tips : [1, '#FF5722'],
								time : 4000
							});
					return false;
				}
				if(document.importantMonitorForm.featureCount.value > document.importantMonitorForm.totalFeatureCount.value) {
					$("#totalFeatureCount").focus();
					$("#totalFeatureCount").val("");
					layer.tips('采集总数 不能小于 单次采集次数 !', '#totalFeatureCount', {
								tips : [1, '#FF5722'],
								time : 4000
							});
					return false;
				}
				if(document.importantMonitorForm.ITUCount.value > document.importantMonitorForm.totalITUCount.value) {
					$("#totalITUCount").focus();
					$("#totalITUCount").val("");
					layer.tips('采集总数 不能小于 单次采集次数 !', '#totalITUCount', {
								tips : [1, '#FF5722'],
								time : 4000
							});
					return false;
				}*/
				if(parseInt(document.importantMonitorForm.audioTimespan.value) > parseInt(document.importantMonitorForm.totalAudioTimespan.value) && parseInt(document.importantMonitorForm.totalAudioTimespan.value) != -1) {
					$("#totalAudioTimespan").focus();
					$("#totalAudioTimespan").val("");
					layer.tips('采集总数 不能小于 单次采集次数 !', '#totalAudioTimespan', {
								tips : [1, '#FF5722'],
								time : 4000
							});
					return false;
				}
				
				if (!document.importantMonitorForm.beginTime.validity.valid) {
					// document.importantMonitorForm.beginTime.setCustomValidity("用户名不能为空");
					$("#beginTime").focus();
					return false;
				}
				if (!document.importantMonitorForm.endTime.validity.valid) {
					$("#endTime").focus();
					return false
				}
				if (!document.importantMonitorForm.cycleStep.validity.valid) {
					$("#cycleStep").focus();
					return false
				}
				if (!document.importantMonitorForm.duration.validity.valid) {
					$("#duration").focus();
					return false
				}
				/*if (!document.importantMonitorForm.IQCount.validity.valid) {
					$("#IQCount").focus();
					return false
				}
				if (!document.importantMonitorForm.totalIQCount.validity.valid) {
					$("#totalIQCount").focus();
					return false
				}
				if (!document.importantMonitorForm.specCount.validity.valid) {
					$("#specCount").focus();
					return false
				}
				if (!document.importantMonitorForm.totalSpecCount.validity.valid) {
					$("#totalSpecCount").focus();
					return false
				}
				if (!document.importantMonitorForm.featureCount.validity.valid) {
					$("#featureCount").focus();
					return false
				}
				if (!document.importantMonitorForm.totalFeatureCount.validity.valid) {
					$("#totalFeatureCount").focus();
					return false
				}
				if (!document.importantMonitorForm.ITUCount.validity.valid) {
					$("#ITUCount").focus();
					return false
				}
				if (!document.importantMonitorForm.totalITUCount.validity.valid) {
					$("#totalITUCount").focus();
					return false
				}*/
				if (!document.importantMonitorForm.audioTimespan.validity.valid) {
					$("#audioTimespan").focus();
					return false
				}
				if (!document.importantMonitorForm.totalAudioTimespan.validity.valid) {
					$("#totalAudioTimespan").focus();
					return false
				}
				return true
			}

			// 告警数据和信号自动确认 更新定时器初始化
			function refresh_timer_init(minutes) {
				clearInterval(INTERVAL_warning);
				INTERVAL_warning = setInterval(task, 1000 * 60 * minutes);
				function task() {
					$('#table-alarm-undeal').bootstrapTable('refresh', {
								silent : true
							});
					$('#table-alarm-dealed').bootstrapTable('refresh', {
								silent : true
							});
					$('#radio_auto_confirm').bootstrapTable('refresh', {
								silent : true
							});
				}
			}

			// 时间选择器初始化
			function datetimepicker_init() {
				$.fn.datetimepicker.defaults = {
					language : 'zh-CN',
					format : 'yyyy-mm-dd hh:ii:ss',
					autoclose : true,
					minView : 2
					
				}
			}

			// 初始化下拉框
			function selector_init() {
				// 执行下拉框
				$('.select2-picker').select2();
				// 默认选中第一个
				var defaultAreaCode = $("#area_select").select2('val');
				console.log(defaultAreaCode);
				// $('.select2-picker').val(defaultAreaCode); // Change the
				// value or make some change to the internal state
				// $('.select2-picker').trigger('change.select2'); //触发事件
				select2_change(defaultAreaCode);
				// 获取请求参数areaCode
				var areaCode = $("#areaCode").value;
				// 有areaCode,直接触发选择事件
				if (areaCode != null) {
					$('.select2-picker').val(areaCode); // Change the value or
					// make some change to
					// the internal state
					$('.select2-picker').trigger('change.select2'); // 触发事件
					select2_change(areaCode);
				}
			}

			// 区域切换
			function select2_change(areaCode) {

				var have_monitors = $("#area_select").select2("data")[0].element.getAttribute("have_monitors");
				console.log(have_monitors);
				if(have_monitors == "false") {
					layer.alert("该市暂无监测站信息！")
					return null;
				}
				AREACODE = areaCode;// 更新全局变量
				var user = getUser();
				var userID = user.ID;
				var monitors = getMonitors(areaCode);
				MONITORS = monitors;//	更新监测站全局变量
				console.log(monitors);
				var monitorsID = new Array();
				for (var i = 0; i < monitors.length; i++) {
					monitorsID[i] = monitors[i].Num;
				}
				table_radio_init(monitors, userID);
				table_alarm_undealed(monitorsID, monitors);
				table_alarm_dealed(monitorsID, monitors);
				radio_auto_confirm(monitorsID, monitors);
				//改变地图中心
				var center = new Point({
							"x" : MONITORS[0].Longitude,
							"y" : MONITORS[0].Latitude
						});
				MAP1.centerAt(center);
				//改变行政区域边界
				addAreaBoundary(MAP1);
				//改变每个监测站点上的信号总数
				addSignalCountOnMonitors(monitors,1,"false");//默认选中1，子类型为false
				redioType(monitors);
			}

			// 取得用户信息
			function getUser() {
				var userStr = Binding.getUser();
				var user = JSON.parse(userStr);
				return user;
			}

			// 根据用户取得区域信息
			function getArea() {
				var user = getUser();
				if (user.AreaType == "Province") {
					// 显示省级选项
					var province = user.Area;
					//					var option = document.createElement("option");
					//					option.setAttribute("value", province.Code);
					//					option.setAttribute("id", "option");
					//					// option.setAttribute("selected","selected");
					//					// option.innerHTML =
					//					// province_name;//此时就设置innerHTML的话会报错，因为<option>标签还没渲染出来
					//					$("#area_select").append(option);
					//					$("#option").append(province.Name);
					var citys = province.Citys;
					// 显示市级选项
					for (var i = 0; i < citys.length; i++) {
						var option_city = document.createElement("option");
						//每个市级选项判断是否有监测站信息，如果没有，则给每个选项添加个标志位
						var monitors = getMonitors(citys[i].Code);
						var bool = monitors.length == 0 ? false : true;
						//或者直接禁用选项
						if (monitors.length == 0) {
							option_city.setAttribute("disabled", true);
						}
						option_city.setAttribute("have_monitors", bool);
						option_city.setAttribute("value", citys[i].Code);
						option_city.setAttribute("id", "option_city" + i);
						$("#area_select").append(option_city);
						$("#option_city" + i).append(citys[i].Name);
					}
				} else {
					// 市级用户就只有一个市级选项
					var city = user.Area;
					var option_city = document.createElement("option");
					option_city.setAttribute("value", city.Code);
					option_city.setAttribute("id", "option_city");
					$("#area_select").append(option_city);
					$("#option_city").append(city.Name);
				}
			}

			// 根据区域码得到监测站信息
			function getMonitors(areaCode) {
				var monitorsStr = Binding.getMonitorNodes(Number(areaCode));
				var monitors = JSON.parse(monitorsStr);
				return monitors;
			}

			// 根据监测站信息得到信号类型统计
			function redioType(monitors) {
				var data = {};
				data.monitorsNum = [];
				for (var i = 0; i < monitors.length; i++) {
					data.monitorsNum[i] = monitors[i].Num;
				}
				var str = JSON.stringify(data);
				$.ajax({
							url : 'waveorder/redioType',
							type : 'post',
							data : str,// 传输数据
							contentType : 'application/json',// 传输数据类型
							dataType : 'html',// 返回数据类型
							success : function(html) {
								$("#redioType").html(html);
							}
						})

			}
			
			// 向地图上添加监测站图标
			function addMonitors(map,monitors) {
							var glayer = map.getLayer('glayer');
							// 监测站symbol
							var monitorSymbol = new PictureMarkerSymbol({
										"url" : "images/monitor-station-union.png",
										"height" : 24,
										"width" : 24
									});
							for (var i = 0; i < monitors.length; i++) {
								var monitor = {};
								monitor.x = monitors[i].Longitude;
								monitor.y = monitors[i].Latitude;
								monitor.monitorID = monitors[i].Num;
								monitor.monitorName = monitors[i].Name;
								var monitorPoint = new Point(monitor);
								var monitorGraphic = new esri.Graphic(
										monitorPoint, monitorSymbol);// 监测站图
								glayer.add(monitorGraphic);
							}
			}
			
			
			// 	取得每个监测站上的信号总量，并绘出图形
			function addSignalCountOnMonitors(monitors, signalType, isSubType) {
				var map = MAP1;
				var glayer = map.getLayer('glayer');
				glayer.clear();
				addMonitors(map,monitors);
				var data = {};
				data.monitorsNum = [];
				data.signalType = signalType;
				data.monitors = monitors;
				data.isSubType = isSubType;
				for (var i = 0; i < monitors.length; i++) {
					data.monitorsNum[i] = monitors[i].Num;
				}
				// 信号统计背景url
				var url_countBackgroundSymbol = null;
				if(isSubType == "true") {
					switch (signalType) {
					case 1:
						url_countBackgroundSymbol = "images/undeclared.svg";
						break;
					default:
						break;
					}
				}else {
					switch (signalType) {
					case 1:
						url_countBackgroundSymbol = "images/legal.svg";
						break;
					case 2:
						url_countBackgroundSymbol = "images/known.svg";
						break;
					case 3:
						url_countBackgroundSymbol = "images/illegal.svg";
						break;
					case 4:
						url_countBackgroundSymbol = "images/unknown.svg";
						break;
					default:
						break;
					}
				}
				//信号统计背景Symbol
				var countBackgroundSymbol = new PictureMarkerSymbol({
					"url" : url_countBackgroundSymbol,
					"height" : 18,
					"width" : 20,
					"xoffset" : 16,
					"yoffset" : 14
				});
				ajax.post("data/waveorder/SignalCountOnMonitors", data,function(result) {
									console.log(result);
									for (var i = 0; i < result.length; i++) {
										var monitorPoint = new Point(result[i]);
										var countSymbol = new TextSymbol(String(monitorPoint.count))
												.setOffset(21,15) 
												.setColor(
														new esri.Color([ 0xff,
																0xff, 0xff ]))
												.setAlign(Font.ALIGN_START)
												.setFont(
														new Font()
																.setSize("10pt")
																.setFamily(
																		" .PingFangSC-Medium"));

										var countGraphic = new esri.Graphic(
												monitorPoint,countSymbol);// 计数图
										var countBackgroundGraphic = new esri.Graphic(
												monitorPoint,
												countBackgroundSymbol);// 计数底图
										glayer.add(countBackgroundGraphic);
										glayer.add(countGraphic);
									}
						});
			}

//			 根据监测站列表，信号类型绘出监测站点
//			function addPoint(monitors, signalType, isSubType) {
//				var map = MAP1;
//				var glayer = map.getLayer('glayer');
//				glayer.clear();
//				addAreaBoundary(map);
//				var data = {};
//				data.monitorsNum = [];
//				data.signalType = signalType;
//				data.monitors = monitors;
//				data.isSubType = isSubType;
//				for (var i = 0; i < monitors.length; i++) {
//					data.monitorsNum[i] = monitors[i].Num;
//				}
//				// 监测站symbol
//				var monitorSymbol = new PictureMarkerSymbol({
//							"url" : "images/monitor-station-union.png",
//							"height" : 24,
//							"width" : 24
//						});
//				// 计数点symbol
//				var url_countBackgroundSymbol = null;
//				if(isSubType == "true") {
//					switch (signalType) {
//					case 1:
//						url_countBackgroundSymbol = "images/undeclared.svg";
//						break;
//					default:
//						break;
//					}
//				}else {
//					switch (signalType) {
//					case 1:
//						url_countBackgroundSymbol = "images/legal.svg";
//						break;
//					case 2:
//						url_countBackgroundSymbol = "images/known.svg";
//						break;
//					case 3:
//						url_countBackgroundSymbol = "images/illegal.svg";
//						break;
//					case 4:
//						url_countBackgroundSymbol = "images/unknown.svg";
//						break;
//					default:
//						break;
//					}
//				}
//				var countBackgroundSymbol = new PictureMarkerSymbol({
//					"url" : url_countBackgroundSymbol,
//					"height" : 18,
//					"width" : 34,
//					"xoffset" : 17,
//					"yoffset" : 15
//				});
//				ajax.post("data/waveorder/monitorsPoint", data,function(result) {
//									console.log(result);
//									for (var i = 0; i < result.length; i++) {
//										var monitorPoint = new Point(result[i]);
//										var countSymbol = new TextSymbol(String(monitorPoint.count))
//												.setOffset(22,15) 
//												.setColor(
//														new esri.Color([ 0xff,
//																0xff, 0xff ]))
//												.setAlign(Font.ALIGN_START)
//												.setFont(
//														new Font()
//																.setSize("12pt")
//																.setFamily(
//																		" .PingFangSC-Medium"));
//
//										var countGraphic = new esri.Graphic(
//												monitorPoint,countSymbol);// 计数图
//										var countBackgroundGraphic = new esri.Graphic(
//												monitorPoint,
//												countBackgroundSymbol);// 计数底图
//										var monitorGraphic = new esri.Graphic(
//												monitorPoint, monitorSymbol);// 监测站图
//										glayer.add(monitorGraphic);
//										glayer.add(countBackgroundGraphic);
//										glayer.add(countGraphic);
//									}
//						});
//			}

			//下方地图初始化
			function mapInit() {

				var mapUrl = Binding.getMapUrl();
				var map = new Map("mapDiv1", {
							logo : false,
							maxZoom : 16,
							zoom : 8
						});

				var agoLayer = new ArcGISTiledMapServiceLayer(mapUrl, {
                    		showAttribution:false
						});
				var glayer = new GraphicsLayer({
					id : "glayer"
				});
				var glayer1 = new GraphicsLayer({
					id : "glayer1"
				});
				map.addLayer(agoLayer);
				map.addLayer(glayer1);//行政区域边界图层
				map.addLayer(glayer);//监测站图层
				//监测站图标点击事件aaa
							dialog = new TooltipDialog({
								id : "tooltipDialog",
								style : "position: absolute; width: 250px; font: normal normal normal 10pt Helvetica;z-index:100"
							});
							dialog.startup();
							glayer.on("mouse-over", function(e) {
								var info = e.graphic.geometry;
								var t = "<b>"+ info.monitorName+"</b><hr style='margin-top: 8px;margin-bottom: 8px;'>"
										+"<b>ID: </b>"+ info.monitorID +"<br>"
										+ "<b>纬度: </b>"+ info.y +"<br>"
										+ "<b>经度: </b>"+ info.x +"<br>"

								var content = esriLang.substitute(
									e.graphic.attributes, t);
								dialog.setContent(content);
								dijitPopup.open({
									popup : dialog,
									x : e.pageX,
									y : e.pageY
								});
							});
							glayer.on("mouse-out", function(e) {
								dijitPopup.close(dialog);
							});
							//监测站图标点击进入信号统计模态框事件
							glayer.on("click", function(e) {
								console.log(e);
								var info = e.graphic.geometry;
								if(typeof info.signalType == "undefined") return;
								// 触发模态框,传入监测站id,信号类型，是否子类型，返回监测站所监测到的所有信号
								$("#modalSignalsOnMonitors").modal("show", {
										monitorid : info.monitorID,
										signaltype : info.signalType,
										issubtype : info.isSubType
								});
							})
							
				return map;
			}

			// 告警处理页面
			function table_alarm_dealed(monitorsID, monitors) {
				$('#table-alarm-dealed').bootstrapTable("destroy");
				$('#table-alarm-dealed').bootstrapTable({
					//height : 630,
					method : 'post',
					contentType : "application/json", // 必须要有！！！！
					url : "data/waveorder/alarmdealed", // 要请求数据的文件路径
					striped : true, // 是否显示行间隔色
					dataField : "data",
					detailView : false,
					pageNumber : 1, // 初始化加载第一页，默认第一页
					pagination : true, // 是否分页
					queryParamsType : 'limit', // 查询参数组织方式
					queryParams : function(params) {
						params.monitorsID = monitorsID;
						return params
					}, // 请求服务器时所传的参数
					sidePagination : 'client', // 指定服务器端分页
					pageSize : 15, // 单页记录数
					pageList : [15, 30, 50, 100], // 分页步进值,
					sortName: "radio",
					sortable: true,
					sortOrder: "asc",
					clickToSelect : true, // 是否启用点击选中行
					//					showRefresh : true,
					responseHandler : function(res) {
						return res;
					},
					columns : [
							{
								align : 'center',
								width : '4%',
								title: '序号',
								formatter : function(value,row,index) {
									return index + 1;
								}
							}, {
								class : 'sortTable2',
							    align : 'center',
								field : 'radio',
								width : '10%',
								title : '频率(MHz)'+"<img src='images/arrow-up.png'width='24'/> ",
								sortable : true,
								sortName : "radio",
								titleTooltip : "频率(MHz)",
								formatter : function(value, row, index) {
									return '<a class="centerFreqA">' + value
											+ '</a>';
								}
							}, {
								class : 'sortTable2',
							    align : 'left',
								field : 'firstTime',
								width : '15%',
								title : '首次出现时间'+"<img src='images/arrow-both.png'width='24'/> ",
								titleTooltip : "首次出现时间",
								sortable : true
							}, {
								class : 'sortTable2',
							    align : 'left',
								field : 'lastingTime',
								width : '15%',
								title : '最后出现时间'+"<img src='images/arrow-both.png'width='24'/> ",
								titleTooltip : "最后出现时间",
								sortable : true
							}, {
								class : 'sortTable2',
								field : 'stationID',
								width : '15%',
								title : '监测站'+"<img src='images/arrow-both.png'width='24'/> ",
								titleTooltip : "监测站",
								sortable : true,
								sortName : "stationID",
								formatter : function(value, row, index) {
									var content = "";
									var value1 = [];
									for (var i = 0; i < value.length; i++) {
										for (var j = 0; j < monitors.length; j++) {
											if (value[i] == monitors[j].Num) {
												value1[i] = monitors[j].Name;
												var sub_content = "<div class='popover-item'>"
														+ value1[i] + "</div>";
												content += sub_content;
											}
										}
									}
									return '<div class="dpopover" data-container="body"  data-placement="top"  data-toggle="popover" data-trigger="hover" data-content="'
											+ content + '">' + value1 + '</div>';
								}
							}, {
								field : 'status',
								width : '10%',
								title : '状态',
								titleTooltip : "状态",
								formatter : function(value, row, index) {
									return '已确认';
								}
							}, {
								field : 'keyword',
								title : '告警内容',
								titleTooltip : "告警内容",
								width : '10%',
								formatter : function(value, row, index) {
									return value;
								}
							},{
								field : 'mark',
								width : '10%',
								title : '描述',
								titleTooltip : "描述",
								formatter : function(value, row, index) {
									value = value == null ? "-" : value;
									return '<div class="dpopover" data-container="body" data-placement="top"  data-toggle="popover" data-trigger="hover" data-content="'
											+ value + '">' + value + '</div>';
								}

							}],
					onLoadSuccess : function() {
						$("#table-alarm-dealed").find(".dpopover").popover({
									html : true
								});
						$(".table-alarm-dealed").find(".pull-right .pagination").append("<span class='goPage'>跳转到第<input id='alarmDealedPageBtn' class='pageNum' type='text'>页</span>")
						$("#alarmDealedPageBtn").on("blur",function(e){
							$("#table-alarm-dealed").bootstrapTable("selectPage",parseInt($("#alarmDealedPageBtn").val()));
						})
					},
					onPageChange:function(){
						$(".table-alarm-dealed").find(".pull-right .pagination").append("<span class='goPage'>跳转到第<input id='alarmDealedPageBtn' class='pageNum' type='text'>页</span>")
						$("#alarmDealedPageBtn").on("blur",function(e){
							$("#table-alarm-dealed").bootstrapTable("selectPage",parseInt($("#alarmDealedPageBtn").val()));
						})
					},
					onAll:function(){
						$("#table-alarm-dealed").find(".dpopover").popover({
							html : true
						});
						$(".sortTable2").on("click",function(e){
							$("th.sortTable2").find('img').attr("src","images/arrow-both.png");
							if($(this).children().attr("class").match("desc")){
								$(this).find('img').attr("src","images/arrow-up.png");
							}else{
								$(this).find('img').attr("src","images/arrow-bottom.png");
							}
						});
					}
				});
			}

			// 告警未处理页面
			function table_alarm_undealed(monitorsID, monitors) {
				$('#table-alarm-undeal').bootstrapTable("destroy");
				var option = {
					//height : 630,
					method : 'post',
					contentType : "application/json", // 必须要有！！！！
					url : "data/waveorder/alarmundealed", // 要请求数据的文件路径
					striped : true, // 是否显示行间隔色
					dataField : "data",
					sidePagination : 'client',
					detailView : false,
					pageNumber : 1, // 初始化加载第一页，默认第一页
					pagination : true, // 是否分页
					queryParamsType : 'limit', // 查询参数组织方式
					queryParams : function(params) {
						params.monitorsID = monitorsID;
						return params
					}, // 请求服务器时所传的参数
					pageSize : 15, // 单页记录数
					pageList : [15, 30, 50, 100],
					sortName: "radio",
					sortable: true,
					sortOrder: "asc",
					clickToSelect : true, // 是否启用点击选中行
					//					showRefresh : true,
					responseHandler : function(res) {
						return res;
					},
					columns : [
							{
								align : 'center',
								width : '4%',
								title: '序号',
								formatter : function(value,row,index) {
									return index + 1;
								}
							},{
								class : 'sortTable2',
							    align : 'center',
								field : 'radio',
								title : '频率(MHz)'+"<img src='images/arrow-up.png'width='24'/> ",
								width : '10%',
								sortable : true,
								sortName : "radio",
								titleTooltip : "频率(MHz)",
								formatter : function(value, row, index) {
									return '<a class="centerFreqA">' + value
											+ '</a>';
								}
							}, {
								class : 'sortTable2',
							    align : 'left',
								field : 'firstTime',
								width : '15%',
								title : '首次出现时间'+"<img src='images/arrow-both.png'width='24'/> ",
								sortable : true,
								titleTooltip : "首次出现时间"
							}, {
								class : 'sortTable2',
							    align : 'left',
								field : 'lastingTime',
								width : '15%',
								title : '最后出现时间'+"<img src='images/arrow-both.png'width='24'/> ",
								sortable : true,
								titleTooltip : "最后出现时间"
							}, {
								class : 'sortTable2',
								field : 'stationID',
								title : '监测站'+"<img src='images/arrow-both.png'width='24'/> ",
								width : '15%',
								sortable : true,
								sortName : "stationID",
								titleTooltip : "监测站",
								formatter : function(value, row, index) {
									var content = "";
									var value1 = [];
									for (var i = 0; i < value.length; i++) {
										for (var j = 0; j < monitors.length; j++) {
											if (value[i] == monitors[j].Num) {
												value1[i] = monitors[j].Name;
												var sub_content = "<div class='popover-item'>"
														+ value1[i] + "</div>";
												content += sub_content;
											}
										}
									}
									return '<div class="dpopover" data-container="body"  data-placement="top"  data-toggle="popover" data-trigger="hover" data-content="'
											+ content + '">' + value1 + '</div>';
								}
							}, {
								field : 'status',
								width : '10%',
								title : '状态',
								titleTooltip : "状态",
								formatter : function(value, row, index) {
									return "<a  freqId="+ row.id +" class='alarmManageA' centorFreq= " + row.radio + ">未确认</a> ";
								}
							}, {
								field : 'id',
								visible : false,
								formatter : function(value, row, index) {
									return value;
								}
							}, {
								field : 'keyword',
								width : '10%',
								title : '告警内容',
								titleTooltip : "告警内容",
								formatter : function(value, row, index) {
									return value;
								}
							},{
								field : 'mark',
								width : '10%',
								title : '描述',
								titleTooltip : "描述",
								formatter : function(value, row, index) {
									value = value == null ? "-" : value;
									return '<div class="dpopover"  data-container="body" data-placement="top"  data-toggle="popover" data-trigger="hover" data-content="'
											+ value + '">' + value + '</div>';
								}
							}],
					onLoadSuccess : function() {
						//$("#table-alarm-undeal").find(".dpopover").popover({
						//			html : true
						//		});
						$(".table-alarm-undeal").find(".pull-right .pagination").append("<span class='goPage'>跳转到第<input id='alarmUndealPageBtn' class='pageNum' type='text'>页</span>")
						$("#alarmUndealPageBtn").on("blur",function(e){
							$("#table-alarm-undeal").bootstrapTable("selectPage",parseInt($("#alarmUndealPageBtn").val()));
						})
					},
					onPageChange:function(){
						$(".table-alarm-undeal").find(".pull-right .pagination").append("<span class='goPage'>跳转到第<input id='PageBtn' class='pageNum' type='text'>页</span>")
						$("#alarmUndealPageBtn").on("blur",function(e){
							$("#table-alarm-undeal").bootstrapTable("selectPage",parseInt($("#alarmUndealPageBtn").val()));
						})
					},
					onAll:function(){
						$("#table-alarm-undeal").find(".dpopover").popover({
							html : true
						});
						$(".sortTable2").on("click",function(e){
							$("th.sortTable2").find('img').attr("src","images/arrow-both.png");
							if($(this).children().attr("class").match("desc")){
								$(this).find('img').attr("src","images/arrow-up.png");
							}else{
								$(this).find('img').attr("src","images/arrow-bottom.png");
							}
						});
					}
				};

				$('#table-alarm-undeal').bootstrapTable(option)
			}
			
			// 告警自动确认成信号页面
			function radio_auto_confirm(monitorsID, monitors) {
				$('#radio_auto_confirm').bootstrapTable("destroy");
				var option = {
					method : 'post',
					contentType : "application/json", 
					url : "data/waveorder/radioAutoConfirm", // 要请求数据的文件路径
					striped : true, // 是否显示行间隔色
					dataField : "data",
					sidePagination : 'client',
					detailView : false,
					pageNumber : 1, // 初始化加载第一页，默认第一页
					pagination : true, // 是否分页
					queryParamsType : 'limit', // 查询参数组织方式
					queryParams : function(params) {
						params.monitorsID = monitorsID;
						return params
					}, // 请求服务器时所传的参数
					pageSize : 15, // 单页记录数
					pageList : [15, 30, 50, 100], // 分页步进值
					sortName: "centor",
					sortable: true,
					sortOrder: "asc",
					clickToSelect : true, // 是否启用点击选中行
					//					showRefresh : true,
					responseHandler : function(res) {
						return res;
					},
					columns : [

						{
							align : 'center',
							width : '4%',
							title: '序号',
							formatter : function(value,row,index) {
								return index + 1;
							}
						},{
							class : 'sortTable2',
							align: 'center',
							field : 'centor',
							title : '频率(MHz)'+"<img src='images/arrow-up.png'width='24'/> ",
							titleTooltip : "频率(MHz)",
							sortable : true,
							sortName : "centor",
							width : '10%',
							formatter : function(value, row, index) {
								return '<a class="centerFreqA">' + value
										+ '</a>';
							}
							
						}, {
							class : 'sortTable2',
							align: 'center',
							field : 'band',
							title : '带宽(kHz)'+"<img src='images/arrow-both.png'width='24'/> ",
							width : '15%',
							titleTooltip : "带宽(kHz)",
							sortable : true
						}, {
							class : 'sortTable2',
							visible : false,
							align: 'center',
							field : 'success_rate',
							title : '监测发射功率'+"<img src='images/arrow-both.png'width='24'/> ",
							width : '18%',
							titleTooltip : "监测发射功率",
							sortable : true
						}, {
							class : 'sortTable2',
							align: 'center',
							field : 'monitorID',
							title : '监测站'+"<img src='images/arrow-both.png'width='24'/> ",
							width : '30%',
							titleTooltip : "监测站",
							sortable : true,
							sortName : "monitorID",
							formatter : function(value, row, index) {
								var monitors = getMonitors(AREACODE);
								var content = "";
									var value1 = [];
									for (var i = 0; i < value.length; i++) {
										for (var j = 0; j < monitors.length; j++) {
											if (value[i] == monitors[j].Num) {
												value1[i] = monitors[j].Name;
												var sub_content = "<div class='popover-item'>"
														+ value1[i] + "</div>";
												content += sub_content;
											}
										}
									}
									return '<div class="dpopover" data-container="body"  data-placement="top"  data-toggle="popover" data-trigger="hover" data-content="'
											+ content + '">' + value1 + '</div>';
							},
							events : {

							}
						}, {
							class : 'sortTable2',
							align: 'center',
							field : 'station',
							title : '发射源'+"<img src='images/arrow-both.png'width='24'/> ",
							width : '30%',
							titleTooltip : "发射源",
							sortable : true,
							sortName : "station",
							formatter : function(value, row, index) {
								value = value == null ? "-" : value;
								return value;
							}
						}, {
							field : 'id',
							visible : false,
							formatter : function(value, row, index) {
								return value;
							}
						}, {
							align: 'center',
							field : "signalManage",
							formatter : function(value, row, index) {
								return '<a signalId=' + row.id
										+ ' class="signalManageA" centorFreq='
										+ row.centor + '>查看</a>';
							}
						}],
					onLoadSuccess : function() {
						$(".radio_auto_confirm").find(".pull-right .pagination").append("<span class='goPage'>跳转到第<input id='autoConfirmPageBtn' class='pageNum' type='text'>页</span>")
						$("#autoConfirmPageBtn").on("blur",function(e){
							$("#radio_auto_confirm").bootstrapTable("selectPage",parseInt($("#autoConfirmPageBtn").val()));
						})
					},
					onPageChange:function(){
						$(".radio_auto_confirm").find(".pull-right .pagination").append("<span class='goPage'>跳转到第<input id='autoConfirmPageBtn' class='pageNum' type='text'>页</span>")
						$("#autoConfirmPageBtn").on("blur",function(e){
							$("#radio_auto_confirm").bootstrapTable("selectPage",parseInt($("#autoConfirmPageBtn").val()));
						})
					},
					onAll:function(){
						$("#radio_auto_confirm").find(".dpopover").popover({
							html : true
						});
							$(".sortTable2").on("click",function(e){
							$("th.sortTable2").find('img').attr("src","images/arrow-both.png");
							if($(this).children().attr("class").match("desc")){
								$(this).find('img').attr("src","images/arrow-up.png");
							}else{
								$(this).find('img').attr("src","images/arrow-bottom.png");
							}
						});
					}
				};
				$('#radio_auto_confirm').bootstrapTable(option);
			}

			// 信号统计表格
			function table_radio_init(monitors, userID) {
				var monitorsID = new Array();
				for (var i = 0; i < monitors.length; i++) {
					monitorsID[i] = monitors[i].Num;
				}
				$('#table-radio').bootstrapTable("destroy");
				$('#table-radio').bootstrapTable({
//					height : 300,
					method : 'post',
					contentType : "application/json",
					url : "data/waveorder/rediostatus", // 要请求数据的文件路径
					striped : true, // 是否显示行间隔色
					dataField : "data",
					detailView : false,
					sidePagination : 'client',
					pageNumber : 1, // 初始化加载第一页，默认第一页
					pagination : true, // 是否分页
					queryParamsType : 'limit', // 查询参数组织方式
					data_local: "zh-US",//表格汉化
					queryParams : function(params) {
						params.monitorsID = monitorsID;
						params.userID = userID;
						//params.offset,//从数据库第几条记录开始
						//params.limit//找多少条
						return params
					},
					pageSize : 5, // 单页记录数
					pageList : [5, 10, 20, 30], // 分页步进值
					clickToSelect : true, // 是否启用点击选中行
					sortName: "beginFreq",
					sortable: true,
					sortOrder: "asc",
					responseHandler : function(res) {
						return res;
					},
					onLoadSuccess : function() {
						$("#table-radio").find(".dpopover").popover({
									html : true
								});
						$(".sortTable1").on("click",function(e){
							$("th.sortTable1").find('img').attr("src","images/arrow-both.png");
							if($(this).children().attr("class").match("desc")){
								$(this).find('img').attr("src","images/arrow-up.png");
							}else{
								$(this).find('img').attr("src","images/arrow-bottom.png");
							}
						});
						$(".table-radio").find(".pull-right .pagination").append("<span class='goPage'>跳转到第<input id='PageBtn' class='pageNum' type='text'>页</span>")
						//$(".table-radio").find(".pull-right.pagination").append("<input type='button' value='go' class='pageBtn'>")
						$("#PageBtn").on("blur",function(e){
							$("#table-radio").bootstrapTable("selectPage",parseInt($("#PageBtn").val()));
						})

					},
					onPageChange:function(){
						$(".table-radio").find(".pull-right .pagination").append("<span class='goPage'>跳转到第<input id='PageBtn' class='pageNum' type='text'>页</span>")
						//$(".table-radio").find(".pull-right.pagination").append("<input type='button' value='go' class='pageBtn'>")
						$("#PageBtn").on("blur",function(e){
							$("#table-radio").bootstrapTable("selectPage",parseInt($("#PageBtn").val()));
						})
					},
					onAll:function(){
						$("#table-radio").find(".dpopover").popover({
							html : true
						});

					},
					columns : [{
						align : 'center',
						title: '序号',
						formatter : function(value,row,index) {
							return index + 1;
						}
					},{
						class : "sortTable1",
						field : 'beginFreq',
						sortable : true,
						sortName : "beginFreq",
						align : 'left',
						title : "频段范围"+"<img src='images/arrow-up.png'width='24'/> ",
						width : '10%',
						titleTooltip : '频段范围',
						formatter : function(value,row,index) {
									var divide = 1000000;
									var beginFreq = Math.round(value / divide * 1000) / 1000; 
									var endFreq = Math.round(row.endFreq / divide * 1000) / 1000; 
							return beginFreq + "-" + endFreq;
						}
					},{
						class : "sortTable1",
						align : 'left',
						field : 'redioName',
						title : '频段名称'+"<img src='images/arrow-both.png'width='24'/> " ,
						width : '14%',
						titleTooltip : '频段名称',
						sortable : true,
						sortName : "redioName",
						formatter : function(value, row, index) {
							return '<div class="dpopover" data-placement="top"  data-toggle="popover" data-trigger="hover" data-content="'
									+ value +'"> <a class="redioNameA">'+ value + '</a> </div>';
						}
					},
						{
						class : "sortTable1",
							align : "center",
						field : 'legalNormalStationNumber',
						title : '告警数量'+"<img src='images/arrow-both.png'width='24'/> " ,
						width : '10%',
						titleTooltip : '告警数量',
						sortable : true,
						sortName : "legalNormalStationNumber",
						formatter : function(value, row, index) {
							return '<a data-toggle="modal" data-target="#modalSignal" data-radioType="1" data-isSubType="false" data-beginFreq="'
								+ row.beginFreq
								+ '" data-endFreq="'
								+ row.endFreq + '">' + value + '</a>';
						}

					},
						{
						class : "sortTable1",
						field : 'legalNormalStationNumber',
						title : '合法正常信号'+"<img src='images/arrow-both.png'width='24'/> " ,
						width : '10%',
						titleTooltip : '合法正常信号',
						sortable : true,
						sortName : "legalNormalStationNumber",
						align : "center",
						footerFormatter : function(data) {
							console.log(data);
							var sum = 0;
							for(var i=0;i<data.length;i++) {
								sum += data[i].legalNormalStationNumber;
							}
							return sum;
						},
						formatter : function(value, row, index) {
							return '<a data-toggle="modal" data-target="#modalSignal" data-radioType="1" data-isSubType="false" data-beginFreq="'
									+ row.beginFreq
									+ '" data-endFreq="'
									+ row.endFreq + '">' + value + '</a>';
						}
					}, {
						class : "sortTable1",
						field : 'legalUnNormalStationNumber',
						title : '合法违规信号'+"<img src='images/arrow-both.png'width='24'/> " ,
						width : '10%',
						titleTooltip : '合法违规信号',
						sortable : true,
						sortName : "legalUnNormalStationNumber",
						footerFormatter : function(data) {
							console.log(data);
							var sum = 0;
							for(var i=0;i<data.length;i++) {
								sum += data[i].legalUnNormalStationNumber;
							}
							return sum;
						},
						formatter : function(value, row, index) {
							return '<a data-toggle="modal" data-target="#modalSignal" data-radioType="1" data-isSubType="true" data-beginFreq="'
									+ row.beginFreq
									+ '" data-endFreq="'
									+ row.endFreq + '">' + value + '</a>';
						}
					}, {
						class : "sortTable1",
						field : 'konwStationNumber',
						title : '已知信号'+"<img src='images/arrow-both.png'width='24'/> " ,
						width : '10%',
						titleTooltip : '已知信号',
						sortable : true,
						sortName : "konwStationNumber",
						footerFormatter : function(data) {
							console.log(data);
							var sum = 0;
							for(var i=0;i<data.length;i++) {
								sum += data[i].konwStationNumber;
							}
							return sum;
						},
						formatter : function(value, row, index) {
							return '<a data-toggle="modal" data-target="#modalSignal" data-radioType="2" data-isSubType="false" data-beginFreq="'
									+ row.beginFreq
									+ '" data-endFreq="'
									+ row.endFreq + '">' + value + '</a>';
						}
					}, {
						class : "sortTable1",
						field : 'illegalSignal',
						title : '非法信号'+"<img src='images/arrow-both.png'width='24'/> " ,
						width : '10%',
						titleTooltip : '非法信号',
						sortable : true,
						sortName : "illegalSignal",
						footerFormatter : function(data) {
							console.log(data);
							var sum = 0;
							for(var i=0;i<data.length;i++) {
								sum += data[i].illegalSignal;
							}
							return sum;
						},
						formatter : function(value, row, index) {
							return '<a data-toggle="modal" data-target="#modalSignal" data-radioType="3" data-isSubType="false" data-beginFreq="'
									+ row.beginFreq
									+ '" data-endFreq="'
									+ row.endFreq + '">' + value + '</a>';
						}
					}, {
						class : "sortTable1",
						field : 'unKonw',
						title : '不明信号'+"<img src='images/arrow-both.png'width='24'/> " ,
						width : '10%',
						titleTooltip : '不明信号',
						sortable : true,
						sortName : "unKonw",
						footerFormatter : function(data) {
							console.log(data);
							var sum = 0;
							for(var i=0;i<data.length;i++) {
								sum += data[i].unKonw;
							}
							return sum;
						},
						formatter : function(value, row, index) {
							return '<a data-toggle="modal" data-target="#modalSignal" data-radioType="4" data-isSubType="false" data-beginFreq="'
									+ row.beginFreq
									+ '" data-endFreq="'
									+ row.endFreq + '">' + value + '</a>';
						}
					}, {
						title : '<input type="checkbox" id="importantMonitor_filter">',
						align : 'right',
						width : "2%",
						formatter : function(value, row, index) {
							return "";
						}
					}, {
						class : "sortTable1",
						field : 'importantMonitor',
						title : '重点监测'+"<img src='images/arrow-both.png'width='24'/> " ,
								//+ '<input type="checkbox" id="importantMonitor_filter">',
						width : '10%',
						titleTooltip : '重点监测',
						align : 'left',
						sortable : true,
						sortName : "importantMonitor",
						formatter : function(value, row, index) {
							if (value == true) {
								return '<a data-toggle="modal" data-target="#modalConfig" data-beginFreq="'
										+ row.beginFreq
										+ '" data-endFreq="'
										+ row.endFreq
										/*+ '"><em class="icon-peizhi"></em></a>';*/
										+ '"> <img src="images/Group 15.png" width="20"></a>';
							} else {
								return '<a data-toggle="modal" data-target="#modalConfig" data-beginFreq="'
										+ row.beginFreq
										+ '" data-endFreq="'
										+ row.endFreq
										/*+ '"> <em class="icon-peizhi"></em></a>';*/
										+ '"> <img src="images/Fill 30.png" width="20"/></a>';
							}
						}
					}]
				});
			}

			return {
				init : wo_init
			}
		})