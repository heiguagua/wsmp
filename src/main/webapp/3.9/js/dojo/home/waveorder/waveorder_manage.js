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
								//pickerOptions2: {
								//	shortcuts: [{
								//		text: '最近一周',
								//		onClick:function(picker) {
								//			const end = new Date();
								//			const start = new Date();
								//			start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
								//			picker.$emit('pick', [start, end]);
								//		}
								//	}, {
								//		text: '最近一个月',
								//		onClick:function(picker) {
								//			const end = new Date();
								//			const start = new Date();
								//			start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
								//			picker.$emit('pick', [start, end]);
								//		}
								//	}, {
								//		text: '最近三个月',
								//		onClick:function(picker) {
								//			const end = new Date();
								//			const start = new Date();
								//			start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
								//			picker.$emit('pick', [start, end]);
								//		}
								//	}]
								//},
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
								count: 0,
								currentTimeProgress:'',
								sliderProgress:1,
								step:100/30,//步长
								//配置项
								//showOrHide:true,
								//showOrHideClass: { //显示还是弹出表单
								//	display:"none"
								//},
								//normalparamform: {
								//	colorMinValue:-75,//颜色最小值
								//	colorMaxValue:74,//颜色最大值
								//	opercityValue: 30,//透明度 ，0为不透明，100为完全透明
								//	morStationisShow:true,//监测站显示或者隐藏
								//	morStationIcon:'图标显示方式' ,//站点图标显示方式
								//	type:['显示监测站能量值','只显示超过门限的监测站'],//显示监测站能量值,只显示超过门限的监测站
                                //
								//	//插值设置
								//	isShowNoValueMorStation:false ,//是否显示未插值站
								//	morStationListType:['移动'] ,//监测站列表类型 移动/固定
								//	morStationAllList:getstationsInfo().stations,//所有的监测站列表
								//	//morStationList:["123监测站","川大花园监测站1","龙泉山监测站"] //监测站列表
								//	morStationList:["51010123","51010026","51010020"] //监测站列表
								//},
                                //
								////业务设置 设置电平门限，时间段，城市
								//dialogTableVisible:false,
								//form: {
								//	maxValue:12,
								//	time: [],
								//	region: ''
								//},
								//formLabelWidth: '120px',
								//告警信息列表,系统配置频段
								searchFre:0,
								searchBrand:'',
								filterText: ''
								//showOrHideBrandInfo: false,//显示还是隐藏频段信息
								//data2: [{
								//	id: 1,
								//	label: '一级 1',
								//	children: [{
								//		id: 4,
								//		label: '二级 1-1',
								//		children: [{
								//			id: 9,
								//			label: '三级 1-1-1'
								//		}, {
								//			id: 10,
								//			label: '三级 1-1-2'
								//		}]
								//	}]
								//}, {
								//	id: 2,
								//	label: '一级 2',
								//	children: [{
								//		id: 5,
								//		label: '二级 2-1'
								//	}, {
								//		id: 6,
								//		label: '二级 2-2'
								//	}]
								//}, {
								//	id: 3,
								//	label: '一级 3',
								//	children: [{
								//		id: 7,
								//		label: '二级 3-1'
								//	}, {
								//		id: 8,
								//		label: '二级 3-2'
								//	}]
								//}],
								//defaultProps: {
								//	children: 'children',
								//	label: 'label'
								//}


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
								//console.log(vm.endTime-vm.startTime)
								if((vm.endTime-vm.startTime)<0){
									vm.endTime ='';
									layer.msg("结束时间不能小于开始时间")
								}else{
									this.autoPlay();
								}

							},
							//改变选择播放帧；按原始数据，按小时，按天,按月
							selectChange: function(){
								//console.log(vm.playType)
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
								//console.log(vm.sliderProgress,vm.step);
								vm.count =vm.sliderProgress/vm.step;
								this.autoPlay();
							},
							//自动播放，自动修改进度条值，即修改某帧的值，自动调用态势图的接口数据
							autoPlay:function(){

								var int = setInterval(function(){
									if(vm.playType=='hour'){
										//默认设置为近一天
										var hours =(vm.endTime-vm.startTime)/(3600*1000);
										//hours =24;
										//console.log("按小时/帧播放，共："+hours+"小时")
										if(vm.count>hours-1){
											clearInterval(int);
											vm.sliderProgress=0;
											vm.currentTimeProgress =(moment(vm.startTime)).format("YYYY-MM-DD HH");
											vm.count= 0;
											return;
										}
										//console.log(vm.count)
										vm.step=100/hours; //步长
										vm.sliderProgress =vm.sliderProgress+vm.step;
										//var dd =(vm.endTime-vm.startTime)/hours;
										vm.currentTimeProgress =moment(moment(vm.startTime)+3600*1000*vm.count).format("YYYY-MM-DD HH");
										//console.log(moment(vm.currentTimeProgress).format("YYYYMMDDHH"));
										updateMap(moment(vm.currentTimeProgress).format("YYYYMMDDHH"));
										vm.count++;
									}else if(vm.playType=='date'){
										//默认设置为近一月
										var dates =(vm.endTime-vm.startTime)/(3600*1000*24);
										//dates =30;
										//console.log("按天/帧播放，共："+dates+"天")
										if(vm.count>dates-1){
											clearInterval(int);
											vm.sliderProgress=0;
											vm.currentTimeProgress =(moment(vm.startTime)).format("YYYY-MM-DD");
											vm.count= 0;
											return;
										}

										vm.step=100/dates; //步长
										vm.sliderProgress =vm.sliderProgress+vm.step;
										//var dd =(vm.endTime-vm.startTime)/dates;
										vm.currentTimeProgress =moment(moment(vm.startTime)+3600*1000*24*vm.count).format("YYYY-MM-DD");
										//console.log(moment(vm.currentTimeProgress).format("YYYYMMDD"));
										updateMap(moment(vm.currentTimeProgress).format("YYYYMMDD"));
										vm.count++;

									}else if(vm.playType=='month'){
										//默认设置为近一年
										var months =(vm.endTime-vm.startTime)/(3600*1000*24*30);
										//months =12;
										//console.log("按月/帧播放，共："+months+"月")
										if(vm.count>months-1){
											clearInterval(int);
											vm.sliderProgress=0;
											vm.currentTimeProgress =(moment(vm.startTime)).format("YYYY-MM");
											vm.count= 0;
											return;
										}
										vm.step=100/months; //步长
										vm.sliderProgress =vm.sliderProgress+vm.step;
										vm.currentTimeProgress =moment(moment(vm.startTime)+3600*1000*24*30*vm.count).format("YYYY-MM");
										//console.log(moment(vm.currentTimeProgress).format("YYYYMM"));
										updateMap(moment(vm.currentTimeProgress).format("YYYYMM"));
										vm.count++;
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
								var str =$("#btn-play").html();
								if(vm.playType=='hour'){
									//默认设置为近一天
									if(str=='<i class="fa fa-play-circle"></i>'){//播放状态：需要暂停；
										$("#btn-play").html('<i class="fa fa-pause-circle"></i>');
										var int =sessionStorage.getItem("evaluatePlay");
										clearInterval(int);
										vm.count--;
									}
									vm.count--;
									if(vm.count<0){
										vm.sliderProgress=0;
										vm.currentTimeProgress =(moment(vm.startTime)).format("YYYY-MM-DD HH");
										return;
									}
									vm.sliderProgress =vm.sliderProgress-vm.step;
									vm.currentTimeProgress =moment(moment(vm.startTime)+3600*1000*vm.count).format("YYYY-MM-DD HH");
									//console.log(moment(vm.currentTimeProgress).format("YYYYMMDDHH"));
									updateMap(moment(vm.currentTimeProgress).format("YYYYMMDDHH"));
								}else if(vm.playType=='date'){
									//默认设置为近一月
									if(str=='<i class="fa fa-play-circle"></i>'){//播放状态：需要暂停；
										$("#btn-play").html('<i class="fa fa-pause-circle"></i>');
										var int =sessionStorage.getItem("evaluatePlay");
										clearInterval(int);
										vm.count--;
									}
									vm.count--;
									if(vm.count<0){
										vm.sliderProgress=0;
										vm.currentTimeProgress =(moment(vm.startTime)).format("YYYY-MM-DD");
										return
									}
									vm.sliderProgress =vm.sliderProgress-vm.step;
									vm.currentTimeProgress =moment(moment(vm.startTime)+3600*1000*24*vm.count).format("YYYY-MM-DD");
									//console.log(moment(vm.currentTimeProgress).format("YYYYMMDD"));
									updateMap(moment(vm.currentTimeProgress).format("YYYYMMDD"));

								}else if(vm.playType=='month'){
									//默认设置为近一年
									if(str=='<i class="fa fa-play-circle"></i>'){//播放状态：需要暂停；
										$("#btn-play").html('<i class="fa fa-pause-circle"></i>');
										var int =sessionStorage.getItem("evaluatePlay");
										clearInterval(int);
										vm.count--;
									}
									vm.count--;
									if(vm.count<0){
										vm.currentTimeProgress =(moment(vm.startTime)).format("YYYY-MM");
										vm.sliderProgress=0;
										return
									}
									vm.sliderProgress =vm.sliderProgress-vm.step;
									vm.currentTimeProgress =moment(moment(vm.startTime)+3600*1000*24*30*vm.count).format("YYYY-MM");
									updateMap(moment(vm.currentTimeProgress).format("YYYYMM"));
								}



							},
							//播放下一条
							playNext: function(){
								var str =$("#btn-play").html();
								if(vm.playType=='hour'){
									//默认设置为近一天
									if(str=='<i class="fa fa-play-circle"></i>'){//播放状态：需要暂停；
										$("#btn-play").html('<i class="fa fa-pause-circle"></i>');
										var int =sessionStorage.getItem("evaluatePlay");
										clearInterval(int);
										vm.count--;
									}
									vm.count++;
									var hours =(vm.endTime-vm.startTime)/(3600*1000);
									if(vm.count>hours-1){
										vm.sliderProgress=0;
										vm.currentTimeProgress =(moment(vm.startTime)).format("YYYY-MM-DD HH");
										vm.count= 0;
										return;
									}
									vm.sliderProgress =vm.sliderProgress+vm.step;
									vm.currentTimeProgress =moment(moment(vm.startTime)+3600*1000*vm.count).format("YYYY-MM-DD HH");
									//console.log(moment(vm.currentTimeProgress).format("YYYYMMDDHH"));
									updateMap(moment(vm.currentTimeProgress).format("YYYYMMDDHH"));

								}else if(vm.playType=='date'){
									//默认设置为近一月
									if(str=='<i class="fa fa-play-circle"></i>'){//播放状态：需要暂停；
										$("#btn-play").html('<i class="fa fa-pause-circle"></i>');
										var int =sessionStorage.getItem("evaluatePlay");
										clearInterval(int);
										vm.count--;
									}
									vm.count++;
									var dates =(vm.endTime-vm.startTime)/(3600*1000*24);

									if(vm.count>dates-1){
										vm.currentTimeProgress =(moment(vm.startTime)).format("YYYY-MM-DD");
										vm.sliderProgress=0;
										vm.count= 0;
										return;
									}
									vm.sliderProgress =vm.sliderProgress+vm.step;
									vm.currentTimeProgress =moment(moment(vm.startTime)+3600*1000*24*vm.count).format("YYYY-MM-DD");
									//console.log(moment(vm.currentTimeProgress).format("YYYYMMDD"));
									updateMap(moment(vm.currentTimeProgress).format("YYYYMMDD"));

								}else if(vm.playType=='month'){
									//默认设置为近一年
									if(str=='<i class="fa fa-play-circle"></i>'){//播放状态：需要暂停；
										$("#btn-play").html('<i class="fa fa-pause-circle"></i>');
										var int =sessionStorage.getItem("evaluatePlay");
										clearInterval(int);
										vm.count--;
									}
									vm.count++;
									var months =(vm.endTime-vm.startTime)/(3600*1000*24*30);
									if(vm.count>months-1){
										vm.currentTimeProgress =(moment(vm.startTime)).format("YYYY-MM");
										vm.sliderProgress=0;
										vm.count= 0;
										return;
									}
									vm.sliderProgress =vm.sliderProgress+vm.step;
									vm.currentTimeProgress =moment(moment(vm.startTime)+3600*1000*24*30*vm.count).format("YYYY-MM");
									//console.log(moment(vm.currentTimeProgress).format("YYYYMM"));
									updateMap(moment(vm.currentTimeProgress).format("YYYYMM"));

								}
							},
							//停止播放
							stop: function(){
								vm.sliderProgress=0;
								vm.count=0;
								if(vm.playType=='hours'){
									vm.currentTimeProgress =(moment(vm.startTime)).format("YYYY-MM-DD HH");
								}else if(vm.playType=='date'){
									vm.currentTimeProgress =(moment(vm.startTime)).format("YYYY-MM-DD");
								}else if(vm.playType=='month'){
									vm.currentTimeProgress =(moment(vm.startTime)).format("YYYY-MM");
								}
								var int =sessionStorage.getItem("evaluatePlay");
								clearInterval(int);
							},
							//配置项
							//showForm: function() {
							//	if(vm.showOrHide){
							//		vm.showOrHide =false;
							//		vm.showOrHideClass ={
							//			display:"block"
							//		}
							//	}else{
							//		vm.showOrHide =true;
							//		vm.showOrHideClass ={
							//			display:"none"
							//		}
							//	}
                            //
							//},
			//				onSubmit: function () {
			//					console.log(vm.normalparamform);
			////                if(vm.form.maxValue&&vm.form.time.length&&vm.form.region){
			////
			////                }
			//					this.showForm();
			//				},
							//告警信息列表,系统配置频段,选中频率或频段
							 searchFreChange: function(){
								 //console.log(vm.searchFre)
								 if(this.searchFre !=1){
									 this.showOrHideBrandInfo =false
								 }

							 }
							//filterNode:function(value, data) {
							//	if (!value) return true;
							//	return data.label.indexOf(value) !== -1;
							//},
							//nodeClick: function(data){
							//	console.log(data)
							//	vm.searchBrand =data.label;
							//	this.showOrHideBrandInfo =false;//隐藏频段信息
							//},
							//显示频段信息
							//showBrandInfo: function() {
							//	if(this.searchFre ==1){
							//		this.showOrHideBrandInfo =true
							//	}else{
							//		this.showOrHideBrandInfo =false
							//	}
							//}


						},
						//watch: {
						//	filterText:function(val) {
						//		this.$refs.tree2.filter(val);
						//	}
						//},
						mounted:function(){

						}
					});



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
							//统计数据，电波秩序总览中，显示各列中每种信号类型的总数
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
					//console.log(beginFreq + '-' + endFreq);
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
                 //信号分类统计列表点击频段名称跳转至单频段
				$('#table-radio').on("click",".redioNameA",function(e){
					var freq = e.target.dataset.frequeryband;
					const urlObj = {
						ServerName : 'host1',
						DisplayName : '单频段',
						MultiTabable : false,
						ReflushIfExist : true,
						Url : '#/FrequencyChannel/' + freq
					};
					Binding.openUrl(JSON.stringify(urlObj));
				})
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
							//console.log(e);
							var freq = e.target.getAttribute("centorfreq");
							var signalId = e.target.getAttribute("signalid");
							//console.log(freq);
							//console.log(signalId);
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
							//console.log(e);
							var freq = e.target.getAttribute("centorfreq");
							var signalId = e.target.getAttribute("signalid");
							//console.log(freq);
							//console.log(signalId);
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
							//console.log(e);
							var freq = e.target.getAttribute("centorfreq");
							var signalId = e.target.getAttribute("signalid");
							//console.log(freq);
							//console.log(signalId);
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
							//console.log(e);
							var freq = e.target.getAttribute("centorfreq");
							var freqid = e.target.getAttribute("freqid");
							//console.log(freq);
							//console.log(freqid);
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
						height:450,
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
//						strictSearch : true,
//						searchText : "搜索信号",
						responseHandler : function(res) {
							return res;
						},
						columns : [
							{
								align : 'left',
								width : '10%',
								title: '序号',
								formatter : function(value,row,index) {
									return index + 1;
								}
							},{
							class : "sortTable3",
							searchable : true,
							searchFormatter : false,
							field : 'centor',
								align : 'left',
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
								align : 'left',
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
							width : '25%',
								align : 'left',
							titleTooltip : "监测站",
							sortable : true,
							sortName : "monitorID",
							formatter : function(value, row, index) {
								var monitors = getMonitors(AREACODE);
								var content = "";
									var value1 = [];
									var temp = [];
									for (var i = 0; i < value.length; i++) {
										for (var j = 0; j < monitors.length; j++) {
											if (value[i] == monitors[j].Num) {
												value1[i] = monitors[j].Name;
												var sub_content = "<div class='popover-item'>"
														+ value1[i] + "</div>";
												content += sub_content;
												temp.push(value1[i]);
											}
										}
									}
									return '<div class="dpopover" data-container="body"  data-placement="top"  data-toggle="popover" data-trigger="hover" data-content="'
											+ content + '">' + temp + '</div>';
							},
							events : {

							}
						}, {
							class : "sortTable3",
							searchable : false,
							field : 'station',
							title : '发射源' +"<img src='images/arrow-both.png'width='24'/> ",
							width : '25%',
							titleTooltip : "发射源",
							sortable : true,
								align : 'left',
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
							title : '操作',
								align : 'left',
							titleTooltip : "操作",
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

							$(".table-signal-list").find(".pull-right .pagination").append("<span class='goPage'>跳转到第<input id='tablesignalListPageBtn' class='pageNum' type='text'>页</span>")
							//$(".table-radio").find(".pull-right.pagination").append("<input type='button' value='go' class='pageBtn'>")
							$("#tablesignalListPageBtn").on("blur keydown",function(e){
								if (e.type=='blur'||(e.type=='keydown'&&e.keyCode == "13")) {

									$("#table-signal-list").bootstrapTable("selectPage",parseInt($("#tablesignalListPageBtn").val()));
								}
							});

						},

						onPageChange:function(){
							$(".table-signal-list").find(".pull-right .pagination").append("<span class='goPage'>跳转到第<input id='tablesignalListPageBtn' class='pageNum' type='text'>页</span>")
							//$(".table-radio").find(".pull-right.pagination").append("<input type='button' value='go' class='pageBtn'>")
							$("#tablesignalListPageBtn").on("blur keydown",function(e){
								if (e.type=='blur'||(e.type=='keydown'&&e.keyCode == "13")) {

									$("#table-signal-list").bootstrapTable("selectPage",parseInt($("#tablesignalListPageBtn").val()));
								}
							});

						},
						onAll:function(){
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
					baseMapInit();
					vm.autoPlay();
				});
				// 电磁环境评估模态框关闭
				$('#modalEvaluate').on('hidden.bs.modal', function(e) {
					vm.stop();
					app.clear('situation');
					app.clear('stations');
					$(".coverage-number").html("");
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
					value=parseInt(value);
					if(value>0&&value<=30){
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
						height:450,
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
						columns : [
							{
								align : 'left',
								width : '10%',
								title: '序号',
								formatter : function(value,row,index) {
									return index + 1;
								}
							},{
							class : "sortTable4",
							field : 'centor',
							title : '频率(MHz)' +"<img src='images/arrow-both.png'width='24'/> ",
							titleTooltip : "频率(MHz)",
							sortable : true,
							sortName : "centor",
							width : '15%',
								align : 'left',
							formatter : function(value, row, index) {
								return '<a class="centerFreqA">' + value
										+ '</a>';
							}
						}, {
							class : "sortTable4",
							field : 'band',
							title : '带宽(kHz)' +"<img src='images/arrow-both.png'width='24'/> ",
							width : '15%',
								align : 'left',
							titleTooltip : "带宽(kHz)",
							sortable : true
						}, {
							class : "sortTable4",
							visible : false,
							field : 'success_rate',
							title : '监测发射功率' +"<img src='images/arrow-both.png'width='24'/> ",
							width : '18%',
								align : 'left',
							titleTooltip : "监测发射功率",
							sortable : true
						}, {
							class : "sortTable4",
							field : 'monitorID',
							title : '监测站' +"<img src='images/arrow-both.png'width='24'/> ",
							width : '25%',
							titleTooltip : "监测站",
							sortable : true,
								align : 'left',
							sortName : "monitorID",
							formatter : function(value, row, index) {
								var monitors = getMonitors(AREACODE);
								var content = "";
									var value1 = [];
									var temp = [];
									for (var i = 0; i < value.length; i++) {
										for (var j = 0; j < monitors.length; j++) {
											if (value[i] == monitors[j].Num) {
												value1[i] = monitors[j].Name;
												var sub_content = "<div class='popover-item'>"
														+ value1[i] + "</div>";
												content += sub_content;
												temp.push(value1[i]);
											}
										}
									}
									return '<div class="dpopover" data-container="body"  data-placement="top"  data-toggle="popover" data-trigger="hover" data-content="'
											+ content + '">' + temp + '</div>';
							},
							events : {

							}
						}, {
							class : "sortTable4",
							field : 'station',
							title : '发射源' +"<img src='images/arrow-both.png'width='24'/> ",
							width : '25%',
							titleTooltip : "发射源",
								align : 'left',
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
							align : 'left',
								title : '操作',
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
							$(".table-signalsOnMonitors-list").find(".pull-right .pagination").append("<span class='goPage'>跳转到第<input id='signalsOnMonitorsPageBtn' class='pageNum' type='text'>页</span>")
							//$(".table-radio").find(".pull-right.pagination").append("<input type='button' value='go' class='pageBtn'>")
							$("#signalsOnMonitorsPageBtn").on("blur keydown",function(e){
								if (e.type=='blur'||(e.type=='keydown'&&e.keyCode == "13")) {

									$("#table-signalsOnMonitors-list").bootstrapTable("selectPage",parseInt($("#signalsOnMonitorsPageBtn").val()));
								}
							})

						},
						onPageChange:function(){
							$(".table-signalsOnMonitors-list").find(".pull-right .pagination").append("<span class='goPage'>跳转到第<input id='signalsOnMonitorsPageBtn' class='pageNum' type='text'>页</span>")
							//$(".table-radio").find(".pull-right.pagination").append("<input type='button' value='go' class='pageBtn'>")
							$("#signalsOnMonitorsPageBtn").on("blur keydown",function(e){
									if (e.type=='blur'||(e.type=='keydown'&&e.keyCode == "13")) {
										$("#table-signalsOnMonitors-list").bootstrapTable("selectPage", parseInt($("#signalsOnMonitorsPageBtn").val()));
									}
							})
						},
						onAll:function(){
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
						}
					});
				})
				
			}
	       //电波秩序总览中，显示每种信号类型的总数
			function getSignalCounts(areaCode,monitorsID,beginFreq,endFreq){
				var data ={
					areaCode:areaCode,
					monitorsID :monitorsID,
					beginFreq:beginFreq,
					endFreq:endFreq

				};
				ajax.post("data/waveorder/statisticsForSingnalsAndWarnings",data,function(result){
					//console.log(result)
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
				var stationsInfo = [];
				for (var index = 0; index < stationObj.length; index++) {
					codes.push(stationObj[index].Num);
					stationsName.push(stationObj[index].Name);
					stations.push({"code":stationObj[index].Num,"name":stationObj[index].Name});
					stationsInfo.push({"flat":stationObj[index].Longitude,"flon":stationObj[index].Latitude,"id":stationObj[index].Num});
				}
				//console.log(stations)
				return {
					codes:codes,
					stationsName:stationsName,
					stations:stations,
					stationsInfo:stationsInfo

				}
			}
	       //态势图的渲染
	        function baseMapInit(){
				//var info = Binding.getUser();
				//info = JSON.parse(info);
				//var code = info.Area.Code;
				var code =$(".select2-picker").val();
				var areas=[{"code":"110000","parentCode":"0","level":"1","name":"北京市","latitude":"39.929986","longitude":"116.395645"},{"code":"110100","parentCode":"110000","level":"2","name":"市辖区","latitude":"","longitude":""},{"code":"110101","parentCode":"110100","level":"3","name":"东城区","latitude":"39.938574","longitude":"116.421885"},{"code":"110102","parentCode":"110100","level":"3","name":"西城区","latitude":"9.93428","longitude":"116.37319"},{"code":"110105","parentCode":"110100","level":"3","name":"朝阳区","latitude":"39.958953","longitude":"116.521695"},{"code":"110106","parentCode":"110100","level":"3","name":"丰台区","latitude":"9.841938","longitude":"116.25837"},{"code":"110107","parentCode":"110100","level":"3","name":"石景山区","latitude":"39.938867","longitude":"116.184556"},{"code":"110108","parentCode":"110100","level":"3","name":"海淀区","latitude":"40.033162","longitude":"116.239678"},{"code":"110109","parentCode":"110100","level":"3","name":"门头沟区","latitude":"40.000893","longitude":"115.795795"},{"code":"110111","parentCode":"110100","level":"3","name":"房山区","latitude":"39.726753","longitude":"115.862836"},{"code":"110112","parentCode":"110100","level":"3","name":"通州区","latitude":"39.809815","longitude":"116.740079"},{"code":"110113","parentCode":"110100","level":"3","name":"顺义区","latitude":"40.154951","longitude":"116.728229"},{"code":"110114","parentCode":"110100","level":"3","name":"昌平区","latitude":"40.221724","longitude":"116.216456"},{"code":"110115","parentCode":"110100","level":"3","name":"大兴区","latitude":"39.65279","longitude":"116.425195"},{"code":"110116","parentCode":"110100","level":"3","name":"怀柔区","latitude":"40.638139","longitude":"116.593408"},{"code":"110117","parentCode":"110100","level":"3","name":"平谷区","latitude":"40.215925","longitude":"117.150433"},{"code":"110200","parentCode":"110000","level":"2","name":"市辖县","latitude":"","longitude":""},{"code":"110228","parentCode":"110200","level":"3","name":"密云县","latitude":"40.532843","longitude":"117.001201"},{"code":"110229","parentCode":"110200","level":"3","name":"延庆县","latitude":"40.545156","longitude":"116.169735"},{"code":"120000","parentCode":"0","level":"1","name":"天津市","latitude":"39.14393","longitude":"117.210813"},{"code":"120100","parentCode":"120000","level":"2","name":"市辖区","latitude":"","longitude":""},{"code":"120101","parentCode":"120100","level":"3","name":"和平区","latitude":"39.124809","longitude":"117.202814"},{"code":"120102","parentCode":"120100","level":"3","name":"河东区","latitude":"39.126626","longitude":"117.261693"},{"code":"120103","parentCode":"120100","level":"3","name":"河西区","latitude":"39.084494","longitude":"117.236165"},{"code":"120104","parentCode":"120100","level":"3","name":"南开区","latitude":"39.116987","longitude":"117.162728"},{"code":"120105","parentCode":"120100","level":"3","name":"河北区","latitude":"39.173149","longitude":"117.220297"},{"code":"120106","parentCode":"120100","level":"3","name":"红桥区","latitude":"39.170621","longitude":"117.162217"},{"code":"120110","parentCode":"120100","level":"3","name":"东丽区","latitude":"39.139605","longitude":"117.414782"},{"code":"120111","parentCode":"120100","level":"3","name":"西青区","latitude":"39.035065","longitude":"117.126201"},{"code":"120112","parentCode":"120100","level":"3","name":"津南区","latitude":"8.969791","longitude":"117.39291"},{"code":"120113","parentCode":"120100","level":"3","name":"北辰区","latitude":"39.259131","longitude":"117.180606"},{"code":"120114","parentCode":"120100","level":"3","name":"武清区","latitude":"39.457043","longitude":"117.034578"},{"code":"120115","parentCode":"120100","level":"3","name":"宝坻区","latitude":"39.615544","longitude":"117.411421"},{"code":"120116","parentCode":"120100","level":"3","name":"滨海新区","latitude":"","longitude":""},{"code":"120200","parentCode":"120000","level":"2","name":"市辖县","latitude":"","longitude":""},{"code":"120221","parentCode":"120200","level":"3","name":"宁河县","latitude":"39.381561","longitude":"117.689424"},{"code":"120223","parentCode":"120200","level":"3","name":"静海县","latitude":"38.861262","longitude":"116.972772"},{"code":"120225","parentCode":"120200","level":"3","name":"蓟县","latitude":"40.009614","longitude":"117.441159"},{"code":"130000","parentCode":"0","level":"1","name":"河北省","latitude":"38.61384","longitude":"115.661434"},{"code":"130100","parentCode":"130000","level":"2","name":"石家庄市","latitude":"38.048958","longitude":"114.522082"},{"code":"130101","parentCode":"130100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"130102","parentCode":"130100","level":"3","name":"长安区","latitude":"38.076875","longitude":"114.592622"},{"code":"130103","parentCode":"130100","level":"3","name":"桥东区","latitude":"38.06339","longitude":"114.510784"},{"code":"130104","parentCode":"130100","level":"3","name":"桥西区","latitude":"8.033365","longitude":"114.43814"},{"code":"130105","parentCode":"130100","level":"3","name":"新华区","latitude":"38.117219","longitude":"114.453501"},{"code":"130107","parentCode":"130100","level":"3","name":"井陉矿区","latitude":"38.081098","longitude":"114.050744"},{"code":"130108","parentCode":"130100","level":"3","name":"裕华区","latitude":"38.014621","longitude":"114.586383"},{"code":"130121","parentCode":"130100","level":"3","name":"井陉县","latitude":"38.000891","longitude":"114.077952"},{"code":"130123","parentCode":"130100","level":"3","name":"正定县","latitude":"38.227073","longitude":"114.570201"},{"code":"130124","parentCode":"130100","level":"3","name":"栾城县","latitude":"37.906161","longitude":"114.632968"},{"code":"130125","parentCode":"130100","level":"3","name":"行唐县","latitude":"38.546695","longitude":"114.457436"},{"code":"130126","parentCode":"130100","level":"3","name":"灵寿县","latitude":"38.510936","longitude":"114.187819"},{"code":"130127","parentCode":"130100","level":"3","name":"高邑县","latitude":"37.622651","longitude":"114.607385"},{"code":"130128","parentCode":"130100","level":"3","name":"深泽县","latitude":"38.194681","longitude":"115.233102"},{"code":"130129","parentCode":"130100","level":"3","name":"赞皇县","latitude":"37.628132","longitude":"114.289553"},{"code":"130130","parentCode":"130100","level":"3","name":"无极县","latitude":"8.183286","longitude":"114.95114"},{"code":"130131","parentCode":"130100","level":"3","name":"平山县","latitude":"38.408762","longitude":"113.872429"},{"code":"130132","parentCode":"130100","level":"3","name":"元氏县","latitude":"7.807353","longitude":"114.42836"},{"code":"130133","parentCode":"130100","level":"3","name":"赵县","latitude":"37.769612","longitude":"114.834938"},{"code":"130181","parentCode":"130100","level":"3","name":"辛集市","latitude":"7.924122","longitude":"115.29875"},{"code":"130182","parentCode":"130100","level":"3","name":"藁城市","latitude":"38.054936","longitude":"114.816476"},{"code":"130183","parentCode":"130100","level":"3","name":"晋州市","latitude":"37.991145","longitude":"115.091738"},{"code":"130184","parentCode":"130100","level":"3","name":"新乐市","latitude":"38.377578","longitude":"114.762271"},{"code":"130185","parentCode":"130100","level":"3","name":"鹿泉市","latitude":"38.084707","longitude":"114.347323"},{"code":"130200","parentCode":"130000","level":"2","name":"唐山市","latitude":"39.650531","longitude":"118.183451"},{"code":"130201","parentCode":"130200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"130202","parentCode":"130200","level":"3","name":"路南区","latitude":"9.612987","longitude":"118.20604"},{"code":"130203","parentCode":"130200","level":"3","name":"路北区","latitude":"9.657846","longitude":"118.18507"},{"code":"130204","parentCode":"130200","level":"3","name":"古冶区","latitude":"39.723045","longitude":"118.462232"},{"code":"130205","parentCode":"130200","level":"3","name":"开平区","latitude":"39.692123","longitude":"118.257848"},{"code":"130207","parentCode":"130200","level":"3","name":"丰南区","latitude":"39.384663","longitude":"118.085847"},{"code":"130208","parentCode":"130200","level":"3","name":"丰润区","latitude":"9.789909","longitude":"118.05949"},{"code":"130209","parentCode":"130200","level":"3","name":"曹妃甸区","latitude":"","longitude":""},{"code":"130223","parentCode":"130200","level":"3","name":"滦县","latitude":"39.785509","longitude":"118.583777"},{"code":"130224","parentCode":"130200","level":"3","name":"滦南县","latitude":"39.360739","longitude":"118.549385"},{"code":"130225","parentCode":"130200","level":"3","name":"乐亭县","latitude":"39.357229","longitude":"118.939943"},{"code":"130227","parentCode":"130200","level":"3","name":"迁西县","latitude":"40.238508","longitude":"118.371389"},{"code":"130229","parentCode":"130200","level":"3","name":"玉田县","latitude":"39.818843","longitude":"117.734753"},{"code":"130281","parentCode":"130200","level":"3","name":"遵化市","latitude":"40.137901","longitude":"117.957639"},{"code":"130283","parentCode":"130200","level":"3","name":"迁安市","latitude":"40.040443","longitude":"118.686955"},{"code":"130300","parentCode":"130000","level":"2","name":"秦皇岛市","latitude":"39.945462","longitude":"119.604368"},{"code":"130301","parentCode":"130300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"130302","parentCode":"130300","level":"3","name":"海港区","latitude":"39.98878","longitude":"119.577617"},{"code":"130303","parentCode":"130300","level":"3","name":"山海关区","latitude":"40.0329","longitude":"119.713616"},{"code":"130304","parentCode":"130300","level":"3","name":"北戴河区","latitude":"39.854293","longitude":"119.479321"},{"code":"130321","parentCode":"130300","level":"3","name":"青龙满族自治县","latitude":"40.35365","longitude":"119.137582"},{"code":"130322","parentCode":"130300","level":"3","name":"昌黎县","latitude":"39.638021","longitude":"119.094621"},{"code":"130323","parentCode":"130300","level":"3","name":"抚宁县","latitude":".012967","longitude":"119.3951"},{"code":"130324","parentCode":"130300","level":"3","name":"卢龙县","latitude":"39.920978","longitude":"118.985564"},{"code":"130400","parentCode":"130000","level":"2","name":"邯郸市","latitude":"36.609308","longitude":"114.482694"},{"code":"130401","parentCode":"130400","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"130402","parentCode":"130400","level":"3","name":"邯山区","latitude":"6.536153","longitude":"114.46929"},{"code":"130403","parentCode":"130400","level":"3","name":"丛台区","latitude":"36.637215","longitude":"114.511068"},{"code":"130404","parentCode":"130400","level":"3","name":"复兴区","latitude":"36.610369","longitude":"114.448095"},{"code":"130406","parentCode":"130400","level":"3","name":"峰峰矿区","latitude":"36.474685","longitude":"114.190422"},{"code":"130421","parentCode":"130400","level":"3","name":"邯郸县","latitude":"36.620347","longitude":"114.494486"},{"code":"130423","parentCode":"130400","level":"3","name":"临漳县","latitude":"36.266142","longitude":"114.586944"},{"code":"130424","parentCode":"130400","level":"3","name":"成安县","latitude":"36.428151","longitude":"114.704775"},{"code":"130425","parentCode":"130400","level":"3","name":"大名县","latitude":"36.309544","longitude":"115.248635"},{"code":"130426","parentCode":"130400","level":"3","name":"涉县","latitude":"36.598105","longitude":"113.742914"},{"code":"130427","parentCode":"130400","level":"3","name":"磁县","latitude":"36.406731","longitude":"114.255101"},{"code":"130428","parentCode":"130400","level":"3","name":"肥乡县","latitude":"36.577261","longitude":"114.836905"},{"code":"130429","parentCode":"130400","level":"3","name":"永年县","latitude":"36.7702","longitude":"114.641602"},{"code":"130430","parentCode":"130400","level":"3","name":"邱县","latitude":"36.79727","longitude":"115.206702"},{"code":"130431","parentCode":"130400","level":"3","name":"鸡泽县","latitude":"36.873677","longitude":"114.869566"},{"code":"130432","parentCode":"130400","level":"3","name":"广平县","latitude":"36.511926","longitude":"115.020874"},{"code":"130433","parentCode":"130400","level":"3","name":"馆陶县","latitude":"36.618537","longitude":"115.299157"},{"code":"130434","parentCode":"130400","level":"3","name":"魏县","latitude":"250568","longitude":"114.936"},{"code":"130435","parentCode":"130400","level":"3","name":"曲周县","latitude":"36.752651","longitude":"115.038532"},{"code":"130481","parentCode":"130400","level":"3","name":"武安市","latitude":"36.748995","longitude":"114.058334"},{"code":"130500","parentCode":"130000","level":"2","name":"邢台市","latitude":"37.069531","longitude":"114.520487"},{"code":"130501","parentCode":"130500","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"130502","parentCode":"130500","level":"3","name":"桥东区","latitude":"38.06339","longitude":"114.510784"},{"code":"130503","parentCode":"130500","level":"3","name":"桥西区","latitude":"8.033365","longitude":"114.43814"},{"code":"130521","parentCode":"130500","level":"3","name":"邢台县","latitude":"37.152422","longitude":"114.167744"},{"code":"130522","parentCode":"130500","level":"3","name":"临城县","latitude":"37.463138","longitude":"114.384665"},{"code":"130523","parentCode":"130500","level":"3","name":"内丘县","latitude":"37.300525","longitude":"114.530622"},{"code":"130524","parentCode":"130500","level":"3","name":"柏乡县","latitude":"37.517418","longitude":"114.707424"},{"code":"130525","parentCode":"130500","level":"3","name":"隆尧县","latitude":"37.364688","longitude":"114.792916"},{"code":"130526","parentCode":"130500","level":"3","name":"任县","latitude":"37.17463","longitude":"114.769967"},{"code":"130527","parentCode":"130500","level":"3","name":"南和县","latitude":"37.016964","longitude":"114.753089"},{"code":"130528","parentCode":"130500","level":"3","name":"宁晋县","latitude":"37.612087","longitude":"115.021678"},{"code":"130529","parentCode":"130500","level":"3","name":"巨鹿县","latitude":"37.278679","longitude":"115.058886"},{"code":"130530","parentCode":"130500","level":"3","name":"新河县","latitude":"37.499363","longitude":"115.257204"},{"code":"130531","parentCode":"130500","level":"3","name":"广宗县","latitude":"37.083549","longitude":"115.198173"},{"code":"130532","parentCode":"130500","level":"3","name":"平乡县","latitude":"37.05611","longitude":"115.004819"},{"code":"130533","parentCode":"130500","level":"3","name":"威县","latitude":"37.078395","longitude":"115.387725"},{"code":"130534","parentCode":"130500","level":"3","name":"清河县","latitude":"7.04053","longitude":"115.69159"},{"code":"130535","parentCode":"130500","level":"3","name":"临西县","latitude":"36.858027","longitude":"115.528441"},{"code":"130581","parentCode":"130500","level":"3","name":"南宫市","latitude":"7.286427","longitude":"115.47941"},{"code":"130582","parentCode":"130500","level":"3","name":"沙河市","latitude":"36.938635","longitude":"114.283093"},{"code":"130600","parentCode":"130000","level":"2","name":"保定市","latitude":"38.886565","longitude":"115.49481"},{"code":"130601","parentCode":"130600","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"130602","parentCode":"130600","level":"3","name":"新市区","latitude":"38.881183","longitude":"115.412245"},{"code":"130603","parentCode":"130600","level":"3","name":"北市区","latitude":"38.897511","longitude":"115.532912"},{"code":"130604","parentCode":"130600","level":"3","name":"南市区","latitude":"38.821877","longitude":"115.484784"},{"code":"130621","parentCode":"130600","level":"3","name":"满城县","latitude":"38.970233","longitude":"115.277554"},{"code":"130622","parentCode":"130600","level":"3","name":"清苑县","latitude":"38.738336","longitude":"115.493492"},{"code":"130623","parentCode":"130600","level":"3","name":"涞水县","latitude":"39.616118","longitude":"115.444628"},{"code":"130624","parentCode":"130600","level":"3","name":"阜平县","latitude":"38.894806","longitude":"114.164211"},{"code":"130625","parentCode":"130600","level":"3","name":"徐水县","latitude":".041872","longitude":"115.5796"},{"code":"130626","parentCode":"130600","level":"3","name":"定兴县","latitude":"39.211518","longitude":"115.755046"},{"code":"130627","parentCode":"130600","level":"3","name":"唐县","latitude":"38.904521","longitude":"114.806091"},{"code":"130628","parentCode":"130600","level":"3","name":"高阳县","latitude":"38.673021","longitude":"115.838442"},{"code":"130629","parentCode":"130600","level":"3","name":"容城县","latitude":"39.057814","longitude":"115.908779"},{"code":"130630","parentCode":"130600","level":"3","name":"涞源县","latitude":"39.366937","longitude":"114.730451"},{"code":"130631","parentCode":"130600","level":"3","name":"望都县","latitude":"38.679015","longitude":"115.178346"},{"code":"130632","parentCode":"130600","level":"3","name":"安新县","latitude":"38.878255","longitude":"115.886731"},{"code":"130633","parentCode":"130600","level":"3","name":"易县","latitude":"39.317566","longitude":"115.254022"},{"code":"130634","parentCode":"130600","level":"3","name":"曲阳县","latitude":"38.706612","longitude":"114.660664"},{"code":"130635","parentCode":"130600","level":"3","name":"蠡县","latitude":"38.528232","longitude":"115.669282"},{"code":"130636","parentCode":"130600","level":"3","name":"顺平县","latitude":"38.927951","longitude":"115.073989"},{"code":"130637","parentCode":"130600","level":"3","name":"博野县","latitude":"38.459123","longitude":"115.487786"},{"code":"130638","parentCode":"130600","level":"3","name":"雄县","latitude":"39.042787","longitude":"116.183299"},{"code":"130681","parentCode":"130600","level":"3","name":"涿州市","latitude":"39.482482","longitude":"115.999054"},{"code":"130682","parentCode":"130600","level":"3","name":"定州市","latitude":"38.465839","longitude":"115.057407"},{"code":"130683","parentCode":"130600","level":"3","name":"安国市","latitude":"38.39374","longitude":"115.334827"},{"code":"130684","parentCode":"130600","level":"3","name":"高碑店市","latitude":"39.265088","longitude":"116.040934"},{"code":"130700","parentCode":"130000","level":"2","name":"张家口市","latitude":"40.811188","longitude":"114.893782"},{"code":"130701","parentCode":"130700","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"130702","parentCode":"130700","level":"3","name":"桥东区","latitude":"38.06339","longitude":"114.510784"},{"code":"130703","parentCode":"130700","level":"3","name":"桥西区","latitude":"8.033365","longitude":"114.43814"},{"code":"130705","parentCode":"130700","level":"3","name":"宣化区","latitude":"40.632394","longitude":"115.258472"},{"code":"130706","parentCode":"130700","level":"3","name":"下花园区","latitude":"40.568837","longitude":"115.350498"},{"code":"130721","parentCode":"130700","level":"3","name":"宣化县","latitude":"40.560152","longitude":"115.035435"},{"code":"130722","parentCode":"130700","level":"3","name":"张北县","latitude":"41.293641","longitude":"114.772897"},{"code":"130723","parentCode":"130700","level":"3","name":"康保县","latitude":"41.784595","longitude":"114.606536"},{"code":"130724","parentCode":"130700","level":"3","name":"沽源县","latitude":"41.580404","longitude":"115.636092"},{"code":"130725","parentCode":"130700","level":"3","name":"尚义县","latitude":"41.132635","longitude":"114.152528"},{"code":"130726","parentCode":"130700","level":"3","name":"蔚县","latitude":"39.879353","longitude":"114.712537"},{"code":"130727","parentCode":"130700","level":"3","name":"阳原县","latitude":"40.138642","longitude":"114.394396"},{"code":"130728","parentCode":"130700","level":"3","name":"怀安县","latitude":"40.559534","longitude":"114.502607"},{"code":"130729","parentCode":"130700","level":"3","name":"万全县","latitude":"40.847108","longitude":"114.611453"},{"code":"130730","parentCode":"130700","level":"3","name":"怀来县","latitude":"40.347984","longitude":"115.634061"},{"code":"130731","parentCode":"130700","level":"3","name":"涿鹿县","latitude":"40.101876","longitude":"115.223925"},{"code":"130732","parentCode":"130700","level":"3","name":"赤城县","latitude":"40.956026","longitude":"115.892223"},{"code":"130733","parentCode":"130700","level":"3","name":"崇礼县","latitude":"41.02606","longitude":"115.219891"},{"code":"130800","parentCode":"130000","level":"2","name":"承德市","latitude":"40.992521","longitude":"117.933822"},{"code":"130801","parentCode":"130800","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"130802","parentCode":"130800","level":"3","name":"双桥区","latitude":"40.971406","longitude":"117.948355"},{"code":"130803","parentCode":"130800","level":"3","name":"双滦区","latitude":"41.051453","longitude":"117.809336"},{"code":"130804","parentCode":"130800","level":"3","name":"鹰手营子矿区","latitude":"40.53176","longitude":"117.679426"},{"code":"130821","parentCode":"130800","level":"3","name":"承德县","latitude":"40.973242","longitude":"118.125718"},{"code":"130822","parentCode":"130800","level":"3","name":"兴隆县","latitude":"40.458142","longitude":"117.726136"},{"code":"130823","parentCode":"130800","level":"3","name":"平泉县","latitude":"41.075304","longitude":"118.739324"},{"code":"130824","parentCode":"130800","level":"3","name":"滦平县","latitude":"40.924821","longitude":"117.369563"},{"code":"130825","parentCode":"130800","level":"3","name":"隆化县","latitude":"1.517995","longitude":"117.56993"},{"code":"130826","parentCode":"130800","level":"3","name":"丰宁满族自治县","latitude":"41.425684","longitude":"116.623795"},{"code":"130827","parentCode":"130800","level":"3","name":"宽城满族自治县","latitude":"40.57809","longitude":"118.635888"},{"code":"130828","parentCode":"130800","level":"3","name":"围场满族蒙古族自治县","latitude":"42.108025","longitude":"117.547022"},{"code":"130900","parentCode":"130000","level":"2","name":"沧州市","latitude":"38.297615","longitude":"116.863806"},{"code":"130901","parentCode":"130900","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"130902","parentCode":"130900","level":"3","name":"新华区","latitude":"38.117219","longitude":"114.453501"},{"code":"130903","parentCode":"130900","level":"3","name":"运河区","latitude":"38.314446","longitude":"116.844854"},{"code":"130921","parentCode":"130900","level":"3","name":"沧县","latitude":"38.302139","longitude":"116.862714"},{"code":"130922","parentCode":"130900","level":"3","name":"青县","latitude":"38.565778","longitude":"116.851234"},{"code":"130923","parentCode":"130900","level":"3","name":"东光县","latitude":"37.887452","longitude":"116.677832"},{"code":"130924","parentCode":"130900","level":"3","name":"海兴县","latitude":"38.14247","longitude":"117.567264"},{"code":"130925","parentCode":"130900","level":"3","name":"盐山县","latitude":"7.96037","longitude":"117.26694"},{"code":"130926","parentCode":"130900","level":"3","name":"肃宁县","latitude":"38.422207","longitude":"115.885816"},{"code":"130927","parentCode":"130900","level":"3","name":"南皮县","latitude":"8.023186","longitude":"116.86585"},{"code":"130928","parentCode":"130900","level":"3","name":"吴桥县","latitude":"37.661863","longitude":"116.508033"},{"code":"130929","parentCode":"130900","level":"3","name":"献县","latitude":"38.242726","longitude":"116.175505"},{"code":"130930","parentCode":"130900","level":"3","name":"孟村回族自治县","latitude":"38.091265","longitude":"117.159538"},{"code":"130981","parentCode":"130900","level":"3","name":"泊头市","latitude":"38.090279","longitude":"116.389236"},{"code":"130982","parentCode":"130900","level":"3","name":"任丘市","latitude":"38.741105","longitude":"116.163214"},{"code":"130983","parentCode":"130900","level":"3","name":"黄骅市","latitude":"38.401522","longitude":"117.400217"},{"code":"130984","parentCode":"130900","level":"3","name":"河间市","latitude":"38.483721","longitude":"116.271593"},{"code":"131000","parentCode":"130000","level":"2","name":"廊坊市","latitude":"39.518611","longitude":"116.703602"},{"code":"131001","parentCode":"131000","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"131002","parentCode":"131000","level":"3","name":"安次区","latitude":"39.345312","longitude":"116.796123"},{"code":"131003","parentCode":"131000","level":"3","name":"广阳区","latitude":"39.533686","longitude":"116.694236"},{"code":"131022","parentCode":"131000","level":"3","name":"固安县","latitude":"9.351106","longitude":"116.28967"},{"code":"131023","parentCode":"131000","level":"3","name":"永清县","latitude":"39.302836","longitude":"116.560557"},{"code":"131024","parentCode":"131000","level":"3","name":"香河县","latitude":"39.7431","longitude":"117.051306"},{"code":"131025","parentCode":"131000","level":"3","name":"大城县","latitude":"38.668803","longitude":"116.588639"},{"code":"131026","parentCode":"131000","level":"3","name":"文安县","latitude":"38.91139","longitude":"116.494817"},{"code":"131028","parentCode":"131000","level":"3","name":"大厂回族自治县","latitude":"39.895316","longitude":"116.955076"},{"code":"131081","parentCode":"131000","level":"3","name":"霸州市","latitude":"39.10932","longitude":"116.574306"},{"code":"131082","parentCode":"131000","level":"3","name":"三河市","latitude":"39.967428","longitude":"117.021284"},{"code":"131100","parentCode":"130000","level":"2","name":"衡水市","latitude":"37.746929","longitude":"115.686229"},{"code":"131101","parentCode":"131100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"131102","parentCode":"131100","level":"3","name":"桃城区","latitude":"37.724218","longitude":"115.666657"},{"code":"131121","parentCode":"131100","level":"3","name":"枣强县","latitude":"37.461024","longitude":"115.757677"},{"code":"131122","parentCode":"131100","level":"3","name":"武邑县","latitude":"37.827679","longitude":"115.944507"},{"code":"131123","parentCode":"131100","level":"3","name":"武强县","latitude":"38.050513","longitude":"115.920118"},{"code":"131124","parentCode":"131100","level":"3","name":"饶阳县","latitude":"38.223059","longitude":"115.740007"},{"code":"131125","parentCode":"131100","level":"3","name":"安平县","latitude":"38.243196","longitude":"115.490416"},{"code":"131126","parentCode":"131100","level":"3","name":"故城县","latitude":"37.356998","longitude":"115.978057"},{"code":"131127","parentCode":"131100","level":"3","name":"景县","latitude":"37.668477","longitude":"116.200134"},{"code":"131128","parentCode":"131100","level":"3","name":"阜城县","latitude":"37.912309","longitude":"116.328425"},{"code":"131181","parentCode":"131100","level":"3","name":"冀州市","latitude":"37.55607","longitude":"115.470293"},{"code":"131182","parentCode":"131100","level":"3","name":"深州市","latitude":"37.957013","longitude":"115.586699"},{"code":"140000","parentCode":"0","level":"1","name":"山西省","latitude":"37.866566","longitude":"112.515496"},{"code":"140100","parentCode":"140000","level":"2","name":"太原市","latitude":"37.890277","longitude":"112.550864"},{"code":"140101","parentCode":"140100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"140105","parentCode":"140100","level":"3","name":"小店区","latitude":"37.753528","longitude":"112.577409"},{"code":"140106","parentCode":"140100","level":"3","name":"迎泽区","latitude":"37.865737","longitude":"112.663203"},{"code":"140107","parentCode":"140100","level":"3","name":"杏花岭区","latitude":"37.915556","longitude":"112.629836"},{"code":"140108","parentCode":"140100","level":"3","name":"尖草坪区","latitude":"7.972758","longitude":"112.48844"},{"code":"140109","parentCode":"140100","level":"3","name":"万柏林区","latitude":"37.894693","longitude":"112.402857"},{"code":"140110","parentCode":"140100","level":"3","name":"晋源区","latitude":"37.748675","longitude":"112.481587"},{"code":"140121","parentCode":"140100","level":"3","name":"清徐县","latitude":"37.593242","longitude":"112.387085"},{"code":"140122","parentCode":"140100","level":"3","name":"阳曲县","latitude":"38.158246","longitude":"112.672659"},{"code":"140123","parentCode":"140100","level":"3","name":"娄烦县","latitude":"38.034584","longitude":"111.797821"},{"code":"140181","parentCode":"140100","level":"3","name":"古交市","latitude":"37.905179","longitude":"112.107739"},{"code":"140200","parentCode":"140000","level":"2","name":"大同市","latitude":"40.113744","longitude":"113.290509"},{"code":"140201","parentCode":"140200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"140202","parentCode":"140200","level":"3","name":"城区","latitude":"37.857865","longitude":"113.612838"},{"code":"140203","parentCode":"140200","level":"3","name":"矿区","latitude":"37.890804","longitude":"113.540771"},{"code":"140211","parentCode":"140200","level":"3","name":"南郊区","latitude":"40.051891","longitude":"113.226457"},{"code":"140212","parentCode":"140200","level":"3","name":"新荣区","latitude":"40.267127","longitude":"113.236894"},{"code":"140221","parentCode":"140200","level":"3","name":"阳高县","latitude":"40.222312","longitude":"113.823181"},{"code":"140222","parentCode":"140200","level":"3","name":"天镇县","latitude":"0.403529","longitude":"114.16813"},{"code":"140223","parentCode":"140200","level":"3","name":"广灵县","latitude":"39.768994","longitude":"114.161702"},{"code":"140224","parentCode":"140200","level":"3","name":"灵丘县","latitude":"39.377268","longitude":"114.213095"},{"code":"140225","parentCode":"140200","level":"3","name":"浑源县","latitude":"39.634162","longitude":"113.710759"},{"code":"140226","parentCode":"140200","level":"3","name":"左云县","latitude":"40.000738","longitude":"112.777856"},{"code":"140227","parentCode":"140200","level":"3","name":"大同县","latitude":"40.001627","longitude":"113.583866"},{"code":"140300","parentCode":"140000","level":"2","name":"阳泉市","latitude":"37.869529","longitude":"113.569238"},{"code":"140301","parentCode":"140300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"140302","parentCode":"140300","level":"3","name":"城区","latitude":"37.857865","longitude":"113.612838"},{"code":"140303","parentCode":"140300","level":"3","name":"矿区","latitude":"37.890804","longitude":"113.540771"},{"code":"140311","parentCode":"140300","level":"3","name":"郊区","latitude":"37.911504","longitude":"113.568086"},{"code":"140321","parentCode":"140300","level":"3","name":"平定县","latitude":"37.849271","longitude":"113.768978"},{"code":"140322","parentCode":"140300","level":"3","name":"盂县","latitude":"38.229386","longitude":"113.360967"},{"code":"140400","parentCode":"140000","level":"2","name":"长治市","latitude":"36.201664","longitude":"113.120292"},{"code":"140401","parentCode":"140400","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"140402","parentCode":"140400","level":"3","name":"城区","latitude":"37.857865","longitude":"113.612838"},{"code":"140411","parentCode":"140400","level":"3","name":"郊区","latitude":"37.911504","longitude":"113.568086"},{"code":"140421","parentCode":"140400","level":"3","name":"长治县","latitude":"36.02468","longitude":"113.086194"},{"code":"140423","parentCode":"140400","level":"3","name":"襄垣县","latitude":"36.580201","longitude":"112.988973"},{"code":"140424","parentCode":"140400","level":"3","name":"屯留县","latitude":"36.34261","longitude":"112.750363"},{"code":"140425","parentCode":"140400","level":"3","name":"平顺县","latitude":"36.221794","longitude":"113.533689"},{"code":"140426","parentCode":"140400","level":"3","name":"黎城县","latitude":"36.619368","longitude":"113.396852"},{"code":"140427","parentCode":"140400","level":"3","name":"壶关县","latitude":"35.992652","longitude":"113.371998"},{"code":"140428","parentCode":"140400","level":"3","name":"长子县","latitude":"36.110999","longitude":"112.802254"},{"code":"140429","parentCode":"140400","level":"3","name":"武乡县","latitude":"6.888323","longitude":"112.96752"},{"code":"140430","parentCode":"140400","level":"3","name":"沁县","latitude":"6.707383","longitude":"112.65221"},{"code":"140431","parentCode":"140400","level":"3","name":"沁源县","latitude":"36.701567","longitude":"112.290094"},{"code":"140481","parentCode":"140400","level":"3","name":"潞城市","latitude":"36.374406","longitude":"113.254387"},{"code":"140500","parentCode":"140000","level":"2","name":"晋城市","latitude":"35.499834","longitude":"112.867333"},{"code":"140501","parentCode":"140500","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"140502","parentCode":"140500","level":"3","name":"城区","latitude":"37.857865","longitude":"113.612838"},{"code":"140521","parentCode":"140500","level":"3","name":"沁水县","latitude":"5.751489","longitude":"112.37743"},{"code":"140522","parentCode":"140500","level":"3","name":"阳城县","latitude":"35.426541","longitude":"112.361527"},{"code":"140524","parentCode":"140500","level":"3","name":"陵川县","latitude":"35.690744","longitude":"113.343387"},{"code":"140525","parentCode":"140500","level":"3","name":"泽州县","latitude":"35.475851","longitude":"112.870985"},{"code":"140581","parentCode":"140500","level":"3","name":"高平市","latitude":"35.809742","longitude":"112.935115"},{"code":"140600","parentCode":"140000","level":"2","name":"朔州市","latitude":"39.337672","longitude":"112.479928"},{"code":"140601","parentCode":"140600","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"140602","parentCode":"140600","level":"3","name":"朔城区","latitude":".243272","longitude":"112.5562"},{"code":"140603","parentCode":"140600","level":"3","name":"平鲁区","latitude":"9.640007","longitude":"112.30435"},{"code":"140621","parentCode":"140600","level":"3","name":"山阴县","latitude":"39.52105","longitude":"112.786805"},{"code":"140622","parentCode":"140600","level":"3","name":"应县","latitude":"39.509316","longitude":"113.260593"},{"code":"140623","parentCode":"140600","level":"3","name":"右玉县","latitude":"40.008136","longitude":"112.421677"},{"code":"140624","parentCode":"140600","level":"3","name":"怀仁县","latitude":"39.793571","longitude":"113.112305"},{"code":"140700","parentCode":"140000","level":"2","name":"晋中市","latitude":"37.693362","longitude":"112.738514"},{"code":"140701","parentCode":"140700","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"140702","parentCode":"140700","level":"3","name":"榆次区","latitude":"37.650825","longitude":"112.843737"},{"code":"140721","parentCode":"140700","level":"3","name":"榆社县","latitude":"37.14005","longitude":"112.954181"},{"code":"140722","parentCode":"140700","level":"3","name":"左权县","latitude":"37.032795","longitude":"113.474538"},{"code":"140723","parentCode":"140700","level":"3","name":"和顺县","latitude":"37.348374","longitude":"113.474934"},{"code":"140724","parentCode":"140700","level":"3","name":"昔阳县","latitude":"37.563418","longitude":"113.762106"},{"code":"140725","parentCode":"140700","level":"3","name":"寿阳县","latitude":"37.825119","longitude":"113.141611"},{"code":"140726","parentCode":"140700","level":"3","name":"太谷县","latitude":"37.407696","longitude":"112.736433"},{"code":"140727","parentCode":"140700","level":"3","name":"祁县","latitude":"37.292198","longitude":"112.469066"},{"code":"140728","parentCode":"140700","level":"3","name":"平遥县","latitude":"37.14809","longitude":"112.265493"},{"code":"140729","parentCode":"140700","level":"3","name":"灵石县","latitude":"36.834487","longitude":"111.735504"},{"code":"140781","parentCode":"140700","level":"3","name":"介休市","latitude":"37.025476","longitude":"111.995188"},{"code":"140800","parentCode":"140000","level":"2","name":"运城市","latitude":"35.038859","longitude":"111.006854"},{"code":"140801","parentCode":"140800","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"140802","parentCode":"140800","level":"3","name":"盐湖区","latitude":"35.063677","longitude":"110.961931"},{"code":"140821","parentCode":"140800","level":"3","name":"临猗县","latitude":"35.14938","longitude":"110.625895"},{"code":"140822","parentCode":"140800","level":"3","name":"万荣县","latitude":"5.388134","longitude":"110.71554"},{"code":"140823","parentCode":"140800","level":"3","name":"闻喜县","latitude":"35.373753","longitude":"111.319287"},{"code":"140824","parentCode":"140800","level":"3","name":"稷山县","latitude":"35.598267","longitude":"110.965122"},{"code":"140825","parentCode":"140800","level":"3","name":"新绛县","latitude":"35.631583","longitude":"111.172875"},{"code":"140826","parentCode":"140800","level":"3","name":"绛县","latitude":"5.498579","longitude":"111.64482"},{"code":"140827","parentCode":"140800","level":"3","name":"垣曲县","latitude":"35.221584","longitude":"111.824787"},{"code":"140828","parentCode":"140800","level":"3","name":"夏县","latitude":"35.12668","longitude":"111.358386"},{"code":"140829","parentCode":"140800","level":"3","name":"平陆县","latitude":"34.888646","longitude":"111.251109"},{"code":"140830","parentCode":"140800","level":"3","name":"芮城县","latitude":"34.709535","longitude":"110.616496"},{"code":"140881","parentCode":"140800","level":"3","name":"永济市","latitude":"34.894672","longitude":"110.488949"},{"code":"140882","parentCode":"140800","level":"3","name":"河津市","latitude":"35.631891","longitude":"110.708539"},{"code":"140900","parentCode":"140000","level":"2","name":"忻州市","latitude":"38.461031","longitude":"112.727939"},{"code":"140901","parentCode":"140900","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"140902","parentCode":"140900","level":"3","name":"忻府区","latitude":".437832","longitude":"112.6052"},{"code":"140921","parentCode":"140900","level":"3","name":"定襄县","latitude":"38.51675","longitude":"113.035589"},{"code":"140922","parentCode":"140900","level":"3","name":"五台县","latitude":"38.778174","longitude":"113.442104"},{"code":"140923","parentCode":"140900","level":"3","name":"代县","latitude":"39.093197","longitude":"113.050582"},{"code":"140924","parentCode":"140900","level":"3","name":"繁峙县","latitude":"39.204757","longitude":"113.596214"},{"code":"140925","parentCode":"140900","level":"3","name":"宁武县","latitude":"38.82189","longitude":"112.193896"},{"code":"140926","parentCode":"140900","level":"3","name":"静乐县","latitude":"38.400067","longitude":"112.064994"},{"code":"140927","parentCode":"140900","level":"3","name":"神池县","latitude":"9.173053","longitude":"112.00992"},{"code":"140928","parentCode":"140900","level":"3","name":"五寨县","latitude":"39.008731","longitude":"111.744757"},{"code":"140929","parentCode":"140900","level":"3","name":"岢岚县","latitude":"38.737957","longitude":"111.543568"},{"code":"140930","parentCode":"140900","level":"3","name":"河曲县","latitude":"39.20644","longitude":"111.359829"},{"code":"140931","parentCode":"140900","level":"3","name":"保德县","latitude":"38.887135","longitude":"111.142835"},{"code":"140932","parentCode":"140900","level":"3","name":"偏关县","latitude":"39.464649","longitude":"111.671903"},{"code":"140981","parentCode":"140900","level":"3","name":"原平市","latitude":"38.838876","longitude":"112.682128"},{"code":"141000","parentCode":"140000","level":"2","name":"临汾市","latitude":"36.099745","longitude":"111.538788"},{"code":"141001","parentCode":"141000","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"141002","parentCode":"141000","level":"3","name":"尧都区","latitude":"36.125937","longitude":"111.474665"},{"code":"141021","parentCode":"141000","level":"3","name":"曲沃县","latitude":"35.704201","longitude":"111.527041"},{"code":"141022","parentCode":"141000","level":"3","name":"翼城县","latitude":"35.695398","longitude":"111.839209"},{"code":"141023","parentCode":"141000","level":"3","name":"襄汾县","latitude":"35.874204","longitude":"111.385953"},{"code":"141024","parentCode":"141000","level":"3","name":"洪洞县","latitude":"36.325514","longitude":"111.659378"},{"code":"141025","parentCode":"141000","level":"3","name":"古县","latitude":"36.303823","longitude":"112.011243"},{"code":"141026","parentCode":"141000","level":"3","name":"安泽县","latitude":"36.164296","longitude":"112.307904"},{"code":"141027","parentCode":"141000","level":"3","name":"浮山县","latitude":"35.945831","longitude":"111.928409"},{"code":"141028","parentCode":"141000","level":"3","name":"吉县","latitude":"36.158677","longitude":"110.728162"},{"code":"141029","parentCode":"141000","level":"3","name":"乡宁县","latitude":"35.925119","longitude":"110.944128"},{"code":"141030","parentCode":"141000","level":"3","name":"大宁县","latitude":"36.432636","longitude":"110.710805"},{"code":"141031","parentCode":"141000","level":"3","name":"隰县","latitude":"36.711951","longitude":"111.009965"},{"code":"141032","parentCode":"141000","level":"3","name":"永和县","latitude":"36.737137","longitude":"110.617898"},{"code":"141033","parentCode":"141000","level":"3","name":"蒲县","latitude":"36.4246","longitude":"111.162359"},{"code":"141034","parentCode":"141000","level":"3","name":"汾西县","latitude":"36.642781","longitude":"111.483148"},{"code":"141081","parentCode":"141000","level":"3","name":"侯马市","latitude":"35.621785","longitude":"111.371509"},{"code":"141082","parentCode":"141000","level":"3","name":"霍州市","latitude":"36.599678","longitude":"111.830836"},{"code":"141100","parentCode":"140000","level":"2","name":"吕梁市","latitude":"","longitude":""},{"code":"141101","parentCode":"141100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"141102","parentCode":"141100","level":"3","name":"离石区","latitude":"","longitude":""},{"code":"141121","parentCode":"141100","level":"3","name":"文水县","latitude":"37.459706","longitude":"111.964995"},{"code":"141122","parentCode":"141100","level":"3","name":"交城县","latitude":"37.687046","longitude":"111.819927"},{"code":"141123","parentCode":"141100","level":"3","name":"兴县","latitude":"38.392263","longitude":"111.069311"},{"code":"141124","parentCode":"141100","level":"3","name":"临县","latitude":"37.962867","longitude":"110.901147"},{"code":"141125","parentCode":"141100","level":"3","name":"柳林县","latitude":"37.403754","longitude":"110.876927"},{"code":"141126","parentCode":"141100","level":"3","name":"石楼县","latitude":"37.035145","longitude":"110.753479"},{"code":"141127","parentCode":"141100","level":"3","name":"岚县","latitude":"38.343813","longitude":"111.606641"},{"code":"141128","parentCode":"141100","level":"3","name":"方山县","latitude":"37.886688","longitude":"111.337979"},{"code":"141129","parentCode":"141100","level":"3","name":"中阳县","latitude":"37.266318","longitude":"111.185904"},{"code":"141130","parentCode":"141100","level":"3","name":"交口县","latitude":"36.957719","longitude":"111.315924"},{"code":"141181","parentCode":"141100","level":"3","name":"孝义市","latitude":"37.118133","longitude":"111.637646"},{"code":"141182","parentCode":"141100","level":"3","name":"汾阳市","latitude":"37.316764","longitude":"111.745996"},{"code":"150000","parentCode":"0","level":"1","name":"内蒙古自治区","latitude":"43.468238","longitude":"114.415868"},{"code":"150100","parentCode":"150000","level":"2","name":"呼和浩特市","latitude":"40.828319","longitude":"111.660351"},{"code":"150101","parentCode":"150100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"150102","parentCode":"150100","level":"3","name":"新城区","latitude":"34.271474","longitude":"108.991539"},{"code":"150103","parentCode":"150100","level":"3","name":"回民区","latitude":"40.838895","longitude":"111.596886"},{"code":"150104","parentCode":"150100","level":"3","name":"玉泉区","latitude":"40.747387","longitude":"111.658553"},{"code":"150105","parentCode":"150100","level":"3","name":"赛罕区","latitude":"40.788864","longitude":"111.876335"},{"code":"150121","parentCode":"150100","level":"3","name":"土默特左旗","latitude":"40.689987","longitude":"111.234704"},{"code":"150122","parentCode":"150100","level":"3","name":"托克托县","latitude":".361084","longitude":"111.3197"},{"code":"150123","parentCode":"150100","level":"3","name":"和林格尔县","latitude":"40.333868","longitude":"111.901693"},{"code":"150124","parentCode":"150100","level":"3","name":"清水河县","latitude":"39.889118","longitude":"111.706236"},{"code":"150125","parentCode":"150100","level":"3","name":"武川县","latitude":"41.116204","longitude":"111.179572"},{"code":"150200","parentCode":"150000","level":"2","name":"包头市","latitude":"40.647119","longitude":"109.846239"},{"code":"150201","parentCode":"150200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"150202","parentCode":"150200","level":"3","name":"东河区","latitude":"40.589124","longitude":"110.070141"},{"code":"150203","parentCode":"150200","level":"3","name":"昆都仑区","latitude":"40.658057","longitude":"109.806834"},{"code":"150204","parentCode":"150200","level":"3","name":"青山区","latitude":"40.658778","longitude":"109.903675"},{"code":"150205","parentCode":"150200","level":"3","name":"石拐区","latitude":"40.716464","longitude":"110.299215"},{"code":"150206","parentCode":"150200","level":"3","name":"白云鄂博矿区","latitude":"","longitude":""},{"code":"150207","parentCode":"150200","level":"3","name":"九原区","latitude":"40.627202","longitude":"109.949197"},{"code":"150221","parentCode":"150200","level":"3","name":"土默特右旗","latitude":"40.527996","longitude":"110.693258"},{"code":"150222","parentCode":"150200","level":"3","name":"固阳县","latitude":"41.104725","longitude":"110.167592"},{"code":"150223","parentCode":"150200","level":"3","name":"达尔罕茂明安联合旗","latitude":"41.943507","longitude":"110.286189"},{"code":"150300","parentCode":"150000","level":"2","name":"乌海市","latitude":"39.683177","longitude":"106.831999"},{"code":"150301","parentCode":"150300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"150302","parentCode":"150300","level":"3","name":"海勃湾区","latitude":"39.734834","longitude":"106.861482"},{"code":"150303","parentCode":"150300","level":"3","name":"海南区","latitude":"19.180501","longitude":"109.733755"},{"code":"150304","parentCode":"150300","level":"3","name":"乌达区","latitude":"39.535878","longitude":"106.725859"},{"code":"150400","parentCode":"150000","level":"2","name":"赤峰市","latitude":"42.297112","longitude":"118.930761"},{"code":"150401","parentCode":"150400","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"150402","parentCode":"150400","level":"3","name":"红山区","latitude":"42.286232","longitude":"118.998103"},{"code":"150403","parentCode":"150400","level":"3","name":"元宝山区","latitude":"42.184131","longitude":"119.268169"},{"code":"150404","parentCode":"150400","level":"3","name":"松山区","latitude":"42.268753","longitude":"118.757106"},{"code":"150421","parentCode":"150400","level":"3","name":"阿鲁科尔沁旗","latitude":"44.195957","longitude":"120.053241"},{"code":"150422","parentCode":"150400","level":"3","name":"巴林左旗","latitude":"44.203431","longitude":"119.280766"},{"code":"150423","parentCode":"150400","level":"3","name":"巴林右旗","latitude":"3.684787","longitude":"118.94609"},{"code":"150424","parentCode":"150400","level":"3","name":"林西县","latitude":"43.771462","longitude":"118.110216"},{"code":"150425","parentCode":"150400","level":"3","name":"克什克腾旗","latitude":"3.218237","longitude":"117.35857"},{"code":"150426","parentCode":"150400","level":"3","name":"翁牛特旗","latitude":"42.97398","longitude":"119.254643"},{"code":"150428","parentCode":"150400","level":"3","name":"喀喇沁旗","latitude":"41.908351","longitude":"118.667056"},{"code":"150429","parentCode":"150400","level":"3","name":"宁城县","latitude":"41.571041","longitude":"118.905499"},{"code":"150430","parentCode":"150400","level":"3","name":"敖汉旗","latitude":"42.430592","longitude":"120.157713"},{"code":"150500","parentCode":"150000","level":"2","name":"通辽市","latitude":"43.633756","longitude":"122.260363"},{"code":"150501","parentCode":"150500","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"150502","parentCode":"150500","level":"3","name":"科尔沁区","latitude":"43.65829","longitude":"122.291294"},{"code":"150521","parentCode":"150500","level":"3","name":"科尔沁左翼中旗","latitude":"4.057579","longitude":"122.49918"},{"code":"150522","parentCode":"150500","level":"3","name":"科尔沁左翼后旗","latitude":"43.196083","longitude":"122.697345"},{"code":"150523","parentCode":"150500","level":"3","name":"开鲁县","latitude":"43.734942","longitude":"121.324094"},{"code":"150524","parentCode":"150500","level":"3","name":"库伦旗","latitude":"42.810038","longitude":"121.573038"},{"code":"150525","parentCode":"150500","level":"3","name":"奈曼旗","latitude":"42.972383","longitude":"120.940789"},{"code":"150526","parentCode":"150500","level":"3","name":"扎鲁特旗","latitude":"44.822451","longitude":"120.596028"},{"code":"150581","parentCode":"150500","level":"3","name":"霍林郭勒市","latitude":"45.528106","longitude":"119.579748"},{"code":"150600","parentCode":"150000","level":"2","name":"鄂尔多斯市","latitude":"39.81649","longitude":"109.993706"},{"code":"150601","parentCode":"150600","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"150602","parentCode":"150600","level":"3","name":"东胜区","latitude":"39.805586","longitude":"109.764419"},{"code":"150621","parentCode":"150600","level":"3","name":"达拉特旗","latitude":"40.220264","longitude":"109.866191"},{"code":"150622","parentCode":"150600","level":"3","name":"准格尔旗","latitude":"39.794725","longitude":"110.886239"},{"code":"150623","parentCode":"150600","level":"3","name":"鄂托克前旗","latitude":"8.275938","longitude":"107.59701"},{"code":"150624","parentCode":"150600","level":"3","name":"鄂托克旗","latitude":"9.286297","longitude":"107.75202"},{"code":"150625","parentCode":"150600","level":"3","name":"杭锦旗","latitude":"40.212873","longitude":"108.212828"},{"code":"150626","parentCode":"150600","level":"3","name":"乌审旗","latitude":"38.640475","longitude":"108.889663"},{"code":"150627","parentCode":"150600","level":"3","name":"伊金霍洛旗","latitude":"39.420696","longitude":"109.704186"},{"code":"150700","parentCode":"150000","level":"2","name":"呼伦贝尔市","latitude":"49.201636","longitude":"119.760822"},{"code":"150701","parentCode":"150700","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"150702","parentCode":"150700","level":"3","name":"海拉尔区","latitude":"49.279245","longitude":"120.042882"},{"code":"150703","parentCode":"150700","level":"3","name":"扎赉诺尔区","latitude":"","longitude":""},{"code":"150721","parentCode":"150700","level":"3","name":"阿荣旗","latitude":"48.639989","longitude":"123.171954"},{"code":"150722","parentCode":"150700","level":"3","name":"莫力达瓦达斡尔族自治旗","latitude":"","longitude":""},{"code":"150723","parentCode":"150700","level":"3","name":"鄂伦春自治旗","latitude":"50.348755","longitude":"123.817278"},{"code":"150724","parentCode":"150700","level":"3","name":"鄂温克族自治旗","latitude":"48.499137","longitude":"120.067483"},{"code":"150725","parentCode":"150700","level":"3","name":"陈巴尔虎旗","latitude":"49.605281","longitude":"119.535208"},{"code":"150726","parentCode":"150700","level":"3","name":"新巴尔虎左旗","latitude":"48.436392","longitude":"118.621525"},{"code":"150727","parentCode":"150700","level":"3","name":"新巴尔虎右旗","latitude":"48.644979","longitude":"116.802184"},{"code":"150781","parentCode":"150700","level":"3","name":"满洲里市","latitude":"49.500032","longitude":"117.603687"},{"code":"150782","parentCode":"150700","level":"3","name":"牙克石市","latitude":"49.329996","longitude":"121.512668"},{"code":"150783","parentCode":"150700","level":"3","name":"扎兰屯市","latitude":"47.743403","longitude":"121.929202"},{"code":"150784","parentCode":"150700","level":"3","name":"额尔古纳市","latitude":"51.660819","longitude":"120.652764"},{"code":"150785","parentCode":"150700","level":"3","name":"根河市","latitude":"51.375925","longitude":"121.797713"},{"code":"150800","parentCode":"150000","level":"2","name":"巴彦淖尔市","latitude":"","longitude":""},{"code":"150801","parentCode":"150800","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"150802","parentCode":"150800","level":"3","name":"临河区","latitude":"","longitude":""},{"code":"150821","parentCode":"150800","level":"3","name":"五原县","latitude":"41.045427","longitude":"108.072284"},{"code":"150822","parentCode":"150800","level":"3","name":"磴口县","latitude":"5181","longitude":"106.7"},{"code":"150823","parentCode":"150800","level":"3","name":"乌拉特前旗","latitude":"40.905993","longitude":"109.105297"},{"code":"150824","parentCode":"150800","level":"3","name":"乌拉特中旗","latitude":"41.831045","longitude":"108.464542"},{"code":"150825","parentCode":"150800","level":"3","name":"乌拉特后旗","latitude":"41.531945","longitude":"106.413808"},{"code":"150826","parentCode":"150800","level":"3","name":"杭锦后旗","latitude":"40.890871","longitude":"107.033454"},{"code":"150900","parentCode":"150000","level":"2","name":"乌兰察布市","latitude":"","longitude":""},{"code":"150901","parentCode":"150900","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"150902","parentCode":"150900","level":"3","name":"集宁区","latitude":"","longitude":""},{"code":"150921","parentCode":"150900","level":"3","name":"卓资县","latitude":"40.958869","longitude":"112.443377"},{"code":"150922","parentCode":"150900","level":"3","name":"化德县","latitude":"41.979126","longitude":"114.165738"},{"code":"150923","parentCode":"150900","level":"3","name":"商都县","latitude":"41.726517","longitude":"113.622155"},{"code":"150924","parentCode":"150900","level":"3","name":"兴和县","latitude":"40.952667","longitude":"113.773721"},{"code":"150925","parentCode":"150900","level":"3","name":"凉城县","latitude":"40.50278","longitude":"112.550432"},{"code":"150926","parentCode":"150900","level":"3","name":"察哈尔右翼前旗","latitude":"40.98171","longitude":"113.241096"},{"code":"150927","parentCode":"150900","level":"3","name":"察哈尔右翼中旗","latitude":"41.428255","longitude":"112.470743"},{"code":"150928","parentCode":"150900","level":"3","name":"察哈尔右翼后旗","latitude":"41.529483","longitude":"113.069693"},{"code":"150929","parentCode":"150900","level":"3","name":"四子王旗","latitude":"42.307146","longitude":"111.589037"},{"code":"150981","parentCode":"150900","level":"3","name":"丰镇市","latitude":"40.558336","longitude":"113.308677"},{"code":"152200","parentCode":"150000","level":"2","name":"兴安盟","latitude":"46.083757","longitude":"122.048167"},{"code":"152201","parentCode":"152200","level":"3","name":"乌兰浩特市","latitude":"46.116944","longitude":"122.081534"},{"code":"152202","parentCode":"152200","level":"3","name":"阿尔山市","latitude":"47.163696","longitude":"120.357534"},{"code":"152221","parentCode":"152200","level":"3","name":"科尔沁右翼前旗","latitude":"46.334025","longitude":"121.221524"},{"code":"152222","parentCode":"152200","level":"3","name":"科尔沁右翼中旗","latitude":"5.242069","longitude":"121.19851"},{"code":"152223","parentCode":"152200","level":"3","name":"扎赉特旗","latitude":"46.790808","longitude":"122.388146"},{"code":"152224","parentCode":"152200","level":"3","name":"突泉县","latitude":"45.632866","longitude":"121.519212"},{"code":"152500","parentCode":"150000","level":"2","name":"锡林郭勒盟","latitude":"43.939705","longitude":"116.02734"},{"code":"152501","parentCode":"152500","level":"3","name":"二连浩特市","latitude":"43.41778","longitude":"111.966178"},{"code":"152502","parentCode":"152500","level":"3","name":"锡林浩特市","latitude":"44.078961","longitude":"116.136948"},{"code":"152522","parentCode":"152500","level":"3","name":"阿巴嘎旗","latitude":"44.276507","longitude":"114.893471"},{"code":"152523","parentCode":"152500","level":"3","name":"苏尼特左旗","latitude":"44.039238","longitude":"113.140307"},{"code":"152524","parentCode":"152500","level":"3","name":"苏尼特右旗","latitude":".900964","longitude":"112.9116"},{"code":"152525","parentCode":"152500","level":"3","name":"东乌珠穆沁旗","latitude":"5.826665","longitude":"117.81045"},{"code":"152526","parentCode":"152500","level":"3","name":"西乌珠穆沁旗","latitude":"44.715903","longitude":"117.816963"},{"code":"152527","parentCode":"152500","level":"3","name":"太仆寺旗","latitude":"41.906216","longitude":"115.304558"},{"code":"152528","parentCode":"152500","level":"3","name":"镶黄旗","latitude":"42.368276","longitude":"114.120589"},{"code":"152529","parentCode":"152500","level":"3","name":"正镶白旗","latitude":"42.554843","longitude":"115.024347"},{"code":"152530","parentCode":"152500","level":"3","name":"正蓝旗","latitude":"2.674414","longitude":"115.94011"},{"code":"152531","parentCode":"152500","level":"3","name":"多伦县","latitude":"42.196601","longitude":"116.498639"},{"code":"152900","parentCode":"150000","level":"2","name":"阿拉善盟","latitude":"38.843075","longitude":"105.695683"},{"code":"152921","parentCode":"152900","level":"3","name":"阿拉善左旗","latitude":"39.547806","longitude":"105.038247"},{"code":"152922","parentCode":"152900","level":"3","name":"阿拉善右旗","latitude":"40.186229","longitude":"102.443856"},{"code":"152923","parentCode":"152900","level":"3","name":"额济纳旗","latitude":"41.6938","longitude":"100.099512"},{"code":"210000","parentCode":"0","level":"1","name":"辽宁省","latitude":"41.6216","longitude":"122.753592"},{"code":"210100","parentCode":"210000","level":"2","name":"沈阳市","latitude":"41.808645","longitude":"123.432791"},{"code":"210101","parentCode":"210100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"210102","parentCode":"210100","level":"3","name":"和平区","latitude":"39.124809","longitude":"117.202814"},{"code":"210103","parentCode":"210100","level":"3","name":"沈河区","latitude":"41.798305","longitude":"123.453552"},{"code":"210104","parentCode":"210100","level":"3","name":"大东区","latitude":"41.835279","longitude":"123.498927"},{"code":"210105","parentCode":"210100","level":"3","name":"皇姑区","latitude":"41.848913","longitude":"123.415376"},{"code":"210106","parentCode":"210100","level":"3","name":"铁西区","latitude":"1.805724","longitude":"123.35863"},{"code":"210111","parentCode":"210100","level":"3","name":"苏家屯区","latitude":"41.589345","longitude":"123.426289"},{"code":"210112","parentCode":"210100","level":"3","name":"东陵区","latitude":"41.765562","longitude":"123.599872"},{"code":"210113","parentCode":"210100","level":"3","name":"沈北新区","latitude":"","longitude":""},{"code":"210114","parentCode":"210100","level":"3","name":"于洪区","latitude":"41.843551","longitude":"123.242847"},{"code":"210122","parentCode":"210100","level":"3","name":"辽中县","latitude":"41.56686","longitude":"122.778872"},{"code":"210123","parentCode":"210100","level":"3","name":"康平县","latitude":"42.765541","longitude":"123.273598"},{"code":"210124","parentCode":"210100","level":"3","name":"法库县","latitude":"42.415298","longitude":"123.248897"},{"code":"210181","parentCode":"210100","level":"3","name":"新民市","latitude":"42.016776","longitude":"122.866418"},{"code":"210200","parentCode":"210000","level":"2","name":"大连市","latitude":"38.94871","longitude":"121.593478"},{"code":"210201","parentCode":"210200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"210202","parentCode":"210200","level":"3","name":"中山区","latitude":"38.900436","longitude":"121.677966"},{"code":"210203","parentCode":"210200","level":"3","name":"西岗区","latitude":"38.91337","longitude":"121.625823"},{"code":"210204","parentCode":"210200","level":"3","name":"沙河口区","latitude":"38.921778","longitude":"121.582618"},{"code":"210211","parentCode":"210200","level":"3","name":"甘井子区","latitude":".955462","longitude":"121.5285"},{"code":"210212","parentCode":"210200","level":"3","name":"旅顺口区","latitude":"38.908291","longitude":"121.295936"},{"code":"210213","parentCode":"210200","level":"3","name":"金州区","latitude":"39.298619","longitude":"121.956582"},{"code":"210224","parentCode":"210200","level":"3","name":"长海县","latitude":"39.260109","longitude":"122.748265"},{"code":"210281","parentCode":"210200","level":"3","name":"瓦房店市","latitude":"39.708956","longitude":"121.790699"},{"code":"210282","parentCode":"210200","level":"3","name":"普兰店市","latitude":"39.620006","longitude":"122.274257"},{"code":"210283","parentCode":"210200","level":"3","name":"庄河市","latitude":"39.85891","longitude":"122.934145"},{"code":"210300","parentCode":"210000","level":"2","name":"鞍山市","latitude":"41.118744","longitude":"123.007763"},{"code":"210301","parentCode":"210300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"210302","parentCode":"210300","level":"3","name":"铁东区","latitude":"41.118235","longitude":"123.020706"},{"code":"210303","parentCode":"210300","level":"3","name":"铁西区","latitude":"1.805724","longitude":"123.35863"},{"code":"210304","parentCode":"210300","level":"3","name":"立山区","latitude":"41.164173","longitude":"123.040474"},{"code":"210311","parentCode":"210300","level":"3","name":"千山区","latitude":"41.061329","longitude":"123.014005"},{"code":"210321","parentCode":"210300","level":"3","name":"台安县","latitude":"41.3471","longitude":"122.443683"},{"code":"210323","parentCode":"210300","level":"3","name":"岫岩满族自治县","latitude":"40.403181","longitude":"123.346069"},{"code":"210381","parentCode":"210300","level":"3","name":"海城市","latitude":"40.840354","longitude":"122.791201"},{"code":"210400","parentCode":"210000","level":"2","name":"抚顺市","latitude":"41.877304","longitude":"123.92982"},{"code":"210401","parentCode":"210400","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"210402","parentCode":"210400","level":"3","name":"新抚区","latitude":"41.86979","longitude":"123.911369"},{"code":"210403","parentCode":"210400","level":"3","name":"东洲区","latitude":"41.833588","longitude":"124.029249"},{"code":"210404","parentCode":"210400","level":"3","name":"望花区","latitude":"41.860404","longitude":"123.785996"},{"code":"210411","parentCode":"210400","level":"3","name":"顺城区","latitude":"41.916014","longitude":"123.901724"},{"code":"210421","parentCode":"210400","level":"3","name":"抚顺县","latitude":"41.750077","longitude":"124.136589"},{"code":"210422","parentCode":"210400","level":"3","name":"新宾满族自治县","latitude":"41.635119","longitude":"124.827866"},{"code":"210423","parentCode":"210400","level":"3","name":"清原满族自治县","latitude":"42.118882","longitude":"124.924317"},{"code":"210500","parentCode":"210000","level":"2","name":"本溪市","latitude":"41.325838","longitude":"123.778062"},{"code":"210501","parentCode":"210500","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"210502","parentCode":"210500","level":"3","name":"平山区","latitude":"41.240401","longitude":"123.692575"},{"code":"210503","parentCode":"210500","level":"3","name":"溪湖区","latitude":"41.456154","longitude":"123.711866"},{"code":"210504","parentCode":"210500","level":"3","name":"明山区","latitude":"41.347752","longitude":"123.901737"},{"code":"210505","parentCode":"210500","level":"3","name":"南芬区","latitude":"1.122716","longitude":"123.82788"},{"code":"210521","parentCode":"210500","level":"3","name":"本溪满族自治县","latitude":"41.19567","longitude":"124.158564"},{"code":"210522","parentCode":"210500","level":"3","name":"桓仁满族自治县","latitude":"41.261816","longitude":"125.290028"},{"code":"210600","parentCode":"210000","level":"2","name":"丹东市","latitude":"40.129023","longitude":"124.338543"},{"code":"210601","parentCode":"210600","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"210602","parentCode":"210600","level":"3","name":"元宝区","latitude":"40.173197","longitude":"124.350321"},{"code":"210603","parentCode":"210600","level":"3","name":"振兴区","latitude":"40.067035","longitude":"124.355563"},{"code":"210604","parentCode":"210600","level":"3","name":"振安区","latitude":"40.211547","longitude":"124.292197"},{"code":"210624","parentCode":"210600","level":"3","name":"宽甸满族自治县","latitude":"40.766142","longitude":"124.934106"},{"code":"210681","parentCode":"210600","level":"3","name":"东港市","latitude":"9.981217","longitude":"123.87687"},{"code":"210682","parentCode":"210600","level":"3","name":"凤城市","latitude":"0.57957","longitude":"124.07296"},{"code":"210700","parentCode":"210000","level":"2","name":"锦州市","latitude":"41.130879","longitude":"121.147749"},{"code":"210701","parentCode":"210700","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"210702","parentCode":"210700","level":"3","name":"古塔区","latitude":"41.141388","longitude":"121.126434"},{"code":"210703","parentCode":"210700","level":"3","name":"凌河区","latitude":"41.13438","longitude":"121.182665"},{"code":"210711","parentCode":"210700","level":"3","name":"太和区","latitude":"41.13683","longitude":"121.118645"},{"code":"210726","parentCode":"210700","level":"3","name":"黑山县","latitude":"41.799698","longitude":"122.260736"},{"code":"210727","parentCode":"210700","level":"3","name":"义县","latitude":"41.534928","longitude":"121.301877"},{"code":"210781","parentCode":"210700","level":"3","name":"凌海市","latitude":"41.152566","longitude":"121.285575"},{"code":"210782","parentCode":"210700","level":"3","name":"北镇市","latitude":"","longitude":""},{"code":"210800","parentCode":"210000","level":"2","name":"营口市","latitude":"40.668651","longitude":"122.233391"},{"code":"210801","parentCode":"210800","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"210802","parentCode":"210800","level":"3","name":"站前区","latitude":"40.70301","longitude":"122.265592"},{"code":"210803","parentCode":"210800","level":"3","name":"西市区","latitude":"40.666949","longitude":"122.210126"},{"code":"210804","parentCode":"210800","level":"3","name":"鲅鱼圈区","latitude":"40.252584","longitude":"122.176897"},{"code":"210811","parentCode":"210800","level":"3","name":"老边区","latitude":"40.672565","longitude":"122.330903"},{"code":"210881","parentCode":"210800","level":"3","name":"盖州市","latitude":"40.235441","longitude":"122.477327"},{"code":"210882","parentCode":"210800","level":"3","name":"大石桥市","latitude":"40.646915","longitude":"122.571551"},{"code":"210900","parentCode":"210000","level":"2","name":"阜新市","latitude":"42.01925","longitude":"121.660822"},{"code":"210901","parentCode":"210900","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"210902","parentCode":"210900","level":"3","name":"海州区","latitude":"41.990902","longitude":"121.652705"},{"code":"210903","parentCode":"210900","level":"3","name":"新邱区","latitude":"42.074628","longitude":"121.824321"},{"code":"210904","parentCode":"210900","level":"3","name":"太平区","latitude":"42.009452","longitude":"121.737753"},{"code":"210905","parentCode":"210900","level":"3","name":"清河门区","latitude":"41.754998","longitude":"121.446839"},{"code":"210911","parentCode":"210900","level":"3","name":"细河区","latitude":"42.043254","longitude":"121.627557"},{"code":"210921","parentCode":"210900","level":"3","name":"阜新蒙古族自治县","latitude":"42.1575","longitude":"121.695578"},{"code":"210922","parentCode":"210900","level":"3","name":"彰武县","latitude":"42.523754","longitude":"122.474173"},{"code":"211000","parentCode":"210000","level":"2","name":"辽阳市","latitude":"41.273339","longitude":"123.172451"},{"code":"211001","parentCode":"211000","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"211002","parentCode":"211000","level":"3","name":"白塔区","latitude":"41.279286","longitude":"123.175163"},{"code":"211003","parentCode":"211000","level":"3","name":"文圣区","latitude":"41.271122","longitude":"123.201216"},{"code":"211004","parentCode":"211000","level":"3","name":"宏伟区","latitude":"41.220764","longitude":"123.220518"},{"code":"211005","parentCode":"211000","level":"3","name":"弓长岭区","latitude":"1.14597","longitude":"123.42628"},{"code":"211011","parentCode":"211000","level":"3","name":"太子河区","latitude":"41.274593","longitude":"123.178374"},{"code":"211021","parentCode":"211000","level":"3","name":"辽阳县","latitude":"41.077281","longitude":"123.219821"},{"code":"211081","parentCode":"211000","level":"3","name":"灯塔市","latitude":"41.420099","longitude":"123.312574"},{"code":"211100","parentCode":"210000","level":"2","name":"盘锦市","latitude":"41.141248","longitude":"122.073228"},{"code":"211101","parentCode":"211100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"211102","parentCode":"211100","level":"3","name":"双台子区","latitude":"41.193225","longitude":"122.032038"},{"code":"211103","parentCode":"211100","level":"3","name":"兴隆台区","latitude":"41.155831","longitude":"121.969629"},{"code":"211121","parentCode":"211100","level":"3","name":"大洼县","latitude":"40.964908","longitude":"122.093755"},{"code":"211122","parentCode":"211100","level":"3","name":"盘山县","latitude":"41.193475","longitude":"121.952166"},{"code":"211200","parentCode":"210000","level":"2","name":"铁岭市","latitude":"42.299757","longitude":"123.85485"},{"code":"211201","parentCode":"211200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"211202","parentCode":"211200","level":"3","name":"银州区","latitude":"42.248295","longitude":"123.858516"},{"code":"211204","parentCode":"211200","level":"3","name":"清河区","latitude":"2.508557","longitude":"124.27578"},{"code":"211221","parentCode":"211200","level":"3","name":"铁岭县","latitude":"42.222765","longitude":"123.914529"},{"code":"211223","parentCode":"211200","level":"3","name":"西丰县","latitude":"42.712739","longitude":"124.738502"},{"code":"211224","parentCode":"211200","level":"3","name":"昌图县","latitude":"43.000462","longitude":"123.946409"},{"code":"211281","parentCode":"211200","level":"3","name":"调兵山市","latitude":"42.44293","longitude":"123.584348"},{"code":"211282","parentCode":"211200","level":"3","name":"开原市","latitude":"42.471223","longitude":"124.283776"},{"code":"211300","parentCode":"210000","level":"2","name":"朝阳市","latitude":"41.571828","longitude":"120.446163"},{"code":"211301","parentCode":"211300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"211302","parentCode":"211300","level":"3","name":"双塔区","latitude":"41.60574","longitude":"120.484073"},{"code":"211303","parentCode":"211300","level":"3","name":"龙城区","latitude":"41.606227","longitude":"120.401333"},{"code":"211321","parentCode":"211300","level":"3","name":"朝阳县","latitude":"41.372796","longitude":"120.305061"},{"code":"211322","parentCode":"211300","level":"3","name":"建平县","latitude":"41.842223","longitude":"119.632527"},{"code":"211324","parentCode":"211300","level":"3","name":"喀喇沁左翼蒙古族自治县","latitude":"","longitude":""},{"code":"211381","parentCode":"211300","level":"3","name":"北票市","latitude":"41.865071","longitude":"120.811885"},{"code":"211382","parentCode":"211300","level":"3","name":"凌源市","latitude":"40.981801","longitude":"119.271543"},{"code":"211400","parentCode":"210000","level":"2","name":"葫芦岛市","latitude":"40.74303","longitude":"120.860758"},{"code":"211401","parentCode":"211400","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"211402","parentCode":"211400","level":"3","name":"连山区","latitude":"40.888537","longitude":"120.688361"},{"code":"211403","parentCode":"211400","level":"3","name":"龙港区","latitude":"40.750993","longitude":"120.904586"},{"code":"211404","parentCode":"211400","level":"3","name":"南票区","latitude":"41.137036","longitude":"120.664645"},{"code":"211421","parentCode":"211400","level":"3","name":"绥中县","latitude":"40.305129","longitude":"120.026302"},{"code":"211422","parentCode":"211400","level":"3","name":"建昌县","latitude":"40.716828","longitude":"119.834892"},{"code":"211481","parentCode":"211400","level":"3","name":"兴城市","latitude":"40.596284","longitude":"120.475527"},{"code":"220000","parentCode":"0","level":"1","name":"吉林省","latitude":"43.678846","longitude":"126.262876"},{"code":"220100","parentCode":"220000","level":"2","name":"长春市","latitude":"43.898338","longitude":"125.313642"},{"code":"220101","parentCode":"220100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"220102","parentCode":"220100","level":"3","name":"南关区","latitude":"43.732191","longitude":"125.419649"},{"code":"220103","parentCode":"220100","level":"3","name":"宽城区","latitude":"43.998252","longitude":"125.344899"},{"code":"220104","parentCode":"220100","level":"3","name":"朝阳区","latitude":"39.958953","longitude":"116.521695"},{"code":"220105","parentCode":"220100","level":"3","name":"二道区","latitude":"43.872223","longitude":"125.611485"},{"code":"220106","parentCode":"220100","level":"3","name":"绿园区","latitude":"43.912165","longitude":"125.191331"},{"code":"220112","parentCode":"220100","level":"3","name":"双阳区","latitude":"43.531747","longitude":"125.712822"},{"code":"220122","parentCode":"220100","level":"3","name":"农安县","latitude":"44.461506","longitude":"125.094327"},{"code":"220181","parentCode":"220100","level":"3","name":"九台市","latitude":"44.209759","longitude":"125.985176"},{"code":"220182","parentCode":"220100","level":"3","name":"榆树市","latitude":"44.879423","longitude":"126.602501"},{"code":"220183","parentCode":"220100","level":"3","name":"德惠市","latitude":"44.510507","longitude":"125.769044"},{"code":"220200","parentCode":"220000","level":"2","name":"吉林市","latitude":"43.678846","longitude":"126.262876"},{"code":"220201","parentCode":"220200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"220202","parentCode":"220200","level":"3","name":"昌邑区","latitude":"44.023898","longitude":"126.326513"},{"code":"220203","parentCode":"220200","level":"3","name":"龙潭区","latitude":"44.100874","longitude":"126.695085"},{"code":"220204","parentCode":"220200","level":"3","name":"船营区","latitude":"43.882172","longitude":"126.389089"},{"code":"220211","parentCode":"220200","level":"3","name":"丰满区","latitude":"43.654515","longitude":"126.698202"},{"code":"220221","parentCode":"220200","level":"3","name":"永吉县","latitude":"3.601481","longitude":"126.22756"},{"code":"220281","parentCode":"220200","level":"3","name":"蛟河市","latitude":"43.716756","longitude":"127.351742"},{"code":"220282","parentCode":"220200","level":"3","name":"桦甸市","latitude":"43.056631","longitude":"127.041392"},{"code":"220283","parentCode":"220200","level":"3","name":"舒兰市","latitude":"44.335465","longitude":"127.116772"},{"code":"220284","parentCode":"220200","level":"3","name":"磐石市","latitude":"43.057456","longitude":"126.174628"},{"code":"220300","parentCode":"220000","level":"2","name":"四平市","latitude":"43.175525","longitude":"124.391382"},{"code":"220301","parentCode":"220300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"220302","parentCode":"220300","level":"3","name":"铁西区","latitude":"1.805724","longitude":"123.35863"},{"code":"220303","parentCode":"220300","level":"3","name":"铁东区","latitude":"41.118235","longitude":"123.020706"},{"code":"220322","parentCode":"220300","level":"3","name":"梨树县","latitude":"43.414438","longitude":"124.380491"},{"code":"220323","parentCode":"220300","level":"3","name":"伊通满族自治县","latitude":"43.346322","longitude":"125.271149"},{"code":"220381","parentCode":"220300","level":"3","name":"公主岭市","latitude":"43.791826","longitude":"124.685882"},{"code":"220382","parentCode":"220300","level":"3","name":"双辽市","latitude":"3.767695","longitude":"123.70852"},{"code":"220400","parentCode":"220000","level":"2","name":"辽源市","latitude":"42.923303","longitude":"125.133686"},{"code":"220401","parentCode":"220400","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"220402","parentCode":"220400","level":"3","name":"龙山区","latitude":"42.913197","longitude":"125.210998"},{"code":"220403","parentCode":"220400","level":"3","name":"西安区","latitude":"42.986365","longitude":"125.150149"},{"code":"220421","parentCode":"220400","level":"3","name":"东丰县","latitude":"42.683934","longitude":"125.454809"},{"code":"220422","parentCode":"220400","level":"3","name":"东辽县","latitude":"42.947925","longitude":"125.184931"},{"code":"220500","parentCode":"220000","level":"2","name":"通化市","latitude":"41.736397","longitude":"125.94265"},{"code":"220501","parentCode":"220500","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"220502","parentCode":"220500","level":"3","name":"东昌区","latitude":"41.677262","longitude":"125.960124"},{"code":"220503","parentCode":"220500","level":"3","name":"二道江区","latitude":"1.772626","longitude":"126.15628"},{"code":"220521","parentCode":"220500","level":"3","name":"通化县","latitude":"41.729156","longitude":"125.857332"},{"code":"220523","parentCode":"220500","level":"3","name":"辉南县","latitude":"42.557949","longitude":"126.342724"},{"code":"220524","parentCode":"220500","level":"3","name":"柳河县","latitude":"42.185665","longitude":"125.917276"},{"code":"220581","parentCode":"220500","level":"3","name":"梅河口市","latitude":"42.54265","longitude":"125.723516"},{"code":"220582","parentCode":"220500","level":"3","name":"集安市","latitude":"41.251411","longitude":"125.998992"},{"code":"220600","parentCode":"220000","level":"2","name":"白山市","latitude":"41.945859","longitude":"126.435798"},{"code":"220601","parentCode":"220600","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"220602","parentCode":"220600","level":"3","name":"浑江区","latitude":"","longitude":""},{"code":"220605","parentCode":"220600","level":"3","name":"江源区","latitude":"","longitude":""},{"code":"220621","parentCode":"220600","level":"3","name":"抚松县","latitude":"42.277909","longitude":"127.623938"},{"code":"220622","parentCode":"220600","level":"3","name":"靖宇县","latitude":"42.449967","longitude":"126.902469"},{"code":"220623","parentCode":"220600","level":"3","name":"长白朝鲜族自治县","latitude":"41.584709","longitude":"127.864358"},{"code":"220681","parentCode":"220600","level":"3","name":"临江市","latitude":"1.816566","longitude":"127.19171"},{"code":"220700","parentCode":"220000","level":"2","name":"松原市","latitude":"45.136049","longitude":"124.832995"},{"code":"220701","parentCode":"220700","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"220702","parentCode":"220700","level":"3","name":"宁江区","latitude":"45.29271","longitude":"124.867571"},{"code":"220721","parentCode":"220700","level":"3","name":"前郭尔罗斯蒙古族自治县","latitude":"","longitude":""},{"code":"220722","parentCode":"220700","level":"3","name":"长岭县","latitude":"44.305645","longitude":"123.866504"},{"code":"220723","parentCode":"220700","level":"3","name":"乾安县","latitude":"44.926914","longitude":"123.969123"},{"code":"220781","parentCode":"220700","level":"3","name":"扶余市","latitude":"","longitude":""},{"code":"220800","parentCode":"220000","level":"2","name":"白城市","latitude":"45.621086","longitude":"122.840777"},{"code":"220801","parentCode":"220800","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"220802","parentCode":"220800","level":"3","name":"洮北区","latitude":"45.623301","longitude":"122.789074"},{"code":"220821","parentCode":"220800","level":"3","name":"镇赉县","latitude":"45.956172","longitude":"123.452272"},{"code":"220822","parentCode":"220800","level":"3","name":"通榆县","latitude":"44.785717","longitude":"122.745291"},{"code":"220881","parentCode":"220800","level":"3","name":"洮南市","latitude":"45.475604","longitude":"122.453677"},{"code":"220882","parentCode":"220800","level":"3","name":"大安市","latitude":"45.432438","longitude":"123.723714"},{"code":"222400","parentCode":"220000","level":"2","name":"延边朝鲜族自治州","latitude":"42.896414","longitude":"129.485902"},{"code":"222401","parentCode":"222400","level":"3","name":"延吉市","latitude":"43.059667","longitude":"129.471302"},{"code":"222402","parentCode":"222400","level":"3","name":"图们市","latitude":"43.030549","longitude":"129.834311"},{"code":"222403","parentCode":"222400","level":"3","name":"敦化市","latitude":"43.560202","longitude":"128.239499"},{"code":"222404","parentCode":"222400","level":"3","name":"珲春市","latitude":"43.074719","longitude":"130.702367"},{"code":"222405","parentCode":"222400","level":"3","name":"龙井市","latitude":"42.844249","longitude":"129.383816"},{"code":"222406","parentCode":"222400","level":"3","name":"和龙市","latitude":"42.466442","longitude":"128.911211"},{"code":"222424","parentCode":"222400","level":"3","name":"汪清县","latitude":"43.540144","longitude":"129.953994"},{"code":"222426","parentCode":"222400","level":"3","name":"安图县","latitude":"42.701033","longitude":"128.437652"},{"code":"230000","parentCode":"0","level":"1","name":"黑龙江省","latitude":"47.356592","longitude":"128.047414"},{"code":"230100","parentCode":"230000","level":"2","name":"哈尔滨市","latitude":"45.773225","longitude":"126.657717"},{"code":"230101","parentCode":"230100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"230102","parentCode":"230100","level":"3","name":"道里区","latitude":".243571","longitude":"107.3434"},{"code":"230103","parentCode":"230100","level":"3","name":"南岗区","latitude":"","longitude":""},{"code":"230104","parentCode":"230100","level":"3","name":"道外区","latitude":"","longitude":""},{"code":"230108","parentCode":"230100","level":"3","name":"平房区","latitude":"45.605686","longitude":"126.658761"},{"code":"230109","parentCode":"230100","level":"3","name":"松北区","latitude":"","longitude":""},{"code":"230110","parentCode":"230100","level":"3","name":"香坊区","latitude":"","longitude":""},{"code":"230111","parentCode":"230100","level":"3","name":"呼兰区","latitude":"","longitude":""},{"code":"230112","parentCode":"230100","level":"3","name":"阿城区","latitude":"","longitude":""},{"code":"230123","parentCode":"230100","level":"3","name":"依兰县","latitude":"46.275637","longitude":"129.721503"},{"code":"230124","parentCode":"230100","level":"3","name":"方正县","latitude":"45.819769","longitude":"128.949419"},{"code":"230125","parentCode":"230100","level":"3","name":"宾县","latitude":"45.783825","longitude":"127.661612"},{"code":"230126","parentCode":"230100","level":"3","name":"巴彦县","latitude":"46.340416","longitude":"127.324287"},{"code":"230127","parentCode":"230100","level":"3","name":"木兰县","latitude":"46.248172","longitude":"127.929838"},{"code":"230128","parentCode":"230100","level":"3","name":"通河县","latitude":"46.247857","longitude":"128.762232"},{"code":"230129","parentCode":"230100","level":"3","name":"延寿县","latitude":"45.48952","longitude":"128.463943"},{"code":"230182","parentCode":"230100","level":"3","name":"双城市","latitude":"45.408171","longitude":"126.188927"},{"code":"230183","parentCode":"230100","level":"3","name":"尚志市","latitude":"5.083893","longitude":"128.31617"},{"code":"230184","parentCode":"230100","level":"3","name":"五常市","latitude":"44.772544","longitude":"127.491113"},{"code":"230200","parentCode":"230000","level":"2","name":"齐齐哈尔市","latitude":"47.3477","longitude":"123.987289"},{"code":"230201","parentCode":"230200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"230202","parentCode":"230200","level":"3","name":"龙沙区","latitude":"47.301073","longitude":"123.944838"},{"code":"230203","parentCode":"230200","level":"3","name":"建华区","latitude":"47.404866","longitude":"124.021279"},{"code":"230204","parentCode":"230200","level":"3","name":"铁锋区","latitude":"47.303489","longitude":"124.262931"},{"code":"230205","parentCode":"230200","level":"3","name":"昂昂溪区","latitude":"47.104048","longitude":"123.972935"},{"code":"230206","parentCode":"230200","level":"3","name":"富拉尔基区","latitude":"47.228952","longitude":"123.571998"},{"code":"230207","parentCode":"230200","level":"3","name":"碾子山区","latitude":"47.585869","longitude":"122.932335"},{"code":"230208","parentCode":"230200","level":"3","name":"梅里斯达斡尔族区","latitude":"47.58308","longitude":"124.005487"},{"code":"230221","parentCode":"230200","level":"3","name":"龙江县","latitude":"47.258895","longitude":"123.089103"},{"code":"230223","parentCode":"230200","level":"3","name":"依安县","latitude":"47.706873","longitude":"125.294633"},{"code":"230224","parentCode":"230200","level":"3","name":"泰来县","latitude":"46.60329","longitude":"123.558048"},{"code":"230225","parentCode":"230200","level":"3","name":"甘南县","latitude":".011583","longitude":"123.8469"},{"code":"230227","parentCode":"230200","level":"3","name":"富裕县","latitude":"47.66582","longitude":"124.571747"},{"code":"230229","parentCode":"230200","level":"3","name":"克山县","latitude":"48.167091","longitude":"125.706471"},{"code":"230230","parentCode":"230200","level":"3","name":"克东县","latitude":"48.009015","longitude":"126.352136"},{"code":"230231","parentCode":"230200","level":"3","name":"拜泉县","latitude":"47.592256","longitude":"126.021786"},{"code":"230281","parentCode":"230200","level":"3","name":"讷河市","latitude":"48.481453","longitude":"125.076553"},{"code":"230300","parentCode":"230000","level":"2","name":"鸡西市","latitude":"45.32154","longitude":"130.941767"},{"code":"230301","parentCode":"230300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"230302","parentCode":"230300","level":"3","name":"鸡冠区","latitude":"45.30761","longitude":"130.959937"},{"code":"230303","parentCode":"230300","level":"3","name":"恒山区","latitude":"45.138571","longitude":"130.916267"},{"code":"230304","parentCode":"230300","level":"3","name":"滴道区","latitude":"45.354342","longitude":"130.734836"},{"code":"230305","parentCode":"230300","level":"3","name":"梨树区","latitude":"45.097064","longitude":"130.765238"},{"code":"230306","parentCode":"230300","level":"3","name":"城子河区","latitude":"45.37969","longitude":"131.027704"},{"code":"230307","parentCode":"230300","level":"3","name":"麻山区","latitude":"45.205826","longitude":"130.566887"},{"code":"230321","parentCode":"230300","level":"3","name":"鸡东县","latitude":"45.273228","longitude":"131.225654"},{"code":"230381","parentCode":"230300","level":"3","name":"虎林市","latitude":"45.997276","longitude":"133.121106"},{"code":"230382","parentCode":"230300","level":"3","name":"密山市","latitude":"45.469765","longitude":"132.176562"},{"code":"230400","parentCode":"230000","level":"2","name":"鹤岗市","latitude":"47.338666","longitude":"130.292472"},{"code":"230401","parentCode":"230400","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"230402","parentCode":"230400","level":"3","name":"向阳区","latitude":"47.35092","longitude":"130.301233"},{"code":"230403","parentCode":"230400","level":"3","name":"工农区","latitude":"47.32777","longitude":"130.277196"},{"code":"230404","parentCode":"230400","level":"3","name":"南山区","latitude":"47.298821","longitude":"130.281765"},{"code":"230405","parentCode":"230400","level":"3","name":"兴安区","latitude":"47.23371","longitude":"130.244375"},{"code":"230406","parentCode":"230400","level":"3","name":"东山区","latitude":"47.483737","longitude":"130.247501"},{"code":"230407","parentCode":"230400","level":"3","name":"兴山区","latitude":"47.393965","longitude":"130.326646"},{"code":"230421","parentCode":"230400","level":"3","name":"萝北县","latitude":"47.746935","longitude":"130.761333"},{"code":"230422","parentCode":"230400","level":"3","name":"绥滨县","latitude":"47.483007","longitude":"131.856595"},{"code":"230500","parentCode":"230000","level":"2","name":"双鸭山市","latitude":"46.655102","longitude":"131.171402"},{"code":"230501","parentCode":"230500","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"230502","parentCode":"230500","level":"3","name":"尖山区","latitude":"46.658525","longitude":"131.178514"},{"code":"230503","parentCode":"230500","level":"3","name":"岭东区","latitude":"46.459522","longitude":"131.246024"},{"code":"230505","parentCode":"230500","level":"3","name":"四方台区","latitude":"46.669775","longitude":"131.308707"},{"code":"230506","parentCode":"230500","level":"3","name":"宝山区","latitude":"46.529228","longitude":"131.564836"},{"code":"230521","parentCode":"230500","level":"3","name":"集贤县","latitude":"46.818437","longitude":"131.150556"},{"code":"230522","parentCode":"230500","level":"3","name":"友谊县","latitude":"46.788593","longitude":"131.854999"},{"code":"230523","parentCode":"230500","level":"3","name":"宝清县","latitude":"46.409383","longitude":"132.409279"},{"code":"230524","parentCode":"230500","level":"3","name":"饶河县","latitude":"47.072629","longitude":"133.729259"},{"code":"230600","parentCode":"230000","level":"2","name":"大庆市","latitude":"46.596709","longitude":"125.02184"},{"code":"230601","parentCode":"230600","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"230602","parentCode":"230600","level":"3","name":"萨尔图区","latitude":"46.663311","longitude":"125.042452"},{"code":"230603","parentCode":"230600","level":"3","name":"龙凤区","latitude":"46.535568","longitude":"125.141767"},{"code":"230604","parentCode":"230600","level":"3","name":"让胡路区","latitude":"46.72916","longitude":"124.838427"},{"code":"230605","parentCode":"230600","level":"3","name":"红岗区","latitude":"46.420779","longitude":"124.914285"},{"code":"230606","parentCode":"230600","level":"3","name":"大同区","latitude":"46.070051","longitude":"124.699077"},{"code":"230621","parentCode":"230600","level":"3","name":"肇州县","latitude":"45.837072","longitude":"125.308969"},{"code":"230622","parentCode":"230600","level":"3","name":"肇源县","latitude":"45.6472","longitude":"124.769044"},{"code":"230623","parentCode":"230600","level":"3","name":"林甸县","latitude":"47.159693","longitude":"124.896783"},{"code":"230624","parentCode":"230600","level":"3","name":"杜尔伯特蒙古族自治县","latitude":"46.561614","longitude":"124.246513"},{"code":"230700","parentCode":"230000","level":"2","name":"伊春市","latitude":"47.734685","longitude":"128.910766"},{"code":"230701","parentCode":"230700","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"230702","parentCode":"230700","level":"3","name":"伊春区","latitude":"7.741959","longitude":"128.90058"},{"code":"230703","parentCode":"230700","level":"3","name":"南岔区","latitude":"46.964156","longitude":"129.538874"},{"code":"230704","parentCode":"230700","level":"3","name":"友好区","latitude":"48.128002","longitude":"128.465964"},{"code":"230705","parentCode":"230700","level":"3","name":"西林区","latitude":"47.500962","longitude":"129.227255"},{"code":"230706","parentCode":"230700","level":"3","name":"翠峦区","latitude":"47.589934","longitude":"128.365411"},{"code":"230707","parentCode":"230700","level":"3","name":"新青区","latitude":"48.216126","longitude":"129.787357"},{"code":"230708","parentCode":"230700","level":"3","name":"美溪区","latitude":"47.768892","longitude":"129.409404"},{"code":"230709","parentCode":"230700","level":"3","name":"金山屯区","latitude":"47.498544","longitude":"129.771903"},{"code":"230710","parentCode":"230700","level":"3","name":"五营区","latitude":"48.229328","longitude":"129.061485"},{"code":"230711","parentCode":"230700","level":"3","name":"乌马河区","latitude":"7.549368","longitude":"128.79469"},{"code":"230712","parentCode":"230700","level":"3","name":"汤旺河区","latitude":"48.563263","longitude":"129.538754"},{"code":"230713","parentCode":"230700","level":"3","name":"带岭区","latitude":"47.090162","longitude":"128.861475"},{"code":"230714","parentCode":"230700","level":"3","name":"乌伊岭区","latitude":"48.836655","longitude":"129.498936"},{"code":"230715","parentCode":"230700","level":"3","name":"红星区","latitude":"48.29802","longitude":"129.251919"},{"code":"230716","parentCode":"230700","level":"3","name":"上甘岭区","latitude":"48.036509","longitude":"129.022399"},{"code":"230722","parentCode":"230700","level":"3","name":"嘉荫县","latitude":"8.76952","longitude":"130.00825"},{"code":"230781","parentCode":"230700","level":"3","name":"铁力市","latitude":"46.866329","longitude":"128.552517"},{"code":"230800","parentCode":"230000","level":"2","name":"佳木斯市","latitude":"46.81378","longitude":"130.284735"},{"code":"230801","parentCode":"230800","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"230803","parentCode":"230800","level":"3","name":"向阳区","latitude":"47.35092","longitude":"130.301233"},{"code":"230804","parentCode":"230800","level":"3","name":"前进区","latitude":"","longitude":""},{"code":"230805","parentCode":"230800","level":"3","name":"东风区","latitude":"","longitude":""},{"code":"230811","parentCode":"230800","level":"3","name":"郊区","latitude":"37.911504","longitude":"113.568086"},{"code":"230822","parentCode":"230800","level":"3","name":"桦南县","latitude":"46.306672","longitude":"130.637015"},{"code":"230826","parentCode":"230800","level":"3","name":"桦川县","latitude":"46.989258","longitude":"130.963018"},{"code":"230828","parentCode":"230800","level":"3","name":"汤原县","latitude":"46.988319","longitude":"130.072406"},{"code":"230833","parentCode":"230800","level":"3","name":"抚远县","latitude":"47.926105","longitude":"134.296044"},{"code":"230881","parentCode":"230800","level":"3","name":"同江市","latitude":"47.833685","longitude":"133.273328"},{"code":"230882","parentCode":"230800","level":"3","name":"富锦市","latitude":"47.170673","longitude":"132.539001"},{"code":"230900","parentCode":"230000","level":"2","name":"七台河市","latitude":"45.775005","longitude":"131.019048"},{"code":"230901","parentCode":"230900","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"230902","parentCode":"230900","level":"3","name":"新兴区","latitude":"45.813494","longitude":"130.893188"},{"code":"230903","parentCode":"230900","level":"3","name":"桃山区","latitude":"45.770093","longitude":"130.992503"},{"code":"230904","parentCode":"230900","level":"3","name":"茄子河区","latitude":"45.883168","longitude":"131.475224"},{"code":"230921","parentCode":"230900","level":"3","name":"勃利县","latitude":"45.930545","longitude":"130.818169"},{"code":"231000","parentCode":"230000","level":"2","name":"牡丹江市","latitude":"44.588521","longitude":"129.608035"},{"code":"231001","parentCode":"231000","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"231002","parentCode":"231000","level":"3","name":"东安区","latitude":"44.408404","longitude":"129.860447"},{"code":"231003","parentCode":"231000","level":"3","name":"阳明区","latitude":"44.587975","longitude":"129.783915"},{"code":"231004","parentCode":"231000","level":"3","name":"爱民区","latitude":"44.685921","longitude":"129.544566"},{"code":"231005","parentCode":"231000","level":"3","name":"西安区","latitude":"42.986365","longitude":"125.150149"},{"code":"231024","parentCode":"231000","level":"3","name":"东宁县","latitude":"44.032436","longitude":"130.850389"},{"code":"231025","parentCode":"231000","level":"3","name":"林口县","latitude":"45.396102","longitude":"130.023181"},{"code":"231081","parentCode":"231000","level":"3","name":"绥芬河市","latitude":"44.408005","longitude":"131.102457"},{"code":"231083","parentCode":"231000","level":"3","name":"海林市","latitude":"44.903617","longitude":"129.221414"},{"code":"231084","parentCode":"231000","level":"3","name":"宁安市","latitude":"44.058017","longitude":"129.215317"},{"code":"231085","parentCode":"231000","level":"3","name":"穆棱市","latitude":"44.57687","longitude":"130.395526"},{"code":"231100","parentCode":"230000","level":"2","name":"黑河市","latitude":"50.25069","longitude":"127.50083"},{"code":"231101","parentCode":"231100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"231102","parentCode":"231100","level":"3","name":"爱辉区","latitude":"50.218245","longitude":"126.764262"},{"code":"231121","parentCode":"231100","level":"3","name":"嫩江县","latitude":"49.621866","longitude":"125.771275"},{"code":"231123","parentCode":"231100","level":"3","name":"逊克县","latitude":"48.88674","longitude":"128.370877"},{"code":"231124","parentCode":"231100","level":"3","name":"孙吴县","latitude":"49.370656","longitude":"127.316672"},{"code":"231181","parentCode":"231100","level":"3","name":"北安市","latitude":"48.115946","longitude":"127.111546"},{"code":"231182","parentCode":"231100","level":"3","name":"五大连池市","latitude":"48.749166","longitude":"126.634501"},{"code":"231200","parentCode":"230000","level":"2","name":"绥化市","latitude":"46.646064","longitude":"126.989095"},{"code":"231201","parentCode":"231200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"231202","parentCode":"231200","level":"3","name":"北林区","latitude":"46.747537","longitude":"126.957863"},{"code":"231221","parentCode":"231200","level":"3","name":"望奎县","latitude":"46.869481","longitude":"126.593023"},{"code":"231222","parentCode":"231200","level":"3","name":"兰西县","latitude":"46.35835","longitude":"126.213543"},{"code":"231223","parentCode":"231200","level":"3","name":"青冈县","latitude":"46.846561","longitude":"125.960524"},{"code":"231224","parentCode":"231200","level":"3","name":"庆安县","latitude":"7.070366","longitude":"127.84449"},{"code":"231225","parentCode":"231200","level":"3","name":"明水县","latitude":"47.201247","longitude":"125.841268"},{"code":"231226","parentCode":"231200","level":"3","name":"绥棱县","latitude":"47.584143","longitude":"127.719413"},{"code":"231281","parentCode":"231200","level":"3","name":"安达市","latitude":"46.535467","longitude":"125.384552"},{"code":"231282","parentCode":"231200","level":"3","name":"肇东市","latitude":"46.009306","longitude":"125.849731"},{"code":"231283","parentCode":"231200","level":"3","name":"海伦市","latitude":"47.44727","longitude":"126.897129"},{"code":"232700","parentCode":"230000","level":"2","name":"大兴安岭地区","latitude":"51.991789","longitude":"124.196104"},{"code":"232721","parentCode":"232700","level":"3","name":"呼玛县","latitude":"51.81313","longitude":"124.912002"},{"code":"232722","parentCode":"232700","level":"3","name":"塔河县","latitude":"52.716506","longitude":"124.640203"},{"code":"232723","parentCode":"232700","level":"3","name":"漠河县","latitude":"52.945659","longitude":"122.715721"},{"code":"310000","parentCode":"0","level":"1","name":"上海市","latitude":"31.249162","longitude":"121.487899"},{"code":"310100","parentCode":"310000","level":"2","name":"市辖区","latitude":"","longitude":""},{"code":"310101","parentCode":"310100","level":"3","name":"黄浦区","latitude":"31.227203","longitude":"121.496072"},{"code":"310104","parentCode":"310100","level":"3","name":"徐汇区","latitude":"31.169152","longitude":"121.446235"},{"code":"310105","parentCode":"310100","level":"3","name":"长宁区","latitude":"31.213301","longitude":"121.387616"},{"code":"310106","parentCode":"310100","level":"3","name":"静安区","latitude":"31.235381","longitude":"121.454756"},{"code":"310107","parentCode":"310100","level":"3","name":"普陀区","latitude":"31.263743","longitude":"121.398443"},{"code":"310108","parentCode":"310100","level":"3","name":"闸北区","latitude":"31.288044","longitude":"121.457769"},{"code":"310109","parentCode":"310100","level":"3","name":"虹口区","latitude":"31.282497","longitude":"121.491919"},{"code":"310110","parentCode":"310100","level":"3","name":"杨浦区","latitude":"31.30451","longitude":"121.535717"},{"code":"310112","parentCode":"310100","level":"3","name":"闵行区","latitude":"31.093538","longitude":"121.425024"},{"code":"310113","parentCode":"310100","level":"3","name":"宝山区","latitude":"46.529228","longitude":"131.564836"},{"code":"310114","parentCode":"310100","level":"3","name":"嘉定区","latitude":"31.364338","longitude":"121.251014"},{"code":"310115","parentCode":"310100","level":"3","name":"浦东新区","latitude":"31.230895","longitude":"121.638481"},{"code":"310116","parentCode":"310100","level":"3","name":"金山区","latitude":"30.835081","longitude":"121.248408"},{"code":"310117","parentCode":"310100","level":"3","name":"松江区","latitude":"31.021245","longitude":"121.226791"},{"code":"310118","parentCode":"310100","level":"3","name":"青浦区","latitude":"31.130862","longitude":"121.091425"},{"code":"310120","parentCode":"310100","level":"3","name":"奉贤区","latitude":"30.915122","longitude":"121.560642"},{"code":"310200","parentCode":"310000","level":"2","name":"市辖县","latitude":"","longitude":""},{"code":"310230","parentCode":"310200","level":"3","name":"崇明县","latitude":"31.633565","longitude":"121.535398"},{"code":"320000","parentCode":"0","level":"1","name":"江苏省","latitude":"33.013797","longitude":"119.368489"},{"code":"320100","parentCode":"320000","level":"2","name":"南京市","latitude":"32.057236","longitude":"118.778074"},{"code":"320101","parentCode":"320100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"320102","parentCode":"320100","level":"3","name":"玄武区","latitude":"32.071766","longitude":"118.848937"},{"code":"320104","parentCode":"320100","level":"3","name":"秦淮区","latitude":"32.007969","longitude":"118.817221"},{"code":"320105","parentCode":"320100","level":"3","name":"建邺区","latitude":"32.012518","longitude":"118.713342"},{"code":"320106","parentCode":"320100","level":"3","name":"鼓楼区","latitude":"32.068604","longitude":"118.765057"},{"code":"320111","parentCode":"320100","level":"3","name":"浦口区","latitude":"32.059062","longitude":"118.569125"},{"code":"320113","parentCode":"320100","level":"3","name":"栖霞区","latitude":"32.169424","longitude":"118.963725"},{"code":"320114","parentCode":"320100","level":"3","name":"雨花台区","latitude":"31.954552","longitude":"118.721979"},{"code":"320115","parentCode":"320100","level":"3","name":"江宁区","latitude":"31.863971","longitude":"118.835418"},{"code":"320116","parentCode":"320100","level":"3","name":"六合区","latitude":"32.40064","longitude":"118.848166"},{"code":"320117","parentCode":"320100","level":"3","name":"溧水区","latitude":"","longitude":""},{"code":"320118","parentCode":"320100","level":"3","name":"高淳区","latitude":"","longitude":""},{"code":"320200","parentCode":"320000","level":"2","name":"无锡市","latitude":"31.570037","longitude":"120.305456"},{"code":"320201","parentCode":"320200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"320202","parentCode":"320200","level":"3","name":"崇安区","latitude":"31.597341","longitude":"120.328903"},{"code":"320203","parentCode":"320200","level":"3","name":"南长区","latitude":"31.542866","longitude":"120.315798"},{"code":"320204","parentCode":"320200","level":"3","name":"北塘区","latitude":"31.609369","longitude":"120.282126"},{"code":"320205","parentCode":"320200","level":"3","name":"锡山区","latitude":"31.615587","longitude":"120.491008"},{"code":"320206","parentCode":"320200","level":"3","name":"惠山区","latitude":"31.656376","longitude":"120.215294"},{"code":"320211","parentCode":"320200","level":"3","name":"滨湖区","latitude":"31.466579","longitude":"120.248502"},{"code":"320281","parentCode":"320200","level":"3","name":"江阴市","latitude":"31.837425","longitude":"120.310679"},{"code":"320282","parentCode":"320200","level":"3","name":"宜兴市","latitude":"31.362245","longitude":"119.790265"},{"code":"320300","parentCode":"320000","level":"2","name":"徐州市","latitude":"34.271553","longitude":"117.188107"},{"code":"320301","parentCode":"320300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"320302","parentCode":"320300","level":"3","name":"鼓楼区","latitude":"32.068604","longitude":"118.765057"},{"code":"320303","parentCode":"320300","level":"3","name":"云龙区","latitude":"34.222487","longitude":"117.276176"},{"code":"320305","parentCode":"320300","level":"3","name":"贾汪区","latitude":"34.410528","longitude":"117.498246"},{"code":"320311","parentCode":"320300","level":"3","name":"泉山区","latitude":"34.241947","longitude":"117.175584"},{"code":"320312","parentCode":"320300","level":"3","name":"铜山区","latitude":"","longitude":""},{"code":"320321","parentCode":"320300","level":"3","name":"丰县","latitude":"34.695773","longitude":"116.615733"},{"code":"320322","parentCode":"320300","level":"3","name":"沛县","latitude":"34.700648","longitude":"116.911468"},{"code":"320324","parentCode":"320300","level":"3","name":"睢宁县","latitude":"33.946571","longitude":"117.890364"},{"code":"320381","parentCode":"320300","level":"3","name":"新沂市","latitude":"34.284443","longitude":"118.344121"},{"code":"320382","parentCode":"320300","level":"3","name":"邳州市","latitude":"4.402946","longitude":"117.90306"},{"code":"320400","parentCode":"320000","level":"2","name":"常州市","latitude":"31.771397","longitude":"119.981861"},{"code":"320401","parentCode":"320400","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"320402","parentCode":"320400","level":"3","name":"天宁区","latitude":"31.777803","longitude":"120.001766"},{"code":"320404","parentCode":"320400","level":"3","name":"钟楼区","latitude":"31.798511","longitude":"119.912439"},{"code":"320405","parentCode":"320400","level":"3","name":"戚墅堰区","latitude":"31.756898","longitude":"120.056359"},{"code":"320411","parentCode":"320400","level":"3","name":"新北区","latitude":"31.939946","longitude":"119.903154"},{"code":"320412","parentCode":"320400","level":"3","name":"武进区","latitude":"31.672903","longitude":"119.943432"},{"code":"320481","parentCode":"320400","level":"3","name":"溧阳市","latitude":"31.425242","longitude":"119.382839"},{"code":"320482","parentCode":"320400","level":"3","name":"金坛市","latitude":"31.723054","longitude":"119.515038"},{"code":"320500","parentCode":"320000","level":"2","name":"苏州市","latitude":"31.317987","longitude":"120.619907"},{"code":"320501","parentCode":"320500","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"320505","parentCode":"320500","level":"3","name":"虎丘区","latitude":"31.351869","longitude":"120.478424"},{"code":"320506","parentCode":"320500","level":"3","name":"吴中区","latitude":"31.17987","longitude":"120.365776"},{"code":"320507","parentCode":"320500","level":"3","name":"相城区","latitude":"31.450775","longitude":"120.646853"},{"code":"320508","parentCode":"320500","level":"3","name":"姑苏区","latitude":"","longitude":""},{"code":"320509","parentCode":"320500","level":"3","name":"吴江区","latitude":"","longitude":""},{"code":"320581","parentCode":"320500","level":"3","name":"常熟市","latitude":"31.669446","longitude":"120.831486"},{"code":"320582","parentCode":"320500","level":"3","name":"张家港市","latitude":"31.907812","longitude":"120.627279"},{"code":"320583","parentCode":"320500","level":"3","name":"昆山市","latitude":"31.328937","longitude":"120.965808"},{"code":"320585","parentCode":"320500","level":"3","name":"太仓市","latitude":"31.571904","longitude":"121.158978"},{"code":"320600","parentCode":"320000","level":"2","name":"南通市","latitude":"32.014665","longitude":"120.873801"},{"code":"320601","parentCode":"320600","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"320602","parentCode":"320600","level":"3","name":"崇川区","latitude":"31.962661","longitude":"120.887599"},{"code":"320611","parentCode":"320600","level":"3","name":"港闸区","latitude":"32.071256","longitude":"120.823875"},{"code":"320612","parentCode":"320600","level":"3","name":"通州区","latitude":"39.809815","longitude":"116.740079"},{"code":"320621","parentCode":"320600","level":"3","name":"海安县","latitude":"32.553985","longitude":"120.473927"},{"code":"320623","parentCode":"320600","level":"3","name":"如东县","latitude":"32.387662","longitude":"121.059244"},{"code":"320681","parentCode":"320600","level":"3","name":"启东市","latitude":"31.871302","longitude":"121.678822"},{"code":"320682","parentCode":"320600","level":"3","name":"如皋市","latitude":"32.273616","longitude":"120.580144"},{"code":"320684","parentCode":"320600","level":"3","name":"海门市","latitude":"1.956039","longitude":"121.31247"},{"code":"320700","parentCode":"320000","level":"2","name":"连云港市","latitude":"34.601549","longitude":"119.173872"},{"code":"320701","parentCode":"320700","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"320703","parentCode":"320700","level":"3","name":"连云区","latitude":"34.638922","longitude":"119.467017"},{"code":"320705","parentCode":"320700","level":"3","name":"新浦区","latitude":"34.656801","longitude":"119.260574"},{"code":"320706","parentCode":"320700","level":"3","name":"海州区","latitude":"41.990902","longitude":"121.652705"},{"code":"320721","parentCode":"320700","level":"3","name":"赣榆县","latitude":".88953","longitude":"119.0414"},{"code":"320722","parentCode":"320700","level":"3","name":"东海县","latitude":"4.556383","longitude":"118.79231"},{"code":"320723","parentCode":"320700","level":"3","name":"灌云县","latitude":"34.406832","longitude":"119.392775"},{"code":"320724","parentCode":"320700","level":"3","name":"灌南县","latitude":"34.175195","longitude":"119.446397"},{"code":"320800","parentCode":"320000","level":"2","name":"淮安市","latitude":"33.606513","longitude":"119.030186"},{"code":"320801","parentCode":"320800","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"320802","parentCode":"320800","level":"3","name":"清河区","latitude":"2.508557","longitude":"124.27578"},{"code":"320803","parentCode":"320800","level":"3","name":"淮安区","latitude":"","longitude":""},{"code":"320804","parentCode":"320800","level":"3","name":"淮阴区","latitude":"33.664059","longitude":"118.935664"},{"code":"320811","parentCode":"320800","level":"3","name":"清浦区","latitude":"33.488642","longitude":"119.025325"},{"code":"320826","parentCode":"320800","level":"3","name":"涟水县","latitude":"33.884155","longitude":"119.324957"},{"code":"320829","parentCode":"320800","level":"3","name":"洪泽县","latitude":".235206","longitude":"118.8282"},{"code":"320830","parentCode":"320800","level":"3","name":"盱眙县","latitude":"32.971613","longitude":"118.538232"},{"code":"320831","parentCode":"320800","level":"3","name":"金湖县","latitude":"33.025834","longitude":"119.145631"},{"code":"320900","parentCode":"320000","level":"2","name":"盐城市","latitude":"33.379862","longitude":"120.148872"},{"code":"320901","parentCode":"320900","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"320902","parentCode":"320900","level":"3","name":"亭湖区","latitude":"","longitude":""},{"code":"320903","parentCode":"320900","level":"3","name":"盐都区","latitude":"","longitude":""},{"code":"320921","parentCode":"320900","level":"3","name":"响水县","latitude":"34.232797","longitude":"119.797602"},{"code":"320922","parentCode":"320900","level":"3","name":"滨海县","latitude":"34.092317","longitude":"120.026609"},{"code":"320923","parentCode":"320900","level":"3","name":"阜宁县","latitude":"3.711976","longitude":"119.70499"},{"code":"320924","parentCode":"320900","level":"3","name":"射阳县","latitude":"33.745462","longitude":"120.279505"},{"code":"320925","parentCode":"320900","level":"3","name":"建湖县","latitude":"33.488908","longitude":"119.836497"},{"code":"320981","parentCode":"320900","level":"3","name":"东台市","latitude":"32.791443","longitude":"120.563769"},{"code":"320982","parentCode":"320900","level":"3","name":"大丰市","latitude":"33.190868","longitude":"120.546274"},{"code":"321000","parentCode":"320000","level":"2","name":"扬州市","latitude":"32.408505","longitude":"119.427778"},{"code":"321001","parentCode":"321000","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"321002","parentCode":"321000","level":"3","name":"广陵区","latitude":"32.39567","longitude":"119.486678"},{"code":"321003","parentCode":"321000","level":"3","name":"邗江区","latitude":"32.42583","longitude":"119.458264"},{"code":"321012","parentCode":"321000","level":"3","name":"江都区","latitude":"","longitude":""},{"code":"321023","parentCode":"321000","level":"3","name":"宝应县","latitude":"33.225834","longitude":"119.455651"},{"code":"321081","parentCode":"321000","level":"3","name":"仪征市","latitude":"32.392636","longitude":"119.200955"},{"code":"321084","parentCode":"321000","level":"3","name":"高邮市","latitude":"32.835944","longitude":"119.503407"},{"code":"321100","parentCode":"320000","level":"2","name":"镇江市","latitude":"32.204409","longitude":"119.455835"},{"code":"321101","parentCode":"321100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"321102","parentCode":"321100","level":"3","name":"京口区","latitude":"32.201996","longitude":"119.584822"},{"code":"321111","parentCode":"321100","level":"3","name":"润州区","latitude":"2.196647","longitude":"119.43092"},{"code":"321112","parentCode":"321100","level":"3","name":"丹徒区","latitude":"32.114041","longitude":"119.498972"},{"code":"321181","parentCode":"321100","level":"3","name":"丹阳市","latitude":"31.960263","longitude":"119.644304"},{"code":"321182","parentCode":"321100","level":"3","name":"扬中市","latitude":"32.189469","longitude":"119.845138"},{"code":"321183","parentCode":"321100","level":"3","name":"句容市","latitude":"1.932635","longitude":"119.20708"},{"code":"321200","parentCode":"320000","level":"2","name":"泰州市","latitude":"32.476053","longitude":"119.919606"},{"code":"321201","parentCode":"321200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"321202","parentCode":"321200","level":"3","name":"海陵区","latitude":"32.488258","longitude":"119.921174"},{"code":"321203","parentCode":"321200","level":"3","name":"高港区","latitude":"32.330075","longitude":"119.925744"},{"code":"321204","parentCode":"321200","level":"3","name":"姜堰区","latitude":"","longitude":""},{"code":"321281","parentCode":"321200","level":"3","name":"兴化市","latitude":"32.961954","longitude":"119.996418"},{"code":"321282","parentCode":"321200","level":"3","name":"靖江市","latitude":"32.039443","longitude":"120.276899"},{"code":"321283","parentCode":"321200","level":"3","name":"泰兴市","latitude":"32.213679","longitude":"120.135346"},{"code":"321300","parentCode":"320000","level":"2","name":"宿迁市","latitude":"33.95205","longitude":"118.296893"},{"code":"321301","parentCode":"321300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"321302","parentCode":"321300","level":"3","name":"宿城区","latitude":"3.862829","longitude":"118.27464"},{"code":"321311","parentCode":"321300","level":"3","name":"宿豫区","latitude":"","longitude":""},{"code":"321322","parentCode":"321300","level":"3","name":"沭阳县","latitude":"4.154014","longitude":"118.85775"},{"code":"321323","parentCode":"321300","level":"3","name":"泗阳县","latitude":"33.708801","longitude":"118.656941"},{"code":"321324","parentCode":"321300","level":"3","name":"泗洪县","latitude":"33.425955","longitude":"118.312551"},{"code":"330000","parentCode":"0","level":"1","name":"浙江省","latitude":"29.159494","longitude":"119.957202"},{"code":"330100","parentCode":"330000","level":"2","name":"杭州市","latitude":"30.259244","longitude":"120.219375"},{"code":"330101","parentCode":"330100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"330102","parentCode":"330100","level":"3","name":"上城区","latitude":"30.232358","longitude":"120.180126"},{"code":"330103","parentCode":"330100","level":"3","name":"下城区","latitude":"30.310288","longitude":"120.186535"},{"code":"330104","parentCode":"330100","level":"3","name":"江干区","latitude":"30.315832","longitude":"120.303823"},{"code":"330105","parentCode":"330100","level":"3","name":"拱墅区","latitude":"30.344732","longitude":"120.158845"},{"code":"330106","parentCode":"330100","level":"3","name":"西湖区","latitude":"28.657326","longitude":"115.898948"},{"code":"330108","parentCode":"330100","level":"3","name":"滨江区","latitude":"0.187588","longitude":"120.19237"},{"code":"330109","parentCode":"330100","level":"3","name":"萧山区","latitude":"30.172894","longitude":"120.389081"},{"code":"330110","parentCode":"330100","level":"3","name":"余杭区","latitude":"30.38812","longitude":"119.998089"},{"code":"330122","parentCode":"330100","level":"3","name":"桐庐县","latitude":"29.836582","longitude":"119.560462"},{"code":"330127","parentCode":"330100","level":"3","name":"淳安县","latitude":"29.614714","longitude":"118.895765"},{"code":"330182","parentCode":"330100","level":"3","name":"建德市","latitude":"29.487115","longitude":"119.379533"},{"code":"330183","parentCode":"330100","level":"3","name":"富阳市","latitude":"30.001094","longitude":"119.846692"},{"code":"330185","parentCode":"330100","level":"3","name":"临安市","latitude":"30.207684","longitude":"119.350295"},{"code":"330200","parentCode":"330000","level":"2","name":"宁波市","latitude":"29.885259","longitude":"121.579006"},{"code":"330201","parentCode":"330200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"330203","parentCode":"330200","level":"3","name":"海曙区","latitude":"29.876801","longitude":"121.535395"},{"code":"330204","parentCode":"330200","level":"3","name":"江东区","latitude":"29.875392","longitude":"121.598001"},{"code":"330205","parentCode":"330200","level":"3","name":"江北区","latitude":"29.966392","longitude":"121.493299"},{"code":"330206","parentCode":"330200","level":"3","name":"北仑区","latitude":"29.868332","longitude":"121.889419"},{"code":"330211","parentCode":"330200","level":"3","name":"镇海区","latitude":"9.995449","longitude":"121.61663"},{"code":"330212","parentCode":"330200","level":"3","name":"鄞州区","latitude":"29.785459","longitude":"121.537835"},{"code":"330225","parentCode":"330200","level":"3","name":"象山县","latitude":"29.378771","longitude":"121.858666"},{"code":"330226","parentCode":"330200","level":"3","name":"宁海县","latitude":"29.314474","longitude":"121.463624"},{"code":"330281","parentCode":"330200","level":"3","name":"余姚市","latitude":"29.996457","longitude":"121.152779"},{"code":"330282","parentCode":"330200","level":"3","name":"慈溪市","latitude":"30.189257","longitude":"121.338408"},{"code":"330283","parentCode":"330200","level":"3","name":"奉化市","latitude":"29.617073","longitude":"121.377186"},{"code":"330300","parentCode":"330000","level":"2","name":"温州市","latitude":"28.002838","longitude":"120.690635"},{"code":"330301","parentCode":"330300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"330302","parentCode":"330300","level":"3","name":"鹿城区","latitude":"28.067865","longitude":"120.565799"},{"code":"330303","parentCode":"330300","level":"3","name":"龙湾区","latitude":"27.913341","longitude":"120.811078"},{"code":"330304","parentCode":"330300","level":"3","name":"瓯海区","latitude":"27.972177","longitude":"120.558404"},{"code":"330322","parentCode":"330300","level":"3","name":"洞头县","latitude":"27.903664","longitude":"121.125826"},{"code":"330324","parentCode":"330300","level":"3","name":"永嘉县","latitude":"28.33639","longitude":"120.668809"},{"code":"330326","parentCode":"330300","level":"3","name":"平阳县","latitude":"27.637701","longitude":"120.389387"},{"code":"330327","parentCode":"330300","level":"3","name":"苍南县","latitude":"27.434436","longitude":"120.445543"},{"code":"330328","parentCode":"330300","level":"3","name":"文成县","latitude":"27.812713","longitude":"120.028422"},{"code":"330329","parentCode":"330300","level":"3","name":"泰顺县","latitude":"27.536407","longitude":"119.884868"},{"code":"330381","parentCode":"330300","level":"3","name":"瑞安市","latitude":"7.829231","longitude":"120.46834"},{"code":"330382","parentCode":"330300","level":"3","name":"乐清市","latitude":"28.261839","longitude":"121.016175"},{"code":"330400","parentCode":"330000","level":"2","name":"嘉兴市","latitude":"30.773992","longitude":"120.760428"},{"code":"330401","parentCode":"330400","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"330402","parentCode":"330400","level":"3","name":"南湖区","latitude":"30.716358","longitude":"120.844535"},{"code":"330411","parentCode":"330400","level":"3","name":"秀洲区","latitude":"30.777679","longitude":"120.691907"},{"code":"330421","parentCode":"330400","level":"3","name":"嘉善县","latitude":"30.905748","longitude":"120.908873"},{"code":"330424","parentCode":"330400","level":"3","name":"海盐县","latitude":"30.526043","longitude":"120.885576"},{"code":"330481","parentCode":"330400","level":"3","name":"海宁市","latitude":"30.442177","longitude":"120.618727"},{"code":"330482","parentCode":"330400","level":"3","name":"平湖市","latitude":"30.716529","longitude":"121.105839"},{"code":"330483","parentCode":"330400","level":"3","name":"桐乡市","latitude":"30.612341","longitude":"120.490411"},{"code":"330500","parentCode":"330000","level":"2","name":"湖州市","latitude":"30.877925","longitude":"120.137243"},{"code":"330501","parentCode":"330500","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"330502","parentCode":"330500","level":"3","name":"吴兴区","latitude":"30.808545","longitude":"120.088919"},{"code":"330503","parentCode":"330500","level":"3","name":"南浔区","latitude":"30.766831","longitude":"120.309147"},{"code":"330521","parentCode":"330500","level":"3","name":"德清县","latitude":"30.567583","longitude":"120.049831"},{"code":"330522","parentCode":"330500","level":"3","name":"长兴县","latitude":"0.983353","longitude":"119.81942"},{"code":"330523","parentCode":"330500","level":"3","name":"安吉县","latitude":"30.62637","longitude":"119.583158"},{"code":"330600","parentCode":"330000","level":"2","name":"绍兴市","latitude":"30.002365","longitude":"120.592467"},{"code":"330601","parentCode":"330600","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"330602","parentCode":"330600","level":"3","name":"越城区","latitude":"30.015793","longitude":"120.618327"},{"code":"330621","parentCode":"330600","level":"3","name":"绍兴县","latitude":"29.968789","longitude":"120.572451"},{"code":"330624","parentCode":"330600","level":"3","name":"新昌县","latitude":"29.414314","longitude":"120.975702"},{"code":"330681","parentCode":"330600","level":"3","name":"诸暨市","latitude":"29.6994","longitude":"120.281434"},{"code":"330682","parentCode":"330600","level":"3","name":"上虞市","latitude":"29.97804","longitude":"120.889432"},{"code":"330683","parentCode":"330600","level":"3","name":"嵊州市","latitude":"29.591008","longitude":"120.761431"},{"code":"330700","parentCode":"330000","level":"2","name":"金华市","latitude":"29.102899","longitude":"119.652576"},{"code":"330701","parentCode":"330700","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"330702","parentCode":"330700","level":"3","name":"婺城区","latitude":"28.98454","longitude":"119.517572"},{"code":"330703","parentCode":"330700","level":"3","name":"金东区","latitude":"29.155526","longitude":"119.809227"},{"code":"330723","parentCode":"330700","level":"3","name":"武义县","latitude":"28.774056","longitude":"119.720833"},{"code":"330726","parentCode":"330700","level":"3","name":"浦江县","latitude":"29.526266","longitude":"119.910488"},{"code":"330727","parentCode":"330700","level":"3","name":"磐安县","latitude":"29.044202","longitude":"120.567447"},{"code":"330781","parentCode":"330700","level":"3","name":"兰溪市","latitude":"29.284103","longitude":"119.533338"},{"code":"330782","parentCode":"330700","level":"3","name":"义乌市","latitude":"29.306444","longitude":"120.067296"},{"code":"330783","parentCode":"330700","level":"3","name":"东阳市","latitude":"29.237427","longitude":"120.380818"},{"code":"330784","parentCode":"330700","level":"3","name":"永康市","latitude":"28.940177","longitude":"120.108684"},{"code":"330800","parentCode":"330000","level":"2","name":"衢州市","latitude":"28.95691","longitude":"118.875842"},{"code":"330801","parentCode":"330800","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"330802","parentCode":"330800","level":"3","name":"柯城区","latitude":"28.998535","longitude":"118.813003"},{"code":"330803","parentCode":"330800","level":"3","name":"衢江区","latitude":"28.941983","longitude":"118.939044"},{"code":"330822","parentCode":"330800","level":"3","name":"常山县","latitude":"8.973666","longitude":"118.54767"},{"code":"330824","parentCode":"330800","level":"3","name":"开化县","latitude":"9.189938","longitude":"118.33165"},{"code":"330825","parentCode":"330800","level":"3","name":"龙游县","latitude":"28.997079","longitude":"119.198664"},{"code":"330881","parentCode":"330800","level":"3","name":"江山市","latitude":"28.58197","longitude":"118.607086"},{"code":"330900","parentCode":"330000","level":"2","name":"舟山市","latitude":"30.03601","longitude":"122.169872"},{"code":"330901","parentCode":"330900","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"330902","parentCode":"330900","level":"3","name":"定海区","latitude":"30.064847","longitude":"122.073024"},{"code":"330903","parentCode":"330900","level":"3","name":"普陀区","latitude":"31.263743","longitude":"121.398443"},{"code":"330921","parentCode":"330900","level":"3","name":"岱山县","latitude":"30.319416","longitude":"122.260359"},{"code":"330922","parentCode":"330900","level":"3","name":"嵊泗县","latitude":"30.705004","longitude":"122.481686"},{"code":"331000","parentCode":"330000","level":"2","name":"台州市","latitude":"28.668283","longitude":"121.440613"},{"code":"331001","parentCode":"331000","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"331002","parentCode":"331000","level":"3","name":"椒江区","latitude":"28.657016","longitude":"121.467376"},{"code":"331003","parentCode":"331000","level":"3","name":"黄岩区","latitude":"28.604655","longitude":"121.088318"},{"code":"331004","parentCode":"331000","level":"3","name":"路桥区","latitude":"28.548659","longitude":"121.450242"},{"code":"331021","parentCode":"331000","level":"3","name":"玉环县","latitude":"28.179738","longitude":"121.284426"},{"code":"331022","parentCode":"331000","level":"3","name":"三门县","latitude":"29.017744","longitude":"121.488229"},{"code":"331023","parentCode":"331000","level":"3","name":"天台县","latitude":"29.151779","longitude":"120.985563"},{"code":"331024","parentCode":"331000","level":"3","name":"仙居县","latitude":"28.738742","longitude":"120.640606"},{"code":"331081","parentCode":"331000","level":"3","name":"温岭市","latitude":"28.400554","longitude":"121.421046"},{"code":"331082","parentCode":"331000","level":"3","name":"临海市","latitude":"28.857389","longitude":"121.221919"},{"code":"331100","parentCode":"330000","level":"2","name":"丽水市","latitude":"28.4563","longitude":"119.929576"},{"code":"331101","parentCode":"331100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"331102","parentCode":"331100","level":"3","name":"莲都区","latitude":"28.447361","longitude":"119.849952"},{"code":"331121","parentCode":"331100","level":"3","name":"青田县","latitude":"28.208429","longitude":"120.146738"},{"code":"331122","parentCode":"331100","level":"3","name":"缙云县","latitude":"28.666326","longitude":"120.191882"},{"code":"331123","parentCode":"331100","level":"3","name":"遂昌县","latitude":"28.52541","longitude":"119.089342"},{"code":"331124","parentCode":"331100","level":"3","name":"松阳县","latitude":"28.41158","longitude":"119.441013"},{"code":"331125","parentCode":"331100","level":"3","name":"云和县","latitude":"8.13132","longitude":"119.54173"},{"code":"331126","parentCode":"331100","level":"3","name":"庆元县","latitude":"27.628046","longitude":"119.157619"},{"code":"331127","parentCode":"331100","level":"3","name":"景宁畲族自治县","latitude":"7.896053","longitude":"119.61929"},{"code":"331181","parentCode":"331100","level":"3","name":"龙泉市","latitude":"28.050639","longitude":"119.082297"},{"code":"340000","parentCode":"0","level":"1","name":"安徽省","latitude":"31.859252","longitude":"117.216005"},{"code":"340100","parentCode":"340000","level":"2","name":"合肥市","latitude":"31.866942","longitude":"117.282699"},{"code":"340101","parentCode":"340100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"340102","parentCode":"340100","level":"3","name":"瑶海区","latitude":"31.905375","longitude":"117.331224"},{"code":"340103","parentCode":"340100","level":"3","name":"庐阳区","latitude":"31.912901","longitude":"117.247835"},{"code":"340104","parentCode":"340100","level":"3","name":"蜀山区","latitude":"1.838185","longitude":"117.23128"},{"code":"340111","parentCode":"340100","level":"3","name":"包河区","latitude":"31.790724","longitude":"117.353913"},{"code":"340121","parentCode":"340100","level":"3","name":"长丰县","latitude":"32.286111","longitude":"117.174438"},{"code":"340122","parentCode":"340100","level":"3","name":"肥东县","latitude":"32.003189","longitude":"117.575857"},{"code":"340123","parentCode":"340100","level":"3","name":"肥西县","latitude":"31.732638","longitude":"117.036261"},{"code":"340124","parentCode":"340100","level":"3","name":"庐江县","latitude":"31.273693","longitude":"117.332167"},{"code":"340181","parentCode":"340100","level":"3","name":"巢湖市","latitude":"31.608733","longitude":"117.88049"},{"code":"340200","parentCode":"340000","level":"2","name":"芜湖市","latitude":"31.36602","longitude":"118.384108"},{"code":"340201","parentCode":"340200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"340202","parentCode":"340200","level":"3","name":"镜湖区","latitude":"31.351966","longitude":"118.387245"},{"code":"340203","parentCode":"340200","level":"3","name":"弋江区","latitude":"","longitude":""},{"code":"340207","parentCode":"340200","level":"3","name":"鸠江区","latitude":"31.375482","longitude":"118.493974"},{"code":"340208","parentCode":"340200","level":"3","name":"三山区","latitude":"","longitude":""},{"code":"340221","parentCode":"340200","level":"3","name":"芜湖县","latitude":"31.191699","longitude":"118.532462"},{"code":"340222","parentCode":"340200","level":"3","name":"繁昌县","latitude":"31.12833","longitude":"118.200118"},{"code":"340223","parentCode":"340200","level":"3","name":"南陵县","latitude":"30.895982","longitude":"118.288216"},{"code":"340225","parentCode":"340200","level":"3","name":"无为县","latitude":"31.256984","longitude":"117.864823"},{"code":"340300","parentCode":"340000","level":"2","name":"蚌埠市","latitude":"32.929499","longitude":"117.35708"},{"code":"340301","parentCode":"340300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"340302","parentCode":"340300","level":"3","name":"龙子湖区","latitude":"","longitude":""},{"code":"340303","parentCode":"340300","level":"3","name":"蚌山区","latitude":"","longitude":""},{"code":"340304","parentCode":"340300","level":"3","name":"禹会区","latitude":"","longitude":""},{"code":"340311","parentCode":"340300","level":"3","name":"淮上区","latitude":"","longitude":""},{"code":"340321","parentCode":"340300","level":"3","name":"怀远县","latitude":"33.037131","longitude":"117.042086"},{"code":"340322","parentCode":"340300","level":"3","name":"五河县","latitude":"3.138465","longitude":"117.76421"},{"code":"340323","parentCode":"340300","level":"3","name":"固镇县","latitude":"33.272841","longitude":"117.354034"},{"code":"340400","parentCode":"340000","level":"2","name":"淮南市","latitude":"32.642812","longitude":"117.018639"},{"code":"340401","parentCode":"340400","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"340402","parentCode":"340400","level":"3","name":"大通区","latitude":"32.643536","longitude":"117.117138"},{"code":"340403","parentCode":"340400","level":"3","name":"田家庵区","latitude":"32.564364","longitude":"117.014687"},{"code":"340404","parentCode":"340400","level":"3","name":"谢家集区","latitude":"32.5444","longitude":"116.908772"},{"code":"340405","parentCode":"340400","level":"3","name":"八公山区","latitude":"32.65239","longitude":"116.825521"},{"code":"340406","parentCode":"340400","level":"3","name":"潘集区","latitude":"32.800695","longitude":"116.866193"},{"code":"340421","parentCode":"340400","level":"3","name":"凤台县","latitude":"32.791416","longitude":"116.584905"},{"code":"340500","parentCode":"340000","level":"2","name":"马鞍山市","latitude":"31.688528","longitude":"118.515882"},{"code":"340501","parentCode":"340500","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"340503","parentCode":"340500","level":"3","name":"花山区","latitude":"31.711627","longitude":"118.578348"},{"code":"340504","parentCode":"340500","level":"3","name":"雨山区","latitude":"31.659719","longitude":"118.554558"},{"code":"340506","parentCode":"340500","level":"3","name":"博望区","latitude":"","longitude":""},{"code":"340521","parentCode":"340500","level":"3","name":"当涂县","latitude":"31.503024","longitude":"118.646673"},{"code":"340522","parentCode":"340500","level":"3","name":"含山县","latitude":"1.662082","longitude":"118.05207"},{"code":"340523","parentCode":"340500","level":"3","name":"和县","latitude":"31.716472","longitude":"118.293882"},{"code":"340600","parentCode":"340000","level":"2","name":"淮北市","latitude":"33.960023","longitude":"116.791447"},{"code":"340601","parentCode":"340600","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"340602","parentCode":"340600","level":"3","name":"杜集区","latitude":"34.113251","longitude":"116.954967"},{"code":"340603","parentCode":"340600","level":"3","name":"相山区","latitude":"33.988335","longitude":"116.728962"},{"code":"340604","parentCode":"340600","level":"3","name":"烈山区","latitude":"33.844054","longitude":"116.908182"},{"code":"340621","parentCode":"340600","level":"3","name":"濉溪县","latitude":"33.693205","longitude":"116.736899"},{"code":"340700","parentCode":"340000","level":"2","name":"铜陵市","latitude":"30.94093","longitude":"117.819429"},{"code":"340701","parentCode":"340700","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"340702","parentCode":"340700","level":"3","name":"铜官山区","latitude":"30.929419","longitude":"117.810737"},{"code":"340703","parentCode":"340700","level":"3","name":"狮子山区","latitude":"30.944772","longitude":"117.889749"},{"code":"340711","parentCode":"340700","level":"3","name":"郊区","latitude":"37.911504","longitude":"113.568086"},{"code":"340721","parentCode":"340700","level":"3","name":"铜陵县","latitude":"30.952359","longitude":"117.918516"},{"code":"340800","parentCode":"340000","level":"2","name":"安庆市","latitude":"30.537898","longitude":"117.058739"},{"code":"340801","parentCode":"340800","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"340802","parentCode":"340800","level":"3","name":"迎江区","latitude":"30.541458","longitude":"117.152542"},{"code":"340803","parentCode":"340800","level":"3","name":"大观区","latitude":"30.532487","longitude":"116.980968"},{"code":"340811","parentCode":"340800","level":"3","name":"宜秀区","latitude":"","longitude":""},{"code":"340822","parentCode":"340800","level":"3","name":"怀宁县","latitude":"30.579025","longitude":"116.803527"},{"code":"340823","parentCode":"340800","level":"3","name":"枞阳县","latitude":"30.860298","longitude":"117.385735"},{"code":"340824","parentCode":"340800","level":"3","name":"潜山县","latitude":"30.758639","longitude":"116.552816"},{"code":"340825","parentCode":"340800","level":"3","name":"太湖县","latitude":"30.5011","longitude":"116.182539"},{"code":"340826","parentCode":"340800","level":"3","name":"宿松县","latitude":"30.108217","longitude":"116.253518"},{"code":"340827","parentCode":"340800","level":"3","name":"望江县","latitude":"30.242568","longitude":"116.688092"},{"code":"340828","parentCode":"340800","level":"3","name":"岳西县","latitude":"0.901821","longitude":"116.22007"},{"code":"340881","parentCode":"340800","level":"3","name":"桐城市","latitude":"30.972568","longitude":"116.953559"},{"code":"341000","parentCode":"340000","level":"2","name":"黄山市","latitude":"29.734435","longitude":"118.29357"},{"code":"341001","parentCode":"341000","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"341002","parentCode":"341000","level":"3","name":"屯溪区","latitude":"29.716535","longitude":"118.309637"},{"code":"341003","parentCode":"341000","level":"3","name":"黄山区","latitude":"30.277746","longitude":"118.077546"},{"code":"341004","parentCode":"341000","level":"3","name":"徽州区","latitude":"29.90214","longitude":"118.278591"},{"code":"341021","parentCode":"341000","level":"3","name":"歙县","latitude":"29.871177","longitude":"118.575156"},{"code":"341022","parentCode":"341000","level":"3","name":"休宁县","latitude":"29.66912","longitude":"118.093082"},{"code":"341023","parentCode":"341000","level":"3","name":"黟县","latitude":"0.014778","longitude":"117.91075"},{"code":"341024","parentCode":"341000","level":"3","name":"祁门县","latitude":"29.873706","longitude":"117.600528"},{"code":"341100","parentCode":"340000","level":"2","name":"滁州市","latitude":"32.317351","longitude":"118.32457"},{"code":"341101","parentCode":"341100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"341102","parentCode":"341100","level":"3","name":"琅琊区","latitude":"32.338458","longitude":"118.337569"},{"code":"341103","parentCode":"341100","level":"3","name":"南谯区","latitude":"32.310209","longitude":"118.270828"},{"code":"341122","parentCode":"341100","level":"3","name":"来安县","latitude":"2.473712","longitude":"118.53563"},{"code":"341124","parentCode":"341100","level":"3","name":"全椒县","latitude":"32.069933","longitude":"118.105778"},{"code":"341125","parentCode":"341100","level":"3","name":"定远县","latitude":"32.473259","longitude":"117.665965"},{"code":"341126","parentCode":"341100","level":"3","name":"凤阳县","latitude":"32.792215","longitude":"117.611472"},{"code":"341181","parentCode":"341100","level":"3","name":"天长市","latitude":"32.721214","longitude":"118.972913"},{"code":"341182","parentCode":"341100","level":"3","name":"明光市","latitude":"32.811836","longitude":"118.140727"},{"code":"341200","parentCode":"340000","level":"2","name":"阜阳市","latitude":"32.901211","longitude":"115.820932"},{"code":"341201","parentCode":"341200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"341202","parentCode":"341200","level":"3","name":"颍州区","latitude":"32.867689","longitude":"115.727727"},{"code":"341203","parentCode":"341200","level":"3","name":"颍东区","latitude":"32.941585","longitude":"116.039985"},{"code":"341204","parentCode":"341200","level":"3","name":"颍泉区","latitude":"33.07351","longitude":"115.734026"},{"code":"341221","parentCode":"341200","level":"3","name":"临泉县","latitude":"32.909769","longitude":"115.248461"},{"code":"341222","parentCode":"341200","level":"3","name":"太和县","latitude":"33.337748","longitude":"115.648756"},{"code":"341225","parentCode":"341200","level":"3","name":"阜南县","latitude":"32.655881","longitude":"115.654099"},{"code":"341226","parentCode":"341200","level":"3","name":"颍上县","latitude":"32.66246","longitude":"116.265314"},{"code":"341282","parentCode":"341200","level":"3","name":"界首市","latitude":"33.226193","longitude":"115.398643"},{"code":"341300","parentCode":"340000","level":"2","name":"宿州市","latitude":"33.636772","longitude":"116.988692"},{"code":"341301","parentCode":"341300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"341302","parentCode":"341300","level":"3","name":"埇桥区","latitude":"","longitude":""},{"code":"341321","parentCode":"341300","level":"3","name":"砀山县","latitude":"34.454057","longitude":"116.420282"},{"code":"341322","parentCode":"341300","level":"3","name":"萧县","latitude":"34.20853","longitude":"116.812422"},{"code":"341323","parentCode":"341300","level":"3","name":"灵璧县","latitude":"33.690737","longitude":"117.543127"},{"code":"341324","parentCode":"341300","level":"3","name":"泗县","latitude":"33.544347","longitude":"117.890359"},{"code":"341500","parentCode":"340000","level":"2","name":"六安市","latitude":"31.755558","longitude":"116.505253"},{"code":"341501","parentCode":"341500","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"341502","parentCode":"341500","level":"3","name":"金安区","latitude":"31.631258","longitude":"116.661941"},{"code":"341503","parentCode":"341500","level":"3","name":"裕安区","latitude":"31.753039","longitude":"116.302573"},{"code":"341521","parentCode":"341500","level":"3","name":"寿县","latitude":"32.262396","longitude":"116.768939"},{"code":"341522","parentCode":"341500","level":"3","name":"霍邱县","latitude":"32.201507","longitude":"116.173521"},{"code":"341523","parentCode":"341500","level":"3","name":"舒城县","latitude":"31.310003","longitude":"116.828559"},{"code":"341524","parentCode":"341500","level":"3","name":"金寨县","latitude":"31.479093","longitude":"115.779315"},{"code":"341525","parentCode":"341500","level":"3","name":"霍山县","latitude":"31.287056","longitude":"116.246675"},{"code":"341600","parentCode":"340000","level":"2","name":"亳州市","latitude":"33.871211","longitude":"115.787928"},{"code":"341601","parentCode":"341600","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"341602","parentCode":"341600","level":"3","name":"谯城区","latitude":"","longitude":""},{"code":"341621","parentCode":"341600","level":"3","name":"涡阳县","latitude":"3.557949","longitude":"116.22355"},{"code":"341622","parentCode":"341600","level":"3","name":"蒙城县","latitude":"33.223044","longitude":"116.591512"},{"code":"341623","parentCode":"341600","level":"3","name":"利辛县","latitude":"33.157376","longitude":"116.166272"},{"code":"341700","parentCode":"340000","level":"2","name":"池州市","latitude":"30.660019","longitude":"117.494477"},{"code":"341701","parentCode":"341700","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"341702","parentCode":"341700","level":"3","name":"贵池区","latitude":"30.514086","longitude":"117.508478"},{"code":"341721","parentCode":"341700","level":"3","name":"东至县","latitude":"30.03407","longitude":"117.006827"},{"code":"341722","parentCode":"341700","level":"3","name":"石台县","latitude":"30.199161","longitude":"117.538282"},{"code":"341723","parentCode":"341700","level":"3","name":"青阳县","latitude":"30.602013","longitude":"117.908159"},{"code":"341800","parentCode":"340000","level":"2","name":"宣城市","latitude":"30.951642","longitude":"118.752096"},{"code":"341801","parentCode":"341800","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"341802","parentCode":"341800","level":"3","name":"宣州区","latitude":"30.943631","longitude":"118.797803"},{"code":"341821","parentCode":"341800","level":"3","name":"郎溪县","latitude":"31.100124","longitude":"119.167904"},{"code":"341822","parentCode":"341800","level":"3","name":"广德县","latitude":"30.89395","longitude":"119.364713"},{"code":"341823","parentCode":"341800","level":"3","name":"泾县","latitude":"0.599287","longitude":"118.37604"},{"code":"341824","parentCode":"341800","level":"3","name":"绩溪县","latitude":"30.162401","longitude":"118.663777"},{"code":"341825","parentCode":"341800","level":"3","name":"旌德县","latitude":"30.321833","longitude":"118.482898"},{"code":"341881","parentCode":"341800","level":"3","name":"宁国市","latitude":"30.502936","longitude":"118.997025"},{"code":"350000","parentCode":"0","level":"1","name":"福建省","latitude":"26.050118","longitude":"117.984943"},{"code":"350100","parentCode":"350000","level":"2","name":"福州市","latitude":"26.047125","longitude":"119.330221"},{"code":"350101","parentCode":"350100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"350102","parentCode":"350100","level":"3","name":"鼓楼区","latitude":"32.068604","longitude":"118.765057"},{"code":"350103","parentCode":"350100","level":"3","name":"台江区","latitude":"26.062154","longitude":"119.324063"},{"code":"350104","parentCode":"350100","level":"3","name":"仓山区","latitude":"26.019664","longitude":"119.334936"},{"code":"350105","parentCode":"350100","level":"3","name":"马尾区","latitude":"26.08265","longitude":"119.510802"},{"code":"350111","parentCode":"350100","level":"3","name":"晋安区","latitude":"26.221752","longitude":"119.314923"},{"code":"350121","parentCode":"350100","level":"3","name":"闽侯县","latitude":"26.182432","longitude":"119.122383"},{"code":"350122","parentCode":"350100","level":"3","name":"连江县","latitude":"26.301591","longitude":"119.568339"},{"code":"350123","parentCode":"350100","level":"3","name":"罗源县","latitude":"26.506326","longitude":"119.465234"},{"code":"350124","parentCode":"350100","level":"3","name":"闽清县","latitude":"26.212273","longitude":"118.778803"},{"code":"350125","parentCode":"350100","level":"3","name":"永泰县","latitude":"25.857384","longitude":"118.794741"},{"code":"350128","parentCode":"350100","level":"3","name":"平潭县","latitude":"25.537738","longitude":"119.766453"},{"code":"350181","parentCode":"350100","level":"3","name":"福清市","latitude":"25.638121","longitude":"119.377547"},{"code":"350182","parentCode":"350100","level":"3","name":"长乐市","latitude":"5.915538","longitude":"119.56272"},{"code":"350200","parentCode":"350000","level":"2","name":"厦门市","latitude":"24.489231","longitude":"118.103886"},{"code":"350201","parentCode":"350200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"350203","parentCode":"350200","level":"3","name":"思明区","latitude":"24.468728","longitude":"118.134535"},{"code":"350205","parentCode":"350200","level":"3","name":"海沧区","latitude":"","longitude":""},{"code":"350206","parentCode":"350200","level":"3","name":"湖里区","latitude":"24.521974","longitude":"118.144676"},{"code":"350211","parentCode":"350200","level":"3","name":"集美区","latitude":"24.640973","longitude":"118.029412"},{"code":"350212","parentCode":"350200","level":"3","name":"同安区","latitude":"24.781705","longitude":"118.114685"},{"code":"350213","parentCode":"350200","level":"3","name":"翔安区","latitude":"","longitude":""},{"code":"350300","parentCode":"350000","level":"2","name":"莆田市","latitude":"25.44845","longitude":"119.077731"},{"code":"350301","parentCode":"350300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"350302","parentCode":"350300","level":"3","name":"城厢区","latitude":"25.433375","longitude":"118.954443"},{"code":"350303","parentCode":"350300","level":"3","name":"涵江区","latitude":"25.604742","longitude":"119.079039"},{"code":"350304","parentCode":"350300","level":"3","name":"荔城区","latitude":"25.427592","longitude":"119.074103"},{"code":"350305","parentCode":"350300","level":"3","name":"秀屿区","latitude":"25.276365","longitude":"119.131466"},{"code":"350322","parentCode":"350300","level":"3","name":"仙游县","latitude":"25.468258","longitude":"118.704626"},{"code":"350400","parentCode":"350000","level":"2","name":"三明市","latitude":"26.270835","longitude":"117.642194"},{"code":"350401","parentCode":"350400","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"350402","parentCode":"350400","level":"3","name":"梅列区","latitude":"26.307449","longitude":"117.630501"},{"code":"350403","parentCode":"350400","level":"3","name":"三元区","latitude":"26.173967","longitude":"117.516896"},{"code":"350421","parentCode":"350400","level":"3","name":"明溪县","latitude":"26.418484","longitude":"117.218599"},{"code":"350423","parentCode":"350400","level":"3","name":"清流县","latitude":"26.099298","longitude":"116.921193"},{"code":"350424","parentCode":"350400","level":"3","name":"宁化县","latitude":"26.310073","longitude":"116.678118"},{"code":"350425","parentCode":"350400","level":"3","name":"大田县","latitude":"25.797449","longitude":"117.817997"},{"code":"350426","parentCode":"350400","level":"3","name":"尤溪县","latitude":"26.150594","longitude":"118.253868"},{"code":"350427","parentCode":"350400","level":"3","name":"沙县","latitude":"26.446506","longitude":"117.818846"},{"code":"350428","parentCode":"350400","level":"3","name":"将乐县","latitude":"6.732329","longitude":"117.40084"},{"code":"350429","parentCode":"350400","level":"3","name":"泰宁县","latitude":"6.865477","longitude":"117.12566"},{"code":"350430","parentCode":"350400","level":"3","name":"建宁县","latitude":"26.817741","longitude":"116.793071"},{"code":"350481","parentCode":"350400","level":"3","name":"永安市","latitude":"25.919433","longitude":"117.328535"},{"code":"350500","parentCode":"350000","level":"2","name":"泉州市","latitude":"24.901652","longitude":"118.600362"},{"code":"350501","parentCode":"350500","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"350502","parentCode":"350500","level":"3","name":"鲤城区","latitude":"24.905745","longitude":"118.568455"},{"code":"350503","parentCode":"350500","level":"3","name":"丰泽区","latitude":"24.936275","longitude":"118.607432"},{"code":"350504","parentCode":"350500","level":"3","name":"洛江区","latitude":"25.133414","longitude":"118.643453"},{"code":"350505","parentCode":"350500","level":"3","name":"泉港区","latitude":"25.173479","longitude":"118.819017"},{"code":"350521","parentCode":"350500","level":"3","name":"惠安县","latitude":"24.991871","longitude":"118.809473"},{"code":"350524","parentCode":"350500","level":"3","name":"安溪县","latitude":"25.125684","longitude":"117.911632"},{"code":"350525","parentCode":"350500","level":"3","name":"永春县","latitude":"25.395599","longitude":"118.140971"},{"code":"350526","parentCode":"350500","level":"3","name":"德化县","latitude":"25.674049","longitude":"118.258039"},{"code":"350527","parentCode":"350500","level":"3","name":"金门县","latitude":"24.453685","longitude":"118.379772"},{"code":"350581","parentCode":"350500","level":"3","name":"石狮市","latitude":"24.744894","longitude":"118.692481"},{"code":"350582","parentCode":"350500","level":"3","name":"晋江市","latitude":"24.729638","longitude":"118.558651"},{"code":"350583","parentCode":"350500","level":"3","name":"南安市","latitude":"25.017973","longitude":"118.388981"},{"code":"350600","parentCode":"350000","level":"2","name":"漳州市","latitude":"24.517065","longitude":"117.676205"},{"code":"350601","parentCode":"350600","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"350602","parentCode":"350600","level":"3","name":"芗城区","latitude":"24.575089","longitude":"117.633366"},{"code":"350603","parentCode":"350600","level":"3","name":"龙文区","latitude":"24.537177","longitude":"117.704037"},{"code":"350622","parentCode":"350600","level":"3","name":"云霄县","latitude":"23.984925","longitude":"117.338111"},{"code":"350623","parentCode":"350600","level":"3","name":"漳浦县","latitude":"24.13461","longitude":"117.691456"},{"code":"350624","parentCode":"350600","level":"3","name":"诏安县","latitude":"23.874041","longitude":"117.132942"},{"code":"350625","parentCode":"350600","level":"3","name":"长泰县","latitude":"24.744594","longitude":"117.812987"},{"code":"350626","parentCode":"350600","level":"3","name":"东山县","latitude":"23.69111","longitude":"117.425416"},{"code":"350627","parentCode":"350600","level":"3","name":"南靖县","latitude":"24.668806","longitude":"117.293055"},{"code":"350628","parentCode":"350600","level":"3","name":"平和县","latitude":"24.324491","longitude":"117.200721"},{"code":"350629","parentCode":"350600","level":"3","name":"华安县","latitude":"24.918688","longitude":"117.543805"},{"code":"350681","parentCode":"350600","level":"3","name":"龙海市","latitude":"4.398817","longitude":"117.80759"},{"code":"350700","parentCode":"350000","level":"2","name":"南平市","latitude":"26.643626","longitude":"118.181883"},{"code":"350701","parentCode":"350700","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"350702","parentCode":"350700","level":"3","name":"延平区","latitude":"26.590155","longitude":"118.254737"},{"code":"350721","parentCode":"350700","level":"3","name":"顺昌县","latitude":"26.908712","longitude":"117.873681"},{"code":"350722","parentCode":"350700","level":"3","name":"浦城县","latitude":"27.945164","longitude":"118.524298"},{"code":"350723","parentCode":"350700","level":"3","name":"光泽县","latitude":"27.655598","longitude":"117.355536"},{"code":"350724","parentCode":"350700","level":"3","name":"松溪县","latitude":"27.610704","longitude":"118.765689"},{"code":"350725","parentCode":"350700","level":"3","name":"政和县","latitude":"27.324782","longitude":"118.971671"},{"code":"350781","parentCode":"350700","level":"3","name":"邵武市","latitude":"27.235197","longitude":"117.480572"},{"code":"350782","parentCode":"350700","level":"3","name":"武夷山市","latitude":"27.748135","longitude":"118.011543"},{"code":"350783","parentCode":"350700","level":"3","name":"建瓯市","latitude":"27.044914","longitude":"118.485147"},{"code":"350784","parentCode":"350700","level":"3","name":"建阳市","latitude":"27.408381","longitude":"118.067793"},{"code":"350800","parentCode":"350000","level":"2","name":"龙岩市","latitude":"25.078685","longitude":"117.017997"},{"code":"350801","parentCode":"350800","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"350802","parentCode":"350800","level":"3","name":"新罗区","latitude":"25.222206","longitude":"117.086322"},{"code":"350821","parentCode":"350800","level":"3","name":"长汀县","latitude":"25.696958","longitude":"116.371884"},{"code":"350822","parentCode":"350800","level":"3","name":"永定县","latitude":"24.761827","longitude":"116.825552"},{"code":"350823","parentCode":"350800","level":"3","name":"上杭县","latitude":"25.126526","longitude":"116.568669"},{"code":"350824","parentCode":"350800","level":"3","name":"武平县","latitude":"25.139021","longitude":"116.135917"},{"code":"350825","parentCode":"350800","level":"3","name":"连城县","latitude":"25.604177","longitude":"116.821448"},{"code":"350881","parentCode":"350800","level":"3","name":"漳平市","latitude":"25.379998","longitude":"117.451722"},{"code":"350900","parentCode":"350000","level":"2","name":"宁德市","latitude":"26.656527","longitude":"119.542082"},{"code":"350901","parentCode":"350900","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"350902","parentCode":"350900","level":"3","name":"蕉城区","latitude":"26.763865","longitude":"119.454559"},{"code":"350921","parentCode":"350900","level":"3","name":"霞浦县","latitude":"26.868877","longitude":"119.990551"},{"code":"350922","parentCode":"350900","level":"3","name":"古田县","latitude":"6.618899","longitude":"118.87954"},{"code":"350923","parentCode":"350900","level":"3","name":"屏南县","latitude":"26.921562","longitude":"118.989291"},{"code":"350924","parentCode":"350900","level":"3","name":"寿宁县","latitude":"7.42623","longitude":"119.50552"},{"code":"350925","parentCode":"350900","level":"3","name":"周宁县","latitude":"27.094313","longitude":"119.313326"},{"code":"350926","parentCode":"350900","level":"3","name":"柘荣县","latitude":"27.207068","longitude":"119.887522"},{"code":"350981","parentCode":"350900","level":"3","name":"福安市","latitude":"27.055897","longitude":"119.656277"},{"code":"350982","parentCode":"350900","level":"3","name":"福鼎市","latitude":"27.224829","longitude":"120.198307"},{"code":"360000","parentCode":"0","level":"1","name":"江西省","latitude":"27.757258","longitude":"115.676082"},{"code":"360100","parentCode":"360000","level":"2","name":"南昌市","latitude":"28.689578","longitude":"115.893528"},{"code":"360101","parentCode":"360100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"360102","parentCode":"360100","level":"3","name":"东湖区","latitude":"28.692375","longitude":"115.910148"},{"code":"360103","parentCode":"360100","level":"3","name":"西湖区","latitude":"28.657326","longitude":"115.898948"},{"code":"360104","parentCode":"360100","level":"3","name":"青云谱区","latitude":"28.636601","longitude":"115.921954"},{"code":"360105","parentCode":"360100","level":"3","name":"湾里区","latitude":"8.800557","longitude":"115.75048"},{"code":"360111","parentCode":"360100","level":"3","name":"青山湖区","latitude":"28.700849","longitude":"115.930906"},{"code":"360121","parentCode":"360100","level":"3","name":"南昌县","latitude":"28.620772","longitude":"116.071261"},{"code":"360122","parentCode":"360100","level":"3","name":"新建县","latitude":"28.812629","longitude":"115.952954"},{"code":"360123","parentCode":"360100","level":"3","name":"安义县","latitude":"28.836412","longitude":"115.595202"},{"code":"360124","parentCode":"360100","level":"3","name":"进贤县","latitude":"28.441758","longitude":"116.317458"},{"code":"360200","parentCode":"360000","level":"2","name":"景德镇市","latitude":"29.303563","longitude":"117.186523"},{"code":"360201","parentCode":"360200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"360202","parentCode":"360200","level":"3","name":"昌江区","latitude":".272155","longitude":"117.1862"},{"code":"360203","parentCode":"360200","level":"3","name":"珠山区","latitude":"29.303231","longitude":"117.234119"},{"code":"360222","parentCode":"360200","level":"3","name":"浮梁县","latitude":"29.556556","longitude":"117.308979"},{"code":"360281","parentCode":"360200","level":"3","name":"乐平市","latitude":"28.969928","longitude":"117.273279"},{"code":"360300","parentCode":"360000","level":"2","name":"萍乡市","latitude":"27.639544","longitude":"113.859917"},{"code":"360301","parentCode":"360300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"360302","parentCode":"360300","level":"3","name":"安源区","latitude":"27.645395","longitude":"113.878869"},{"code":"360313","parentCode":"360300","level":"3","name":"湘东区","latitude":"27.53371","longitude":"113.739818"},{"code":"360321","parentCode":"360300","level":"3","name":"莲花县","latitude":"27.223445","longitude":"113.959777"},{"code":"360322","parentCode":"360300","level":"3","name":"上栗县","latitude":"27.83226","longitude":"113.867806"},{"code":"360323","parentCode":"360300","level":"3","name":"芦溪县","latitude":"27.578023","longitude":"114.070007"},{"code":"360400","parentCode":"360000","level":"2","name":"九江市","latitude":"29.71964","longitude":"115.999848"},{"code":"360401","parentCode":"360400","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"360402","parentCode":"360400","level":"3","name":"庐山区","latitude":"29.666586","longitude":"116.009736"},{"code":"360403","parentCode":"360400","level":"3","name":"浔阳区","latitude":"29.717849","longitude":"116.002768"},{"code":"360421","parentCode":"360400","level":"3","name":"九江县","latitude":"29.64023","longitude":"115.842035"},{"code":"360423","parentCode":"360400","level":"3","name":"武宁县","latitude":"29.263844","longitude":"115.023159"},{"code":"360424","parentCode":"360400","level":"3","name":"修水县","latitude":"29.000021","longitude":"114.446192"},{"code":"360425","parentCode":"360400","level":"3","name":"永修县","latitude":"29.141311","longitude":"115.742475"},{"code":"360426","parentCode":"360400","level":"3","name":"德安县","latitude":"29.401728","longitude":"115.634084"},{"code":"360427","parentCode":"360400","level":"3","name":"星子县","latitude":"29.360271","longitude":"115.964883"},{"code":"360428","parentCode":"360400","level":"3","name":"都昌县","latitude":"29.356215","longitude":"116.342048"},{"code":"360429","parentCode":"360400","level":"3","name":"湖口县","latitude":"29.668061","longitude":"116.292561"},{"code":"360430","parentCode":"360400","level":"3","name":"彭泽县","latitude":"29.834597","longitude":"116.629332"},{"code":"360481","parentCode":"360400","level":"3","name":"瑞昌市","latitude":"29.628545","longitude":"115.459686"},{"code":"360482","parentCode":"360400","level":"3","name":"共青城市","latitude":"","longitude":""},{"code":"360500","parentCode":"360000","level":"2","name":"新余市","latitude":"27.822322","longitude":"114.947117"},{"code":"360501","parentCode":"360500","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"360502","parentCode":"360500","level":"3","name":"渝水区","latitude":"27.850578","longitude":"115.007851"},{"code":"360521","parentCode":"360500","level":"3","name":"分宜县","latitude":"27.844993","longitude":"114.678163"},{"code":"360600","parentCode":"360000","level":"2","name":"鹰潭市","latitude":"28.24131","longitude":"117.03545"},{"code":"360601","parentCode":"360600","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"360602","parentCode":"360600","level":"3","name":"月湖区","latitude":"28.247205","longitude":"117.057706"},{"code":"360622","parentCode":"360600","level":"3","name":"余江县","latitude":"28.32107","longitude":"116.921574"},{"code":"360681","parentCode":"360600","level":"3","name":"贵溪市","latitude":"8.190604","longitude":"117.19787"},{"code":"360700","parentCode":"360000","level":"2","name":"赣州市","latitude":"25.845296","longitude":"114.935909"},{"code":"360701","parentCode":"360700","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"360702","parentCode":"360700","level":"3","name":"章贡区","latitude":"25.838711","longitude":"114.937365"},{"code":"360721","parentCode":"360700","level":"3","name":"赣县","latitude":"25.902025","longitude":"115.072586"},{"code":"360722","parentCode":"360700","level":"3","name":"信丰县","latitude":".286018","longitude":"114.9818"},{"code":"360723","parentCode":"360700","level":"3","name":"大余县","latitude":"5.448472","longitude":"114.36649"},{"code":"360724","parentCode":"360700","level":"3","name":"上犹县","latitude":"25.939253","longitude":"114.402605"},{"code":"360725","parentCode":"360700","level":"3","name":"崇义县","latitude":"25.679632","longitude":"114.199337"},{"code":"360726","parentCode":"360700","level":"3","name":"安远县","latitude":"25.238854","longitude":"115.396613"},{"code":"360727","parentCode":"360700","level":"3","name":"龙南县","latitude":"24.772706","longitude":"114.731825"},{"code":"360728","parentCode":"360700","level":"3","name":"定南县","latitude":"4.82416","longitude":"115.09388"},{"code":"360729","parentCode":"360700","level":"3","name":"全南县","latitude":"24.853233","longitude":"114.522343"},{"code":"360730","parentCode":"360700","level":"3","name":"宁都县","latitude":"26.590232","longitude":"116.012116"},{"code":"360731","parentCode":"360700","level":"3","name":"于都县","latitude":"25.936772","longitude":"115.508893"},{"code":"360732","parentCode":"360700","level":"3","name":"兴国县","latitude":"26.425201","longitude":"115.446507"},{"code":"360733","parentCode":"360700","level":"3","name":"会昌县","latitude":"25.505757","longitude":"115.765151"},{"code":"360734","parentCode":"360700","level":"3","name":"寻乌县","latitude":"24.905101","longitude":"115.665148"},{"code":"360735","parentCode":"360700","level":"3","name":"石城县","latitude":"26.305565","longitude":"116.372322"},{"code":"360781","parentCode":"360700","level":"3","name":"瑞金市","latitude":"25.921831","longitude":"115.985867"},{"code":"360782","parentCode":"360700","level":"3","name":"南康市","latitude":"25.832944","longitude":"114.712561"},{"code":"360800","parentCode":"360000","level":"2","name":"吉安市","latitude":"27.113848","longitude":"114.992039"},{"code":"360801","parentCode":"360800","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"360802","parentCode":"360800","level":"3","name":"吉州区","latitude":"27.160925","longitude":"114.960437"},{"code":"360803","parentCode":"360800","level":"3","name":"青原区","latitude":"26.859217","longitude":"115.266167"},{"code":"360821","parentCode":"360800","level":"3","name":"吉安县","latitude":"27.144039","longitude":"114.751278"},{"code":"360822","parentCode":"360800","level":"3","name":"吉水县","latitude":"27.197465","longitude":"115.254638"},{"code":"360823","parentCode":"360800","level":"3","name":"峡江县","latitude":"27.589281","longitude":"115.214437"},{"code":"360824","parentCode":"360800","level":"3","name":"新干县","latitude":"27.73858","longitude":"115.506839"},{"code":"360825","parentCode":"360800","level":"3","name":"永丰县","latitude":"27.097545","longitude":"115.592831"},{"code":"360826","parentCode":"360800","level":"3","name":"泰和县","latitude":"26.744021","longitude":"114.909356"},{"code":"360827","parentCode":"360800","level":"3","name":"遂川县","latitude":"26.344269","longitude":"114.370589"},{"code":"360828","parentCode":"360800","level":"3","name":"万安县","latitude":"26.444633","longitude":"114.825016"},{"code":"360829","parentCode":"360800","level":"3","name":"安福县","latitude":"27.361338","longitude":"114.455591"},{"code":"360830","parentCode":"360800","level":"3","name":"永新县","latitude":"26.973089","longitude":"114.188447"},{"code":"360881","parentCode":"360800","level":"3","name":"井冈山市","latitude":"26.63315","longitude":"114.125439"},{"code":"360900","parentCode":"360000","level":"2","name":"宜春市","latitude":"27.81113","longitude":"114.400039"},{"code":"360901","parentCode":"360900","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"360902","parentCode":"360900","level":"3","name":"袁州区","latitude":"27.839383","longitude":"114.290358"},{"code":"360921","parentCode":"360900","level":"3","name":"奉新县","latitude":"28.714689","longitude":"115.180078"},{"code":"360922","parentCode":"360900","level":"3","name":"万载县","latitude":"28.209464","longitude":"114.336143"},{"code":"360923","parentCode":"360900","level":"3","name":"上高县","latitude":"28.199053","longitude":"114.860958"},{"code":"360924","parentCode":"360900","level":"3","name":"宜丰县","latitude":"28.454955","longitude":"114.774366"},{"code":"360925","parentCode":"360900","level":"3","name":"靖安县","latitude":"28.946084","longitude":"115.237709"},{"code":"360926","parentCode":"360900","level":"3","name":"铜鼓县","latitude":"28.615516","longitude":"114.370134"},{"code":"360981","parentCode":"360900","level":"3","name":"丰城市","latitude":"28.111516","longitude":"115.823404"},{"code":"360982","parentCode":"360900","level":"3","name":"樟树市","latitude":"28.002514","longitude":"115.421346"},{"code":"360983","parentCode":"360900","level":"3","name":"高安市","latitude":"28.365232","longitude":"115.304482"},{"code":"361000","parentCode":"360000","level":"2","name":"抚州市","latitude":"27.954545","longitude":"116.360919"},{"code":"361001","parentCode":"361000","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"361002","parentCode":"361000","level":"3","name":"临川区","latitude":"27.924732","longitude":"116.362974"},{"code":"361021","parentCode":"361000","level":"3","name":"南城县","latitude":"27.518966","longitude":"116.681732"},{"code":"361022","parentCode":"361000","level":"3","name":"黎川县","latitude":"27.261522","longitude":"116.931717"},{"code":"361023","parentCode":"361000","level":"3","name":"南丰县","latitude":"27.114896","longitude":"116.500362"},{"code":"361024","parentCode":"361000","level":"3","name":"崇仁县","latitude":"27.714537","longitude":"116.066336"},{"code":"361025","parentCode":"361000","level":"3","name":"乐安县","latitude":"27.372429","longitude":"115.843324"},{"code":"361026","parentCode":"361000","level":"3","name":"宜黄县","latitude":"27.393067","longitude":"116.251242"},{"code":"361027","parentCode":"361000","level":"3","name":"金溪县","latitude":"27.931492","longitude":"116.757712"},{"code":"361028","parentCode":"361000","level":"3","name":"资溪县","latitude":"27.745229","longitude":"117.035767"},{"code":"361029","parentCode":"361000","level":"3","name":"东乡县","latitude":"28.221298","longitude":"116.619623"},{"code":"361030","parentCode":"361000","level":"3","name":"广昌县","latitude":"26.761885","longitude":"116.363117"},{"code":"361100","parentCode":"360000","level":"2","name":"上饶市","latitude":"28.457623","longitude":"117.955464"},{"code":"361101","parentCode":"361100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"361102","parentCode":"361100","level":"3","name":"信州区","latitude":"28.497223","longitude":"118.050578"},{"code":"361121","parentCode":"361100","level":"3","name":"上饶县","latitude":"28.40568","longitude":"117.944367"},{"code":"361122","parentCode":"361100","level":"3","name":"广丰县","latitude":"28.364109","longitude":"118.277125"},{"code":"361123","parentCode":"361100","level":"3","name":"玉山县","latitude":"28.75934","longitude":"118.168917"},{"code":"361124","parentCode":"361100","level":"3","name":"铅山县","latitude":"28.109822","longitude":"117.713461"},{"code":"361125","parentCode":"361100","level":"3","name":"横峰县","latitude":"28.513847","longitude":"117.645197"},{"code":"361126","parentCode":"361100","level":"3","name":"弋阳县","latitude":"8.452236","longitude":"117.41665"},{"code":"361127","parentCode":"361100","level":"3","name":"余干县","latitude":"28.682776","longitude":"116.621327"},{"code":"361128","parentCode":"361100","level":"3","name":"鄱阳县","latitude":"29.243056","longitude":"116.787693"},{"code":"361129","parentCode":"361100","level":"3","name":"万年县","latitude":"28.703236","longitude":"117.014413"},{"code":"361130","parentCode":"361100","level":"3","name":"婺源县","latitude":"29.327232","longitude":"117.787485"},{"code":"361181","parentCode":"361100","level":"3","name":"德兴市","latitude":"28.940752","longitude":"117.753259"},{"code":"370000","parentCode":"0","level":"1","name":"山东省","latitude":"36.09929","longitude":"118.527663"},{"code":"370100","parentCode":"370000","level":"2","name":"济南市","latitude":"36.682785","longitude":"117.024967"},{"code":"370101","parentCode":"370100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"370102","parentCode":"370100","level":"3","name":"历下区","latitude":"36.671439","longitude":"117.081309"},{"code":"370103","parentCode":"370100","level":"3","name":"市中区","latitude":"36.656617","longitude":"117.002545"},{"code":"370104","parentCode":"370100","level":"3","name":"槐荫区","latitude":"36.682531","longitude":"116.891199"},{"code":"370105","parentCode":"370100","level":"3","name":"天桥区","latitude":"36.778078","longitude":"116.983157"},{"code":"370112","parentCode":"370100","level":"3","name":"历城区","latitude":"36.612688","longitude":"117.190818"},{"code":"370113","parentCode":"370100","level":"3","name":"长清区","latitude":"36.559828","longitude":"116.759284"},{"code":"370124","parentCode":"370100","level":"3","name":"平阴县","latitude":"36.295093","longitude":"116.463321"},{"code":"370125","parentCode":"370100","level":"3","name":"济阳县","latitude":"36.985199","longitude":"117.179917"},{"code":"370126","parentCode":"370100","level":"3","name":"商河县","latitude":"37.314123","longitude":"117.161858"},{"code":"370181","parentCode":"370100","level":"3","name":"章丘市","latitude":"36.685982","longitude":"117.530225"},{"code":"370200","parentCode":"370000","level":"2","name":"青岛市","latitude":"36.105215","longitude":"120.384428"},{"code":"370201","parentCode":"370200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"370202","parentCode":"370200","level":"3","name":"市南区","latitude":"36.072517","longitude":"120.376184"},{"code":"370203","parentCode":"370200","level":"3","name":"市北区","latitude":"36.100058","longitude":"120.378495"},{"code":"370211","parentCode":"370200","level":"3","name":"黄岛区","latitude":"36.005019","longitude":"120.169541"},{"code":"370212","parentCode":"370200","level":"3","name":"崂山区","latitude":"36.195587","longitude":"120.584906"},{"code":"370213","parentCode":"370200","level":"3","name":"李沧区","latitude":"36.192897","longitude":"120.431146"},{"code":"370214","parentCode":"370200","level":"3","name":"城阳区","latitude":"36.284247","longitude":"120.346326"},{"code":"370281","parentCode":"370200","level":"3","name":"胶州市","latitude":"36.248031","longitude":"119.959421"},{"code":"370282","parentCode":"370200","level":"3","name":"即墨市","latitude":"36.487909","longitude":"120.521106"},{"code":"370283","parentCode":"370200","level":"3","name":"平度市","latitude":"36.78855","longitude":"119.951062"},{"code":"370285","parentCode":"370200","level":"3","name":"莱西市","latitude":"36.863637","longitude":"120.442831"},{"code":"370300","parentCode":"370000","level":"2","name":"淄博市","latitude":"36.804685","longitude":"118.059134"},{"code":"370301","parentCode":"370300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"370302","parentCode":"370300","level":"3","name":"淄川区","latitude":"36.585463","longitude":"118.020181"},{"code":"370303","parentCode":"370300","level":"3","name":"张店区","latitude":"36.816097","longitude":"118.077151"},{"code":"370304","parentCode":"370300","level":"3","name":"博山区","latitude":"36.425427","longitude":"117.965553"},{"code":"370305","parentCode":"370300","level":"3","name":"临淄区","latitude":"36.854244","longitude":"118.300697"},{"code":"370306","parentCode":"370300","level":"3","name":"周村区","latitude":"36.771219","longitude":"117.875108"},{"code":"370321","parentCode":"370300","level":"3","name":"桓台县","latitude":"36.99629","longitude":"118.034367"},{"code":"370322","parentCode":"370300","level":"3","name":"高青县","latitude":"37.171378","longitude":"117.828242"},{"code":"370323","parentCode":"370300","level":"3","name":"沂源县","latitude":"36.135642","longitude":"118.203972"},{"code":"370400","parentCode":"370000","level":"2","name":"枣庄市","latitude":"34.807883","longitude":"117.279305"},{"code":"370401","parentCode":"370400","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"370402","parentCode":"370400","level":"3","name":"市中区","latitude":"36.656617","longitude":"117.002545"},{"code":"370403","parentCode":"370400","level":"3","name":"薛城区","latitude":"34.79633","longitude":"117.358507"},{"code":"370404","parentCode":"370400","level":"3","name":"峄城区","latitude":"34.716097","longitude":"117.603556"},{"code":"370405","parentCode":"370400","level":"3","name":"台儿庄区","latitude":"34.587964","longitude":"117.638243"},{"code":"370406","parentCode":"370400","level":"3","name":"山亭区","latitude":"35.09315","longitude":"117.484036"},{"code":"370481","parentCode":"370400","level":"3","name":"滕州市","latitude":"35.065791","longitude":"117.147616"},{"code":"370500","parentCode":"370000","level":"2","name":"东营市","latitude":"37.487121","longitude":"118.583926"},{"code":"370501","parentCode":"370500","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"370502","parentCode":"370500","level":"3","name":"东营区","latitude":"37.408666","longitude":"118.612643"},{"code":"370503","parentCode":"370500","level":"3","name":"河口区","latitude":"37.9695","longitude":"118.620012"},{"code":"370521","parentCode":"370500","level":"3","name":"垦利县","latitude":"37.670007","longitude":"118.814423"},{"code":"370522","parentCode":"370500","level":"3","name":"利津县","latitude":"37.655326","longitude":"118.400337"},{"code":"370523","parentCode":"370500","level":"3","name":"广饶县","latitude":"37.162071","longitude":"118.538569"},{"code":"370600","parentCode":"370000","level":"2","name":"烟台市","latitude":"37.536562","longitude":"121.309555"},{"code":"370601","parentCode":"370600","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"370602","parentCode":"370600","level":"3","name":"芝罘区","latitude":"37.520933","longitude":"121.364156"},{"code":"370611","parentCode":"370600","level":"3","name":"福山区","latitude":"37.481074","longitude":"121.203461"},{"code":"370612","parentCode":"370600","level":"3","name":"牟平区","latitude":"7.272446","longitude":"121.56924"},{"code":"370613","parentCode":"370600","level":"3","name":"莱山区","latitude":"37.407476","longitude":"121.451535"},{"code":"370634","parentCode":"370600","level":"3","name":"长岛县","latitude":"38.077456","longitude":"120.755996"},{"code":"370681","parentCode":"370600","level":"3","name":"龙口市","latitude":".610401","longitude":"120.5228"},{"code":"370682","parentCode":"370600","level":"3","name":"莱阳市","latitude":"36.905533","longitude":"120.751343"},{"code":"370683","parentCode":"370600","level":"3","name":"莱州市","latitude":"37.190401","longitude":"120.001344"},{"code":"370684","parentCode":"370600","level":"3","name":"蓬莱市","latitude":"37.66116","longitude":"120.862694"},{"code":"370685","parentCode":"370600","level":"3","name":"招远市","latitude":"37.344146","longitude":"120.400517"},{"code":"370686","parentCode":"370600","level":"3","name":"栖霞市","latitude":"37.311748","longitude":"120.901556"},{"code":"370687","parentCode":"370600","level":"3","name":"海阳市","latitude":"36.861588","longitude":"121.113614"},{"code":"370700","parentCode":"370000","level":"2","name":"潍坊市","latitude":"36.716115","longitude":"119.142634"},{"code":"370701","parentCode":"370700","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"370702","parentCode":"370700","level":"3","name":"潍城区","latitude":"36.701982","longitude":"119.034305"},{"code":"370703","parentCode":"370700","level":"3","name":"寒亭区","latitude":"36.908366","longitude":"119.179135"},{"code":"370704","parentCode":"370700","level":"3","name":"坊子区","latitude":"36.625674","longitude":"119.258465"},{"code":"370705","parentCode":"370700","level":"3","name":"奎文区","latitude":"36.691227","longitude":"119.196972"},{"code":"370724","parentCode":"370700","level":"3","name":"临朐县","latitude":"36.365389","longitude":"118.558256"},{"code":"370725","parentCode":"370700","level":"3","name":"昌乐县","latitude":"36.535532","longitude":"118.913914"},{"code":"370781","parentCode":"370700","level":"3","name":"青州市","latitude":"36.680584","longitude":"118.470187"},{"code":"370782","parentCode":"370700","level":"3","name":"诸城市","latitude":"6.016658","longitude":"119.41617"},{"code":"370783","parentCode":"370700","level":"3","name":"寿光市","latitude":"37.029892","longitude":"118.852534"},{"code":"370784","parentCode":"370700","level":"3","name":"安丘市","latitude":"36.335046","longitude":"119.155992"},{"code":"370785","parentCode":"370700","level":"3","name":"高密市","latitude":"36.387318","longitude":"119.702512"},{"code":"370786","parentCode":"370700","level":"3","name":"昌邑市","latitude":"36.834234","longitude":"119.449917"},{"code":"370800","parentCode":"370000","level":"2","name":"济宁市","latitude":"35.402122","longitude":"116.600798"},{"code":"370801","parentCode":"370800","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"370802","parentCode":"370800","level":"3","name":"市中区","latitude":"36.656617","longitude":"117.002545"},{"code":"370811","parentCode":"370800","level":"3","name":"任城区","latitude":"35.380135","longitude":"116.572199"},{"code":"370826","parentCode":"370800","level":"3","name":"微山县","latitude":"34.892715","longitude":"116.992409"},{"code":"370827","parentCode":"370800","level":"3","name":"鱼台县","latitude":"35.019858","longitude":"116.578437"},{"code":"370828","parentCode":"370800","level":"3","name":"金乡县","latitude":"35.072637","longitude":"116.317425"},{"code":"370829","parentCode":"370800","level":"3","name":"嘉祥县","latitude":"35.434199","longitude":"116.307291"},{"code":"370830","parentCode":"370800","level":"3","name":"汶上县","latitude":"35.715701","longitude":"116.506444"},{"code":"370831","parentCode":"370800","level":"3","name":"泗水县","latitude":"35.640741","longitude":"117.345264"},{"code":"370832","parentCode":"370800","level":"3","name":"梁山县","latitude":"35.808748","longitude":"116.100372"},{"code":"370881","parentCode":"370800","level":"3","name":"曲阜市","latitude":"35.58747","longitude":"116.991258"},{"code":"370882","parentCode":"370800","level":"3","name":"兖州市","latitude":"35.574016","longitude":"116.754139"},{"code":"370883","parentCode":"370800","level":"3","name":"邹城市","latitude":"35.411134","longitude":"117.010819"},{"code":"370900","parentCode":"370000","level":"2","name":"泰安市","latitude":"36.188078","longitude":"117.089415"},{"code":"370901","parentCode":"370900","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"370902","parentCode":"370900","level":"3","name":"泰山区","latitude":"36.215457","longitude":"117.183902"},{"code":"370911","parentCode":"370900","level":"3","name":"岱岳区","latitude":"36.148101","longitude":"117.190487"},{"code":"370921","parentCode":"370900","level":"3","name":"宁阳县","latitude":"35.8336","longitude":"116.932939"},{"code":"370923","parentCode":"370900","level":"3","name":"东平县","latitude":"35.975161","longitude":"116.342953"},{"code":"370982","parentCode":"370900","level":"3","name":"新泰市","latitude":"35.89581","longitude":"117.613016"},{"code":"370983","parentCode":"370900","level":"3","name":"肥城市","latitude":"36.112514","longitude":"116.744762"},{"code":"371000","parentCode":"370000","level":"2","name":"威海市","latitude":"37.528787","longitude":"122.093958"},{"code":"371001","parentCode":"371000","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"371002","parentCode":"371000","level":"3","name":"环翠区","latitude":"37.399344","longitude":"122.152075"},{"code":"371081","parentCode":"371000","level":"3","name":"文登市","latitude":"37.15412","longitude":"122.010782"},{"code":"371082","parentCode":"371000","level":"3","name":"荣成市","latitude":"37.128686","longitude":"122.406926"},{"code":"371083","parentCode":"371000","level":"3","name":"乳山市","latitude":"36.976575","longitude":"121.529788"},{"code":"371100","parentCode":"370000","level":"2","name":"日照市","latitude":"35.420225","longitude":"119.50718"},{"code":"371101","parentCode":"371100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"371102","parentCode":"371100","level":"3","name":"东港区","latitude":"35.469377","longitude":"119.377852"},{"code":"371103","parentCode":"371100","level":"3","name":"岚山区","latitude":"","longitude":""},{"code":"371121","parentCode":"371100","level":"3","name":"五莲县","latitude":"35.744383","longitude":"119.249433"},{"code":"371122","parentCode":"371100","level":"3","name":"莒县","latitude":"35.655875","longitude":"118.893585"},{"code":"371200","parentCode":"370000","level":"2","name":"莱芜市","latitude":"36.233654","longitude":"117.684667"},{"code":"371201","parentCode":"371200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"371202","parentCode":"371200","level":"3","name":"莱城区","latitude":"36.313395","longitude":"117.645913"},{"code":"371203","parentCode":"371200","level":"3","name":"钢城区","latitude":"36.092836","longitude":"117.827537"},{"code":"371300","parentCode":"370000","level":"2","name":"临沂市","latitude":"35.072409","longitude":"118.340768"},{"code":"371301","parentCode":"371300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"371302","parentCode":"371300","level":"3","name":"兰山区","latitude":"35.174845","longitude":"118.312243"},{"code":"371311","parentCode":"371300","level":"3","name":"罗庄区","latitude":"34.964343","longitude":"118.297279"},{"code":"371312","parentCode":"371300","level":"3","name":"河东区","latitude":"39.126626","longitude":"117.261693"},{"code":"371321","parentCode":"371300","level":"3","name":"沂南县","latitude":"35.555284","longitude":"118.470478"},{"code":"371322","parentCode":"371300","level":"3","name":"郯城县","latitude":"34.649855","longitude":"118.324431"},{"code":"371323","parentCode":"371300","level":"3","name":"沂水县","latitude":"35.914369","longitude":"118.609358"},{"code":"371324","parentCode":"371300","level":"3","name":"苍山县","latitude":"34.865344","longitude":"117.998342"},{"code":"371325","parentCode":"371300","level":"3","name":"费县","latitude":"35.273244","longitude":"117.981814"},{"code":"371326","parentCode":"371300","level":"3","name":"平邑县","latitude":"35.51103","longitude":"117.646516"},{"code":"371327","parentCode":"371300","level":"3","name":"莒南县","latitude":"35.213123","longitude":"118.890079"},{"code":"371328","parentCode":"371300","level":"3","name":"蒙阴县","latitude":"35.716383","longitude":"117.952229"},{"code":"371329","parentCode":"371300","level":"3","name":"临沭县","latitude":"34.885484","longitude":"118.659445"},{"code":"371400","parentCode":"370000","level":"2","name":"德州市","latitude":"37.460826","longitude":"116.328161"},{"code":"371401","parentCode":"371400","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"371402","parentCode":"371400","level":"3","name":"德城区","latitude":"37.457437","longitude":"116.332912"},{"code":"371421","parentCode":"371400","level":"3","name":"陵县","latitude":"7.417105","longitude":"116.66506"},{"code":"371422","parentCode":"371400","level":"3","name":"宁津县","latitude":"37.657859","longitude":"116.806794"},{"code":"371423","parentCode":"371400","level":"3","name":"庆云县","latitude":"37.801824","longitude":"117.462737"},{"code":"371424","parentCode":"371400","level":"3","name":"临邑县","latitude":"37.235893","longitude":"116.899595"},{"code":"371425","parentCode":"371400","level":"3","name":"齐河县","latitude":"36.723454","longitude":"116.678254"},{"code":"371426","parentCode":"371400","level":"3","name":"平原县","latitude":"37.156618","longitude":"116.430079"},{"code":"371427","parentCode":"371400","level":"3","name":"夏津县","latitude":"37.016689","longitude":"116.037322"},{"code":"371428","parentCode":"371400","level":"3","name":"武城县","latitude":"37.219962","longitude":"116.076565"},{"code":"371481","parentCode":"371400","level":"3","name":"乐陵市","latitude":"37.674417","longitude":"117.145553"},{"code":"371482","parentCode":"371400","level":"3","name":"禹城市","latitude":"36.940491","longitude":"116.644284"},{"code":"371500","parentCode":"370000","level":"2","name":"聊城市","latitude":"36.455829","longitude":"115.986869"},{"code":"371501","parentCode":"371500","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"371502","parentCode":"371500","level":"3","name":"东昌府区","latitude":"36.45583","longitude":"115.907706"},{"code":"371521","parentCode":"371500","level":"3","name":"阳谷县","latitude":"36.121356","longitude":"115.795889"},{"code":"371522","parentCode":"371500","level":"3","name":"莘县","latitude":"36.139122","longitude":"115.552673"},{"code":"371523","parentCode":"371500","level":"3","name":"茌平县","latitude":"36.587361","longitude":"116.262962"},{"code":"371524","parentCode":"371500","level":"3","name":"东阿县","latitude":"36.339782","longitude":"116.253877"},{"code":"371525","parentCode":"371500","level":"3","name":"冠县","latitude":"36.48983","longitude":"115.45"},{"code":"371526","parentCode":"371500","level":"3","name":"高唐县","latitude":"36.872221","longitude":"116.236733"},{"code":"371581","parentCode":"371500","level":"3","name":"临清市","latitude":"36.782069","longitude":"115.782602"},{"code":"371600","parentCode":"370000","level":"2","name":"滨州市","latitude":"37.405314","longitude":"117.968292"},{"code":"371601","parentCode":"371600","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"371602","parentCode":"371600","level":"3","name":"滨城区","latitude":"37.424891","longitude":"117.981211"},{"code":"371621","parentCode":"371600","level":"3","name":"惠民县","latitude":"37.375971","longitude":"117.578984"},{"code":"371622","parentCode":"371600","level":"3","name":"阳信县","latitude":"7.6055","longitude":"117.57343"},{"code":"371623","parentCode":"371600","level":"3","name":"无棣县","latitude":"37.942568","longitude":"117.797782"},{"code":"371624","parentCode":"371600","level":"3","name":"沾化县","latitude":"37.787251","longitude":"118.090098"},{"code":"371625","parentCode":"371600","level":"3","name":"博兴县","latitude":"37.191354","longitude":"118.225715"},{"code":"371626","parentCode":"371600","level":"3","name":"邹平县","latitude":"36.956593","longitude":"117.670806"},{"code":"371700","parentCode":"370000","level":"2","name":"菏泽市","latitude":"","longitude":""},{"code":"371701","parentCode":"371700","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"371702","parentCode":"371700","level":"3","name":"牡丹区","latitude":"35.283537","longitude":"115.470025"},{"code":"371721","parentCode":"371700","level":"3","name":"曹县","latitude":"34.827953","longitude":"115.553601"},{"code":"371722","parentCode":"371700","level":"3","name":"单县","latitude":"34.800508","longitude":"116.093377"},{"code":"371723","parentCode":"371700","level":"3","name":"成武县","latitude":"34.989111","longitude":"115.944989"},{"code":"371724","parentCode":"371700","level":"3","name":"巨野县","latitude":"35.2794","longitude":"116.041131"},{"code":"371725","parentCode":"371700","level":"3","name":"郓城县","latitude":"35.605115","longitude":"115.950686"},{"code":"371726","parentCode":"371700","level":"3","name":"鄄城县","latitude":"35.555043","longitude":"115.552871"},{"code":"371727","parentCode":"371700","level":"3","name":"定陶县","latitude":"5.103626","longitude":"115.62942"},{"code":"371728","parentCode":"371700","level":"3","name":"东明县","latitude":"35.182435","longitude":"115.074115"},{"code":"410000","parentCode":"0","level":"1","name":"河南省","latitude":"34.157184","longitude":"113.486804"},{"code":"410100","parentCode":"410000","level":"2","name":"郑州市","latitude":"34.75661","longitude":"113.649644"},{"code":"410101","parentCode":"410100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"410102","parentCode":"410100","level":"3","name":"中原区","latitude":"34.779474","longitude":"113.557281"},{"code":"410103","parentCode":"410100","level":"3","name":"二七区","latitude":"34.698066","longitude":"113.616482"},{"code":"410104","parentCode":"410100","level":"3","name":"管城回族区","latitude":"34.709004","longitude":"113.721861"},{"code":"410105","parentCode":"410100","level":"3","name":"金水区","latitude":"34.797406","longitude":"113.708011"},{"code":"410106","parentCode":"410100","level":"3","name":"上街区","latitude":"34.822089","longitude":"113.298182"},{"code":"410108","parentCode":"410100","level":"3","name":"惠济区","latitude":"","longitude":""},{"code":"410122","parentCode":"410100","level":"3","name":"中牟县","latitude":"34.720319","longitude":"114.011222"},{"code":"410181","parentCode":"410100","level":"3","name":"巩义市","latitude":"4.703799","longitude":"113.03959"},{"code":"410182","parentCode":"410100","level":"3","name":"荥阳市","latitude":"34.80618","longitude":"113.351802"},{"code":"410183","parentCode":"410100","level":"3","name":"新密市","latitude":"34.514075","longitude":"113.439854"},{"code":"410184","parentCode":"410100","level":"3","name":"新郑市","latitude":"34.459443","longitude":"113.736115"},{"code":"410185","parentCode":"410100","level":"3","name":"登封市","latitude":"34.418362","longitude":"113.041749"},{"code":"410200","parentCode":"410000","level":"2","name":"开封市","latitude":"34.801854","longitude":"114.351642"},{"code":"410201","parentCode":"410200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"410202","parentCode":"410200","level":"3","name":"龙亭区","latitude":"34.807158","longitude":"114.361971"},{"code":"410203","parentCode":"410200","level":"3","name":"顺河回族区","latitude":"34.802277","longitude":"114.368531"},{"code":"410204","parentCode":"410200","level":"3","name":"鼓楼区","latitude":"32.068604","longitude":"118.765057"},{"code":"410205","parentCode":"410200","level":"3","name":"禹王台区","latitude":"","longitude":""},{"code":"410211","parentCode":"410200","level":"3","name":"金明区","latitude":"","longitude":""},{"code":"410221","parentCode":"410200","level":"3","name":"杞县","latitude":"34.505963","longitude":"114.768782"},{"code":"410222","parentCode":"410200","level":"3","name":"通许县","latitude":"34.441631","longitude":"114.502199"},{"code":"410223","parentCode":"410200","level":"3","name":"尉氏县","latitude":"34.388437","longitude":"114.161037"},{"code":"410224","parentCode":"410200","level":"3","name":"开封县","latitude":"34.726004","longitude":"114.452186"},{"code":"410225","parentCode":"410200","level":"3","name":"兰考县","latitude":"34.879764","longitude":"114.980293"},{"code":"410300","parentCode":"410000","level":"2","name":"洛阳市","latitude":"34.657368","longitude":"112.447525"},{"code":"410301","parentCode":"410300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"410302","parentCode":"410300","level":"3","name":"老城区","latitude":"34.704033","longitude":"112.459173"},{"code":"410303","parentCode":"410300","level":"3","name":"西工区","latitude":"34.689694","longitude":"112.407126"},{"code":"410304","parentCode":"410300","level":"3","name":"瀍河回族区","latitude":"","longitude":""},{"code":"410305","parentCode":"410300","level":"3","name":"涧西区","latitude":"34.671668","longitude":"112.390753"},{"code":"410306","parentCode":"410300","level":"3","name":"吉利区","latitude":"34.905379","longitude":"112.589765"},{"code":"410311","parentCode":"410300","level":"3","name":"洛龙区","latitude":"34.638792","longitude":"112.467093"},{"code":"410322","parentCode":"410300","level":"3","name":"孟津县","latitude":"34.831148","longitude":"112.476996"},{"code":"410323","parentCode":"410300","level":"3","name":"新安县","latitude":"34.837607","longitude":"112.127744"},{"code":"410324","parentCode":"410300","level":"3","name":"栾川县","latitude":"33.912392","longitude":"111.617014"},{"code":"410325","parentCode":"410300","level":"3","name":"嵩县","latitude":"34.0106","longitude":"112.049511"},{"code":"410326","parentCode":"410300","level":"3","name":"汝阳县","latitude":"34.062967","longitude":"112.435544"},{"code":"410327","parentCode":"410300","level":"3","name":"宜阳县","latitude":"34.486036","longitude":"112.040468"},{"code":"410328","parentCode":"410300","level":"3","name":"洛宁县","latitude":"34.345208","longitude":"111.506791"},{"code":"410329","parentCode":"410300","level":"3","name":"伊川县","latitude":"34.407088","longitude":"112.468877"},{"code":"410381","parentCode":"410300","level":"3","name":"偃师市","latitude":"34.630802","longitude":"112.734822"},{"code":"410400","parentCode":"410000","level":"2","name":"平顶山市","latitude":"33.745301","longitude":"113.300849"},{"code":"410401","parentCode":"410400","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"410402","parentCode":"410400","level":"3","name":"新华区","latitude":"38.117219","longitude":"114.453501"},{"code":"410403","parentCode":"410400","level":"3","name":"卫东区","latitude":"33.769108","longitude":"113.365388"},{"code":"410404","parentCode":"410400","level":"3","name":"石龙区","latitude":"33.892094","longitude":"112.894691"},{"code":"410411","parentCode":"410400","level":"3","name":"湛河区","latitude":"33.712341","longitude":"113.278189"},{"code":"410421","parentCode":"410400","level":"3","name":"宝丰县","latitude":"33.915497","longitude":"113.035771"},{"code":"410422","parentCode":"410400","level":"3","name":"叶县","latitude":"33.551013","longitude":"113.350676"},{"code":"410423","parentCode":"410400","level":"3","name":"鲁山县","latitude":"33.748697","longitude":"112.740309"},{"code":"410425","parentCode":"410400","level":"3","name":"郏县","latitude":"34.005499","longitude":"113.233282"},{"code":"410481","parentCode":"410400","level":"3","name":"舞钢市","latitude":"33.289605","longitude":"113.525996"},{"code":"410482","parentCode":"410400","level":"3","name":"汝州市","latitude":"34.162778","longitude":"112.812717"},{"code":"410500","parentCode":"410000","level":"2","name":"安阳市","latitude":"36.110267","longitude":"114.351807"},{"code":"410501","parentCode":"410500","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"410502","parentCode":"410500","level":"3","name":"文峰区","latitude":"36.034148","longitude":"114.418522"},{"code":"410503","parentCode":"410500","level":"3","name":"北关区","latitude":"36.141696","longitude":"114.391436"},{"code":"410505","parentCode":"410500","level":"3","name":"殷都区","latitude":"6.135573","longitude":"114.29713"},{"code":"410506","parentCode":"410500","level":"3","name":"龙安区","latitude":"36.056025","longitude":"114.256604"},{"code":"410522","parentCode":"410500","level":"3","name":"安阳县","latitude":"36.125135","longitude":"114.317124"},{"code":"410523","parentCode":"410500","level":"3","name":"汤阴县","latitude":"35.907982","longitude":"114.462063"},{"code":"410526","parentCode":"410500","level":"3","name":"滑县","latitude":"35.471734","longitude":"114.673647"},{"code":"410527","parentCode":"410500","level":"3","name":"内黄县","latitude":"35.906569","longitude":"114.823344"},{"code":"410581","parentCode":"410500","level":"3","name":"林州市","latitude":"36.016561","longitude":"113.861084"},{"code":"410600","parentCode":"410000","level":"2","name":"鹤壁市","latitude":"35.755426","longitude":"114.29777"},{"code":"410601","parentCode":"410600","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"410602","parentCode":"410600","level":"3","name":"鹤山区","latitude":"35.973346","longitude":"114.098454"},{"code":"410603","parentCode":"410600","level":"3","name":"山城区","latitude":"35.927454","longitude":"114.253029"},{"code":"410611","parentCode":"410600","level":"3","name":"淇滨区","latitude":"35.812419","longitude":"114.199514"},{"code":"410621","parentCode":"410600","level":"3","name":"浚县","latitude":"35.686206","longitude":"114.467186"},{"code":"410622","parentCode":"410600","level":"3","name":"淇县","latitude":"35.667572","longitude":"114.169034"},{"code":"410700","parentCode":"410000","level":"2","name":"新乡市","latitude":"35.307258","longitude":"113.91269"},{"code":"410701","parentCode":"410700","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"410702","parentCode":"410700","level":"3","name":"红旗区","latitude":"35.28615","longitude":"113.914619"},{"code":"410703","parentCode":"410700","level":"3","name":"卫滨区","latitude":"","longitude":""},{"code":"410704","parentCode":"410700","level":"3","name":"凤泉区","latitude":"","longitude":""},{"code":"410711","parentCode":"410700","level":"3","name":"牧野区","latitude":"","longitude":""},{"code":"410721","parentCode":"410700","level":"3","name":"新乡县","latitude":"35.220522","longitude":"113.848246"},{"code":"410724","parentCode":"410700","level":"3","name":"获嘉县","latitude":"35.203481","longitude":"113.651969"},{"code":"410725","parentCode":"410700","level":"3","name":"原阳县","latitude":"35.029036","longitude":"113.953164"},{"code":"410726","parentCode":"410700","level":"3","name":"延津县","latitude":"35.279608","longitude":"114.231357"},{"code":"410727","parentCode":"410700","level":"3","name":"封丘县","latitude":"35.040384","longitude":"114.487678"},{"code":"410728","parentCode":"410700","level":"3","name":"长垣县","latitude":"35.218128","longitude":"114.766903"},{"code":"410781","parentCode":"410700","level":"3","name":"卫辉市","latitude":"35.499572","longitude":"114.078112"},{"code":"410782","parentCode":"410700","level":"3","name":"辉县市","latitude":"35.543594","longitude":"113.687892"},{"code":"410800","parentCode":"410000","level":"2","name":"焦作市","latitude":"35.234608","longitude":"113.211836"},{"code":"410801","parentCode":"410800","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"410802","parentCode":"410800","level":"3","name":"解放区","latitude":"35.241712","longitude":"113.230804"},{"code":"410803","parentCode":"410800","level":"3","name":"中站区","latitude":"35.257024","longitude":"113.161536"},{"code":"410804","parentCode":"410800","level":"3","name":"马村区","latitude":"35.304171","longitude":"113.367321"},{"code":"410811","parentCode":"410800","level":"3","name":"山阳区","latitude":"35.24116","longitude":"113.276351"},{"code":"410821","parentCode":"410800","level":"3","name":"修武县","latitude":"35.309678","longitude":"113.363528"},{"code":"410822","parentCode":"410800","level":"3","name":"博爱县","latitude":"35.186007","longitude":"113.075078"},{"code":"410823","parentCode":"410800","level":"3","name":"武陟县","latitude":"35.057332","longitude":"113.399935"},{"code":"410825","parentCode":"410800","level":"3","name":"温县","latitude":"34.95026","longitude":"113.055296"},{"code":"410882","parentCode":"410800","level":"3","name":"沁阳市","latitude":"35.133826","longitude":"112.888305"},{"code":"410883","parentCode":"410800","level":"3","name":"孟州市","latitude":"34.925884","longitude":"112.769699"},{"code":"410900","parentCode":"410000","level":"2","name":"濮阳市","latitude":"35.753298","longitude":"115.026627"},{"code":"410901","parentCode":"410900","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"410902","parentCode":"410900","level":"3","name":"华龙区","latitude":"35.771934","longitude":"115.048097"},{"code":"410922","parentCode":"410900","level":"3","name":"清丰县","latitude":"35.924382","longitude":"115.161201"},{"code":"410923","parentCode":"410900","level":"3","name":"南乐县","latitude":"36.097697","longitude":"115.249823"},{"code":"410926","parentCode":"410900","level":"3","name":"范县","latitude":"35.801405","longitude":"115.538401"},{"code":"410927","parentCode":"410900","level":"3","name":"台前县","latitude":"35.966389","longitude":"115.885738"},{"code":"410928","parentCode":"410900","level":"3","name":"濮阳县","latitude":"35.592287","longitude":"115.156602"},{"code":"411000","parentCode":"410000","level":"2","name":"许昌市","latitude":"34.02674","longitude":"113.835312"},{"code":"411001","parentCode":"411000","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"411002","parentCode":"411000","level":"3","name":"魏都区","latitude":"34.043477","longitude":"113.825316"},{"code":"411023","parentCode":"411000","level":"3","name":"许昌县","latitude":"34.048516","longitude":"113.835262"},{"code":"411024","parentCode":"411000","level":"3","name":"鄢陵县","latitude":"34.01193","longitude":"114.202409"},{"code":"411025","parentCode":"411000","level":"3","name":"襄城县","latitude":"33.861905","longitude":"113.568983"},{"code":"411081","parentCode":"411000","level":"3","name":"禹州市","latitude":"34.200308","longitude":"113.392694"},{"code":"411082","parentCode":"411000","level":"3","name":"长葛市","latitude":"34.236601","longitude":"113.855568"},{"code":"411100","parentCode":"410000","level":"2","name":"漯河市","latitude":"33.576279","longitude":"114.046061"},{"code":"411101","parentCode":"411100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"411102","parentCode":"411100","level":"3","name":"源汇区","latitude":"33.534755","longitude":"113.923601"},{"code":"411103","parentCode":"411100","level":"3","name":"郾城区","latitude":"","longitude":""},{"code":"411104","parentCode":"411100","level":"3","name":"召陵区","latitude":"","longitude":""},{"code":"411121","parentCode":"411100","level":"3","name":"舞阳县","latitude":"33.549301","longitude":"113.680055"},{"code":"411122","parentCode":"411100","level":"3","name":"临颍县","latitude":"33.844426","longitude":"113.963899"},{"code":"411200","parentCode":"410000","level":"2","name":"三门峡市","latitude":"34.78332","longitude":"111.181262"},{"code":"411201","parentCode":"411200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"411202","parentCode":"411200","level":"3","name":"湖滨区","latitude":"34.771778","longitude":"111.281295"},{"code":"411221","parentCode":"411200","level":"3","name":"渑池县","latitude":"34.839691","longitude":"111.802535"},{"code":"411222","parentCode":"411200","level":"3","name":"陕县","latitude":"34.643633","longitude":"111.376065"},{"code":"411224","parentCode":"411200","level":"3","name":"卢氏县","latitude":"33.973394","longitude":"110.994724"},{"code":"411281","parentCode":"411200","level":"3","name":"义马市","latitude":"34.749525","longitude":"111.906093"},{"code":"411282","parentCode":"411200","level":"3","name":"灵宝市","latitude":"34.437104","longitude":"110.779737"},{"code":"411300","parentCode":"410000","level":"2","name":"南阳市","latitude":"33.01142","longitude":"112.542842"},{"code":"411301","parentCode":"411300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"411302","parentCode":"411300","level":"3","name":"宛城区","latitude":"32.934703","longitude":"112.613908"},{"code":"411303","parentCode":"411300","level":"3","name":"卧龙区","latitude":"33.009839","longitude":"112.484267"},{"code":"411321","parentCode":"411300","level":"3","name":"南召县","latitude":"33.472842","longitude":"112.393666"},{"code":"411322","parentCode":"411300","level":"3","name":"方城县","latitude":"33.299954","longitude":"113.016822"},{"code":"411323","parentCode":"411300","level":"3","name":"西峡县","latitude":"3.486925","longitude":"111.43899"},{"code":"411324","parentCode":"411300","level":"3","name":"镇平县","latitude":"33.070817","longitude":"112.193285"},{"code":"411325","parentCode":"411300","level":"3","name":"内乡县","latitude":"33.224377","longitude":"111.847405"},{"code":"411326","parentCode":"411300","level":"3","name":"淅川县","latitude":"32.989723","longitude":"111.445396"},{"code":"411327","parentCode":"411300","level":"3","name":"社旗县","latitude":"32.982431","longitude":"112.998527"},{"code":"411328","parentCode":"411300","level":"3","name":"唐河县","latitude":"32.619993","longitude":"112.859118"},{"code":"411329","parentCode":"411300","level":"3","name":"新野县","latitude":"32.553441","longitude":"112.415991"},{"code":"411330","parentCode":"411300","level":"3","name":"桐柏县","latitude":"32.49565","longitude":"113.434169"},{"code":"411381","parentCode":"411300","level":"3","name":"邓州市","latitude":"32.68465","longitude":"112.056861"},{"code":"411400","parentCode":"410000","level":"2","name":"商丘市","latitude":"34.438589","longitude":"115.641886"},{"code":"411401","parentCode":"411400","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"411402","parentCode":"411400","level":"3","name":"梁园区","latitude":"34.50304","longitude":"115.637731"},{"code":"411403","parentCode":"411400","level":"3","name":"睢阳区","latitude":"34.286755","longitude":"115.589784"},{"code":"411421","parentCode":"411400","level":"3","name":"民权县","latitude":"34.696117","longitude":"115.178414"},{"code":"411422","parentCode":"411400","level":"3","name":"睢县","latitude":"34.39976","longitude":"115.043001"},{"code":"411423","parentCode":"411400","level":"3","name":"宁陵县","latitude":"34.454602","longitude":"115.298401"},{"code":"411424","parentCode":"411400","level":"3","name":"柘城县","latitude":"34.111652","longitude":"115.309042"},{"code":"411425","parentCode":"411400","level":"3","name":"虞城县","latitude":"34.369072","longitude":"115.914225"},{"code":"411426","parentCode":"411400","level":"3","name":"夏邑县","latitude":"34.223681","longitude":"116.157454"},{"code":"411481","parentCode":"411400","level":"3","name":"永城市","latitude":"33.972013","longitude":"116.330775"},{"code":"411500","parentCode":"410000","level":"2","name":"信阳市","latitude":"32.128582","longitude":"114.085491"},{"code":"411501","parentCode":"411500","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"411502","parentCode":"411500","level":"3","name":"浉河区","latitude":"","longitude":""},{"code":"411503","parentCode":"411500","level":"3","name":"平桥区","latitude":"32.30784","longitude":"114.139086"},{"code":"411521","parentCode":"411500","level":"3","name":"罗山县","latitude":"32.03123","longitude":"114.443563"},{"code":"411522","parentCode":"411500","level":"3","name":"光山县","latitude":"31.941432","longitude":"114.843162"},{"code":"411523","parentCode":"411500","level":"3","name":"新县","latitude":"31.646279","longitude":"114.859089"},{"code":"411524","parentCode":"411500","level":"3","name":"商城县","latitude":"31.766262","longitude":"115.375246"},{"code":"411525","parentCode":"411500","level":"3","name":"固始县","latitude":"32.136944","longitude":"115.709743"},{"code":"411526","parentCode":"411500","level":"3","name":"潢川县","latitude":"2.132798","longitude":"115.16441"},{"code":"411527","parentCode":"411500","level":"3","name":"淮滨县","latitude":"32.446574","longitude":"115.324561"},{"code":"411528","parentCode":"411500","level":"3","name":"息县","latitude":"32.410808","longitude":"114.871682"},{"code":"411600","parentCode":"410000","level":"2","name":"周口市","latitude":"33.623741","longitude":"114.654102"},{"code":"411601","parentCode":"411600","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"411602","parentCode":"411600","level":"3","name":"川汇区","latitude":"3.630876","longitude":"114.65795"},{"code":"411621","parentCode":"411600","level":"3","name":"扶沟县","latitude":"34.100655","longitude":"114.437327"},{"code":"411622","parentCode":"411600","level":"3","name":"西华县","latitude":"33.793632","longitude":"114.478087"},{"code":"411623","parentCode":"411600","level":"3","name":"商水县","latitude":"33.520933","longitude":"114.559577"},{"code":"411624","parentCode":"411600","level":"3","name":"沈丘县","latitude":"33.29515","longitude":"115.178718"},{"code":"411625","parentCode":"411600","level":"3","name":"郸城县","latitude":"33.6415","longitude":"115.301297"},{"code":"411626","parentCode":"411600","level":"3","name":"淮阳县","latitude":"33.709947","longitude":"114.902018"},{"code":"411627","parentCode":"411600","level":"3","name":"太康县","latitude":"34.097096","longitude":"114.855701"},{"code":"411628","parentCode":"411600","level":"3","name":"鹿邑县","latitude":"33.894051","longitude":"115.383983"},{"code":"411681","parentCode":"411600","level":"3","name":"项城市","latitude":"3.27447","longitude":"114.89338"},{"code":"411700","parentCode":"410000","level":"2","name":"驻马店市","latitude":"32.983158","longitude":"114.049154"},{"code":"411701","parentCode":"411700","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"411702","parentCode":"411700","level":"3","name":"驿城区","latitude":"2.968357","longitude":"114.00829"},{"code":"411721","parentCode":"411700","level":"3","name":"西平县","latitude":"33.371549","longitude":"113.922838"},{"code":"411722","parentCode":"411700","level":"3","name":"上蔡县","latitude":"33.301221","longitude":"114.409239"},{"code":"411723","parentCode":"411700","level":"3","name":"平舆县","latitude":"32.992144","longitude":"114.647449"},{"code":"411724","parentCode":"411700","level":"3","name":"正阳县","latitude":"32.546931","longitude":"114.497961"},{"code":"411725","parentCode":"411700","level":"3","name":"确山县","latitude":"2.711951","longitude":"113.96359"},{"code":"411726","parentCode":"411700","level":"3","name":"泌阳县","latitude":"32.883864","longitude":"113.447174"},{"code":"411727","parentCode":"411700","level":"3","name":"汝南县","latitude":"32.921968","longitude":"114.325776"},{"code":"411728","parentCode":"411700","level":"3","name":"遂平县","latitude":"33.167855","longitude":"113.902485"},{"code":"411729","parentCode":"411700","level":"3","name":"新蔡县","latitude":"32.783574","longitude":"114.949393"},{"code":"419000","parentCode":"410000","level":"2","name":"省直辖县级行政区划","latitude":"","longitude":""},{"code":"419001","parentCode":"419000","level":"3","name":"济源市","latitude":"35.105365","longitude":"112.405268"},{"code":"420000","parentCode":"0","level":"1","name":"湖北省","latitude":"31.209316","longitude":"112.410562"},{"code":"420100","parentCode":"420000","level":"2","name":"武汉市","latitude":"30.581084","longitude":"114.3162"},{"code":"420101","parentCode":"420100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"420102","parentCode":"420100","level":"3","name":"江岸区","latitude":"30.656091","longitude":"114.332868"},{"code":"420103","parentCode":"420100","level":"3","name":"江汉区","latitude":"30.610951","longitude":"114.266384"},{"code":"420104","parentCode":"420100","level":"3","name":"硚口区","latitude":"","longitude":""},{"code":"420105","parentCode":"420100","level":"3","name":"汉阳区","latitude":"30.547265","longitude":"114.217592"},{"code":"420106","parentCode":"420100","level":"3","name":"武昌区","latitude":"30.56486","longitude":"114.353622"},{"code":"420107","parentCode":"420100","level":"3","name":"青山区","latitude":"40.658778","longitude":"109.903675"},{"code":"420111","parentCode":"420100","level":"3","name":"洪山区","latitude":"30.543623","longitude":"114.433896"},{"code":"420112","parentCode":"420100","level":"3","name":"东西湖区","latitude":"30.698153","longitude":"114.087155"},{"code":"420113","parentCode":"420100","level":"3","name":"汉南区","latitude":"30.28714","longitude":"113.962732"},{"code":"420114","parentCode":"420100","level":"3","name":"蔡甸区","latitude":"30.456184","longitude":"113.972065"},{"code":"420115","parentCode":"420100","level":"3","name":"江夏区","latitude":"30.252484","longitude":"114.367082"},{"code":"420116","parentCode":"420100","level":"3","name":"黄陂区","latitude":"30.985286","longitude":"114.364644"},{"code":"420117","parentCode":"420100","level":"3","name":"新洲区","latitude":"30.803888","longitude":"114.762085"},{"code":"420200","parentCode":"420000","level":"2","name":"黄石市","latitude":"30.216127","longitude":"115.050683"},{"code":"420201","parentCode":"420200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"420202","parentCode":"420200","level":"3","name":"黄石港区","latitude":"30.233765","longitude":"115.073159"},{"code":"420203","parentCode":"420200","level":"3","name":"西塞山区","latitude":"30.184486","longitude":"115.132267"},{"code":"420204","parentCode":"420200","level":"3","name":"下陆区","latitude":"30.195818","longitude":"114.992987"},{"code":"420205","parentCode":"420200","level":"3","name":"铁山区","latitude":"30.218698","longitude":"114.903009"},{"code":"420222","parentCode":"420200","level":"3","name":"阳新县","latitude":"29.828087","longitude":"115.140493"},{"code":"420281","parentCode":"420200","level":"3","name":"大冶市","latitude":"30.072896","longitude":"114.846142"},{"code":"420300","parentCode":"420000","level":"2","name":"十堰市","latitude":"32.636994","longitude":"110.801229"},{"code":"420301","parentCode":"420300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"420302","parentCode":"420300","level":"3","name":"茅箭区","latitude":"32.605602","longitude":"110.785953"},{"code":"420303","parentCode":"420300","level":"3","name":"张湾区","latitude":"32.66384","longitude":"110.717401"},{"code":"420321","parentCode":"420300","level":"3","name":"郧县","latitude":"32.864335","longitude":"110.736399"},{"code":"420322","parentCode":"420300","level":"3","name":"郧西县","latitude":"33.048428","longitude":"110.150151"},{"code":"420323","parentCode":"420300","level":"3","name":"竹山县","latitude":"2.240142","longitude":"110.07274"},{"code":"420324","parentCode":"420300","level":"3","name":"竹溪县","latitude":"32.037738","longitude":"109.791237"},{"code":"420325","parentCode":"420300","level":"3","name":"房县","latitude":"32.079873","longitude":"110.628795"},{"code":"420381","parentCode":"420300","level":"3","name":"丹江口市","latitude":"32.567477","longitude":"111.193228"},{"code":"420500","parentCode":"420000","level":"2","name":"宜昌市","latitude":"30.732758","longitude":"111.310981"},{"code":"420501","parentCode":"420500","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"420502","parentCode":"420500","level":"3","name":"西陵区","latitude":"30.740828","longitude":"111.313706"},{"code":"420503","parentCode":"420500","level":"3","name":"伍家岗区","latitude":"30.678659","longitude":"111.380922"},{"code":"420504","parentCode":"420500","level":"3","name":"点军区","latitude":"30.625385","longitude":"111.216279"},{"code":"420505","parentCode":"420500","level":"3","name":"猇亭区","latitude":"","longitude":""},{"code":"420506","parentCode":"420500","level":"3","name":"夷陵区","latitude":"30.979971","longitude":"111.310649"},{"code":"420525","parentCode":"420500","level":"3","name":"远安县","latitude":"31.176854","longitude":"111.585113"},{"code":"420526","parentCode":"420500","level":"3","name":"兴山县","latitude":"31.31935","longitude":"110.824406"},{"code":"420527","parentCode":"420500","level":"3","name":"秭归县","latitude":"30.903335","longitude":"110.685993"},{"code":"420528","parentCode":"420500","level":"3","name":"长阳土家族自治县","latitude":"30.482855","longitude":"110.853968"},{"code":"420529","parentCode":"420500","level":"3","name":"五峰土家族自治县","latitude":"30.173165","longitude":"110.709999"},{"code":"420581","parentCode":"420500","level":"3","name":"宜都市","latitude":"30.29492","longitude":"111.375534"},{"code":"420582","parentCode":"420500","level":"3","name":"当阳市","latitude":"30.825538","longitude":"111.842712"},{"code":"420583","parentCode":"420500","level":"3","name":"枝江市","latitude":"30.451767","longitude":"111.728567"},{"code":"420600","parentCode":"420000","level":"2","name":"襄阳市","latitude":"32.229169","longitude":"112.250093"},{"code":"420601","parentCode":"420600","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"420602","parentCode":"420600","level":"3","name":"襄城区","latitude":"31.93536","longitude":"112.017083"},{"code":"420606","parentCode":"420600","level":"3","name":"樊城区","latitude":"32.153953","longitude":"111.928528"},{"code":"420607","parentCode":"420600","level":"3","name":"襄州区","latitude":"","longitude":""},{"code":"420624","parentCode":"420600","level":"3","name":"南漳县","latitude":"31.64328","longitude":"111.764629"},{"code":"420625","parentCode":"420600","level":"3","name":"谷城县","latitude":"32.173452","longitude":"111.495958"},{"code":"420626","parentCode":"420600","level":"3","name":"保康县","latitude":"31.719673","longitude":"111.209905"},{"code":"420682","parentCode":"420600","level":"3","name":"老河口市","latitude":"2.434166","longitude":"111.76583"},{"code":"420683","parentCode":"420600","level":"3","name":"枣阳市","latitude":"32.092511","longitude":"112.772607"},{"code":"420684","parentCode":"420600","level":"3","name":"宜城市","latitude":"31.673335","longitude":"112.372745"},{"code":"420700","parentCode":"420000","level":"2","name":"鄂州市","latitude":"30.384439","longitude":"114.895594"},{"code":"420701","parentCode":"420700","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"420702","parentCode":"420700","level":"3","name":"梁子湖区","latitude":"30.172732","longitude":"114.650029"},{"code":"420703","parentCode":"420700","level":"3","name":"华容区","latitude":"30.473068","longitude":"114.701472"},{"code":"420704","parentCode":"420700","level":"3","name":"鄂城区","latitude":"30.320603","longitude":"114.901016"},{"code":"420800","parentCode":"420000","level":"2","name":"荆门市","latitude":"31.042611","longitude":"112.21733"},{"code":"420801","parentCode":"420800","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"420802","parentCode":"420800","level":"3","name":"东宝区","latitude":"31.129835","longitude":"112.087311"},{"code":"420804","parentCode":"420800","level":"3","name":"掇刀区","latitude":"30.932878","longitude":"112.193923"},{"code":"420821","parentCode":"420800","level":"3","name":"京山县","latitude":"31.085752","longitude":"113.112261"},{"code":"420822","parentCode":"420800","level":"3","name":"沙洋县","latitude":"30.66455","longitude":"112.395983"},{"code":"420881","parentCode":"420800","level":"3","name":"钟祥市","latitude":"31.244981","longitude":"112.584826"},{"code":"420900","parentCode":"420000","level":"2","name":"孝感市","latitude":"30.927955","longitude":"113.935734"},{"code":"420901","parentCode":"420900","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"420902","parentCode":"420900","level":"3","name":"孝南区","latitude":"30.944617","longitude":"114.016142"},{"code":"420921","parentCode":"420900","level":"3","name":"孝昌县","latitude":"31.239759","longitude":"114.034872"},{"code":"420922","parentCode":"420900","level":"3","name":"大悟县","latitude":".578255","longitude":"114.3103"},{"code":"420923","parentCode":"420900","level":"3","name":"云梦县","latitude":"31.004979","longitude":"113.778186"},{"code":"420981","parentCode":"420900","level":"3","name":"应城市","latitude":"0.925709","longitude":"113.55644"},{"code":"420982","parentCode":"420900","level":"3","name":"安陆市","latitude":"31.304355","longitude":"113.633387"},{"code":"420984","parentCode":"420900","level":"3","name":"汉川市","latitude":"30.622039","longitude":"113.681678"},{"code":"421000","parentCode":"420000","level":"2","name":"荆州市","latitude":"30.332591","longitude":"112.241866"},{"code":"421001","parentCode":"421000","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"421002","parentCode":"421000","level":"3","name":"沙市区","latitude":"30.325723","longitude":"112.424109"},{"code":"421003","parentCode":"421000","level":"3","name":"荆州区","latitude":"30.396103","longitude":"112.099857"},{"code":"421022","parentCode":"421000","level":"3","name":"公安县","latitude":"29.95713","longitude":"112.153618"},{"code":"421023","parentCode":"421000","level":"3","name":"监利县","latitude":"29.848933","longitude":"113.001956"},{"code":"421024","parentCode":"421000","level":"3","name":"江陵县","latitude":"30.101503","longitude":"112.473701"},{"code":"421081","parentCode":"421000","level":"3","name":"石首市","latitude":"9.742222","longitude":"112.51436"},{"code":"421083","parentCode":"421000","level":"3","name":"洪湖市","latitude":"29.996772","longitude":"113.538915"},{"code":"421087","parentCode":"421000","level":"3","name":"松滋市","latitude":"30.105224","longitude":"111.696205"},{"code":"421100","parentCode":"420000","level":"2","name":"黄冈市","latitude":"30.446109","longitude":"114.906618"},{"code":"421101","parentCode":"421100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"421102","parentCode":"421100","level":"3","name":"黄州区","latitude":"30.518802","longitude":"114.949569"},{"code":"421121","parentCode":"421100","level":"3","name":"团风县","latitude":"30.723706","longitude":"115.014087"},{"code":"421122","parentCode":"421100","level":"3","name":"红安县","latitude":"31.290123","longitude":"114.628119"},{"code":"421123","parentCode":"421100","level":"3","name":"罗田县","latitude":"30.932373","longitude":"115.481022"},{"code":"421124","parentCode":"421100","level":"3","name":"英山县","latitude":"30.872992","longitude":"115.774302"},{"code":"421125","parentCode":"421100","level":"3","name":"浠水县","latitude":"30.5074","longitude":"115.276251"},{"code":"421126","parentCode":"421100","level":"3","name":"蕲春县","latitude":"30.328717","longitude":"115.600771"},{"code":"421127","parentCode":"421100","level":"3","name":"黄梅县","latitude":"29.998876","longitude":"115.941883"},{"code":"421181","parentCode":"421100","level":"3","name":"麻城市","latitude":"31.217943","longitude":"115.089715"},{"code":"421182","parentCode":"421100","level":"3","name":"武穴市","latitude":"30.015614","longitude":"115.625834"},{"code":"421200","parentCode":"420000","level":"2","name":"咸宁市","latitude":"29.880657","longitude":"114.300061"},{"code":"421201","parentCode":"421200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"421202","parentCode":"421200","level":"3","name":"咸安区","latitude":"29.85465","longitude":"114.391867"},{"code":"421221","parentCode":"421200","level":"3","name":"嘉鱼县","latitude":"30.013807","longitude":"113.967139"},{"code":"421222","parentCode":"421200","level":"3","name":"通城县","latitude":"29.229496","longitude":"113.853266"},{"code":"421223","parentCode":"421200","level":"3","name":"崇阳县","latitude":"29.461789","longitude":"114.067935"},{"code":"421224","parentCode":"421200","level":"3","name":"通山县","latitude":"29.55767","longitude":"114.615246"},{"code":"421281","parentCode":"421200","level":"3","name":"赤壁市","latitude":"29.742561","longitude":"113.889168"},{"code":"421300","parentCode":"420000","level":"2","name":"随州市","latitude":"31.717858","longitude":"113.379358"},{"code":"421301","parentCode":"421300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"421303","parentCode":"421300","level":"3","name":"曾都区","latitude":"31.890024","longitude":"113.294246"},{"code":"421321","parentCode":"421300","level":"3","name":"随县","latitude":"","longitude":""},{"code":"421381","parentCode":"421300","level":"3","name":"广水市","latitude":"31.682325","longitude":"113.812619"},{"code":"422800","parentCode":"420000","level":"2","name":"恩施土家族苗族自治州","latitude":"30.308978","longitude":"109.517433"},{"code":"422801","parentCode":"422800","level":"3","name":"恩施市","latitude":"30.308979","longitude":"109.517433"},{"code":"422802","parentCode":"422800","level":"3","name":"利川市","latitude":".239967","longitude":"108.8271"},{"code":"422822","parentCode":"422800","level":"3","name":"建始县","latitude":"30.578576","longitude":"109.939599"},{"code":"422823","parentCode":"422800","level":"3","name":"巴东县","latitude":"30.827453","longitude":"110.300617"},{"code":"422825","parentCode":"422800","level":"3","name":"宣恩县","latitude":"29.886112","longitude":"109.565069"},{"code":"422826","parentCode":"422800","level":"3","name":"咸丰县","latitude":"29.736284","longitude":"109.003815"},{"code":"422827","parentCode":"422800","level":"3","name":"来凤县","latitude":"29.425663","longitude":"109.246714"},{"code":"422828","parentCode":"422800","level":"3","name":"鹤峰县","latitude":"9.918894","longitude":"110.20022"},{"code":"429000","parentCode":"420000","level":"2","name":"省直辖县级行政区划","latitude":"","longitude":""},{"code":"429004","parentCode":"429000","level":"3","name":"仙桃市","latitude":"30.293966","longitude":"113.387448"},{"code":"429005","parentCode":"429000","level":"3","name":"潜江市","latitude":"30.343116","longitude":"112.768768"},{"code":"429006","parentCode":"429000","level":"3","name":"天门市","latitude":"30.649047","longitude":"113.12623"},{"code":"429021","parentCode":"429000","level":"3","name":"神农架林区","latitude":"31.595768","longitude":"110.487231"},{"code":"430000","parentCode":"0","level":"1","name":"湖南省","latitude":"27.695864","longitude":"111.720664"},{"code":"430100","parentCode":"430000","level":"2","name":"长沙市","latitude":"28.213478","longitude":"112.979353"},{"code":"430101","parentCode":"430100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"430102","parentCode":"430100","level":"3","name":"芙蓉区","latitude":"28.203811","longitude":"113.020969"},{"code":"430103","parentCode":"430100","level":"3","name":"天心区","latitude":"28.144471","longitude":"112.996195"},{"code":"430104","parentCode":"430100","level":"3","name":"岳麓区","latitude":"28.202707","longitude":"112.908699"},{"code":"430105","parentCode":"430100","level":"3","name":"开福区","latitude":"8.260219","longitude":"113.02473"},{"code":"430111","parentCode":"430100","level":"3","name":"雨花区","latitude":"28.146444","longitude":"113.020201"},{"code":"430112","parentCode":"430100","level":"3","name":"望城区","latitude":"","longitude":""},{"code":"430121","parentCode":"430100","level":"3","name":"长沙县","latitude":"28.322759","longitude":"113.224946"},{"code":"430124","parentCode":"430100","level":"3","name":"宁乡县","latitude":"28.131213","longitude":"112.360465"},{"code":"430181","parentCode":"430100","level":"3","name":"浏阳市","latitude":"28.234472","longitude":"113.721985"},{"code":"430200","parentCode":"430000","level":"2","name":"株洲市","latitude":"27.827433","longitude":"113.131695"},{"code":"430201","parentCode":"430200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"430202","parentCode":"430200","level":"3","name":"荷塘区","latitude":"27.907229","longitude":"113.212526"},{"code":"430203","parentCode":"430200","level":"3","name":"芦淞区","latitude":"7.822073","longitude":"113.16976"},{"code":"430204","parentCode":"430200","level":"3","name":"石峰区","latitude":"27.941584","longitude":"113.163511"},{"code":"430211","parentCode":"430200","level":"3","name":"天元区","latitude":"27.777772","longitude":"113.068009"},{"code":"430221","parentCode":"430200","level":"3","name":"株洲县","latitude":"27.535936","longitude":"113.153348"},{"code":"430223","parentCode":"430200","level":"3","name":"攸县","latitude":"27.172268","longitude":"113.487831"},{"code":"430224","parentCode":"430200","level":"3","name":"茶陵县","latitude":"26.806729","longitude":"113.652481"},{"code":"430225","parentCode":"430200","level":"3","name":"炎陵县","latitude":"26.382712","longitude":"113.850536"},{"code":"430281","parentCode":"430200","level":"3","name":"醴陵市","latitude":"27.662279","longitude":"113.470625"},{"code":"430300","parentCode":"430000","level":"2","name":"湘潭市","latitude":"27.835095","longitude":"112.935556"},{"code":"430301","parentCode":"430300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"430302","parentCode":"430300","level":"3","name":"雨湖区","latitude":"7.871843","longitude":"112.89448"},{"code":"430304","parentCode":"430300","level":"3","name":"岳塘区","latitude":"27.927747","longitude":"113.023488"},{"code":"430321","parentCode":"430300","level":"3","name":"湘潭县","latitude":"27.669223","longitude":"112.788805"},{"code":"430381","parentCode":"430300","level":"3","name":"湘乡市","latitude":"27.77668","longitude":"112.355169"},{"code":"430382","parentCode":"430300","level":"3","name":"韶山市","latitude":"27.927333","longitude":"112.533095"},{"code":"430400","parentCode":"430000","level":"2","name":"衡阳市","latitude":"26.898164","longitude":"112.583819"},{"code":"430401","parentCode":"430400","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"430405","parentCode":"430400","level":"3","name":"珠晖区","latitude":"6.882225","longitude":"112.68849"},{"code":"430406","parentCode":"430400","level":"3","name":"雁峰区","latitude":"26.852862","longitude":"112.607907"},{"code":"430407","parentCode":"430400","level":"3","name":"石鼓区","latitude":"26.95888","longitude":"112.602488"},{"code":"430408","parentCode":"430400","level":"3","name":"蒸湘区","latitude":"26.886509","longitude":"112.555047"},{"code":"430412","parentCode":"430400","level":"3","name":"南岳区","latitude":"27.259359","longitude":"112.708767"},{"code":"430421","parentCode":"430400","level":"3","name":"衡阳县","latitude":"27.109626","longitude":"112.351579"},{"code":"430422","parentCode":"430400","level":"3","name":"衡南县","latitude":"26.759845","longitude":"112.648514"},{"code":"430423","parentCode":"430400","level":"3","name":"衡山县","latitude":"7.281912","longitude":"112.71963"},{"code":"430424","parentCode":"430400","level":"3","name":"衡东县","latitude":"27.08508","longitude":"113.029002"},{"code":"430426","parentCode":"430400","level":"3","name":"祁东县","latitude":"26.806848","longitude":"111.961606"},{"code":"430481","parentCode":"430400","level":"3","name":"耒阳市","latitude":"26.423993","longitude":"112.921552"},{"code":"430482","parentCode":"430400","level":"3","name":"常宁市","latitude":"26.365629","longitude":"112.435504"},{"code":"430500","parentCode":"430000","level":"2","name":"邵阳市","latitude":"27.236811","longitude":"111.461525"},{"code":"430501","parentCode":"430500","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"430502","parentCode":"430500","level":"3","name":"双清区","latitude":"27.248222","longitude":"111.545347"},{"code":"430503","parentCode":"430500","level":"3","name":"大祥区","latitude":"27.156737","longitude":"111.486639"},{"code":"430511","parentCode":"430500","level":"3","name":"北塔区","latitude":"27.250338","longitude":"111.422279"},{"code":"430521","parentCode":"430500","level":"3","name":"邵东县","latitude":"7.193654","longitude":"111.85672"},{"code":"430522","parentCode":"430500","level":"3","name":"新邵县","latitude":"27.431199","longitude":"111.471275"},{"code":"430523","parentCode":"430500","level":"3","name":"邵阳县","latitude":"26.984977","longitude":"111.332372"},{"code":"430524","parentCode":"430500","level":"3","name":"隆回县","latitude":"27.351831","longitude":"110.973326"},{"code":"430525","parentCode":"430500","level":"3","name":"洞口县","latitude":"27.103196","longitude":"110.599739"},{"code":"430527","parentCode":"430500","level":"3","name":"绥宁县","latitude":"26.714433","longitude":"110.205985"},{"code":"430528","parentCode":"430500","level":"3","name":"新宁县","latitude":"26.548581","longitude":"110.924698"},{"code":"430529","parentCode":"430500","level":"3","name":"城步苗族自治县","latitude":"26.325515","longitude":"110.325303"},{"code":"430581","parentCode":"430500","level":"3","name":"武冈市","latitude":"26.786578","longitude":"110.745815"},{"code":"430600","parentCode":"430000","level":"2","name":"岳阳市","latitude":"29.378007","longitude":"113.146196"},{"code":"430601","parentCode":"430600","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"430602","parentCode":"430600","level":"3","name":"岳阳楼区","latitude":"9.367743","longitude":"113.15537"},{"code":"430603","parentCode":"430600","level":"3","name":"云溪区","latitude":"29.526211","longitude":"113.353774"},{"code":"430611","parentCode":"430600","level":"3","name":"君山区","latitude":"9.461963","longitude":"112.82353"},{"code":"430621","parentCode":"430600","level":"3","name":"岳阳县","latitude":"29.178499","longitude":"113.237527"},{"code":"430623","parentCode":"430600","level":"3","name":"华容县","latitude":"29.493396","longitude":"112.651009"},{"code":"430624","parentCode":"430600","level":"3","name":"湘阴县","latitude":"28.71309","longitude":"112.805374"},{"code":"430626","parentCode":"430600","level":"3","name":"平江县","latitude":"28.762203","longitude":"113.720846"},{"code":"430681","parentCode":"430600","level":"3","name":"汨罗市","latitude":"28.801958","longitude":"113.125027"},{"code":"430682","parentCode":"430600","level":"3","name":"临湘市","latitude":"29.496146","longitude":"113.519749"},{"code":"430700","parentCode":"430000","level":"2","name":"常德市","latitude":"29.012149","longitude":"111.653718"},{"code":"430701","parentCode":"430700","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"430702","parentCode":"430700","level":"3","name":"武陵区","latitude":"8.996871","longitude":"111.69745"},{"code":"430703","parentCode":"430700","level":"3","name":"鼎城区","latitude":"28.995243","longitude":"111.747796"},{"code":"430721","parentCode":"430700","level":"3","name":"安乡县","latitude":"29.448996","longitude":"112.162437"},{"code":"430722","parentCode":"430700","level":"3","name":"汉寿县","latitude":"28.8648","longitude":"112.044311"},{"code":"430723","parentCode":"430700","level":"3","name":"澧县","latitude":"29.750168","longitude":"111.707703"},{"code":"430724","parentCode":"430700","level":"3","name":"临澧县","latitude":"29.486257","longitude":"111.625422"},{"code":"430725","parentCode":"430700","level":"3","name":"桃源县","latitude":"28.917818","longitude":"111.270707"},{"code":"430726","parentCode":"430700","level":"3","name":"石门县","latitude":"29.801743","longitude":"111.044287"},{"code":"430781","parentCode":"430700","level":"3","name":"津市市","latitude":"9.474442","longitude":"111.90685"},{"code":"430800","parentCode":"430000","level":"2","name":"张家界市","latitude":"29.124889","longitude":"110.48162"},{"code":"430801","parentCode":"430800","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"430802","parentCode":"430800","level":"3","name":"永定区","latitude":"29.088539","longitude":"110.501007"},{"code":"430811","parentCode":"430800","level":"3","name":"武陵源区","latitude":"29.357201","longitude":"110.488496"},{"code":"430821","parentCode":"430800","level":"3","name":"慈利县","latitude":".397693","longitude":"110.9362"},{"code":"430822","parentCode":"430800","level":"3","name":"桑植县","latitude":"29.567692","longitude":"110.187336"},{"code":"430900","parentCode":"430000","level":"2","name":"益阳市","latitude":"28.588088","longitude":"112.366547"},{"code":"430901","parentCode":"430900","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"430902","parentCode":"430900","level":"3","name":"资阳区","latitude":"28.694069","longitude":"112.343121"},{"code":"430903","parentCode":"430900","level":"3","name":"赫山区","latitude":"28.456919","longitude":"112.461324"},{"code":"430921","parentCode":"430900","level":"3","name":"南县","latitude":"29.242714","longitude":"112.444499"},{"code":"430922","parentCode":"430900","level":"3","name":"桃江县","latitude":"28.464142","longitude":"111.990464"},{"code":"430923","parentCode":"430900","level":"3","name":"安化县","latitude":"28.28658","longitude":"111.390782"},{"code":"430981","parentCode":"430900","level":"3","name":"沅江市","latitude":"28.977186","longitude":"112.564942"},{"code":"431000","parentCode":"430000","level":"2","name":"郴州市","latitude":"25.782264","longitude":"113.037704"},{"code":"431001","parentCode":"431000","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"431002","parentCode":"431000","level":"3","name":"北湖区","latitude":"25.679158","longitude":"112.884476"},{"code":"431003","parentCode":"431000","level":"3","name":"苏仙区","latitude":"25.773515","longitude":"113.051002"},{"code":"431021","parentCode":"431000","level":"3","name":"桂阳县","latitude":"25.89349","longitude":"112.608108"},{"code":"431022","parentCode":"431000","level":"3","name":"宜章县","latitude":"25.275887","longitude":"112.933447"},{"code":"431023","parentCode":"431000","level":"3","name":"永兴县","latitude":"26.216492","longitude":"113.198393"},{"code":"431024","parentCode":"431000","level":"3","name":"嘉禾县","latitude":"25.637287","longitude":"112.414353"},{"code":"431025","parentCode":"431000","level":"3","name":"临武县","latitude":"25.343997","longitude":"112.568041"},{"code":"431026","parentCode":"431000","level":"3","name":"汝城县","latitude":"25.555137","longitude":"113.677677"},{"code":"431027","parentCode":"431000","level":"3","name":"桂东县","latitude":"25.986645","longitude":"113.906401"},{"code":"431028","parentCode":"431000","level":"3","name":"安仁县","latitude":"26.580786","longitude":"113.365699"},{"code":"431081","parentCode":"431000","level":"3","name":"资兴市","latitude":"25.937184","longitude":"113.468522"},{"code":"431100","parentCode":"430000","level":"2","name":"永州市","latitude":"26.435972","longitude":"111.614648"},{"code":"431101","parentCode":"431100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"431102","parentCode":"431100","level":"3","name":"零陵区","latitude":"","longitude":""},{"code":"431103","parentCode":"431100","level":"3","name":"冷水滩区","latitude":"26.560382","longitude":"111.621586"},{"code":"431121","parentCode":"431100","level":"3","name":"祁阳县","latitude":"26.460846","longitude":"111.972594"},{"code":"431122","parentCode":"431100","level":"3","name":"东安县","latitude":"26.495588","longitude":"111.342809"},{"code":"431123","parentCode":"431100","level":"3","name":"双牌县","latitude":"25.914933","longitude":"111.716294"},{"code":"431124","parentCode":"431100","level":"3","name":"道县","latitude":"25.499397","longitude":"111.602042"},{"code":"431125","parentCode":"431100","level":"3","name":"江永县","latitude":"25.199988","longitude":"111.253887"},{"code":"431126","parentCode":"431100","level":"3","name":"宁远县","latitude":"25.65384","longitude":"111.988063"},{"code":"431127","parentCode":"431100","level":"3","name":"蓝山县","latitude":"25.319503","longitude":"112.196393"},{"code":"431128","parentCode":"431100","level":"3","name":"新田县","latitude":"25.890527","longitude":"112.234807"},{"code":"431129","parentCode":"431100","level":"3","name":"江华瑶族自治县","latitude":"24.977642","longitude":"111.752496"},{"code":"431200","parentCode":"430000","level":"2","name":"怀化市","latitude":"27.557483","longitude":"109.986959"},{"code":"431201","parentCode":"431200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"431202","parentCode":"431200","level":"3","name":"鹤城区","latitude":"27.612024","longitude":"109.945539"},{"code":"431221","parentCode":"431200","level":"3","name":"中方县","latitude":"27.520935","longitude":"110.165362"},{"code":"431222","parentCode":"431200","level":"3","name":"沅陵县","latitude":"28.576605","longitude":"110.601178"},{"code":"431223","parentCode":"431200","level":"3","name":"辰溪县","latitude":"27.895902","longitude":"110.273009"},{"code":"431224","parentCode":"431200","level":"3","name":"溆浦县","latitude":"27.83591","longitude":"110.658581"},{"code":"431225","parentCode":"431200","level":"3","name":"会同县","latitude":"26.914136","longitude":"109.809945"},{"code":"431226","parentCode":"431200","level":"3","name":"麻阳苗族自治县","latitude":"27.791376","longitude":"109.729179"},{"code":"431227","parentCode":"431200","level":"3","name":"新晃侗族自治县","latitude":"27.234509","longitude":"109.168741"},{"code":"431228","parentCode":"431200","level":"3","name":"芷江侗族自治县","latitude":"27.40251","longitude":"109.611105"},{"code":"431229","parentCode":"431200","level":"3","name":"靖州苗族侗族自治县","latitude":"26.550431","longitude":"109.590833"},{"code":"431230","parentCode":"431200","level":"3","name":"通道侗族自治县","latitude":"26.215115","longitude":"109.744661"},{"code":"431281","parentCode":"431200","level":"3","name":"洪江市","latitude":"27.239105","longitude":"110.087193"},{"code":"431300","parentCode":"430000","level":"2","name":"娄底市","latitude":"27.741073","longitude":"111.996396"},{"code":"431301","parentCode":"431300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"431302","parentCode":"431300","level":"3","name":"娄星区","latitude":"27.766945","longitude":"112.004619"},{"code":"431321","parentCode":"431300","level":"3","name":"双峰县","latitude":"27.465564","longitude":"112.187923"},{"code":"431322","parentCode":"431300","level":"3","name":"新化县","latitude":"27.873273","longitude":"111.246845"},{"code":"431381","parentCode":"431300","level":"3","name":"冷水江市","latitude":"27.684915","longitude":"111.493942"},{"code":"431382","parentCode":"431300","level":"3","name":"涟源市","latitude":"27.743727","longitude":"111.794581"},{"code":"433100","parentCode":"430000","level":"2","name":"湘西土家族苗族自治州","latitude":"28.317951","longitude":"109.745746"},{"code":"433101","parentCode":"433100","level":"3","name":"吉首市","latitude":"28.317143","longitude":"109.770926"},{"code":"433122","parentCode":"433100","level":"3","name":"泸溪县","latitude":".164426","longitude":"110.0027"},{"code":"433123","parentCode":"433100","level":"3","name":"凤凰县","latitude":"28.013633","longitude":"109.518882"},{"code":"433124","parentCode":"433100","level":"3","name":"花垣县","latitude":"28.431708","longitude":"109.439129"},{"code":"433125","parentCode":"433100","level":"3","name":"保靖县","latitude":"28.672428","longitude":"109.573905"},{"code":"433126","parentCode":"433100","level":"3","name":"古丈县","latitude":"28.603594","longitude":"110.008149"},{"code":"433127","parentCode":"433100","level":"3","name":"永顺县","latitude":"29.015941","longitude":"109.976124"},{"code":"433130","parentCode":"433100","level":"3","name":"龙山县","latitude":"29.225529","longitude":"109.515961"},{"code":"440000","parentCode":"0","level":"1","name":"广东省","latitude":"23.408004","longitude":"113.394818"},{"code":"440100","parentCode":"440000","level":"2","name":"广州市","latitude":"23.120049","longitude":"113.30765"},{"code":"440101","parentCode":"440100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"440103","parentCode":"440100","level":"3","name":"荔湾区","latitude":"23.093666","longitude":"113.234423"},{"code":"440104","parentCode":"440100","level":"3","name":"越秀区","latitude":"23.139278","longitude":"113.287833"},{"code":"440105","parentCode":"440100","level":"3","name":"海珠区","latitude":"23.087629","longitude":"113.333841"},{"code":"440106","parentCode":"440100","level":"3","name":"天河区","latitude":"23.166129","longitude":"113.385643"},{"code":"440111","parentCode":"440100","level":"3","name":"白云区","latitude":"23.294514","longitude":"113.331306"},{"code":"440112","parentCode":"440100","level":"3","name":"黄埔区","latitude":"23.108712","longitude":"113.492885"},{"code":"440113","parentCode":"440100","level":"3","name":"番禺区","latitude":".934591","longitude":"113.4168"},{"code":"440114","parentCode":"440100","level":"3","name":"花都区","latitude":"23.446661","longitude":"113.220176"},{"code":"440115","parentCode":"440100","level":"3","name":"南沙区","latitude":"","longitude":""},{"code":"440116","parentCode":"440100","level":"3","name":"萝岗区","latitude":"23.225191","longitude":"113.573742"},{"code":"440183","parentCode":"440100","level":"3","name":"增城市","latitude":"23.346745","longitude":"113.767687"},{"code":"440184","parentCode":"440100","level":"3","name":"从化市","latitude":"23.680249","longitude":"113.684824"},{"code":"440200","parentCode":"440000","level":"2","name":"韶关市","latitude":"24.80296","longitude":"113.594461"},{"code":"440201","parentCode":"440200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"440203","parentCode":"440200","level":"3","name":"武江区","latitude":"24.708193","longitude":"113.379606"},{"code":"440204","parentCode":"440200","level":"3","name":"浈江区","latitude":"4.919162","longitude":"113.57745"},{"code":"440205","parentCode":"440200","level":"3","name":"曲江区","latitude":"","longitude":""},{"code":"440222","parentCode":"440200","level":"3","name":"始兴县","latitude":"24.852706","longitude":"114.115404"},{"code":"440224","parentCode":"440200","level":"3","name":"仁化县","latitude":"25.148466","longitude":"113.785474"},{"code":"440229","parentCode":"440200","level":"3","name":"翁源县","latitude":"24.426735","longitude":"114.030428"},{"code":"440232","parentCode":"440200","level":"3","name":"乳源瑶族自治县","latitude":"24.812052","longitude":"113.175778"},{"code":"440233","parentCode":"440200","level":"3","name":"新丰县","latitude":"24.070092","longitude":"114.141775"},{"code":"440281","parentCode":"440200","level":"3","name":"乐昌市","latitude":"25.244442","longitude":"113.246956"},{"code":"440282","parentCode":"440200","level":"3","name":"南雄市","latitude":"25.189905","longitude":"114.386583"},{"code":"440300","parentCode":"440000","level":"2","name":"深圳市","latitude":"22.546054","longitude":"114.025974"},{"code":"440301","parentCode":"440300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"440303","parentCode":"440300","level":"3","name":"罗湖区","latitude":"22.581934","longitude":"114.156395"},{"code":"440304","parentCode":"440300","level":"3","name":"福田区","latitude":"22.551731","longitude":"114.055593"},{"code":"440305","parentCode":"440300","level":"3","name":"南山区","latitude":"47.298821","longitude":"130.281765"},{"code":"440306","parentCode":"440300","level":"3","name":"宝安区","latitude":"22.707433","longitude":"113.930013"},{"code":"440307","parentCode":"440300","level":"3","name":"龙岗区","latitude":"22.657462","longitude":"114.347696"},{"code":"440308","parentCode":"440300","level":"3","name":"盐田区","latitude":"22.606981","longitude":"114.278483"},{"code":"440400","parentCode":"440000","level":"2","name":"珠海市","latitude":"22.256915","longitude":"113.562447"},{"code":"440401","parentCode":"440400","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"440402","parentCode":"440400","level":"3","name":"香洲区","latitude":"22.2656","longitude":"113.533731"},{"code":"440403","parentCode":"440400","level":"3","name":"斗门区","latitude":"22.216637","longitude":"113.247982"},{"code":"440404","parentCode":"440400","level":"3","name":"金湾区","latitude":"2.047215","longitude":"113.41759"},{"code":"440500","parentCode":"440000","level":"2","name":"汕头市","latitude":"23.383908","longitude":"116.72865"},{"code":"440501","parentCode":"440500","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"440507","parentCode":"440500","level":"3","name":"龙湖区","latitude":"23.408849","longitude":"116.759347"},{"code":"440511","parentCode":"440500","level":"3","name":"金平区","latitude":"23.399888","longitude":"116.651794"},{"code":"440512","parentCode":"440500","level":"3","name":"濠江区","latitude":"23.282443","longitude":"116.711363"},{"code":"440513","parentCode":"440500","level":"3","name":"潮阳区","latitude":"23.347254","longitude":"116.485448"},{"code":"440514","parentCode":"440500","level":"3","name":"潮南区","latitude":"23.181395","longitude":"116.414056"},{"code":"440515","parentCode":"440500","level":"3","name":"澄海区","latitude":"23.532997","longitude":"116.814808"},{"code":"440523","parentCode":"440500","level":"3","name":"南澳县","latitude":"23.439132","longitude":"117.070405"},{"code":"440600","parentCode":"440000","level":"2","name":"佛山市","latitude":"23.035095","longitude":"113.134026"},{"code":"440601","parentCode":"440600","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"440604","parentCode":"440600","level":"3","name":"禅城区","latitude":"23.00421","longitude":"113.070423"},{"code":"440605","parentCode":"440600","level":"3","name":"南海区","latitude":"23.078265","longitude":"113.041381"},{"code":"440606","parentCode":"440600","level":"3","name":"顺德区","latitude":"22.810804","longitude":"113.300576"},{"code":"440607","parentCode":"440600","level":"3","name":"三水区","latitude":"23.294581","longitude":"112.904677"},{"code":"440608","parentCode":"440600","level":"3","name":"高明区","latitude":"22.824523","longitude":"112.683258"},{"code":"440700","parentCode":"440000","level":"2","name":"江门市","latitude":"22.575117","longitude":"113.078125"},{"code":"440701","parentCode":"440700","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"440703","parentCode":"440700","level":"3","name":"蓬江区","latitude":"2.660133","longitude":"113.06077"},{"code":"440704","parentCode":"440700","level":"3","name":"江海区","latitude":"22.554847","longitude":"113.135371"},{"code":"440705","parentCode":"440700","level":"3","name":"新会区","latitude":"22.388215","longitude":"113.034751"},{"code":"440781","parentCode":"440700","level":"3","name":"台山市","latitude":"22.034639","longitude":"112.715908"},{"code":"440783","parentCode":"440700","level":"3","name":"开平市","latitude":"22.374201","longitude":"112.548041"},{"code":"440784","parentCode":"440700","level":"3","name":"鹤山市","latitude":"22.675317","longitude":"112.801618"},{"code":"440785","parentCode":"440700","level":"3","name":"恩平市","latitude":"22.240985","longitude":"112.286461"},{"code":"440800","parentCode":"440000","level":"2","name":"湛江市","latitude":"21.257463","longitude":"110.365067"},{"code":"440801","parentCode":"440800","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"440802","parentCode":"440800","level":"3","name":"赤坎区","latitude":"21.287668","longitude":"110.379723"},{"code":"440803","parentCode":"440800","level":"3","name":"霞山区","latitude":"21.204847","longitude":"110.385196"},{"code":"440804","parentCode":"440800","level":"3","name":"坡头区","latitude":"21.28382","longitude":"110.512726"},{"code":"440811","parentCode":"440800","level":"3","name":"麻章区","latitude":"21.0941","longitude":"110.338022"},{"code":"440823","parentCode":"440800","level":"3","name":"遂溪县","latitude":"21.270307","longitude":"110.039895"},{"code":"440825","parentCode":"440800","level":"3","name":"徐闻县","latitude":"20.429968","longitude":"110.257847"},{"code":"440881","parentCode":"440800","level":"3","name":"廉江市","latitude":"21.645265","longitude":"110.141711"},{"code":"440882","parentCode":"440800","level":"3","name":"雷州市","latitude":"20.796584","longitude":"110.012636"},{"code":"440883","parentCode":"440800","level":"3","name":"吴川市","latitude":"21.441681","longitude":"110.708187"},{"code":"440900","parentCode":"440000","level":"2","name":"茂名市","latitude":"21.668226","longitude":"110.931245"},{"code":"440901","parentCode":"440900","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"440902","parentCode":"440900","level":"3","name":"茂南区","latitude":"1.676116","longitude":"110.86861"},{"code":"440903","parentCode":"440900","level":"3","name":"茂港区","latitude":"21.579411","longitude":"110.986287"},{"code":"440923","parentCode":"440900","level":"3","name":"电白县","latitude":"21.685028","longitude":"111.240553"},{"code":"440981","parentCode":"440900","level":"3","name":"高州市","latitude":"22.035522","longitude":"110.975605"},{"code":"440982","parentCode":"440900","level":"3","name":"化州市","latitude":"21.845482","longitude":"110.539591"},{"code":"440983","parentCode":"440900","level":"3","name":"信宜市","latitude":"22.431974","longitude":"111.125429"},{"code":"441200","parentCode":"440000","level":"2","name":"肇庆市","latitude":"23.078663","longitude":"112.479653"},{"code":"441201","parentCode":"441200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"441202","parentCode":"441200","level":"3","name":"端州区","latitude":"23.103323","longitude":"112.477794"},{"code":"441203","parentCode":"441200","level":"3","name":"鼎湖区","latitude":"23.208968","longitude":"112.625249"},{"code":"441223","parentCode":"441200","level":"3","name":"广宁县","latitude":"23.677207","longitude":"112.443316"},{"code":"441224","parentCode":"441200","level":"3","name":"怀集县","latitude":"3.974273","longitude":"112.18024"},{"code":"441225","parentCode":"441200","level":"3","name":"封开县","latitude":"23.561267","longitude":"111.723487"},{"code":"441226","parentCode":"441200","level":"3","name":"德庆县","latitude":"23.276367","longitude":"111.987268"},{"code":"441283","parentCode":"441200","level":"3","name":"高要市","latitude":"23.082587","longitude":"112.461712"},{"code":"441284","parentCode":"441200","level":"3","name":"四会市","latitude":"23.431444","longitude":"112.687558"},{"code":"441300","parentCode":"440000","level":"2","name":"惠州市","latitude":"23.11354","longitude":"114.410658"},{"code":"441301","parentCode":"441300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"441302","parentCode":"441300","level":"3","name":"惠城区","latitude":"23.163063","longitude":"114.485898"},{"code":"441303","parentCode":"441300","level":"3","name":"惠阳区","latitude":"22.810804","longitude":"113.300576"},{"code":"441322","parentCode":"441300","level":"3","name":"博罗县","latitude":"23.352582","longitude":"114.288475"},{"code":"441323","parentCode":"441300","level":"3","name":"惠东县","latitude":"23.049117","longitude":"114.955518"},{"code":"441324","parentCode":"441300","level":"3","name":"龙门县","latitude":"23.666408","longitude":"114.137243"},{"code":"441400","parentCode":"440000","level":"2","name":"梅州市","latitude":"24.304571","longitude":"116.126403"},{"code":"441401","parentCode":"441400","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"441402","parentCode":"441400","level":"3","name":"梅江区","latitude":"24.29075","longitude":"116.115952"},{"code":"441421","parentCode":"441400","level":"3","name":"梅县","latitude":"24.371491","longitude":"116.199543"},{"code":"441422","parentCode":"441400","level":"3","name":"大埔县","latitude":"24.347934","longitude":"116.664124"},{"code":"441423","parentCode":"441400","level":"3","name":"丰顺县","latitude":"23.916085","longitude":"116.291395"},{"code":"441424","parentCode":"441400","level":"3","name":"五华县","latitude":"3.802833","longitude":"115.64132"},{"code":"441426","parentCode":"441400","level":"3","name":"平远县","latitude":"24.695654","longitude":"115.932656"},{"code":"441427","parentCode":"441400","level":"3","name":"蕉岭县","latitude":"24.683283","longitude":"116.196142"},{"code":"441481","parentCode":"441400","level":"3","name":"兴宁市","latitude":".267311","longitude":"115.7533"},{"code":"441500","parentCode":"440000","level":"2","name":"汕尾市","latitude":"22.778731","longitude":"115.372924"},{"code":"441501","parentCode":"441500","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"441502","parentCode":"441500","level":"3","name":"城区","latitude":"37.857865","longitude":"113.612838"},{"code":"441521","parentCode":"441500","level":"3","name":"海丰县","latitude":"22.9696","longitude":"115.286322"},{"code":"441523","parentCode":"441500","level":"3","name":"陆河县","latitude":"23.284407","longitude":"115.629196"},{"code":"441581","parentCode":"441500","level":"3","name":"陆丰市","latitude":"2.967877","longitude":"115.78803"},{"code":"441600","parentCode":"440000","level":"2","name":"河源市","latitude":"23.757251","longitude":"114.713721"},{"code":"441601","parentCode":"441600","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"441602","parentCode":"441600","level":"3","name":"源城区","latitude":"23.693604","longitude":"114.654484"},{"code":"441621","parentCode":"441600","level":"3","name":"紫金县","latitude":"23.525442","longitude":"115.064471"},{"code":"441622","parentCode":"441600","level":"3","name":"龙川县","latitude":"24.33468","longitude":"115.362292"},{"code":"441623","parentCode":"441600","level":"3","name":"连平县","latitude":"24.340566","longitude":"114.542977"},{"code":"441624","parentCode":"441600","level":"3","name":"和平县","latitude":"24.45211","longitude":"115.011815"},{"code":"441625","parentCode":"441600","level":"3","name":"东源县","latitude":"23.933053","longitude":"114.826946"},{"code":"441700","parentCode":"440000","level":"2","name":"阳江市","latitude":"21.871517","longitude":"111.97701"},{"code":"441701","parentCode":"441700","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"441702","parentCode":"441700","level":"3","name":"江城区","latitude":"21.762804","longitude":"111.930036"},{"code":"441721","parentCode":"441700","level":"3","name":"阳西县","latitude":"21.72061","longitude":"111.600509"},{"code":"441723","parentCode":"441700","level":"3","name":"阳东县","latitude":"21.963855","longitude":"112.066283"},{"code":"441781","parentCode":"441700","level":"3","name":"阳春市","latitude":"22.223898","longitude":"111.694449"},{"code":"441800","parentCode":"440000","level":"2","name":"清远市","latitude":"23.698469","longitude":"113.040773"},{"code":"441801","parentCode":"441800","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"441802","parentCode":"441800","level":"3","name":"清城区","latitude":"23.625856","longitude":"113.114585"},{"code":"441803","parentCode":"441800","level":"3","name":"清新区","latitude":"","longitude":""},{"code":"441821","parentCode":"441800","level":"3","name":"佛冈县","latitude":"23.881077","longitude":"113.566689"},{"code":"441823","parentCode":"441800","level":"3","name":"阳山县","latitude":"4.509486","longitude":"112.68133"},{"code":"441825","parentCode":"441800","level":"3","name":"连山壮族瑶族自治县","latitude":"24.515165","longitude":"112.100806"},{"code":"441826","parentCode":"441800","level":"3","name":"连南瑶族自治县","latitude":"24.574156","longitude":"112.263642"},{"code":"441881","parentCode":"441800","level":"3","name":"英德市","latitude":"24.22568","longitude":"113.323169"},{"code":"441882","parentCode":"441800","level":"3","name":"连州市","latitude":"24.937021","longitude":"112.459189"},{"code":"441900","parentCode":"440000","level":"2","name":"东莞市","latitude":"23.043024","longitude":"113.763434"},{"code":"442000","parentCode":"440000","level":"2","name":"中山市","latitude":"22.545178","longitude":"113.42206"},{"code":"445100","parentCode":"440000","level":"2","name":"潮州市","latitude":"23.661812","longitude":"116.630076"},{"code":"445101","parentCode":"445100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"445102","parentCode":"445100","level":"3","name":"湘桥区","latitude":".700044","longitude":"116.6779"},{"code":"445103","parentCode":"445100","level":"3","name":"潮安区","latitude":"","longitude":""},{"code":"445122","parentCode":"445100","level":"3","name":"饶平县","latitude":"23.86503","longitude":"116.906123"},{"code":"445200","parentCode":"440000","level":"2","name":"揭阳市","latitude":"23.547999","longitude":"116.379501"},{"code":"445201","parentCode":"445200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"445202","parentCode":"445200","level":"3","name":"榕城区","latitude":"23.529453","longitude":"116.369224"},{"code":"445203","parentCode":"445200","level":"3","name":"揭东区","latitude":"","longitude":""},{"code":"445222","parentCode":"445200","level":"3","name":"揭西县","latitude":"23.494712","longitude":"115.916825"},{"code":"445224","parentCode":"445200","level":"3","name":"惠来县","latitude":"23.034047","longitude":"116.224799"},{"code":"445281","parentCode":"445200","level":"3","name":"普宁市","latitude":"23.288954","longitude":"116.078166"},{"code":"445300","parentCode":"440000","level":"2","name":"云浮市","latitude":"22.937976","longitude":"112.050946"},{"code":"445301","parentCode":"445300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"445302","parentCode":"445300","level":"3","name":"云城区","latitude":"22.973002","longitude":"112.171604"},{"code":"445321","parentCode":"445300","level":"3","name":"新兴县","latitude":"22.626992","longitude":"112.217541"},{"code":"445322","parentCode":"445300","level":"3","name":"郁南县","latitude":"23.043633","longitude":"111.619938"},{"code":"445323","parentCode":"445300","level":"3","name":"云安县","latitude":"22.847396","longitude":"111.963134"},{"code":"445381","parentCode":"445300","level":"3","name":"罗定市","latitude":"22.690984","longitude":"111.493242"},{"code":"450000","parentCode":"0","level":"1","name":"广西壮族自治区","latitude":"23.552255","longitude":"108.924274"},{"code":"450100","parentCode":"450000","level":"2","name":"南宁市","latitude":"22.806493","longitude":"108.297234"},{"code":"450101","parentCode":"450100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"450102","parentCode":"450100","level":"3","name":"兴宁区","latitude":"22.924531","longitude":"108.417621"},{"code":"450103","parentCode":"450100","level":"3","name":"青秀区","latitude":"","longitude":""},{"code":"450105","parentCode":"450100","level":"3","name":"江南区","latitude":"22.663807","longitude":"108.135591"},{"code":"450107","parentCode":"450100","level":"3","name":"西乡塘区","latitude":"","longitude":""},{"code":"450108","parentCode":"450100","level":"3","name":"良庆区","latitude":"","longitude":""},{"code":"450109","parentCode":"450100","level":"3","name":"邕宁区","latitude":"","longitude":""},{"code":"450122","parentCode":"450100","level":"3","name":"武鸣县","latitude":"23.258066","longitude":"108.260139"},{"code":"450123","parentCode":"450100","level":"3","name":"隆安县","latitude":"23.110228","longitude":"107.690666"},{"code":"450124","parentCode":"450100","level":"3","name":"马山县","latitude":"23.664943","longitude":"108.169604"},{"code":"450125","parentCode":"450100","level":"3","name":"上林县","latitude":"23.52173","longitude":"108.645815"},{"code":"450126","parentCode":"450100","level":"3","name":"宾阳县","latitude":"23.168344","longitude":"108.940495"},{"code":"450127","parentCode":"450100","level":"3","name":"横县","latitude":"22.774919","longitude":"109.168927"},{"code":"450200","parentCode":"450000","level":"2","name":"柳州市","latitude":"24.329053","longitude":"109.422402"},{"code":"450201","parentCode":"450200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"450202","parentCode":"450200","level":"3","name":"城中区","latitude":"36.606649","longitude":"101.777361"},{"code":"450203","parentCode":"450200","level":"3","name":"鱼峰区","latitude":"24.275816","longitude":"109.456327"},{"code":"450204","parentCode":"450200","level":"3","name":"柳南区","latitude":"24.306184","longitude":"109.343466"},{"code":"450205","parentCode":"450200","level":"3","name":"柳北区","latitude":"24.471743","longitude":"109.413915"},{"code":"450221","parentCode":"450200","level":"3","name":"柳江县","latitude":"24.209539","longitude":"109.274221"},{"code":"450222","parentCode":"450200","level":"3","name":"柳城县","latitude":"24.629882","longitude":"109.230197"},{"code":"450223","parentCode":"450200","level":"3","name":"鹿寨县","latitude":"24.532198","longitude":"109.802816"},{"code":"450224","parentCode":"450200","level":"3","name":"融安县","latitude":"5.139783","longitude":"109.51401"},{"code":"450225","parentCode":"450200","level":"3","name":"融水苗族自治县","latitude":"25.343699","longitude":"109.057863"},{"code":"450226","parentCode":"450200","level":"3","name":"三江侗族自治县","latitude":"25.747566","longitude":"109.510081"},{"code":"450300","parentCode":"450000","level":"2","name":"桂林市","latitude":"25.262901","longitude":"110.26092"},{"code":"450301","parentCode":"450300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"450302","parentCode":"450300","level":"3","name":"秀峰区","latitude":"25.287138","longitude":"110.274549"},{"code":"450303","parentCode":"450300","level":"3","name":"叠彩区","latitude":"25.318874","longitude":"110.336226"},{"code":"450304","parentCode":"450300","level":"3","name":"象山区","latitude":"25.215755","longitude":"110.284608"},{"code":"450305","parentCode":"450300","level":"3","name":"七星区","latitude":"25.26467","longitude":"110.356588"},{"code":"450311","parentCode":"450300","level":"3","name":"雁山区","latitude":"25.112806","longitude":"110.371485"},{"code":"450312","parentCode":"450300","level":"3","name":"临桂区","latitude":"","longitude":""},{"code":"450321","parentCode":"450300","level":"3","name":"阳朔县","latitude":"24.857282","longitude":"110.482929"},{"code":"450323","parentCode":"450300","level":"3","name":"灵川县","latitude":"25.381009","longitude":"110.418129"},{"code":"450324","parentCode":"450300","level":"3","name":"全州县","latitude":"25.936465","longitude":"111.026435"},{"code":"450325","parentCode":"450300","level":"3","name":"兴安县","latitude":"25.607031","longitude":"110.601021"},{"code":"450326","parentCode":"450300","level":"3","name":"永福县","latitude":"4.99733","longitude":"109.91693"},{"code":"450327","parentCode":"450300","level":"3","name":"灌阳县","latitude":"25.458881","longitude":"111.077708"},{"code":"450328","parentCode":"450300","level":"3","name":"龙胜各族自治县","latitude":"5.868328","longitude":"110.01025"},{"code":"450329","parentCode":"450300","level":"3","name":"资源县","latitude":"26.067857","longitude":"110.598427"},{"code":"450330","parentCode":"450300","level":"3","name":"平乐县","latitude":"4.55892","longitude":"110.79769"},{"code":"450331","parentCode":"450300","level":"3","name":"荔浦县","latitude":"","longitude":""},{"code":"450332","parentCode":"450300","level":"3","name":"恭城瑶族自治县","latitude":"24.949326","longitude":"110.909447"},{"code":"450400","parentCode":"450000","level":"2","name":"梧州市","latitude":"23.485395","longitude":"111.305472"},{"code":"450401","parentCode":"450400","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"450403","parentCode":"450400","level":"3","name":"万秀区","latitude":"23.563455","longitude":"111.421626"},{"code":"450405","parentCode":"450400","level":"3","name":"长洲区","latitude":"23.5602","longitude":"111.189914"},{"code":"450406","parentCode":"450400","level":"3","name":"龙圩区","latitude":"","longitude":""},{"code":"450421","parentCode":"450400","level":"3","name":"苍梧县","latitude":"23.626738","longitude":"111.298352"},{"code":"450422","parentCode":"450400","level":"3","name":"藤县","latitude":"23.510903","longitude":"110.778838"},{"code":"450423","parentCode":"450400","level":"3","name":"蒙山县","latitude":"24.133851","longitude":"110.561223"},{"code":"450481","parentCode":"450400","level":"3","name":"岑溪市","latitude":"2.925291","longitude":"111.02872"},{"code":"450500","parentCode":"450000","level":"2","name":"北海市","latitude":"21.472718","longitude":"109.122628"},{"code":"450501","parentCode":"450500","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"450502","parentCode":"450500","level":"3","name":"海城区","latitude":"21.518621","longitude":"109.165344"},{"code":"450503","parentCode":"450500","level":"3","name":"银海区","latitude":"21.489723","longitude":"109.251591"},{"code":"450512","parentCode":"450500","level":"3","name":"铁山港区","latitude":"21.574915","longitude":"109.422489"},{"code":"450521","parentCode":"450500","level":"3","name":"合浦县","latitude":"21.740444","longitude":"109.335393"},{"code":"450600","parentCode":"450000","level":"2","name":"防城港市","latitude":"21.617398","longitude":"108.351791"},{"code":"450601","parentCode":"450600","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"450602","parentCode":"450600","level":"3","name":"港口区","latitude":"21.662036","longitude":"108.449166"},{"code":"450603","parentCode":"450600","level":"3","name":"防城区","latitude":"1.764842","longitude":"108.02974"},{"code":"450621","parentCode":"450600","level":"3","name":"上思县","latitude":"22.053625","longitude":"107.902344"},{"code":"450681","parentCode":"450600","level":"3","name":"东兴市","latitude":"21.62717","longitude":"108.061081"},{"code":"450700","parentCode":"450000","level":"2","name":"钦州市","latitude":"21.97335","longitude":"108.638798"},{"code":"450701","parentCode":"450700","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"450702","parentCode":"450700","level":"3","name":"钦南区","latitude":"21.896681","longitude":"108.816524"},{"code":"450703","parentCode":"450700","level":"3","name":"钦北区","latitude":"22.171133","longitude":"108.528676"},{"code":"450721","parentCode":"450700","level":"3","name":"灵山县","latitude":"22.315716","longitude":"109.147748"},{"code":"450722","parentCode":"450700","level":"3","name":"浦北县","latitude":"22.271304","longitude":"109.542367"},{"code":"450800","parentCode":"450000","level":"2","name":"贵港市","latitude":"23.103373","longitude":"109.613708"},{"code":"450801","parentCode":"450800","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"450802","parentCode":"450800","level":"3","name":"港北区","latitude":"23.244655","longitude":"109.689558"},{"code":"450803","parentCode":"450800","level":"3","name":"港南区","latitude":"22.874751","longitude":"109.709851"},{"code":"450804","parentCode":"450800","level":"3","name":"覃塘区","latitude":"","longitude":""},{"code":"450821","parentCode":"450800","level":"3","name":"平南县","latitude":"23.538683","longitude":"110.412601"},{"code":"450881","parentCode":"450800","level":"3","name":"桂平市","latitude":"23.333281","longitude":"110.087119"},{"code":"450900","parentCode":"450000","level":"2","name":"玉林市","latitude":"22.643974","longitude":"110.151676"},{"code":"450901","parentCode":"450900","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"450902","parentCode":"450900","level":"3","name":"玉州区","latitude":"22.557213","longitude":"110.064534"},{"code":"450903","parentCode":"450900","level":"3","name":"福绵区","latitude":"","longitude":""},{"code":"450921","parentCode":"450900","level":"3","name":"容县","latitude":"22.831614","longitude":"110.610277"},{"code":"450922","parentCode":"450900","level":"3","name":"陆川县","latitude":"22.251747","longitude":"110.272113"},{"code":"450923","parentCode":"450900","level":"3","name":"博白县","latitude":"22.066766","longitude":"109.878905"},{"code":"450924","parentCode":"450900","level":"3","name":"兴业县","latitude":"22.798462","longitude":"109.928611"},{"code":"450981","parentCode":"450900","level":"3","name":"北流市","latitude":"22.52889","longitude":"110.467055"},{"code":"451000","parentCode":"450000","level":"2","name":"百色市","latitude":"23.901512","longitude":"106.631821"},{"code":"451001","parentCode":"451000","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"451002","parentCode":"451000","level":"3","name":"右江区","latitude":"23.941866","longitude":"106.505596"},{"code":"451021","parentCode":"451000","level":"3","name":"田阳县","latitude":"3.729759","longitude":"106.81127"},{"code":"451022","parentCode":"451000","level":"3","name":"田东县","latitude":"23.614585","longitude":"107.191637"},{"code":"451023","parentCode":"451000","level":"3","name":"平果县","latitude":"23.540954","longitude":"107.577512"},{"code":"451024","parentCode":"451000","level":"3","name":"德保县","latitude":"23.382215","longitude":"106.594285"},{"code":"451025","parentCode":"451000","level":"3","name":"靖西县","latitude":"23.149606","longitude":"106.321095"},{"code":"451026","parentCode":"451000","level":"3","name":"那坡县","latitude":"23.247546","longitude":"105.834705"},{"code":"451027","parentCode":"451000","level":"3","name":"凌云县","latitude":"24.363726","longitude":"106.648379"},{"code":"451028","parentCode":"451000","level":"3","name":"乐业县","latitude":"24.829664","longitude":"106.517899"},{"code":"451029","parentCode":"451000","level":"3","name":"田林县","latitude":"24.392538","longitude":"105.999827"},{"code":"451030","parentCode":"451000","level":"3","name":"西林县","latitude":"24.391378","longitude":"105.097327"},{"code":"451031","parentCode":"451000","level":"3","name":"隆林各族自治县","latitude":"24.680433","longitude":"105.303213"},{"code":"451100","parentCode":"450000","level":"2","name":"贺州市","latitude":"24.411054","longitude":"111.552594"},{"code":"451101","parentCode":"451100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"451102","parentCode":"451100","level":"3","name":"八步区","latitude":"24.309336","longitude":"111.688352"},{"code":"451121","parentCode":"451100","level":"3","name":"昭平县","latitude":"24.108073","longitude":"110.976908"},{"code":"451122","parentCode":"451100","level":"3","name":"钟山县","latitude":"24.513865","longitude":"111.248833"},{"code":"451123","parentCode":"451100","level":"3","name":"富川瑶族自治县","latitude":"24.891614","longitude":"111.313243"},{"code":"451200","parentCode":"450000","level":"2","name":"河池市","latitude":"24.699521","longitude":"108.069948"},{"code":"451201","parentCode":"451200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"451202","parentCode":"451200","level":"3","name":"金城江区","latitude":"24.660762","longitude":"107.873444"},{"code":"451221","parentCode":"451200","level":"3","name":"南丹县","latitude":"25.119439","longitude":"107.468001"},{"code":"451222","parentCode":"451200","level":"3","name":"天峨县","latitude":"25.018834","longitude":"106.996594"},{"code":"451223","parentCode":"451200","level":"3","name":"凤山县","latitude":"24.560065","longitude":"107.019716"},{"code":"451224","parentCode":"451200","level":"3","name":"东兰县","latitude":"24.5116","longitude":"107.413534"},{"code":"451225","parentCode":"451200","level":"3","name":"罗城仫佬族自治县","latitude":"24.904568","longitude":"108.827191"},{"code":"451226","parentCode":"451200","level":"3","name":"环江毛南族自治县","latitude":"25.104531","longitude":"108.291985"},{"code":"451227","parentCode":"451200","level":"3","name":"巴马瑶族自治县","latitude":"24.157596","longitude":"107.207666"},{"code":"451228","parentCode":"451200","level":"3","name":"都安瑶族自治县","latitude":"24.169778","longitude":"108.118061"},{"code":"451229","parentCode":"451200","level":"3","name":"大化瑶族自治县","latitude":"23.970745","longitude":"107.711959"},{"code":"451281","parentCode":"451200","level":"3","name":"宜州市","latitude":"24.481177","longitude":"108.546552"},{"code":"451300","parentCode":"450000","level":"2","name":"来宾市","latitude":"23.741166","longitude":"109.231817"},{"code":"451301","parentCode":"451300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"451302","parentCode":"451300","level":"3","name":"兴宾区","latitude":"23.664271","longitude":"109.193205"},{"code":"451321","parentCode":"451300","level":"3","name":"忻城县","latitude":"24.018747","longitude":"108.752319"},{"code":"451322","parentCode":"451300","level":"3","name":"象州县","latitude":"24.01917","longitude":"109.771968"},{"code":"451323","parentCode":"451300","level":"3","name":"武宣县","latitude":"3.610721","longitude":"109.68768"},{"code":"451324","parentCode":"451300","level":"3","name":"金秀瑶族自治县","latitude":"24.089877","longitude":"110.137776"},{"code":"451381","parentCode":"451300","level":"3","name":"合山市","latitude":"3.802816","longitude":"108.94254"},{"code":"451400","parentCode":"450000","level":"2","name":"崇左市","latitude":"22.415455","longitude":"107.357322"},{"code":"451401","parentCode":"451400","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"451402","parentCode":"451400","level":"3","name":"江州区","latitude":"","longitude":""},{"code":"451421","parentCode":"451400","level":"3","name":"扶绥县","latitude":"22.524058","longitude":"107.829125"},{"code":"451422","parentCode":"451400","level":"3","name":"宁明县","latitude":"22.005062","longitude":"107.294659"},{"code":"451423","parentCode":"451400","level":"3","name":"龙州县","latitude":"22.431578","longitude":"106.858539"},{"code":"451424","parentCode":"451400","level":"3","name":"大新县","latitude":"22.813463","longitude":"107.137109"},{"code":"451425","parentCode":"451400","level":"3","name":"天等县","latitude":"23.117161","longitude":"107.081339"},{"code":"451481","parentCode":"451400","level":"3","name":"凭祥市","latitude":"22.093647","longitude":"106.837053"},{"code":"460000","parentCode":"0","level":"1","name":"海南省","latitude":"19.180501","longitude":"109.733755"},{"code":"460100","parentCode":"460000","level":"2","name":"海口市","latitude":"20.022071","longitude":"110.330802"},{"code":"460101","parentCode":"460100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"460105","parentCode":"460100","level":"3","name":"秀英区","latitude":".884344","longitude":"110.2632"},{"code":"460106","parentCode":"460100","level":"3","name":"龙华区","latitude":"19.905351","longitude":"110.335224"},{"code":"460107","parentCode":"460100","level":"3","name":"琼山区","latitude":"9.741334","longitude":"110.48011"},{"code":"460108","parentCode":"460100","level":"3","name":"美兰区","latitude":"19.942909","longitude":"110.507269"},{"code":"460200","parentCode":"460000","level":"2","name":"三亚市","latitude":"18.257776","longitude":"109.522771"},{"code":"460201","parentCode":"460200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"460300","parentCode":"460000","level":"2","name":"三沙市","latitude":"","longitude":""},{"code":"460321","parentCode":"460300","level":"3","name":"西沙群岛","latitude":"16.497085","longitude":"111.673087"},{"code":"460322","parentCode":"460300","level":"3","name":"南沙群岛","latitude":"4.974366","longitude":"112.660302"},{"code":"460323","parentCode":"460300","level":"3","name":"中沙群岛的岛礁及其海域","latitude":"","longitude":""},{"code":"469000","parentCode":"460000","level":"2","name":"省直辖县级行政区划","latitude":"","longitude":""},{"code":"469001","parentCode":"469000","level":"3","name":"五指山市","latitude":"18.831306","longitude":"109.51775"},{"code":"469002","parentCode":"469000","level":"3","name":"琼海市","latitude":"19.21483","longitude":"110.414359"},{"code":"469003","parentCode":"469000","level":"3","name":"儋州市","latitude":"19.571153","longitude":"109.413973"},{"code":"469005","parentCode":"469000","level":"3","name":"文昌市","latitude":"19.750947","longitude":"110.780909"},{"code":"469006","parentCode":"469000","level":"3","name":"万宁市","latitude":"18.839886","longitude":"110.292505"},{"code":"469007","parentCode":"469000","level":"3","name":"东方市","latitude":"18.998161","longitude":"108.85101"},{"code":"469021","parentCode":"469000","level":"3","name":"定安县","latitude":"19.490991","longitude":"110.32009"},{"code":"469022","parentCode":"469000","level":"3","name":"屯昌县","latitude":"19.347749","longitude":"110.063364"},{"code":"469023","parentCode":"469000","level":"3","name":"澄迈县","latitude":"19.693135","longitude":"109.996736"},{"code":"469024","parentCode":"469000","level":"3","name":"临高县","latitude":"19.805922","longitude":"109.724101"},{"code":"469025","parentCode":"469000","level":"3","name":"白沙黎族自治县","latitude":"19.216056","longitude":"109.358586"},{"code":"469026","parentCode":"469000","level":"3","name":"昌江黎族自治县","latitude":"19.222483","longitude":"109.0113"},{"code":"469027","parentCode":"469000","level":"3","name":"乐东黎族自治县","latitude":"18.658614","longitude":"109.062698"},{"code":"469028","parentCode":"469000","level":"3","name":"陵水黎族自治县","latitude":"18.575985","longitude":"109.948661"},{"code":"469029","parentCode":"469000","level":"3","name":"保亭黎族苗族自治县","latitude":"18.597592","longitude":"109.656113"},{"code":"469030","parentCode":"469000","level":"3","name":"琼中黎族苗族自治县","latitude":"19.039771","longitude":"109.861849"},{"code":"500000","parentCode":"0","level":"1","name":"重庆市","latitude":"29.544606","longitude":"106.530635"},{"code":"500100","parentCode":"500000","level":"2","name":"市辖区","latitude":"","longitude":""},{"code":"500101","parentCode":"500100","level":"3","name":"万州区","latitude":"30.710054","longitude":"108.413439"},{"code":"500102","parentCode":"500100","level":"3","name":"涪陵区","latitude":".664671","longitude":"107.3408"},{"code":"500103","parentCode":"500100","level":"3","name":"渝中区","latitude":"29.555236","longitude":"106.546967"},{"code":"500104","parentCode":"500100","level":"3","name":"大渡口区","latitude":"29.42414","longitude":"106.465322"},{"code":"500105","parentCode":"500100","level":"3","name":"江北区","latitude":"29.966392","longitude":"121.493299"},{"code":"500106","parentCode":"500100","level":"3","name":"沙坪坝区","latitude":"29.630548","longitude":"106.374805"},{"code":"500107","parentCode":"500100","level":"3","name":"九龙坡区","latitude":"29.434566","longitude":"106.370595"},{"code":"500108","parentCode":"500100","level":"3","name":"南岸区","latitude":"29.541515","longitude":"106.667178"},{"code":"500109","parentCode":"500100","level":"3","name":"北碚区","latitude":"29.866596","longitude":"106.520342"},{"code":"500110","parentCode":"500100","level":"3","name":"綦江区","latitude":"","longitude":""},{"code":"500111","parentCode":"500100","level":"3","name":"大足区","latitude":"","longitude":""},{"code":"500112","parentCode":"500100","level":"3","name":"渝北区","latitude":"29.816264","longitude":"106.753799"},{"code":"500113","parentCode":"500100","level":"3","name":"巴南区","latitude":"29.378028","longitude":"106.758274"},{"code":"500114","parentCode":"500100","level":"3","name":"黔江区","latitude":"29.440981","longitude":"108.714808"},{"code":"500115","parentCode":"500100","level":"3","name":"长寿区","latitude":"29.960491","longitude":"107.146615"},{"code":"500116","parentCode":"500100","level":"3","name":"江津区","latitude":"","longitude":""},{"code":"500117","parentCode":"500100","level":"3","name":"合川区","latitude":"","longitude":""},{"code":"500118","parentCode":"500100","level":"3","name":"永川区","latitude":"","longitude":""},{"code":"500119","parentCode":"500100","level":"3","name":"南川区","latitude":"","longitude":""},{"code":"500200","parentCode":"500000","level":"2","name":"市辖县","latitude":"","longitude":""},{"code":"500223","parentCode":"500200","level":"3","name":"潼南县","latitude":"30.149933","longitude":"105.819679"},{"code":"500224","parentCode":"500200","level":"3","name":"铜梁县","latitude":"29.818323","longitude":"106.039243"},{"code":"500226","parentCode":"500200","level":"3","name":"荣昌县","latitude":"29.471196","longitude":"105.512656"},{"code":"500227","parentCode":"500200","level":"3","name":"璧山县","latitude":"29.567502","longitude":"106.198727"},{"code":"500228","parentCode":"500200","level":"3","name":"梁平县","latitude":"30.664363","longitude":"107.725428"},{"code":"500229","parentCode":"500200","level":"3","name":"城口县","latitude":"31.888131","longitude":"108.741855"},{"code":"500230","parentCode":"500200","level":"3","name":"丰都县","latitude":"29.890596","longitude":"107.837517"},{"code":"500231","parentCode":"500200","level":"3","name":"垫江县","latitude":"30.259498","longitude":"107.444445"},{"code":"500232","parentCode":"500200","level":"3","name":"武隆县","latitude":"29.379271","longitude":"107.716106"},{"code":"500233","parentCode":"500200","level":"3","name":"忠县","latitude":"30.342003","longitude":"107.921738"},{"code":"500234","parentCode":"500200","level":"3","name":"开县","latitude":"1.277101","longitude":"108.38926"},{"code":"500235","parentCode":"500200","level":"3","name":"云阳县","latitude":"31.042409","longitude":"108.863186"},{"code":"500236","parentCode":"500200","level":"3","name":"奉节县","latitude":"30.958553","longitude":"109.355667"},{"code":"500237","parentCode":"500200","level":"3","name":"巫山县","latitude":"31.121152","longitude":"109.908611"},{"code":"500238","parentCode":"500200","level":"3","name":"巫溪县","latitude":"31.509161","longitude":"109.360531"},{"code":"500240","parentCode":"500200","level":"3","name":"石柱土家族自治县","latitude":"0.099637","longitude":"108.30489"},{"code":"500241","parentCode":"500200","level":"3","name":"秀山土家族苗族自治县","latitude":"28.498315","longitude":"109.025321"},{"code":"500242","parentCode":"500200","level":"3","name":"酉阳土家族苗族自治县","latitude":"28.905278","longitude":"108.806808"},{"code":"500243","parentCode":"500200","level":"3","name":"彭水苗族土家族自治县","latitude":"29.359628","longitude":"108.272868"},{"code":"510000","parentCode":"0","level":"1","name":"四川省","latitude":"30.679943","longitude":"104.067923"},{"code":"510100","parentCode":"510000","level":"2","name":"成都市","latitude":"30.679943","longitude":"104.067923"},{"code":"510101","parentCode":"510100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"510104","parentCode":"510100","level":"3","name":"锦江区","latitude":"30.606302","longitude":"104.124269"},{"code":"510105","parentCode":"510100","level":"3","name":"青羊区","latitude":"30.685102","longitude":"103.988429"},{"code":"510106","parentCode":"510100","level":"3","name":"金牛区","latitude":"30.735622","longitude":"104.061377"},{"code":"510107","parentCode":"510100","level":"3","name":"武侯区","latitude":"0.612882","longitude":"104.04124"},{"code":"510108","parentCode":"510100","level":"3","name":"成华区","latitude":"30.69504","longitude":"104.150032"},{"code":"510112","parentCode":"510100","level":"3","name":"龙泉驿区","latitude":"30.603368","longitude":"104.301181"},{"code":"510113","parentCode":"510100","level":"3","name":"青白江区","latitude":"0.796354","longitude":"104.34643"},{"code":"510114","parentCode":"510100","level":"3","name":"新都区","latitude":"30.839504","longitude":"104.116583"},{"code":"510115","parentCode":"510100","level":"3","name":"温江区","latitude":"","longitude":""},{"code":"510121","parentCode":"510100","level":"3","name":"金堂县","latitude":"30.728613","longitude":"104.615371"},{"code":"510122","parentCode":"510100","level":"3","name":"双流县","latitude":"30.459478","longitude":"104.040899"},{"code":"510124","parentCode":"510100","level":"3","name":"郫县","latitude":"30.839642","longitude":"103.884625"},{"code":"510129","parentCode":"510100","level":"3","name":"大邑县","latitude":"30.614941","longitude":"103.388452"},{"code":"510131","parentCode":"510100","level":"3","name":"蒲江县","latitude":"30.239939","longitude":"103.497738"},{"code":"510132","parentCode":"510100","level":"3","name":"新津县","latitude":"30.427866","longitude":"103.832177"},{"code":"510181","parentCode":"510100","level":"3","name":"都江堰市","latitude":"31.039124","longitude":"103.637342"},{"code":"510182","parentCode":"510100","level":"3","name":"彭州市","latitude":"31.148577","longitude":"103.889866"},{"code":"510183","parentCode":"510100","level":"3","name":"邛崃市","latitude":"30.388736","longitude":"103.376512"},{"code":"510184","parentCode":"510100","level":"3","name":"崇州市","latitude":"30.719641","longitude":"103.529467"},{"code":"510300","parentCode":"510000","level":"2","name":"自贡市","latitude":"29.359157","longitude":"104.776071"},{"code":"510301","parentCode":"510300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"510302","parentCode":"510300","level":"3","name":"自流井区","latitude":"29.282614","longitude":"104.707854"},{"code":"510303","parentCode":"510300","level":"3","name":"贡井区","latitude":"29.314591","longitude":"104.602735"},{"code":"510304","parentCode":"510300","level":"3","name":"大安区","latitude":"29.411548","longitude":"104.877566"},{"code":"510311","parentCode":"510300","level":"3","name":"沿滩区","latitude":"29.24264","longitude":"104.854763"},{"code":"510321","parentCode":"510300","level":"3","name":"荣县","latitude":"29.398978","longitude":"104.372408"},{"code":"510322","parentCode":"510300","level":"3","name":"富顺县","latitude":"9.152297","longitude":"105.02222"},{"code":"510400","parentCode":"510000","level":"2","name":"攀枝花市","latitude":"26.587571","longitude":"101.722423"},{"code":"510401","parentCode":"510400","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"510402","parentCode":"510400","level":"3","name":"东区","latitude":"22.276112","longitude":"114.235394"},{"code":"510403","parentCode":"510400","level":"3","name":"西区","latitude":"26.610869","longitude":"101.555332"},{"code":"510411","parentCode":"510400","level":"3","name":"仁和区","latitude":"26.567907","longitude":"101.669702"},{"code":"510421","parentCode":"510400","level":"3","name":"米易县","latitude":"26.932749","longitude":"102.000726"},{"code":"510422","parentCode":"510400","level":"3","name":"盐边县","latitude":"6.940087","longitude":"101.58605"},{"code":"510500","parentCode":"510000","level":"2","name":"泸州市","latitude":"28.89593","longitude":"105.44397"},{"code":"510501","parentCode":"510500","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"510502","parentCode":"510500","level":"3","name":"江阳区","latitude":"28.876901","longitude":"105.371713"},{"code":"510503","parentCode":"510500","level":"3","name":"纳溪区","latitude":"28.614041","longitude":"105.390606"},{"code":"510504","parentCode":"510500","level":"3","name":"龙马潭区","latitude":"28.98746","longitude":"105.437842"},{"code":"510521","parentCode":"510500","level":"3","name":"泸县","latitude":"29.12492","longitude":"105.508267"},{"code":"510522","parentCode":"510500","level":"3","name":"合江县","latitude":".751865","longitude":"105.9316"},{"code":"510524","parentCode":"510500","level":"3","name":"叙永县","latitude":"28.099207","longitude":"105.468592"},{"code":"510525","parentCode":"510500","level":"3","name":"古蔺县","latitude":"27.983319","longitude":"105.936293"},{"code":"510600","parentCode":"510000","level":"2","name":"德阳市","latitude":"31.13114","longitude":"104.402398"},{"code":"510601","parentCode":"510600","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"510603","parentCode":"510600","level":"3","name":"旌阳区","latitude":"31.179805","longitude":"104.415258"},{"code":"510623","parentCode":"510600","level":"3","name":"中江县","latitude":"30.887114","longitude":"104.804952"},{"code":"510626","parentCode":"510600","level":"3","name":"罗江县","latitude":"1.320265","longitude":"104.53541"},{"code":"510681","parentCode":"510600","level":"3","name":"广汉市","latitude":"31.006481","longitude":"104.298476"},{"code":"510682","parentCode":"510600","level":"3","name":"什邡市","latitude":"31.293694","longitude":"104.019871"},{"code":"510683","parentCode":"510600","level":"3","name":"绵竹市","latitude":"31.436657","longitude":"104.129294"},{"code":"510700","parentCode":"510000","level":"2","name":"绵阳市","latitude":"31.504701","longitude":"104.705519"},{"code":"510701","parentCode":"510700","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"510703","parentCode":"510700","level":"3","name":"涪城区","latitude":"31.435735","longitude":"104.670514"},{"code":"510704","parentCode":"510700","level":"3","name":"游仙区","latitude":"1.518816","longitude":"104.98158"},{"code":"510722","parentCode":"510700","level":"3","name":"三台县","latitude":"31.118872","longitude":"105.042581"},{"code":"510723","parentCode":"510700","level":"3","name":"盐亭县","latitude":"31.247943","longitude":"105.479071"},{"code":"510724","parentCode":"510700","level":"3","name":"安县","latitude":"31.589182","longitude":"104.368786"},{"code":"510725","parentCode":"510700","level":"3","name":"梓潼县","latitude":"31.653621","longitude":"105.193834"},{"code":"510726","parentCode":"510700","level":"3","name":"北川羌族自治县","latitude":"","longitude":""},{"code":"510727","parentCode":"510700","level":"3","name":"平武县","latitude":"32.446912","longitude":"104.404308"},{"code":"510781","parentCode":"510700","level":"3","name":"江油市","latitude":"31.952427","longitude":"104.933149"},{"code":"510800","parentCode":"510000","level":"2","name":"广元市","latitude":"32.44104","longitude":"105.819687"},{"code":"510801","parentCode":"510800","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"510802","parentCode":"510800","level":"3","name":"利州区","latitude":"","longitude":""},{"code":"510811","parentCode":"510800","level":"3","name":"元坝区","latitude":"32.149903","longitude":"105.883379"},{"code":"510812","parentCode":"510800","level":"3","name":"朝天区","latitude":"32.708417","longitude":"106.022164"},{"code":"510821","parentCode":"510800","level":"3","name":"旺苍县","latitude":"32.37214","longitude":"106.401823"},{"code":"510822","parentCode":"510800","level":"3","name":"青川县","latitude":"32.51586","longitude":"105.190447"},{"code":"510823","parentCode":"510800","level":"3","name":"剑阁县","latitude":"31.921948","longitude":"105.503021"},{"code":"510824","parentCode":"510800","level":"3","name":"苍溪县","latitude":"31.918552","longitude":"106.113283"},{"code":"510900","parentCode":"510000","level":"2","name":"遂宁市","latitude":"30.557491","longitude":"105.564888"},{"code":"510901","parentCode":"510900","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"510903","parentCode":"510900","level":"3","name":"船山区","latitude":"","longitude":""},{"code":"510904","parentCode":"510900","level":"3","name":"安居区","latitude":"","longitude":""},{"code":"510921","parentCode":"510900","level":"3","name":"蓬溪县","latitude":"30.657491","longitude":"105.716088"},{"code":"510922","parentCode":"510900","level":"3","name":"射洪县","latitude":"30.908079","longitude":"105.388245"},{"code":"510923","parentCode":"510900","level":"3","name":"大英县","latitude":"30.580191","longitude":"105.256372"},{"code":"511000","parentCode":"510000","level":"2","name":"内江市","latitude":"29.599462","longitude":"105.073056"},{"code":"511001","parentCode":"511000","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"511002","parentCode":"511000","level":"3","name":"市中区","latitude":"36.656617","longitude":"117.002545"},{"code":"511011","parentCode":"511000","level":"3","name":"东兴区","latitude":"29.628089","longitude":"105.202169"},{"code":"511024","parentCode":"511000","level":"3","name":"威远县","latitude":"29.599589","longitude":"104.593976"},{"code":"511025","parentCode":"511000","level":"3","name":"资中县","latitude":"29.813836","longitude":"104.807466"},{"code":"511028","parentCode":"511000","level":"3","name":"隆昌县","latitude":"29.367869","longitude":"105.252958"},{"code":"511100","parentCode":"510000","level":"2","name":"乐山市","latitude":"29.600958","longitude":"103.760824"},{"code":"511101","parentCode":"511100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"511102","parentCode":"511100","level":"3","name":"市中区","latitude":"36.656617","longitude":"117.002545"},{"code":"511111","parentCode":"511100","level":"3","name":"沙湾区","latitude":"29.31641","longitude":"103.604548"},{"code":"511112","parentCode":"511100","level":"3","name":"五通桥区","latitude":"29.395444","longitude":"103.846633"},{"code":"511113","parentCode":"511100","level":"3","name":"金口河区","latitude":"29.29382","longitude":"103.073366"},{"code":"511123","parentCode":"511100","level":"3","name":"犍为县","latitude":"29.23119","longitude":"103.980199"},{"code":"511124","parentCode":"511100","level":"3","name":"井研县","latitude":"9.644501","longitude":"104.05533"},{"code":"511126","parentCode":"511100","level":"3","name":"夹江县","latitude":"29.776107","longitude":"103.559263"},{"code":"511129","parentCode":"511100","level":"3","name":"沐川县","latitude":"29.006905","longitude":"103.826503"},{"code":"511132","parentCode":"511100","level":"3","name":"峨边彝族自治县","latitude":"9.050416","longitude":"103.21674"},{"code":"511133","parentCode":"511100","level":"3","name":"马边彝族自治县","latitude":"28.776739","longitude":"103.481388"},{"code":"511181","parentCode":"511100","level":"3","name":"峨眉山市","latitude":"29.507004","longitude":"103.400912"},{"code":"511300","parentCode":"510000","level":"2","name":"南充市","latitude":"30.800965","longitude":"106.105554"},{"code":"511301","parentCode":"511300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"511302","parentCode":"511300","level":"3","name":"顺庆区","latitude":"30.949625","longitude":"106.115798"},{"code":"511303","parentCode":"511300","level":"3","name":"高坪区","latitude":"30.754684","longitude":"106.259759"},{"code":"511304","parentCode":"511300","level":"3","name":"嘉陵区","latitude":"30.665452","longitude":"105.938703"},{"code":"511321","parentCode":"511300","level":"3","name":"南部县","latitude":"31.349803","longitude":"105.923514"},{"code":"511322","parentCode":"511300","level":"3","name":"营山县","latitude":"31.162323","longitude":"106.718527"},{"code":"511323","parentCode":"511300","level":"3","name":"蓬安县","latitude":"31.007076","longitude":"106.428917"},{"code":"511324","parentCode":"511300","level":"3","name":"仪陇县","latitude":"31.443593","longitude":"106.534725"},{"code":"511325","parentCode":"511300","level":"3","name":"西充县","latitude":"31.063877","longitude":"105.857332"},{"code":"511381","parentCode":"511300","level":"3","name":"阆中市","latitude":"31.602117","longitude":"106.078093"},{"code":"511400","parentCode":"510000","level":"2","name":"眉山市","latitude":"30.061115","longitude":"103.84143"},{"code":"511401","parentCode":"511400","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"511402","parentCode":"511400","level":"3","name":"东坡区","latitude":"30.057372","longitude":"103.748333"},{"code":"511421","parentCode":"511400","level":"3","name":"仁寿县","latitude":"29.985869","longitude":"104.225519"},{"code":"511422","parentCode":"511400","level":"3","name":"彭山县","latitude":"30.254453","longitude":"103.865691"},{"code":"511423","parentCode":"511400","level":"3","name":"洪雅县","latitude":"29.694316","longitude":"103.180159"},{"code":"511424","parentCode":"511400","level":"3","name":"丹棱县","latitude":"30.014803","longitude":"103.434513"},{"code":"511425","parentCode":"511400","level":"3","name":"青神县","latitude":"29.82276","longitude":"103.837508"},{"code":"511500","parentCode":"510000","level":"2","name":"宜宾市","latitude":"28.769675","longitude":"104.633019"},{"code":"511501","parentCode":"511500","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"511502","parentCode":"511500","level":"3","name":"翠屏区","latitude":"28.81582","longitude":"104.693255"},{"code":"511503","parentCode":"511500","level":"3","name":"南溪区","latitude":"","longitude":""},{"code":"511521","parentCode":"511500","level":"3","name":"宜宾县","latitude":"8.906871","longitude":"104.38271"},{"code":"511523","parentCode":"511500","level":"3","name":"江安县","latitude":"28.663533","longitude":"105.128778"},{"code":"511524","parentCode":"511500","level":"3","name":"长宁县","latitude":"28.515434","longitude":"104.931149"},{"code":"511525","parentCode":"511500","level":"3","name":"高县","latitude":"28.4632","longitude":"104.593066"},{"code":"511526","parentCode":"511500","level":"3","name":"珙县","latitude":"28.19699","longitude":"104.806618"},{"code":"511527","parentCode":"511500","level":"3","name":"筠连县","latitude":"28.042099","longitude":"104.588433"},{"code":"511528","parentCode":"511500","level":"3","name":"兴文县","latitude":"28.255538","longitude":"105.141226"},{"code":"511529","parentCode":"511500","level":"3","name":"屏山县","latitude":"28.702429","longitude":"103.999118"},{"code":"511600","parentCode":"510000","level":"2","name":"广安市","latitude":"30.463984","longitude":"106.63572"},{"code":"511601","parentCode":"511600","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"511602","parentCode":"511600","level":"3","name":"广安区","latitude":"30.59925","longitude":"106.758912"},{"code":"511603","parentCode":"511600","level":"3","name":"前锋区","latitude":"","longitude":""},{"code":"511621","parentCode":"511600","level":"3","name":"岳池县","latitude":"30.540769","longitude":"106.420833"},{"code":"511622","parentCode":"511600","level":"3","name":"武胜县","latitude":"30.373905","longitude":"106.231366"},{"code":"511623","parentCode":"511600","level":"3","name":"邻水县","latitude":"30.263284","longitude":"107.003334"},{"code":"511681","parentCode":"511600","level":"3","name":"华蓥市","latitude":"","longitude":""},{"code":"511700","parentCode":"510000","level":"2","name":"达州市","latitude":"31.214199","longitude":"107.494973"},{"code":"511701","parentCode":"511700","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"511702","parentCode":"511700","level":"3","name":"通川区","latitude":"31.238764","longitude":"107.519204"},{"code":"511703","parentCode":"511700","level":"3","name":"达川区","latitude":"","longitude":""},{"code":"511722","parentCode":"511700","level":"3","name":"宣汉县","latitude":"31.519798","longitude":"107.936033"},{"code":"511723","parentCode":"511700","level":"3","name":"开江县","latitude":"31.051587","longitude":"107.891012"},{"code":"511724","parentCode":"511700","level":"3","name":"大竹县","latitude":"30.690772","longitude":"107.279877"},{"code":"511725","parentCode":"511700","level":"3","name":"渠县","latitude":"30.948814","longitude":"106.987602"},{"code":"511781","parentCode":"511700","level":"3","name":"万源市","latitude":"31.986241","longitude":"107.993811"},{"code":"511800","parentCode":"510000","level":"2","name":"雅安市","latitude":"29.999716","longitude":"103.009356"},{"code":"511801","parentCode":"511800","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"511802","parentCode":"511800","level":"3","name":"雨城区","latitude":"29.928507","longitude":"103.038405"},{"code":"511803","parentCode":"511800","level":"3","name":"名山区","latitude":"","longitude":""},{"code":"511822","parentCode":"511800","level":"3","name":"荥经县","latitude":"29.740878","longitude":"102.691946"},{"code":"511823","parentCode":"511800","level":"3","name":"汉源县","latitude":"29.431576","longitude":"102.625136"},{"code":"511824","parentCode":"511800","level":"3","name":"石棉县","latitude":"9.235485","longitude":"102.29397"},{"code":"511825","parentCode":"511800","level":"3","name":"天全县","latitude":"30.078875","longitude":"102.578305"},{"code":"511826","parentCode":"511800","level":"3","name":"芦山县","latitude":"30.440282","longitude":"103.018099"},{"code":"511827","parentCode":"511800","level":"3","name":"宝兴县","latitude":"30.56765","longitude":"102.716894"},{"code":"511900","parentCode":"510000","level":"2","name":"巴中市","latitude":"31.869189","longitude":"106.757916"},{"code":"511901","parentCode":"511900","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"511902","parentCode":"511900","level":"3","name":"巴州区","latitude":"31.785303","longitude":"106.739266"},{"code":"511903","parentCode":"511900","level":"3","name":"恩阳区","latitude":"","longitude":""},{"code":"511921","parentCode":"511900","level":"3","name":"通江县","latitude":"32.136407","longitude":"107.352775"},{"code":"511922","parentCode":"511900","level":"3","name":"南江县","latitude":"32.337239","longitude":"106.836181"},{"code":"511923","parentCode":"511900","level":"3","name":"平昌县","latitude":"31.597715","longitude":"107.167357"},{"code":"512000","parentCode":"510000","level":"2","name":"资阳市","latitude":"30.132191","longitude":"104.63593"},{"code":"512001","parentCode":"512000","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"512002","parentCode":"512000","level":"3","name":"雁江区","latitude":"30.091647","longitude":"104.755417"},{"code":"512021","parentCode":"512000","level":"3","name":"安岳县","latitude":"29.999677","longitude":"105.400876"},{"code":"512022","parentCode":"512000","level":"3","name":"乐至县","latitude":"30.313945","longitude":"105.028316"},{"code":"512081","parentCode":"512000","level":"3","name":"简阳市","latitude":"30.393006","longitude":"104.536305"},{"code":"513200","parentCode":"510000","level":"2","name":"阿坝藏族羌族自治州","latitude":"31.905763","longitude":"102.228565"},{"code":"513221","parentCode":"513200","level":"3","name":"汶川县","latitude":"31.168774","longitude":"103.294317"},{"code":"513222","parentCode":"513200","level":"3","name":"理县","latitude":"31.579557","longitude":"103.021281"},{"code":"513223","parentCode":"513200","level":"3","name":"茂县","latitude":"31.855708","longitude":"103.635043"},{"code":"513224","parentCode":"513200","level":"3","name":"松潘县","latitude":"32.625459","longitude":"103.532712"},{"code":"513225","parentCode":"513200","level":"3","name":"九寨沟县","latitude":"33.317446","longitude":"103.934044"},{"code":"513226","parentCode":"513200","level":"3","name":"金川县","latitude":"31.52757","longitude":"101.804769"},{"code":"513227","parentCode":"513200","level":"3","name":"小金县","latitude":"31.135396","longitude":"102.475493"},{"code":"513228","parentCode":"513200","level":"3","name":"黑水县","latitude":"32.16531","longitude":"103.055487"},{"code":"513229","parentCode":"513200","level":"3","name":"马尔康县","latitude":"32.019304","longitude":"102.023305"},{"code":"513230","parentCode":"513200","level":"3","name":"壤塘县","latitude":"32.148226","longitude":"101.059717"},{"code":"513231","parentCode":"513200","level":"3","name":"阿坝县","latitude":"32.890762","longitude":"101.787569"},{"code":"513232","parentCode":"513200","level":"3","name":"若尔盖县","latitude":"33.668819","longitude":"102.895582"},{"code":"513233","parentCode":"513200","level":"3","name":"红原县","latitude":"2.736132","longitude":"102.64115"},{"code":"513300","parentCode":"510000","level":"2","name":"甘孜藏族自治州","latitude":"30.055144","longitude":"101.969232"},{"code":"513321","parentCode":"513300","level":"3","name":"康定县","latitude":"29.957989","longitude":"101.755331"},{"code":"513322","parentCode":"513300","level":"3","name":"泸定县","latitude":"29.747744","longitude":"102.120066"},{"code":"513323","parentCode":"513300","level":"3","name":"丹巴县","latitude":"30.967074","longitude":"101.752398"},{"code":"513324","parentCode":"513300","level":"3","name":"九龙县","latitude":"8.917804","longitude":"101.63508"},{"code":"513325","parentCode":"513300","level":"3","name":"雅江县","latitude":"9.922924","longitude":"100.96924"},{"code":"513326","parentCode":"513300","level":"3","name":"道孚县","latitude":"30.870126","longitude":"101.194842"},{"code":"513327","parentCode":"513300","level":"3","name":"炉霍县","latitude":"31.492155","longitude":"100.675871"},{"code":"513328","parentCode":"513300","level":"3","name":"甘孜县","latitude":"2.029329","longitude":"99.762677"},{"code":"513329","parentCode":"513300","level":"3","name":"新龙县","latitude":"30.945763","longitude":"100.287518"},{"code":"513330","parentCode":"513300","level":"3","name":"德格县","latitude":"2.059409","longitude":"98.967481"},{"code":"513331","parentCode":"513300","level":"3","name":"白玉县","latitude":"1.052586","longitude":"99.291922"},{"code":"513332","parentCode":"513300","level":"3","name":"石渠县","latitude":"3.187627","longitude":"98.204993"},{"code":"513333","parentCode":"513300","level":"3","name":"色达县","latitude":"32.35662","longitude":"100.213885"},{"code":"513334","parentCode":"513300","level":"3","name":"理塘县","latitude":"9.895283","longitude":"100.18511"},{"code":"513335","parentCode":"513300","level":"3","name":"巴塘县","latitude":"9.916288","longitude":"99.300291"},{"code":"513336","parentCode":"513300","level":"3","name":"乡城县","latitude":"9.117376","longitude":"99.738452"},{"code":"513337","parentCode":"513300","level":"3","name":"稻城县","latitude":"28.766497","longitude":"100.265891"},{"code":"513338","parentCode":"513300","level":"3","name":"得荣县","latitude":"8.736358","longitude":"99.324235"},{"code":"513400","parentCode":"510000","level":"2","name":"凉山彝族自治州","latitude":"27.892393","longitude":"102.259591"},{"code":"513401","parentCode":"513400","level":"3","name":"西昌市","latitude":"27.863377","longitude":"102.117888"},{"code":"513422","parentCode":"513400","level":"3","name":"木里藏族自治县","latitude":"28.360344","longitude":"100.953057"},{"code":"513423","parentCode":"513400","level":"3","name":"盐源县","latitude":"27.603028","longitude":"101.467624"},{"code":"513424","parentCode":"513400","level":"3","name":"德昌县","latitude":"27.331194","longitude":"102.191734"},{"code":"513425","parentCode":"513400","level":"3","name":"会理县","latitude":"26.591301","longitude":"102.263927"},{"code":"513426","parentCode":"513400","level":"3","name":"会东县","latitude":"26.573608","longitude":"102.742967"},{"code":"513427","parentCode":"513400","level":"3","name":"宁南县","latitude":"27.09125","longitude":"102.716634"},{"code":"513428","parentCode":"513400","level":"3","name":"普格县","latitude":"27.548286","longitude":"102.568091"},{"code":"513429","parentCode":"513400","level":"3","name":"布拖县","latitude":"27.599974","longitude":"102.881928"},{"code":"513430","parentCode":"513400","level":"3","name":"金阳县","latitude":"27.706169","longitude":"103.201059"},{"code":"513431","parentCode":"513400","level":"3","name":"昭觉县","latitude":"28.013719","longitude":"102.832818"},{"code":"513432","parentCode":"513400","level":"3","name":"喜德县","latitude":"28.196489","longitude":"102.449968"},{"code":"513433","parentCode":"513400","level":"3","name":"冕宁县","latitude":"28.514859","longitude":"102.068914"},{"code":"513434","parentCode":"513400","level":"3","name":"越西县","latitude":"28.59219","longitude":"102.628681"},{"code":"513435","parentCode":"513400","level":"3","name":"甘洛县","latitude":"28.974853","longitude":"102.767401"},{"code":"513436","parentCode":"513400","level":"3","name":"美姑县","latitude":"8.443545","longitude":"103.10173"},{"code":"513437","parentCode":"513400","level":"3","name":"雷波县","latitude":"28.279341","longitude":"103.512505"},{"code":"520000","parentCode":"0","level":"1","name":"贵州省","latitude":"26.902826","longitude":"106.734996"},{"code":"520100","parentCode":"520000","level":"2","name":"贵阳市","latitude":"26.629907","longitude":"106.709177"},{"code":"520101","parentCode":"520100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"520102","parentCode":"520100","level":"3","name":"南明区","latitude":"26.541413","longitude":"106.724173"},{"code":"520103","parentCode":"520100","level":"3","name":"云岩区","latitude":"26.603525","longitude":"106.717914"},{"code":"520111","parentCode":"520100","level":"3","name":"花溪区","latitude":"26.397917","longitude":"106.665273"},{"code":"520112","parentCode":"520100","level":"3","name":"乌当区","latitude":"6.688326","longitude":"106.73345"},{"code":"520113","parentCode":"520100","level":"3","name":"白云区","latitude":"23.294514","longitude":"113.331306"},{"code":"520115","parentCode":"520100","level":"3","name":"观山湖区","latitude":"","longitude":""},{"code":"520121","parentCode":"520100","level":"3","name":"开阳县","latitude":"27.075427","longitude":"107.046881"},{"code":"520122","parentCode":"520100","level":"3","name":"息烽县","latitude":"7.150808","longitude":"106.68198"},{"code":"520123","parentCode":"520100","level":"3","name":"修文县","latitude":"26.931538","longitude":"106.590593"},{"code":"520181","parentCode":"520100","level":"3","name":"清镇市","latitude":"26.688621","longitude":"106.353814"},{"code":"520200","parentCode":"520000","level":"2","name":"六盘水市","latitude":"26.591866","longitude":"104.852087"},{"code":"520201","parentCode":"520200","level":"3","name":"钟山区","latitude":"26.731157","longitude":"104.762547"},{"code":"520203","parentCode":"520200","level":"3","name":"六枝特区","latitude":"26.235865","longitude":"105.383034"},{"code":"520221","parentCode":"520200","level":"3","name":"水城县","latitude":"26.430547","longitude":"104.930357"},{"code":"520222","parentCode":"520200","level":"3","name":"盘县","latitude":"25.772838","longitude":"104.666913"},{"code":"520300","parentCode":"520000","level":"2","name":"遵义市","latitude":"27.699961","longitude":"106.93126"},{"code":"520301","parentCode":"520300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"520302","parentCode":"520300","level":"3","name":"红花岗区","latitude":"27.670445","longitude":"106.922651"},{"code":"520303","parentCode":"520300","level":"3","name":"汇川区","latitude":"","longitude":""},{"code":"520321","parentCode":"520300","level":"3","name":"遵义县","latitude":"27.669342","longitude":"106.902662"},{"code":"520322","parentCode":"520300","level":"3","name":"桐梓县","latitude":"28.41448","longitude":"106.886331"},{"code":"520323","parentCode":"520300","level":"3","name":"绥阳县","latitude":"28.146365","longitude":"107.203542"},{"code":"520324","parentCode":"520300","level":"3","name":"正安县","latitude":"28.506639","longitude":"107.412773"},{"code":"520325","parentCode":"520300","level":"3","name":"道真仡佬族苗族自治县","latitude":"28.934154","longitude":"107.616865"},{"code":"520326","parentCode":"520300","level":"3","name":"务川仡佬族苗族自治县","latitude":"28.661404","longitude":"107.919935"},{"code":"520327","parentCode":"520300","level":"3","name":"凤冈县","latitude":"27.928827","longitude":"107.771574"},{"code":"520328","parentCode":"520300","level":"3","name":"湄潭县","latitude":"7.764873","longitude":"107.49168"},{"code":"520329","parentCode":"520300","level":"3","name":"余庆县","latitude":"7.394794","longitude":"107.70936"},{"code":"520330","parentCode":"520300","level":"3","name":"习水县","latitude":"28.35732","longitude":"106.358926"},{"code":"520381","parentCode":"520300","level":"3","name":"赤水市","latitude":"28.493334","longitude":"105.920513"},{"code":"520382","parentCode":"520300","level":"3","name":"仁怀市","latitude":"27.839203","longitude":"106.347908"},{"code":"520400","parentCode":"520000","level":"2","name":"安顺市","latitude":"26.228595","longitude":"105.92827"},{"code":"520401","parentCode":"520400","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"520402","parentCode":"520400","level":"3","name":"西秀区","latitude":"26.197377","longitude":"106.060017"},{"code":"520421","parentCode":"520400","level":"3","name":"平坝县","latitude":"26.408769","longitude":"106.291405"},{"code":"520422","parentCode":"520400","level":"3","name":"普定县","latitude":"26.345748","longitude":"105.742693"},{"code":"520423","parentCode":"520400","level":"3","name":"镇宁布依族苗族自治县","latitude":"25.844353","longitude":"105.833553"},{"code":"520424","parentCode":"520400","level":"3","name":"关岭布依族苗族自治县","latitude":"25.86219","longitude":"105.568727"},{"code":"520425","parentCode":"520400","level":"3","name":"紫云苗族布依族自治县","latitude":"25.700615","longitude":"106.188362"},{"code":"520500","parentCode":"520000","level":"2","name":"毕节市","latitude":"27.408562","longitude":"105.333323"},{"code":"520501","parentCode":"520500","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"520502","parentCode":"520500","level":"3","name":"七星关区","latitude":"","longitude":""},{"code":"520521","parentCode":"520500","level":"3","name":"大方县","latitude":"27.23128","longitude":"105.694534"},{"code":"520522","parentCode":"520500","level":"3","name":"黔西县","latitude":"27.050413","longitude":"106.120805"},{"code":"520523","parentCode":"520500","level":"3","name":"金沙县","latitude":"27.448578","longitude":"106.275142"},{"code":"520524","parentCode":"520500","level":"3","name":"织金县","latitude":"26.648167","longitude":"105.792167"},{"code":"520525","parentCode":"520500","level":"3","name":"纳雍县","latitude":"26.808509","longitude":"105.283876"},{"code":"520526","parentCode":"520500","level":"3","name":"威宁彝族回族苗族自治县","latitude":"","longitude":""},{"code":"520527","parentCode":"520500","level":"3","name":"赫章县","latitude":"27.135471","longitude":"104.638175"},{"code":"520600","parentCode":"520000","level":"2","name":"铜仁市","latitude":"27.674903","longitude":"109.168558"},{"code":"520601","parentCode":"520600","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"520602","parentCode":"520600","level":"3","name":"碧江区","latitude":"","longitude":""},{"code":"520603","parentCode":"520600","level":"3","name":"万山区","latitude":"","longitude":""},{"code":"520621","parentCode":"520600","level":"3","name":"江口县","latitude":"27.72881","longitude":"108.778623"},{"code":"520622","parentCode":"520600","level":"3","name":"玉屏侗族自治县","latitude":"7.335871","longitude":"108.99183"},{"code":"520623","parentCode":"520600","level":"3","name":"石阡县","latitude":"27.49076","longitude":"108.169934"},{"code":"520624","parentCode":"520600","level":"3","name":"思南县","latitude":"7.848809","longitude":"108.14948"},{"code":"520625","parentCode":"520600","level":"3","name":"印江土家族苗族自治县","latitude":"27.998718","longitude":"108.532367"},{"code":"520626","parentCode":"520600","level":"3","name":"德江县","latitude":"28.264527","longitude":"108.094797"},{"code":"520627","parentCode":"520600","level":"3","name":"沿河土家族自治县","latitude":"28.624269","longitude":"108.344245"},{"code":"520628","parentCode":"520600","level":"3","name":"松桃苗族自治县","latitude":"28.129714","longitude":"109.041543"},{"code":"522300","parentCode":"520000","level":"2","name":"黔西南布依族苗族自治州","latitude":"","longitude":""},{"code":"522301","parentCode":"522300","level":"3","name":"兴义市","latitude":"25.002544","longitude":"104.857837"},{"code":"522322","parentCode":"522300","level":"3","name":"兴仁县","latitude":"5.499784","longitude":"105.27763"},{"code":"522323","parentCode":"522300","level":"3","name":"普安县","latitude":"25.727814","longitude":"105.000167"},{"code":"522324","parentCode":"522300","level":"3","name":"晴隆县","latitude":"25.84135","longitude":"105.208462"},{"code":"522325","parentCode":"522300","level":"3","name":"贞丰县","latitude":"5.421634","longitude":"105.69335"},{"code":"522326","parentCode":"522300","level":"3","name":"望谟县","latitude":"25.232322","longitude":"106.173282"},{"code":"522327","parentCode":"522300","level":"3","name":"册亨县","latitude":"24.923562","longitude":"105.838606"},{"code":"522328","parentCode":"522300","level":"3","name":"安龙县","latitude":"25.139971","longitude":"105.380224"},{"code":"522600","parentCode":"520000","level":"2","name":"黔东南苗族侗族自治州","latitude":"26.583992","longitude":"107.985353"},{"code":"522601","parentCode":"522600","level":"3","name":"凯里市","latitude":"26.631742","longitude":"107.948239"},{"code":"522622","parentCode":"522600","level":"3","name":"黄平县","latitude":"26.953082","longitude":"107.869245"},{"code":"522623","parentCode":"522600","level":"3","name":"施秉县","latitude":"27.070345","longitude":"108.154109"},{"code":"522624","parentCode":"522600","level":"3","name":"三穗县","latitude":"26.94023","longitude":"108.762217"},{"code":"522625","parentCode":"522600","level":"3","name":"镇远县","latitude":"27.12163","longitude":"108.475888"},{"code":"522626","parentCode":"522600","level":"3","name":"岑巩县","latitude":"27.365341","longitude":"108.707447"},{"code":"522627","parentCode":"522600","level":"3","name":"天柱县","latitude":"6.924696","longitude":"109.26453"},{"code":"522628","parentCode":"522600","level":"3","name":"锦屏县","latitude":"26.566755","longitude":"109.132436"},{"code":"522629","parentCode":"522600","level":"3","name":"剑河县","latitude":"26.651212","longitude":"108.694596"},{"code":"522630","parentCode":"522600","level":"3","name":"台江县","latitude":"26.656112","longitude":"108.323555"},{"code":"522631","parentCode":"522600","level":"3","name":"黎平县","latitude":"26.157514","longitude":"109.074591"},{"code":"522632","parentCode":"522600","level":"3","name":"榕江县","latitude":"26.036271","longitude":"108.419917"},{"code":"522633","parentCode":"522600","level":"3","name":"从江县","latitude":"25.718844","longitude":"108.729733"},{"code":"522634","parentCode":"522600","level":"3","name":"雷山县","latitude":"26.343206","longitude":"108.154491"},{"code":"522635","parentCode":"522600","level":"3","name":"麻江县","latitude":"26.468254","longitude":"107.618713"},{"code":"522636","parentCode":"522600","level":"3","name":"丹寨县","latitude":"26.231206","longitude":"107.931258"},{"code":"522700","parentCode":"520000","level":"2","name":"黔南布依族苗族自治州","latitude":"26.264536","longitude":"107.523205"},{"code":"522701","parentCode":"522700","level":"3","name":"都匀市","latitude":"26.153657","longitude":"107.477559"},{"code":"522702","parentCode":"522700","level":"3","name":"福泉市","latitude":"26.777119","longitude":"107.491217"},{"code":"522722","parentCode":"522700","level":"3","name":"荔波县","latitude":"25.401318","longitude":"107.930213"},{"code":"522723","parentCode":"522700","level":"3","name":"贵定县","latitude":"26.433664","longitude":"107.203904"},{"code":"522725","parentCode":"522700","level":"3","name":"瓮安县","latitude":"27.17234","longitude":"107.421412"},{"code":"522726","parentCode":"522700","level":"3","name":"独山县","latitude":"25.636841","longitude":"107.563752"},{"code":"522727","parentCode":"522700","level":"3","name":"平塘县","latitude":"25.791849","longitude":"107.113088"},{"code":"522728","parentCode":"522700","level":"3","name":"罗甸县","latitude":"25.419237","longitude":"106.709797"},{"code":"522729","parentCode":"522700","level":"3","name":"长顺县","latitude":"26.000476","longitude":"106.404198"},{"code":"522730","parentCode":"522700","level":"3","name":"龙里县","latitude":"26.506568","longitude":"106.995901"},{"code":"522731","parentCode":"522700","level":"3","name":"惠水县","latitude":"25.982997","longitude":"106.722223"},{"code":"522732","parentCode":"522700","level":"3","name":"三都水族自治县","latitude":"25.852864","longitude":"107.956506"},{"code":"530000","parentCode":"0","level":"1","name":"云南省","latitude":"24.864213","longitude":"101.592952"},{"code":"530100","parentCode":"530000","level":"2","name":"昆明市","latitude":"25.049153","longitude":"102.714601"},{"code":"530101","parentCode":"530100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"530102","parentCode":"530100","level":"3","name":"五华区","latitude":"25.261306","longitude":"102.649377"},{"code":"530103","parentCode":"530100","level":"3","name":"盘龙区","latitude":"25.274019","longitude":"102.767556"},{"code":"530111","parentCode":"530100","level":"3","name":"官渡区","latitude":"25.031311","longitude":"102.828819"},{"code":"530112","parentCode":"530100","level":"3","name":"西山区","latitude":"24.98363","longitude":"102.603478"},{"code":"530113","parentCode":"530100","level":"3","name":"东川区","latitude":"26.139329","longitude":"103.078562"},{"code":"530114","parentCode":"530100","level":"3","name":"呈贡区","latitude":"","longitude":""},{"code":"530122","parentCode":"530100","level":"3","name":"晋宁县","latitude":"24.605041","longitude":"102.579614"},{"code":"530124","parentCode":"530100","level":"3","name":"&nbsnbsp; 富民县","latitude":"","longitude":""},{"code":"530125","parentCode":"530100","level":"3","name":"宜良县","latitude":"24.944908","longitude":"103.192815"},{"code":"530126","parentCode":"530100","level":"3","name":"石林彝族自治县","latitude":"24.754309","longitude":"103.427336"},{"code":"530127","parentCode":"530100","level":"3","name":"嵩明县","latitude":"25.3179","longitude":"103.006525"},{"code":"530128","parentCode":"530100","level":"3","name":"禄劝彝族苗族自治县","latitude":"25.943771","longitude":"102.593027"},{"code":"530129","parentCode":"530100","level":"3","name":"寻甸回族彝族自治县","latitude":"25.66661","longitude":"103.127813"},{"code":"530181","parentCode":"530100","level":"3","name":"安宁市","latitude":"24.852355","longitude":"102.391127"},{"code":"530300","parentCode":"530000","level":"2","name":"曲靖市","latitude":"25.520758","longitude":"103.782539"},{"code":"530301","parentCode":"530300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"530302","parentCode":"530300","level":"3","name":"麒麟区","latitude":"25.360057","longitude":"103.913326"},{"code":"530321","parentCode":"530300","level":"3","name":"马龙县","latitude":"25.36884","longitude":"103.513095"},{"code":"530322","parentCode":"530300","level":"3","name":"陆良县","latitude":"25.037571","longitude":"103.707386"},{"code":"530323","parentCode":"530300","level":"3","name":"师宗县","latitude":"24.680198","longitude":"104.129479"},{"code":"530324","parentCode":"530300","level":"3","name":"罗平县","latitude":"24.983157","longitude":"104.349279"},{"code":"530325","parentCode":"530300","level":"3","name":"富源县","latitude":"25.467214","longitude":"104.367452"},{"code":"530326","parentCode":"530300","level":"3","name":"会泽县","latitude":"26.462218","longitude":"103.468544"},{"code":"530328","parentCode":"530300","level":"3","name":"沾益县","latitude":"25.786711","longitude":"103.846238"},{"code":"530381","parentCode":"530300","level":"3","name":"宣威市","latitude":"26.276829","longitude":"104.152571"},{"code":"530400","parentCode":"530000","level":"2","name":"玉溪市","latitude":"24.370447","longitude":"102.545068"},{"code":"530401","parentCode":"530400","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"530402","parentCode":"530400","level":"3","name":"红塔区","latitude":"24.369854","longitude":"102.499895"},{"code":"530421","parentCode":"530400","level":"3","name":"江川县","latitude":"24.357607","longitude":"102.769728"},{"code":"530422","parentCode":"530400","level":"3","name":"澄江县","latitude":"4.67838","longitude":"102.94685"},{"code":"530423","parentCode":"530400","level":"3","name":"通海县","latitude":"24.117558","longitude":"102.711416"},{"code":"530424","parentCode":"530400","level":"3","name":"华宁县","latitude":"24.284812","longitude":"102.999068"},{"code":"530425","parentCode":"530400","level":"3","name":"易门县","latitude":"24.696404","longitude":"102.122197"},{"code":"530426","parentCode":"530400","level":"3","name":"峨山彝族自治县","latitude":"4.246115","longitude":"102.21925"},{"code":"530427","parentCode":"530400","level":"3","name":"新平彝族傣族自治县","latitude":"24.029741","longitude":"101.739131"},{"code":"530428","parentCode":"530400","level":"3","name":"元江哈尼族彝族傣族自治县","latitude":"","longitude":""},{"code":"530500","parentCode":"530000","level":"2","name":"保山市","latitude":"25.120489","longitude":"99.177996"},{"code":"530501","parentCode":"530500","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"530502","parentCode":"530500","level":"3","name":"隆阳区","latitude":"5.205265","longitude":"99.069046"},{"code":"530521","parentCode":"530500","level":"3","name":"施甸县","latitude":".65722","longitude":"99.15749"},{"code":"530522","parentCode":"530500","level":"3","name":"腾冲县","latitude":"5.279978","longitude":"98.499988"},{"code":"530523","parentCode":"530500","level":"3","name":"龙陵县","latitude":"4.499046","longitude":"98.842542"},{"code":"530524","parentCode":"530500","level":"3","name":"昌宁县","latitude":"4.758163","longitude":"99.591112"},{"code":"530600","parentCode":"530000","level":"2","name":"昭通市","latitude":"27.340633","longitude":"103.725021"},{"code":"530601","parentCode":"530600","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"530602","parentCode":"530600","level":"3","name":"昭阳区","latitude":"27.427583","longitude":"103.607277"},{"code":"530621","parentCode":"530600","level":"3","name":"鲁甸县","latitude":"27.205703","longitude":"103.425856"},{"code":"530622","parentCode":"530600","level":"3","name":"巧家县","latitude":"7.008328","longitude":"103.13002"},{"code":"530623","parentCode":"530600","level":"3","name":"盐津县","latitude":"28.130707","longitude":"104.230535"},{"code":"530624","parentCode":"530600","level":"3","name":"大关县","latitude":"27.905096","longitude":"103.912178"},{"code":"530625","parentCode":"530600","level":"3","name":"永善县","latitude":"27.953163","longitude":"103.652823"},{"code":"530626","parentCode":"530600","level":"3","name":"绥江县","latitude":"28.538866","longitude":"104.015588"},{"code":"530627","parentCode":"530600","level":"3","name":"镇雄县","latitude":"27.568916","longitude":"104.833852"},{"code":"530628","parentCode":"530600","level":"3","name":"彝良县","latitude":"27.630986","longitude":"104.241449"},{"code":"530629","parentCode":"530600","level":"3","name":"威信县","latitude":"27.891463","longitude":"105.050283"},{"code":"530630","parentCode":"530600","level":"3","name":"水富县","latitude":"28.51093","longitude":"104.228833"},{"code":"530700","parentCode":"530000","level":"2","name":"丽江市","latitude":"26.875351","longitude":"100.229628"},{"code":"530701","parentCode":"530700","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"530702","parentCode":"530700","level":"3","name":"古城区","latitude":"26.8593","longitude":"100.328596"},{"code":"530721","parentCode":"530700","level":"3","name":"玉龙纳西族自治县","latitude":"7.104463","longitude":"99.951634"},{"code":"530722","parentCode":"530700","level":"3","name":"永胜县","latitude":"26.491706","longitude":"100.704921"},{"code":"530723","parentCode":"530700","level":"3","name":"华坪县","latitude":"26.645807","longitude":"101.251729"},{"code":"530724","parentCode":"530700","level":"3","name":"宁蒗彝族自治县","latitude":"27.265589","longitude":"100.778302"},{"code":"530800","parentCode":"530000","level":"2","name":"普洱市","latitude":"22.788778","longitude":"100.980058"},{"code":"530801","parentCode":"530800","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"530802","parentCode":"530800","level":"3","name":"思茅区","latitude":"22.739133","longitude":"100.855253"},{"code":"530821","parentCode":"530800","level":"3","name":"宁洱哈尼族彝族自治县","latitude":"3.09735","longitude":"101.19686"},{"code":"530822","parentCode":"530800","level":"3","name":"墨江哈尼族自治县","latitude":"23.363251","longitude":"101.555486"},{"code":"530823","parentCode":"530800","level":"3","name":"景东彝族自治县","latitude":"24.396729","longitude":"100.795206"},{"code":"530824","parentCode":"530800","level":"3","name":"景谷傣族彝族自治县","latitude":"23.368117","longitude":"100.564291"},{"code":"530825","parentCode":"530800","level":"3","name":"镇沅彝族哈尼族拉祜族自治县","latitude":"","longitude":""},{"code":"530826","parentCode":"530800","level":"3","name":"江城哈尼族彝族自治县","latitude":"22.625658","longitude":"101.796929"},{"code":"530827","parentCode":"530800","level":"3","name":"孟连傣族拉祜族佤族自治县","latitude":"","longitude":""},{"code":"530828","parentCode":"530800","level":"3","name":"澜沧拉祜族自治县","latitude":"2.665994","longitude":"99.984537"},{"code":"530829","parentCode":"530800","level":"3","name":"西盟佤族自治县","latitude":".708423","longitude":"99.52212"},{"code":"530900","parentCode":"530000","level":"2","name":"临沧市","latitude":"","longitude":""},{"code":"530901","parentCode":"530900","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"530902","parentCode":"530900","level":"3","name":"临翔区","latitude":"","longitude":""},{"code":"530921","parentCode":"530900","level":"3","name":"凤庆县","latitude":".610506","longitude":"99.92091"},{"code":"530922","parentCode":"530900","level":"3","name":"云县","latitude":"4.327406","longitude":"100.23368"},{"code":"530923","parentCode":"530900","level":"3","name":"永德县","latitude":"4.08958","longitude":"99.427632"},{"code":"530924","parentCode":"530900","level":"3","name":"镇康县","latitude":"3.901063","longitude":"99.005736"},{"code":"530925","parentCode":"530900","level":"3","name":"双江拉祜族佤族布朗族傣族自治县","latitude":"","longitude":""},{"code":"530926","parentCode":"530900","level":"3","name":"耿马傣族佤族自治县","latitude":"3.64173","longitude":"99.434265"},{"code":"530927","parentCode":"530900","level":"3","name":"沧源佤族自治县","latitude":"3.274581","longitude":"99.270498"},{"code":"532300","parentCode":"530000","level":"2","name":"楚雄彝族自治州","latitude":"25.066356","longitude":"101.529382"},{"code":"532301","parentCode":"532300","level":"3","name":"楚雄市","latitude":"24.880252","longitude":"101.328638"},{"code":"532322","parentCode":"532300","level":"3","name":"双柏县","latitude":"24.535545","longitude":"101.640322"},{"code":"532323","parentCode":"532300","level":"3","name":"牟定县","latitude":"25.407357","longitude":"101.596758"},{"code":"532324","parentCode":"532300","level":"3","name":"南华县","latitude":"25.103523","longitude":"101.038012"},{"code":"532325","parentCode":"532300","level":"3","name":"姚安县","latitude":"25.516954","longitude":"101.211238"},{"code":"532326","parentCode":"532300","level":"3","name":"大姚县","latitude":"25.94767","longitude":"101.242913"},{"code":"532327","parentCode":"532300","level":"3","name":"永仁县","latitude":"6.143679","longitude":"101.56019"},{"code":"532328","parentCode":"532300","level":"3","name":"元谋县","latitude":"25.783196","longitude":"101.870511"},{"code":"532329","parentCode":"532300","level":"3","name":"武定县","latitude":"25.73111","longitude":"102.201176"},{"code":"532331","parentCode":"532300","level":"3","name":"禄丰县","latitude":"5.185819","longitude":"102.02613"},{"code":"532500","parentCode":"530000","level":"2","name":"红河哈尼族彝族自治州","latitude":"23.367718","longitude":"103.384065"},{"code":"532501","parentCode":"532500","level":"3","name":"个旧市","latitude":"23.305692","longitude":"103.149155"},{"code":"532502","parentCode":"532500","level":"3","name":"开远市","latitude":"23.729056","longitude":"103.400155"},{"code":"532503","parentCode":"532500","level":"3","name":"蒙自市","latitude":"","longitude":""},{"code":"532504","parentCode":"532500","level":"3","name":"弥勒市","latitude":"","longitude":""},{"code":"532523","parentCode":"532500","level":"3","name":"屏边苗族自治县","latitude":"23.102937","longitude":"103.748419"},{"code":"532524","parentCode":"532500","level":"3","name":"建水县","latitude":"23.683914","longitude":"102.889674"},{"code":"532525","parentCode":"532500","level":"3","name":"石屏县","latitude":"23.746259","longitude":"102.456249"},{"code":"532527","parentCode":"532500","level":"3","name":"泸西县","latitude":"24.517287","longitude":"103.783253"},{"code":"532528","parentCode":"532500","level":"3","name":"元阳县","latitude":"23.069203","longitude":"102.819567"},{"code":"532529","parentCode":"532500","level":"3","name":"红河县","latitude":"23.250955","longitude":"102.243348"},{"code":"532530","parentCode":"532500","level":"3","name":"金平苗族瑶族傣族自治县","latitude":"","longitude":""},{"code":"532531","parentCode":"532500","level":"3","name":"绿春县","latitude":"22.879206","longitude":"102.245327"},{"code":"532532","parentCode":"532500","level":"3","name":"河口瑶族自治县","latitude":"22.787363","longitude":"103.823449"},{"code":"532600","parentCode":"530000","level":"2","name":"文山壮族苗族自治州","latitude":"23.401781","longitude":"104.089112"},{"code":"532601","parentCode":"532600","level":"3","name":"文山市","latitude":"","longitude":""},{"code":"532622","parentCode":"532600","level":"3","name":"砚山县","latitude":"23.68779","longitude":"104.255968"},{"code":"532623","parentCode":"532600","level":"3","name":"西畴县","latitude":"23.362736","longitude":"104.687854"},{"code":"532624","parentCode":"532600","level":"3","name":"麻栗坡县","latitude":"23.225356","longitude":"104.902518"},{"code":"532625","parentCode":"532600","level":"3","name":"马关县","latitude":"22.956555","longitude":"104.270312"},{"code":"532626","parentCode":"532600","level":"3","name":"丘北县","latitude":"24.127817","longitude":"104.110334"},{"code":"532627","parentCode":"532600","level":"3","name":"广南县","latitude":"3.96782","longitude":"105.00649"},{"code":"532628","parentCode":"532600","level":"3","name":"富宁县","latitude":"23.707256","longitude":"105.749608"},{"code":"532800","parentCode":"530000","level":"2","name":"西双版纳傣族自治州","latitude":"22.009433","longitude":"100.803038"},{"code":"532801","parentCode":"532800","level":"3","name":"景洪市","latitude":"22.111413","longitude":"100.927318"},{"code":"532822","parentCode":"532800","level":"3","name":"勐海县","latitude":"21.960731","longitude":"100.337382"},{"code":"532823","parentCode":"532800","level":"3","name":"勐腊县","latitude":"21.73666","longitude":"101.461959"},{"code":"532900","parentCode":"530000","level":"2","name":"大理白族自治州","latitude":"25.5969","longitude":"100.223675"},{"code":"532901","parentCode":"532900","level":"3","name":"大理市","latitude":"25.693967","longitude":"100.219209"},{"code":"532922","parentCode":"532900","level":"3","name":"漾濞彝族自治县","latitude":"5.605572","longitude":"99.898375"},{"code":"532923","parentCode":"532900","level":"3","name":"祥云县","latitude":"25.516673","longitude":"100.778071"},{"code":"532924","parentCode":"532900","level":"3","name":"宾川县","latitude":"25.875307","longitude":"100.627538"},{"code":"532925","parentCode":"532900","level":"3","name":"弥渡县","latitude":"25.191108","longitude":"100.581866"},{"code":"532926","parentCode":"532900","level":"3","name":"南涧彝族自治县","latitude":".903014","longitude":"100.4249"},{"code":"532927","parentCode":"532900","level":"3","name":"巍山彝族回族自治县","latitude":"25.246753","longitude":"100.178639"},{"code":"532928","parentCode":"532900","level":"3","name":"永平县","latitude":"5.374647","longitude":"99.600792"},{"code":"532929","parentCode":"532900","level":"3","name":"云龙县","latitude":"5.894118","longitude":"99.310078"},{"code":"532930","parentCode":"532900","level":"3","name":"洱源县","latitude":"6.072916","longitude":"99.912156"},{"code":"532931","parentCode":"532900","level":"3","name":"剑川县","latitude":"6.439596","longitude":"99.750308"},{"code":"532932","parentCode":"532900","level":"3","name":"鹤庆县","latitude":"26.335454","longitude":"100.277175"},{"code":"533100","parentCode":"530000","level":"2","name":"德宏傣族景颇族自治州","latitude":"4.44124","longitude":"98.589434"},{"code":"533102","parentCode":"533100","level":"3","name":"瑞丽市","latitude":"4.037273","longitude":"97.811532"},{"code":"533103","parentCode":"533100","level":"3","name":"芒市","latitude":"","longitude":""},{"code":"533122","parentCode":"533100","level":"3","name":"梁河县","latitude":"4.743717","longitude":"98.322123"},{"code":"533123","parentCode":"533100","level":"3","name":"盈江县","latitude":"4.862316","longitude":"97.922052"},{"code":"533124","parentCode":"533100","level":"3","name":"陇川县","latitude":"4.381371","longitude":"97.965385"},{"code":"533300","parentCode":"530000","level":"2","name":"怒江傈僳族自治州","latitude":"5.860677","longitude":"98.859932"},{"code":"533321","parentCode":"533300","level":"3","name":"泸水县","latitude":"070228","longitude":"98.8593"},{"code":"533323","parentCode":"533300","level":"3","name":"福贡县","latitude":"6.996507","longitude":"98.868659"},{"code":"533324","parentCode":"533300","level":"3","name":"贡山独龙族怒族自治县","latitude":"7.891993","longitude":"98.509416"},{"code":"533325","parentCode":"533300","level":"3","name":"兰坪白族普米族自治县","latitude":"633568","longitude":"99.2639"},{"code":"533400","parentCode":"530000","level":"2","name":"迪庆藏族自治州","latitude":"7.831029","longitude":"99.713682"},{"code":"533421","parentCode":"533400","level":"3","name":"香格里拉县","latitude":"7.907358","longitude":"99.831599"},{"code":"533422","parentCode":"533400","level":"3","name":"德钦县","latitude":"8.351417","longitude":"99.037554"},{"code":"533423","parentCode":"533400","level":"3","name":"维西傈僳族自治县","latitude":"7.452958","longitude":"99.152723"},{"code":"540000","parentCode":"0","level":"1","name":"西藏自治区","latitude":"1.367315","longitude":"89.137982"},{"code":"540100","parentCode":"540000","level":"2","name":"拉萨市","latitude":"29.662557","longitude":"91.111891"},{"code":"540101","parentCode":"540100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"540102","parentCode":"540100","level":"3","name":"城关区","latitude":".6664","longitude":"91.16873"},{"code":"540121","parentCode":"540100","level":"3","name":"林周县","latitude":"0.116478","longitude":"91.347043"},{"code":"540122","parentCode":"540100","level":"3","name":"当雄县","latitude":"0.424299","longitude":"90.894815"},{"code":"540123","parentCode":"540100","level":"3","name":"尼木县","latitude":"9.603194","longitude":"90.095471"},{"code":"540124","parentCode":"540100","level":"3","name":"曲水县","latitude":"9.445004","longitude":"90.714553"},{"code":"540125","parentCode":"540100","level":"3","name":"堆龙德庆县","latitude":"9.804706","longitude":"90.793393"},{"code":"540126","parentCode":"540100","level":"3","name":"达孜县","latitude":"9.747666","longitude":"91.473901"},{"code":"540127","parentCode":"540100","level":"3","name":"墨竹工卡县","latitude":"9.916718","longitude":"92.031892"},{"code":"542100","parentCode":"540000","level":"2","name":"昌都地区","latitude":"31.140576","longitude":"97.185582"},{"code":"542121","parentCode":"542100","level":"3","name":"昌都县","latitude":"1.44848","longitude":"97.244052"},{"code":"542122","parentCode":"542100","level":"3","name":"江达县","latitude":"1.8137","longitude":"98.036375"},{"code":"542123","parentCode":"542100","level":"3","name":"贡觉县","latitude":"0.746651","longitude":"98.496122"},{"code":"542124","parentCode":"542100","level":"3","name":"类乌齐县","latitude":"1.44057","longitude":"96.405088"},{"code":"542125","parentCode":"542100","level":"3","name":"丁青县","latitude":"1.681676","longitude":"95.311845"},{"code":"542126","parentCode":"542100","level":"3","name":"察雅县","latitude":"0.589053","longitude":"97.843886"},{"code":"542127","parentCode":"542100","level":"3","name":"八宿县","latitude":"0.162298","longitude":"96.885641"},{"code":"542128","parentCode":"542100","level":"3","name":"左贡县","latitude":"9.562561","longitude":"97.808288"},{"code":"542129","parentCode":"542100","level":"3","name":"芒康县","latitude":".620319","longitude":"98.62076"},{"code":"542132","parentCode":"542100","level":"3","name":"洛隆县","latitude":"0.735105","longitude":"95.916922"},{"code":"542133","parentCode":"542100","level":"3","name":"边坝县","latitude":"0.89183","longitude":"94.599889"},{"code":"542200","parentCode":"540000","level":"2","name":"山南地区","latitude":"29.229027","longitude":"91.750644"},{"code":"542221","parentCode":"542200","level":"3","name":"乃东县","latitude":".213116","longitude":"91.83204"},{"code":"542222","parentCode":"542200","level":"3","name":"扎囊县","latitude":"9.280298","longitude":"91.408245"},{"code":"542223","parentCode":"542200","level":"3","name":"贡嘎县","latitude":".213067","longitude":"90.94437"},{"code":"542224","parentCode":"542200","level":"3","name":"桑日县","latitude":"9.407083","longitude":"92.230603"},{"code":"542225","parentCode":"542200","level":"3","name":"琼结县","latitude":".011722","longitude":"91.61377"},{"code":"542226","parentCode":"542200","level":"3","name":"曲松县","latitude":"8.950715","longitude":"92.210064"},{"code":"542227","parentCode":"542200","level":"3","name":"措美县","latitude":"8.576671","longitude":"91.515318"},{"code":"542228","parentCode":"542200","level":"3","name":"洛扎县","latitude":"8.216916","longitude":"90.873599"},{"code":"542229","parentCode":"542200","level":"3","name":"加查县","latitude":"9.293908","longitude":"92.719872"},{"code":"542231","parentCode":"542200","level":"3","name":"隆子县","latitude":"8.481868","longitude":"92.926094"},{"code":"542232","parentCode":"542200","level":"3","name":"错那县","latitude":"7.67283","longitude":"92.900839"},{"code":"542233","parentCode":"542200","level":"3","name":"浪卡子县","latitude":"8.761273","longitude":"90.638268"},{"code":"542300","parentCode":"540000","level":"2","name":"日喀则地区","latitude":"29.269023","longitude":"88.891486"},{"code":"542301","parentCode":"542300","level":"3","name":"日喀则市","latitude":"9.26816","longitude":"88.956063"},{"code":"542322","parentCode":"542300","level":"3","name":"南木林县","latitude":"9.814826","longitude":"89.380043"},{"code":"542323","parentCode":"542300","level":"3","name":"江孜县","latitude":".91068","longitude":"89.66905"},{"code":"542324","parentCode":"542300","level":"3","name":"定日县","latitude":"8.477712","longitude":"86.999961"},{"code":"542325","parentCode":"542300","level":"3","name":"萨迦县","latitude":".854891","longitude":"88.34757"},{"code":"542326","parentCode":"542300","level":"3","name":"拉孜县","latitude":"9.179935","longitude":"87.858421"},{"code":"542327","parentCode":"542300","level":"3","name":"昂仁县","latitude":"9.956055","longitude":"86.629188"},{"code":"542328","parentCode":"542300","level":"3","name":"谢通门县","latitude":"9.914903","longitude":"88.082372"},{"code":"542329","parentCode":"542300","level":"3","name":"白朗县","latitude":"8.820024","longitude":"89.104404"},{"code":"542330","parentCode":"542300","level":"3","name":"仁布县","latitude":"9.224222","longitude":"89.976281"},{"code":"542331","parentCode":"542300","level":"3","name":"康马县","latitude":"8.443364","longitude":"89.655312"},{"code":"542332","parentCode":"542300","level":"3","name":"定结县","latitude":"8.23751","longitude":"87.913745"},{"code":"542333","parentCode":"542300","level":"3","name":"仲巴县","latitude":"0.484477","longitude":"83.662595"},{"code":"542334","parentCode":"542300","level":"3","name":"亚东县","latitude":"7.815088","longitude":"89.081382"},{"code":"542335","parentCode":"542300","level":"3","name":"吉隆县","latitude":"8.8668","longitude":"85.306389"},{"code":"542336","parentCode":"542300","level":"3","name":"聂拉木县","latitude":"8.599824","longitude":"86.067461"},{"code":"542337","parentCode":"542300","level":"3","name":"萨嘎县","latitude":"9.402132","longitude":"85.083569"},{"code":"542338","parentCode":"542300","level":"3","name":"岗巴县","latitude":"8.316706","longitude":"88.571885"},{"code":"542400","parentCode":"540000","level":"2","name":"那曲地区","latitude":"31.48068","longitude":"92.067018"},{"code":"542421","parentCode":"542400","level":"3","name":"那曲县","latitude":"1.252315","longitude":"92.034626"},{"code":"542422","parentCode":"542400","level":"3","name":"嘉黎县","latitude":"0.668912","longitude":"92.961316"},{"code":"542423","parentCode":"542400","level":"3","name":"比如县","latitude":"1.447136","longitude":"93.493424"},{"code":"542424","parentCode":"542400","level":"3","name":"聂荣县","latitude":"2.24965","longitude":"92.642153"},{"code":"542425","parentCode":"542400","level":"3","name":"安多县","latitude":"3.321682","longitude":"90.569314"},{"code":"542426","parentCode":"542400","level":"3","name":"申扎县","latitude":"1.035234","longitude":"88.735362"},{"code":"542427","parentCode":"542400","level":"3","name":"索县","latitude":".592788","longitude":"94.31255"},{"code":"542428","parentCode":"542400","level":"3","name":"班戈县","latitude":"1.218112","longitude":"90.123401"},{"code":"542429","parentCode":"542400","level":"3","name":"巴青县","latitude":"2.198839","longitude":"94.018949"},{"code":"542430","parentCode":"542400","level":"3","name":"尼玛县","latitude":"3.536966","longitude":"87.654847"},{"code":"542431","parentCode":"542400","level":"3","name":"双湖县","latitude":"","longitude":""},{"code":"542500","parentCode":"540000","level":"2","name":"阿里地区","latitude":"30.404557","longitude":"81.107669"},{"code":"542521","parentCode":"542500","level":"3","name":"普兰县","latitude":"0.63712","longitude":"81.530583"},{"code":"542522","parentCode":"542500","level":"3","name":"札达县","latitude":"1.553649","longitude":"79.552757"},{"code":"542523","parentCode":"542500","level":"3","name":"噶尔县","latitude":"2.005501","longitude":"80.315974"},{"code":"542524","parentCode":"542500","level":"3","name":"日土县","latitude":"3.984683","longitude":"80.719742"},{"code":"542525","parentCode":"542500","level":"3","name":"革吉县","latitude":"2.057883","longitude":"82.033798"},{"code":"542526","parentCode":"542500","level":"3","name":"改则县","latitude":"3.841205","longitude":"84.285002"},{"code":"542527","parentCode":"542500","level":"3","name":"措勤县","latitude":"0.749851","longitude":"85.210286"},{"code":"542600","parentCode":"540000","level":"2","name":"林芝地区","latitude":"29.666941","longitude":"94.349985"},{"code":"542621","parentCode":"542600","level":"3","name":"林芝县","latitude":"9.783256","longitude":"94.380416"},{"code":"542622","parentCode":"542600","level":"3","name":"工布江达县","latitude":"0.025216","longitude":"93.242098"},{"code":"542623","parentCode":"542600","level":"3","name":"米林县","latitude":"9.24841","longitude":"94.018213"},{"code":"542624","parentCode":"542600","level":"3","name":"墨脱县","latitude":"8.732211","longitude":"95.017836"},{"code":"542625","parentCode":"542600","level":"3","name":"波密县","latitude":"0.124828","longitude":"95.556585"},{"code":"542626","parentCode":"542600","level":"3","name":"察隅县","latitude":"8.681698","longitude":"97.069124"},{"code":"542627","parentCode":"542600","level":"3","name":"朗县","latitude":"8.96475","longitude":"93.106766"},{"code":"610000","parentCode":"0","level":"1","name":"陕西省","latitude":"35.860026","longitude":"109.503789"},{"code":"610100","parentCode":"610000","level":"2","name":"西安市","latitude":"34.2778","longitude":"108.953098"},{"code":"610101","parentCode":"610100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"610102","parentCode":"610100","level":"3","name":"新城区","latitude":"34.271474","longitude":"108.991539"},{"code":"610103","parentCode":"610100","level":"3","name":"碑林区","latitude":"34.255485","longitude":"108.966259"},{"code":"610104","parentCode":"610100","level":"3","name":"莲湖区","latitude":"34.273192","longitude":"108.915547"},{"code":"610111","parentCode":"610100","level":"3","name":"灞桥区","latitude":"34.303915","longitude":"109.108755"},{"code":"610112","parentCode":"610100","level":"3","name":"未央区","latitude":"34.331331","longitude":"108.926462"},{"code":"610113","parentCode":"610100","level":"3","name":"雁塔区","latitude":"4.221415","longitude":"108.93879"},{"code":"610114","parentCode":"610100","level":"3","name":"阎良区","latitude":"34.686373","longitude":"109.313417"},{"code":"610115","parentCode":"610100","level":"3","name":"临潼区","latitude":"34.456277","longitude":"109.310453"},{"code":"610116","parentCode":"610100","level":"3","name":"长安区","latitude":"38.076875","longitude":"114.592622"},{"code":"610122","parentCode":"610100","level":"3","name":"蓝田县","latitude":"4.100787","longitude":"109.42339"},{"code":"610124","parentCode":"610100","level":"3","name":"周至县","latitude":"33.953602","longitude":"108.113541"},{"code":"610125","parentCode":"610100","level":"3","name":"户县","latitude":"34.003834","longitude":"108.592481"},{"code":"610126","parentCode":"610100","level":"3","name":"高陵县","latitude":"34.513762","longitude":"109.073775"},{"code":"610200","parentCode":"610000","level":"2","name":"铜川市","latitude":"34.908368","longitude":"108.968067"},{"code":"610201","parentCode":"610200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"610202","parentCode":"610200","level":"3","name":"王益区","latitude":"35.070041","longitude":"109.068504"},{"code":"610203","parentCode":"610200","level":"3","name":"印台区","latitude":"35.160934","longitude":"109.185386"},{"code":"610204","parentCode":"610200","level":"3","name":"耀州区","latitude":".032","longitude":"108.8355"},{"code":"610222","parentCode":"610200","level":"3","name":"宜君县","latitude":"35.383902","longitude":"109.204402"},{"code":"610300","parentCode":"610000","level":"2","name":"宝鸡市","latitude":"34.364081","longitude":"107.170645"},{"code":"610301","parentCode":"610300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"610302","parentCode":"610300","level":"3","name":"渭滨区","latitude":"34.311027","longitude":"107.108244"},{"code":"610303","parentCode":"610300","level":"3","name":"金台区","latitude":"34.403175","longitude":"107.117614"},{"code":"610304","parentCode":"610300","level":"3","name":"陈仓区","latitude":"","longitude":""},{"code":"610322","parentCode":"610300","level":"3","name":"凤翔县","latitude":"34.577026","longitude":"107.436789"},{"code":"610323","parentCode":"610300","level":"3","name":"岐山县","latitude":"34.410705","longitude":"107.688985"},{"code":"610324","parentCode":"610300","level":"3","name":"扶风县","latitude":"34.411974","longitude":"107.925102"},{"code":"610326","parentCode":"610300","level":"3","name":"眉县","latitude":"34.15054","longitude":"107.833844"},{"code":"610327","parentCode":"610300","level":"3","name":"陇县","latitude":"34.876941","longitude":"106.773064"},{"code":"610328","parentCode":"610300","level":"3","name":"千阳县","latitude":"34.766951","longitude":"107.177974"},{"code":"610329","parentCode":"610300","level":"3","name":"麟游县","latitude":"34.785693","longitude":"107.710775"},{"code":"610330","parentCode":"610300","level":"3","name":"凤县","latitude":"33.993252","longitude":"106.766104"},{"code":"610331","parentCode":"610300","level":"3","name":"太白县","latitude":"33.942972","longitude":"107.416865"},{"code":"610400","parentCode":"610000","level":"2","name":"咸阳市","latitude":"34.345373","longitude":"108.707509"},{"code":"610401","parentCode":"610400","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"610402","parentCode":"610400","level":"3","name":"秦都区","latitude":"34.354285","longitude":"108.683415"},{"code":"610403","parentCode":"610400","level":"3","name":"杨陵区","latitude":"","longitude":""},{"code":"610404","parentCode":"610400","level":"3","name":"渭城区","latitude":"34.423853","longitude":"108.817312"},{"code":"610422","parentCode":"610400","level":"3","name":"三原县","latitude":".703212","longitude":"108.9807"},{"code":"610423","parentCode":"610400","level":"3","name":"泾阳县","latitude":"34.608867","longitude":"108.780753"},{"code":"610424","parentCode":"610400","level":"3","name":"乾县","latitude":"34.527673","longitude":"108.229483"},{"code":"610425","parentCode":"610400","level":"3","name":"礼泉县","latitude":"34.597854","longitude":"108.482569"},{"code":"610426","parentCode":"610400","level":"3","name":"永寿县","latitude":"34.777656","longitude":"108.136714"},{"code":"610427","parentCode":"610400","level":"3","name":"彬县","latitude":"35.051835","longitude":"108.067986"},{"code":"610428","parentCode":"610400","level":"3","name":"长武县","latitude":".170582","longitude":"107.8348"},{"code":"610429","parentCode":"610400","level":"3","name":"旬邑县","latitude":"35.216832","longitude":"108.494125"},{"code":"610430","parentCode":"610400","level":"3","name":"淳化县","latitude":"34.869116","longitude":"108.570219"},{"code":"610431","parentCode":"610400","level":"3","name":"武功县","latitude":"34.316553","longitude":"108.190993"},{"code":"610481","parentCode":"610400","level":"3","name":"兴平市","latitude":"4.307609","longitude":"108.47576"},{"code":"610500","parentCode":"610000","level":"2","name":"渭南市","latitude":"34.502358","longitude":"109.483933"},{"code":"610501","parentCode":"610500","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"610502","parentCode":"610500","level":"3","name":"临渭区","latitude":"34.55352","longitude":"109.564746"},{"code":"610521","parentCode":"610500","level":"3","name":"华县","latitude":"34.411964","longitude":"109.812703"},{"code":"610522","parentCode":"610500","level":"3","name":"潼关县","latitude":"34.507137","longitude":"110.295546"},{"code":"610523","parentCode":"610500","level":"3","name":"大荔县","latitude":"4.79684","longitude":"110.01195"},{"code":"610524","parentCode":"610500","level":"3","name":"合阳县","latitude":"35.208388","longitude":"110.191104"},{"code":"610525","parentCode":"610500","level":"3","name":"澄城县","latitude":"35.222564","longitude":"109.901605"},{"code":"610526","parentCode":"610500","level":"3","name":"蒲城县","latitude":"34.967697","longitude":"109.628246"},{"code":"610527","parentCode":"610500","level":"3","name":"白水县","latitude":"35.271646","longitude":"109.570166"},{"code":"610528","parentCode":"610500","level":"3","name":"富平县","latitude":"4.879424","longitude":"109.23594"},{"code":"610581","parentCode":"610500","level":"3","name":"韩城市","latitude":"35.582782","longitude":"110.393774"},{"code":"610582","parentCode":"610500","level":"3","name":"华阴市","latitude":"34.532718","longitude":"110.058188"},{"code":"610600","parentCode":"610000","level":"2","name":"延安市","latitude":"36.60332","longitude":"109.50051"},{"code":"610601","parentCode":"610600","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"610602","parentCode":"610600","level":"3","name":"宝塔区","latitude":"36.575992","longitude":"109.648602"},{"code":"610621","parentCode":"610600","level":"3","name":"延长县","latitude":"36.543669","longitude":"110.138204"},{"code":"610622","parentCode":"610600","level":"3","name":"延川县","latitude":"36.882427","longitude":"110.084097"},{"code":"610623","parentCode":"610600","level":"3","name":"子长县","latitude":"7.231002","longitude":"109.62229"},{"code":"610624","parentCode":"610600","level":"3","name":"安塞县","latitude":"36.92692","longitude":"109.170681"},{"code":"610625","parentCode":"610600","level":"3","name":"志丹县","latitude":"36.753503","longitude":"108.662447"},{"code":"610626","parentCode":"610600","level":"3","name":"吴起县","latitude":"","longitude":""},{"code":"610627","parentCode":"610600","level":"3","name":"甘泉县","latitude":"36.353544","longitude":"109.182239"},{"code":"610628","parentCode":"610600","level":"3","name":"富县","latitude":"36.017427","longitude":"109.049604"},{"code":"610629","parentCode":"610600","level":"3","name":"洛川县","latitude":"35.744158","longitude":"109.560982"},{"code":"610630","parentCode":"610600","level":"3","name":"宜川县","latitude":"36.071139","longitude":"110.191127"},{"code":"610631","parentCode":"610600","level":"3","name":"黄龙县","latitude":"35.702636","longitude":"109.945101"},{"code":"610632","parentCode":"610600","level":"3","name":"黄陵县","latitude":"35.628414","longitude":"108.953058"},{"code":"610700","parentCode":"610000","level":"2","name":"汉中市","latitude":"33.081569","longitude":"107.045478"},{"code":"610701","parentCode":"610700","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"610702","parentCode":"610700","level":"3","name":"汉台区","latitude":"33.187204","longitude":"107.046167"},{"code":"610721","parentCode":"610700","level":"3","name":"南郑县","latitude":"32.812036","longitude":"106.969741"},{"code":"610722","parentCode":"610700","level":"3","name":"城固县","latitude":"33.223583","longitude":"107.260837"},{"code":"610723","parentCode":"610700","level":"3","name":"洋县","latitude":"33.371587","longitude":"107.616093"},{"code":"610724","parentCode":"610700","level":"3","name":"西乡县","latitude":"32.894903","longitude":"107.753712"},{"code":"610725","parentCode":"610700","level":"3","name":"勉县","latitude":"33.243886","longitude":"106.664578"},{"code":"610726","parentCode":"610700","level":"3","name":"宁强县","latitude":"32.914183","longitude":"106.140871"},{"code":"610727","parentCode":"610700","level":"3","name":"略阳县","latitude":"33.385374","longitude":"106.162834"},{"code":"610728","parentCode":"610700","level":"3","name":"镇巴县","latitude":"32.517416","longitude":"107.882774"},{"code":"610729","parentCode":"610700","level":"3","name":"留坝县","latitude":"33.61296","longitude":"106.959628"},{"code":"610730","parentCode":"610700","level":"3","name":"佛坪县","latitude":"33.549939","longitude":"107.928836"},{"code":"610800","parentCode":"610000","level":"2","name":"榆林市","latitude":"38.279439","longitude":"109.745926"},{"code":"610801","parentCode":"610800","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"610802","parentCode":"610800","level":"3","name":"榆阳区","latitude":"38.386407","longitude":"109.642692"},{"code":"610821","parentCode":"610800","level":"3","name":"神木县","latitude":"8.829036","longitude":"110.33127"},{"code":"610822","parentCode":"610800","level":"3","name":"府谷县","latitude":"39.187272","longitude":"110.866934"},{"code":"610823","parentCode":"610800","level":"3","name":"横山县","latitude":"37.851606","longitude":"109.466991"},{"code":"610824","parentCode":"610800","level":"3","name":"靖边县","latitude":"37.484216","longitude":"108.813259"},{"code":"610825","parentCode":"610800","level":"3","name":"定边县","latitude":"37.388791","longitude":"107.754293"},{"code":"610826","parentCode":"610800","level":"3","name":"绥德县","latitude":"37.520861","longitude":"110.396144"},{"code":"610827","parentCode":"610800","level":"3","name":"米脂县","latitude":"37.828529","longitude":"110.186901"},{"code":"610828","parentCode":"610800","level":"3","name":"佳县","latitude":"8.07838","longitude":"110.37374"},{"code":"610829","parentCode":"610800","level":"3","name":"吴堡县","latitude":"37.594879","longitude":"110.691877"},{"code":"610830","parentCode":"610800","level":"3","name":"清涧县","latitude":"37.187444","longitude":"110.289294"},{"code":"610831","parentCode":"610800","level":"3","name":"子洲县","latitude":"37.533672","longitude":"109.877293"},{"code":"610900","parentCode":"610000","level":"2","name":"安康市","latitude":"32.70437","longitude":"109.038045"},{"code":"610901","parentCode":"610900","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"610902","parentCode":"610900","level":"3","name":"汉滨区","latitude":"32.814464","longitude":"108.896243"},{"code":"610921","parentCode":"610900","level":"3","name":"汉阴县","latitude":"32.902521","longitude":"108.496955"},{"code":"610922","parentCode":"610900","level":"3","name":"石泉县","latitude":"33.065316","longitude":"108.250518"},{"code":"610923","parentCode":"610900","level":"3","name":"宁陕县","latitude":"33.536924","longitude":"108.451796"},{"code":"610924","parentCode":"610900","level":"3","name":"紫阳县","latitude":"32.448942","longitude":"108.444826"},{"code":"610925","parentCode":"610900","level":"3","name":"岚皋县","latitude":"32.24247","longitude":"108.887181"},{"code":"610926","parentCode":"610900","level":"3","name":"平利县","latitude":"32.291257","longitude":"109.270397"},{"code":"610927","parentCode":"610900","level":"3","name":"镇坪县","latitude":"31.939262","longitude":"109.456711"},{"code":"610928","parentCode":"610900","level":"3","name":"旬阳县","latitude":"2.902077","longitude":"109.42358"},{"code":"610929","parentCode":"610900","level":"3","name":"白河县","latitude":"32.729865","longitude":"109.918375"},{"code":"611000","parentCode":"610000","level":"2","name":"商洛市","latitude":"33.873907","longitude":"109.934208"},{"code":"611001","parentCode":"611000","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"611002","parentCode":"611000","level":"3","name":"商州区","latitude":"33.895485","longitude":"109.873271"},{"code":"611021","parentCode":"611000","level":"3","name":"洛南县","latitude":"34.165684","longitude":"110.272643"},{"code":"611022","parentCode":"611000","level":"3","name":"丹凤县","latitude":".684554","longitude":"110.4438"},{"code":"611023","parentCode":"611000","level":"3","name":"商南县","latitude":"33.411703","longitude":"110.766533"},{"code":"611024","parentCode":"611000","level":"3","name":"山阳县","latitude":"33.427684","longitude":"109.981319"},{"code":"611025","parentCode":"611000","level":"3","name":"镇安县","latitude":"33.380939","longitude":"109.077371"},{"code":"611026","parentCode":"611000","level":"3","name":"柞水县","latitude":"33.6954","longitude":"109.280549"},{"code":"620000","parentCode":"0","level":"1","name":"甘肃省","latitude":"38.103267","longitude":"102.457625"},{"code":"620100","parentCode":"620000","level":"2","name":"兰州市","latitude":"36.064226","longitude":"103.823305"},{"code":"620101","parentCode":"620100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"620102","parentCode":"620100","level":"3","name":"城关区","latitude":".6664","longitude":"91.16873"},{"code":"620103","parentCode":"620100","level":"3","name":"七里河区","latitude":"35.992495","longitude":"103.771994"},{"code":"620104","parentCode":"620100","level":"3","name":"西固区","latitude":"6.106472","longitude":"103.56268"},{"code":"620105","parentCode":"620100","level":"3","name":"安宁区","latitude":"36.115523","longitude":"103.719156"},{"code":"620111","parentCode":"620100","level":"3","name":"红古区","latitude":"36.303488","longitude":"103.060275"},{"code":"620121","parentCode":"620100","level":"3","name":"永登县","latitude":"36.616924","longitude":"103.252794"},{"code":"620122","parentCode":"620100","level":"3","name":"皋兰县","latitude":"36.394688","longitude":"103.890467"},{"code":"620123","parentCode":"620100","level":"3","name":"榆中县","latitude":"5.999785","longitude":"104.24429"},{"code":"620200","parentCode":"620000","level":"2","name":"嘉峪关市","latitude":"39.802397","longitude":"98.281635"},{"code":"620201","parentCode":"620200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"620300","parentCode":"620000","level":"2","name":"金昌市","latitude":"38.516072","longitude":"102.208126"},{"code":"620301","parentCode":"620300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"620302","parentCode":"620300","level":"3","name":"金川区","latitude":"8.492172","longitude":"102.32868"},{"code":"620321","parentCode":"620300","level":"3","name":"永昌县","latitude":"38.43341","longitude":"102.034316"},{"code":"620400","parentCode":"620000","level":"2","name":"白银市","latitude":"36.546682","longitude":"104.171241"},{"code":"620401","parentCode":"620400","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"620402","parentCode":"620400","level":"3","name":"白银区","latitude":"36.501822","longitude":"104.205649"},{"code":"620403","parentCode":"620400","level":"3","name":"平川区","latitude":"36.69035","longitude":"104.945609"},{"code":"620421","parentCode":"620400","level":"3","name":"靖远县","latitude":"36.749103","longitude":"104.732327"},{"code":"620422","parentCode":"620400","level":"3","name":"会宁县","latitude":"35.963482","longitude":"105.101861"},{"code":"620423","parentCode":"620400","level":"3","name":"景泰县","latitude":"37.146079","longitude":"104.061668"},{"code":"620500","parentCode":"620000","level":"2","name":"天水市","latitude":"34.584319","longitude":"105.736932"},{"code":"620501","parentCode":"620500","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"620502","parentCode":"620500","level":"3","name":"秦州区","latitude":"","longitude":""},{"code":"620503","parentCode":"620500","level":"3","name":"麦积区","latitude":"","longitude":""},{"code":"620521","parentCode":"620500","level":"3","name":"清水县","latitude":"4.742527","longitude":"106.14008"},{"code":"620522","parentCode":"620500","level":"3","name":"秦安县","latitude":"34.9535","longitude":"105.698041"},{"code":"620523","parentCode":"620500","level":"3","name":"甘谷县","latitude":"34.809421","longitude":"105.274532"},{"code":"620524","parentCode":"620500","level":"3","name":"武山县","latitude":"4.680182","longitude":"104.88673"},{"code":"620525","parentCode":"620500","level":"3","name":"张家川回族自治县","latitude":"34.995955","longitude":"106.282137"},{"code":"620600","parentCode":"620000","level":"2","name":"武威市","latitude":"37.933172","longitude":"102.640147"},{"code":"620601","parentCode":"620600","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"620602","parentCode":"620600","level":"3","name":"凉州区","latitude":"37.916272","longitude":"102.759477"},{"code":"620621","parentCode":"620600","level":"3","name":"民勤县","latitude":"38.827728","longitude":"103.202473"},{"code":"620622","parentCode":"620600","level":"3","name":"古浪县","latitude":"37.531272","longitude":"103.342923"},{"code":"620623","parentCode":"620600","level":"3","name":"天祝藏族自治县","latitude":"37.280912","longitude":"102.761164"},{"code":"620700","parentCode":"620000","level":"2","name":"张掖市","latitude":"38.93932","longitude":"100.459892"},{"code":"620701","parentCode":"620700","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"620702","parentCode":"620700","level":"3","name":"甘州区","latitude":"39.010621","longitude":"100.522079"},{"code":"620721","parentCode":"620700","level":"3","name":"肃南裕固族自治县","latitude":"8.920571","longitude":"99.326772"},{"code":"620722","parentCode":"620700","level":"3","name":"民乐县","latitude":"38.473163","longitude":"100.798429"},{"code":"620723","parentCode":"620700","level":"3","name":"临泽县","latitude":"39.347032","longitude":"100.191224"},{"code":"620724","parentCode":"620700","level":"3","name":"高台县","latitude":"9.541675","longitude":"99.607521"},{"code":"620725","parentCode":"620700","level":"3","name":"山丹县","latitude":"38.530221","longitude":"101.231647"},{"code":"620800","parentCode":"620000","level":"2","name":"平凉市","latitude":"35.55011","longitude":"106.688911"},{"code":"620801","parentCode":"620800","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"620802","parentCode":"620800","level":"3","name":"崆峒区","latitude":"35.515774","longitude":"106.748887"},{"code":"620821","parentCode":"620800","level":"3","name":"泾川县","latitude":"35.354115","longitude":"107.441405"},{"code":"620822","parentCode":"620800","level":"3","name":"灵台县","latitude":"35.074478","longitude":"107.409606"},{"code":"620823","parentCode":"620800","level":"3","name":"崇信县","latitude":"35.249103","longitude":"107.003776"},{"code":"620824","parentCode":"620800","level":"3","name":"华亭县","latitude":"5.20555","longitude":"106.60867"},{"code":"620825","parentCode":"620800","level":"3","name":"庄浪县","latitude":"35.255968","longitude":"106.065686"},{"code":"620826","parentCode":"620800","level":"3","name":"静宁县","latitude":"35.434012","longitude":"105.677562"},{"code":"620900","parentCode":"620000","level":"2","name":"酒泉市","latitude":"39.741474","longitude":"98.508415"},{"code":"620901","parentCode":"620900","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"620902","parentCode":"620900","level":"3","name":"肃州区","latitude":"9.598374","longitude":"98.802616"},{"code":"620921","parentCode":"620900","level":"3","name":"金塔县","latitude":"0.382579","longitude":"99.186587"},{"code":"620922","parentCode":"620900","level":"3","name":"瓜州县","latitude":"","longitude":""},{"code":"620923","parentCode":"620900","level":"3","name":"肃北蒙古族自治县","latitude":"0.67652","longitude":"96.532551"},{"code":"620924","parentCode":"620900","level":"3","name":"阿克塞哈萨克族自治县","latitude":"9.02589","longitude":"94.452301"},{"code":"620981","parentCode":"620900","level":"3","name":"玉门市","latitude":"0.225552","longitude":"97.461209"},{"code":"620982","parentCode":"620900","level":"3","name":"敦煌市","latitude":"0.388771","longitude":"94.158042"},{"code":"621000","parentCode":"620000","level":"2","name":"庆阳市","latitude":"35.726801","longitude":"107.644227"},{"code":"621001","parentCode":"621000","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"621002","parentCode":"621000","level":"3","name":"西峰区","latitude":"35.677201","longitude":"107.673674"},{"code":"621021","parentCode":"621000","level":"3","name":"庆城县","latitude":"36.046138","longitude":"107.682548"},{"code":"621022","parentCode":"621000","level":"3","name":"环县","latitude":"36.616789","longitude":"107.072172"},{"code":"621023","parentCode":"621000","level":"3","name":"华池县","latitude":"36.444472","longitude":"108.034312"},{"code":"621024","parentCode":"621000","level":"3","name":"合水县","latitude":"36.01426","longitude":"108.317341"},{"code":"621025","parentCode":"621000","level":"3","name":"正宁县","latitude":"35.414732","longitude":"108.378087"},{"code":"621026","parentCode":"621000","level":"3","name":"宁县","latitude":"35.571366","longitude":"108.114173"},{"code":"621027","parentCode":"621000","level":"3","name":"镇原县","latitude":"35.787954","longitude":"107.177227"},{"code":"621100","parentCode":"620000","level":"2","name":"定西市","latitude":"","longitude":""},{"code":"621101","parentCode":"621100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"621102","parentCode":"621100","level":"3","name":"安定区","latitude":"","longitude":""},{"code":"621121","parentCode":"621100","level":"3","name":"通渭县","latitude":"35.213474","longitude":"105.193978"},{"code":"621122","parentCode":"621100","level":"3","name":"陇西县","latitude":"35.111802","longitude":"104.632913"},{"code":"621123","parentCode":"621100","level":"3","name":"渭源县","latitude":"35.139481","longitude":"104.146328"},{"code":"621124","parentCode":"621100","level":"3","name":"临洮县","latitude":"35.531079","longitude":"103.912015"},{"code":"621125","parentCode":"621100","level":"3","name":"漳县","latitude":"34.726751","longitude":"104.365403"},{"code":"621126","parentCode":"621100","level":"3","name":"岷县","latitude":"34.429644","longitude":"104.246726"},{"code":"621200","parentCode":"620000","level":"2","name":"陇南市","latitude":"","longitude":""},{"code":"621201","parentCode":"621200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"621202","parentCode":"621200","level":"3","name":"武都区","latitude":"","longitude":""},{"code":"621221","parentCode":"621200","level":"3","name":"成县","latitude":"33.747297","longitude":"105.688289"},{"code":"621222","parentCode":"621200","level":"3","name":"文县","latitude":"32.947265","longitude":"104.784206"},{"code":"621223","parentCode":"621200","level":"3","name":"宕昌县","latitude":"34.013489","longitude":"104.452827"},{"code":"621224","parentCode":"621200","level":"3","name":"康县","latitude":"33.28499","longitude":"105.637974"},{"code":"621225","parentCode":"621200","level":"3","name":"西和县","latitude":"33.919625","longitude":"105.338531"},{"code":"621226","parentCode":"621200","level":"3","name":"礼县","latitude":"34.111637","longitude":"105.064091"},{"code":"621227","parentCode":"621200","level":"3","name":"徽县","latitude":"33.892851","longitude":"106.033317"},{"code":"621228","parentCode":"621200","level":"3","name":"两当县","latitude":"33.911379","longitude":"106.403885"},{"code":"622900","parentCode":"620000","level":"2","name":"临夏回族自治州","latitude":"35.598514","longitude":"103.215249"},{"code":"622901","parentCode":"622900","level":"3","name":"临夏市","latitude":"35.585835","longitude":"103.200576"},{"code":"622921","parentCode":"622900","level":"3","name":"临夏县","latitude":"35.518719","longitude":"103.050791"},{"code":"622922","parentCode":"622900","level":"3","name":"康乐县","latitude":"5.258018","longitude":"103.62902"},{"code":"622923","parentCode":"622900","level":"3","name":"永靖县","latitude":"36.007875","longitude":"103.225044"},{"code":"622924","parentCode":"622900","level":"3","name":"广河县","latitude":"5.478027","longitude":"103.63114"},{"code":"622925","parentCode":"622900","level":"3","name":"和政县","latitude":"35.345732","longitude":"103.298568"},{"code":"622926","parentCode":"622900","level":"3","name":"东乡族自治县","latitude":"35.698472","longitude":"103.452145"},{"code":"622927","parentCode":"622900","level":"3","name":"积石山保安族东乡族撒拉族自治县","latitude":"","longitude":""},{"code":"623000","parentCode":"620000","level":"2","name":"甘南藏族自治州","latitude":"34.992211","longitude":"102.917442"},{"code":"623001","parentCode":"623000","level":"3","name":"合作市","latitude":"34.99726","longitude":"103.085649"},{"code":"623021","parentCode":"623000","level":"3","name":"临潭县","latitude":"34.742615","longitude":"103.631906"},{"code":"623022","parentCode":"623000","level":"3","name":"卓尼县","latitude":"4.614458","longitude":"103.39362"},{"code":"623023","parentCode":"623000","level":"3","name":"舟曲县","latitude":"33.63481","longitude":"104.326323"},{"code":"623024","parentCode":"623000","level":"3","name":"迭部县","latitude":"34.005621","longitude":"103.570446"},{"code":"623025","parentCode":"623000","level":"3","name":"玛曲县","latitude":"33.850722","longitude":"101.668977"},{"code":"623026","parentCode":"623000","level":"3","name":"碌曲县","latitude":"34.392609","longitude":"102.477547"},{"code":"623027","parentCode":"623000","level":"3","name":"夏河县","latitude":"35.023031","longitude":"102.506578"},{"code":"630000","parentCode":"0","level":"1","name":"青海省","latitude":"5.499761","longitude":"96.202544"},{"code":"630100","parentCode":"630000","level":"2","name":"西宁市","latitude":"36.640739","longitude":"101.767921"},{"code":"630101","parentCode":"630100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"630102","parentCode":"630100","level":"3","name":"城东区","latitude":"36.602117","longitude":"101.831865"},{"code":"630103","parentCode":"630100","level":"3","name":"城中区","latitude":"36.606649","longitude":"101.777361"},{"code":"630104","parentCode":"630100","level":"3","name":"城西区","latitude":"36.631636","longitude":"101.727603"},{"code":"630105","parentCode":"630100","level":"3","name":"城北区","latitude":"36.686368","longitude":"101.712664"},{"code":"630121","parentCode":"630100","level":"3","name":"大通回族土族自治县","latitude":"37.120688","longitude":"101.490478"},{"code":"630122","parentCode":"630100","level":"3","name":"湟中县","latitude":"36.579759","longitude":"101.544494"},{"code":"630123","parentCode":"630100","level":"3","name":"湟源县","latitude":"36.636355","longitude":"101.163178"},{"code":"630200","parentCode":"630000","level":"2","name":"海东市","latitude":"","longitude":""},{"code":"630202","parentCode":"630200","level":"3","name":"乐都区","latitude":"","longitude":""},{"code":"630221","parentCode":"630200","level":"3","name":"平安县","latitude":"36.406412","longitude":"102.003965"},{"code":"630222","parentCode":"630200","level":"3","name":"民和回族土族自治县","latitude":"36.157718","longitude":"102.775994"},{"code":"630223","parentCode":"630200","level":"3","name":"互助土族自治县","latitude":"36.849455","longitude":"102.151905"},{"code":"630224","parentCode":"630200","level":"3","name":"化隆回族自治县","latitude":"36.071883","longitude":"102.243978"},{"code":"630225","parentCode":"630200","level":"3","name":"循化撒拉族自治县","latitude":"35.713963","longitude":"102.463877"},{"code":"632200","parentCode":"630000","level":"2","name":"海北藏族自治州","latitude":"36.960654","longitude":"100.879802"},{"code":"632221","parentCode":"632200","level":"3","name":"门源回族自治县","latitude":"37.458384","longitude":"101.731344"},{"code":"632222","parentCode":"632200","level":"3","name":"祁连县","latitude":"8.327949","longitude":"99.711263"},{"code":"632223","parentCode":"632200","level":"3","name":"海晏县","latitude":"37.114748","longitude":"100.843355"},{"code":"632224","parentCode":"632200","level":"3","name":"刚察县","latitude":"7.556877","longitude":"99.988383"},{"code":"632300","parentCode":"630000","level":"2","name":"黄南藏族自治州","latitude":".522852","longitude":"102.0076"},{"code":"632321","parentCode":"632300","level":"3","name":"同仁县","latitude":"35.426829","longitude":"102.078449"},{"code":"632322","parentCode":"632300","level":"3","name":"尖扎县","latitude":"35.918697","longitude":"101.849754"},{"code":"632323","parentCode":"632300","level":"3","name":"泽库县","latitude":"35.139217","longitude":"101.435446"},{"code":"632324","parentCode":"632300","level":"3","name":"河南蒙古族自治县","latitude":"34.157184","longitude":"113.486804"},{"code":"632500","parentCode":"630000","level":"2","name":"海南藏族自治州","latitude":"19.180501","longitude":"109.733755"},{"code":"632521","parentCode":"632500","level":"3","name":"共和县","latitude":"36.538342","longitude":"100.064876"},{"code":"632522","parentCode":"632500","level":"3","name":"同德县","latitude":"35.068401","longitude":"100.601739"},{"code":"632523","parentCode":"632500","level":"3","name":"贵德县","latitude":"36.010503","longitude":"101.415762"},{"code":"632524","parentCode":"632500","level":"3","name":"兴海县","latitude":"5.5403","longitude":"99.733309"},{"code":"632525","parentCode":"632500","level":"3","name":"贵南县","latitude":"5.698086","longitude":"100.88461"},{"code":"632600","parentCode":"630000","level":"2","name":"果洛藏族自治州","latitude":"34.480485","longitude":"100.223723"},{"code":"632621","parentCode":"632600","level":"3","name":"玛沁县","latitude":"4.504017","longitude":"99.794262"},{"code":"632622","parentCode":"632600","level":"3","name":"班玛县","latitude":"32.909736","longitude":"100.550429"},{"code":"632623","parentCode":"632600","level":"3","name":"甘德县","latitude":"34.021808","longitude":"100.147842"},{"code":"632624","parentCode":"632600","level":"3","name":"达日县","latitude":"3.482697","longitude":"99.410809"},{"code":"632625","parentCode":"632600","level":"3","name":"久治县","latitude":"33.473903","longitude":"101.005508"},{"code":"632626","parentCode":"632600","level":"3","name":"玛多县","latitude":"4.79757","longitude":"98.244477"},{"code":"632700","parentCode":"630000","level":"2","name":"玉树藏族自治州","latitude":"3.00624","longitude":"97.013316"},{"code":"632701","parentCode":"632700","level":"3","name":"玉树市","latitude":"","longitude":""},{"code":"632722","parentCode":"632700","level":"3","name":"杂多县","latitude":"3.065764","longitude":"94.301315"},{"code":"632723","parentCode":"632700","level":"3","name":"称多县","latitude":"3.935172","longitude":"97.001974"},{"code":"632724","parentCode":"632700","level":"3","name":"治多县","latitude":"4.884439","longitude":"92.608642"},{"code":"632725","parentCode":"632700","level":"3","name":"囊谦县","latitude":"2.178289","longitude":"96.137026"},{"code":"632726","parentCode":"632700","level":"3","name":"曲麻莱县","latitude":"4.876865","longitude":"95.140846"},{"code":"632800","parentCode":"630000","level":"2","name":"海西蒙古族藏族自治州","latitude":"7.373799","longitude":"97.342625"},{"code":"632801","parentCode":"632800","level":"3","name":"格尔木市","latitude":"5.580972","longitude":"92.701667"},{"code":"632802","parentCode":"632800","level":"3","name":"德令哈市","latitude":"7.727059","longitude":"97.162832"},{"code":"632821","parentCode":"632800","level":"3","name":"乌兰县","latitude":"6.902367","longitude":"98.672631"},{"code":"632822","parentCode":"632800","level":"3","name":"都兰县","latitude":"6.160067","longitude":"97.154435"},{"code":"632823","parentCode":"632800","level":"3","name":"天峻县","latitude":"8.051753","longitude":"98.496512"},{"code":"640000","parentCode":"0","level":"1","name":"宁夏回族自治区","latitude":"37.321323","longitude":"106.155481"},{"code":"640100","parentCode":"640000","level":"2","name":"银川市","latitude":"38.502621","longitude":"106.206479"},{"code":"640101","parentCode":"640100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"640104","parentCode":"640100","level":"3","name":"兴庆区","latitude":"38.464266","longitude":"106.382121"},{"code":"640105","parentCode":"640100","level":"3","name":"西夏区","latitude":"38.553281","longitude":"106.055556"},{"code":"640106","parentCode":"640100","level":"3","name":"金凤区","latitude":"8.478591","longitude":"106.24265"},{"code":"640121","parentCode":"640100","level":"3","name":"永宁县","latitude":"38.295049","longitude":"106.109048"},{"code":"640122","parentCode":"640100","level":"3","name":"贺兰县","latitude":"38.687107","longitude":"106.266518"},{"code":"640181","parentCode":"640100","level":"3","name":"灵武市","latitude":"935175","longitude":"106.532"},{"code":"640200","parentCode":"640000","level":"2","name":"石嘴山市","latitude":"39.020223","longitude":"106.379337"},{"code":"640201","parentCode":"640200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"640202","parentCode":"640200","level":"3","name":"大武口区","latitude":"38.967534","longitude":"106.387216"},{"code":"640205","parentCode":"640200","level":"3","name":"惠农区","latitude":"","longitude":""},{"code":"640221","parentCode":"640200","level":"3","name":"平罗县","latitude":"38.891511","longitude":"106.544379"},{"code":"640300","parentCode":"640000","level":"2","name":"吴忠市","latitude":"37.993561","longitude":"106.208254"},{"code":"640301","parentCode":"640300","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"640302","parentCode":"640300","level":"3","name":"利通区","latitude":"37.767882","longitude":"106.219012"},{"code":"640303","parentCode":"640300","level":"3","name":"红寺堡区","latitude":"","longitude":""},{"code":"640323","parentCode":"640300","level":"3","name":"盐池县","latitude":"37.625337","longitude":"107.049761"},{"code":"640324","parentCode":"640300","level":"3","name":"同心县","latitude":"37.098457","longitude":"106.247387"},{"code":"640381","parentCode":"640300","level":"3","name":"青铜峡市","latitude":"37.942125","longitude":"105.961462"},{"code":"640400","parentCode":"640000","level":"2","name":"固原市","latitude":"36.021523","longitude":"106.285268"},{"code":"640401","parentCode":"640400","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"640402","parentCode":"640400","level":"3","name":"原州区","latitude":"36.206829","longitude":"106.254011"},{"code":"640422","parentCode":"640400","level":"3","name":"西吉县","latitude":"35.939934","longitude":"105.726749"},{"code":"640423","parentCode":"640400","level":"3","name":"隆德县","latitude":"35.589138","longitude":"106.073611"},{"code":"640424","parentCode":"640400","level":"3","name":"泾源县","latitude":"35.529746","longitude":"106.354023"},{"code":"640425","parentCode":"640400","level":"3","name":"彭阳县","latitude":"35.972546","longitude":"106.662473"},{"code":"640500","parentCode":"640000","level":"2","name":"中卫市","latitude":"","longitude":""},{"code":"640501","parentCode":"640500","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"640502","parentCode":"640500","level":"3","name":"沙坡头区","latitude":"","longitude":""},{"code":"640521","parentCode":"640500","level":"3","name":"中宁县","latitude":"7.360097","longitude":"105.69187"},{"code":"640522","parentCode":"640500","level":"3","name":"海原县","latitude":"36.603125","longitude":"105.679649"},{"code":"650000","parentCode":"0","level":"1","name":"新疆维吾尔自治区","latitude":"2.127001","longitude":"85.614899"},{"code":"650100","parentCode":"650000","level":"2","name":"乌鲁木齐市","latitude":"43.84038","longitude":"87.564988"},{"code":"650101","parentCode":"650100","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"650102","parentCode":"650100","level":"3","name":"天山区","latitude":"3.78386","longitude":"87.632903"},{"code":"650103","parentCode":"650100","level":"3","name":"沙依巴克区","latitude":"3.807886","longitude":"87.545631"},{"code":"650104","parentCode":"650100","level":"3","name":"新市区","latitude":"38.881183","longitude":"115.412245"},{"code":"650105","parentCode":"650100","level":"3","name":"水磨沟区","latitude":"3.843907","longitude":"87.668014"},{"code":"650106","parentCode":"650100","level":"3","name":"头屯河区","latitude":"3.925789","longitude":"87.425049"},{"code":"650107","parentCode":"650100","level":"3","name":"达坂城区","latitude":"3.004872","longitude":"87.527134"},{"code":"650109","parentCode":"650100","level":"3","name":"米东区","latitude":"","longitude":""},{"code":"650121","parentCode":"650100","level":"3","name":"乌鲁木齐县","latitude":"3.419108","longitude":"87.360244"},{"code":"650200","parentCode":"650000","level":"2","name":"克拉玛依市","latitude":"45.594331","longitude":"84.88118"},{"code":"650201","parentCode":"650200","level":"3","name":"市辖区","latitude":"","longitude":""},{"code":"650202","parentCode":"650200","level":"3","name":"独山子区","latitude":"4.302338","longitude":"84.899917"},{"code":"650203","parentCode":"650200","level":"3","name":"克拉玛依区","latitude":".203919","longitude":"84.92699"},{"code":"650204","parentCode":"650200","level":"3","name":"白碱滩区","latitude":"5.633602","longitude":"85.177829"},{"code":"650205","parentCode":"650200","level":"3","name":"乌尔禾区","latitude":"6.006576","longitude":"85.511149"},{"code":"652100","parentCode":"650000","level":"2","name":"吐鲁番地区","latitude":"42.96047","longitude":"89.181595"},{"code":"652101","parentCode":"652100","level":"3","name":"吐鲁番市","latitude":"2.678925","longitude":"89.266025"},{"code":"652122","parentCode":"652100","level":"3","name":"鄯善县","latitude":"2.291735","longitude":"90.616574"},{"code":"652123","parentCode":"652100","level":"3","name":"托克逊县","latitude":"2.450354","longitude":"88.424282"},{"code":"652200","parentCode":"650000","level":"2","name":"哈密地区","latitude":"42.858596","longitude":"93.528355"},{"code":"652201","parentCode":"652200","level":"3","name":"哈密市","latitude":"2.344467","longitude":"93.529373"},{"code":"652222","parentCode":"652200","level":"3","name":"巴里坤哈萨克自治县","latitude":"4.273632","longitude":"92.809873"},{"code":"652223","parentCode":"652200","level":"3","name":"伊吾县","latitude":".570382","longitude":"94.91157"},{"code":"652300","parentCode":"650000","level":"2","name":"昌吉回族自治州","latitude":"4.007058","longitude":"87.296038"},{"code":"652301","parentCode":"652300","level":"3","name":"昌吉市","latitude":"4.175083","longitude":"87.073618"},{"code":"652302","parentCode":"652300","level":"3","name":"阜康市","latitude":"4.424104","longitude":"88.305949"},{"code":"652323","parentCode":"652300","level":"3","name":"呼图壁县","latitude":"4.380956","longitude":"86.693166"},{"code":"652324","parentCode":"652300","level":"3","name":"玛纳斯县","latitude":"4.503552","longitude":"86.137669"},{"code":"652325","parentCode":"652300","level":"3","name":"奇台县","latitude":"4.527652","longitude":"90.110269"},{"code":"652327","parentCode":"652300","level":"3","name":"吉木萨尔县","latitude":"4.352914","longitude":"89.053073"},{"code":"652328","parentCode":"652300","level":"3","name":"木垒哈萨克自治县","latitude":"4.106619","longitude":"90.823488"},{"code":"652700","parentCode":"650000","level":"2","name":"博尔塔拉蒙古自治州","latitude":"4.913651","longitude":"82.052436"},{"code":"652701","parentCode":"652700","level":"3","name":"博乐市","latitude":"4.844209","longitude":"81.874285"},{"code":"652702","parentCode":"652700","level":"3","name":"阿拉山口市","latitude":"","longitude":""},{"code":"652722","parentCode":"652700","level":"3","name":"精河县","latitude":"4.557568","longitude":"82.922362"},{"code":"652723","parentCode":"652700","level":"3","name":"温泉县","latitude":"4.96882","longitude":"80.952156"},{"code":"652800","parentCode":"650000","level":"2","name":"巴音郭楞蒙古自治州","latitude":"41.771362","longitude":"86.121688"},{"code":"652801","parentCode":"652800","level":"3","name":"库尔勒市","latitude":"1.7055","longitude":"85.709418"},{"code":"652822","parentCode":"652800","level":"3","name":"轮台县","latitude":"1.819288","longitude":"84.578959"},{"code":"652823","parentCode":"652800","level":"3","name":"尉犁县","latitude":"0.858796","longitude":"86.866991"},{"code":"652824","parentCode":"652800","level":"3","name":"若羌县","latitude":"8.973844","longitude":"89.762772"},{"code":"652825","parentCode":"652800","level":"3","name":"且末县","latitude":"8.100709","longitude":"85.506366"},{"code":"652826","parentCode":"652800","level":"3","name":"焉耆回族自治县","latitude":"2.096104","longitude":"86.076068"},{"code":"652827","parentCode":"652800","level":"3","name":"和静县","latitude":"2.828681","longitude":"85.200093"},{"code":"652828","parentCode":"652800","level":"3","name":"和硕县","latitude":"2.141076","longitude":"87.588716"},{"code":"652829","parentCode":"652800","level":"3","name":"博湖县","latitude":"1.857898","longitude":"86.885379"},{"code":"652900","parentCode":"650000","level":"2","name":"阿克苏地区","latitude":"41.171731","longitude":"80.269846"},{"code":"652901","parentCode":"652900","level":"3","name":"阿克苏市","latitude":"0.349444","longitude":"81.156013"},{"code":"652922","parentCode":"652900","level":"3","name":"温宿县","latitude":"1.582085","longitude":"80.461878"},{"code":"652923","parentCode":"652900","level":"3","name":"库车县","latitude":"1.781933","longitude":"83.459807"},{"code":"652924","parentCode":"652900","level":"3","name":"沙雅县","latitude":"0.406065","longitude":"82.925516"},{"code":"652925","parentCode":"652900","level":"3","name":"新和县","latitude":"1.3657","longitude":"81.985216"},{"code":"652926","parentCode":"652900","level":"3","name":"拜城县","latitude":"2.045285","longitude":"81.901235"},{"code":"652927","parentCode":"652900","level":"3","name":"乌什县","latitude":"1.261847","longitude":"79.281639"},{"code":"652928","parentCode":"652900","level":"3","name":"阿瓦提县","latitude":"0.060788","longitude":"80.439932"},{"code":"652929","parentCode":"652900","level":"3","name":"柯坪县","latitude":"0.456666","longitude":"78.994696"},{"code":"653000","parentCode":"650000","level":"2","name":"克孜勒苏柯尔克孜自治州","latitude":"","longitude":""},{"code":"653001","parentCode":"653000","level":"3","name":"阿图什市","latitude":"0.13123","longitude":"76.867154"},{"code":"653022","parentCode":"653000","level":"3","name":"阿克陶县","latitude":"8.632494","longitude":"75.258638"},{"code":"653023","parentCode":"653000","level":"3","name":"阿合奇县","latitude":"0.855268","longitude":"77.891629"},{"code":"653024","parentCode":"653000","level":"3","name":"乌恰县","latitude":"9.834579","longitude":"74.921544"},{"code":"653100","parentCode":"650000","level":"2","name":"喀什地区","latitude":"39.470627","longitude":"75.992973"},{"code":"653101","parentCode":"653100","level":"3","name":"喀什市","latitude":"9.513111","longitude":"76.014343"},{"code":"653121","parentCode":"653100","level":"3","name":"疏附县","latitude":"9.409741","longitude":"75.754898"},{"code":"653122","parentCode":"653100","level":"3","name":"疏勒县","latitude":".187645","longitude":"76.36999"},{"code":"653123","parentCode":"653100","level":"3","name":"英吉沙县","latitude":"8.800015","longitude":"76.368709"},{"code":"653124","parentCode":"653100","level":"3","name":"泽普县","latitude":"8.122553","longitude":"77.226408"},{"code":"653125","parentCode":"653100","level":"3","name":"莎车县","latitude":"8.322588","longitude":"77.014833"},{"code":"653126","parentCode":"653100","level":"3","name":"叶城县","latitude":"6.993014","longitude":"77.223631"},{"code":"653127","parentCode":"653100","level":"3","name":"麦盖提县","latitude":".848363","longitude":"78.24231"},{"code":"653128","parentCode":"653100","level":"3","name":"岳普湖县","latitude":"9.116645","longitude":"76.989631"},{"code":"653129","parentCode":"653100","level":"3","name":"伽师县","latitude":"9.599103","longitude":"77.231563"},{"code":"653130","parentCode":"653100","level":"3","name":"巴楚县","latitude":"9.618107","longitude":"78.907139"},{"code":"653131","parentCode":"653100","level":"3","name":"塔什库尔干塔吉克自治县","latitude":"","longitude":""},{"code":"653200","parentCode":"650000","level":"2","name":"和田地区","latitude":"37.116774","longitude":"79.930239"},{"code":"653201","parentCode":"653200","level":"3","name":"和田市","latitude":"7.15335","longitude":"79.915814"},{"code":"653221","parentCode":"653200","level":"3","name":"和田县","latitude":"5.683432","longitude":"79.354993"},{"code":"653222","parentCode":"653200","level":"3","name":"墨玉县","latitude":"8.384224","longitude":"80.047148"},{"code":"653223","parentCode":"653200","level":"3","name":"皮山县","latitude":".228319","longitude":"78.52185"},{"code":"653224","parentCode":"653200","level":"3","name":"洛浦县","latitude":"8.02422","longitude":"80.741311"},{"code":"653225","parentCode":"653200","level":"3","name":"策勒县","latitude":"7.084314","longitude":"81.097996"},{"code":"653226","parentCode":"653200","level":"3","name":"于田县","latitude":"7.16913","longitude":"81.995463"},{"code":"653227","parentCode":"653200","level":"3","name":"民丰县","latitude":"7.173147","longitude":"83.352763"},{"code":"654000","parentCode":"650000","level":"2","name":"伊犁哈萨克自治州","latitude":"3.922248","longitude":"81.297854"},{"code":"654002","parentCode":"654000","level":"3","name":"伊宁市","latitude":"4.020356","longitude":"81.289048"},{"code":"654003","parentCode":"654000","level":"3","name":"奎屯市","latitude":"4.559557","longitude":"85.013934"},{"code":"654021","parentCode":"654000","level":"3","name":"伊宁县","latitude":".008117","longitude":"81.75694"},{"code":"654022","parentCode":"654000","level":"3","name":"察布查尔锡伯自治县","latitude":"3.638377","longitude":"81.098298"},{"code":"654023","parentCode":"654000","level":"3","name":"霍城县","latitude":"4.30912","longitude":"80.781159"},{"code":"654024","parentCode":"654000","level":"3","name":"巩留县","latitude":"3.30246","longitude":"82.445701"},{"code":"654025","parentCode":"654000","level":"3","name":"新源县","latitude":".376951","longitude":"83.55815"},{"code":"654026","parentCode":"654000","level":"3","name":"昭苏县","latitude":"2.776878","longitude":"80.984257"},{"code":"654027","parentCode":"654000","level":"3","name":"特克斯县","latitude":"2.925564","longitude":"82.006852"},{"code":"654028","parentCode":"654000","level":"3","name":"尼勒克县","latitude":"816731","longitude":"83.2311"},{"code":"654200","parentCode":"650000","level":"2","name":"塔城地区","latitude":"46.758684","longitude":"82.974881"},{"code":"654201","parentCode":"654200","level":"3","name":"塔城市","latitude":"6.811367","longitude":"83.190128"},{"code":"654202","parentCode":"654200","level":"3","name":"乌苏市","latitude":"4.407687","longitude":"84.277878"},{"code":"654221","parentCode":"654200","level":"3","name":"额敏县","latitude":".590664","longitude":"84.20932"},{"code":"654223","parentCode":"654200","level":"3","name":"沙湾县","latitude":"4.353745","longitude":"85.474874"},{"code":"654224","parentCode":"654200","level":"3","name":"托里县","latitude":"5.656986","longitude":"83.895485"},{"code":"654225","parentCode":"654200","level":"3","name":"裕民县","latitude":"6.004456","longitude":"82.814799"},{"code":"654226","parentCode":"654200","level":"3","name":"和布克赛尔蒙古自治县","latitude":"6.256703","longitude":"86.217436"},{"code":"654300","parentCode":"650000","level":"2","name":"阿勒泰地区","latitude":"47.839744","longitude":"88.137915"},{"code":"654301","parentCode":"654300","level":"3","name":"阿勒泰市","latitude":"7.890136","longitude":"87.926214"},{"code":"654321","parentCode":"654300","level":"3","name":"布尔津县","latitude":"8.316007","longitude":"87.235518"},{"code":"654322","parentCode":"654300","level":"3","name":"富蕴县","latitude":"6.536157","longitude":"89.393484"},{"code":"654323","parentCode":"654300","level":"3","name":"福海县","latitude":"6.391694","longitude":"88.050871"},{"code":"654324","parentCode":"654300","level":"3","name":"哈巴河县","latitude":"8.316559","longitude":"86.409673"},{"code":"654325","parentCode":"654300","level":"3","name":"青河县","latitude":"6.268151","longitude":"90.403028"},{"code":"654326","parentCode":"654300","level":"3","name":"吉木乃县","latitude":"7.406311","longitude":"86.208104"},{"code":"659000","parentCode":"650000","level":"2","name":"自治区直辖县级行政区划","latitude":"","longitude":""},{"code":"659001","parentCode":"659000","level":"3","name":"石河子市","latitude":"44.308259","longitude":"86.041865"},{"code":"659002","parentCode":"659000","level":"3","name":"阿拉尔市","latitude":"40.61568","longitude":"81.291737"},{"code":"659003","parentCode":"659000","level":"3","name":"图木舒克市","latitude":"39.889223","longitude":"79.198155"},{"code":"659004","parentCode":"659000","level":"3","name":"五家渠市","latitude":"44.368899","longitude":"87.565449"},{"code":"710000","parentCode":"0","level":"1","name":"台湾省","latitude":"","longitude":""},{"code":"810000","parentCode":"0","level":"1","name":"香港特别行政区","latitude":"22.293586","longitude":"114.186124"},{"code":"820000","parentCode":"0","level":"1","name":"澳门特别行政区","latitude":"22.204118","longitude":"113.557519"}];
				if (code.length < 5) {
					code = code + "00";
				}
				var center = [];
				for (var i = 0; i < areas.length; i++) {
					if (areas[i].code === code) {
						center.push(areas[i].longitude)
						center.push(areas[i].latitude)
					}
				}
				//console.log(center)
				app = new AppMap('mapDiv2', {
					// center: [104.360, 33.360],
					center:center,
					maxZoom: 12
					//minZoom: 10 //禁止缩放，就把maxZoom 和minZoom弄成一样的，10
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
			}
          	//更新态势图
	        function updateMap(time){
				console.log(time)
				//debugger
				var stationsAll =getstationsInfo().stationsInfo;
				var data = {"time":time,stations:stationsAll}
				$.ajaxSetup({
						async : false
				});
				ajax.post("data/alarm/estimate", data, function (result) {
					var stations = result.stationPiont,
						data = result.kriking3.result;
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
					document.getElementById('valCtrl').addEventListener('click', function () {
						var opCtrl = document.getElementById("opCtrl").value||0.7;
						if(opCtrl<0){
							opCtrl=0
						}else if(opCtrl>1){
							opCtrl=1
						}
						app.update('situation', {
							opacity: opCtrl
						});
					});
					$(".coverage-number").html(parseInt(result.electrCoverage* 100)+ "%");
				})
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
				//console.log(defaultAreaCode);
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
				//console.log(have_monitors);
				if(have_monitors == "false") {
					layer.alert("该市暂无监测站信息！")
					return null;
				}
				AREACODE = areaCode;// 更新全局变量
				var user = getUser();
				var userID = user.ID;
				var monitors = getMonitors(areaCode);
				MONITORS = monitors;//	更新监测站全局变量
				//console.log(monitors);
				var monitorsID = new Array();
				for (var i = 0; i < monitors.length; i++) {
					monitorsID[i] = monitors[i].Num;
				}
				$("#searchFremax").on('blur',function(){
					var beginFreq =$("#searchFremin").val(),
						endFreq =$("#searchFremax").val();
					if((parseInt(endFreq)-parseInt(beginFreq))<0){
                     layer.msg('最大频率不能小于最小频率');
						$("#searchFremax").val('')
					}
				})
				$("#searchFremin").on('focus',function(){
					$("#searchFremin").val('');
				})
				//按频段过滤显示“实时告警未确认”、“实时告警已确认”、“信号智能识别”的内容,监听查询
				$("#filterFrequeryBand").on("click", function(e) {
					//按频段过滤显示“实时告警未确认”、“实时告警已确认”、“信号智能识别”的内容
					var beginFreq =$("#searchFremin").val(),
						endFreq =$("#searchFremax").val();
					//console.log('过滤频段：'+beginFreq+'-'+endFreq)
					var filter ={
						beginFreq:parseInt(beginFreq)*1000000,
						endFreq:parseInt(endFreq)*1000000
					}
					table_alarm_undealed(monitorsID, monitors,filter);
					table_alarm_dealed(monitorsID, monitors,filter);
					radio_auto_confirm(monitorsID, monitors,filter);
				});
				//按频段过滤显示“实时告警未确认”、“实时告警已确认”、“信号智能识别”的内容
				var beginFreq =$("#searchFremin").val(),
					endFreq =$("#searchFremax").val();
				//console.log('过滤频段：'+beginFreq+'-'+endFreq)
				var filter ={
					beginFreq:parseInt(beginFreq)*1000000,
					endFreq:parseInt(endFreq)*1000000
				}
				getSignalCounts(areaCode,monitorsID,beginFreq,endFreq);
				table_radio_init(monitors, userID);
				table_alarm_undealed(monitorsID, monitors,filter);
				table_alarm_dealed(monitorsID, monitors,filter);
				radio_auto_confirm(monitorsID, monitors,filter);
				//改变地图中心
				var center = new Point({
							"x" : MONITORS[0].Longitude,
							"y" : MONITORS[0].Latitude
						});
				MAP1.centerAt(center);
				//改变行政区域边界
				addAreaBoundary(MAP1);
				//改变每个监测站点上的信号总数
				addSignalCountOnMonitors(monitors,3,"false");//默认选中3非法信号，子类型为false
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
									//console.log(result);
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
										+ "<b>纬度: </b>"+ info.y.toFixed(5) +"<br>"
										+ "<b>经度: </b>"+ info.x.toFixed(5)  +"<br>"

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
								//console.log(e);
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
			function table_alarm_dealed(monitorsID, monitors,filter) {
				$('#table-alarm-dealed').bootstrapTable("destroy");
				$('#table-alarm-dealed').bootstrapTable({
					height : 510,
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
						params.beginFreq = filter.beginFreq;
						params.endFreq=filter.endFreq;
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
								align : 'left',
								width : '4%',
								title: '序号',
								formatter : function(value,row,index) {
									return index + 1;
								}
							}, {
								class : 'sortTable2',
							    align : 'left',
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
							    align: "left",
								width : '15%',
								title : '监测站'+"<img src='images/arrow-both.png'width='24'/> ",
								titleTooltip : "监测站",
								sortable : true,
								sortName : "stationID",
								formatter : function(value, row, index) {
									var content = "";
									var value1 = [];
									var temp=[];
									for (var i = 0; i < value.length; i++) {
										for (var j = 0; j < monitors.length; j++) {
											if (value[i] == monitors[j].Num) {
												value1[i] = monitors[j].Name;
												var sub_content = "<div class='popover-item'>"
														+ value1[i] + "</div>";
												content += sub_content;
												temp.push(value1[i]);
											}
										}
									}
									return '<div class="dpopover" data-container="body"  data-placement="top"  data-toggle="popover" data-trigger="hover" data-content="'
											+ content + '">' + temp + '</div>';
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
							    align: "left",
								formatter : function(value, row, index) {
									return value;
								}
							},{
								field : 'mark',
								width : '10%',
							    align: "left",
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
						$(".table-alarm-dealed").find(".pull-right .pagination").append("<span class='goPage'>跳转到第<input id='alarmDealedPageBtn' class='pageNum' type='number'>页</span>")
						$("#alarmDealedPageBtn").on("blur keydown",function(e){
							if (e.type=='blur'||(e.type=='keydown'&&e.keyCode == "13")) {
								$("#table-alarm-dealed").bootstrapTable("selectPage",parseInt($("#alarmDealedPageBtn").val()));
							}
						})
					},
					onPageChange:function(){
						$(".table-alarm-dealed").find(".pull-right .pagination").append("<span class='goPage'>跳转到第<input id='alarmDealedPageBtn' class='pageNum' type='number'>页</span>")
						$("#alarmDealedPageBtn").on("blur keydown",function(e){
							if (e.type=='blur'||(e.type=='keydown'&&e.keyCode == "13")) {
								$("#table-alarm-dealed").bootstrapTable("selectPage",parseInt($("#alarmDealedPageBtn").val()));
							}
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
			function table_alarm_undealed(monitorsID, monitors,filter) {
				$('#table-alarm-undeal').bootstrapTable("destroy");
				var option = {
					height:610,
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
						params.beginFreq = filter.beginFreq;
						params.endFreq=filter.endFreq;
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
								align : 'left',
								width : '6%',
								title: '序号',
								formatter : function(value,row,index) {
									return index + 1;
								}
							},{
								class : 'sortTable2',
							    align : 'left',
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
							align: 'left',
								sortName : "stationID",
								titleTooltip : "监测站",
								formatter : function(value, row, index) {
									var content = "";
									var value1 = [];
									var temp=[];
									for (var i = 0; i < value.length; i++) {
										for (var j = 0; j < monitors.length; j++) {
											if (value[i] == monitors[j].Num) {
												value1[i] = monitors[j].Name;
												var sub_content = "<div class='popover-item'>"
														+ value1[i] + "</div>";
												content += sub_content;
												temp.push(value1[i]);
											}
										}
									}
									return '<div class="dpopover" data-container="body"  data-placement="top"  data-toggle="popover" data-trigger="hover" data-content="'
											+ content + '">' + temp + '</div>';
								}
							}, {
								field : 'status',
								width : '10%',
								title : '状态',
							align: 'left',
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
							 align: 'left',
								title : '告警内容',
								titleTooltip : "告警内容",
								formatter : function(value, row, index) {
									return value;
								}
							},{
								field : 'mark',
								width : '10%',
								title : '描述',
							    align: 'left',
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
						$("#alarmUndealPageBtn").on("blur keydown",function(e){
							if (e.type=='blur'||(e.type=='keydown'&&e.keyCode == "13")) {
								$("#table-alarm-undeal").bootstrapTable("selectPage",parseInt($("#alarmUndealPageBtn").val()));
							}
						})
					},
					onPageChange:function(){
						$(".table-alarm-undeal").find(".pull-right .pagination").append("<span class='goPage'>跳转到第<input id='alarmUndealPageBtn' class='pageNum' type='text'>页</span>")
						$("#alarmUndealPageBtn").on("blur keydown",function(e){
							if (e.type=='blur'||(e.type=='keydown'&&e.keyCode == "13")) {

								$("#table-alarm-undeal").bootstrapTable("selectPage",parseInt($("#alarmUndealPageBtn").val()));
							}
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
			function radio_auto_confirm(monitorsID, monitors,filter) {
				$('#radio_auto_confirm').bootstrapTable("destroy");
				var option = {
					height : 510,
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
						params.beginFreq = filter.beginFreq;
						params.endFreq=filter.endFreq;
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
							align : 'left',
							width : '6%',
							title: '序号',
							formatter : function(value,row,index) {
								return index + 1;
							}
						},{
							class : 'sortTable2',
							align: 'left',
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
							align: 'left',
							field : 'band',
							title : '带宽(kHz)'+"<img src='images/arrow-both.png'width='24'/> ",
							width : '15%',
							titleTooltip : "带宽(kHz)",
							sortable : true
						}, {
							class : 'sortTable2',
							visible : false,
							align: 'left',
							field : 'success_rate',
							title : '监测发射功率'+"<img src='images/arrow-both.png'width='24'/> ",
							width : '18%',
							titleTooltip : "监测发射功率",
							sortable : true
						}, {
							class : 'sortTable2',
							align: 'left',
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
									var temp=[];
									for (var i = 0; i < value.length; i++) {
										for (var j = 0; j < monitors.length; j++) {
											if (value[i] == monitors[j].Num) {
												value1[i] = monitors[j].Name;
												var sub_content = "<div class='popover-item'>"
														+ value1[i] + "</div>";
												content += sub_content;
												temp.push(value1[i]);
											}
										}
									}
									return '<div class="dpopover" data-container="body"  data-placement="top"  data-toggle="popover" data-trigger="hover" data-content="'
											+ content + '">' + temp + '</div>';
							},
							events : {

							}
						}, {
							class : 'sortTable2',
							align: 'left',
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
							title : '操作',
							titleTooltip : "操作",
							align: 'left',
							field : "signalManage",
							formatter : function(value, row, index) {
								return '<a signalId=' + row.id
										+ ' class="signalManageA" centorFreq='
										+ row.centor + '>查看</a>';
							}
						}],
					onLoadSuccess : function() {
						$(".radio_auto_confirm").find(".pull-right .pagination").append("<span class='goPage'>跳转到第<input id='autoConfirmPageBtn' class='pageNum' type='text'>页</span>")
						$("#autoConfirmPageBtn").on("blur keydown",function(e){
							if (e.type=='blur'||(e.type=='keydown'&&e.keyCode == "13")) {

								$("#radio_auto_confirm").bootstrapTable("selectPage",parseInt($("#autoConfirmPageBtn").val()));
							}
						})
					},
					onPageChange:function(){
						$(".radio_auto_confirm").find(".pull-right .pagination").append("<span class='goPage'>跳转到第<input id='autoConfirmPageBtn' class='pageNum' type='text'>页</span>")
						$("#autoConfirmPageBtn").on("blur keydown",function(e){
							if (e.type=='blur'||(e.type=='keydown'&&e.keyCode == "13")) {

								$("#radio_auto_confirm").bootstrapTable("selectPage",parseInt($("#autoConfirmPageBtn").val()));
							}
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
					//height : 320,
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

						$(".table-radio").find(".pull-right .pagination").append("<span class='goPage'>跳转到第<input id='PageBtn' class='pageNum' type='text'>页</span>")
						//$(".table-radio").find(".pull-right.pagination").append("<input type='button' value='go' class='pageBtn'>")
						$("#PageBtn").on("blur keydown",function(e){
							if (e.type=='blur'||(e.type=='keydown'&&e.keyCode == "13")) {

								$("#table-radio").bootstrapTable("selectPage",parseInt($("#PageBtn").val()));
							}
						})

					},
					onPageChange:function(){
						$(".table-radio").find(".pull-right .pagination").append("<span class='goPage'>跳转到第<input id='PageBtn' class='pageNum' type='text'>页</span>")
						//$(".table-radio").find(".pull-right.pagination").append("<input type='button' value='go' class='pageBtn'>")
						$("#PageBtn").on("blur keydown",function(e){
							if (e.type=='blur'||(e.type=='keydown'&&e.keyCode == "13")) {

								$("#table-radio").bootstrapTable("selectPage",parseInt($("#PageBtn").val()));
							}
						})
					},
					onAll:function(){
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

					},
					columns : [{
						align: "left",
						title: '序号',
						width : '6%',
						formatter : function(value,row,index) {
							return index + 1;
						}
					},{
						class : "sortTable1",
						field : 'beginFreq',
						sortable : true,
						sortName : "beginFreq",
						align: "left",
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
						align: "left",
						field : 'redioName',
						title : '频段名称'+"<img src='images/arrow-both.png'width='24'/> " ,
						width : '14%',
						titleTooltip : '频段名称',
						sortable : true,
						sortName : "redioName",
						formatter : function(value, row, index) {
							var divide = 1000000;
							var beginFreq = Math.round(row.beginFreq / divide * 1000) / 1000;
							var endFreq = Math.round(row.endFreq / divide * 1000) / 1000;
							return '<div class="dpopover" data-placement="top"  data-toggle="popover" data-trigger="hover" data-content="'
									+ value +'"> <a class="redioNameA" data-frequeryBand="'+(beginFreq+ "-" +endFreq)+'">'+ value + '</a> </div>';
						}
					},
						{
						class : "sortTable1",
							align: "left",
						field : 'alarmingNumber',
						title : '告警数量'+"<img src='images/arrow-both.png'width='24'/> " ,
						width : '10%',
						titleTooltip : '告警数量',
						sortable : true,
						sortName : "alarmingNumber"
						//formatter : function(value, row, index) {
						//	return '<a data-toggle="modal" data-target="#modalAlarm" data-radioType="1" data-isSubType="false" data-beginFreq="'
						//		+ row.beginFreq
						//		+ '" data-endFreq="'
						//		+ row.endFreq + '">' + value + '</a>';
						//}

					},
						{
						class : "sortTable1",
						field : 'legalNormalStationNumber',
						title : '合法正常信号'+"<img src='images/arrow-both.png'width='24'/> " ,
						width : '10%',
						titleTooltip : '合法正常信号',
						sortable : true,
							align: "left",
							//halign: "left",
						sortName : "legalNormalStationNumber",
						footerFormatter : function(data) {
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
							align: "left",
							//halign: "left",
						sortName : "legalUnNormalStationNumber",
						footerFormatter : function(data) {
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
							align: "left",
							//halign: "left",
						sortName : "konwStationNumber",
						footerFormatter : function(data) {
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
							align: "left",
							//halign: "left",
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
							align: "left",
							//halign: "left",
						sortName : "unKonw",
						footerFormatter : function(data) {
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