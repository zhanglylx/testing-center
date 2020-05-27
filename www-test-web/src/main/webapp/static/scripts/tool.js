$(function () {
    $("#tools").on("click", ".tool_div", function () {
        if ($(this).attr("class") !== "tool_div") {
            return;
        }
        var data = $(this).data(data_tools_div);
        if (_$isNullNonZero(data)) {
            alert("数据不能为空,请联系管理员");
            return;
        }
        var tool_box_id = data.tool_box_id;
        var tool_list_type = data.tool_list_type;
        var tool_list_url = data.tool_list_url;
        //1:get,2:post
        var tool_list_request_method = data.tool_list_request_method;
        var tool_list_request_parameter = data.tool_list_request_parameter;
        var tool_list_js = data.tool_list_js;
        var tool_list_css = data.tool_list_css;
        var tool_list_name = data.tool_list_name;
        var toolListId = data.id;
        //上传热度埋点
        _$ajax(
            path_toolList_addHeat,
            "post",
            {"toolListId":toolListId},
            "json",
            function (response) {
                _$analysisJsonSuccessResponse(response);
            }
        );
        if (_$isNullNonZero(tool_box_id)) {
            alert("boxId不能为空,请联系管理员");
            return;
        }
        if (_$isNullNonZero(tool_list_type)) {
            alert("type不能为空,请联系管理员");
            return;
        }
        if (_$isNullNonZero(tool_list_url)) {
            alert("url不能为空,请联系管理员");
            return;
        }

        if (_$isNullNonZero(tool_list_request_method)) {
            alert("请求方法不能为空,请联系管理员");
            return;
        }

        if (_$isNullNonZero(tool_list_request_parameter)) {
            tool_list_request_parameter = "";
        }
        switch (tool_list_request_method) {
            case 1:
                tool_list_request_method = "get";
                break;
            case 2:
                tool_list_request_method = "post";
                break;
            default:
                alert("不支持的请求方法类型：" + tool_list_request_method);
                return;
        }
        //1:加载当前页面到tools
        //2:跳转新的页面
        //3:直接访问url并且将返回的消息:msg进行弹窗
        switch (tool_list_type) {
            case 1:
                clickNavigationByIdCss(tool_box_id);
                //隐藏底部版权
                test_home_copy_hide();
                _$ajax(
                    tool_list_url,
                    tool_list_request_method,
                    tool_list_request_parameter,
                    "HTML",
                    function (response) {
                        $("#tools").html(response);
                        var $title = $('<div id="tools_title">' + tool_list_name + '</div>');
                        $("#tools").prepend($title);
                        if (tool_list_js) {
                            _$loadJs(tool_list_js);
                        }
                        if (tool_list_css) {
                            _$loadCss(tool_list_css);
                        }
                    }, null, $("body")
                );
                break;
            case 2:
                _$goToUrlNewPage(tool_list_url, tool_list_request_method, tool_list_request_parameter);
                break;
            case 3:
                _$ajaxJSON(
                    tool_list_url,
                    tool_list_request_method,
                    tool_list_request_parameter,
                    function (response) {
                        alert(response.msg);
                    }, null, $("body")
                );
                break;
            default:
                alert("加载工具失败，请联系管理员");
        }

    });
});


function createToolDiv(data) {
    var imgUrl = data.tool_list_image;
    if (_$isNullNonZero(imgUrl)) {
        imgUrl = "static/image/logo.png";
    }
    var tool_box_colour = data.tool_box_colour;
    if (_$isNullNonZero(tool_box_colour)) {
        tool_box_colour = "var(--theme-colors)";
    }
    var tool = '<div class="tool_div">' +
        '<div class="tools_div_img"><img src="' + imgUrl + '" ></div>' +
        '<div class="tool_name"><h3>' + data.tool_list_name + '</h3>' +
        '<div class="tool_name_navigation" style="color: ' + tool_box_colour + '">[' + data.tool_box_name + ']</div>' +
        '</div>' +
        '<div class="clear_float"></div>' +
        '<div class="too_halving_div" style="background-color: '+_$cssGetSelectorColorRGBA(".testing-center-test-home-theme",0.2)+'"></div>' +
        '<div class="tool_desc">' + data.tool_list_des + '</div>' +
        '<div class="tools_div_root_clear"></div>' +
        '</div>';
    // '<div class="tools_div_root_clear"></div>';
    var $tool = $(tool);
    $tool.data(data_tools_div, data);
    return $tool;
}