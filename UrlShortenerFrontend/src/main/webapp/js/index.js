/**
 * Created by Alberto on 27/09/2014.
 */
'use strict'
$(document).ready(function () {
    $('#url-form').find('.btn').on('click', function (ev) {
        var url = $('#url-form').find('input').val();
        if(validateURL(url)){
            var data = {};
            data.url = url;
            addUrl(data);
            $('#error').hide();
        }else{
            $('#error').show();
        }
    })
});

function addUrl(url) {
    jQuery.ajax({
        type: "PUT",
        url: "/UrlShortener",
        accepts: "application/json",
        cache: false,
        contentType: "application/json",
        data: JSON.stringify(url),
        dataType: "json",
        success: function (data, status, jqXHR) {
            updateDialog(data)
            $('#result').modal('show');
        },

        error: function (jqXHR, status) {
            console.log("Something went wrong!")

        }
    });
}

function updateDialog(data) {
    var link = $('#result').find('.modal-body').find('a'),
        res = data.url.split("/"),
        encodedSegment = res[res.length - 1],
        mockDNS = "http//sho.rt/" + encodedSegment;
    link.text(mockDNS);
    link.attr("href", data.url);

    //fill the p element
    var paragraph = $('#result').find('.modal-body').find('p');
    paragraph.text('Note: we use a mock base URL as we do not have our own domain, complete here:\n' + data.url);
}

function validateURL(textval) {
    var urlregex = new RegExp(
        "^(http|https|ftp)\://([a-zA-Z0-9\.\-]+(\:[a-zA-Z0-9\.&amp;%\$\-]+)*@)*((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0-9\-]+\.)*[a-zA-Z0-9\-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(\:[0-9]+)*(/($|[a-zA-Z0-9\.\,\?\'\\\+&amp;%\$#\=~_\-]+))*$");
    return urlregex.test(textval);
}