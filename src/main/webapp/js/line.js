var uploadedDataURL = "/assets/json/line-data.json";


$.getJSON(uploadedDataURL, function (data) {

    data = data.slice(0, 50);
    var dateList = data.map(function (item) {
        return item[0];
    });
    var valueList = data.map(function (item) {
        return item[1];
    });

    var myChart = echarts.init($('#line')[0]);
    myChart.setOption(option = {

        // Make gradient line here
        visualMap: {
            show: false,
            type: 'continuous',
            seriesIndex: 0,
            min: 0,
            max: 400,
            inRange: {
                color: ['rgb(40,112,191)', 'rgb(44,189,137)', 'rgb(42,167,151)']
            }
        },

        title: {
            left: 'center',
            text: 'Gradient along the y axis'
        },
        tooltip: {
            trigger: 'axis'
        },
        xAxis: {
            data: dateList
        },
        yAxis: {
            splitLine: {show: false},
            splitArea:{
                show:true,
                areaStyle:{
                    color:['rgb(229,243,254)','rgb(246,250,253)']
                }
            }
        },
        grid: {
            bottom: '60%'
        },
        series: {
            type: 'line',
            name:'占用度',
            data: valueList,
            showSymbol:false,
            symbolSize:8,
            lineStyle:{
                normal:{
                    width:4
                }
            }
        }
    });
});
