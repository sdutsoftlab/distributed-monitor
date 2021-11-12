setInterval(function () {
        $.ajax({
            async: true,
            url: "instances",
            type: "get",
            success(data) {
                $('#tbody').empty();
                var html = "";
                var i = 0;
                for (var key in data) {
                    i++;
                    var item = data[key];
                    html +=
                        `<tr>
                        <td scope="col">${i}</td>
                        <td scope="col">
                            <a href="detail?addr=${item.addr}">${item.addr}</a>
                        </td>
                        <td scope="col">
                            <span class="badge badge-secondary">${item.regisTime}</span>
                        </td>
                        <td scope="col">
                            <span class="badge badge-secondary">${item.lastRegisTime}</span>
                        </td>`;
                    if (item.status === "OK") {
                        html += `
                            <td scope="col"><span class="badge badge-success">${item.status}</span></td>
                            <td scope="col">
                                <span class="badge badge-danger">
                                    <a style="color: white" href="shutdown?addr=${item.addr}">关机</a>
                                </span>
                            </td>
                        </tr>`;
                    } else {
                        html += `
                            <td scope="col"><span class="badge badge-danger">${item.status}</span></td>
                            <td scope="col">
                                <span class="badge badge-danger">
                                    <a style="color: white" href="shutdown?addr=${item.addr}">关机</a>
                                </span>
                            </td>
                        </tr>`
                    }
                }
                $('#tbody').html(html);
            }
        })
    },
    1000
)

document.getElementById("workBtn").onclick = function () {
    var val = document.getElementById("workInput").value;
    console.log(val);
    $.ajax({
        async: true,
        url: "runWork?cmd=" + val,
        type: "get",
        success(data) {
            var alertDiv = `<div class="alert alert-success alert-dismissible fade show" role="alert">
                                <strong id="alertValue">${data}</strong>
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>`
            document.getElementById("alertBox").innerHTML = alertDiv;
            console.log(data);
        }
    })
}