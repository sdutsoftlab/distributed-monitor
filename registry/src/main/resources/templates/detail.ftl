<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="refresh" content="5">
    <title>分布式监控平台</title>
    <script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
          integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
</head>
<body>
<div id="main" style="margin: auto; max-width: 1400px;">

    <h1 style="text-align: center;margin-top: 30px;">实例信息</h1>



    <#if cpu??>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th scope="col" colSpan="8" style="text-align: center">CPU信息</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>核数</td>
                <td><span class="badge badge-pill badge-info">${cpu["cpuNum"]!}</span></td>
                <td>空闲</td>
                <td><span class="badge badge-pill badge-info">${cpu["free"]!}${cpu["unit"]!}</span></td>
                <td>系统占用</td>
                <td><span class="badge badge-pill badge-info">${cpu["sys"]!}${cpu["unit"]!}</span></td>
                <td>使用占用</td>
                <td><span class="badge badge-pill badge-info">${cpu["used"]!}${cpu["unit"]!}</span></td>
            </tr>
            </tbody>
        </table>
    </#if>

    <#if jvm??>
        <table class="table table-bordered">
            <!-- JVM -->
            <thead>
            <tr>
                <th scope="col" colSpan="10" style="text-align: center">JVM信息</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>空闲内存</td>
                <td><span class="badge badge-pill badge-info">${jvm["free"]!}${jvm["unit"]!}</span></td>
                <td>总内存</td>
                <td><span class="badge badge-pill badge-info">${jvm["total"]!}${jvm["unit"]!}</span></td>
                <td>已使用</td>
                <td><span class="badge badge-pill badge-info">${jvm["used"]!}${jvm["unit"]!}</span></td>
                <td>最大内存</td>
                <td><span class="badge badge-pill badge-info">${jvm["max"]!}${jvm["unit"]!}</span></td>
                <td>使用率</td>
                <td><span class="badge badge-pill badge-info">${jvm["usage"]!}</span></td>
            </tr>
            <tr>
                <td>虚拟机版本</td>
                <td colSpan="3">${jvm["name"]!}</td>
                <td>版本</td>
                <td>${jvm["version"]!}</td>
                <td>Home路径</td>
                <td colSpan="3">${jvm["home"]!}</td>
            </tr>
            <tr>
                <td>开始时间</td>
                <td>${jvm["startTime"]!}5</td>
                <td>已经运行</td>
                <td><span class="badge badge-pill badge-info">${jvm["runTime"]!}</span></td>
            </tr>
            </tbody>
        </table>
    </#if>

    <#if mem??>
        <table class="table table-bordered">
            <!-- 内存 -->
            <thead>
            <tr>
                <th scope="col" colSpan="10" style="text-align: center">内存信息</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>总内存</td>
                <td><span class="badge badge-pill badge-info">${mem["total"]!}${mem["unit"]!}</span></td>
                <td>空闲内存</td>
                <td><span class="badge badge-pill badge-info">${mem["free"]!}${mem["unit"]!}</span></td>
                <td>已使用</td>
                <td><span class="badge badge-pill badge-info">${mem["used"]!}${mem["unit"]!}</span></td>
                <td>使用率</td>
                <td><span class="badge badge-pill badge-info">${mem["usage"]!}</span></td>
            </tr>
            </tbody>
        </table>
    </#if>

    <#if sys??>
        <table class="table table-bordered">
            <!-- 系统 -->
            <thead>
            <tr>
                <th scope="col" colSpan="10" style="text-align: center">系统信息</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>IP</td>
                <td>${sys["computerIP"]!}</td>
                <td>实例名称</td>
                <td>${sys["computerName"]!}</td>
                <td>MAC</td>
                <td>${sys["macAddr"]!}</td>
                <td>系统架构</td>
                <td><span class="badge badge-pill badge-info">${sys["osArch"]!}</span></td>
            </tr>
            <tr>
                <td>OS</td>
                <td><span class="badge badge-pill badge-info">${sys["osName"]!}</span></td>
                <td>用户目录</td>
                <td>${sys["userDir"]!}</td>
            </tr>
            </tbody>
        </table>
    </#if>
</div>
</body>
</html>