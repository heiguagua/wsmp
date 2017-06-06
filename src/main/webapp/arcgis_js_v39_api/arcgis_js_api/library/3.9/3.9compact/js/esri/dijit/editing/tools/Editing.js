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
define("esri/dijit/editing/tools/Editing","dojo/_base/declare dojo/_base/lang dojo/_base/array dojo/has esri/layers/FeatureTemplate esri/dijit/editing/tools/Edit esri/dijit/editing/tools/EditingTools esri/dijit/editing/tools/DropDownToolBase esri/kernel esri/lang".split(" "),function(d,f,e,m,n,p,l,q,r,s){d=d([q],{declaredClass:"esri.dijit.editing.tools.Editing",_enabled:!1,deactivate:function(){this._enabled&&(this._enabled=!1,this.inherited(arguments),this._settings.templatePicker.clearSelection())},
onItemClicked:function(a,b){this.inherited(arguments);this._activeTool===this._tools.AUTOCOMPLETE&&(this._settings.editor._drawingTool=n.TOOL_AUTO_COMPLETE_POLYGON)},_activateTool:function(a,b){var c;this.enable(b);for(c in this._tools)this._tools.hasOwnProperty(c)&&(this.dropDown.removeChild(this._tools[c]),!0===this._tools[c]._enabled&&this.dropDown.addChild(this._tools[c]));if(this._geometryType!==b||!1===this._activeTool._enabled)this._activeTool=this._tools[a.toUpperCase()];this._geometryType=
b;this._activeTool.activate();this._activeTool.setChecked(!0);this._updateUI()},_initializeTool:function(a){this.inherited(arguments);this._initializeTools()},_initializeTools:function(){var a=this._settings.layers,b=this._settings.editor,c=!1,d=!1,h=!1,k=this._toolTypes=[],g;e.forEach(a,function(a){g=a.geometryType;c=c||"esriGeometryPoint"===g;d=d||"esriGeometryPolyline"===g;h=h||"esriGeometryPolygon"===g;k=k.concat(e.map(this._getTemplatesFromLayer(a),f.hitch(this,function(c){return b._toDrawTool(c.drawingTool,
a)})))},this);a=this._settings.createOptions;c&&this._toolTypes.push("point");d&&(this._toolTypes=this._toolTypes.concat(a.polylineDrawTools));h&&(this._toolTypes=this._toolTypes.concat(a.polygonDrawTools));this._toolTypes=this._toUnique(this._toolTypes.concat(k))},_toUnique:function(a){var b={};return e.filter(a,function(a){return b[a]?!1:b[a]=!0})},_getTemplatesFromLayer:function(a){var b=a.templates||[];e.forEach(a.types,function(a){b=b.concat(a.templates)});return e.filter(b,s.isDefined)},_createTools:function(){e.forEach(this._toolTypes,
this._createTool,this);this.inherited(arguments)},_createTool:function(a){var b=f.mixin(l[a],{settings:this._settings,onClick:f.hitch(this,"onItemClicked",l[a].id)});this._tools[a.toUpperCase()]=new p(b)}});m("extend-esri")&&f.setObject("dijit.editing.tools.Editing",d,r);return d});