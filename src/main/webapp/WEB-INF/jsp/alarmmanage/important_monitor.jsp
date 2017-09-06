<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<form id="important-monitor-form" class="form-horizontal" name="importantMonitorForm" >
	<div class="form-box-wrap">
		<div class="form-group col-sm-6">
			<label for="" class="col-xs-3 control-label">开始时间</label>
			<div class=' input-group date time-picker'  style="padding-left:15px">
				<input title="不能为空" id='startTime' required name="beginTime" type='text' class="form-control " value="${dto.getBeginTime()}" />
		          <span class="input-group-addon">
		            <span class="glyphicon glyphicon-calendar"></span>
		          </span>
			</div>
		</div>
		<div class="form-group col-sm-6">
			<label for="" class="col-xs-3 control-label">结束时间</label>
			<div class='input-group date time-picker'  style="padding-left:15px">
				<input title="不能为空" id='endTime' required name="endTime" type='text' class="form-control " value="${dto.getEndTime()}"/>
		          <span class="input-group-addon">
		            <span class="glyphicon glyphicon-calendar"></span>
		          </span>
			</div>
		</div>
		<div class="form-group col-sm-6">
			<label for="" class="col-xs-3 control-label">循环周期</label>
			<div class="col-sm-9">
				<div class="input-group">
					<input title="不能为空" required id="cycleStep" name="cycleStep" value="${dto.getCycleStep()}" type="text"
						   class="form-control " placeholder="单位(秒)">
					<span class="input-group-addon unit ">秒</span>

				</div>
			</div>
		</div>
		<div class="form-group col-sm-6">
			<label for="" class="col-xs-3 control-label">执行时长</label>
			<div class="col-xs-9">
				<div class="input-group">
					<input title="不能为空" required id="duration" name="duration" value="${dto.getDuration()}" type="text"
						   class="form-control " placeholder="单位(秒)">
					<span class="input-group-addon unit">秒</span>
				</div>
			</div>
		</div>
	</div>

	<div class="col-sm-6 box-title-wrap">
		<label for="" class="col-xs-6 control-label"><span
				class="wrap-title">单次采集数</span></label>
	</div>
	<div class="col-sm-6 box-title-wrap">
		<label for="" class="col-xs-6 control-label"><span
				class="wrap-title">采集总数</span></label>
	</div>
	<div class="form-box-wrap form-box-down">
		<div class="form-group col-sm-6">
			<label for="" class="col-xs-3 control-label">IQ帧数</label>
			<div class="col-xs-9">
				<div class="input-group">
					<input title="不能为空" required id ="IQCount" name="IQCount" value="${dto.getIQCount()}" type="text"
						   class="form-control " placeholder="单位(帧)">
					<span class="input-group-addon unit">帧</span>
				</div>
			</div>
		</div>
		<div class="form-group col-sm-6">
			<div class="col-xs-6 padding_0_8">
				<div class="input-group">
					<input title="不能为空" id="totalIQCount" required name="totalIQCount" type="text"
						   value="${dto.getTotalIQCount()}" class="form-control "
						   placeholder="单位(帧)">
					<span class="input-group-addon unit">帧</span>
				</div>
			</div>
		</div>
		<div class="form-group col-sm-6">
			<label for="" class="col-xs-3 control-label">频谱帧数</label>
			<div class="col-xs-9">
				<div class="input-group">
					<input title="不能为空" required id="specCount"name="specCount" value="${dto.getSpecCount()}" type="text"
						   class="form-control " placeholder="单位(帧)">
					<span class="input-group-addon unit">帧</span>
				</div>
			</div>
		</div>
		<div class="form-group col-sm-6">
			<div class="col-xs-6 padding_0_8">
				<div class="input-group">
					<input title="不能为空" required id="totalSpecCount" name="totalSpecCount" value="${dto.getTotalSpecCount()}"
						   type="text" class="form-control " placeholder="单位(帧)">
					<span class="input-group-addon unit">帧</span>
				</div>
			</div>
		</div>
		<div class="form-group col-sm-6">
			<label for="" class="col-xs-3 control-label">特征帧数</label>
			<div class="col-xs-9">
				<div class="input-group">
					<input title="不能为空" required id="featureCount" name="featureCount" value="${dto.getFeatureCount()}"
						   type="text" class="form-control " placeholder="单位(帧)">
					<span class="input-group-addon unit">帧</span>
				</div>
			</div>
		</div>
		<div class="form-group col-sm-6">
			<div class="col-xs-6 padding_0_8">
				<div class="input-group">
					<input title="不能为空" id="totalFeatureCount" required name="totalFeatureCount"
						   value="${dto.getTotalFeatureCount()}" type="text"
						   class="form-control " placeholder="单位(帧)">
					<span class="input-group-addon unit">帧</span>
				</div>
			</div>
		</div>
		<div class="form-group col-sm-6">
			<label for="" class="col-xs-3 control-label">ITU帧数</label>
			<div class="col-xs-9">
				<div class="input-group">
					<input title="不能为空" required id="ITUCount" name="ITUCount" value="${dto.getITUCount()}" type="text"
						   class="form-control " placeholder="单位(帧)">
					<span class="input-group-addon unit">帧</span>
				</div>
			</div>
		</div>
		<div class="form-group col-sm-6">
			<div class="col-xs-6 padding_0_8">
				<div class="input-group">
					<input title="不能为空" id="totalITUCount "required name="totalITUCount" value="${dto.getTotalITUCount()}"
						   type="text" class="form-control " placeholder="单位(帧)">
					<span class="input-group-addon unit">帧</span>
				</div>
			</div>
		</div>
		<div class="form-group col-sm-6">
			<label for="" class="col-xs-3 control-label">声音时间</label>
			<div class="col-xs-9">
				<div class="input-group">
					<input title="不能为空" id="audioTimespan" required name="audioTimespan" value="${dto.getAudioTimespan()}"
						   type="text" class="form-control " placeholder="单位(秒)">
					<span class="input-group-addon unit">秒</span>
				</div>
			</div>
		</div>
		<div class="form-group col-sm-6">
			<div class="col-xs-6 padding_0_8">
				<div class="input-group">
					<input title="不能为空" id="totalAudioTimespan" required name="totalAudioTimespan"
						   value="${dto.getTotalAudioTimespan()}" type="text"
						   class="form-control " placeholder="单位(秒)">
					<span class="input-group-addon unit">秒</span>
				</div>
			</div>
		</div>
	</div>

	<div class="form-group ">
		<div class="col-sm-9 mrg-top15 text-right">
			<%--<input type="submit" value="更新" id="buttonUpdate" class="btn btn-default btn-apply"/>--%>
			<button type="button" id="buttonUpdate" class="btn btn-default btn-apply">更新</button>
		</div>
		<div class="col-sm-3  mrg-top15 text-left">
			<%--<input type="submit" value="删除" id="buttonDelete" class="btn btn-default btn-apply"/>--%>
			<button type="button" id="buttonDelete" class="btn btn-default btn-apply">删除</button>
		</div>
	</div>


	<input name="ID" value="${dto.getID()}" hidden="true"> 
	<input name="warnID" value="${dto.getWarnID()}" hidden="true">
	<input name="freqRange" value="${dto.isFreqRange()}" hidden="true">
	<input name="beginFreq" value="${dto.getBeginFreq()}" hidden="true">
	<input name="endFreq" value="${dto.getEndFreq()}" hidden="true">
</form>

			