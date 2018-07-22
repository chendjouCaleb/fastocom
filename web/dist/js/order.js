$(document).ready(function (e) {
    $(".accept-order").click(function (e) {
        e.preventDefault();
        var btn = $(this);
        var href = btn.attr("href");


        $.ajax({
            type: 'POST',
            data: {},
            contentType: false,
            url: href,
            success: function (f) {
                btn.parent().parent().find(".state").removeClass("btn-info").addClass("btn-success").text("acceptée");
                btn.parent().html("");
                new Toast("Commande accepté.", {color: 'success'})
            },
            error: function (e) {
                console.log(e);
                new Toast(e.responseJSON.detail, {color:'danger'})
            }
        })

    })


    $(".accept-reject").click(function (e) {
        e.preventDefault();
        var btn = $(this);
        var href = btn.attr("href");


        $.ajax({
            type: 'PUT',
            data: {},
            contentType: false,
            url: href,
            success: function (f) {
                btn.parent().parent().find(".state").removeClass("btn-info").addClass("btn-danger").text("rejetée");
                btn.parent().html("");
                new Toast("Commande rejetée.", {color: 'warning'})
            },
            error: function (e) {
                console.log(e);
                new Toast(e.responseJSON.detail, {color:'danger'})
            }
        })

    })
})

let Toast = function(message, property) {
    if (property === undefined) property = {};
    let color = {
        info:   "#00ADD2",
        danger: "#e81b58",
        warning:"#FF6D37",
        success:"#00CB70",
        dark:   "#06133B"
    };
    let duration = 2000;

    let container = document.createElement("div");
    container.className = "toast-container";
    container.innerHTML = message;
    property.color === undefined ? container.style.backgroundColor = color.info : container.style.backgroundColor = color[property.color];
    property.duration === undefined ? duration = 3000 : duration = color[property.duration];

    document.body.appendChild(container);
    setTimeout(() => container.style.display = "none", duration)
};