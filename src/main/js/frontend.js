
var content = {
    Y1:     '<b>Y1</b> is the jump height reached when you release jump immediately after pressing it and without any momentum.',
    Y2:     '<b>Y2</b> is the jump height reached when you hold jump all the way but without any momentum.',
    Y3:     '<b>Y3</b> is the jump height reached when you hold jump all the way with maximum momentum. (The box becomes magenta.)',
    L:      '<b>L</b> is the duration in seconds for a jump reaching height <b>Y2</b>.',
    Output: 'The following values are the constants used in the velocity formula in order to produce those physics. You can learn more about the formula in the next sections.'
};

$(function () {

    if(getParameterByName("donated") == 'true')
        $('#thankyou').css("display", "block");

    $('#y1Helper').popover({
        content: content.Y1,
        trigger: 'focus'
    });

    $('#y2Helper').popover({
        content: content.Y2,
        trigger: 'focus'
    });

    $('#y3Helper').popover({
        content: content.Y3,
        trigger: 'focus'
    });

    $('#lHelper').popover({
        content: content.L,
        trigger: 'focus'
    });

    $('#OutputHelper').popover({
        content: content.Output,
        trigger: 'focus'
    });
});

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}