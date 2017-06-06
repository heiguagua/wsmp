/**
 * Created by wuhaoran on 2017/2/25.
 */
define([], function (declare,Widget) {
    function test() {
        declare("MyFirstBehavioralWidget",Widget,{});
    }

    function btnClick(e) {
        require(["dojo/request", "dojo/_base/window", "dojo/dom-style", "dojox/layout/FloatingPane"],
            function (request, win, style, FloatingPane) {
                //alert(111);
                console.log(e.target.id)
                var floaterDiv = document.createElement("div");
                win.body().appendChild(floaterDiv);
                floaterDiv.appendChild(document.createElement("br"));
                var textarea = document.createElement("div");
//                  textarea.innerHTML = "徐兰就是一个屌丝";
                floaterDiv.appendChild(textarea);
                var tmp = new FloatingPane({
                        title: "<b>关于徐兰</b>",
                        id: "aboutBox",
                        closeable: true,
                        resizable: true,
                        dockable: false,
                        resizeAxis: 'xy'
                    },
                    floaterDiv);
                tmp.startup();
                tmp.resize({w: 350, h: 220});
                style.set(tmp.domNode, "top", "100px");
                style.set(tmp.domNode, "left", "100px");
                style.set(tmp.domNode, "z-index", "500");
                tmp.show();
                request("about.html").then(function (response) {
                        textarea.innerHTML = response;
                    },
                    function (response) {
                        alert("get it");
                        alert("出现 ，原因是" + response);
                    });
                tmp.bringToTop;
            });
    }

    return {

        mehtod: test,
        btnClick: btnClick
    }
});
