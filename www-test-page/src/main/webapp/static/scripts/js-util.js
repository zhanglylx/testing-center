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
 * 跳转连接
 * @param url
 * @param method   请求方法，默认get
 * @param isNewPage  是否新建页面打开
 * @param params
 * @returns {HTMLFormElement}
 */
function _$go(url, method, isNewPage, params) {
    if (!method) {
        method = "get";
    }
    var className = "go" + parseInt(Math.random() * 100000);
    var temp = document.createElement("form");
    temp.action = url;
    temp.method = method;
    temp.class = className;
    if (isNewPage === true) {
        temp.target = "_blank";
    }
    temp.style.display = 'none';
    if (params) {
        params = eval("(" + params + ")");
        for (var p in params) {
            var opt = document.createElement('textarea')
            ;
            opt.name = p;
            opt.value = params[p];
            temp.appendChild(opt);
        }
    }
    var mathOpt = document.createElement('textarea');
    mathOpt.name = "math";
    mathOpt.value = Math.random();
    temp.appendChild(mathOpt);
    document.body.appendChild(temp);
    $(temp).addClass(className);
    temp.submit();
    $("." + className + "").remove();
    return temp;
}

/**
 * 当前页面跳转链接
 * @param url
 * @param method
 * @param params
 */
function _$goToUrlCurrentPage(url, method, params) {
    // window.location.href = url;
    _$go(url, method, false, params);
};

/**
 * 新建页面跳转
 * @param url
 */
function _$goToUrlNewPage(url, method, params) {
    // window.open(url);
    _$go(url, method, true, params);
};


/**
 * 按钮不可点
 * @param $button jQuery对象
 * @param boolean true:不可点，false：可点击
 */
function _$buttonClickUndoable($button, boolean) {
    $($button).attr('disabled', boolean);
};

/**
 * 获取表单的val值并去除前后空格
 * @param $element
 * @returns {string}
 */
function _$getValTrim($element) {
    return $($element).val().trim();
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
    _$ajaxAutoRequestLimits(stringUrl, type, data, "JSON", successFunction, errorFuncation, $button, async);
};

/**
 *发送ajax请求，自动限制请求没有执行完成时的操作和请求错误后自动弹窗提示
 *
 */
function _$ajaxAutoRequestLimits(stringUrl, type, data, dataType, successFunction, errorFuncation, $button, async) {
    if (!$button) {
        $button = $("body");
    }
    if (errorFuncation) {
        _$ajax(stringUrl, type, data, dataType, successFunction, errorFuncation, $button, async);
    } else {
        _$ajax(stringUrl, type, data, dataType, successFunction, function (httpStatus) {
            alert("服务器响应失败，请稍后重试,服务状态:" + httpStatus.status);
        }, $button, async);
    }

}

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
            _$ajaxRequestLimits($button);
        },
        error: function (httpStatus) {
            if (errorFuncation) {
                errorFuncation(httpStatus);
            }
            _$ajaxRequestLimits($button);
        }
    });
};

/**
 * ajax请求限制
 * @private
 */
function _$ajaxRequestLimits($button) {
    if ($button) {
        _$buttonClickUndoable($button, false);
        $($button).data('_$ajaxJSONClcikStatus', false);
    }
}

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
    if (_$isNullNonZero(keyName) || _$isNullNonZero(value)) {
        alert("_$setCssPropertyValue：参数传入非法");
        return;
    }
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
        if (successFunction) {
            successFunction(response.data, response);
        }
    } else {
        if (errorFuncation) {
            setTimeout(errorFuncation, 0);
        }
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

/**
 * 遍历list
 * @param object list对象
 * @param f 回调方法,传入$对象,方法如果返回true，则停止循环
 */
function _$traversalList(object, f) {
    for (var i = 0; i < object.length; i++) {
        if (f(object[i]) === true) {
            return;
        }
    }
}

/**
 * 动态引入css
 * @param path
 */
function _$loadCss(path) {
    $("head").append("<link>");
    var $css = $("head").children(":last");
    $css.attr({
        rel: "stylesheet",
        type: "text/css",
        href: path
    });
}

/**
 * 动态引入js
 * @param url
 * @param f  回调方法
 */
function _$loadJs(url, f) {
    try {
        $.getScript(url, function () {
            if (f) f();
        });
    } catch (e) {
        alert("获取js失败：" + url);
    }
}

/**
 * 复制文本
 * 使用介绍:<textarea id="bar">Mussum ipsum cacilds...</textarea>
 *          data-clipboard-action 属性为 copy/cut 来，明确操作是 复制 / 剪切,默认为复制
 *           <button class="btn" data-clipboard-action="cut" data-clipboard-target="#bar">
 *           Cut to clipboard
 *           </button>
 * @param selector  选择器
 */
function _$copyText(selector) {
    var clipboard = new ClipboardJS(selector);

    clipboard.on('success', function (e) {
        // console.info('Action:', e.action);
        // console.info('Text:', e.text);
        // console.info('Trigger:', e.trigger);
        e.clearSelection();
        _$toastText("复制成功：" + e.text);
    });

    clipboard.on('error', function (e) {
        // console.error('Action:', e.action);
        // console.error('Trigger:', e.trigger);
        _$toastText("复制成功失败，请手动复制");
    });
}

/**
 * 浮动弹窗提示文本
 * 在页面中心悬浮2.5秒后自然消失
 */
var _$toastTextIntervalId;

function _$toastText(text) {
    var width = text.length * 20;
    var math = Math.random();
    if (_$toastTextIntervalId) {
        clearInterval(_$toastTextIntervalId);
    }

    $(".body_reminder_div").remove();
    $("body").prepend("<div math=" + math + " class='body_reminder_div'style='z-index: 1000000;position: absolute;top: 45%;left:49%;background: rgba(220,220,220,0.9);'>" +
        '<div style="height: 25px ;width: ' + width + 'px;text-align: center; font-size: 15px ;line-height: 25px; color: var(--theme-colors)">' +
        text +
        '</div>' +
        "</div>");
    var i = 0;
    var a = 0.8;
    var ai = 0.04;
    _$toastTextIntervalId = setInterval(function () {
        a = a - ai;
        $(".body_reminder_div").css("background", "rgba(220,220,220," + a + ")");
        i++;
        if (i === 20) {
            clearInterval(_$toastTextIntervalId);
            $(".body_reminder_div").remove();
        }
    }, 100)
}

/**
 * 生成从minNum到maxNum的随机数，包前不包后
 * 如果只传入minNum，则0-minNum-1的返回值
 * 如果传入超过2个参数，默认返回0
 * @param minNum  最小值
 * @param maxNum 最大值
 */
function _$randomNum(minNum, maxNum) {
    switch (arguments.length) {
        case 1:
            return parseInt(Math.random() * minNum, 10);
        case 2:
            return parseInt(Math.random() * (maxNum - minNum) + minNum, 10);
        default:
            return 0;
    }
};

/**
 * 获取元素颜色的rgb
 * @param selector
 * @param selectorAttribute 获取的元素属性
 * @returns {string[]}rgb数组
 */
function _$cssRGB(selector, selectorAttribute) {
    var color = $(selector).css(selectorAttribute);
    color = color.replace("rgb(", "");
    color = color.replace(")", "");
    color = color.split(",");
    return color;
};


function _$cssGetRGBA(selector, selectorAttribute, a) {
    var color = _$cssRGB(selector, selectorAttribute);
    var rgba = "rgba(";
    _$traversalList(color, function (n) {
        rgba += n + ",";
    });
    rgba += a + ")";
    return rgba;
}

function _$cssGetSelectorColorRGBA(selector, a) {
    return _$cssGetRGBA(selector, "color", a)
};

function _$cssGetSelectorBColorRGBA(selector, a) {
    return _$cssGetRGBA(selector, "background-color", a)
};

/**
 *  * 静态绑定元素回车与其他元素点击事件
 */
function _$keydownEnter_ClickDynamic(enterSelector, parentSelector, otherSelector, f) {
    _$checkParametersIsNull(enterSelector);
    _$checkParametersIsNull(parentSelector);
    _$checkParametersIsNull(otherSelector);
    _$checkParametersIsNull(f);
    $(parentSelector).on("click", otherSelector, f);
    _$keydownEnter($(enterSelector), f);
}

/**
 * 静态绑定元素回车与其他元素点击事件
 */
function _$keydownEnter_ClickStatic(enterSelector, otherSelector, f) {
    _$checkParametersIsNull(enterSelector);
    _$checkParametersIsNull(otherSelector);
    _$checkParametersIsNull(f);
    $(otherSelector).click(f);
    _$keydownEnter($(enterSelector), f);
}

/**
 * 动态绑定元素回车事件与其他元素事件调用同一个方法
 */
function _$keydownEnter_otherEventsDynamic(enterSelector, otherParentSelector, otherSelector, otherEvents, f) {
    _$checkParametersIsNull(enterSelector);
    _$checkParametersIsNull(otherSelector);
    _$checkParametersIsNull(otherParentSelector);
    _$checkParametersIsNull(otherEvents);
    _$checkParametersIsNull(f);
    $(otherParentSelector).on(otherEvents, otherSelector, f);
    _$keydownEnter($(enterSelector), f);
}

/**
 * 静态绑定元素回车事件与其他元素事件调用同一个方法
 *
 */
function _$keydownEnter_otherElementEventsStatic(enterSelector, otherSelector, otherEvents, f) {
    _$checkParametersIsNull(enterSelector);
    _$checkParametersIsNull(otherSelector);
    _$checkParametersIsNull(otherEvents);
    _$checkParametersIsNull(f);
    $("body").on(events, otherSelector, f);
    _$keydownEnter($(enterSelector), f);
}

/**
 * 绑定元素下的回车事件
 * @param $e
 * @param f
 */
function _$keydownEnter($e, f) {
    _$checkParametersIsNull(f);
    _$checkParametersIsNull($e);
    _$keydown($e, function (number) {
        if (number === 13) {
            f();
        }
    });
};

/**
 * 绑定元素下的键盘按下事件
 * @param $e  jquery元素，dom元素将自动转换为jquery元素
 * @param f 回调方法，并传入按下的keyNumber
 */
function _$keydown($e, f) {
    _$checkParametersIsNull(f);
    _$checkParametersIsNull($e);
    if ($e && f) {
        $($e).keydown(function (event) {
            const code = event.keyCode;
            f(code);
        });
    }
};

function _$checkParametersIsNull(param, msg) {
    if (_$isNullNonZero(msg)) {
        msg = "";
    }
    if (_$isNullNonZero(param)) {
        throw new Error("NullPointerException" + msg);
    }
};