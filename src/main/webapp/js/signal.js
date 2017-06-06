$(function(){
  var option = {
    color:['rgb(44,205,125)','rgb(55,165,255)'],
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)"
    },
    legend: {
        show:false,
        orient: 'vertical',
        x: 'left',
        data:['AM','FM']
    },
    series: [
        {
            name:'信号',
            type:'pie',
            radius: ['30%', '45%'],
            avoidLabelOverlap: false,
            label: {
                normal: {
                    show: true,
                    position: 'outside',
                    formatter:'{b} {d}%',
                    textStyle: {
                        fontSize: '16'
                    }
                },
                emphasis: {
                    show: true,
                    textStyle: {
                        fontSize: '20',
                        fontWeight: 'bold'
                    }
                }
            },
            labelLine: {
                normal: {
                    show: false
                }
            },
            data:[
                {value:20, name:'AM'},
                {value:80, name:'FM'}
            ]
        }
    ]
};
var myChart = echarts.init($('#radioChart')[0]);
myChart.setOption(option);
})
