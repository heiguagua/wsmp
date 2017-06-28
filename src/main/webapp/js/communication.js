$(function() {


    $('.select2-picker').select2();

    $('.time-picker').datetimepicker({});
    $('#table-comms').bootstrapTable({
        method : 'get',
        contentType : "application/x-www-form-urlencoded", //必须要有！！！！
        url : "../assets/json/table-comms.json", //要请求数据的文件路径
        striped : true, //是否显示行间隔色
        dataField : "rows", //bootstrap table 可以前端分页也可以后端分页，这里
        //我们使用的是后端分页，后端分页时需返回含有total：总记录数,这个键值好像是固定的
        //rows： 记录集合 键值可以修改  dataField 自己定义成自己想要的就好
        detailView : false,
        pageNumber : 1, //初始化加载第一页，默认第一页
        pagination : true, //是否分页
        queryParamsType : 'limit', //查询参数组织方式
        // queryParams:queryParams,//请求服务器时所传的参数
        sidePagination : 'server', //指定服务器端分页
        pageSize : 10, //单页记录数
        pageList : [5, 10, 20, 30], //分页步进值
        clickToSelect : true, //是否启用点击选中行
        responseHandler : function(res) {
            return res;
        },
        columns : [{
            field : 'net_type',
            title : '2G-4G',
            sortable : true,
            formatter : function(value, row, index) {
                return '<a>' + value + '</a>';
            }
        }, {
            field : 'operator',
            title : '运营商',
            sortable : true,
            formatter : function(value, row, index) {
                return '<a>' + value + '</a>';
            }
        }, {
            field : 'channel',
            title : '频段范围',
            sortable : true,
            formatter : function(value, row, index) {
                return '<a>' + value + '</a>';
            }
        }, {
            field : 'tech_way',
            title : '技术制式 ',
            sortable : true,
        }, {
            field : 'channel_use',
            title : '频段占用度',
            sortable : true,
        }, {
            field : 'info_use',
            title : '信道占用度',
            sortable : true,
        }, {
            field : 'detect_coverage',
            title : '监测覆盖率',
            sortable : true,
        }, {
            field : 'comm_coverage',
            title : '通讯覆盖率',
            sortable : true,
        }]
    });

    $('#table-station-compare').bootstrapTable({
        method : 'get',
        contentType : "application/x-www-form-urlencoded", //必须要有！！！！
        url : "../assets/json/table-station-compare.json", //要请求数据的文件路径
        striped : true, //是否显示行间隔色
        dataField : "rows", //bootstrap table 可以前端分页也可以后端分页，这里
        //我们使用的是后端分页，后端分页时需返回含有total：总记录数,这个键值好像是固定的
        //rows： 记录集合 键值可以修改  dataField 自己定义成自己想要的就好
        detailView : false,
        pageNumber : 1, //初始化加载第一页，默认第一页
        pagination : true, //是否分页
        queryParamsType : 'limit', //查询参数组织方式
        // queryParams:queryParams,//请求服务器时所传的参数
        sidePagination : 'server', //指定服务器端分页
        pageSize : 10, //单页记录数
        pageList : [5, 10, 20, 30], //分页步进值
        clickToSelect : true, //是否启用点击选中行
        responseHandler : function(res) {
            return res;
        },
        columns : [{
            field : 'station_type',
            title : '类型',
            class : '',
        }, {
            field : 'G2',
            title : '2G',
            formatter : function(value, row, index) {
                if (row.G2_compare == 'up') {
                    return value + '<span class="fa fa-arrow-up"></span>';
                }
                return value + '<span class="fa fa-arrow-down"></span>';
            }
        }, {
            field : 'G3',
            title : '3G',
            formatter : function(value, row, index) {
                if (row.G3_compare == 'up') {
                    return value + '<span class="fa fa-arrow-up"></span>';
                }
                return value + '<span class="fa fa-arrow-down"></span>';
            }
        }, {
            field : 'G4',
            title : '4G ',
            formatter : function(value, row, index) {
                if (row.G4_compare == 'up') {
                    return value + '<span class="fa fa-arrow-up"></span>';
                }
                return value + '<span class="fa fa-arrow-down"></span>';
            }
        }, {
            field : 'station_total',
            title : '总数',
            formatter : function(value, row, index) {
                if (row.total_compare == 'up') {
                    return value + '<span class="fa fa-arrow-up"></span>';
                }
                return value + '<span class="fa fa-arrow-down"></span>';
            }
        }]
    });


})
