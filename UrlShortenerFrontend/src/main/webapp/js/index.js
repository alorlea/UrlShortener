/**
 * Created by Alberto on 27/09/2014.
 */
'use strict'
$(document).ready(function () {
    $('#url-form').find('.btn').on('click', function (ev) {
        var url = $('#url-form').find('input').val();
        var data ={};
        data.url = url;
        addUrl(data);
    })
});

function addUrl(url) {
    jQuery.ajax({
        type: "PUT",
        url: "/UrlShortener",
        accepts : "application/json",
        cache : false,
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
    var link = $('#result').find('.modal-body').find('a');
    link.text(data.url);
    link.attr("href",data.url);
}