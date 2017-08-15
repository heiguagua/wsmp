<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<form id="important-monitor-form" class="form-horizontal">
	<div class="form-box-wrap">
		<%-- <div class="form-group col-sm-6">
			<label for="" class="col-sm-3 control-label">开始时间</label>
			<div class="col-sm-9">
				<div class="input-group date time-picker" id="startTime">
					<input name="beginTime" type="text" class=" date-control"
						value="${dto.getBeginTime()}"> <span
						class="input-group-addon"> <img src="images/date.png" /></span>
				</div>
			</div>
		</div> --%>
		 <div class="form-group col-sm-6">
			<label for="" class="col-xs-3 control-label">开始时间</label>
		        <div class=' input-group date time-picker' id='startTime' style="padding-left:15px">
		          <input name="beginTime" type='text' class="form-control " value="${dto.getBeginTime()}" />
		          <span class="input-group-addon">
		            <span class="glyphicon glyphicon-calendar"></span>
		          </span>
		        </div> 
		</div> 
		 <div class="form-group col-sm-6">
			<label for="" class="col-xs-3 control-label">结束时间</label>
		        <div class='input-group date time-picker' id='endTime' style="padding-left:15px">
		          <input name="endTime" type='text' class="form-control " value="${dto.getEndTime()}"/>
		          <span class="input-group-addon">
		            <span class="glyphicon glyphicon-calendar"></span>
		          </span>
		        </div> 
		</div> 
	 		   
		<%-- <div class="form-group col-sm-6">
			<label for="" class="col-sm-3 control-label">结束时间</label>
			<div class="col-sm-9">
				<div class="input-group date time-picker" id="endTime">
					<input name="endTime" type="text" class="date-control "
						value="${dto.getEndTime()}"> <span
						class="input-group-addon"> <img src="images/date.png" /></span>
				</div>
			</div>
		</div> --%>
		<div class="form-group col-sm-6">
			<label for="" class="col-xs-3 control-label">循环周期</label>
			<div class="col-xs-9">
				<div class="input-group">
					<input name="cycleStep" value="${dto.getCycleStep()}" type="text"
						class="form-control " placeholder="单位(分钟)">
				</div>
			</div>
		</div>
		<div class="form-group col-sm-6">
			<label for="" class="col-xs-3 control-label">执行时长</label>
			<div class="col-xs-9">
				<div class="input-group">
					<input name="duration" value="${dto.getDuration()}" type="text"
						class="form-control " placeholder="单位(秒)">
				</div>
			</div>
		</div>
	</div>

	<div class="col-sm-6 box-title-wrap">
		<label for="" class="col-xs-6 control-label"><span
			class="wrap-title">参数</span></label>
	</div>
	<div class="col-sm-6 box-title-wrap">
		<label for="" class="col-xs-6 control-label"><span
			class="wrap-title">采集次数</span></label>
	</div>
	<div class="form-box-wrap form-box-down">
		<div class="form-group col-sm-6">
			<label for="" class="col-xs-3 control-label">IQ帧数</label>
			<div class="col-xs-9">
				<div class="input-group">
					<input name="IQCount" value="${dto.getIQCount()}" type="text"
						class="form-control " placeholder="单位(次)">
				</div>
			</div>
		</div>
		<div class="form-group col-sm-6">
			<div class="col-xs-6 padding_0_8">
				<div class="input-group">
					<input name="totalIQCount" type="text"
						value="${dto.getTotalIQCount()}" class="form-control "
						placeholder="单位(次)">
				</div>
			</div>
		</div>
		<div class="form-group col-sm-6">
			<label for="" class="col-xs-3 control-label">频谱帧数</label>
			<div class="col-xs-9">
				<div class="input-group">
					<input name="specCount" value="${dto.getSpecCount()}" type="text"
						class="form-control " placeholder="单位(次)">
				</div>
			</div>
		</div>
		<div class="form-group col-sm-6">
			<div class="col-xs-6 padding_0_8">
				<div class="input-group">
					<input name="totalSpecCount" value="${dto.getTotalSpecCount()}"
						type="text" class="form-control " placeholder="单位(次)">
				</div>
			</div>
		</div>
		<div class="form-group col-sm-6">
			<label for="" class="col-xs-3 control-label">特征帧数</label>
			<div class="col-xs-9">
				<div class="input-group">
					<input name="featureCount" value="${dto.getFeatureCount()}"
						type="text" class="form-control " placeholder="单位(次)">
				</div>
			</div>
		</div>
		<div class="form-group col-sm-6">
			<div class="col-xs-6 padding_0_8">
				<div class="input-group">
					<input name="totalFeatureCount"
						value="${dto.getTotalFeatureCount()}" type="text"
						class="form-control " placeholder="单位(次)">
				</div>
			</div>
		</div>
		<div class="form-group col-sm-6">
			<label for="" class="col-xs-3 control-label">ITU帧数</label>
			<div class="col-xs-9">
				<div class="input-group">
					<input name="ITUCount" value="${dto.getITUCount()}" type="text"
						class="form-control " placeholder="单位(次)">
				</div>
			</div>
		</div>
		<div class="form-group col-sm-6">
			<div class="col-xs-6 padding_0_8">
				<div class="input-group">
					<input name="totalITUCount" value="${dto.getTotalITUCount()}"
						type="text" class="form-control " placeholder="单位(次)">
				</div>
			</div>
		</div>
		<div class="form-group col-sm-6">
			<label for="" class="col-xs-3 control-label">声音时间</label>
			<div class="col-xs-9">
				<div class="input-group">
					<input name="audioTimespan" value="${dto.getAudioTimespan()}"
						type="text" class="form-control " placeholder="单位(秒)">
				</div>
			</div>
		</div>
		<div class="form-group col-sm-6">
			<div class="col-xs-6 padding_0_8">
				<div class="input-group">
					<input name="totalAudioTimespan"
						value="${dto.getTotalAudioTimespan()}" type="text"
						class="form-control " placeholder="单位(次)">
				</div>
			</div>
		</div>
	</div>

	<div class="form-group ">
		<div class="col-sm-12 mrg-top15 text-right">
			<button type="button" id="buttonInsert" class="btn btn-default btn-apply">添加</button>
		</div>
	</div>


	<input name="ID" value="${dto.getID()}" hidden="true"> 
	<input name="freqRange" value="${dto.isFreqRange()}" hidden="true">
	<input name="beginFreq" value="${dto.getBeginFreq()}" hidden="true">
	<input name="endFreq" value="${dto.getEndFreq()}" hidden="true">
</form>

			