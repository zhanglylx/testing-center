function createToolDiv(data) {
    var tool = '<div class="tools_div"><div class="tool_div">' +
        '<div class="tools_div_img"><img src="static/image/test.jpg" ></div>' +
        '<div class="tool_name"><h3>' + data.tool_list_name + '</h3>' +
        '<div class="tool_name_navigation" style="color: ' + data.tool_box_colour + '">[' + data.tool_box_name + ']</div>' +
        '</div>' +
        '<div class="clear_float"></div>' +
        '<div class="too_halving_div"></div>' +
        '<div class="tool_desc">' + data.tool_list_des + '</div>' +
        '</div></div>';
    var $tool = $(tool);
    $tool.data("data", data);
    return $tool;
}