define([ "ajax", "echarts", "jquery" ], function(ajax, echarts, jquery) {
	// 加载音频数据
    var audio_play_list = [];
    function load_audio_data(stationcode,centorfreq,beginTime,endTime) {

        var url = "data/asiq/audio/" + stationcode + "/" + centorfreq + "/" + beginTime + "/" + endTime;
        //var url = "data/asiq/audio/52010126/10040000000000/20170810135330/20170810135330";
        //var url = "assets/json/audio-player-list.json";
        ajax.get(url, null, function(result) {
            var data = result;
            if(!data || data.length == 0) {
                data = null;
                return;
            }
            $('#audio-table').bootstrapTable({
                method : 'get',
                contentType : "application/x-www-form-urlencoded", //必须要有！！！！
                striped : true,
                dataField : "rows",
                detailView : false,
                pageNumber : 1, //初始化加载第一页，默认第一页
                pagination : true, //是否分页
                data : data,
                //					url :"assets/json/audio-player-list.json",
                queryParamsType : 'limit', //查询参数组织方式
                queryParams : function(params) {
                    return params
                }, //请求服务器时所传的参数
                onClickRow : function(row) {},
                sidePagination : 'client', //指定服务器端分页
                pageSize : 5, //单页记录数
                clickToSelect : true, //是否启用点击选中行
                responseHandler : function(res) {
                    return res;
                },
                onCheck : function(row) {
                    audio_play_list.push(row);
                },
                onUncheck : function(row) {
                    for (var i = 0; i < audio_play_list.length; i++) {
                        if (row == audio_play_list[i]) {
                            audio_play_list.splice(i, 1);
                        }
                    }

                },
                onCheckAll : function(rows) {
                    audio_play_list = rows;
                },
                onUncheckAll : function(rows) {
                    audio_play_list = [];
                },
                columns : [{
                    checkbox : true,
                    title : "选中"
                }, {
                    field : 'id',
                    title : '传感器编号',
                    width : '10%',
                    titleTooltip:"传感器编号"
                }, {
                    field : 'taskId',
                    title : '任务唯一编号',
                    width : '25%',
                    titleTooltip:"任务唯一编号"
                }, {
                    field : 'timeStart',
                    title : '任务开始时间',
                    titleTooltip:"任务开始时间",
                    width : '15%',
                    formatter : function(value) {
                        return new Date(value).format('yyyy-MM-dd hh:mm:ss');
                    }
                }, {
                    field : 'timeStop',
                    title : '任务结束时间',
                    width : '15%',
                    titleTooltip:"任务结束时间",
                    formatter : function(value) {
                        return new Date(value).format('yyyy-MM-dd hh:mm:ss');
                    }
                }, {
                    field : 'centerFreq',
                    title : '测量中心频率(MHz)',
                    align:'center',
                    width : '15%',
                    align:'center',
                    titleTooltip:"测量中心频率(MHz)"
                }, {
                    field : 'audioLength',
                    title : '声音数据长度',
                    width : '10%',
                    align:'center',
                    titleTooltip:"声音数据长度"
                }]
            });
        })



    }
    
 // 音频数据播放
    var wavesurfer;
    function audio_player() {
        if(audio_play_list && audio_play_list.length>0) {
            if (wavesurfer) {
            wavesurfer.destroy();
        }

        wavesurfer = WaveSurfer.create({
            container : document.querySelector('#visualizer'),
            waveColor : '#00ff00',
            progressColor : '#038E03',
            splitChannels : true
        });

        // Load audio from URL
        wavesurfer.load("data/asiq/audio/" + audio_play_list[0].dataUrl);

        // Play/pause on button press
        document.querySelector('[data-action="play"]').addEventListener(
            'click', wavesurfer.playPause.bind(wavesurfer)
        );

        var links = audio_play_list;
        var currentTrack = 0;

        // Load a track by index and highlight the corresponding link
        var setCurrentSong = function(index) {
            currentTrack = index;
            wavesurfer.load("data/asiq/audio/" + links[currentTrack].dataUrl);
            $(".audio-play-control .current-index").html(currentTrack+1);
        };
        
        // 播放控制
        $(".audio-play-control .current-index").html(1);
        $(".audio-play-control .total-length").html(audio_play_list.length);
        $(".audio-play-control .play i").removeClass("fa-play").addClass("fa-pause");

        $(".audio-play-control .play").unbind("click").bind("click",function(){
        	if($(".audio-play-control .play i").hasClass("fa-pause")) {
                $(".audio-play-control .play i").removeClass("fa-pause").addClass("fa-play");
            }
            else{
                $(".audio-play-control .play i").removeClass("fa-play").addClass("fa-pause");
            }
            wavesurfer.playPause();
        })

        $(".audio-play-control .backward").unbind("click").bind("click",function(){
            if(currentTrack <= 0) {
                return;
            }
            setCurrentSong((currentTrack - 1) % links.length);
        })

        $(".audio-play-control .forward").unbind("click").bind("click",function(){
            setCurrentSong((currentTrack + 1) % links.length);
        })

        var progressDiv = $('#progress-bar');
        var progressBar = $('.progress-bar');

        var showProgress = function(percent) {
            progressDiv.show();
            progressBar.css({
                "width" : percent + '%'
            })
        //progressBar.style.width = percent + '%';
        };

        var hideProgress = function() {
            progressDiv.hide();
        };

        wavesurfer.on('loading', showProgress);
        wavesurfer.on('destroy', hideProgress);
        wavesurfer.on('error', hideProgress);

        wavesurfer.on('finish', function() {
            setCurrentSong((currentTrack + 1) % links.length);
        })

        // Play on audio load
        wavesurfer.on('ready', function() {
            hideProgress();
            wavesurfer.play();
        });

        }


    }
    function destroy(){
        $('#audio-table').bootstrapTable('destroy');
    }
    function autoClickInit(){
        $("#audio").on("click", function() {
            if ($(this).is(":checked")) {
                $("#audio-wrap").slideDown();
            } else {
                $("#audio-wrap").slideUp();
                if(wavesurfer){
                    wavesurfer.destroy();
                }
            }
        })
    }
    function audioloseClick(){
        //关闭音频播放
        $("#audio-close").on("click", function() {
            $("#audio-wrap").slideUp();
            $("#audio").prop("checked", false);
            if(wavesurfer){
                wavesurfer.destroy();
            }

        })
    }
	return {
		init:load_audio_data,
		play:audio_player,
        destroy:destroy,
        autoClickInit: autoClickInit,
        audioloseClick:audioloseClick
	}
})