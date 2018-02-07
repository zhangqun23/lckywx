//分页插件
/**
2014-08-05 ch
**/
(function($){
	var ms = {
		init:function(obj,args){
			return (function(){
				ms.fillHtml(obj,args);
				ms.bindEvent(obj,args);
			})();
		},
		//填充html
		fillHtml:function(obj,args){
			return (function(){
				obj.empty();
				//上一页
				if(args.current > 1){
					obj.append('<a href="javascript:;" class="prevPage">上一页</a>');
				}else{
					obj.remove('.prevPage');
					obj.append('<span class="disabled">上一页</span>');
				}
				//中间页码
				if(args.current != 1 && args.current >= 4 && args.pageCount != 4){
					obj.append('<a href="javascript:;" class="tcdNumber">'+1+'</a>');
				}
				if(args.current-2 > 2 && args.current <= args.pageCount && args.pageCount > 5){
					obj.append('<span>...</span>');
				}
				var start = args.current -2,end = args.current+2;
				if((start > 1 && args.current < 4)||args.current == 1){
					end++;
				}
				if(args.current > args.pageCount-4 && args.current >= args.pageCount){
					start--;
				}
				for (;start <= end; start++) {
					if(start <= args.pageCount && start >= 1){
						if(start != args.current){
							obj.append('<a href="javascript:;" class="tcdNumber">'+ start +'</a>');
						}else{
							obj.append('<span class="current">'+ start +'</span>');
						}
					}
				}
				if(args.current + 2 < args.pageCount - 1 && args.current >= 1 && args.pageCount > 5){
					obj.append('<span>...</span>');
				}
				if(args.current != args.pageCount && args.current < args.pageCount -2  && args.pageCount != 4){
					obj.append('<a href="javascript:;" class="tcdNumber">'+args.pageCount+'</a>');
				}
				//下一页
				if(args.current < args.pageCount){
					obj.append('<a href="javascript:;" class="nextPage">下一页</a>');
				}else{
					obj.remove('.nextPage');
					obj.append('<span class="disabled">下一页</span>');
				}
			})();
		},
		//绑定事件
		bindEvent:function(obj,args){
			return (function(){
				obj.on("click","a.tcdNumber",function(){
					var current = parseInt($(this).text());
					ms.fillHtml(obj,{"current":current,"pageCount":args.pageCount});
					if(typeof(args.backFn)=="function"){
						args.backFn(current);
					}
				});
				//上一页
				obj.on("click","a.prevPage",function(){
					var current = parseInt(obj.children("span.current").text());
					ms.fillHtml(obj,{"current":current-1,"pageCount":args.pageCount});
					if(typeof(args.backFn)=="function"){
						args.backFn(current-1);
					}
				});
				//下一页
				obj.on("click","a.nextPage",function(){
					var current = parseInt(obj.children("span.current").text());
					ms.fillHtml(obj,{"current":current+1,"pageCount":args.pageCount});
					if(typeof(args.backFn)=="function"){
						args.backFn(current+1);
					}
				});
			})();
		}
	}
	$.fn.createPage = function(options){
		var args = $.extend({
			pageCount : 10,
			current : 1,
			backFn : function(){}
		},options);
		ms.init(this,args);
	}
})(jQuery);
/*jQuery数字加减插件*/
;(function ($) {
	  $.fn.spinner = function (opts) {
	    return this.each(function () {
	      var defaults = {value:1, min:0}
	      var options = $.extend(defaults, opts)
	      var keyCodes = {up:38, down:40}
	      var container = $('<div></div>')
	      container.addClass('spinner1')
	      var textField = $(this).addClass('value').attr('maxlength', '2').val(options.value)
	        .bind('keyup paste change', function (e) {
	          var field = $(this)
	          if (e.keyCode == keyCodes.up) changeValue(1)
	          else if (e.keyCode == keyCodes.down) changeValue(-1)
	          else if (getValue(field) != container.data('lastValidValue')) validateAndTrigger(field)
	        })
	      textField.wrap(container)

	      var increaseButton = $('<button class="increase">+</button>').click(function () { changeValue(1) })
	      var decreaseButton = $('<button class="decrease">-</button>').click(function () { changeValue(-1) })

	      validate(textField)
	      container.data('lastValidValue', options.value)
	      textField.before(decreaseButton)
	      textField.after(increaseButton)

	      function changeValue(delta) {
	        textField.val(getValue() + delta)
	        validateAndTrigger(textField)
	      }

	      function validateAndTrigger(field) {
	        clearTimeout(container.data('timeout'))
	        var value = validate(field)
	        if (!isInvalid(value)) {
	          textField.trigger('update', [field, value])
	        }
	      }

	      function validate(field) {
	        var value = getValue()
	        if (value <= options.min) decreaseButton.attr('disabled', 'disabled')
	        else decreaseButton.removeAttr('disabled')
	        field.toggleClass('invalid', isInvalid(value)).toggleClass('passive', value === 0)

	        if (isInvalid(value)) {
	          var timeout = setTimeout(function () {
	            textField.val(container.data('lastValidValue'))
	            validate(field)
	          }, 500)
	          container.data('timeout', timeout)
	        } else {
	          container.data('lastValidValue', value)
	        }
	        return value
	      }

	      function isInvalid(value) { return isNaN(+value) || value < options.min; }

	      function getValue(field) {
	        field = field || textField;
	        return parseInt(field.val() || 0, 10)
	      }
	    })
	  }
	})(jQuery)
