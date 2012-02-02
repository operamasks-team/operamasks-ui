/*
 * $Id: om-slider.js,v 1.23 2012/01/31 09:33:40 chentianzhen Exp $
 * operamasks-ui omSlider @VERSION
 *
 * Copyright 2011, AUTHORS.txt (http://ui.operamasks.org/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://ui.operamasks.org/license
 *
 * http://ui.operamasks.org/docs/
 *
 * Depends:
 */
    /** 
     * @name omSlider
     * @class 用来展示页面中多个HTML元素的滑动器.<br/>
     * <b>特点：</b><br/>
     * <ol>
     * 		<li>以滑动器的方式展示页面中的多个元素，元素的HTML结构不限</li>
     * 		<li>内置控制导航条</li>
     * 		<li>内置多种切换的动画效果</li>
     * 		<li>可自定义导航条的内容和样式</li>
     * </ol>
     * <b>示例：</b><br/>
     * <pre>
     * &lt;script type="text/javascript" &gt;
     * $(document).ready(function() {
     *     $('#slider').omSlider({
     *         animSpeed : 100,
     *         effect : 'slide-v',
     *         onBeforeSlide : function(index){
     *             // do something
     *         }
     *     });
     * });
     * &lt;/script&gt;
     * 
     * &lt;div id="slider" class="slider-demo"&gt;
     *	&lt;img src="images/turtle.jpg" /&gt;
     *	&lt;a href="#"&gt;&lt;img src="images/rabbit.jpg" /&gt;&lt;/a&gt;
     *	&lt;img src="images/penguin.jpg" /&gt;
     *	&lt;img src="images/lizard.jpg" /&gt;
     *	&lt;img src="images/crocodile.jpg" /&gt;
	 * &lt;/div&gt;
	 * </pre>
     * @constructor
     * @description 构造函数. 
     * @param p 标准config对象：{}
     */
(function($) {
	$.fn.omSlider = function(options) {
		var methods = {
	        /**
	         * 切换到指定index的面板。
	         * @name omSlider#slideTo
	         * @function
	         * @param index 面板的索引
	         * @example
	         * //切换到第三个面板
	         * $('#slider').omSlider('slideTo', 2);
	         */
			slideTo : function(index) {
				return this.each(function(){
					opts = $(this).data('omSlider:opts');
					_slideTo($(this),index);
				});
			},
	        /**
	         * 切换到下一个面板。
	         * @name omSlider#next
	         * @function
	         * @example
	         * $('#slider').omSlider('next');
	         */			
			next : function(){
				return this.each(function(){
					opts = $(this).data('omSlider:opts');
					_next($(this));
				});
			},
	        /**
	         * 切换到上一个面板。
	         * @name omSlider#prev
	         * @function
	         * @example
	         * $('#slider').omSlider('prev');
	         */			
			prev : function(){
				return this.each(function(){
					opts = $(this).data('omSlider:opts');
					_prev($(this));
				});
			}
		};
		if (methods[options]) {
			return methods[options].apply(this, Array.prototype.slice.call(arguments, 1));
		} 
		var defaults = /** @lends omSlider#*/{
	        /**
	         * 设置面板是否自动切换。
	         * @default true
	         * @type Boolean
	         * @example
	         * $('#slider').omSlider({autoPlay : false});
	         */
			autoPlay : true,
	        /**
	         * 自动切换间隔时间，只有当autoPlay为true的时候这个属性才有效。
	         * @default 5000
	         * @type Number
	         * @example
	         * $('#slider').omSlider({interval: 1000});//设置slider自动切换的间隔时间为1秒
	         */
			interval : 5000,
			/**
			 * 设置是否需要显示用来切换上一个或下一个面板的方向导航键（设为true时把鼠标移动到slider上面的时候会出现一个悬浮的上一个或下一个的工具条）。
			 * @default false
			 * @type Boolean
			 * @example
			 * $('#slider').omSlider({directionNav : true});
			 */
			directionNav: false,
			/**
			 * 设置当鼠标移动到slider上面的时候是否暂停自动切换。
			 * @default true
			 * @type Boolean
			 * @example
			 * $('#slider').omSlider({pauseOnHover : false});
			 */
			pauseOnHover: true,
			/**
			 * 设置是否需要导航条，当属性值为String的时候表示使用内置的导航条类型，
			 * 当属性值为Selector的时候表示使用自定义的导航条，当属性值设置为true的时候默认使用内置的"classical"导航条。
			 * 内置的导航条类型包括"classical"，"dot"。
			 * @default true
			 * @type Boolean,String,Selector
			 * @example
			 * $('#slider').omSlider({controlNav : false});//不使用导航条
			 * $('#slider').omSlider({controlNav : 'dot'});//使用内置的风格为'dot'的导航条
			 * $('#slider').omSlider({controlNav : 'div#my-nav'});//使用页面中id为'my-nav'的div作为导航条
			 */
			controlNav: true,
	        /**
	         * 设置导航条选中的时候设置的class样式，同时作用于内置导航条和自定义导航条。
	         * @default 'nav-selected'
	         * @type String
	         * @example
	         * $('#slider').omSlider({activeNavCls: 'my-nav-selected'});
	         */
			activeNavCls: 'nav-selected',

	        /**
	         * 设置面板切换的动画效果。
	         * 内置的动画效果包括'fade'(淡入淡出)、'slide-v'(垂直滑动)、'slide-h'(水平滑动)、'random'(随机动画)。
	         * 设置为true使用默认'fade'动画效果，设置为false不使用动画效果。
	         * @default 'fade'
	         * @type String,Boolean
	         * @example
	         * $('#slider').omSlider({effect : false});
	         * //使用垂直滑动的动画效果。
	         * $('#slider').omSlider({effect : 'slide-v'});
	         */
			effect : 'fade',
	        /**
	         * 动画执行的速度。单位毫秒，值越小动画执行的速度越快。
	         * @default 500
	         * @type Number
	         * @example
	         * $('#slider').omSlider({animSpeed : 100});
	         */
			animSpeed : 500,
			/**
			 * 组件初始化时默认激活的面板的index，index从0开始计算，0表示第一个面板。比如设成2则页面显示后默认显示第3个面板。
			 * @default 0
			 * @type Number
	         * @example
	         * $('#slider').omSlider({startSlide : 2});
			 */
			startSlide: 0,
			/**
			 * 鼠标移动到导航条上面后触发切换动作的延迟时间。单位为毫秒。
			 * @default 200
			 * @type Number
	         * @example
	         * $('#slider').omSlider({delay : 100});
			 */
			delay: 200,
            /**
             * 面板切换前触发事件，事件的处理函数返回false则阻止切换动作。
             * @event
             * @type Function
             * @default emptyFn
             * @param index 面板的索引
             * @name omSlider#onBeforeSlide
	         * @example
	         * $('#slider').omSlider({onBeforeSlide : function(index){if(index==2) return false;}});// 阻止slider切换到第三个面板
             */
			onBeforeSlide:function(index){},
			/**
			 * 面板切换后触发事件。
			 * @event
			 * @type Function
			 * @default emptyFn
			 * @param index 面板的索引
			 * @name omSlider#onAfterSlide
			 * @example
			 * $('#slider').omSlider({onAfterSlide : function(index){alert(index + ' slide complete');});
			 */
			onAfterSlide:function(index){}
		};
		var opts = $.extend({}, defaults, options);
		
		/**
		 * 获取slider中保存的变量
		 */
		function _getSliderVars(slider){
			return slider.data('omSlider:vars');
		}
		
		function _runSlideEffect(slider, index, effect){
			var vars = _getSliderVars(slider),
				$container = slider.find('ul.om-slider-content'),
				$item = $container.children(),
				top = 0,
				left = 0;
			var effectnow = effect ? effect : opts.effect;
			if(opts.effect == 'random'){
				$container.addClass('om-slider-effect-'+ effectnow);
			}
			$container.stop();
			if(effectnow == 'slide-v' || effectnow == 'slide-h'){
				var slideV = (effectnow == 'slide-v');
				if(opts.effect == 'random'){
					var cur = 0;
					$item.each(function(n){
						if(n !== vars.currentSlide){
							cur -= ( slideV? $(this).height() : $(this).width() );
						}else{
							return false;
						}
					});
					$container.css(slideV? "top":"left" , cur);
				}
				$item.each(function(n){
					if(n == index) return false;
					slideV? top -= $(this).height() : left -= $(this).width();
				});
			} else{
				return false;
			}
			vars.running = true;
			$container.animate({top:top,left:left},opts.animSpeed,function(){
				vars.running = false;
				opts.onAfterSlide.call(self, index);
			});
		}
		
		function _runFadeEffect(slider,index){
			var vars = _getSliderVars(slider),
				items = slider.find('ul.om-slider-content').children();
			items.each(function(n){
				var $child = $(this);
				if(n == index){
					vars.running = true;
					$child.fadeIn(opts.animSpeed,function(){
						vars.running = false;
						opts.onAfterSlide.call(self, index);
					});
				} else if(n == vars.currentSlide){
					$child.fadeOut(opts.animSpeed);
				}
			});
		}
		
		function _runNoEffect(slider,index){
			var vars = _getSliderVars(slider),
				items = slider.find('ul.om-slider-content').children();
			items.each(function(n){
				var $child = $(this);
				if(n == index){
					$child.show();
					opts.onAfterSlide.call(self, index);
				} else if(n == vars.currentSlide){
					$child.hide();
				}
			});
		}
		
		/**
		 * 切换导航条
		 */
		function _toggleControlNav(slider, index){
			var vars = _getSliderVars(slider);
			var parent = slider;
			// 如果是自定义导航条，则controlNav的位置在slider外面，所以从body下面找controlNav
			if(vars.customNav){
				parent = $('body');
			}
			var navItems = parent.find(vars.controlNav).children();
			navItems.each(function(n){
				$(this).toggleClass(opts.activeNavCls,n==index);
			});
		}
		
		/**
		 * 切换至指定面板，index从0开始
		 */
		function _slideTo(slider, index){
			var vars = _getSliderVars(slider),
			$container = slider.find('ul.om-slider-content');
			if(isNaN(index) || index < 0 || index >= vars.totalSlides){
				return;
			}
	        if (opts.onBeforeSlide.call(self, index) == false) {
	            return false;
	        }
	        if(opts.effect == 'random'){
	        	var array=['fade','slide-h','slide-v'];
	        	var effect = array[Math.floor(Math.random()*3)];
	        	$container.removeClass().addClass('om-slider-content');
	        	$container.removeAttr('style');
	        	$container.find('li').removeAttr('style');
	        	if(effect == 'slide-h' || effect == 'slide-v'){
					_runSlideEffect(slider, index, effect);
				} else {
					_runFadeEffect(slider, index);
				}
	        }else if(opts.effect == 'slide-h' || opts.effect == 'slide-v'){
				_runSlideEffect(slider, index);
			} else if(opts.effect == 'fade' || opts.effect === true){
				_runFadeEffect(slider, index);
			} else{
				_runNoEffect(slider, index);
			}
			
			if(vars.controlNav){
				_toggleControlNav(slider, index);
			}
			vars.currentSlide = index;
			return slider;
		}
		
		function _next(slider){
			var vars = _getSliderVars(slider),
				next_index = 0;
			if(vars.currentSlide+2 <= vars.totalSlides){
				next_index = vars.currentSlide + 1;
			}
			return _slideTo(slider,next_index);
		}
		function _prev(slider){
			var vars = _getSliderVars(slider),
				index = vars.totalSlides - 1;
			if(vars.currentSlide != 0){
				index = vars.currentSlide - 1;
			}
			return _slideTo(slider,index);
		}
		function _processDirectionNav(slider){
			var vars = _getSliderVars(slider),
				directionNav = $('<div class="om-slider-directionNav">').appendTo(slider);
			$('<a class="om-slider-prevNav"></a>').appendTo(directionNav).click(function(){
				if(vars.running)return false;
				_prev(slider);
			});
			$('<a class="om-slider-nextNav"></a>').appendTo(directionNav).click(function(){
				if(vars.running)return false;
				_next(slider);
			});
			slider.hover(function(){
				directionNav.show();
			},function(){
				directionNav.hide();
			});
			
		} 
		function _processControlNav(slider){
			var vars = _getSliderVars(slider);
			if(opts.controlNav === true || opts.controlNav === 'classical'){
				var $nav = $('<ul class="om-slider-nav-classical"></ul>');
				vars.controlNav = '.om-slider-nav-classical';
				for(n=0;n<vars.totalSlides;n++){
					var $navItem = $('<li>'+(n+1)+'</li>');
					$navItem.data('sid',n);
					var hTimer = 0;
					$navItem.click(function(){
						//if(vars.running)return false;
						_slideTo(slider,$(this).data('sid'));
					});
					$navItem.hover(function(){
						if(vars.running)return false;
						var _self = $(this);
						if(_self.hasClass(opts.activeNavCls))return false;
						hTimer = setTimeout(function(){_slideTo(slider,_self.data('sid'));},opts.delay);
					},function(){
						clearTimeout(hTimer);
					});
					$nav.append($navItem);
				}
				slider.append($nav);
			} else if(opts.controlNav === 'dot'){
				var $nav = $('<div class="om-slider-nav-dot"></div>');
				vars.controlNav = '.om-slider-nav-dot';
				for(n=0;n<vars.totalSlides;n++){
					var $navItem = $('<a href="javascript:void(0)">'+(n+1)+'</a>');
					$navItem.data('sid',n);
					var hTimer = 0;
					$navItem.click(function(){
						//if(vars.running)return false;
						_slideTo(slider,$(this).data('sid'));
					});					
					$navItem.hover(function(){
						if(vars.running)return false;
						var _self = $(this);
						if(_self.hasClass(opts.activeNavCls))return false;
						hTimer = setTimeout(function(){_slideTo(slider,_self.data('sid'));},opts.delay);
					},function(){
						clearTimeout(hTimer);
					});
					$nav.append($navItem);
				}
				//$nav.insertAfter(slider);
				$nav.appendTo(slider).css({marginLeft:-1*$nav.width()/2});
			} else{
				if($(opts.controlNav).length > 0){
					vars.controlNav = opts.controlNav;
					vars.customNav = true;
					var $nav = $(opts.controlNav);
					$nav.children().each(function(n){
						var $navItem = $(this);
						$navItem.data('sid',n);
						var hTimer = 0;
						$navItem.click(function(){
							//if(vars.running)return false;
							_slideTo(slider,$(this).data('sid'));
						});
						$navItem.hover(function(){
							if(vars.running)return false;
							var _self = $(this);
							if(_self.hasClass(opts.activeNavCls))return false;
							hTimer = setTimeout(function(){_slideTo(slider,_self.data('sid'));},opts.delay);
						},function(){
							clearTimeout(hTimer);
						});
					});
				}
			}
		}
		return this.each(function() {
			var timer = 0;
			var $this = $(this);
	        var vars = {
                currentSlide: 0,
                totalSlides: 0,
                running: false,
                paused: false,
                stop: false,
                controlNav: '.om-slider-nav-classical'
            };
			var data = $this.data('omSlider');
			if(data){return data;}
			
			$this.data('omSlider',$this).data('omSlider:vars',vars).data('omSlider:opts',opts).addClass('om-slider');
			if(opts.startSlide > 0){
				vars.currentSlide = opts.startSlide; 
			}
			var kids = $this.children();
			kids.wrapAll('<ul class="om-slider-content"></ul>').wrap('<li class="om-slider-item"></li>');
			if(opts.effect == 'slide-v' || opts.effect == 'slide-h'){
				$this.find('.om-slider-content').addClass('om-slider-effect-'+opts.effect);
			}
			vars.totalSlides = kids.length;
			_processControlNav($this);
			/**
			 * 内部方法。在effect="slide-h||slide-v"且startSlide>0的情况下需要在window.onload中执行。
			 */
			function _initSlider(slider, startSlide){
				if(isNaN(startSlide) || startSlide < 0 || startSlide >= vars.totalSlides){
					return;
				}
				var $container = slider.find('ul.om-slider-content'),
					$item = $container.children(),
					top = 0,
					left = 0;
				if(opts.effect == 'slide-h'){
					$item.each(function(n){
						if(n == startSlide) return false;
						left -= $(this).width();
					});
					setTimeout(function(){$container.css({left:left,top:top});},0);
				} else if(opts.effect == 'slide-v'){
					$item.each(function(n){
						if(n == startSlide) return false;
						top -= $(this).height();
					});
					// 修复omSlider在omTabs的隐藏tab下设置样式无效的问题。只在IE7浏览器下发生。
					setTimeout(function(){$container.css({left:left,top:top});},0);
				} else{
					$container.children().eq(startSlide).show();
				}
				
				if(vars.controlNav){
					_toggleControlNav(slider, startSlide);
				}
				if(opts.autoPlay){
					timer = setInterval(function(){_next(slider);},opts.interval);
				}
				if(opts.pauseOnHover){
					slider.hover(function(){
						vars.paused = true;
						clearInterval(timer);
					},function(){
						vars.paused = false;
						if(opts.autoPlay){
							timer = setInterval(function(){_next(slider);},opts.interval);
						}
					});
				}
				if(opts.directionNav){
					_processDirectionNav(slider);
				}
			}
			
			if(opts.startSlide > 0 && (opts.effect == 'slide-h' || opts.effect == 'slide-v')){
				// 考虑到slide的动画效果下如果设置startSlide>0需要计算startSlide前面的面板的大小。
				// 如果面板里面包含img标签的话只有在图片下载完后(既window.onload事件)才能获得面板的大小。
				$(window).load(function(){
					_initSlider($this,opts.startSlide);
				});
			} else{
				_initSlider($this,opts.startSlide);
			}

		});
	};
})(jQuery);