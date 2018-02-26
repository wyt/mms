  $(function() {
            $("#div_Slide").Slide({
                auto: true,
                width: 85,
                height: 42,
                onstart: function(curImg, nextImg) {
                    var cData = curImg.attr("data");
                    var nData = nextImg.attr("data");
                    var bigCur = $("#" + cData), bigNext = $("#" + nData);

                    var allBigImg = bigCur.parent().children("img");
                    //var curIndex = allBigImg.index(bigCur[0]);
                    //var nextIndex = allBigImg.index(bigNext[0]);

                    var firstImg = $(allBigImg[0]);
                    if (firstImg.attr("id") != bigCur.attr("id"))
                        bigCur.insertBefore(firstImg);
                    $("#div_BigImg").scrollLeft(0);
                    bigNext.insertAfter(bigCur);
                },
                onchange: function(percent) {
                    $("#div_BigImg").scrollLeft(400 * percent);
                }
            });
            var bigDiv = $("#div_BigImg");
            var bigDivPos = bigDiv.position();
            bigDiv.scrollLeft(0);

            $("#div_Slide").css({
                "top": (bigDivPos.top + bigDiv.height() - $("#div_Slide").height()) + "px",
                "left": bigDivPos.left + "px"
            });
        });