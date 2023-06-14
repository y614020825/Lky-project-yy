//单击多选框
// function check(thiss, isNotFirst){
	
// }
const checkMe = function (thiss, isNotFirst) {
	var thissCheckAll = thiss.attr("checkAll");
	var thissGroup = thiss.attr("group");

	if(!isNotFirst){//全选
		if(thiss.hasClass("ivu-checkbox-wrapper-checked")){
			thiss.removeClass("ivu-checkbox-wrapper-checked");
			thiss.children().removeClass("ivu-checkbox-checked");
		}else{
			thiss.addClass("ivu-checkbox-wrapper-checked");
			thiss.children().addClass("ivu-checkbox-checked");
		}
	}

//	if(thiss.prop('checked')){//全选
	if(thiss.hasClass("ivu-checkbox-wrapper-checked")){//全选
		if(thissCheckAll){
			if(!isNotFirst){//全选
				console.log(thiss.text() + "["+thissCheckAll+"]全选")
			}
			
	//		$("[group='"+thissCheckAll+"']").prop("checked", true);
			$("[group='"+thissCheckAll+"']").addClass("ivu-checkbox-wrapper-checked");
			$("[group='"+thissCheckAll+"']").children().addClass("ivu-checkbox-checked");
			
			$("[group='"+thissCheckAll+"']").each(function(){
				checkMe($(this), true);
			});
		}
		//选中所有父级
		checkParent(thissGroup);
	}else{
		if(thissCheckAll){
			if(!isNotFirst){//全选
				console.log(thiss.text() + "["+thissCheckAll+"]反选")
			}
			
	//		$("[group='"+thissCheckAll+"']").prop("checked", false);
			$("[group='"+thissCheckAll+"']").removeClass("ivu-checkbox-wrapper-checked");
			$("[group='"+thissCheckAll+"']").children().removeClass("ivu-checkbox-checked");
			
			$("[group='"+thissCheckAll+"']").each(function(){
				checkMe($(this), true);
			});
		}
		
		if(thissGroup){
			//取消选择其父级
			if(!isNotFirst){
				unCheckParent(thissGroup);
			}
		}
	}
}

//选中所有父级
function checkParent(thissGroup){
//	$("[checkAll='"+thissGroup+"']").prop("checked", true);
	$("[checkAll='"+thissGroup+"']").addClass("ivu-checkbox-wrapper-checked");
	$("[checkAll='"+thissGroup+"']").children().addClass("ivu-checkbox-checked");
	
	if($("[checkAll='"+thissGroup+"']").attr("group")){
		checkParent($("[checkAll='"+thissGroup+"']").attr("group"));
	}
}

//取消选中父级
function unCheckParent(thissGroup){
//	if($("[group='"+thissGroup+"']:checked").length == 0 && $("[checkAll='"+thissGroup+"']").length > 0){
	if($("[group='"+thissGroup+"'].ivu-checkbox-wrapper-checked").length == 0 && $("[checkAll='"+thissGroup+"']").length > 0){
		var parent = $("[checkAll='"+thissGroup+"']");
		if(parent.length > 0){
			
//			parent.prop("checked", false);
			parent.removeClass("ivu-checkbox-wrapper-checked");
			parent.children().removeClass("ivu-checkbox-checked");
			
			unCheckParent(parent.attr("group"));
		}
	}
}

//获取选中的id
function getSelectIds(id){
	var ids = [];
	$("#" + id + " .ivu-checkbox-wrapper-checked").each(function(index){
		//ids += index==0?$(this).attr("value"):(","+$(this).attr("value"));
		// ids += "&pmids="+$(this).attr("value");
		if($(this).attr("value")){
			ids.push($(this).attr("value"));
		}
	});
	return ids;
}

const checkMe2 = function (thiss, isNotFirst) {
	console.log(thiss);

	var thissCheckAll = thiss.attr("checkAll");
	var thissGroup = thiss.attr("group");

	if(!isNotFirst){//全选
		if(thiss.hasClass("ivu-checkbox-wrapper-checked")){
			thiss.removeClass("ivu-checkbox-wrapper-checked");
			thiss.children().removeClass("ivu-checkbox-checked");
		}else{
			thiss.addClass("ivu-checkbox-wrapper-checked");
			thiss.children().addClass("ivu-checkbox-checked");
		}
	}

//	if(thiss.prop('checked')){//全选
	if(thiss.hasClass("ivu-checkbox-wrapper-checked")){//全选
		if(thissCheckAll){
			if(!isNotFirst){//全选
				console.log(thiss.text() + "["+thissCheckAll+"]全选")
			}
			
	//		$("[group='"+thissCheckAll+"']").prop("checked", true);
			$("[group='"+thissCheckAll+"']").addClass("ivu-checkbox-wrapper-checked");
			$("[group='"+thissCheckAll+"']").children().addClass("ivu-checkbox-checked");
			
			$("[group='"+thissCheckAll+"']").each(function(){
				checkMe2($(this), true);
			});
		}
		//若其兄弟全部选中，则选中父级
		checkParent2(thissGroup);
	}else{
		if(thissCheckAll){
			if(!isNotFirst){//全选
				console.log(thiss.text() + "["+thissCheckAll+"]反选")
			}
	//		$("[group='"+thissCheckAll+"']").prop("checked", false);
			$("[group='"+thissCheckAll+"']").removeClass("ivu-checkbox-wrapper-checked");
			$("[group='"+thissCheckAll+"']").children().removeClass("ivu-checkbox-checked");
			
			$("[group='"+thissCheckAll+"']").each(function(){
				checkMe2($(this), true);
			});
		}
		if(thissGroup){
			//取消选择其父级
			if(!isNotFirst){
				unCheckParent2(thissGroup);
			}
		}
	}
}


//选中所有父级
function checkParent2(thissGroup){
	var ischeckParent = true;
	//若其兄弟全部选中，则选中父级
	$("[group='"+thissGroup+"']").each(function(){
		if(!$(this).hasClass("ivu-checkbox-wrapper-checked")){
			ischeckParent = false;
		}
	});
	if(ischeckParent){
		var parent = $("[checkAll='"+thissGroup+"']");
		parent.addClass("ivu-checkbox-wrapper-checked");
		parent.children().addClass("ivu-checkbox-checked");
		console.log("选中" + thissGroup + "的父级" + parent.text());
		var nextGroup = parent.attr("group");
		if(nextGroup){
			console.log("选中下一个父级：" + nextGroup);
			checkParent2(nextGroup);
		}
	}
}

//取消选择父级
function unCheckParent2(thissGroup){
	var parent = $("[checkAll='"+thissGroup+"']");
	if(parent.length > 0){
		parent.removeClass("ivu-checkbox-wrapper-checked");
		parent.children().removeClass("ivu-checkbox-checked");
		var nextGroup = parent.attr("group");
		console.log("取消选中下一个父级：" + nextGroup);
		if(nextGroup){
			unCheckParent2(parent.attr("group"));
		}
	}
}

//获取选中的id
function getSelectIdsByElement(element){
	var ids = [];
	element.each(function(index){
		if($(this).attr("value")){
			ids.push($(this).attr("value"));
		}
	});
	return ids;
}

export {
	checkMe, checkMe2, getSelectIds, getSelectIdsByElement
}