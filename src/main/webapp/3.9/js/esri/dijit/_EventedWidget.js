/*
 COPYRIGHT 2009 ESRI

 TRADE SECRETS: ESRI PROPRIETARY AND CONFIDENTIAL
 Unpublished material - all rights reserved under the
 Copyright Laws of the United States and applicable international
 laws, treaties, and conventions.

 For additional information, contact:
 Environmental Systems Research Institute, Inc.
 Attn: Contracts and Legal Services Department
 380 New York Street
 Redlands, California, 92373
 USA

 email: contracts@esri.com
 */
//>>built
define("esri/dijit/_EventedWidget","dojo/_base/declare dojo/_base/lang dojo/aspect dojo/on esri/Evented dijit/_WidgetBase".split(" "),function(k,l,q,m,n,p){return k([p,n],{_onMap:function(b){var a=this.constructor._onMap,c;if(!a||!a.FINAL)delete this.constructor._onMap,a=this.registerConnectEvents(),a.FINAL=!0;b=b.toLowerCase();a[b]?c=this[a[b].method]:(b=this._onCamelCase(b),this[b]&&(c=b));return c},on:function(b,a){var c=this._onMap(b),d=b.replace(/\-/g,""),e="on"+d in this.domNode;return c||!e?
this.inherited(arguments):this.own(m(this.domNode,d,a))[0]},emit:function(b,a,c){var d,e,f,g=b.toLowerCase(),h=this.constructor._onMap||this.registerConnectEvents();e=this[this._onMap(g)];a=a||{};a.target||(a.target=this);e&&(h&&h[g])&&(this._onObj2Arr(function(){d=Array.prototype.slice.call(arguments)},h[g].argKeys)(a),f=l.mixin({},arguments),f[2]=d,f[0]=h[g].name.replace(/^on/,""));return this.inherited(f||arguments)}})});