//>>built
define("dojox/lang/aspect/profiler",["dijit","dojo","dojox"],function(f,b,d){b.provide("dojox.lang.aspect.profiler");(function(){var e=d.lang.aspect,c=function(a){this.args=a?[a]:[];this.inCall=0};b.extend(c,{before:function(){this.inCall++||console.profile.apply(console,this.args)},after:function(){--this.inCall||console.profileEnd()}});e.profiler=function(a){return new c(a)}})()});