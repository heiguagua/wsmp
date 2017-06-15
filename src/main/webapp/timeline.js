
function radios() {
	var data = [];
	for (var i = 0; i < 100; i += 0.25) {
		data[i] = i + ""
	}
	return data;
}

function values() {
	var data = [];

	var n = 20;
	var m = 100;
	var c = m - n + 1;
	for (var i = 0; i < 100; i++) {
		data[i] = Math.floor(Math.random() * c + n);
	}
	return data;
}

var data1 = [];
var line = 200;
for (var i = 1; i <= line; i++) {
	data1[i] = i
}

function _options() {
	var op = [];
	for (var i = 0; i < line; i++) {
		op[i] = {
			series : [ {
				data : values()
			} ]
		};

	}
	return op;
}


var option = {
	baseOption : {
		timeline : {
			// y: 0,
			data : data1,
			axisType : 'value',
			// realtime: false,
			// loop: false,
			autoPlay : true,
			// currentIndex: 2,
			playInterval : 100,
			symbol : "emptyCircle",
			symbolSize : 1,
			controlStyle : {
				position : 'left',
				showPrevBtn : "true",
				showNextBtn : "true"
			},
			lineStyle : {
				show : false,
				color : {
					type : 'radial',
					x : 0.5,
					y : 0.5,
					r : 0.5,
					colorStops : [ {
						offset : 0,
						color : 'red' // 0% 处的颜色
					}, {
						offset : 1,
						color : 'blue' // 100% 处的颜色
					} ],
					globalCoord : false // 缺省为 false
				}
			},
			label : {
				normal : {
					show : false
				}
			}
		},
		xAxis : [
			{
				show : false,
				//				type : 'category',
				data : radios(),
				splitLine : {
					show : false
				}
			}
		],
		yAxis : [
			{
				type : 'value',
				max : 120,
				min : 0
			}
		],
		series : [
			{
				name : '能量值',
				type : 'line'
			}
		]
	},
	options : _options()
}

var myChart = echarts.init(document.getElementById('main'));
myChart.setOption(option);