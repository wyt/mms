/*
* jQuery Slide 1.0
* Copyright (c) 2010 陆宏鸣
* Date: 2011-01-22
* Slide 组件
* 本人原创代码，如有幸被您在项目中用到，请保留此信息。
* http://www.cnblogs.com/homeLu
*/
(function($) {
    $.fn.Slide = function(options, val) {
        if (this.size() == 0) return;

			this.defaults = {
				auto: false,        //是否自动播放
				width: 85,          //缩略图的宽度
				height: 42,         //缩略图的高度
				onstart: null,      //开始滚动
				onchange: null     //滚动事件
			};
        this.options = $.extend(this.defaults, options);

        var SlideClass = function(contain, opt) {
            this.Images = {
                /*img: [{
                img$: null,          
                imgsPosition: null
                }],*/
                img: [],
                imgCount: 0,        //图片总数
                curIdx: 0           //当前显示第几张
            };

            //是否正在移动
            this.moving = false;
            this.autoMoveTimer = null;

            this.autoMove = function() {
            	if(me.Images.imgCount<=1){
	           		 this.moving = true;
	           	}
                if (!me.moving) {
                    me.moveToIdx((me.Images.imgCount + me.Images.curIdx - 1) % me.Images.imgCount);
                }
            };

            //轮转到第几个图像
            this.moveToIdx = function(idx) {
                var me = this;
                var curIdx = me.Images.curIdx;

                //初始状态下，左列最后一个图片索引。
                var leftEnd = Math.floor((me.Images.imgCount - 1) / 2);

                //点击的当前位置的图片对应在初始状态时的哪个索引
                var curEndToInitEnd = (me.Images.imgCount + (idx - curIdx)) % me.Images.imgCount;

                //当前点击的是否是在左发散列的图片。
                var isLeft = (curEndToInitEnd <= leftEnd);

                //要移动图片的索引距离
                var dis = (me.Images.imgCount + (isLeft ? 1 : -1) * (idx - curIdx)) % me.Images.imgCount;

                opt.onstart && opt.onstart(me.Images.img[curIdx].img$, me.Images.img[idx].img$);

                var stepPercent = new Array(15);    //固定走15步，每步走至百分之多少的一个数列。 表示经过时间一定，速度不固定
                stepPercent[0] = 0.2;               //起始20%
                stepPercent[1] = 0.2 + 0.2 * 0.81;   //第二步
                for (var i = 2, total = stepPercent[1]; i < stepPercent.length; i++) {
                    stepPercent[i] = total + (total - stepPercent[i - 2]) * 0.81;  //初始化数列。
                    total = stepPercent[i];
                    if (i == stepPercent.length - 1)
                        stepPercent[i] = 1;
                }

                var step = 0;
                var moveAll = function() {
                    var count = me.Images.imgCount;
                    opt.onchange && opt.onchange(stepPercent[step]);
                    for (var i = 0; i < count; i++) {
                        //移动至此img位置
                        var toIdx = (count + i + dis * (isLeft ? -1 : 1)) % count;
                        var toImg = me.Images.img[toIdx];
                        var toPos = toImg.imgsPosition;
                        var toWidth = toImg.size.width;
                        var toHeight = toImg.size.height;
                        var toOpacity = toImg.opacity;
                        var toZIndex = toImg.zIndex;

                        //当前处理的img
                        var fImg = me.Images.img[i];
                        var fPos = fImg.imgsPosition;
                        var fWidth = fImg.size.width;
                        var fHeight = fImg.size.height;
                        var fOpacity = fImg.opacity;
                        var fZIndex = fImg.zIndex;

                        //设置尺寸与目标位置
                        fImg.img$.css({
                            "left": fPos.left + (toPos.left - fPos.left) * stepPercent[step],
                            "top": fPos.top + (toPos.top - fPos.top) * stepPercent[step],
                            "width": (fWidth + (toWidth - fWidth) * stepPercent[step]) + "px",
                            "height": (fHeight + (toHeight - fHeight) * stepPercent[step]) + "px",
                            "opacity": fOpacity + (toOpacity - fOpacity) * stepPercent[step],
                            "z-index": toZIndex
                        });
                    };

                    //记录经过的步数
                    step++;
                    if (step < stepPercent.length)
                        setTimeout(moveAll, 50);
                    else {
                        //移动完毕
                        step = 0;
                        me.moving = false;
                        for (var i = 0; i < count; i++) {
                            //移动至此img位置
                            var toIdx = (count + i + dis * (isLeft ? -1 : 1)) % count;
                            var img = me.Images.img[i];
                            var img$ = img.img$;
                            img.imgsPosition.left = img$.position().left;
                            img.imgsPosition.top = img$.position().top;
                            img.size.width = img$.width();
                            img.size.height = img$.height();
                            img.opacity = parseFloat(img$.css("opacity"));
                            img.zIndex = img$.css("z-index");
                        }
                    }
                };
                var timerMove = null;
                timerMove = setTimeout(function() {
                    me.moving = true;
                    moveAll();
                }, 50);

                me.Images.curIdx = idx;
            };

            //初始化方法
            this.Init = function() {
                var me = this;
                contain.children("img").each(function(n) {
                    me.Images.img.push({ img$: $(this), size: null, imgsPosition: null, opacity: 1, zIndex: 999999 }); //.push($(this));
                    $(this)
                        .css({ "cursor": "pointer", "border": "solid 1px #999" })
                        .attr("idx", n)
                        .click(function() {
                        	$("#imageName").val($(this).attr("title"));
                        	$("#imageId").val($(this).attr("imageId"));
                            if (me.moving)
                                return;
                            else {
                                var curImg = $(this), curIdx = curImg.attr("idx");
                                if (curIdx == me.Images.curIdx)
                                    return;
                                clearInterval(me.autoMoveTimer);
                                me.moveToIdx(curIdx);
                                if (opt.auto)
                                    me.autoMoveTimer = setInterval(me.autoMove, 4000);
                            }
                        });
                });

                var w = contain.width(), h = contain.height();
                me.Images.imgCount = me.Images.img.length;

                //左发散的结束图片
                var leftEnd = Math.floor((me.Images.imgCount - 1) / 2);

                // 第0张图片
                me.Images.img[0].img$.css({ "width": opt.width + "px", "height": opt.height + "px" });
                me.Images.img[0].imgsPosition = {
                    top: 0,
                    left: w / 2 - me.Images.img[0].img$.width() / 2
                };
                me.Images.img[0].size = {
                    width: me.Images.img[0].img$.width(),
                    height: me.Images.img[0].img$.height()
                };
                me.Images.img[0].zIndex = 999999;
                me.Images.img[0].opacity = 1;
                /* 
                图形布阵形式
                0
                1 4
                2   3
                */
                //左发散
                for (var i = 1; i <= leftEnd; i++) {
                    var preIdx = i - 1;
                    var preImg = me.Images.img[preIdx];
                    var img = preImg.img$;
                    var prePos = preImg.imgsPosition;
                    var preSize = preImg.size;
                    //缩小至上一个的75%
                    me.Images.img[i].size = {
                        width: preSize.width * 0.75,
                        height: preSize.height * 0.75
                    };
                    me.Images.img[i].imgsPosition = {
                        top: prePos.top + 15,
                        left: prePos.left - (me.Images.img[i].size.width * 0.75)
                    };
                    me.Images.img[i].opacity = preImg.opacity * 0.75;
                    me.Images.img[i].zIndex = preImg.zIndex - 1;
                }
                //右发散
                for (var i = me.Images.imgCount - 1; i > leftEnd; i--) {
                    var preIdx = (i + 1) % me.Images.imgCount;
                    var preImg = me.Images.img[preIdx];
                    var img = preImg.img$;
                    var prePos = preImg.imgsPosition;
                    var preSize = preImg.size;

                    //缩小至上一个的75%
                    me.Images.img[i].size = {
                        width: preSize.width * 0.75,
                        height: preSize.height * 0.75
                    };

                    me.Images.img[i].imgsPosition = {
                        top: prePos.top + 15,
                        left: prePos.left + (preSize.width - me.Images.img[i].size.width * 0.25)
                    };

                    me.Images.img[i].opacity = preImg.opacity * 0.75;
                    me.Images.img[i].zIndex = preImg.zIndex - 1;
                }

                var contPosType = contain.css("position").toLowerCase();
                if (contPosType != "absolute" && contPosType != "relative") {
                    contain.css({ "position": "relative" });
                }
                for (var i = 0; i < me.Images.imgCount; i++) {
                    var img = me.Images.img[i];
                    var img$ = me.Images.img[i].img$;
                    var imgPos = me.Images.img[i].imgsPosition;
                    img$.css({
                        "position": "absolute",
                        "left": imgPos.left + "px",
                        "top": imgPos.top + "px",
                        "width": img.size.width + "px",
                        "height": img.size.height + "px",
                        "z-index": img.zIndex
                    });
                    img$.fadeTo("fast", img.opacity);
                }
            };

            this.Init();    //初始化

            if (opt.auto) {
                var me = this;
                me.autoMoveTimer = setInterval(function() { me.autoMove(); }, 4000);
            }
        };

        var objSlide = [];
        var me = this;
        this.each(function() {
            objSlide.push(new SlideClass($(this), me.options));
        });
    };
})(jQuery);