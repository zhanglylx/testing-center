// document.write("<script src='static/scripts/base-values.js'></script>");

/**
 *  清空元素text
 * @param $element_Arr jQuery对象,可以是数组，可以是单个或多个对象
 */
function _$empty($element_Arr) {
    if (arguments.length > 0) {
        for (var i = 0; i < arguments.length; i++) {
            var element = arguments[i];
            if (Array.isArray(element)) {
                for (i = 0; i < element.length; i++) {
                    $(element[i]).empty();
                }
            } else {
                $(element).empty();
            }
        }
    }
};

/**
 * 清空表单控件的内容
 * @param $element_Arr
 */
function _$emptyInput($element_Arr) {
    if (arguments.length > 0) {
        for (var i = 0; i < arguments.length; i++) {
            var element = arguments[i];
            if (Array.isArray(element)) {
                for (i = 0; i < $element.length; i++) {
                    $(element[i]).val("");
                }
            } else {
                $(element).val("");
            }
        }
    }
};

/**
 * 当前页面跳转链接
 * @param url
 */
function _$goToUrlCurrentPage(url) {
    window.location.href = url;
};

/**
 * 新建页面跳转
 * @param url
 */
function _$goToUrlNewPage(url) {
    window.open(url);
};


/**
 * 按钮不可点
 * @param $button jQuery对象
 * @param boolean true:不可点，false：可点击
 */
function _$buttonClickUndoable($button, boolean) {
    $button.attr('disabled', boolean);
};

/**
 * 获取表单的val值并去除前后空格
 * @param $element
 * @returns {string}
 */
function _$getValTrim($element) {
    return $element.val().trim();
};

/**
 * 发送ajaxGet请求
 * @param stringUrl
 * @param data
 * @param successFunction
 * @param errorFuncation
 * @param async  是否异步请求，空或者true为异步请求
 * @param $button 处理完成后自动将按钮处理为可点击状态
 */
function _$ajaxGetJSON(stringUrl, data, successFunction, errorFuncation, $button, async) {
    _$ajaxJSON(stringUrl, "get", data, successFunction, errorFuncation, $button, async);
};

/**
 * 发送ajaxPost请求,默认返回值格式为JSON
 * @param stringUrl
 * @param data
 * @param successFunction
 * @param errorFuncation
 * @param async  是否异步请求，空或者true为异步请求
 * @param $button 处理完成后自动将按钮处理为可点击状态
 */
function _$ajaxPostJSON(stringUrl, data, successFunction, errorFuncation, $button, async) {
    _$ajaxJSON(stringUrl, "post", data, successFunction, errorFuncation, $button, async);
};

/**
 * 发送ajax请求
 * @param stringUrl
 * @param type
 * @param data
 * @param successFunction
 * @param errorFuncation
 * @param $button 处理完成后自动将按钮处理为可点击状态
 * @param async  是否异步请求，空或者true为异步请求
 */
function _$ajaxJSON(stringUrl, type, data, successFunction, errorFuncation, $button, async) {
    _$ajax(stringUrl, type, data, "JSON", successFunction, errorFuncation, $button, async);
};


/**
 * 发送ajax请求
 * @param stringUrl
 * @param type
 * @param data
 * @param dataType  返回的数据类型
 * @param successFunction
 * @param errorFuncation 如果为null，则默认响应失败时弹出错误提示
 * @param $button 处理完成后自动将按钮处理为可点击状态,如果不是按钮，则再次点击此元素时会弹出正在加载提示
 * @param async  是否异步请求，空或者true为异步请求
 */
function _$ajax(stringUrl, type, data, dataType, successFunction, errorFuncation, $button, async) {
    var as = true;
    if (async !== null) {
        as = async;
    }
    if ($button) {
        _$buttonClickUndoable($button, true);
        if ($button.data('_$ajaxJSONClcikStatus') != null && $button.data('_$ajaxJSONClcikStatus') === true) {
            alert("正在加载中，请稍后");
            return;
        }
        $button.data('_$ajaxJSONClcikStatus', true);
    }
    $.ajax({
        url: stringUrl,
        type: type,
        data: data,
        dataType: dataType,
        async: as,
        success: function (httpResponse) {
            if (successFunction) successFunction(httpResponse);
            if ($button) {
                _$buttonClickUndoable($button, false);
                $button.data('_$ajaxJSONClcikStatus', false);
            }

        },
        error: function (httpStatus) {
            if (errorFuncation) {
                errorFuncation(httpStatus);
            } else {
                alert("服务器响应失败，请稍后重试,服务状态:" + httpStatus.status);
            }

            if ($button) {
                _$buttonClickUndoable($button, false);
                $($button).data('_$ajaxJSONClcikStatus', false);
            }

        }
    });
};


/**
 * 获取css变量,默认从root获取
 * @param keyName
 * @returns {string}
 */

function _$getCssPropertyValueRoot(keyName) {
    return _$getCssPropertyValue(":root", keyName);
};

/**
 * 获取css变量
 * @param node
 * @param keyName
 * @returns {string}
 */
function _$getCssPropertyValue(node, keyName) {
    var aEl = document.querySelector(node);
    return window.getComputedStyle(aEl).getPropertyValue(keyName).trim();
};


/**
 * 设置css自定义属性
 * @param keyName
 * @param value
 */
function _$setCssPropertyValue(keyName, value) {
    document.documentElement.style.setProperty(keyName, value);
};

/**
 * 自动解析响应内容，格式必须为{status,msg,data}
 * 当status为0时，将执行传入的方法，不为0是会弹出提示框，提示框内容为:msg，并执行错误方法
 * @param response  响应数据
 * @param successFunction  成功方法
 * @param errorFuncation  错误方法
 *
 */
function _$analysisJsonSuccessResponse(response, successFunction, errorFuncation) {
    if (response.status === 0) {
        successFunction(response.data, response);
    } else {
        if (errorFuncation !== null) setTimeout(errorFuncation, 0);
        alert(response.msg);
    }
};

/**
 * 判断对象是否为空 null "" undefined  NaN
 * 注意：此方法不包含 0
 * @param object
 * @returns {boolean}
 */
function _$isNullNonZero(object) {
    if (object) {
        return false;
    } else {
        return object !== 0;

    }
};