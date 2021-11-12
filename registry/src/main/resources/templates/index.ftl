<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>分布式监控平台</title>
    <script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-fQybjgWLrvvRgtW6bFlB7jaZrFsaBXjsOMm/tB9LTS58ONXgqbR9W8oWht/amnpF"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
          integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
</head>
<body>
<div style="margin: 50px">
    <h1 style="text-align: center;margin: 30px;">分布式监控平台</h1>

    <div id="alertBox">
    </div>

    <div class="input-group mb-3" style="margin: auto; text-align: center;">
        <input id="workInput" type="text" class="form-control" placeholder="cmd path" aria-label="cmd path"
               aria-describedby="button-addon2">
        <div class="input-group-append">
            <button class="btn btn-outline-secondary" type="button" id="workBtn">执行任务</button>
        </div>
    </div>
    <table class="table table-bordered" style="margin-top: 50px">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">实例</th>
            <th scope="col">注册时间</th>
            <th scope="col">刷新时间</th>
            <th scope="col">状态</th>
            <th scope="col">操作</th>
        </tr>
        </thead>
        <tbody id="tbody">
        </tbody>
    </table>
</div>
<script src="../static/main.js"></script>
</body>
</html>