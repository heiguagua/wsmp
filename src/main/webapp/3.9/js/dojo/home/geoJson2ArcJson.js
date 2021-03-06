/**
 * Created by wuhaoran on 2017/2/25.
 */
//
define(["jquery"],function(jquery){
    //var map = null;
    //config.defaults.io.corsEnabledServers.push("192.168.13.79:7080");
    function pares() {
    	  var gCon = {};  
    	  
    	    /*compares a GeoJSON geometry type and ESRI geometry type to see if they can be safely 
    	     put together in a single ESRI feature. ESRI features must only have one 
    	     geometry type, point, line, polygon*/  
    	    function isCompatible(esriGeomType, gcGeomType) {  
    	        var compatible = false;  
    	        if ((esriGeomType === "esriGeometryPoint" || esriGeomType === "esriGeometryMultipoint") && (gcGeomType === "Point" || gcGeomType === "MultiPoint")) {  
    	            compatible = true;  
    	        } else if (esriGeomType === "esriGeometryPolyline" && (gcGeomType === "LineString" || gcGeomType === "MultiLineString")) {  
    	            compatible = true;  
    	        } else if (esriGeomType === "esriGeometryPolygon" && (gcGeomType === "Polygon" || gcGeomType === "MultiPolygon")) {  
    	            compatible = true;  
    	        }  
    	        return compatible;  
    	    }  
    	  
    	    /*Take a GeoJSON geometry type and make an object that has information about 
    	     what the ESRI geometry should hold. Includes the ESRI geometry type and the name 
    	     of the member that holds coordinate information*/  
    	    function gcGeomTypeToEsriGeomInfo(gcType) {  
    	        var esriType,  
    	            geomHolderId;  
    	        if (gcType === "Point") {  
    	            esriType = "esriGeometryPoint";  
    	        } else if (gcType === "MultiPoint") {  
    	            esriType = "esriGeometryMultipoint";  
    	            geomHolderId = "points";  
    	        } else if (gcType === "LineString" || gcType === "MultiLineString") {  
    	            esriType = "esriGeometryPolyline";  
    	            geomHolderId = "paths";  
    	        } else if (gcType === "Polygon" || gcType === "MultiPolygon") {  
    	            esriType = "esriGeometryPolygon";  
    	            geomHolderId = "rings";  
    	        }  
    	        return {  
    	            type: esriType,  
    	            geomHolder: geomHolderId  
    	        };  
    	    }  
    	  
    	    /*Convert GeoJSON polygon coordinates to ESRI polygon coordinates. 
    	     GeoJSON rings are listed starting with a singular outer ring. ESRI 
    	     rings can be listed in any order, but unlike GeoJSON, the ordering of 
    	     vertices determines whether it's an outer or inner ring. Clockwise 
    	     vertices indicate outer ring and counter-clockwise vertices indicate 
    	     inner ring */  
    	    function gcPolygonCoordinatesToEsriPolygonCoordinates(gcCoords) {  
    	        var i,  
    	            len,  
    	            esriCoords = [],  
    	            ring;  
    	        for (i = 0, len = gcCoords.length; i < len; i++) {  
    	            ring = gcCoords[i];  
    	            // Exclusive OR.  
//    	            if ((i === 0) !== ringIsClockwise(ring)) {  
//    	                ring = ring.reverse();  
//    	            }  
    	            esriCoords.push(ring);  
    	        }  
    	        return esriCoords;  
    	    }  
    	  
    	    /*Wraps GeoJSON coordinates in an array if necessary so code can iterate 
    	     through array of points, rings, or lines and add them to an ESRI geometry 
    	     Input is a GeoJSON geometry object. A GeoJSON GeometryCollection is not a 
    	     valid input */  
    	    function gcCoordinatesToEsriCoordinates(gcGeom) {  
    	        var i,  
    	            len,  
    	            esriCoords;  
    	        if (gcGeom.type === "MultiPoint" || gcGeom.type === "MultiLineString") {  
    	            esriCoords = gcGeom.coordinates || [];  
    	        } else if (gcGeom.type === "Point" || gcGeom.type === "LineString") {  
    	            esriCoords = gcGeom.coordinates ? [gcGeom.coordinates] : [];  
    	        } else if (gcGeom.type === "Polygon") {  
    	            esriCoords = [];  
    	            if(gcGeom.coordinates){  
    	                esriCoords = gcPolygonCoordinatesToEsriPolygonCoordinates(gcGeom.coordinates);  
    	            }  
    	        } else if (gcGeom.type === "MultiPolygon") {  
    	            esriCoords = [];  
    	            if(gcGeom.coordinates){  
    	                for (i = 0, len = gcGeom.coordinates.length; i < len; i++) {  
    	                    esriCoords.push(gcPolygonCoordinatesToEsriPolygonCoordinates(gcGeom.coordinates[i]));  
    	                }  
    	            }  
    	        }  
    	        return esriCoords;  
    	    }  
    	  
    	    /*Converts GeoJSON geometry to ESRI geometry. The ESRI geometry is 
    	     only allowed to contain one type of geometry, so if the GeoJSON 
    	     geometry is a GeometryCollection, then only geometries compatible 
    	     with the first geometry type in the collection are added to the ESRI geometry 
    	 
    	     Input parameter is a GeoJSON geometry object.*/  
    	    function gcGeometryToEsriGeometry(gcGeom) {  
    	        var esriGeometry,  
    	            esriGeomInfo,  
    	            gcGeometriesToConvert,  
    	            i,  
    	            g,  
    	            coords;  
    	  
    	        //if geometry collection, get info about first geometry in collection  
    	        if (gcGeom.type === "GeometryCollection") {  
    	            var geomCompare = gcGeom.geometries[0];  
    	            gcGeometriesToConvert = [];  
    	            esriGeomInfo = gcGeomTypeToEsriGeomInfo(geomCompare.type);  
    	  
    	            //loop through collection and only add compatible geometries to the array  
    	            //of geometries that will be converted  
    	            for (i = 0; i < gcGeom.geometries.length; i++) {  
    	                if (isCompatible(esriGeomInfo.type, gcGeom.geometries[i].type)) {  
    	                    gcGeometriesToConvert.push(gcGeom.geometries[i]);  
    	                }  
    	            }  
    	        } else {  
    	            esriGeomInfo = gcGeomTypeToEsriGeomInfo(gcGeom.type);  
    	            gcGeometriesToConvert = [gcGeom];  
    	        }  
    	  
    	        //if a collection contained multiple points, change the ESRI geometry  
    	        //type to MultiPoint  
    	        if (esriGeomInfo.type === "esriGeometryPoint" && gcGeometriesToConvert.length > 1) {  
    	            esriGeomInfo = gcGeomTypeToEsriGeomInfo("MultiPoint");  
    	        }  
    	  
    	        //make new empty ESRI geometry object  
    	        esriGeometry = {  
    	            //type: esriGeomInfo.type,  
    	            spatialReference: {  
    	                wkid: 4326  
    	            }  
    	        };  
    	  
    	        //perform conversion  
    	        if (esriGeomInfo.type === "esriGeometryPoint") {  
    	            if (!gcGeometriesToConvert[0] || !gcGeometriesToConvert[0].coordinates || gcGeometriesToConvert[0].coordinates.length === 0) {  
    	                esriGeometry.x = null;  
    	            } else {  
    	                esriGeometry.x = gcGeometriesToConvert[0].coordinates[0];  
    	                esriGeometry.y = gcGeometriesToConvert[0].coordinates[1];  
    	            }  
    	        } else {  
    	            esriGeometry[esriGeomInfo.geomHolder] = [];  
    	            for (i = 0; i < gcGeometriesToConvert.length; i++) {  
    	                coords = gcCoordinatesToEsriCoordinates(gcGeometriesToConvert[i]);  
    	                for (g = 0; g < coords.length; g++) {  
    	                    esriGeometry[esriGeomInfo.geomHolder].push(coords[g]);  
    	                }  
    	            }  
    	        }  
    	        return esriGeometry;  
    	    }  
    	  
    	    /*Converts GeoJSON feature to ESRI REST Feature. 
    	     Input parameter is a GeoJSON Feature object*/  
    	    function gcFeatureToEsriFeature(gcFeature) {  
    	        var esriFeat,  
    	            prop,  
    	            esriAttribs;  
    	        if (gcFeature) {  
    	            esriFeat = {};  
    	            if (gcFeature.geometry) {  
    	                esriFeat.geometry = gcGeometryToEsriGeometry(gcFeature.geometry);  
    	            }  
    	            if (gcFeature.properties) {  
    	                esriAttribs = {};  
    	                for (prop in gcFeature.properties) {  
    	                    esriAttribs[prop] = gcFeature.properties[prop];  
    	                }  
    	                esriFeat.attributes = esriAttribs;  
    	            }  
    	        }  
    	        return esriFeat;  
    	    }  
    	  
    	    /*Converts GeoJSON FeatureCollection, Feature, or Geometry 
    	     to ESRI Rest Featureset, Feature, or Geometry*/  
    	    gCon.toEsri = function(geoJsonObject) {  
    	        var outObj,  
    	            i,  
    	            gcFeats,  
    	            esriFeat;  
    	        if (geoJsonObject){  
    	            if (geoJsonObject.type === "FeatureCollection"){  
    	                outObj = {  
    	                    features: []  
    	                };  
    	                gcFeats = geoJsonObject.features;  
    	                for (i = 0; i < gcFeats.length; i++) {  
    	                    esriFeat = gcFeatureToEsriFeature(gcFeats[i]);  
    	                    if (esriFeat) {  
    	                        outObj.features.push(esriFeat);  
    	                    }  
    	                }  
    	            }  
    	            else if (geoJsonObject.type === "Feature"){  
    	                outObj = gcFeatureToEsriFeature(geoJsonObject);  
    	            }  
    	            else{  
    	                outObj = gcGeometryToEsriGeometry(geoJsonObject);  
    	            }  
    	        }  
    	        return outObj;  
    	    };  
    	  
    	    return gCon;  
    }
    return {
        init : pares
    }
});
