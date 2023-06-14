// 引入jquery
import $ from 'jquery'
// console.log($("[group2=1]").length)
export const checkMe2 = (thiss, level) => {
	
	if(level==1 || level==2 || level==3){
		if(level==1){
			let group2 = thiss.attr("checkAll1");
			if(thiss.hasClass("checked")){
				thiss.removeClass("checked");
				thiss.attr("src","resources/images/iben/1.png");
				$("[group2='"+group2+"']").each(function(){
					var group3 = $(this).attr("checkAll2");
					$("[group2='"+group2+"']").removeClass("checked");
					$("[group2='"+group2+"']").attr("src","resources/images/iben/1.png");
					$("[group3='"+group3+"']").removeClass("checked");
					$("[group3='"+group3+"']").attr("src","resources/images/iben/1.png");
				});
			}else{
				thiss.addClass("checked");
				thiss.attr("src","resources/images/iben/2.png");
				$("[group2='"+group2+"']").each(function(){
					var group3 = $(this).attr("checkAll2");
					$("[group2='"+group2+"']").addClass("checked");
					$("[group2='"+group2+"']").attr("src","resources/images/iben/2.png");
					$("[group3='"+group3+"']").addClass("checked");
					$("[group3='"+group3+"']").attr("src","resources/images/iben/2.png");
				});
			}
		}else if(level==2){
			if(thiss.hasClass("checked")){
				thiss.removeClass("checked");
				thiss.attr("src","resources/images/iben/1.png");
			}else{
				thiss.addClass("checked");
				thiss.attr("src","resources/images/iben/2.png");
			}
			let group2 = thiss.attr("group2");
			let checkAll2 = false;
			$("[group2='"+group2+"']").each(function(index){
				if($(this).hasClass('checked')){
					checkAll2 = true;
					return false;//跳出循环
				}
			});
			if(checkAll2){
				$("[checkAll1='"+group2+"']").addClass("checked");
				$("[checkAll1='"+group2+"']").attr("src","resources/images/iben/2.png");
			}else{
				$("[checkAll1='"+group2+"']").removeClass("checked");
				$("[checkAll1='"+group2+"']").attr("src","resources/images/iben/1.png");
			}
			var group3 = thiss.attr("checkAll2");
			if(thiss.hasClass("checked")){
				$("[group3='"+group3+"']").addClass("checked");
				$("[group3='"+group3+"']").attr("src","resources/images/iben/2.png");
			}else{
				$("[group3='"+group3+"']").removeClass("checked");
				$("[group3='"+group3+"']").attr("src","resources/images/iben/1.png");
			}
		}else if(level==3){
			if(thiss.hasClass("checked")){
				thiss.removeClass("checked");
				thiss.attr("src","resources/images/iben/1.png");
			}else{
				thiss.addClass("checked");
				thiss.attr("src","resources/images/iben/2.png");
			}
			let group3 = thiss.attr("group3");
			let checkAll3 = false;
			$("[group3='"+group3+"']").each(function(index){
				if($(this).hasClass('checked')){
					checkAll3 = true;
					return false;//跳出循环
				}
			});
			if(checkAll3){
				$("[checkAll2='"+group3+"']").addClass("checked");
				$("[checkAll2='"+group3+"']").attr("src","resources/images/iben/2.png");
			}else{
				$("[checkAll2='"+group3+"']").removeClass("checked");
				$("[checkAll2='"+group3+"']").attr("src","resources/images/iben/1.png");
			}

			let group2 = $("[checkAll2='"+group3+"']").attr("group2");
			let checkAll2 = false;
			$("[group2='"+group2+"']").each(function(){
				if($(this).hasClass('checked')){
					checkAll2 = true;
					return false;//跳出循环
				}
			});
			
			if(checkAll2){
				$("[checkAll1='"+group2+"']").addClass("checked");
				$("[checkAll1='"+group2+"']").attr("src","resources/images/iben/2.png");
			}else{
				$("[checkAll1='"+group2+"']").removeClass("checked");
				$("[checkAll1='"+group2+"']").attr("src","resources/images/iben/1.png");
			}
		}
	}
}
export  default{
	checkMe2
}