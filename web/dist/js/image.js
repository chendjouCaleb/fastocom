$(document).ready(function () {
    $("#imageForm").submit(function (e) {
        e.preventDefault();
        var form = $("#imageForm");
        var data = new FormData(form[0]);

        $.ajax({
            type:'POST',
            data: data,
            enctype: 'multipart/form-data',
            url: form.attr("action"),
            processData: false,
            contentType: false,
            timeout:60000,
            success: function (data) {
                    console.log(data)
                    form.find("#image").attr("src", form.attr("action")+ "?x"+new Date().getSeconds()) ;
            },
            error: function (data) {
                console.log(data)
            }
        })
    })
});