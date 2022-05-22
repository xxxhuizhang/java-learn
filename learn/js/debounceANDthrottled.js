function debounce(func, wait) {
    var timeout;

    return function () {
        var context = this;
        var args = arguments;

        clearTimeout(timeout)
        timeout = setTimeout(function () {
            func.apply(context, args)
        }, wait);
    }
}

function throttle(func, wait) {
    var timeout, context, args, result;
    var previous = 0;

    var later = function () {
        previous = +new Date();
        timeout = null;
        func.apply(context, args)
    };

    var throttled = function () {
        var now = +new Date();
        //下次触发 func 剩余的时间
        var remaining = wait - (now - previous);
        context = this;
        args = arguments;
        // 如果没有剩余的时间了或者你改了系统时间
        if (remaining <= 0 || remaining > wait) {
            if (timeout) {
                clearTimeout(timeout);
                timeout = null;
            }
            previous = now;
            func.apply(context, args);
        } else if (!timeout) {
            timeout = setTimeout(later, remaining);
        }
    };
    return throttled;
}


function throttle() {
var context;
    console.log(context);
}







