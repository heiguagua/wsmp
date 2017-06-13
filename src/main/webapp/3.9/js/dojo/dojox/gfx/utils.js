//>>built
define("dojox/gfx/utils","dojo/_base/kernel dojo/_base/lang ./_base dojo/_base/html dojo/_base/array dojo/_base/window dojo/_base/json dojo/_base/Deferred dojo/_base/sniff require dojo/_base/config".split(" "),function(e,k,f,l,g,m,n,p,q,r,s){var d=f.utils={};k.mixin(d,{forEach:function(a,b,c){c=c||e.global;b.call(c,a);(a instanceof f.Surface||a instanceof f.Group)&&g.forEach(a.children,function(a){d.forEach(a,b,c)})},serialize:function(a){var b={},c;if((c=a instanceof f.Surface)||a instanceof f.Group){if(b.children=
g.map(a.children,d.serialize),c)return b.children}else b.shape=a.getShape();if(a.getTransform&&(c=a.getTransform()))b.transform=c;if(a.getStroke&&(c=a.getStroke()))b.stroke=c;if(a.getFill&&(c=a.getFill()))b.fill=c;if(a.getFont&&(c=a.getFont()))b.font=c;return b},toJson:function(a,b){return n.toJson(d.serialize(a),b)},deserialize:function(a,b){if(b instanceof Array)return g.map(b,k.hitch(null,d.deserialize,a));var c="shape"in b?a.createShape(b.shape):a.createGroup();"transform"in b&&c.setTransform(b.transform);
"stroke"in b&&c.setStroke(b.stroke);"fill"in b&&c.setFill(b.fill);"font"in b&&c.setFont(b.font);"children"in b&&g.forEach(b.children,k.hitch(null,d.deserialize,c));return c},fromJson:function(a,b){return d.deserialize(a,n.fromJson(b))},toSvg:function(a){var b=new p;if("svg"===f.renderer)try{var c=d._cleanSvg(d._innerXML(a.rawNode));b.callback(c)}catch(e){b.errback(e)}else{d._initSvgSerializerDeferred||d._initSvgSerializer();var g=d.toJson(a),c=function(){try{var c=a.getDimensions(),e=c.width,f=c.height,
h=d._gfxSvgProxy.document.createElement("div");d._gfxSvgProxy.document.body.appendChild(h);m.withDoc(d._gfxSvgProxy.document,function(){l.style(h,"width",e);l.style(h,"height",f)},this);d._gfxSvgProxy[dojox._scopeName].gfx.createSurface(h,e,f).whenLoaded(null,function(a){try{d._gfxSvgProxy[dojox._scopeName].gfx.utils.fromJson(a,g);var c=d._cleanSvg(h.innerHTML);a.clear();a.destroy();d._gfxSvgProxy.document.body.removeChild(h);b.callback(c)}catch(e){b.errback(e)}})}catch(k){b.errback(k)}};0<d._initSvgSerializerDeferred.fired?
c():d._initSvgSerializerDeferred.addCallback(c)}return b},_gfxSvgProxy:null,_initSvgSerializerDeferred:null,_svgSerializerInitialized:function(){d._initSvgSerializerDeferred.callback(!0)},_initSvgSerializer:function(){if(!d._initSvgSerializerDeferred){d._initSvgSerializerDeferred=new p;var a=m.doc.createElement("iframe");l.style(a,{display:"none",position:"absolute",width:"1em",height:"1em",top:"-10000px"});var b;q("ie")?a.onreadystatechange=function(){"complete"==a.contentWindow.document.readyState&&
(a.onreadystatechange=function(){},b=setInterval(function(){a.contentWindow[e.scopeMap.dojo[1]._scopeName]&&(a.contentWindow[e.scopeMap.dojox[1]._scopeName].gfx&&a.contentWindow[e.scopeMap.dojox[1]._scopeName].gfx.utils)&&(clearInterval(b),a.contentWindow.parent[e.scopeMap.dojox[1]._scopeName].gfx.utils._gfxSvgProxy=a.contentWindow,a.contentWindow.parent[e.scopeMap.dojox[1]._scopeName].gfx.utils._svgSerializerInitialized())},50))}:a.onload=function(){a.onload=function(){};b=setInterval(function(){a.contentWindow[e.scopeMap.dojo[1]._scopeName]&&
(a.contentWindow[e.scopeMap.dojox[1]._scopeName].gfx&&a.contentWindow[e.scopeMap.dojox[1]._scopeName].gfx.utils)&&(clearInterval(b),a.contentWindow.parent[e.scopeMap.dojox[1]._scopeName].gfx.utils._gfxSvgProxy=a.contentWindow,a.contentWindow.parent[e.scopeMap.dojox[1]._scopeName].gfx.utils._svgSerializerInitialized())},50)};var c=s.dojoxGfxSvgProxyFrameUrl||r.toUrl("dojox/gfx/resources/gfxSvgProxyFrame.html");a.setAttribute("src",c.toString());m.body().appendChild(a)}},_innerXML:function(a){return a.innerXML?
a.innerXML:a.xml?a.xml:"undefined"!=typeof XMLSerializer?(new XMLSerializer).serializeToString(a):null},_cleanSvg:function(a){a&&(-1==a.indexOf('xmlns\x3d"http://www.w3.org/2000/svg"')&&(a=a.substring(4,a.length),a='\x3csvg xmlns\x3d"http://www.w3.org/2000/svg"'+a),-1==a.indexOf('xmlns:xlink\x3d"http://www.w3.org/1999/xlink"')&&(a=a.substring(4,a.length),a='\x3csvg xmlns:xlink\x3d"http://www.w3.org/1999/xlink"'+a),-1===a.indexOf("xlink:href")&&(a=a.replace(/href\s*=/g,"xlink:href\x3d")),a=a.replace(/<img\b([^>]*)>/gi,
"\x3cimage $1 /\x3e"),a=a.replace(/\bdojoGfx\w*\s*=\s*(['"])\w*\1/g,""),a=a.replace(/\b__gfxObject__\s*=\s*(['"])\w*\1/g,""),a=a.replace(/[=]([^"']+?)(\s|>)/g,'\x3d"$1"$2'));return a}});return d});